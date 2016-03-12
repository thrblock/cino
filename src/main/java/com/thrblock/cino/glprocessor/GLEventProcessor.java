package com.thrblock.cino.glprocessor;

import java.awt.Color;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.thrblock.cino.fragment.IGLFragmentContainer;
import com.thrblock.cino.gllayer.GLLayer;
import com.thrblock.cino.gllayer.IGLLayerContainer;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLShape;

@Component
public class GLEventProcessor implements GLEventListener {
	private static final Logger LOG = LogManager.getLogger(GLEventProcessor.class);
	
	@Autowired
	private IGLFragmentContainer fragmentContainer;
	
	@Autowired
	private IGLLayerContainer layerContainer;
	
	private GL gl;
	private GL2 gl2;
	private GLLine x = new GLLine(0,300f,800f,300f);
	private GLLine y = new GLLine(400f,0f,400f,600f);
	{
		x.setColor(Color.GREEN);
		x.setLineWidth(3.5f);
		y.setColor(Color.YELLOW);
		y.setLineWidth(3.5f);
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		x.drawShape(gl2);
		y.drawShape(gl2);
		
		for(GLLayer layer:layerContainer) {
			gl2.glBlendFunc(layer.getMixA(),layer.getMixB());
			Iterator<GLShape> shapeIter = layer.iterator();
			while(shapeIter.hasNext()) {
				GLShape shape = shapeIter.next();
				if(shape.isVisible()) {
					shape.drawShape(gl2);
				}
				if(shape.isDestory()) {
					shapeIter.remove();
				}
			}
		}
		gl2.glFlush();
		fragmentContainer.allFragment();
		layerContainer.swap();
	}


	@Override
	public void init(GLAutoDrawable drawable) {
		Thread.currentThread().setName("GL_Draw");
		gl = drawable.getGL();
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
		LOG.info("reshape:x-" + x + ",y-" + y + ",w-" + w + ",h-" + h);
		if (h == 0) {
			h = 1;
		}
		gl2.glViewport(0, 0, w, h);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		gl2.glOrtho(0.0f, w, h, 0.0f, 0.0f, 1.0f);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}
