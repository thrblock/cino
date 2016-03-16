package com.thrblock.cino.glshape;

import java.awt.Color;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLPoint extends GLShape {
    private float pointSize = 1f;
    private float x;
    private float y;
    
    private float alpha = 1.0f;
    private float r = 1.0f;
    private float g = 1.0f;
    private float b = 1.0f;
    public GLPoint(float x,float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }

    @Override
    public void setColor(Color c) {
        this.r = c.getRed() / 255f;
        this.g = c.getGreen() / 255f;
        this.b = c.getBlue() / 255f;
    }
    
    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
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
    @Override
    public void setXOffset(float offset) {
        this.x += offset;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    @Override
    public void setYOffset(float offset) {
        this.y += offset;
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public void drawShape(GL2 gl) {
        gl.glPointSize(pointSize);
        gl.glBegin(GL.GL_POINTS);
        gl.glColor4f(r,g,b,alpha);
        gl.glVertex2f(x, y);
        gl.glEnd();
    }
    
    public float getDistanceSquare(GLPoint point) {
        float xl = x - point.x;
        float yl = y - point.y;
        return xl * xl + yl * yl;
    }
    
    public float getDistanceSquare(float ax,float ay) {
        float xl = x - ax;
        float yl = y - ay;
        return xl * xl + yl*yl;
    }
    
    public float getDistance(GLPoint point) {
        return (float)Math.sqrt(getDistanceSquare(point));
    }
    
    public float getDistance(float ax,float ay) {
        return (float)Math.sqrt(getDistanceSquare(ax,ay));
    }
}
