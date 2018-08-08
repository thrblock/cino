package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.gltransform.GLTransformManager;

/**
 * 鼠标控制器
 * 
 * @author lizepu
 */
@Component
public class MouseControl implements AWTEventListener {
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

    @Value("${cino.frame.flexmode:0}")
    private int flexmode;

    private float currentW;
    private float currentH;

    private int scaledX;
    private int scaledY;

    private boolean[] mouseButtonStatus = new boolean[64];

    @Autowired
    private GLTransformManager glTransform;
    private int originalX;
    private int originalY;

    @PostConstruct
    void init() {
        this.currentW = screenWidth;
        this.currentH = screenHeight;
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    @PreDestroy
    void destroy() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {
            MouseEvent e = (MouseEvent) event;
            if (e.getID() == MouseEvent.MOUSE_MOVED || e.getID() == MouseEvent.MOUSE_DRAGGED) {
                this.originalX = e.getX();
                this.originalY = e.getY();
                this.scaledX = getConvertedX(e.getX());
                this.scaledY = getConvertedY(e.getY());
            } else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                press(e.getButton());
            } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                release(e.getButton());
            }
        }
    }

    private int getConvertedX(int x) {
        return flexmode == 1 ? x : (int) (x * screenWidth / currentW);
    }

    private int getConvertedY(int y) {
        return flexmode == 1 ? y : (int) (y * screenHeight / currentH);
    }

    public int getScaledMouseX() {
        return scaledX;
    }

    public int getScaledMouseY() {
        return scaledY;
    }

    public int getOriginalX() {
        return originalX;
    }

    public int getOriginalY() {
        return originalY;
    }

    public float getScaledFactorX() {
        return flexmode == 1 ? 1.0f : (screenWidth / currentW);
    }

    public float getScaledFactorY() {
        return flexmode == 1 ? 1.0f : (screenHeight / currentH);
    }

    public int getMouseX() {
        return getMouseX(-1);
    }

    public int getMouseX(int layerIndex) {
        return Math.round(glTransform.getGLTransform(layerIndex).getUnprojectedMouseData()[0]);
    }

    public int getMouseY() {
        return getMouseY(-1);
    }

    public int getMouseY(int layerIndex) {
        return Math.round(glTransform.getGLTransform(layerIndex).getUnprojectedMouseData()[1]);
    }

    private void press(int st) {
        if (st >= 0 && st <= mouseButtonStatus.length) {
            mouseButtonStatus[st] = true;
        }
    }

    private void release(int st) {
        if (st >= 0 && st <= mouseButtonStatus.length) {
            mouseButtonStatus[st] = false;
        }
    }

    public AWTEventListener addMouseListener(Consumer<MouseEvent> mouseListener, int mouseEventID) {
        AWTEventListener listener = event -> {
            if (event instanceof MouseEvent) {
                MouseEvent e = (MouseEvent) event;
                if (e.getID() == mouseEventID) {
                    mouseListener.accept(e);
                }
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_MOTION_EVENT_MASK);
        return listener;
    }

    public AWTEventListener addMouseClicked(Consumer<MouseEvent> mouseClicked) {
        return addMouseListener(mouseClicked, MouseEvent.MOUSE_CLICKED);
    }

    public AWTEventListener addMousePressed(Consumer<MouseEvent> mousePressed) {
        return addMouseListener(mousePressed, MouseEvent.MOUSE_PRESSED);
    }

    public AWTEventListener addMouseReleased(Consumer<MouseEvent> mouseReleased) {
        return addMouseListener(mouseReleased, MouseEvent.MOUSE_RELEASED);
    }

    public void removeMouseHolder(AWTEventListener listener) {
        Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
    }

    public boolean isMouseButtonDown(int key) {
        if (key >= 0 && key <= mouseButtonStatus.length) {
            return mouseButtonStatus[key];
        } else {
            return false;
        }
    }

    @ScreenSizeChangeListener
    public void screenChange(int w, int h) {
        this.currentW = w;
        this.currentH = h;
    }
}
