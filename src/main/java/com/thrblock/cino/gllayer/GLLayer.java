package com.thrblock.cino.gllayer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.glshape.GLShape;
import com.thrblock.cino.util.structure.CrudeLinkedList;

/**
 * 绘制层<br />
 * 一个绘制层定义了绘制顺序、混合模式、视角偏移量
 * 一个绘制层包含了该层次的图形对象，并提供遍历支持
 * @author thrblock
 */
public class GLLayer {
    private float viewXOffset;
    private float viewYOffset;
    private int mixA = GL.GL_SRC_ALPHA;
    private int mixB = GL.GL_ONE_MINUS_SRC_ALPHA;
    private CrudeLinkedList<GLShape> shapeList = new CrudeLinkedList<>();
    private CrudeLinkedList<GLShape>.CrudeIter crudeIter = shapeList.genCrudeIter();
    private List<GLShape> swap = new LinkedList<>();
    private Semaphore swapSp = new Semaphore(1);
    
    /**
     * 获得 次层次的视角X偏移量
     * @return 偏移量
     */
    public float getViewXOffset() {
        return viewXOffset;
    }
    
    /**
     * 设置此层次的X偏移量
     * @param viewXOffset X偏移量
     */
    public void setViewXOffset(float viewXOffset) {
        this.viewXOffset = viewXOffset;
    }
    
    /**
     * 获得 次层次的视角Y偏移量
     * @return 偏移量
     */
    public float getViewYOffset() {
        return viewYOffset;
    }
    
    /**
     * 设置此层次的Y偏移量
     * @param viewYOffset Y偏移量
     */
    public void setViewYOffset(float viewYOffset) {
        this.viewYOffset = viewYOffset;
    }
    
    /**
     * 操作OpenGL模型矩阵进行视角偏移
     * @param gl opengl绘制实例
     */
    public void viewOffset(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glTranslatef(viewXOffset,viewYOffset,0);
    }
    
    /**
     * 获得混合因子alpha
     * @return 混合因子alpha
     */
    public int getMixA() {
        return mixA;
    }
    /**
     * 设置混合因子 alpha
     * @param mixA 混合因子alpha
     */
    public void setMixA(int mixA) {
        this.mixA = mixA;
    }
    /**
     * 获得混合因子 beta
     * @return 混合因子beta
     */
    public int getMixB() {
        return mixB;
    }
    /**
     * 设置混合因子 beta
     * @param mixB 混合因子beta
     */
    public void setMixB(int mixB) {
        this.mixB = mixB;
    }
    
    /**
     * 向此层次的交换区插入一个图形对象，交换区会在单次绘制完成后进行实际的同步插入
     * @param shape 图形对象
     */
    public void addShapeToSwap(GLShape shape) {
        swapSp.acquireUninterruptibly();
        swap.add(shape);
        swapSp.release();
    }
    
    /**
     * 执行同步插入，将交换区的对象插入实际绘制区
     */
    public void swap() {
        if(!swap.isEmpty()) {
            swapSp.acquireUninterruptibly();
            shapeList.addAll(swap);
            swap.clear();
            swapSp.release();
        }
    }
    
    /**
     * 提供此层次图形的迭代器
     * @return GLShape迭代器
     */
    public CrudeLinkedList<GLShape>.CrudeIter iterator() {
        return crudeIter;
    }
}
