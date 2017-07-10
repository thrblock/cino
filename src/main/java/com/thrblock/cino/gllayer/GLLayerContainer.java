package com.thrblock.cino.gllayer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.gllayer.fbo.GLFrameBufferObjectManager;
import com.thrblock.cino.glshape.GLShape;

/**
 * GLLayerContainer 绘制层容器<br />
 * 会按照提供的需求索引动态的拓展绘制层结构 <br />
 * 提供特殊索引-1代表顶层绘制层结构，使用此索引的图形对象会处于其它全部层的上方 <br />
 * 
 * @author lizepu
 */
@Component
public class GLLayerContainer {
    private static final Logger LOG = LoggerFactory.getLogger(GLLayerContainer.class);
    private List<GLLayer> layerList = new CopyOnWriteArrayList<>();
    private GLLayer topLayer = new GLLayer();

    @Autowired
    private GLFrameBufferObjectManager fboManager;
    
    @Value("${cino.frame.screen.width:800}")
    private int frameSizeW;
    @Value("${cino.frame.screen.height:600}")
    private int frameSizeH;

    /**
     * 根据索引 获得一个绘制层结构
     * 
     * @param index
     *            索引 大于等于0的数字代表层数，高层覆盖底层，不存在的层将会自动创建。<br />
     *            特别的:使用-1作为索引，会放置图形对象到预定义的最顶层，该层不会被任意其它层覆盖
     * @return 层次对象
     */
    public GLLayer getLayer(int index) {
        if (index == -1) {
            return topLayer;
        }
        if (layerList.size() <= index) {
            LOG.warn("layer not found:" + index);
            for (int i = layerList.size(); i <= index; i++) {
                LOG.info("layer auto generated:" + i);
                GLLayer gen = new GLLayer();
                layerList.add(gen);
            }
        }
        return layerList.get(index);
    }

    /**
     * 将一个图形对象加入交换区，当绘制结束后会加入下次绘制的列表中
     * 
     * @param index
     *            层次索引
     * @param shape
     *            图形对象
     */
    public void addShapeToSwap(int index, GLShape shape) {
        if (index == -1) {
            topLayer.addShapeToSwap(shape);
            return;
        }
        if (layerList.size() <= index) {
            LOG.warn("layer not found:" + index);
            for (int i = layerList.size(); i <= index; i++) {
                LOG.info("layer auto generated:" + i);
                GLLayer gen = new GLLayer();
                layerList.add(gen);
            }
        }
        layerList.get(index).addShapeToSwap(shape);
    }

    /**
     * 将交换区内的图形对象加入绘制队列，一般由绘制同步逻辑进行调用
     */
    public void swap() {
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
     * @param gl2
     *            OpenGL2 上下文
     */
    public void drawAllLayer(GL2 gl2) {
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        for (int i = 0; i < layerList.size(); i++) {
            fboManager.beforeLayerDraw(i);
            drawLayer(getLayer(i), gl2);
            fboManager.afterLayerDraw(i);
        }
        fboManager.beforeLayerDraw(-1);
        drawLayer(getLayer(-1), gl2);
        fboManager.afterLayerDraw(-1);
        gl2.glFlush();
        swap();
    }

    private void drawLayer(GLLayer layer, GL2 gl2) {
        gl2.glBlendFunc(layer.getMixA(), layer.getMixB());
        layer.viewOffset(gl2);
        layer.draw(gl2);
    }

}
