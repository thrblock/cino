package com.thrblock.cino.glshape;

import java.awt.Color;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLPoint extends GLShape {
    private float pointSize = 1f;
    private float x;
    private float y;
    
    private float alpha = 1.0f;
    private float r = 1.0f;
    private float g = 1.0f;
    private float b = 1.0f;
    private float theta = 0;
    public GLPoint(float x,float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }

    public void setColor(Color c) {
        this.r = c.getRed() / 255f;
        this.g = c.getGreen() / 255f;
        this.b = c.getBlue() / 255f;
    }
    
    public void setR(float r) {
		this.r = r;
	}

	public void setG(float g) {
		this.g = g;
	}

	public void setB(float b) {
		this.b = b;
	}

	public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public Color getColor() {
        return new Color(r,g,b);
    }
    /**
     * {@inheritDoc}<br />
     * 获得该点的横坐标
     * */
    @Override
    public float getX() {
        return x;
    }
    /**
     * {@inheritDoc}<br />
     * 设置该点的横坐标
     * */
    @Override
    public void setX(float x) {
        this.x = x;
    }
    /**
     * {@inheritDoc}<br />
     * 设置该点的横向偏移量
     * */
    @Override
    public void setXOffset(float offset) {
        this.x += offset;
    }
    /**
     * {@inheritDoc}<br />
     * 获得该点的纵坐标
     * */
    @Override
    public float getY() {
        return y;
    }
    /**
     * {@inheritDoc}<br />
     * 设置该点的纵坐标
     * */
    @Override
    public void setY(float y) {
        this.y = y;
    }
    /**
     * {@inheritDoc}<br />
     * 设置该点的纵向偏移量
     * */
    @Override
    public void setYOffset(float offset) {
        this.y += offset;
    }
    /**
     * {@inheritDoc}<br />
     * 获得 通道
     * */
    @Override
    public float getAlpha() {
        return alpha;
    }
    /**
     * {@inheritDoc}<br />
     * 设置 通道,将自动截断为0~1.0f
     * */
    @Override
    public void setAlpha(float alpha) {
        if(alpha < 0) {
        	this.alpha = 0;
        } else if(alpha > 1.0f) {
        	this.alpha = 1.0f;
        } else {
        	this.alpha = alpha;
        }
    }

    @Override
    public void drawShape(GL2 gl) {
        gl.glPointSize(pointSize);
        gl.glBegin(GL.GL_POINTS);
        gl.glColor4f(r,g,b,alpha);
        gl.glVertex2f(x, y);
        gl.glEnd();
    }
    
    public float getDistanceSquare(GLPoint point) {
        float xl = x - point.x;
        float yl = y - point.y;
        return xl * xl + yl * yl;
    }
    
    public float getDistanceSquare(float ax,float ay) {
        float xl = x - ax;
        float yl = y - ay;
        return xl * xl + yl*yl;
    }
    
    public float getDistance(GLPoint point) {
        return (float)Math.sqrt(getDistanceSquare(point));
    }
    
    public float getDistance(float ax,float ay) {
        return (float)Math.sqrt(getDistanceSquare(ax,ay));
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
     * */
	@Override
	public void setCentralX(float x) {
		setX(x);
	}

	/**
     * {@inheritDoc}<br />
     * 点图形的中心即为位置坐标
     * */
	@Override
	public void setCentralY(float y) {
		setY(y);
	}

	/**
     * {@inheritDoc}<br />
     * 获得 旋转角度，该设置不会影响到点图形的显示，但会影响其子节点
     * */
	@Override
	public float getTheta() {
		return theta;
	}

	/**
     * {@inheritDoc}<br />
     * 设置 旋转角度，该设置不会影响到点图形的显示，但会影响其子节点
     * */
	@Override
	public void setTheta(float dstTheta) {
		this.theta = dstTheta;
	}

	/**
     * {@inheritDoc}<br />
     * 设置 旋转角度，指定旋转轴
     * */
	@Override
	public void setTheta(float dstTheta, float cx, float cy) {
		float offset = dstTheta - this.theta;
		float nx = revolveX(x, y, cx, cy, offset);
		float ny = revolveY(x, y, cx, cy, offset);
		this.x = nx;
		this.y = ny;
	}
}
