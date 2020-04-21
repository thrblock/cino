package com.thrblock.cino.concept;

import com.thrblock.cino.vec.Vec2;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class Point extends GeometricConcept {

    private Vec2 vec;

    public Point(Vec2 vec2) {
        this.vec = new Vec2(vec2);
    }

    public Point(float f, float g) {
        this.vec = new Vec2(f, g);
    }

    public float getX() {
        return vec.getX();
    }

    public float getY() {
        return vec.getY();
    }

    public void setX(float x) {
        vec.setX(x);
    }

    public void setY(float y) {
        vec.setY(y);
    }

    public Vec2 getXy() {
        return new Vec2(vec);
    }

    public void setXy(Vec2 vec) {
        vec.setXy(vec);
    }
    
    public void setXOffset(float offset) {
        vec.setX(vec.getX() + offset);
    }
    
    public void setYOffset(float offset) {
        vec.setY(vec.getY() + offset);
    }

    /**
     * 获得与另一点的距离平方值
     * 
     * @param point 另一点
     * @return 距离的平方值
     */
    public float getDistanceSquare(Point point) {
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
    public float getDistance(Point point) {
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
    
    public void revolve(float cx,float cy,float radianOffset) {
        float cdx = getX() - cx;
        float cdy = getY() - cy;
        float x = (float) (cdx * Math.cos(radianOffset) - cdy * Math.sin(radianOffset)) + cx;
        float y = (float) (cdx * Math.sin(radianOffset) + cdy * Math.cos(radianOffset)) + cy;
        setX(x);
        setY(y);
    }
}
