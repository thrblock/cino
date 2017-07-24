package com.thrblock.cino.gllayer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.glshape.GLShape;
import com.thrblock.cino.util.structure.CrudeLinkedList;

/**
 * 绘制层<br />
 * 一个绘制层定义了绘制顺序、混合模式、视角偏移量 一个绘制层包含了该层次的图形对象，并提供遍历支持
 * 
 * @author thrblock
 */
public class GLLayer {
    private float viewXOffset;
    private float viewYOffset;
    private int mixA = GL.GL_SRC_ALPHA;
    private int mixB = GL.GL_ONE_MINUS_SRC_ALPHA;
    private CrudeLinkedList<GLShape> shapeList = new CrudeLinkedList<>();
    private CrudeLinkedList<GLShape>.CrudeIter crudeIter = shapeList.genCrudeIter();
    private List<GLShape> shapSwap = new LinkedList<>();
    private List<GLFrameBufferObject> fboSwap = new LinkedList<>();
    private Semaphore swapSp = new Semaphore(1);

    private GLFrameBufferObject[] st = new GLFrameBufferObject[0];
    private Deque<GLFrameBufferObject> stack;
    private int frameSizeW = 800;
    private int frameSizeH = 600;
    private boolean refreshFBO = false;

    /**
     * 构造GLLayer
     * 
     * @param stack
     *            帧缓冲堆栈
     */
    public GLLayer(Deque<GLFrameBufferObject> stack) {
        this.stack = stack;
    }

    /**
     * 获得 次层次的视角X偏移量
     * 
     * @return 偏移量
     */
    public float getViewXOffset() {
        return viewXOffset;
    }

    /**
     * 设置此层次的X偏移量
     * 
     * @param viewXOffset
     *            X偏移量
     */
    public void setViewXOffset(float viewXOffset) {
        this.viewXOffset = viewXOffset;
    }

    /**
     * 获得 次层次的视角Y偏移量
     * 
     * @return 偏移量
     */
    public float getViewYOffset() {
        return viewYOffset;
    }

    /**
     * 设置此层次的Y偏移量
     * 
     * @param viewYOffset
     *            Y偏移量
     */
    public void setViewYOffset(float viewYOffset) {
        this.viewYOffset = viewYOffset;
    }

    /**
     * 操作OpenGL模型矩阵进行视角偏移
     * 
     * @param gl
     *            opengl绘制实例
     */
    public void viewOffset(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glTranslatef(viewXOffset, viewYOffset, 0);
    }

    /**
     * 获得混合因子alpha
     * 
     * @return 混合因子alpha
     */
    public int getMixA() {
        return mixA;
    }

    /**
     * 设置混合因子 alpha
     * 
     * @param mixA
     *            混合因子alpha
     */
    public void setMixA(int mixA) {
        this.mixA = mixA;
    }

    /**
     * 获得混合因子 beta
     * 
     * @return 混合因子beta
     */
    public int getMixB() {
        return mixB;
    }

    /**
     * 设置混合因子 beta
     * 
     * @param mixB
     *            混合因子beta
     */
    public void setMixB(int mixB) {
        this.mixB = mixB;
    }

    /**
     * 向此层次的交换区插入一个图形对象，交换区会在单次绘制完成后进行实际的同步插入
     * 
     * @param shape
     *            图形对象
     */
    public void addShapeToSwap(GLShape shape) {
        swapSp.acquireUninterruptibly();
        shapSwap.add(shape);
        swapSp.release();
    }

    /**
     * 执行同步插入，将交换区的对象插入实际绘制区
     */
    public void swap() {
        if(refreshFBO) {
            swapSp.acquireUninterruptibly();
            refreshFBO = false;
            for(GLFrameBufferObject fbo:st) {
                fbo.resize(frameSizeW, frameSizeH);
            }
            swapSp.release();
        }
        if (!shapSwap.isEmpty()) {
            swapSp.acquireUninterruptibly();
            shapeList.addAll(shapSwap);
            shapSwap.clear();
            swapSp.release();
        }
        if (!fboSwap.isEmpty()) {
            swapSp.acquireUninterruptibly();
            int totalLength = st.length + fboSwap.size();
            GLFrameBufferObject[] arr = new GLFrameBufferObject[totalLength];
            System.arraycopy(st, 0, arr, 0, st.length);
            Object[] fboSwapArr = fboSwap.toArray();
            System.arraycopy(fboSwapArr, 0, arr, st.length, fboSwapArr.length);
            this.st = arr;
            fboSwap.clear();
            swapSp.release();
        }
    }

    /**
     * 绘制此层次
     * 
     * @param gl2
     */
    public void draw(GL2 gl2) {
        beforeDraw(gl2);
        while (crudeIter.hasNext()) {
            GLShape shape = crudeIter.next();
            if (shape.isVisible()) {
                shape.beforeDraw(gl2);
                shape.drawShape(gl2);
                shape.afterDraw(gl2);
            }
            if (shape.isDestory()) {
                crudeIter.remove();
            }
        }
        crudeIter.reset();
        afterDraw(gl2);
    }

    private void beforeDraw(GL2 gl2) {
        if (st.length > 0) {
            for (int i = 0; i < st.length; i++) {
                stack.push(st[i]);
                st[i].bindFBO(gl2);
            }
        }
    }

    private void afterDraw(GL2 gl) {
        for (int i = 0; i < st.length; i++) {
            GLFrameBufferObject crt = stack.pop();
            GLFrameBufferObject next = stack.peek();
            if (next != null) {
                next.reBindFBO(gl);
            } else {
                crt.unBindFBO(gl);
            }
            crt.drawAsTexture(gl);
        }
    }

    /**
     * 创建一个属于此层次的帧缓冲对象
     * @param w
     * @param h
     * @param flexmode 缩放模式
     * @return
     */
    public GLFrameBufferObject generageFBO(int w, int h,int flexmode) {
        swapSp.acquireUninterruptibly();
        GLFrameBufferObject result = new GLFrameBufferObject(w, h, flexmode);
        fboSwap.add(result);
        swapSp.release();
        return result;
    }

    /**
     * 通知渲染窗口大小变化
     * @param w
     * @param h
     */
    public void noticeScreenChange(int w, int h) {
        this.frameSizeW = w;
        this.frameSizeH = h;
        swapSp.acquireUninterruptibly();
        this.refreshFBO   = true;
        swapSp.release();
    }
}
