package com.thrblock.cino.gltransform;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.thrblock.cino.io.MouseControl;

@Component
public class GLTransformManager implements IGLTransForm {
    private static final Logger LOG = LoggerFactory.getLogger(GLTransformManager.class);

    /**
     * 等比例缩放
     * 
     * @see #setFlexMode
     */
    public static final int SCALE = 0;
    /**
     * 仅仅是画布变大，图形大小不变
     * 
     * @see #setFlexMode
     */
    public static final int FIX = 1;
    /**
     * 当前缩放模式
     */
    @Value("${cino.frame.flexmode:" + SCALE + "}")
    private int flexMode;

    @Value("${cino.gl.projectfunction:glOrtho}")
    private String projectfunction;

    @Value("${cino.gl.project.near:0.0}")
    private double projectnear;

    @Value("${cino.gl.project.far:1.0}")
    private double projectfar;

    @Value("${cino.frame.screen.width:800}")
    private int initW;
    @Value("${cino.frame.screen.height:600}")
    private int initH;
    @Value("${cino.layer.maxlayer:128}")
    private int maxLayer;

    private int currentW;
    private int currentH;

    private GLTransform defTransform;
    private GLTransform[] transformArr;

    @Autowired
    private MouseControl mouse;

    @PostConstruct
    void init() {
        defTransform = new GLTransform(projectfunction, projectnear, projectfar);
        transformArr = new GLTransform[maxLayer];
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        this.currentW = w;
        this.currentH = h;
        initDefault(drawable.getGL());
        LOG.info("GL reshape:{},{},{},{}", x, y, w, h);
    }

    public void initDefault(GL gl) {
        defTransform.transform(gl, gethW(), gethH(), mouse.getOriginalX(), mouse.getOriginalY());
    }

    public void initBeforeLayer(GL gl, int layerIndex) {
        getGLTransform(layerIndex).transform(gl, gethW(), gethH(), mouse.getOriginalX(), mouse.getOriginalY());
    }

    private int gethW() {
        if (flexMode == FIX) {
            return currentW <= 0 ? 1 : currentW / 2;
        } else {
            return initW <= 0 ? 1 : initW / 2;
        }
    }

    private int gethH() {
        if (flexMode == FIX) {
            return currentH <= 0 ? 1 : currentH / 2;
        } else {
            return initH <= 0 ? 1 : initH / 2;
        }
    }

    @Override
    public GLTransform getGLTransform(int layerIndex) {
        if (layerIndex > transformArr.length || layerIndex < 0) {
            return getMaxTransform(transformArr.length - 1);
        } else {
            return getMaxTransform(layerIndex);
        }
    }

    private GLTransform getMaxTransform(int layerIndex) {
        if (transformArr[layerIndex] != null) {
            return transformArr[layerIndex];
        } else {
            for (int i = layerIndex; i >= 0; i--) {
                if (transformArr[i] != null) {
                    return transformArr[i];
                }
            }
        }
        return defTransform;
    }

    @Override
    public void addBeforeLayer(GLTransform trans, int layerIndex) {
        if (layerIndex < transformArr.length) {
            transformArr[layerIndex] = trans;
        }
    }

    @Override
    public GLTransform createTransform(boolean perspective, double near, double far) {
        return new GLTransform(perspective, near, far);
    }

    @Override
    public GLTransform createTransform(boolean perspective) {
        return new GLTransform(perspective, projectnear, projectfar);
    }

    @Override
    public GLTransform createTransform() {
        return new GLTransform("glFrustum".equals(projectfunction), projectnear, projectfar);
    }

    @Override
    public GLTransform removeTransform(int layerIndex) {
        if (layerIndex < transformArr.length) {
            GLTransform result = transformArr[layerIndex];
            transformArr[layerIndex] = null;
            return result;
        } else {
            return null;
        }
    }
}
