package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLLine extends GLMutiPointShape {
    
    public GLLine(float x1,float y1,float x2,float y2) {
        super(new GLPoint[]{new GLPoint(x1,y1),new GLPoint(x2,y2)});
    }

	@Override
    public void drawShape(GL2 gl) {
		gl.glLineWidth(lineWidth);
        gl.glBegin(GL.GL_LINES);
        for(GLPoint point:points) {
            gl.glColor4f(point.getR(), point.getG(),point.getB(),point.getAlpha());
            gl.glVertex2f(point.getX(),point.getY());
        }
        gl.glEnd();
    }
}
