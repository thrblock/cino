package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


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

    private boolean[] mouseButtonStatus = new boolean[64];

    private int originalX;
    private int originalY;

    @PostConstruct
    void init() {
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
            } else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                press(e.getButton());
            } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                release(e.getButton());
            }
        }
    }

    public int getOriginalX() {
        return originalX;
    }

    public int getOriginalY() {
        return originalY;
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

}
