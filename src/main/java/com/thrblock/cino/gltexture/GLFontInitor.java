package com.thrblock.cino.gltexture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.CinoInitor;

public abstract class GLFontInitor {
	private IGLTextureContainer textureContainer;
	protected Logger logger;
	public GLFontInitor() {
		logger = LogManager.getLogger(getClass());
		AbstractApplicationContext context = CinoInitor.getCinoContext();
		textureContainer = context.getBean(IGLTextureContainer.class);
		GLFontTexture ft = getFontTexture();
		if(ft != null) {
			textureContainer.registerFont(getName(), ft);
		}
	}
	protected abstract GLFontTexture getFontTexture();
	protected abstract String getName();
}
