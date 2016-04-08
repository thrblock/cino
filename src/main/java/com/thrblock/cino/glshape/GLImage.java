package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.gltexture.IGLTextureContainer;

public class GLImage extends GLRect {
	private static final int MODE_NORMAL = 0b00;
	private static final int MODE_X_VERT    = 0b01;
	private static final int MODE_Y_VERT    = 0b10;
	private int mode = MODE_NORMAL;
	private String textureName;
	private IGLTextureContainer textureContainer;
	public GLImage(IGLTextureContainer textureContainer,float x, float y, float width, float height,String textureName) {
		super(x, y, width, height);
		this.textureName = textureName;
		this.textureContainer = textureContainer;
	}
	
	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	public void vertX() {
		mode = mode | MODE_X_VERT;
	}
	
	public void vertY() {
		mode = mode | MODE_Y_VERT;
	}
	
	public void unVertX() {
		mode = mode &~MODE_X_VERT;
	}
	
	public void unVertY() {
		mode = mode &~MODE_Y_VERT;
	}
	@Override
	public void drawShape(GL2 gl) {
		Texture texture = textureContainer.getTexture(textureName);
		if(texture != null) {
			texture.bind(gl);
		}
		
	    gl.glBegin(GL2.GL_QUADS);
	    if(mode == MODE_NORMAL) {
	    	normalTexCoord(gl);
	    } else if(mode == MODE_X_VERT) {
	    	xvertTexCoord(gl);
	    } else if(mode == MODE_Y_VERT) {
	    	yvertTexCoord(gl);
	    } else if(mode == (MODE_X_VERT | MODE_Y_VERT)) {
	    	xyvertTexCoord(gl);
	    }
	    
	    gl.glEnd();
	    gl.glBindTexture(GL.GL_TEXTURE_2D,0);
	}
	
	private void normalTexCoord(GL2 gl) {
	    gl.glColor4f(points[0].getR(), points[0].getG(),points[0].getB(),points[0].getAlpha());
	    gl.glTexCoord2f(0.0f,1.0f);
	    gl.glVertex2f(points[0].getX(),points[0].getY());
	    
	    gl.glColor4f(points[1].getR(), points[1].getG(),points[1].getB(),points[1].getAlpha());
	    gl.glTexCoord2f(1.0f,1.0f);
	    gl.glVertex2f(points[1].getX(),points[1].getY());
	    
	    gl.glColor4f(points[2].getR(), points[2].getG(),points[2].getB(),points[2].getAlpha());
	    gl.glTexCoord2f(1.0f,0.0f);
	    gl.glVertex2f(points[2].getX(),points[2].getY());
	    
	    gl.glColor4f(points[3].getR(), points[3].getG(),points[3].getB(),points[3].getAlpha());
	    gl.glTexCoord2f(0.0f,0.0f);
	    gl.glVertex2f(points[3].getX(),points[3].getY());
	}
	private void xyvertTexCoord(GL2 gl) {
		gl.glColor4f(points[0].getR(), points[0].getG(),points[0].getB(),points[0].getAlpha());
		gl.glTexCoord2f(1.0f,0.0f);
	    gl.glVertex2f(points[0].getX(),points[0].getY());
	    
	    gl.glColor4f(points[1].getR(), points[1].getG(),points[1].getB(),points[1].getAlpha());
	    gl.glTexCoord2f(0.0f,0.0f);
	    gl.glVertex2f(points[1].getX(),points[1].getY());
	    
	    gl.glColor4f(points[2].getR(), points[2].getG(),points[2].getB(),points[2].getAlpha());
	    gl.glTexCoord2f(0.0f,1.0f);
	    gl.glVertex2f(points[2].getX(),points[2].getY());
	    
	    gl.glColor4f(points[3].getR(), points[3].getG(),points[3].getB(),points[3].getAlpha());
	    gl.glTexCoord2f(1.0f,1.0f);
	    gl.glVertex2f(points[3].getX(),points[3].getY());
	}
	private void xvertTexCoord(GL2 gl) {
		gl.glColor4f(points[0].getR(), points[0].getG(),points[0].getB(),points[0].getAlpha());
	    
		gl.glTexCoord2f(0.0f,0.0f);
	    gl.glVertex2f(points[0].getX(),points[0].getY());
	    
	    gl.glColor4f(points[1].getR(), points[1].getG(),points[1].getB(),points[1].getAlpha());
	    gl.glTexCoord2f(1.0f,0.0f);
	    gl.glVertex2f(points[1].getX(),points[1].getY());
	    
	    gl.glColor4f(points[2].getR(), points[2].getG(),points[2].getB(),points[2].getAlpha());
	    gl.glTexCoord2f(1.0f,1.0f);
	    gl.glVertex2f(points[2].getX(),points[2].getY());
	    
	    gl.glColor4f(points[3].getR(), points[3].getG(),points[3].getB(),points[3].getAlpha());
	    gl.glTexCoord2f(0.0f,1.0f);
	    gl.glVertex2f(points[3].getX(),points[3].getY());
	}
	private void yvertTexCoord(GL2 gl) {
		gl.glColor4f(points[0].getR(), points[0].getG(),points[0].getB(),points[0].getAlpha());
		gl.glTexCoord2f(1.0f,1.0f);
	    gl.glVertex2f(points[0].getX(),points[0].getY());
	    
	    gl.glColor4f(points[1].getR(), points[1].getG(),points[1].getB(),points[1].getAlpha());
	    gl.glTexCoord2f(0.0f,1.0f);
	    gl.glVertex2f(points[1].getX(),points[1].getY());
	    
	    gl.glColor4f(points[2].getR(), points[2].getG(),points[2].getB(),points[2].getAlpha());
	    gl.glTexCoord2f(0.0f,0.0f);
	    gl.glVertex2f(points[2].getX(),points[2].getY());
	    
	    gl.glColor4f(points[3].getR(), points[3].getG(),points[3].getB(),points[3].getAlpha());
	    gl.glTexCoord2f(1.0f,0.0f);
	    gl.glVertex2f(points[3].getX(),points[3].getY());
	}
}
