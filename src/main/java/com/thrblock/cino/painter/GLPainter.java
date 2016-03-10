package com.thrblock.cino.painter;

import java.awt.Color;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.thrblock.cino.drawable.GLLine;

public class GLPainter implements GLEventListener {
	private static final Logger LOG = LogManager.getLogger(GLPainter.class);
	private GL gl;
	private GL2 gl2;
	private GLLine line = new GLLine(-1f,0f,1f,0f);
	{
		line.setPointColor(0, Color.RED);
		line.setPointColor(1, Color.GREEN);
		line.setLineWidth(10f);
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl2.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		line.drawShape(gl2);
		line.setTheta(line.getTheta() + 0.02f);
		gl2.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
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
		gl2.glOrtho(-1f,1f, 1.5f,1.5f, 0,0);
	}

}
