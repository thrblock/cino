package com.thrblock.cino.demo;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.thrblock.cino.painter.GLPainter;

public class SimpleFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5581088030379424903L;
	private FPSAnimator animator;
	public SimpleFrame() {
		setSize(800 + 8, 600 + 27);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GLCapabilities glcaps = new GLCapabilities(GLProfile.getDefault());
		glcaps.setDoubleBuffered(true);
		GLCanvas canvas = new GLCanvas(glcaps);
		canvas.addGLEventListener(new GLPainter());
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		this.animator= new FPSAnimator(canvas,30,true);
		SwingUtilities.invokeLater(new Runnable() { 
            @Override
			public void run() {  
            	SimpleFrame.this.setVisible(true);
                animator.start();  
            }  
        }); 
	}
	public static void main(String[] args) {
		new SimpleFrame();
	}
}
