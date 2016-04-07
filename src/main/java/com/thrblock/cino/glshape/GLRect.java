package com.thrblock.cino.glshape;

public class GLRect extends GLPolygonShape {
	public GLRect(float x,float y,float width,float height) {
		super(new GLPoint[]{
				new GLPoint(x,y),
				new GLPoint(x + width,y),
				new GLPoint(x + width,y + height),
				new GLPoint(x,y + height)});
	}
	
	public float getWidth() {
		float xl = points[0].getX() - points[1].getX();
		float yl = points[0].getY() - points[1].getY();
		return (float)(Math.sqrt(xl * xl + yl * yl));
	}
	
	public float getHeight() {
		float xl = points[1].getX() - points[2].getX();
		float yl = points[1].getY() - points[2].getY();
		return (float)(Math.sqrt(xl * xl + yl * yl));
	}
	
	public void setWidth(float width) {
		if(width > 0) {
			float m = getWidth();
			float k = width / m;
			
			points[1].setX(k * (points[1].getX() - points[0].getX()) + points[0].getX());
			points[2].setX(k * (points[2].getX() - points[3].getX()) + points[3].getX());
			points[1].setY(k * (points[1].getY() - points[0].getY()) + points[0].getY());
			points[2].setY(k * (points[2].getY() - points[3].getY()) + points[3].getY());
		}
	}
	
	public void setHeight(float height) {
		if(height > 0) {
			float m = getHeight();
			float k = height / m;
			
			points[2].setX(k * (points[2].getX() - points[1].getX()) + points[1].getX());
			points[3].setX(k * (points[3].getX() - points[0].getX()) + points[0].getX());
			points[2].setY(k * (points[2].getY() - points[1].getY()) + points[1].getY());
			points[3].setY(k * (points[3].getY() - points[0].getY()) + points[0].getY());
		}
	}
}
