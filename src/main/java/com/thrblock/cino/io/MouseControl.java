package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MouseBus bus;

    private int x;
    private int y;

    private boolean[] mouseButtonStatus = new boolean[64];

    private class NEWTAdapter extends MouseAdapter {
        private void moving(com.jogamp.newt.event.MouseEvent e) {
            MouseControl.this.x = e.getX() - screenWidth / 2;
            MouseControl.this.y = -e.getY() + screenHeight / 2;
        }

        @Override
        public void mouseMoved(com.jogamp.newt.event.MouseEvent e) {
            moving(e);
        }

        @Override
        public void mouseDragged(com.jogamp.newt.event.MouseEvent e) {
            moving(e);
        }

        @Override
        public void mousePressed(com.jogamp.newt.event.MouseEvent e) {
            press(e.getButton());
        }

        @Override
        public void mouseReleased(com.jogamp.newt.event.MouseEvent e) {
            release(e.getButton());
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
            if (e.getID() == java.awt.event.MouseEvent.MOUSE_MOVED
                    || e.getID() == java.awt.event.MouseEvent.MOUSE_DRAGGED) {
                this.x = e.getX() - screenWidth / 2;
                this.y = -e.getY() + screenHeight / 2;
            } else if (e.getID() == java.awt.event.MouseEvent.MOUSE_PRESSED) {
                press(e.getButton());
            } else if (e.getID() == java.awt.event.MouseEvent.MOUSE_RELEASED) {
                release(e.getButton());
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
        if (st >= 0 && st <= mouseButtonStatus.length) {
            mouseButtonStatus[st] = true;
        }
    }

    private void release(int st) {
        if (st >= 0 && st <= mouseButtonStatus.length) {
            mouseButtonStatus[st] = false;
        }
    }

    public Object addMouseClicked(Consumer<MouseEvent> mouseClicked) {
        return bus.addMouseListener(new com.thrblock.cino.io.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClicked.accept(e);
            }
        });
    }

    public Object addMousePressed(Consumer<MouseEvent> mousePressed) {
        return bus.addMouseListener(new com.thrblock.cino.io.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed.accept(e);
            }
        });
    }

    public Object addMouseReleased(Consumer<MouseEvent> mouseReleased) {
        return bus.addMouseListener(new com.thrblock.cino.io.MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleased.accept(e);
            }
        });
    }
    
    public void removeMouseHolder(Object holder) {
        bus.removeMouseListener(holder);
    }
    
    public boolean isMouseButtonDown(int key) {
        if (key >= 0 && key <= mouseButtonStatus.length) {
            return mouseButtonStatus[key];
        } else {
            return false;
        }
    }
}
