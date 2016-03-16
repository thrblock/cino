package com.thrblock.cino.glshape;

import java.awt.Color;
import java.util.ArrayList;

import org.springframework.context.support.AbstractApplicationContext;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.CinoInitor;
import com.thrblock.cino.gltexture.GLFontTexture;
import com.thrblock.cino.gltexture.IGLTextureContainer;


public class GLCharLine extends GLShape {
    private IGLTextureContainer textureContainer;
    
    private char[] str;
    private String fontName;
    private final ArrayList<GLPoint> points;
    private boolean recalcPoint = true;
    public GLCharLine(String fontName,float x,float y,String initStr) {
        this(fontName,x,y,initStr.toCharArray());
    }
    
    public GLCharLine(String fontName,float x,float y,char[] charmap) {
        AbstractApplicationContext context = CinoInitor.getCinoContext();
        textureContainer = context.getBean(IGLTextureContainer.class);
        
        this.fontName = fontName;
        this.str = charmap;
        points = new ArrayList<>(charmap.length * 4 > 16?charmap.length * 4:16);
        points.add(new GLPoint(x,y));
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
    public String getFontName() {
    	return fontName;
    }
    public void setFontString(String str) {
        this.str = str.toCharArray();
        resize();
    }
    public void setFontString(char[] str) {
    	this.str = str;
    	resize();
    }
    public void resize() {
    	recalcPoint = true;
    }
    @Override
    public void setAlpha(float alpha) {
        for(GLPoint point:points) {
            point.setAlpha(alpha);
        }
    }

    @Override
    public void setColor(Color c) {
    	for(GLPoint point:points) {
            point.setColor(c);
        }
    }

    @Override
    public void setXOffset(float offset) {
        for (GLPoint point : points) {
            point.setXOffset(offset);
        }
    }

    @Override
    public void setYOffset(float offset) {
        for (GLPoint point : points) {
            point.setYOffset(offset);
        }
    }

    @Override
    public void drawShape(GL2 gl) {
        GLFontTexture tx = textureContainer.getGLFontTexture(fontName);
        if(recalcPoint) {
            recalc(tx);
            recalcPoint = false;
        }
        for(int i = 0;i < str.length;i++) {
            Texture t = tx.getTexture(str[i]);
            if(t != null) {
                t.bind(gl);
                gl.glBegin(GL2.GL_QUADS);
                GLPoint p0 = points.get(i * 4 + 0);
                gl.glColor4f(p0.getR(), p0.getG(),p0.getB(),p0.getAlpha());
                gl.glTexCoord2f(0.0f,1.0f);
                gl.glVertex2f(p0.getX(),p0.getY());
                
                GLPoint p1 = points.get(i * 4 + 1);
                gl.glColor4f(p1.getR(), p1.getG(),p1.getB(),p1.getAlpha());
                gl.glTexCoord2f(1.0f,1.0f);
                gl.glVertex2f(p1.getX(),p1.getY());
                
                GLPoint p2 = points.get(i * 4 + 2);
                gl.glColor4f(p2.getR(),p2.getG(),p2.getB(),p2.getAlpha());
                gl.glTexCoord2f(1.0f,0.0f);
                gl.glVertex2f(p2.getX(),p2.getY());
                
                GLPoint p3 = points.get(i * 4 + 3);
                gl.glColor4f(p3.getR(), p3.getG(),p3.getB(),p3.getAlpha());
                gl.glTexCoord2f(0.0f,0.0f);
                gl.glVertex2f(p3.getX(),p3.getY());
                
                gl.glEnd();
                gl.glBindTexture(GL.GL_TEXTURE_2D,0);
            }
        }
    }

    private void recalc(GLFontTexture tx) {
        GLPoint pre = points.get(0);
        if(points.size() < str.length * 4) {
            for(int i = points.size();i < str.length * 4;i++) {
                points.add(new GLPoint(pre.getX(),pre.getY()));
            }
        }
        for(int i = 0;i < str.length;i++) {
            Texture t = tx.getTexture(str[i]);
            if(t != null) {
                GLPoint p0 = points.get(i * 4 + 0);
                p0.setX(pre.getX());
                p0.setY(pre.getY());
                
                GLPoint p1 = points.get(i * 4 + 1);
                p1.setX(pre.getX() + t.getWidth());
                p1.setY(pre.getY());
                
                GLPoint p2 = points.get(i * 4 + 2);
                p2.setX(pre.getX() + t.getWidth());
                p2.setY(pre.getY() + t.getHeight());
                
                GLPoint p3 = points.get(i * 4 + 3);
                p3.setX(pre.getX());
                p3.setY(pre.getY() + t.getHeight());
            }
            pre = points.get(i * 4 + 1);
        }
    }

}
