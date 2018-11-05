package com.thrblock.cino.gllayer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.glprocessor.GLScreenSizeChangeListenerHolder;
import com.thrblock.cino.glshape.GLShape;
import com.thrblock.cino.gltransform.GLTransformManager;

/**
 * GLLayerContainer 绘制层容器<br />
 * 会按照提供的需求索引动态的拓展绘制层结构 <br />
 * 提供特殊索引-1代表顶层绘制层结构，使用此索引的图形对象会处于其它全部层的上方 <br />
 * 
 * @author lizepu
 */
@Component
public class GLLayerManager implements IGLFrameBufferObjectManager {
    private static final Logger LOG = LoggerFactory.getLogger(GLLayerManager.class);
    private List<GLLayer> layerList = new CopyOnWriteArrayList<>();
    private Deque<GLFrameBufferObject> globalFBOStack = new ArrayDeque<>();
    private GLLayer topLayer;
    private Semaphore layerSp = new Semaphore(1);

    @Value("${cino.frame.screen.width:800}")
    private int frameSizeW;
    @Value("${cino.frame.screen.height:600}")
    private int frameSizeH;
    @Value("${cino.frame.flexmode:0}")
    private int flexmode;
    @Value("${cino.layer.maxlayer:255}")
    private int maxLayer;

    @Autowired
    private GLScreenSizeChangeListenerHolder eventProcessor;

    @Autowired
    private GLTransformManager transformManager;

    private List<GLFrameBufferObject> fboSwap = new LinkedList<>();
    private Semaphore swapSp = new Semaphore(1);
    private GLFrameBufferObject[] globalFBOArr = new GLFrameBufferObject[0];
    private boolean refreshFBO = false;

    @PostConstruct
    void init() {
        this.topLayer = new GLLayer(globalFBOStack, frameSizeW, frameSizeH);
        eventProcessor.addScreenSizeChangeListener(topLayer::noticeScreenChange);
    }

    /**
     * 根据索引 获得一个绘制层结构
     * 
     * @param index 索引 大于等于0的数字代表层数，高层覆盖底层，不存在的层将会自动创建。<br />
     *              特别的:使用-1作为索引，会放置图形对象到预定义的最顶层，该层不会被任意其它层覆盖
     * @return 层次对象
     */
    public GLLayer getLayer(int index) {
        if (index == -1) {
            return topLayer;
        }
        if (layerList.size() > index) {
            return layerList.get(index);
        }
        layerSp.acquireUninterruptibly();
        if (layerList.size() <= index) {
            LOG.warn("layer not found:{}", index);
            for (int i = layerList.size(); i <= index; i++) {
                LOG.info("layer auto generated:{}", i);
                GLLayer gen = new GLLayer(globalFBOStack, frameSizeW, frameSizeH);
                eventProcessor.addScreenSizeChangeListener(gen::noticeScreenChange);
                layerList.add(gen);
            }
        }
        layerSp.release();
        return layerList.get(index);
    }

    /**
     * 将一个图形对象加入交换区，当绘制结束后会加入下次绘制的列表中
     * 
     * @param index 层次索引
     * @param shape 图形对象
     */
    public void addShapeToSwap(int index, GLShape<?> shape) {
        shape.setLayerIndex(index);
        if (index == -1) {
            topLayer.addShapeToSwap(shape);
            return;
        }
        getLayer(index).addShapeToSwap(shape);
    }

    /**
     * 将交换区内的图形对象加入绘制队列，一般由绘制同步逻辑进行调用
     */
    public void swap() {
        if (refreshFBO) {
            swapSp.acquireUninterruptibly();
            refreshFBO = false;
            for (GLFrameBufferObject fbo : globalFBOArr) {
                fbo.resize(frameSizeW, frameSizeH);
            }
            swapSp.release();
        }
        if (!fboSwap.isEmpty()) {
            swapSp.acquireUninterruptibly();
            int totalLength = globalFBOArr.length + fboSwap.size();
            GLFrameBufferObject[] arr = new GLFrameBufferObject[totalLength];
            System.arraycopy(globalFBOArr, 0, arr, 0, globalFBOArr.length);
            Object[] fboSwapArr = fboSwap.toArray();
            System.arraycopy(fboSwapArr, 0, arr, globalFBOArr.length, fboSwapArr.length);
            this.globalFBOArr = arr;
            fboSwap.clear();
            swapSp.release();
        }
        for (int i = 0; i < layerList.size(); i++) {
            layerList.get(i).swap();
        }
        topLayer.swap();
    }

    /**
     * 返回当前的层次数量（不包含预定义的最顶层）
     * 
     * @return 层次数量
     */
    public int size() {
        return layerList.size();
    }

    /**
     * 按照层次绘制全部图形对象
     * 
     * @param gl2 OpenGL2 上下文
     */
    public void drawAllLayer(GL2 gl2) {
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        beforeAllLayerDraw(gl2);
        for (int i = 0; i < layerList.size(); i++) {
            drawLayer(getLayer(i), gl2, i);
        }
        drawLayer(getLayer(-1), gl2, -1);
        gl2.glFlush();
        afterAllLayerDraw(gl2);
        swap();
    }

    private void afterAllLayerDraw(GL2 gl2) {
        while (!globalFBOStack.isEmpty()) {
            GLFrameBufferObject crt = globalFBOStack.pop();
            GLFrameBufferObject next = globalFBOStack.peek();
            if (next != null) {
                next.bindFBO(gl2, true);
            } else {
                crt.unBindFBO(gl2);
            }
            crt.drawAsTexture(gl2);
        }
    }

    private void beforeAllLayerDraw(GL2 gl2) {
        if (globalFBOArr.length > 0) {
            for (int i = 0; i < globalFBOArr.length; i++) {
                globalFBOStack.push(globalFBOArr[i]);
                if (i == globalFBOArr.length - 1) {
                    globalFBOArr[i].bindFBO(gl2, true);
                }
            }
        }
    }

    private void drawLayer(GLLayer layer, GL2 gl2, int i) {
        gl2.glBlendFunc(layer.getMixA(), layer.getMixB());
        transformManager.initBeforeLayer(gl2, i);
        layer.draw(gl2);
    }

    @Override
    public GLFrameBufferObject generateLayerFBO(int index) {
        return getLayer(index).generageFBO(frameSizeW, frameSizeH, flexmode);
    }

    @Override
    public GLFrameBufferObject generateGlobalFBO() {
        swapSp.acquireUninterruptibly();
        GLFrameBufferObject result = new GLFrameBufferObject(frameSizeW, frameSizeH, flexmode);
        fboSwap.add(result);
        swapSp.release();
        return result;
    }

    /**
     * 通知 屏幕尺寸变更
     * 
     * @param w
     * @param h
     */
    @ScreenSizeChangeListener
    public void noticeScreenChange(int w, int h) {
        this.frameSizeW = w;
        this.frameSizeH = h;
        swapSp.acquireUninterruptibly();
        this.refreshFBO = true;
        swapSp.release();
    }

}
