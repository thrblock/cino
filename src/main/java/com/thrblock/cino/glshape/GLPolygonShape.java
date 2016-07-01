package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL2;

/**
 * 封闭式图形对象(抽象) 定义了一个封闭图形对象
 * @author lizepu
 *
 */
public abstract class GLPolygonShape extends GLMutiPointShape{

    private boolean fill = false;
    
    /**
     * 以顶点数组构造一个封闭图形
     * @param points 顶点数组
     */
    public GLPolygonShape(GLPoint[] points) {
        super(points);
    }
    /**
     * 该图形是否填充
     * @return 是否填充的布尔值
     */
    public boolean isFill() {
        return fill;
    }

    /**
     * 设置是否填充的布尔值
     * @param fill 是否填充的布尔值
     */
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    /**
     * 判断 此多边形与另一多边形是否发生碰撞
     * @param another 另一个多边形
     * @return 是否发生碰撞的布尔值
     */
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
    
    /**
     * 判断给定点是否在本图形内部
     * @param px 给定点的x坐标
     * @param py 给定点的y坐标
     * @return 点是否在内部的布尔值
     */
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
