package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.glshape.builder.GLNode;

public abstract class GLShape implements GLNode{
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
	@Override
	public void show() {
		this.visible = true;
	}
	@Override
	public void hide() {
		this.visible = false;
	}
	@Override
	public void destory() {
		this.destory = true;
	}
	public boolean isVisible() {
		return visible;
	}
	public boolean isDestory() {
		return destory;
	}
    protected float revolveX(float x, float y, float cx, float cy, float theta) {
        float cdx = x - cx;
        float cdy = y - cy;
        return (float) (cdx * Math.cos(theta) - cdy * Math.sin(theta)) + cx;
    }

    protected float revolveY(float x, float y, float cx, float cy, float theta) {
        float cdx = x - cx;
        float cdy = y - cy;
        return (float) (cdx * Math.sin(theta) + cdy * Math.cos(theta)) + cy;
    }
	public abstract void drawShape(GL2 gl);
}
