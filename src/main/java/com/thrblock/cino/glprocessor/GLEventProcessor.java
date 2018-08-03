package com.thrblock.cino.glprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.thrblock.cino.debug.DebugPannel;
import com.thrblock.cino.glanimate.GLAnimateManager;
import com.thrblock.cino.glinitable.GLInitor;
import com.thrblock.cino.gllayer.GLLayerManager;

/**
 * GLEventProcessor 捕捉OpenGL绘制事件并进行处理，是各类组件中同步逻辑的调用者
 * 
 * @author lizepu
 *
 */
@Component
public class GLEventProcessor implements GLEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(GLEventProcessor.class);

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

    @Autowired
    private GLAnimateManager animateManager;

    @Autowired
    private GLLayerManager layerManager;

    @Autowired
    private GLInitor contextInitor;

    @Value("${cino.frame.vsync:false}")
    private boolean vsync = false;

    /**
     * 当前缩放模式
     */
    @Value("${cino.frame.flexmode:" + SCALE + "}")
    private int flexMode;

    @Autowired
    private DebugPannel fpsCounter;

    private boolean reshaped = false;

    @Autowired
    GLScreenSizeChangeListenerHolder screenChangeListener;

    @Override
    public void display(GLAutoDrawable drawable) {
        long startTime = System.currentTimeMillis();
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        contextInitor.glInitializing(gl);
        layerManager.drawAllLayer(gl2);
        animateManager.runAll();
        fpsCounter.noticeDrawCall(System.currentTimeMillis() - startTime);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        LOG.info("GL init");
        if(LOG.isInfoEnabled()) {
            LOG.info("The Renderer you are current using:{}", gl.glGetString(GL.GL_RENDERER));
            LOG.info("The Renderer driver version is {}", gl.glGetString(GL.GL_VERSION));
        }
        Thread.currentThread().setName("GL_Draw");
        if (vsync) {
            gl.setSwapInterval(1);
        }
        gl2.glEnable(GL.GL_MULTISAMPLE);

        gl2.glEnable(GL.GL_BLEND);
        gl2.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);

        gl2.glEnable(GL2.GL_ALPHA_TEST);
        gl2.glAlphaFunc(GL.GL_GREATER, 0);

        gl2.glPointSize(1);
        gl2.glEnable(GL2.GL_POINT_SMOOTH);
        gl2.glHint(GL2.GL_POINT_SMOOTH_HINT, GL.GL_NICEST);

        gl2.glLineWidth(1);
        gl2.glEnable(GL.GL_LINE_SMOOTH);
        gl2.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

        int glErrorCode = gl.glGetError();
        if (glErrorCode != GL.GL_NO_ERROR) {
            LOG.warn("GL Error Status:{}", glErrorCode);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        
        int orthW = w <= 0 ? 1 : w / 2;
        int orthH = h <= 0 ? 1 : h / 2;
        if (!reshaped || flexMode == FIX) {
            reshaped = true;
            gl2.glViewport(0, 0, w, h);
            gl2.glMatrixMode(GL2.GL_PROJECTION);
            gl2.glLoadIdentity();
            gl2.glOrtho(-orthW, orthW, -orthH, orthH, 0, 1.0f);
            LOG.info("GL reshape:{},{},{},{}", x, y, w, h);
        }
        screenChangeListener.getScreenChangeListener().forEach(e -> e.accept(w, h));
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        LOG.info("GL dispose");
    }
}
