package com.thrblock.cino.concept;

public abstract class GeometricConcept {

    /**
     * 自旋角度
     */
    protected float radian;

    public float getRadian() {
        return radian;
    }

    public void setRadian(float radian) {
        this.radian = radian;
    }

    public float revolveX(float x, float y, float cx, float cy, float theta) {
        float cdx = x - cx;
        float cdy = y - cy;
        return (float) (cdx * Math.cos(theta) - cdy * Math.sin(theta)) + cx;
    }

    public float revolveY(float x, float y, float cx, float cy, float theta) {
        float cdx = x - cx;
        float cdy = y - cy;
        return (float) (cdx * Math.sin(theta) + cdy * Math.cos(theta)) + cy;
    }
}
