package com.thrblock.cino.lnode;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.fbo.GLFrameBufferObjectManager;
import com.thrblock.cino.gllifecycle.CycleArray;
import com.thrblock.cino.gltransform.GLTransform;
import com.thrblock.cino.io.MouseControl;

@Component
public class LNodeManager {

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

    @Value("${cino.frame.screen.width:800}")
    private int initW;
    @Value("${cino.frame.screen.height:600}")
    private int initH;
    @Value("${cino.frame.flexmode:0}")
    private int flexmode;

    private int currentW;
    private int currentH;

    private CycleArray<LNode> roots = new CycleArray<>(LNode[]::new);

    @Autowired
    private MouseControl mouse;

    @Autowired
    private GLFrameBufferObjectManager fboManager;

    @Autowired
    private ShapeBeanFactory shapeFactory;

    private GLTransform defTransform;

    @PostConstruct
    void init() {
        this.defTransform = new GLTransform();
    }
    
    public LNode createRootNode() {
        LNode node = new LNode(fboManager, shapeFactory);
        node.setTransform(defTransform);
        roots.safeAdd(node);
        Arrays.sort(roots.safeHold());
        return node;
    }

    public LNode createSubNode(LNode parent) {
        return new LNode(parent);
    }

    public void destroyRootNode(LNode n) {
        roots.safeRemove(n);
        Arrays.sort(roots.safeHold());
    }

    public void drawAllNode(GL2 gl2) {
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        LNode[] array = roots.safeHold();
        GLTransform prevTrans = null;
        for (int i = 0; i < array.length; i++) {
            LNode crt = array[i];
            GLTransform crtTrans = crt.getTransform();
            if (prevTrans != crtTrans) {
                crtTrans.transform(gl2, gethW(), gethH(), mouse.getOriginalX(), mouse.getOriginalY());
                prevTrans = crtTrans;
            }
            crt.drawShape(gl2);
        }
        gl2.glFlush();
    }

    private int gethW() {
        if (flexmode == FIX) {
            return currentW <= 0 ? 1 : currentW / 2;
        } else {
            return initW <= 0 ? 1 : initW / 2;
        }
    }

    private int gethH() {
        if (flexmode == FIX) {
            return currentH <= 0 ? 1 : currentH / 2;
        } else {
            return initH <= 0 ? 1 : initH / 2;
        }
    }

    @ScreenSizeChangeListener
    public void noticeScreenChange(int w, int h) {
        this.currentW = w;
        this.currentH = h;
    }

}
