package com.thrblock.cino.gltexture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thrblock.cino.CinoInitor;
public abstract class GLFontInitor {
	protected Logger logger;
	private IGLTextureContainer textureContainer;
	public GLFontInitor() {
		this.textureContainer = CinoInitor.getCinoContext().getBean(IGLTextureContainer.class);
		logger = LogManager.getLogger(getClass());
		GLFontTexture ft = getFontTexture();
		if(ft != null) {
			textureContainer.registerFont(getName(), ft);
		}
	}
	protected abstract GLFontTexture getFontTexture();
	protected abstract String getName();
}
