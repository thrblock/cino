package com.thrblock.cino.gltexture;

import java.awt.Font;

import org.springframework.stereotype.Component;

import com.jogamp.opengl.util.awt.TextRenderer;

@Component
public class GLTextRenderer {
	private TextRenderer renderer;
	public void init() {
		this.renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
	}
	
	public TextRenderer getRenderer() {
		return renderer;
	}
}
