package com.thrblock.cino.gllayer;

import java.util.ArrayDeque;
import java.util.Deque;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.gllifecycle.GLCycle;
import com.thrblock.cino.glshape.GLShape;

/**
 * 绘制层<br />
 * 一个绘制层定义了绘制顺序、混合模式、视角偏移量 一个绘制层包含了该层次的图形对象，并提供遍历支持
 * 
 * @author thrblock
 */
public class GLLayer {
    private int mixA = GL.GL_SRC_ALPHA;
    private int mixB = GL.GL_ONE_MINUS_SRC_ALPHA;
    private GLCycle<GLFrameBufferObject> fboCycle = new GLCycle<>(GLFrameBufferObject[]::new);
    private GLCycle<GLShape<?>> shapeCycle = new GLCycle<>(GLShape<?>[]::new);
    private Deque<GLFrameBufferObject> globalFBOStack;
    private Deque<GLFrameBufferObject> stack;

    /**
     * 构造GLLayer
     * 
     * @param stack 帧缓冲堆栈
     */
    public GLLayer(Deque<GLFrameBufferObject> globalStack) {
        this.globalFBOStack = globalStack;
        this.stack = new ArrayDeque<>();
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
     * @param mixA 混合因子alpha
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
     * @param mixB 混合因子beta
     */
    public void setMixB(int mixB) {
        this.mixB = mixB;
    }

    /**
     * 向此层次的交换区插入一个图形对象，交换区会在单次绘制完成后进行实际的同步插入
     * 
     * @param shape 图形对象
     */
    public void addShapeToSwap(GLShape<?> shape) {
        shapeCycle.safeAdd(shape);
    }

    /**
     * 绘制此层次
     * 
     * @param gl2
     */
    public void draw(GL2 gl2) {
        beforeDraw(gl2);
        GLShape<?>[] arr = shapeCycle.safeHold();
        for (int i = 0; i < arr.length; i++) {
            GLShape<?> shape = arr[i];
            if (shape.isVisible()) {
                shape.beforeDraw(gl2);
                shape.drawShape(gl2);
                shape.afterDraw(gl2);
            }
            if (shape.isDestory()) {
                shapeCycle.safeRemove(shape);
            }
        }
        afterDraw(gl2);
    }

    private void beforeDraw(GL2 gl2) {
        GLFrameBufferObject[] fboArr = fboCycle.safeHold();
        if (fboArr.length > 0) {
            for (int i = 0; i < fboArr.length; i++) {
                stack.push(fboArr[i]);
                if (i == fboArr.length - 1) {
                    fboArr[i].bindFBO(gl2, true);
                }
            }
        }
    }

    private void afterDraw(GL2 gl) {
        while (!stack.isEmpty()) {
            GLFrameBufferObject crt = stack.pop();
            GLFrameBufferObject next = stack.peek();
            if (next != null) {
                next.bindFBO(gl, true);
            } else if (!globalFBOStack.isEmpty()) {
                globalFBOStack.peek().bindFBO(gl, false);
            } else {
                crt.unBindFBO(gl);
            }
            crt.drawAsTexture(gl);
        }
    }

    /**
     * 创建一个属于此层次的帧缓冲对象
     * 
     * @param w
     * @param h
     * @param flexmode 缩放模式
     * @return
     */
    public GLFrameBufferObject generageFBO(int w, int h, int flexmode) {
        GLFrameBufferObject result = new GLFrameBufferObject(w, h, flexmode);
        fboCycle.safeAdd(result);
        return result;
    }

    /**
     * 通知渲染窗口大小变化
     * 
     * @param w
     * @param h
     */
    public void noticeScreenChange(int w, int h) {
        fboCycle.safeUpdate(fbo -> fbo.resize(w, h));
    }
}
