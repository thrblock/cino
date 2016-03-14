package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MouseControl implements AWTEventListener,IMouseControl {
    private static final Logger LOG = LogManager.getLogger(MouseControl.class);
    private int x;
    private int y;
    public MouseControl(){
        Toolkit.getDefaultToolkit().addAWTEventListener(this,AWTEvent.MOUSE_MOTION_EVENT_MASK);
        LOG.info("inited.");
    }
    @Override
    public void eventDispatched(AWTEvent event) {
        if(event instanceof MouseEvent) {
            MouseEvent e = (MouseEvent)event;
            if(e.getID() == MouseEvent.MOUSE_MOVED) {
                this.x = e.getX();
                this.y = e.getY();
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
