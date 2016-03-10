package com.thrblock.cino.drawable;

import com.jogamp.opengl.GL2;

public abstract class MutiPointGLShape extends GLShape{
	private final GLPoint[] points;
	public MutiPointGLShape(GLPoint... points) {
		this.points = points;
	}
	public float getCentralX() {
		float result = 0;
		for(GLPoint point:points) {
			result += point.getX();
		}
		return result/points.length;
	}
	public float getCentralY() {
		float result = 0;
		for(GLPoint point:points) {
			result += point.getY();
		}
		return result/points.length;
	}
	public void setXOffset(float offset) {
		for(GLPoint point:points) {
			point.setXOffset(offset);
		}
	}
	public void setYOffset(float offset) {
		for(GLPoint point:points) {
			point.setYOffset(offset);
		}
	}
	public void revolve(float thetaOffset) {
		
	}
	@Override
	public void drawShape(GL2 gl) {
		
	}
}
