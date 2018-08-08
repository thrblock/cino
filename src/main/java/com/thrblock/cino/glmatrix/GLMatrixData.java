package com.thrblock.cino.glmatrix;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLMatrixData {
    
    private final float[] modelViewMat = new float[16];
    private final float[] projectViewMat = new float[16];
    private final int[] viewport = new int[4];
    
    public void storeMatrix(GL gl) {
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelViewMat, 0);
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projectViewMat, 0);
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
    }

    public float[] getModelViewMat() {
        return modelViewMat;
    }

    public float[] getProjectViewMat() {
        return projectViewMat;
    }

    public int[] getViewport() {
        return viewport;
    }
}
