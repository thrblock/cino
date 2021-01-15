package com.thrblock.cino.gltransform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.gllifecycle.CycleArray;
import com.thrblock.cino.io.MouseControl;

@Component
public class GLTransformManager {
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

    @Autowired
    private MouseControl mouse;

    private CycleArray<GLTransform> transCycle = new CycleArray<>(GLTransform[]::new);

    public GLTransform generateGLTransform() {
        GLTransform transform = new GLTransform();
        transform.setMouse(mouse);
        transCycle.safeAdd(transform);
        setHalfData(transform);
        return transform;
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

    /**
     * 通知 屏幕尺寸变更
     * 
     * @param w
     * @param h
     */
    @ScreenSizeChangeListener
    public void noticeScreenChange(int w, int h) {
        this.currentW = w;
        this.currentH = h;
        // update cycle;
        transCycle.safeUpdate(this::setHalfData);
    }

    private void setHalfData(GLTransform t) {
        t.setHH(gethH());
        t.setHW(gethW());
    }
}
