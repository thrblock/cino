package com.thrblock.cino.glshape;

import java.awt.Color;

public abstract class GLMutiPointShape extends GLShape {
    protected float lineWidth = 1.0f;
    protected final GLPoint[] points;
    private float theta = 0;
    public GLMutiPointShape(GLPoint... points) {
        this.points = points;
    }
    
    public int maxPointIndex() {
    	return points.length - 1;
    }
    
    public float getLineWidth() {
		return lineWidth;
	}

    public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}
    public float getTheta() {
        return theta;
    }
    @Override
    public void setColor(Color c) {
        for(GLPoint point:points) {
            point.setColor(c);
        }
    }
    
    public void setPointColor(int index,Color c) {
        if(index >=0 && index < points.length) {
            points[index].setColor(c);
        }
    }
    
    @Override
    public void setAlpha(float alpha) {
    	for(GLPoint point:points) {
            point.setAlpha(alpha);
        }
    }
    
    public void setPointAlpha(int index,int alpha) {
        if(index >=0 && index < points.length) {
            points[index].setAlpha(alpha);
        }
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
    
    public void setCentralX(float x) {
        setXOffset(x - getCentralX());
    }

    public float getCentralY() {
        float result = 0;
        for (GLPoint point : points) {
            result += point.getY();
        }
        return result / points.length;
    }
    
    public void setCentralY(float y) {
    	setYOffset(y - getCentralY());
    }
    
    @Override
    public void setXOffset(float offset) {
        for (GLPoint point : points) {
            point.setXOffset(offset);
        }
    }
    @Override
    public void setYOffset(float offset) {
        for (GLPoint point : points) {
            point.setYOffset(offset);
        }
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
