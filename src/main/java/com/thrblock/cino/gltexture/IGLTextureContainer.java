package com.thrblock.cino.gltexture;

import java.io.File;
import java.io.InputStream;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public interface IGLTextureContainer {
	public Texture getTexture(String name);
	public void registerTexture(String name,String imgType,InputStream srcStream);
	public void registerTexture(String name,File imgFile);
	public void parseTexture(GL2 gl);
}
