package com.thrblock.cino.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.springframework.context.support.AbstractApplicationContext;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.thrblock.cino.CinoInitor;
import com.thrblock.cino.glfragment.AbstractGLFragment;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.builder.IGLShapeBuilder;

public class SimpleFrame extends JFrame {

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
        
        this.animator= new FPSAnimator(canvas,60,true);
        SwingUtilities.invokeLater(new Runnable() { 
            @Override
            public void run() {  
                SimpleFrame.this.setVisible(true);
                animator.start();  
            }  
        }); 
    }
    public static void main(String[] args) throws IOException {
        AbstractApplicationContext context = CinoInitor.getCinoContext();
        GLEventListener proc = context.getBean(GLEventListener.class);
        new SimpleFrame(proc);
        
        IGLShapeBuilder builder = context.getBean(IGLShapeBuilder.class);
        
        GLLine xline = builder.buildGLLine(0,300f,800f,300f);
    	GLLine yline = builder.buildGLLine(400f,0f,400f,600f);
    	xline.setColor(Color.GREEN);
    	xline.setLineWidth(3.5f);
    	yline.setColor(Color.YELLOW);
    	yline.setLineWidth(3.5f);
    	xline.show();
    	yline.show();
    	
        final GLPoint point = builder.buildGLPoint(200f,150f);
        point.setColor(Color.GREEN);
        point.setPointSize(5.0f);
        point.show();
        
        final GLRect rect = builder.buildGLRect(50f,50f,100f,100f);
        rect.setPointColor(0,Color.RED);
        rect.setPointColor(1,Color.GREEN);
        rect.setPointColor(2,Color.BLUE);
        rect.setPointColor(3,Color.YELLOW);
        rect.setLineWidth(2.0f);
        
        rect.setWidth(100f);
        rect.setHeight(100f);
        rect.setFill(true);
        rect.show();
        
        final GLOval oval = builder.buildGLOval(600f,400f, 100f,50f,50);
        oval.setFill(true);
        oval.setColor(Color.GREEN);
        oval.show();
        
        GLImage image = builder.buildGLImage(300f,50f,400f,240f,SimpleFrame.class.getResourceAsStream("T57.jpg"),"jpg");
        image.show();
        
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
