package com.thrblock.cino.glshape;

import java.awt.Color;
import java.util.ArrayList;

import org.springframework.context.support.AbstractApplicationContext;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.CinoInitor;
import com.thrblock.cino.glfont.GLFontTexture;
import com.thrblock.cino.gltexture.IGLTextureContainer;

public class GLCharArea extends GLShape {
    private IGLTextureContainer textureContainer;
    private float widthLimit = -1f;
    private char[] str;
    private String fontName;
    private final ArrayList<GLPoint> points;
    private boolean recalcPoint = true;
    private float r = 1.0f;
    private float g = 1.0f;
    private float b = 1.0f;
    private float alpha = 1.0f;

    public GLCharArea(String fontName, float x, float y, String initStr) {
        this(fontName, x, y, initStr.toCharArray());
    }

    public GLCharArea(String fontName, float x, float y, char[] charmap) {
        AbstractApplicationContext context = CinoInitor.getCinoContext();
        textureContainer = context.getBean(IGLTextureContainer.class);

        this.fontName = fontName;
        this.str = charmap;
        points = new ArrayList<>(charmap.length * 4 > 16 ? charmap.length * 4 : 16);
        points.add(new GLPoint(x, y));
    }

    public float getX() {
        return points.get(0).getX();
    }

    public float getY() {
        return points.get(0).getY();
    }

    public void setX(float x) {
        float offset = x - getX();
        setXOffset(offset);
    }

    public void setY(float y) {
        float offset = y - getY();
        setYOffset(offset);
    }

    public float getWidthLimit() {
        return widthLimit;
    }

    public void setWidthLimit(float widthLimit) {
        this.widthLimit = widthLimit;
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

    public String getFontString() {
        return new String(str);
    }

    public void resize() {
        recalcPoint = true;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setColor(Color c) {
        this.r = c.getRed() / 255f;
        this.g = c.getGreen() / 255f;
        this.b = c.getBlue() / 255f;
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
        char[] local = this.str;
        GLFontTexture tx = textureContainer.getGLFontTexture(fontName);
        if (tx == null) {
            return;
        }
        if (recalcPoint) {
            if (widthLimit > 0) {
                recalcWithLimit(tx);
            } else {
                recalc(tx);
            }
            recalcPoint = false;
        }
        gl.glColor4f(r, g, b, alpha);
        for (int i = 0; i < local.length; i++) {
            if (local[i] == '\n') {
                continue;
            }
            Texture t = tx.getTexture(local[i]);
            t.bind(gl);
            gl.glBegin(GL2.GL_QUADS);
            GLPoint p0 = points.get(i * 4 + 0);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex2f(p0.getX(), p0.getY());

            GLPoint p1 = points.get(i * 4 + 1);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex2f(p1.getX(), p1.getY());

            GLPoint p2 = points.get(i * 4 + 2);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex2f(p2.getX(), p2.getY());

            GLPoint p3 = points.get(i * 4 + 3);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex2f(p3.getX(), p3.getY());

            gl.glEnd();
            gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
        }
    }

    private void recalcWithLimit(GLFontTexture tx) {
        char[] local = this.str;
        GLPoint pre = points.get(0);
        if (points.size() < local.length * 4) {
            for (int i = points.size(); i < local.length * 4; i++) {
                GLPoint npt = new GLPoint(pre.getX(), pre.getY());
                points.add(npt);
            }
        }
        GLPoint linePoint = points.get(3);
        int crtWidth = 0;
        for (int i = 0; i < local.length; i++) {
            Texture t = tx.getTexture(local[i]);
            if (t != null) {
                if (local[i] == '\n') {
                    crtWidth = 0;
                    pre = linePoint;
                    linePoint = points.get(i * 4 + 3);
                } else if (crtWidth + t.getWidth() > widthLimit) {
                    crtWidth = t.getWidth();
                    pre = linePoint;
                    linePoint = points.get(i * 4 + 3);
                } else {
                    crtWidth += t.getWidth();
                }
                positionPoints(pre, i, t, local);
            }
            pre = points.get(i * 4 + 1);
        }
    }

    private void recalc(GLFontTexture tx) {
        char[] local = this.str;
        GLPoint pre = points.get(0);
        if (points.size() < local.length * 4) {
            for (int i = points.size(); i < local.length * 4; i++) {
                GLPoint npt = new GLPoint(pre.getX(), pre.getY());
                points.add(npt);
            }
        }
        for (int i = 0; i < local.length; i++) {
            Texture t = tx.getTexture(local[i]);
            if (t != null) {
                positionPoints(pre, i, t, local);
            }
            pre = points.get(i * 4 + 1);
        }
    }

    private void positionPoints(GLPoint pre, int i, Texture t, char[] local) {
        int w = local[i] == '\n' ? 0 : t.getWidth();
        GLPoint p0 = points.get(i * 4 + 0);
        p0.setX(pre.getX());
        p0.setY(pre.getY());

        GLPoint p1 = points.get(i * 4 + 1);
        p1.setX(pre.getX() + w);
        p1.setY(pre.getY());

        GLPoint p2 = points.get(i * 4 + 2);
        p2.setX(pre.getX() + w);
        p2.setY(pre.getY() + t.getHeight());

        GLPoint p3 = points.get(i * 4 + 3);
        p3.setX(pre.getX());
        p3.setY(pre.getY() + t.getHeight());
    }

}
