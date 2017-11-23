package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.util.function.Consumer;

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
    
    private boolean[] mouseButtonStatus = new boolean[64];
    private Consumer<MouseEvent> mouseClicked;
    private Consumer<MouseEvent> mousePressed;
    private Consumer<MouseEvent> mouseReleased;
    
    private class NEWTAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(com.jogamp.newt.event.MouseEvent e) {
            MouseControl.this.x = e.getX() - screenWidth / 2;
            MouseControl.this.y = -e.getY() + screenHeight / 2;
        }
        
        @Override
        public void mouseClicked(com.jogamp.newt.event.MouseEvent e) {
            if(mouseClicked != null) {
                mouseClicked.accept(new MouseEvent(e));
            }
        }
        
        @Override
        public void mousePressed(com.jogamp.newt.event.MouseEvent e) {
            press(e.getButton());
            if(mousePressed != null) {
                mousePressed.accept(new MouseEvent(e));
            }
        }

        @Override
        public void mouseReleased(com.jogamp.newt.event.MouseEvent e) {
            release(e.getButton());
            if(mouseReleased != null) {
                mouseReleased.accept(new MouseEvent(e));
            }
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
        if (event instanceof java.awt.event.MouseEvent) {
            java.awt.event.MouseEvent e = (java.awt.event.MouseEvent) event;
            if (e.getID() == java.awt.event.MouseEvent.MOUSE_MOVED) {
                this.x = e.getX() - screenWidth / 2;
                this.y = -e.getY() + screenHeight / 2;
            } else if(e.getID() == java.awt.event.MouseEvent.MOUSE_CLICKED) {
                if(mouseClicked != null) {
                    mouseClicked.accept(new MouseEvent(e));
                }
            } else if(e.getID() == java.awt.event.MouseEvent.MOUSE_PRESSED) {
                press(e.getButton());
                if(mousePressed != null) {
                    mousePressed.accept(new MouseEvent(e));
                }
            } else if(e.getID() == java.awt.event.MouseEvent.MOUSE_RELEASED) {
                release(e.getButton());
                if(mouseReleased != null) {
                    mouseReleased.accept(new MouseEvent(e));
                }
            }
        }
    }

    public int getMouseX() {
        return x;
    }

    public int getMouseY() {
        return y;
    }
    
    private void press(int st) {
        if(st >= 0 && st <= mouseButtonStatus.length) {
            mouseButtonStatus[st] = true;
        }
    }
    private void release(int st) {
        if(st >= 0 && st <= mouseButtonStatus.length) {
            mouseButtonStatus[st] = true;
        }
    }

    public void setMouseClicked(Consumer<MouseEvent> mouseClicked) {
        this.mouseClicked = mouseClicked;
    }

    public void setMousePressed(Consumer<MouseEvent> mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseReleased(Consumer<MouseEvent> mouseReleased) {
        this.mouseReleased = mouseReleased;
    }
}
