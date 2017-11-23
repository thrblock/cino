package com.thrblock.cino.frame;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import com.thrblock.cino.io.KeyControlStack;
import com.thrblock.cino.io.MouseControl;

@Component
@Lazy(true)
public class NEWTFrameFactory {
    private static final Logger LOG = LoggerFactory.getLogger(NEWTFrameFactory.class);
    /**
     * 等比例缩放
     * 
     * @see #setFlexMode
     */
    public static final int SCALE = 0;
    /**
     * 仅仅是画布变大，图形大小不变
     * 
     * @see #setFlexMode
     */
    public static final int FIX = 1;

    @Autowired
    private GLEventListener glEventListener;
    
    @Autowired
    private MouseControl mouseControl;
    
    @Autowired
    private KeyControlStack keyStack;

    /**
     * 使用的显示设备
     */
    private GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    @Value("${cino.frame.title:title}")
    private String frameTitle = "title";
    /**
     * 开启双缓冲
     */
    @Value("${cino.frame.doublebuffer:true}")
    private boolean doubleBuffer = true;
    /**
     * 开启垂直同步
     */
    @Value("${cino.frame.vsync:false}")
    private boolean vsync = false;
    /**
     * 是否全屏
     */
    @Value("${cino.frame.fullscreen:false}")
    private boolean fullScreen = false;
    /**
     * FPS 每秒绘制速率，垂直同步开启时无效
     */
    @Value("${cino.frame.fps:60}")
    private int framesPerSecond = 60;
    /**
     * 窗体是否可变，全屏时无效
     */
    @Value("${cino.frame.flexible:false}")
    private boolean flexible = false;
    /**
     * 隐藏鼠标
     */
    @Value("${cino.frame.hidemouse:true}")
    private boolean hideMouse = true;
    /**
     * 当前缩放模式
     */
    @Value("${cino.frame.flexmode:" + SCALE + "}")
    private int flexMode = SCALE;

    /**
     * 渲染位置宽度 （像素）
     */
    @Value("${cino.frame.screen.width:800}")
    private int screenWidth = 800;
    /**
     * 渲染位置高度（像素）
     */
    @Value("${cino.frame.screen.height:600}")
    private int screenHeight = 600;

    public GLWindow buildFrame() {
        // Get the default OpenGL profile, reflecting the best for your running platform
        GLProfile glp = GLProfile.getDefault();
        // Specifies a set of OpenGL capabilities, based on your profile.
        GLCapabilities caps = new GLCapabilities(glp);
        // Create the OpenGL rendering canvas
        GLWindow window = GLWindow.create(caps);

        // Create a animator that drives canvas' display() at the specified FPS.
        final FPSAnimator animator = new FPSAnimator(window, framesPerSecond, true);

        LOG.info("graphics device you are current using:" + graphicsDevice.toString());

        window.addWindowListener(new WindowAdapter() {
            // Use a dedicate thread to run the stop() to ensure that the
            // animator stops before program exits.
            @Override
            public void windowDestroyNotify(WindowEvent arg0) {
                new Thread(()-> {
                    if (animator.isStarted()) {
                        animator.stop(); // stop the animator loop
                    }
                    System.exit(0);
                }).start();
            }
        });

        window.addGLEventListener(glEventListener);
        window.addMouseListener(mouseControl.newtMouseListener());
        window.addKeyListener(keyStack.newtKeyListener());
        window.setSize(screenWidth, screenHeight);
        window.setTitle(frameTitle);
        window.setPointerVisible(!hideMouse);
        window.setVisible(true);
        animator.start(); // start the animator loop
        return window;
    }
}
