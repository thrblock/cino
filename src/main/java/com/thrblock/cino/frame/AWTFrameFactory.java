package com.thrblock.cino.frame;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.thrblock.cino.io.KeyControlStack;
import com.thrblock.cino.io.MouseBus;
import com.thrblock.cino.io.MouseControl;
import com.thrblock.cino.io.MouseEvent;

/**
 * GL渲染窗体设置
 * 
 * @author thrblock
 *
 */
@Component
@Lazy(true)
public class AWTFrameFactory {
    private static final Logger LOG = LoggerFactory.getLogger(AWTFrameFactory.class);

    @Autowired
    private GLEventListener glEventListener;

    @Autowired
    private KeyControlStack keyStack;

    @Autowired
    private MouseControl mouseControl;

    @Autowired
    private MouseBus mouseBus;
    /**
     * 使用的显示卡
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
     * 渲染位置宽度 （像素）
     */
    @Value("${cino.frame.screen.width:800}")
    private int screenWidth = 800;
    /**
     * 渲染位置高度（像素）
     */
    @Value("${cino.frame.screen.height:600}")
    private int screenHeight = 600;

    private BiConsumer<Integer, Integer> resizeFunction;
    @PostConstruct
    void init() {
        Toolkit.getDefaultToolkit().addAWTEventListener(keyStack, AWTEvent.KEY_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(mouseControl, AWTEvent.MOUSE_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(mouseControl, AWTEvent.MOUSE_MOTION_EVENT_MASK);
        mouseBus.setAdder(l -> {
            AWTEventListener awtl = event -> {
                if (event instanceof java.awt.event.MouseEvent) {
                    java.awt.event.MouseEvent e = (java.awt.event.MouseEvent) event;
                    if (e.getID() == java.awt.event.MouseEvent.MOUSE_CLICKED) {
                        l.mouseClicked(new MouseEvent(e));
                    } else if (e.getID() == java.awt.event.MouseEvent.MOUSE_PRESSED) {
                        l.mousePressed(new MouseEvent(e));
                    } else if (e.getID() == java.awt.event.MouseEvent.MOUSE_RELEASED) {
                        l.mouseReleased(new MouseEvent(e));
                    }
                }
            };
            Toolkit.getDefaultToolkit().addAWTEventListener(awtl, AWTEvent.MOUSE_EVENT_MASK);
            Toolkit.getDefaultToolkit().addAWTEventListener(awtl, AWTEvent.MOUSE_MOTION_EVENT_MASK);
            return awtl;
        });
        mouseBus.setRemover(o -> Toolkit.getDefaultToolkit().removeAWTEventListener((AWTEventListener) o));
    }

    /**
     * 按照配置 构造JFrame
     * 
     * @return 满足配置的jframe
     */
    public JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setTitle(frameTitle);
        return buildFrame(frame, frame.getContentPane(), true);
    }

    /**
     * 按照配置 已固定步骤初始化一个JFrame
     * 
     * @param frame
     *            提供的jframe
     * @param container
     *            awt container
     * @param pack
     *            是否按照建议的大小重新布局组件
     * @return 满足条件的jframe实例
     */
    public JFrame buildFrame(JFrame frame, Container container, boolean pack) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GLCapabilities glcaps = new GLCapabilities(GLProfile.getDefault());
        glcaps.setDoubleBuffered(doubleBuffer);
        GLCanvas canvas = new GLCanvas(glcaps);
        canvas.addGLEventListener(glEventListener);
        LOG.info("graphics device you are current using:" + graphicsDevice.toString());
        if (fullScreen) {
            frame.setUndecorated(true);
            container.add(canvas);
            graphicsDevice.setFullScreenWindow(frame);
        } else {
            frame.setResizable(flexible);
            canvas.setPreferredSize(new Dimension(screenWidth, screenHeight));
            container.add(canvas);
            if (pack) {
                frame.pack();
            }
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        }
        if (vsync) {
            Animator animator = new Animator(canvas);
            animator.setRunAsFastAsPossible(false);
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(true);
                animator.start();
            });
        } else {
            FPSAnimator animator = new FPSAnimator(canvas, framesPerSecond, true);
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(true);
                animator.start();
            });
        }
        if (hideMouse) {
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(), null);
            frame.getContentPane().setCursor(blankCursor);
        }
        this.resizeFunction = (w, h) -> canvas.setPreferredSize(new Dimension(w, h));
        return frame;
    }

    public void resizeLatest(int w,int h) {
        if(resizeFunction != null) {
            resizeFunction.accept(w, h);
        }
    }
}
