package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.gltexture.IGLTextureContainer;

public class GLImage extends GLRect {
    private static final int MODE_NORMAL = 0b00;
    private static final int MODE_X_VERT = 0b01;
    private static final int MODE_Y_VERT = 0b10;
    private int mode = MODE_NORMAL;
    private String textureName;
    private boolean resize = false;
    private IGLTextureContainer textureContainer;
    private final float[] alph = {1f,0,0,1f};
    private final float[] beta = {0,1f,1f,0};
    private final float[] gama = {1f,1f,0,0};
    private final float[] zeta = {0,0,1f,1f};
    public GLImage(IGLTextureContainer textureContainer,float x, float y, float width, float height,String textureName) {
        super(x, y, width, height);
        this.textureName = textureName;
        this.textureContainer = textureContainer;
    }
    
    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        setTextureName(textureName,false);
    }
    
    public void setTextureName(String textureName,boolean resize) {
        this.textureName = textureName;
        this.resize = resize;
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
            if(resize) {
                setWidth(texture.getWidth());
                setHeight(texture.getHeight());
                resize = false;
            }
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
        for(int i = 0;i < 4;i++) {
            gl.glColor4f(points[i].getR(), points[i].getG(),points[i].getB(),points[i].getAlpha());
            gl.glTexCoord2f(beta[i],gama[i]);
            gl.glVertex2f(points[i].getX(),points[i].getY());
        }
    }
    private void xyvertTexCoord(GL2 gl) {
        for(int i = 0;i < 4;i++) {
            gl.glColor4f(points[i].getR(), points[i].getG(),points[i].getB(),points[i].getAlpha());
            gl.glTexCoord2f(alph[i],zeta[i]);
            gl.glVertex2f(points[i].getX(),points[i].getY());
        }
    }
    private void xvertTexCoord(GL2 gl) {
        for(int i = 0;i < 4;i++) {
            gl.glColor4f(points[i].getR(), points[i].getG(),points[i].getB(),points[i].getAlpha());
            gl.glTexCoord2f(beta[i],zeta[i]);
            gl.glVertex2f(points[i].getX(),points[i].getY());
        }
    }
    private void yvertTexCoord(GL2 gl) {
        for(int i = 0;i < 4;i++) {
            gl.glColor4f(points[i].getR(), points[i].getG(),points[i].getB(),points[i].getAlpha());
            gl.glTexCoord2f(alph[i],gama[i]);
            gl.glVertex2f(points[i].getX(),points[i].getY());
        }
    }
}
