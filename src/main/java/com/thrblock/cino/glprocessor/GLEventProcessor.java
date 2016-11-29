package com.thrblock.cino.glprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.thrblock.cino.CinoFrameConfig;
import com.thrblock.cino.glfragment.IGLFragmentContainer;
import com.thrblock.cino.glinitable.GLInitor;
import com.thrblock.cino.gllayer.GLLayer;
import com.thrblock.cino.gllayer.IGLLayerContainer;
import com.thrblock.cino.glshape.GLShape;
import com.thrblock.cino.gltexture.GLCharTextureGenerater;
import com.thrblock.cino.gltexture.GLTextureContainer;
import com.thrblock.cino.util.structure.CrudeLinkedList;

/**
 * GLEventProcessor 捕捉OpenGL绘制事件并进行处理，是各类组件中同步逻辑的调用者
 * @author lizepu
 *
 */
@Component
public class GLEventProcessor implements GLEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(GLEventProcessor.class);
    
    @Autowired
    private IGLFragmentContainer fragmentContainer;
    
    @Autowired
    private IGLLayerContainer layerContainer;
    
    @Autowired
    private GLTextureContainer textureContainer;
    
    @Autowired
    private GLCharTextureGenerater charTextureContainer;
    
    @Autowired
    private GLInitor contextInitor;
    
    @Autowired
    private CinoFrameConfig config;
    
    private boolean reshaped = false;
    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        contextInitor.glInitializing(gl);
        textureContainer.parseTexture(gl2);
        charTextureContainer.parseTexture(gl2);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        for(int i = 0;i < layerContainer.size();i++) {
            drawLayer(layerContainer.getLayer(i),gl2);
        }
        drawLayer(layerContainer.getLayer(-1),gl2);
        gl2.glFlush();
        fragmentContainer.allFragment();
        layerContainer.swap();
    }

    private void drawLayer(GLLayer layer,GL2 gl2) {
        gl2.glBlendFunc(layer.getMixA(),layer.getMixB());
        layer.viewOffset(gl2);
        CrudeLinkedList<GLShape>.CrudeIter shapeIter = layer.iterator();
        while(shapeIter.hasNext()) {
            GLShape shape = shapeIter.next();
            if(shape.isVisible()) {
                shape.beforeDraw(gl2);
                shape.drawShape(gl2);
                shape.afterDraw(gl2);
            }
            if(shape.isDestory()) {
                shapeIter.remove();
            }
        }
        shapeIter.reset();
    }


    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        LOG.info("GL init");
        Thread.currentThread().setName("GL_Draw");
        if(config.isVsync()) {
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
        if(glErrorCode != GL.GL_NO_ERROR) {
            LOG.warn("GL Error Status:" + glErrorCode);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w,
            int h) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        config.setScreenWidth(w);
        config.setScreenHeight(h);
        
        int orthW = w <= 0?1:w/2;
        int orthH = h <= 0?1:h/2;
        if(!reshaped || config.getFlexMode() == CinoFrameConfig.FIX) {
            reshaped = true;
            gl2.glViewport(0, 0, w, h);
            gl2.glMatrixMode(GL2.GL_PROJECTION);
            gl2.glLoadIdentity();
            gl2.glOrtho(-orthW,orthW,-orthH,orthH,0,1.0f);
            LOG.info("GL reshape:" + x + "," + y + "," + w + "," + h);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        LOG.info("GL dispose");
    }
}
