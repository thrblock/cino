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

import lombok.Getter;

@Component
public class GLTransformManager {
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
    @Value("${cino.frame.screen.width:800}")
    private int initW;
    @Value("${cino.frame.screen.height:600}")
    private int initH;

    private int currentW;
    private int currentH;

    @Getter
    private GLTransform defTransform;

    @Autowired
    private MouseControl mouse;

    @PostConstruct
    void init() {
        defTransform = new GLTransform();
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

}
