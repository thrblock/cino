package com.thrblock.cino.drawable;

import com.jogamp.opengl.GL2;

public abstract class GLShape {
	private boolean visible = false;
	private boolean destory = false;
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
	public abstract void drawShape(GL2 gl);
}
