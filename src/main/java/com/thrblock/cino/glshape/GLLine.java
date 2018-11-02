package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.util.math.CMath;
import com.thrblock.cino.vec.Vec2;

/**
 * 直线图形对象
 * 
 * @author lizepu
 *
 */
public class GLLine extends GLMultiPointShape {

    /**
     * 使用两点构造一个直线对象
     * 
     * @param start vec2
     * @param end   vec2
     */
    public GLLine(Vec2 start, Vec2 end) {
        super(start, end);
    }

    /**
     * 使用两点构造一个直线对象
     * 
     * @param x1 点1横坐标
     * @param y1 点1纵坐标
     * @param x2 点2横坐标
     * @param y2 点2纵坐标
     */
    public GLLine(float x1, float y1, float x2, float y2) {
        super(new Vec2(x1, y1), new Vec2(x2, y2));
    }

    /**
     * {@inheritDoc}<br />
     * 直线的角度被重定义为 x轴正 向沿逆时针方向转至 向量p0->p1 方向所需转动的角度（弧度单位）
     */
    @Override
    public float getRadian() {
        return CMath.getQuadrantTheta(getPointX(0), getPointY(0), getPointX(1), getPointY(1));
    }

    /**
     * 获得起点x 起点索引为0 同getPointX(0)
     * 
     * @return
     */
    public float getStartX() {
        return getPointX(0);
    }

    /**
     * 获得起点y 起点索引为0 同getPointY(0)
     * 
     * @return
     */
    public float getStartY() {
        return getPointY(0);
    }

    public Vec2 getStart() {
        return new Vec2(getStartX(), getStartY());
    }

    /**
     * 获得终点x 终点索引为1 同getPointX(1)
     * 
     * @return
     */
    public float getEndX() {
        return getPointX(1);
    }

    /**
     * 获得终点y 终点索引为1 同getPointY(1)
     * 
     * @return
     */
    public float getEndY() {
        return getPointY(1);
    }

    public Vec2 getEnd() {
        return new Vec2(getEndX(), getEndY());
    }

    /**
     * 设置起点x坐标
     * 
     * @param sx
     */
    public void setStartX(float sx) {
        points[0].setX(sx);
    }

    /**
     * 设置起点y坐标
     * 
     * @param sy
     */
    public void setStartY(float sy) {
        points[0].setY(sy);
    }

    public void setStart(Vec2 xy) {
        setStartX(xy.getX());
        setStartY(xy.getY());
    }

    /**
     * 设置终点x坐标
     * 
     * @param ex
     */
    public void setEndX(float ex) {
        points[1].setX(ex);
    }

    /**
     * 设置终点y坐标
     * 
     * @param ey
     */
    public void setEndY(float ey) {
        points[1].setY(ey);
    }

    public void setEnd(Vec2 xy) {
        setEndX(xy.getX());
        setEndY(xy.getY());
    }

    @Override
    public void drawShape(GL2 gl) {
        gl.glLineWidth(lineWidth);
        gl.glBegin(GL.GL_LINES);
        for (int i = 0; i < points.length; i++) {
            GLPoint point = points[i];
            gl.glColor4f(point.getR(), point.getG(), point.getB(), point.getAlpha());
            gl.glVertex2f(point.getX(), point.getY());
        }
        gl.glEnd();
        gl.glLineWidth(1.0f);
    }
}
