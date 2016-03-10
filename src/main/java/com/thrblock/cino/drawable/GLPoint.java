package com.thrblock.cino.drawable;

import java.awt.Color;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLPoint extends GLShape {
    private float x;
    private float y;
    
    private int alpha;
    private int r = 255;
    private int g = 255;
    private int b = 255;
    public GLPoint(float x,float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setColor(Color c) {
        this.r = c.getRed();
        this.g = c.getGreen();
        this.b = c.getBlue();
    }
    
    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public Color getColor() {
        return new Color(r,g,b);
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }
    
    public void setXOffset(float offset) {
        this.x += offset;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public void setYOffset(float offset) {
        this.y += offset;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public void drawShape(GL2 gl) {
        gl.glColor4i(r,g,b,alpha);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2f(x, y);
        gl.glEnd();
    }
    
    public float getDistanceSquare(GLPoint point) {
        return point.x * point.x + point.y * point.y;
    }
    
    public float getDistance(GLPoint point) {
    	return (float)Math.sqrt(getDistanceSquare(point));
    }
}
