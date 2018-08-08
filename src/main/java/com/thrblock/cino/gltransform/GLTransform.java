package com.thrblock.cino.gltransform;

import java.nio.FloatBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.thrblock.cino.glmatrix.GLMatrixData;

public final class GLTransform {

    private String projectfunction;

    private double projectnear;

    private double projectfar;

    private float viewOffsetX;
    private float viewOffsetY;

    private GLMatrixData glmat = new GLMatrixData();
    private GLU glu = new GLU();
    private float[] unprojectedMouseData = new float[3];

    FloatBuffer buffer = FloatBuffer.allocate(1);
    
    GLTransform(boolean perspective, double projectnear, double projectfar) {
        this(perspective ? "glFrustum" : "glOrtho", projectnear, projectfar);
    }

    GLTransform(String projectfunction, double projectnear, double projectfar) {
        this.projectfunction = projectfunction;
        this.projectnear = projectnear;
        this.projectfar = projectfar;
    }

    public void transform(GL gl, int hW, int hH, int orgX, int orgY) {
        GL2 gl2 = gl.getGL2();
        project(gl2, hW, hH);
        modelAndView(gl2);
        calcMouse(gl2, orgX, orgY);
    }

    private void calcMouse(GL2 gl2, int orgX, int orgY) {
        glmat.storeMatrix(gl2);
        int x = orgX;
        int y = glmat.getViewport()[3] - orgY;
        glu.gluUnProject(x, y, 0f, glmat.getModelViewMat(), 0, glmat.getProjectViewMat(), 0, glmat.getViewport(), 0,
                unprojectedMouseData, 0);
    }

    private void project(GL2 gl, int hW, int hH) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        if ("glOrtho".equals(projectfunction)) {
            gl.glOrtho(-hW, hW, -hH, hH, projectnear, projectfar);
        } else {
            gl.glFrustum(-hW, hW, -hH, hH, projectnear, projectfar);
        }
    }

    private void modelAndView(GL2 gl2) {
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
        gl2.glTranslatef(viewOffsetX, viewOffsetY, -(float) projectnear);
    }

    public float getViewOffsetX() {
        return viewOffsetX;
    }

    public void setViewOffsetX(float viewOffsetX) {
        this.viewOffsetX = viewOffsetX;
    }

    public float getViewOffsetY() {
        return viewOffsetY;
    }

    public void setViewOffsetY(float viewOffsetY) {
        this.viewOffsetY = viewOffsetY;
    }

    public String getProjectfunction() {
        return projectfunction;
    }

    public void setProjectfunction(String projectfunction) {
        this.projectfunction = projectfunction;
    }

    public double getProjectnear() {
        return projectnear;
    }

    public void setProjectnear(double projectnear) {
        this.projectnear = projectnear;
    }

    public double getProjectfar() {
        return projectfar;
    }

    public void setProjectfar(double projectfar) {
        this.projectfar = projectfar;
    }

    public float[] getUnprojectedMouseData() {
        return unprojectedMouseData;
    }

    public void setUnprojectedMouseData(float[] unprojectedMouseData) {
        this.unprojectedMouseData = unprojectedMouseData;
    }
}
