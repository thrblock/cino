package com.thrblock.cino.frame;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.thrblock.cino.CinoFrameConfig;

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

    /**
     * 使用的显示卡
     */
    private GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    @Autowired
    private CinoFrameConfig frameConfig;
    
    private BiConsumer<Integer, Integer> resizeFunction;

    /**
     * 按照配置 构造JFrame
     * 
     * @return 满足配置的jframe
     */
    public JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setTitle(frameConfig.getFrameTitle());
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
        glcaps.setDoubleBuffered(frameConfig.isDoubleBuffer());
        GLCanvas canvas = new GLCanvas(glcaps);
        canvas.addGLEventListener(glEventListener);
        LOG.info("graphics device you are current using:{}",graphicsDevice);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameConfig.isFullScreen()) {
            frame.setUndecorated(true);
            container.add(canvas);
            graphicsDevice.setFullScreenWindow(frame);
        } else {
            frame.setResizable(frameConfig.isFlexible());
            canvas.setPreferredSize(new Dimension(frameConfig.getScreenWidth(), frameConfig.getScreenHeight()));
            container.add(canvas);
            if (pack) {
                frame.pack();
            }
            frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        }
        if (frameConfig.isVsync()) {
            Animator animator = new Animator(canvas);
            animator.setRunAsFastAsPossible(false);
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(true);
                animator.start();
            });
        } else {
            FPSAnimator animator = new FPSAnimator(canvas, frameConfig.getFramesPerSecond(), true);
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(true);
                animator.start();
            });
        }
        if (frameConfig.isHideMouse()) {
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
