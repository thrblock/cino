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
import com.thrblock.cino.gllayer.GLLayer;
import com.thrblock.cino.gllayer.IGLLayerContainer;
import com.thrblock.cino.glshape.GLShape;
import com.thrblock.cino.gltexture.IGLTextureContainer;
import com.thrblock.cino.structureutil.CrudeLinkedList;

@Component
public class GLEventProcessor implements GLEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(GLEventProcessor.class);
    
    @Autowired
    private IGLFragmentContainer fragmentContainer;
    
    @Autowired
    private IGLLayerContainer layerContainer;
    
    @Autowired
    private IGLTextureContainer textureContainer;
    
    @Autowired
    CinoFrameConfig config;
    
    private GL gl;
    private GL2 gl2;
    private boolean reshaped = false;
    @Override
    public void display(GLAutoDrawable drawable) {
        textureContainer.parseTexture(gl2);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        for(int i = 0;i < layerContainer.size();i++) {
            drawLayer(layerContainer.getLayer(i));
        }
        drawLayer(layerContainer.getLayer(-1));
        gl2.glFlush();
        fragmentContainer.allFragment();
        layerContainer.swap();
    }

    private void drawLayer(GLLayer layer) {
        gl2.glBlendFunc(layer.getMixA(),layer.getMixB());
        layer.viewOffset(gl2);
        CrudeLinkedList<GLShape>.CrudeIter shapeIter = layer.iterator();
        while(shapeIter.hasNext()) {
            GLShape shape = shapeIter.next();
            if(shape.isVisible()) {
                shape.drawShape(gl2);
            }
            if(shape.isDestory()) {
                shapeIter.remove();
            }
        }
        shapeIter.reset();
    }


    @Override
    public void init(GLAutoDrawable drawable) {
        LOG.info("GL init");
        Thread.currentThread().setName("GL_Draw");
        gl = drawable.getGL();
        if(config.isVsync()) {
            gl.setSwapInterval(1);
        }
        gl2 = gl.getGL2();

        gl2.glEnable(GL.GL_MULTISAMPLE);

        gl2.glEnable(GL.GL_BLEND);
        gl2.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);

        gl2.glAlphaFunc(GL.GL_GREATER, 0);
        gl2.glEnable(GL.GL_ALPHA);

        gl2.glPointSize(1);
        gl2.glEnable(GL2.GL_POINT_SMOOTH);
        gl2.glHint(GL2.GL_POINT_SMOOTH, GL.GL_NICEST);

        gl2.glLineWidth(1);
        gl2.glEnable(GL.GL_LINE_SMOOTH);
        gl2.glHint(GL.GL_LINE_SMOOTH, GL.GL_NICEST);

        gl2.glEnable(GL.GL_TEXTURE_2D);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w,
            int h) {
        config.setScreenWidth(w);
        config.setScreenHeight(h);
        if(!reshaped || config.getFlexMode() == CinoFrameConfig.FIX) {
            reshaped = true;
            gl2.glViewport(0, 0, w, h);
            gl2.glMatrixMode(GL2.GL_PROJECTION);
            gl2.glLoadIdentity();
            gl2.glOrtho(0,w <= 0?1:w,h <= 0?1:h,0,0,1.0f);
            LOG.info("GL reshape:" + x + "," + y + "," + w + "," + h);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        LOG.info("GL dispose");
    }
}
