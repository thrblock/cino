package com.thrblock.cino.glfont;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.gltexture.IGLTextureContainer;
@Component
public abstract class GLFontInitor {
	protected Logger logger;
	@Autowired
	private IGLTextureContainer textureContainer;
	public GLFontInitor() {
		logger = LoggerFactory.getLogger(getClass());
	}
	@PostConstruct
	public void init() {
		for(String name:getName()) {
			GLFontTexture ft = getFontTexture(name);
			if(ft != null) {
				textureContainer.registerFont(name, ft);
			}
		}
	}
	protected abstract GLFontTexture getFontTexture(String name);
	protected abstract String[] getName();
}
