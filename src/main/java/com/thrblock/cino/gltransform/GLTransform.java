package com.thrblock.cino.gltransform;

import java.nio.FloatBuffer;
import java.util.function.BiConsumer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.thrblock.cino.glmatrix.GLMatrixData;

public final class GLTransform {

    private float translateX;
    private float translateY;

    private float scaleX = 1.0f;
    private float scaleY = 1.0f;

    private float angle = 0;

    private GLMatrixData glmat = new GLMatrixData();
    private GLU glu = new GLU();
    private float[] unprojectedMouseData = new float[3];

    FloatBuffer buffer = FloatBuffer.allocate(1);

    @FunctionalInterface
    interface OrderedTransform extends BiConsumer<GLTransform, GL2> {
    }

    private OrderedTransform orderedTransformfun;
    /**
     * 平移-缩放-旋转 变换（默认)
     */
    public static final OrderedTransform TSR = (tr, gl2) -> {
        gl2.glTranslatef(tr.translateX, tr.translateY, 0f);

        gl2.glScalef(tr.scaleX, tr.scaleY, 1.0f);
        gl2.glRotatef(tr.angle, 0, 0, 1.0f);
    };
    /**
     * 缩放-旋转-平移 变换
     */
    public static final OrderedTransform SRT = (tr, gl2) -> {
        gl2.glScalef(tr.scaleX, tr.scaleY, 1.0f);
        gl2.glRotatef(tr.angle, 0, 0, 1.0f);

        gl2.glTranslatef(tr.translateX, tr.translateY, 0f);
    };

    /**
     * 缩放-平移-旋转 变换
     */
    public static final OrderedTransform STR = (tr, gl2) -> {
        gl2.glScalef(tr.scaleX, tr.scaleY, 1.0f);
        gl2.glTranslatef(tr.translateX, tr.translateY, 0f);
        gl2.glRotatef(tr.angle, 0, 0, 1.0f);
    };

    /**
     * 旋转-平移-缩放 变换
     */
    public static final OrderedTransform RTS = (tr, gl2) -> {
        gl2.glRotatef(tr.angle, 0, 0, 1.0f);
        gl2.glTranslatef(tr.translateX, tr.translateY, 0f);
        gl2.glScalef(tr.scaleX, tr.scaleY, 1.0f);
    };

    public GLTransform() {
        this.orderedTransformfun = TSR;
    }

    public GLTransform(OrderedTransform tr) {
        this.orderedTransformfun = tr;
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
        gl.glOrtho(-hW, hW, -hH, hH, 0.0f, 1.0f);
    }

    private void modelAndView(GL2 gl2) {
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();

        orderedTransformfun.accept(this, gl2);
    }

    public float getTranslateX() {
        return translateX;
    }

    public void setTranslateX(float translateX) {
        this.translateX = translateX;
    }

    public float getTranslateY() {
        return translateY;
    }

    public void setTranslateY(float translateY) {
        this.translateY = translateY;
    }

    public void setRoteAngle(float angle) {
        this.angle = angle;
    }

    public float getRoteAngle() {
        return angle;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setScale(float factor) {
        this.scaleX = factor;
        this.scaleY = factor;
    }

    public float[] getUnprojectedMouseData() {
        return unprojectedMouseData;
    }

    public void setUnprojectedMouseData(float[] unprojectedMouseData) {
        this.unprojectedMouseData = unprojectedMouseData;
    }
}
