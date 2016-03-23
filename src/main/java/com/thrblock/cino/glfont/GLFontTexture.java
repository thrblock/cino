package com.thrblock.cino.glfont;

import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class GLFontTexture {
	private Texture[] tarray;
	private char max;
	private char min;
	private Class<?> clazz;
	private char[] chars;
	private String name;
	public GLFontTexture(Class<?> clazz,String name,char[] chars,char max,char min) {
		tarray = new Texture[max - min + 1];
		this.name = name;
		this.clazz = clazz;
		this.chars = chars;
		this.max = max;
		this.min = min;
	}
	public Texture getTexture(char c){
		if(c >= min && c <= max) {
			return tarray[c - min];
		} else {
			return tarray[min];
		}
	}
	public void loadFontAsTexture(GL2 gl) throws IOException {
		for(char c:chars) {
			try(InputStream is = clazz.getResourceAsStream(name + "/" + Integer.valueOf(c))){
				Texture texture = TextureIO.newTexture(is,false,"png");
				texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
				texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
				tarray[c - min] = texture;
			};
		}
	}
}
