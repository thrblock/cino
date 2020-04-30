package com.thrblock.cino.glshape;

import org.springframework.beans.factory.annotation.Autowired;

import com.jogamp.opengl.GL2;
import com.thrblock.cino.concept.Polygon;
import com.thrblock.cino.io.MouseControl;
import com.thrblock.cino.vec.Vec2;

/**
 * 封闭式图形对象(抽象) 定义了一个封闭图形对象
 * 
 * @author lizepu
 *
 */
public class GLPolygonShape<E extends Polygon> extends GLMultiPointShape<E> {

    private boolean fill = false;
    
    @Autowired
    protected MouseControl mouseIO;

    /**
     * 以顶点数组构造一个封闭图形
     * 
     * @param points 顶点数组
     */
    public GLPolygonShape(E e) {
        super(e);
    }

    /**
     * 该图形是否填充
     * 
     * @return 是否填充的布尔值
     */
    public boolean isFill() {
        return fill;
    }

    /**
     * 设置是否填充的布尔值
     * 
     * @param fill 是否填充的布尔值
     */
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    /**
     * 判断 此多边形与另一多边形是否发生碰撞
     * 
     * @param another 另一个多边形
     * @return 是否发生碰撞的布尔值
     */
    public boolean isSquareableCollide(GLPolygonShape<?> another) {
        return concept.isSquareableCollide(another.concept);
    }

    public boolean isPointInside(Vec2 vec) {
        return concept.isPointInside(vec.getX(), vec.getY());
    }

    /**
     * 判断给定点是否在本图形内部
     * 
     * @param px 给定点的x坐标
     * @param py 给定点的y坐标
     * @return 点是否在内部的布尔值
     */
    public boolean isPointInside(float px, float py) {
        return concept.isPointInside(px, py);
    }
    
    /**
     * 鼠标指针是否处于多边形内<br />
     * 该方法考虑绘制层级对应的变换操作
     * @return
     */
    public boolean isMouseInside() {
        return !isDestory() && isPointInside(mouseIO.getMouseX(getLayerIndex()), mouseIO.getMouseY(getLayerIndex()));
    }

    @Override
    public void drawShape(GL2 gl) {
        gl.glLineWidth(lineWidth);
        if (fill) {
            gl.glBegin(GL2.GL_POLYGON);
        } else {
            gl.glBegin(GL2.GL_LINE_LOOP);
        }
        for (int i = 0; i < points.length; i++) {
            GLPoint point = points[i];
            gl.glColor4f(point.getR(), point.getG(), point.getB(), point.getAlpha());
            gl.glVertex2f(point.getX(), point.getY());
        }
        gl.glEnd();
        gl.glLineWidth(1.0f);
    }
}
