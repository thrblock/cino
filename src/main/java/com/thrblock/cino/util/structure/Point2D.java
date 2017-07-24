package com.thrblock.cino.util.structure;

/**
 * 2D 点坐标
 * @author thrblock
 *
 */
public class Point2D {
    private float x;
    private float y;

    /**
     * 构造2D 点坐标 位置(0,0)
     */
    public Point2D() {
        this(0f,0f);
    }
    /**
     * 构造2D 点坐标 位置(x,y)
     * @param x
     * @param y
     */
    public Point2D(float x,float y) {
        this.x = x;
        this.y = y;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
