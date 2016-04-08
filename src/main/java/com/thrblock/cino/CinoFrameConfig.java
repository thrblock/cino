package com.thrblock.cino;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

@Component
public class CinoFrameConfig {
    /**
     * 等比例缩放
     */
    public static final int SCALE = 0;
    /**
     * 仅仅是画布变大，图形大小不变
     */
    public static final int FIX = 1;
    
    @Autowired
    private GLEventListener glEventListener;
    
    /**
     * 使用的显示卡
     */
    private GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    
    /**
     * 开启双缓冲
     */
    private boolean doubleBuffer = true;
    /**
     * 开启垂直同步
     */
    private boolean vsync = false;
    /**
     * 是否全屏
     */
    private boolean fullScreen = false;
    /**
     * FPS 每秒绘制速率，垂直同步开启时无效
     */
    private int framesPerSecond = 60;
    /**
     * 窗体是否可变，全屏时无效
     */
    private boolean flexible = false;
    /**
     * 隐藏鼠标
     */
    private boolean hideMouse = true;
    /**
     * 当前缩放模式
     */
    private int flexMode = SCALE;
    
    private int screenWidth = 800;
    private int screenHeight = 600;
    
    
    public boolean isHideMouse() {
        return hideMouse;
    }

    public void setHideMouse(boolean hideMouse) {
        this.hideMouse = hideMouse;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public GraphicsDevice getGraphicsDevice() {
        return graphicsDevice;
    }

    public void setGraphicsDevice(GraphicsDevice graphicsDevice) {
        this.graphicsDevice = graphicsDevice;
    }

    public boolean isDoubleBuffer() {
        return doubleBuffer;
    }

    public void setDoubleBuffer(boolean doubleBuffer) {
        this.doubleBuffer = doubleBuffer;
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    public boolean isFlexible() {
        return flexible;
    }

    public void setFlexible(boolean flexible) {
        this.flexible = flexible;
    }

    public int getFlexMode() {
        return flexMode;
    }

    public void setFlexMode(int flexMode) {
        this.flexMode = flexMode;
    }

    public JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GLCapabilities glcaps = new GLCapabilities(GLProfile.getDefault());
        glcaps.setDoubleBuffered(doubleBuffer);
        GLCanvas canvas = new GLCanvas(glcaps);
        canvas.addGLEventListener(glEventListener);
        if(fullScreen) {
            frame.setUndecorated(true);
            frame.getContentPane().add(canvas);
            graphicsDevice.setFullScreenWindow(frame);
        } else {
            frame.setResizable(flexible);
            canvas.setPreferredSize(new Dimension(screenWidth, screenHeight));
            frame.getContentPane().add(canvas);
            frame.pack();
            
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        }
        if(vsync) {
            Animator animator = new Animator(canvas);
            animator.setRunAsFastAsPossible(false);
            
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(true);
                animator.start();
            });
        } else {
            FPSAnimator animator = new FPSAnimator(canvas,framesPerSecond, true);
            
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(true);
                animator.start();
            });
        }
        if(hideMouse) {
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(), null);
            frame.getContentPane().setCursor(blankCursor);
        }
        return frame;
    }
}
