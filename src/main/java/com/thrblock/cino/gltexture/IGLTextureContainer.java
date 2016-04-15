package com.thrblock.cino.gltexture;

import java.io.File;
import java.io.InputStream;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.glfont.GLFontTexture;

public interface IGLTextureContainer {
	public Texture getTexture(String name);
	public void registerTexture(String name,InputStream srcStream);
	public void registerTexture(String name,String imgType,InputStream srcStream);
	public void registerTexture(String name,File imgFile);
	public GifMetaData registerGifAsTexture(String name,File gifFile);
	public GifMetaData registerGifAsTexture(String name,InputStream srcStream);
	public void parseTexture(GL2 gl);
	
	public void registerFont(String name,GLFontTexture fontTexture);
	public GLFontTexture getGLFontTexture(String name);
	
	public static class GifMetaData {
		private int[] widths;
		private int[] heights;
		private String[] textureGroup;
		private int rate;
		public String[] getTextureGroup() {
			return textureGroup;
		}
		public void setTextureGroup(String[] textureGroup) {
			this.textureGroup = textureGroup;
		}
		public int getRate() {
			return rate;
		}
		public void setRate(int rate) {
			this.rate = rate;
		}
		public int getFrameNum() {
			return textureGroup.length;
		}
		public int[] getWidths() {
			return widths;
		}
		public void setWidths(int[] widths) {
			this.widths = widths;
		}
		public int[] getHeights() {
			return heights;
		}
		public void setHeights(int[] heights) {
			this.heights = heights;
		}
	}
}
