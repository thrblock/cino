package com.thrblock.cino.drawable;

import com.jogamp.opengl.GL2;

public abstract class MutiPointGLShape extends GLShape {
    private final GLPoint[] points;
    private float theta = 0;
    public MutiPointGLShape(GLPoint... points) {
        this.points = points;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float dstTheta) {
        float offset = dstTheta - this.theta;
        float cx = getCentralX();
        float cy = getCentralY();
        for (GLPoint point : points) {
            float dx = revolveX(point.getX(),point.getY(),cx,cy,offset);
            float dy = revolveY(point.getX(),point.getY(),cx,cy,offset);
            point.setX(dx);
            point.setY(dy);
        }
        this.theta = dstTheta;
    }

    public float getCentralX() {
        float result = 0;
        for (GLPoint point : points) {
            result += point.getX();
        }
        return result / points.length;
    }

    public float getCentralY() {
        float result = 0;
        for (GLPoint point : points) {
            result += point.getY();
        }
        return result / points.length;
    }

    public void setXOffset(float offset) {
        for (GLPoint point : points) {
            point.setXOffset(offset);
        }
    }

    public void setYOffset(float offset) {
        for (GLPoint point : points) {
            point.setYOffset(offset);
        }
    }

    @Override
    public void drawShape(GL2 gl) {
        
    }

    private float revolveX(float x, float y, float cx, float cy, float theta) {
        float cdx = x - cx;
        float cdy = y - cy;
        return (float) (cdx * Math.cos(theta) - cdy * Math.sin(theta)) + cx;
    }

    private float revolveY(float x, float y, float cx, float cy, float theta) {
        float cdx = x - cx;
        float cdy = y - cy;
        return (float) (cdx * Math.sin(theta) + cdy * Math.cos(theta)) + cy;
    }
}
