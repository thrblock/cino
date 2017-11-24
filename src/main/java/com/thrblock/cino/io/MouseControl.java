package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.util.HashMap;
import java.util.Map;
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
    private boolean enableMouseClick = false;
    private boolean enableMousePressed = false;
    private boolean enableMouseReleased = false;
    private boolean enableMouseWheelMoved = false;

    private Consumer<MouseEvent> mouseClicked = e -> {
    };
    private Consumer<MouseEvent> mousePressed = e -> {
    };
    private Consumer<MouseEvent> mouseReleased = e -> {
    };
    private Consumer<MouseEvent> mouseWheelMoved = e -> {
    };

    private Map<Integer, Consumer<java.awt.event.MouseEvent>> awtKeyMapping = new HashMap<>();

    private class NEWTAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(com.jogamp.newt.event.MouseEvent e) {
            MouseControl.this.x = e.getX() - screenWidth / 2;
            MouseControl.this.y = -e.getY() + screenHeight / 2;
        }

        @Override
        public void mouseClicked(com.jogamp.newt.event.MouseEvent e) {
            if (enableMouseClick) {
                mouseClicked.accept(new MouseEvent(e));
            }
        }

        @Override
        public void mousePressed(com.jogamp.newt.event.MouseEvent e) {
            press(e.getButton());
            if (enableMousePressed) {
                mousePressed.accept(new MouseEvent(e));
            }
        }

        @Override
        public void mouseReleased(com.jogamp.newt.event.MouseEvent e) {
            release(e.getButton());
            if (enableMouseReleased) {
                mouseReleased.accept(new MouseEvent(e));
            }
        }

        @Override
        public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent e) {
            if (enableMouseWheelMoved) {
                mouseWheelMoved.accept(new MouseEvent(e));
            }
        }
    }

    private NEWTAdapter newtMouseAdapter;

    @PostConstruct
    void init() {
        this.newtMouseAdapter = new NEWTAdapter();
        awtKeyMapping.put(java.awt.event.MouseEvent.MOUSE_MOVED, e -> {
            this.x = e.getX() - screenWidth / 2;
            this.y = -e.getY() + screenHeight / 2;
        });
        awtKeyMapping.put(java.awt.event.MouseEvent.MOUSE_CLICKED, e -> {
            if (enableMouseClick) {
                mouseClicked.accept(new MouseEvent(e));
            }
        });
        awtKeyMapping.put(java.awt.event.MouseEvent.MOUSE_PRESSED, e -> {
            press(e.getButton());
            if (enableMousePressed) {
                mousePressed.accept(new MouseEvent(e));
            }
        });
        awtKeyMapping.put(java.awt.event.MouseEvent.MOUSE_RELEASED, e -> {
            release(e.getButton());
            if (enableMouseReleased) {
                mouseReleased.accept(new MouseEvent(e));
            }
        });
        awtKeyMapping.put(java.awt.event.MouseEvent.MOUSE_WHEEL, e -> {
            if (enableMouseWheelMoved) {
                mouseWheelMoved.accept(new MouseEvent(e));
            }
        });
    }

    public MouseListener newtMouseListener() {
        return newtMouseAdapter;
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof java.awt.event.MouseEvent) {
            java.awt.event.MouseEvent e = (java.awt.event.MouseEvent) event;
            if (awtKeyMapping.containsKey(e.getID())) {
                awtKeyMapping.get(e.getID()).accept(e);
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

    public void setMouseClicked(Consumer<MouseEvent> mouseClicked) {
        if (mouseClicked == null) {
            this.enableMouseClick = false;
            this.mouseClicked = e -> {
            };
        } else {
            this.enableMouseClick = true;
            this.mouseClicked = mouseClicked;
        }
    }

    public void setMousePressed(Consumer<MouseEvent> mousePressed) {
        if (mousePressed == null) {
            this.enableMousePressed = false;
            this.mousePressed = e -> {
            };
        } else {
            this.enableMousePressed = true;
            this.mousePressed = mousePressed;
        }
    }

    public void setMouseReleased(Consumer<MouseEvent> mouseReleased) {
        if (mouseReleased == null) {
            this.enableMouseReleased = false;
            this.mouseReleased = e -> {
            };
        } else {
            this.enableMouseReleased = true;
            this.mouseReleased = mouseReleased;
        }
    }

    public void setMouseWheelMoved(Consumer<MouseEvent> mouseWheelMoved) {
        if (mouseWheelMoved == null) {
            this.enableMouseWheelMoved = false;
            this.mouseWheelMoved = e -> {
            };
        } else {
            this.enableMouseWheelMoved = true;
            this.mouseWheelMoved = mouseWheelMoved;
        }
    }

    public boolean isMouseButtonDown(int key) {
        if (key >= 0 && key <= mouseButtonStatus.length) {
            return mouseButtonStatus[key];
        } else {
            return false;
        }
    }
}
