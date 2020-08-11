package com.thrblock.cino.glshape;

import java.awt.Color;
import java.util.Arrays;

import com.thrblock.cino.concept.MultiPoint;
import com.thrblock.cino.vec.Vec2;

/**
 * 多点图形对象（抽象） 定义了包含多个点的图形
 * 
 * @author lizepu
 *
 */
public abstract class GLMultiPointShape<R extends MultiPoint> extends GLShape<R> {
    protected float lineWidth = 1.0f;
    protected final GLPoint[] points;

    public GLMultiPointShape(R r) {
        super(r);
        this.points = Arrays.stream(r.getPoints()).map(GLPoint::new).toArray(GLPoint[]::new);
    }
    
    /**
     * 获得顶点的最大索引值
     * 
     * @return 顶点最大索引值
     */
    public int maxPointIndex() {
        return points.length - 1;
    }

    /**
     * 获得顶点数量
     * 
     * @return 顶点数量
     */
    public int getPointNumber() {
        return points.length;
    }

    /**
     * 获得线宽度
     * 
     * @return 线的宽度
     */
    public float getLineWidth() {
        return lineWidth;
    }

    /**
     * 设置线宽度
     * 
     * @param lineWidth 线的宽度
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * 设置所有点的颜色
     * 
     * @param c 颜色对象
     */
    public void setAllPointColor(Color c) {
        for (int i = 0; i < points.length; i++) {
            GLPoint point = points[i];
            point.setColor(c);
        }
    }

    /**
     * 设置指定索引点的颜色
     * 
     * @param index 点索引
     * @param c     颜色对象
     */
    public void setPointColor(int index, Color c) {
        if (index >= 0 && index < points.length) {
            points[index].setColor(c);
        }
    }

    /**
     * 设置指定索引点的颜色R分量
     * 
     * @param index 点索引
     * @param r     颜色的R分量 0~255
     */
    public void setPointR(int index, int r) {
        points[index].setR(r);
    }

    /**
     * 获得指定索引点的R分量
     * 
     * @param index 点索引
     * @return R分量 0~255
     */
    public float getPointR(int index) {
        return points[index].getR();
    }

    /**
     * 设置指定索引点的颜色G分量
     * 
     * @param index 点索引
     * @param g     颜色的G分量 0~255
     */
    public void setPointG(int index, int g) {
        points[index].setG(g);
    }

    /**
     * 获得指定索引点的G分量
     * 
     * @param index 点索引
     * @return G分量 0~255
     */
    public float getPointG(int index) {
        return points[index].getG();
    }

    /**
     * 设置指定索引点的颜色B分量
     * 
     * @param index 点索引
     * @param b     颜色的B分量 0~255
     */
    public void setPointB(int index, int b) {
        points[index].setB(b);
    }

    /**
     * 获得指定索引点的B分量
     * 
     * @param index 点索引
     * @return B分量 0~255
     */
    public float getPointB(int index) {
        return points[index].getB();
    }

    /**
     * {@inheritDoc}<br />
     * 设置 通道，多点图形将设置全部点的通道为alpha<br />
     * 
     * @see #setPointAlpha(int, int)
     */
    @Override
    public void setAlpha(float alpha) {
        for (GLPoint point : points) {
            point.setAlpha(alpha);
        }
    }

    /**
     * {@inheritDoc}<br />
     * 获得 通道，多点图形将返回第一个点的通道
     * 
     * @see #getPointAlpha(int)
     */
    @Override
    public float getAlpha() {
        return points[0].getAlpha();
    }

    /**
     * 设置指顶点的通道
     * 
     * @param index 索引，由0开始
     * @param alpha 通道
     */
    public void setPointAlpha(int index, int alpha) {
        if (index >= 0 && index < points.length) {
            points[index].setAlpha(alpha);
        }
    }

    /**
     * 获得指顶点的通道
     * 
     * @param index 索引，由0开始
     * @return 通道量
     */
    public float getPointAlpha(int index) {
        return points[index].getAlpha();
    }

