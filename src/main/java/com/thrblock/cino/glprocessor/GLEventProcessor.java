package com.thrblock.cino.glprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.thrblock.cino.debug.DebugPannel;
import com.thrblock.cino.debug.GLDebugHelper;
import com.thrblock.cino.glanimate.GLAnimateManager;
import com.thrblock.cino.glinitable.GLInitor;
import com.thrblock.cino.gllayer.GLLayerManager;
import com.thrblock.cino.gltransform.GLTransformManager;

/**
 * GLEventProcessor 捕捉OpenGL绘制事件并进行处理，是各类组件中同步逻辑的调用者
 * 
 * @author lizepu
 *
 */
@Component
public class GLEventProcessor implements GLEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(GLEventProcessor.class);

    @Autowired
    private GLAnimateManager animateManager;

    @Autowired
    private GLLayerManager layerManager;

    @Autowired
    private GLInitor contextInitor;

    @Value("${cino.frame.vsync:false}")
    private boolean vsync = false;

    @Value("${cino.debug.enable:false}")
    private boolean debug;

    @Autowired
    private DebugPannel fpsCounter;

    @Autowired
    private GLScreenSizeChangeListenerHolder screenChangeListener;

    @Autowired
    private GLTransformManager transformManager;

    @Override
    public void display(GLAutoDrawable drawable) {
        long startTime = System.currentTimeMillis();
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();

        transformManager.initDefault(gl2);
        GLDebugHelper.logIfError(gl2, "init default transform");

        contextInitor.glInitializing(gl);
        GLDebugHelper.logIfError(gl2, "glInitializing");

        layerManager.drawAllLayer(gl2);
        GLDebugHelper.logIfError(gl2, "drawAllLayer");

        animateManager.runAll();
        GLDebugHelper.logIfError(gl2, "runAllAnimate");

        fpsCounter.noticeDrawCall(System.currentTimeMillis() - startTime);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        int[] version = new int[2];
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        gl2.glGetIntegerv(GL2.GL_MAJOR_VERSION, version, 0);
        gl2.glGetIntegerv(GL2.GL_MINOR_VERSION, version, 1);
        if (debug) {
            GLDebugHelper.enable(version);
            if(version[0] * 10 + version[1] >= 43) {
                GL4 gl4 = gl2.getGL4();
                gl4.glEnable(GL4.GL_DEBUG_OUTPUT);
                LOG.info("Opengl debug output enabled.");
            }
        }
        LOG.info("GL init");
        if (LOG.isInfoEnabled()) {
            LOG.info("The opengl version you are current using:{}", version);
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

        GLDebugHelper.logIfError(gl2, "glinit");
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        transformManager.reshape(drawable, x, y, w, h);
        screenChangeListener.getScreenChangeListener().forEach(e -> e.accept(w, h));
        GLDebugHelper.logIfError(drawable.getGL(), "glreshape");
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        LOG.info("GL dispose");
        GLDebugHelper.logIfError(drawable.getGL(), "gldispose");
    }
}
