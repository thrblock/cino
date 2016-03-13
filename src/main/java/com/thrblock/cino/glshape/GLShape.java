package com.thrblock.cino.glshape;

import java.awt.Color;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public abstract class GLShape {
	private boolean visible = false;
	private boolean destory = false;
	private int mixAlpha = GL.GL_SRC_ALPHA;
	private int mixBeta = GL.GL_ONE_MINUS_SRC_ALPHA;
	public int getMixAlpha() {
		return mixAlpha;
	}
	public void setMixAlpha(int mixAlpha) {
		this.mixAlpha = mixAlpha;
	}
	public int getMixBeta() {
		return mixBeta;
	}
	public void setMixBeta(int mixBeta) {
		this.mixBeta = mixBeta;
	}
	public void show() {
		this.visible = true;
	}
	public void hide() {
		this.visible = false;
	}
	public void destory() {
		this.destory = true;
	}
	public boolean isVisible() {
		return visible;
	}
	public boolean isDestory() {
		return destory;
	}
	public abstract void setAlpha(float alpha);
	public abstract void setColor(Color c);
	public abstract void setXOffset(float offset);
	public abstract void setYOffset(float offset);
	public abstract void drawShape(GL2 gl);
}