    /**
     * {@inheritDoc}<br />
     * 获得 旋转角度,定义为逆时针方向旋转的弧度
     */
    @Override
    public float getRadian() {
        return concept.getRadian();
    }

    /**
     * {@inheritDoc}<br />
     * 设置 自旋角度,定义为逆时针方向旋转的弧度
     */
    @Override
    public void setRadian(float dstTheta) {
        concept.setRadian(dstTheta);
    }

    /**
     * {@inheritDoc}<br />
     * 以x,y为轴旋转图形,定义为逆时针方向旋转的相对弧度
     */
    @Override
    public void revolve(float dstTheta, float x, float y) {
        concept.revolve(x, y, dstTheta);
    }

    /**
     * 设置自旋角度（角度制）
     * 
     * @param angle 角度
     */
    public void setDegree(float angle) {
        this.setRadian((float) (angle * Math.PI / 180));
    }

    /**
     * 获得指定顶点的x坐标
     * 
     * @param index 索引
     * @return 指定的x坐标
     */
    public float getPointX(int index) {
        return points[index].getX();
    }

    /**
     * 获得指定顶点的y坐标
     * 
     * @param index 索引
     * @return 指定的y坐标
     */
    public float getPointY(int index) {
        return points[index].getY();
    }
    
    /**
     * {@inheritDoc}<br />
     * 获得 位置坐标x，多点图形的位置定义为中心
     */
    @Override
    public float getX() {
        return getCentralX();
    }

    /**
     * {@inheritDoc}<br />
     * 设置 位置坐标x，多点图形的位置定义为中心
     */
    @Override
    public void setX(float x) {
        setCentralX(x);
    }

    /**
     * {@inheritDoc}<br />
     * 获得 位置坐标y，多点图形的位置定义为中心
     */
    @Override
    public float getY() {
        return getCentralY();
    }

    /**
     * {@inheritDoc}<br />
     * 设置 位置坐标y，多点图形的位置定义为中心
     */
    @Override
    public void setY(float y) {
        setCentralY(y);
    }

    /**
     * {@inheritDoc}<br />
     * 获得 中心位置x
     */
    @Override
    public float getCentralX() {
        return concept.getCentralX();
    }

    /**
     * {@inheritDoc}<br />
     * 设置 中心位置x
     */
    @Override
    public void setCentralX(float x) {
        concept.setCentralX(x);
    }

    /**
     * {@inheritDoc}<br />
     * 获得 中心位置y
     */
    @Override
    public float getCentralY() {
        return concept.getCentralY();
    }

    /**
     * {@inheritDoc}<br />
     * 设置 中心位置y
     */
    @Override
    public void setCentralY(float y) {
        concept.setCentralY(y);
    }

    /**
     * {@inheritDoc}<br />
     * 设置 水平偏移量
     */
    @Override
    public void setXOffset(float offset) {
        concept.setXOffset(offset);
    }

    /**
     * {@inheritDoc}<br />
     * 设置 垂直偏移量
     */
    @Override
    public void setYOffset(float offset) {
        concept.setYOffset(offset);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GLMutiPoint[");
        for (GLPoint point : this.points) {
            builder.append("(" + point.getX() + "," + point.getY() + ");");
        }
        builder.append(']');
        return builder.toString();
    }

    public void sameCentralOf(GLMultiPointShape<?> shape) {
        concept.sameCentralOf(shape.exuviate());
    }

    public void sameStatusOf(GLMultiPointShape<?> shape) {
        concept.sameStatusOf(shape.exuviate());
    }

    @Override
    public void setXy(Vec2 xy) {
        setCentral(xy);
    }

    @Override
    public Vec2 getXy() {
        return getCentral();
    }

    @Override
    public Vec2 getCentral() {
        return new Vec2(getCentralX(), getCentralY());
    }

    @Override
    public void setCentral(Vec2 xy) {
        setCentralX(xy.getX());
        setCentralY(xy.getY());
    }
}
