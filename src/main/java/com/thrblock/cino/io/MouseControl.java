package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseListener;

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
    private int x;
    private int y;

    private class NEWTAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(com.jogamp.newt.event.MouseEvent e) {
            MouseControl.this.x = e.getX() - screenWidth / 2;
            MouseControl.this.y = -e.getY() + screenHeight / 2;
        }
    }
    
    private NEWTAdapter newtMouseAdapter;
    
    @PostConstruct
    void init() {
        this.newtMouseAdapter = new NEWTAdapter();
    }
    
    public MouseListener newtMouseListener() {
        return newtMouseAdapter;
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

    public int getMouseX() {
        return x;
    }

    public int getMouseY() {
        return y;
    }
}
