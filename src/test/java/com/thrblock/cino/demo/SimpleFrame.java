package com.thrblock.cino.demo;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.springframework.context.support.AbstractApplicationContext;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.thrblock.cino.CinoInitor;
import com.thrblock.cino.fragment.AbstractGLFragment;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.builder.IGLShapeBuilder;

public class SimpleFrame extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = -5581088030379424903L;
    private FPSAnimator animator;
    public SimpleFrame(GLEventListener proc) {
        setSize(800 + 8, 600 + 27);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GLCapabilities glcaps = new GLCapabilities(GLProfile.getDefault());
        glcaps.setDoubleBuffered(true);
        GLCanvas canvas = new GLCanvas(glcaps);
        canvas.addGLEventListener(proc);
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
        AbstractApplicationContext context = CinoInitor.getCinoContext();
        GLEventListener proc = context.getBean(GLEventListener.class);
        new SimpleFrame(proc);
        
        IGLShapeBuilder builder = context.getBean(IGLShapeBuilder.class);
        final GLPoint point = builder.buildGLPoint(200f,150f);
        point.setColor(Color.GREEN);
        point.setPointSize(5.0f);
        point.show();
        
        new AbstractGLFragment() {
        	double t = 0;
			@Override
			public void fragment() {
				point.setXOffset((float)Math.sin(t));
				point.setYOffset((float)Math.cos(t));
				t+= 0.03;
			}
		};
    }
}
