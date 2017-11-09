package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 鼠标控制器
 * 
 * @author lizepu
 */
@Component
public class MouseControl implements AWTEventListener, IMouseControl {
    private static final Logger LOG = LoggerFactory.getLogger(MouseControl.class);
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
    private int x;
    private int y;

    /**
     * 构造鼠标控制器
     */
    public MouseControl() {
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_MOTION_EVENT_MASK);
        LOG.info("inited.");
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {
            MouseEvent e = (MouseEvent) event;
            if (e.getID() == MouseEvent.MOUSE_MOVED) {
                this.x = e.getX() - screenWidth / 2;
                this.y = -e.getY() + screenHeight / 2;
            }
        }
    }

    @Override
    public int getMouseX() {
        return x;
    }

    @Override
    public int getMouseY() {
        return y;
    }
}
