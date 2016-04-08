package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL2;

public abstract class GLPolygonShape extends GLMutiPointShape{

    private boolean fill = false;
    
    public GLPolygonShape(GLPoint[] points) {
        super(points);
    }
    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public boolean isSquareableCollide(GLPolygonShape another) {
        for(GLPoint point:points) {
            if(another.isPointInside(point.getX(),point.getY())) {
                return true;
            }
        }
        for(GLPoint point:another.points) {
            if(isPointInside(point.getX(),point.getY())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isPointInside(float px,float py) {
        int nCount = points.length;
        int nCross = 0;
        for (int i = 0; i < nCount; i++) { 
            float px1 = points[i].getX();
            float py1 = points[i].getY();
            
            float px2 = points[(i + 1) % nCount].getX();
            float py2 = points[(i + 1) % nCount].getY();

            boolean cont = false;
            if (Float.compare(py1, py2) == 0) {
            	cont = true;
            } else if ( py < (py1> py2?py2:py1) ) {
            	cont = true;
            } else if ( py >= (py1> py2?py1:py2) ) {
            	cont = true;
            }
            if(cont) {
            	continue;
            }
            float x = (py - py1) * (px2 - px1) / (py2 - py1) + px1;
            if ( x > px ) {
                nCross++; 
            }
        }
        return nCross % 2 == 1; 
    }
    @Override
    public void drawShape(GL2 gl) {
        gl.glLineWidth(lineWidth);
        if(fill) {
            gl.glBegin(GL2.GL_POLYGON);
        } else {
            gl.glBegin(GL2.GL_LINE_LOOP);
        }
        for(GLPoint point:points) {
            gl.glColor4f(point.getR(), point.getG(),point.getB(),point.getAlpha());
            gl.glVertex2f(point.getX(),point.getY());
        }
        gl.glLineWidth(1.0f);
        gl.glEnd();
    }
    
}
