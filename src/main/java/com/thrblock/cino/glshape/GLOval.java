package com.thrblock.cino.glshape;

public class GLOval extends GLPolygonShape {
	private GLOval(GLPoint[] points) {
		super(points);
	}
	
	public static GLOval generate(float x,float y,float axisA,float axisB,int accuracy) {
		double thetaAcc = 2*Math.PI/accuracy;
		GLPoint[] ovalpoints = new GLPoint[accuracy];
		for(int i = 0;i < ovalpoints.length;i++) {
			float px = (axisA/2)*(float)Math.cos(thetaAcc*i)+x+axisA/2;
			float py = (axisB/2)*(float)Math.sin(thetaAcc*i)+y+axisB/2;
			ovalpoints[i] = new GLPoint(px,py);
		}
		return new GLOval(ovalpoints);
	}
}
