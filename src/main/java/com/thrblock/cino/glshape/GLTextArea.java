package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.thrblock.cino.gltexture.GLTextRenderer;

/**
 * @author user
 *
 */
public class GLTextArea extends GLRect {
	GLTextRenderer render;
	private String text = "haha";
	private int x;
	private int y;
	public GLTextArea(GLTextRenderer renderer,float x, float y, float width, float height) {
		super(x, y, width, height);
		this.render = renderer;
	}
	
	public void setText(String t) {
		this.text = t;
	}
	
	@Override
	public void drawShape(GL2 gl) {
		TextRenderer renderer = render.getRenderer();
		renderer.beginRendering(800,600);
		// optionally set the color
		renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
		renderer.draw(text, x, y);
		// ... more draw commands, color changes, etc.
		renderer.endRendering();
	}

	public int getRX() {
		return x;
	}

	public void setRX(int x) {
		this.x = x;
	}

	public int getRY() {
		return y;
	}

	public void setRY(int y) {
		this.y = y;
	}
	
}
