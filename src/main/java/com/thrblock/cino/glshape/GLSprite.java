package com.thrblock.cino.glshape;

import com.thrblock.cino.gltexture.IGLTextureContainer;

public class GLSprite extends GLImage {
	private String[][] textureName;
	private int currentTextureGroup = 0;
	private int[] rate;
	private int[] crtTextureIndex;
	private int fragCount = 0;
	public GLSprite(IGLTextureContainer textureContainer, float x, float y, float width, float height,
			String[][] textureName,int[] rate) {
		super(textureContainer, x, y, width, height, textureName[0][0]);
		this.textureName = textureName;
		this.rate = rate;
		this.crtTextureIndex = new int[textureName.length];
		this.vertX();//使用了不同于图片的纹理加载器，绑定的定点有所不同
	}
	
	public void setTextureGroupIndex(int index) {
		if(index >= 0 && index < textureName.length) {
			this.currentTextureGroup = index;
		}
	}
	
	private boolean skipFrag() {
		if(fragCount < rate[currentTextureGroup]) {
			fragCount ++;
			return false;
		} else {
			fragCount = 0;
			return true;
		}
	}
	
	public boolean fragment() {
		boolean result = false;
		if(skipFrag()) {
			crtTextureIndex[currentTextureGroup]++;
			if(crtTextureIndex[currentTextureGroup] >= textureName[currentTextureGroup].length) {
				result = true;
				crtTextureIndex[currentTextureGroup] = 0;
			}
			this.setTextureName(textureName[currentTextureGroup][crtTextureIndex[currentTextureGroup]]);
		}
		return result;
	}

}
