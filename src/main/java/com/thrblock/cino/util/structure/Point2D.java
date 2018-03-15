package com.thrblock.cino.util.structure;

import com.thrblock.cino.util.math.CMath;

/**
 * 2D 点坐标
 * 
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
        this(0f, 0f);
    }

    /**
     * 构造2D 点坐标 位置(x,y)
     * 
     * @param x
     * @param y
     */
    public Point2D(float x, float y) {
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

    @Override
    public String toString() {
        return "Point2D[" + x + "," + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point2D) {
            Point2D another = (Point2D) obj;
            return CMath.floatEqual(x, another.x) && CMath.floatEqual(y, another.y);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (x + y);
    }

}
