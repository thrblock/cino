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
    
    /**
     * {@inheritDoc}<br />
     * 设置 通道，多点图形将设置全部点的通道为alpha<br />
     * @see #setPointAlpha(int, int)
     * */
    @Override
    public void setAlpha(float alpha) {
    	for(GLPoint point:points) {
            point.setAlpha(alpha);
        }
    }
    
    /**
     * {@inheritDoc}<br />
     * 获得 通道，多点图形将返回第一个点的通道
     * @see #getPointAlpha(int)
     * */
    @Override
    public float getAlpha() {
    	return points[0].getAlpha();
    }
    
    /**
     * 设置指定点的通道
     * @param index 索引，由0开始
     * @param alpha 通道
     */
    public void setPointAlpha(int index,int alpha) {
        if(index >=0 && index < points.length) {
            points[index].setAlpha(alpha);
        }
    }
    /**
     * 获得指定点的通道
     * @param index 索引，由0开始
     * @return 通道量
     */
    public float getPointAlpha(int index) {
    	return points[index].getAlpha();
    }
    
    /**
     * {@inheritDoc}<br />
     * 获得 旋转角度
     * */
    @Override
    public float getTheta() {
        return theta;
    }
    
    /**
     * {@inheritDoc}<br />
     * 设置 自旋角度
     * */
    @Override
    public void setTheta(float dstTheta) {
    	setTheta(dstTheta,getCentralX(),getCentralY());
    }
    /**
	 * {@inheritDoc}<br />
	 * 以x,y为轴旋转图形
	 */
	public void setTheta(float dstTheta,float x,float y){
        float offset = dstTheta - this.theta;
        for (GLPoint point : points) {
            float dx = revolveX(point.getX(),point.getY(),x,y,offset);
            float dy = revolveY(point.getX(),point.getY(),x,y,offset);
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
    
    /**
     * {@inheritDoc}<br />
     * 获得 位置坐标x，多点图形的位置定义为中心
     * */
    @Override
    public float getX() {
    	return getCentralX();
    }
    /**
     * {@inheritDoc}<br />
     * 设置 位置坐标x，多点图形的位置定义为中心
     * */
    @Override
    public void setX(float x) {
        setCentralX(x);
    }
    
    /**
     * {@inheritDoc}<br />
     * 获得 位置坐标y，多点图形的位置定义为中心
     * */
    @Override
    public float getY() {
    	return getCentralY();
    }
    /**
     * {@inheritDoc}<br />
     * 设置 位置坐标y，多点图形的位置定义为中心
     * */
    @Override
    public void setY(float y) {
        setCentralY(y);
    }
    
    /**
     * {@inheritDoc}<br />
     * 获得 中心位置x
     * */
    @Override
    public float getCentralX() {
        float result = 0;
        for (GLPoint point : points) {
            result += point.getX();
        }
        return result / points.length;
    }
    
    /**
     * {@inheritDoc}<br />
     * 设置 中心位置x
     * */
    @Override
    public void setCentralX(float x) {
        setXOffset(x - getCentralX());
    }
    /**
     * {@inheritDoc}<br />
     * 获得 中心位置y
     * */
    @Override
    public float getCentralY() {
        float result = 0;
        for (GLPoint point : points) {
            result += point.getY();
        }
        return result / points.length;
    }
    
    /**
     * {@inheritDoc}<br />
     * 设置 中心位置y
     * */
    @Override
    public void setCentralY(float y) {
    	setYOffset(y - getCentralY());
    }
    
    /**
     * {@inheritDoc}<br />
     * 设置 水平偏移量
     * */
    @Override
    public void setXOffset(float offset) {
        for (GLPoint point : points) {
            point.setXOffset(offset);
        }
    }
    /**
     * {@inheritDoc}<br />
     * 设置 垂直偏移量
     * */
    @Override
    public void setYOffset(float offset) {
        for (GLPoint point : points) {
            point.setYOffset(offset);
        }
    }
}
