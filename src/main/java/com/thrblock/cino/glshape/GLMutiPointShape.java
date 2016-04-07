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
    
    public int getPointNumber() {
    	return points.length;
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

    public void setAllPointColor(Color c) {
        for(GLPoint point:points) {
            point.setColor(c);
        }
    }
    
    public void setPointColor(int index,Color c) {
        if(index >=0 && index < points.length) {
            points[index].setColor(c);
        }
    }
    
    public void setPointR(int index,int r) {
    	points[index].setR(r);
    }
    
    public float getPointR(int index) {
    	return points[index].getR();
    }
    
    public void setPointG(int index,int g) {
    	points[index].setG(g);
    }
    
    public float getPointG(int index) {
    	return points[index].getG();
    }
    
    public void setPointB(int index,int b) {
    	points[index].setB(b);
    }
    
    public float getPointB(int index) {
    	return points[index].getB();
    }
    
    public void setAllPointAlpha(float alpha) {
    	for(GLPoint point:points) {
            point.setAlpha(alpha);
        }
    }
    
    public void setPointAlpha(int index,int alpha) {
        if(index >=0 && index < points.length) {
            points[index].setAlpha(alpha);
        }
    }
    
    public float getPointAlpha(int index) {
    	return points[index].getAlpha();
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
    
    public void setAngle(float angle) {
    	this.setTheta((float)(angle * Math.PI / 180));
    }

    public float getPointX(int index) {
    	return points[index].getX();
    }
    public float getPointY(int index) {
    	return points[index].getY();
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
