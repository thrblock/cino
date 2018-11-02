package com.thrblock.cino.glshape;

import java.awt.Color;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.vec.Vec2;
import com.thrblock.cino.vec.Vec3;
import com.thrblock.cino.vec.Vec4;

/**
 * 图形对象 点，这是仅包含一个点的图形对象
 * 
 * @author lizepu
 */
public class GLPoint extends GLShape {
    private float pointSize = 1f;
    private Vec2 point;
    private Vec4 color = new Vec4(1.0f);
    private float theta = 0;

    /**
     * 使用坐标构造一个图形对象
     * 
     * @param x 顶点横坐标
     * @param y 顶点纵坐标
     */
    public GLPoint(float x, float y) {
        this.point = new Vec2(x, y);
    }
    
    /**
     * 使用坐标构造一个图形对象
     * @param xy vec2
     */
    public GLPoint(Vec2 xy) {
        this.point = new Vec2(xy);
    }

    /**
     * 获得顶点大小参数
     * 
     * @return 顶点大小
     */
    public float getPointSize() {
        return pointSize;
    }

    /**
     * 设置顶点大小参数
     * 
     * @param pointSize 顶点大小
     */
    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }

    /**
     * 设置顶点颜色
     * 
     * @param c 颜色
     */
    public void setColor(Color c) {
        float r = c.getRed() / 255f;
        float g = c.getGreen() / 255f;
        float b = c.getBlue() / 255f;
        color.setRgb(new Vec3(r, g, b));
    }

    /**
     * 设置颜色分量R
     * 
     * @param r
     */
    public void setR(float r) {
        color.setR(r);
    }

    /**
     * 设置颜色分量G
     * 
     * @param g
     */
    public void setG(float g) {
        color.setG(g);
    }

    /**
     * 设置颜色分量B
     * 
     * @param b
     */
    public void setB(float b) {
        color.setB(b);
    }
    
    public void setRgba(Vec4 rgba) {
        color.setRgba(rgba);
    }

    /**
     * 获得颜色R分量
     * 
     * @return r分量
     */
    public float getR() {
        return color.getR();
    }

    /**
     * 获得颜色G分量
     * 
     * @return g分量
     */
    public float getG() {
        return color.getG();
    }

    /**
     * 获得颜色B分量
     * 
     * @return b分量
     */
    public float getB() {
        return color.getB();
    }
    
    /**
     * 获得颜色
     * @return
     */
    public Vec4 getRgba() {
        return new Vec4(color);
    }

    /**
     * 获得当前颜色对象
     * 
     * @return 颜色对象
     */
    public Color getColor() {
        return new Color(getR(), getG(), getB());
    }

    /**
     * {@inheritDoc}<br />
     * 获得该点的横坐标
     */
    @Override
    public float getX() {
        return point.getX();
    }

    /**
     * {@inheritDoc}<br />
     * 设置该点的横坐标
     */
    @Override
    public void setX(float x) {
        point.setX(x);
    }

    /**
     * {@inheritDoc}<br />
     * 设置该点的横向偏移量
     */
    @Override
    public void setXOffset(float offset) {
        point.setX(point.getX() + offset);
    }

    /**
     * {@inheritDoc}<br />
     * 获得该点的纵坐标
     */
    @Override
    public float getY() {
        return point.getY();
    }

    /**
     * {@inheritDoc}<br />
     * 设置该点的纵坐标
     */
    @Override
    public void setY(float y) {
        point.setY(y);
    }

    /**
     * {@inheritDoc}<br />
     * 设置该点的纵向偏移量
     */
    @Override
    public void setYOffset(float offset) {
        point.setY(point.getY() + offset);
    }

    /**
     * {@inheritDoc}<br />
     * 获得 通道
     */
    @Override
    public float getAlpha() {
        return color.getA();
    }

    /**
     * {@inheritDoc}<br />
     * 设置 通道,将自动截断为0~1.0f
     */
    @Override
    public void setAlpha(float alpha) {
        if (alpha < 0) {
            color.setA(0);
        } else if (alpha > 1.0f) {
            color.setA(1.0f);
        } else {
            color.setA(alpha);
        }
    }

    @Override
    public void drawShape(GL2 gl) {
        gl.glPointSize(pointSize);
        gl.glBegin(GL.GL_POINTS);
        gl.glColor4f(color.getR(), color.getG(), color.getB(), color.getA());
        gl.glVertex2f(point.getX(), point.getY());
        gl.glEnd();
    }

    /**
     * 获得与另一点的距离平方值
     * 
     * @param point 另一点
     * @return 距离的平方值
     */
    public float getDistanceSquare(GLPoint point) {
        float xl = getX() - point.getX();
        float yl = getY() - point.getY();
        return xl * xl + yl * yl;
    }

    /**
     * 获得与另一点的距离平方值
     * 
     * @param ax 另一点的x坐标
     * @param ay 另一点的y坐标
     * @return 距离的平方值
     */
    public float getDistanceSquare(float ax, float ay) {
        float xl = getX() - ax;
        float yl = getY() - ay;
        return xl * xl + yl * yl;
    }

    /**
     * 获得与另一点的距离值
     * 
     * @param point 另一点
     * @return 距离
     */
    public float getDistance(GLPoint point) {
        return (float) Math.sqrt(getDistanceSquare(point));
    }

    /**
     * 获得与另一点的距离值
     * 
     * @param ax 另一点的x坐标
     * @param ay 另一点的y坐标
     * @return 距离
     */
    public float getDistance(float ax, float ay) {
        return (float) Math.sqrt(getDistanceSquare(ax, ay));
    }

    @Override
    public float getCentralX() {
        return getX();
    }

    @Override
    public float getCentralY() {
        return getY();
    }

    /**
     * {@inheritDoc}<br />
     * 点图形的中心即为位置坐标
     */
    @Override
    public void setCentralX(float x) {
        setX(x);
    }

    /**
     * {@inheritDoc}<br />
     * 点图形的中心即为位置坐标
     */
    @Override
    public void setCentralY(float y) {
        setY(y);
    }

    /**
     * {@inheritDoc}<br />
     * 获得 旋转角度，该设置不会影响到点图形的显示，但会影响其子节点
     */
    @Override
    public float getRadian() {
        return theta;
    }

    /**
     * {@inheritDoc}<br />
     * 设置 旋转角度，该设置不会影响到点图形的显示，但会影响其子节点
     */
    @Override
    public void setRadian(float dstTheta) {
        this.theta = dstTheta;
    }

    /**
     * {@inheritDoc}<br />
     * 设置 旋转角度，指定旋转轴
     */
    @Override
    public void setRadian(float dstTheta, float cx, float cy) {
        float offset = dstTheta - this.theta;
        float nx = revolveX(getX(), getY(), cx, cy, offset);
        float ny = revolveY(getX(), getY(), cx, cy, offset);
        setX(nx);
        setY(ny);
        this.theta = dstTheta;
    }

    @Override
    public String toString() {
        return "GLPoint[" + point + "]";
    }

    @Override
    public void setXy(Vec2 xy) {
        point.setXy(xy);
    }

    @Override
    public Vec2 getXy() {
        return new Vec2(point);
    }

    @Override
    public Vec2 getCentral() {
        return getXy();
    }

    @Override
    public void setCentral(Vec2 xy) {
        setXy(xy);
    }
    
}
