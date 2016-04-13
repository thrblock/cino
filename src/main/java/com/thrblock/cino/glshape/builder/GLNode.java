package com.thrblock.cino.glshape.builder;

public interface GLNode {
	public void show();
	public void hide();
	public void destory();
	
	public void setX(float x);
	public void setY(float y);
	public float getX();
	public float getY();
	
	public float getCentralX();
	public float getCentralY();
	public void setCentralX(float x);
	public void setCentralY(float x);
	
	public void setAlpha(float alpha);
	public float getAlpha();
	
	public void setXOffset(float offset);
	public void setYOffset(float offset);
	
	public float getTheta();
	public void setTheta(float dstTheta);
	public void setTheta(float dstTheta,float x,float y);
}
