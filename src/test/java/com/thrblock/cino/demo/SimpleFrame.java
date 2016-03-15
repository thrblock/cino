package com.thrblock.cino.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.thrblock.cino.CinoInitor;
import com.thrblock.cino.glfragment.AbstractGLFragment;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.builder.IGLShapeBuilder;
import com.thrblock.cino.io.IKeyControlStack;
import com.thrblock.cino.io.IMouseControl;

public class SimpleFrame extends JFrame {
    private static final Logger LOG = LogManager.getLogger(SimpleFrame.class);
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
        
        this.animator= new FPSAnimator(canvas,55,true);
        
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(),null);

        // Set the blank cursor to the JFrame.
        getContentPane().setCursor(blankCursor);
        
        SwingUtilities.invokeLater(new Runnable() { 
            @Override
            public void run() {  
                SimpleFrame.this.setVisible(true);
                animator.start();  
            }  
        }); 
    }
    public static void main(String[] args) throws IOException {
        LOG.info("simple test begin");
        AbstractApplicationContext context = CinoInitor.getCinoContext();
        GLEventListener proc = context.getBean(GLEventListener.class);
        new SimpleFrame(proc);
        IGLShapeBuilder builder = context.getBean(IGLShapeBuilder.class);
        IGLShapeBuilder builder2 = context.getBean(IGLShapeBuilder.class);
        builder2.setLayer(1);
        
        GLLine xline = builder2.buildGLLine(0,300f,800f,300f);
        GLLine yline = builder2.buildGLLine(400f,0f,400f,600f);
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
        
        final GLRect rect = builder.buildGLRect(50f,50f,200f,200f);
        rect.setPointColor(0,Color.RED);
        rect.setPointColor(1,Color.GREEN);
        rect.setPointColor(2,Color.BLUE);
        rect.setPointColor(3,Color.YELLOW);
        
        rect.setFill(true);
        rect.show();
        
        final IKeyControlStack keyIO = context.getBean(IKeyControlStack.class);
        final IMouseControl mouseIO = context.getBean(IMouseControl.class);
        new AbstractGLFragment() {
            @Override
            public void fragment() {
                point.setX(mouseIO.getMouseX());
                point.setY(mouseIO.getMouseY());
                if(keyIO.isKeyDown(KeyEvent.VK_LEFT)) {
                    rect.setXOffset(-3f);
                } else if(keyIO.isKeyDown(KeyEvent.VK_RIGHT)) {
                    rect.setXOffset(3f);
                }
                if(keyIO.isKeyDown(KeyEvent.VK_UP)) {
                    rect.setYOffset(-3f);
                } else if(keyIO.isKeyDown(KeyEvent.VK_DOWN)) {
                    rect.setYOffset(3f);
                }
            }
        };
    }
}
