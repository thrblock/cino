package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Component;

@Component
public class KeyControlStack implements AWTEventListener, IKeyControlStack {
    private boolean[] keyStatus = new boolean[1024];
    private Deque<KeyListener> listenerStack = new ConcurrentLinkedDeque<>();

    public KeyControlStack() {
        Toolkit.getDefaultToolkit().addAWTEventListener(this,AWTEvent.KEY_EVENT_MASK);
    }

    @Override
    public void pushKeyListener(KeyListener keyListener) {
        listenerStack.push(keyListener);
    }

    @Override
    public KeyListener popKeyListener() {
        return listenerStack.pop();
    }

    @Override
    public boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keyStatus.length) {
            return keyStatus[keyCode];
        } else {
            return false;
        }
    }

    private KeyListener peekKeyListener() {
        return listenerStack.peek();
    }

    private void onKeyTyped(KeyEvent e) {
    	KeyListener listener = peekKeyListener();
        if(listener != null) {
            listener.keyTyped(e);
        }
    }
    
    private void onKeyPressed(KeyEvent e) {
    	KeyListener listener = peekKeyListener();
        if(listener != null) {
            listener.keyPressed(e);
        }
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keyStatus.length) {
            keyStatus[e.getKeyCode()] = true;
        }
    }
    
    private void onKeyRelease(KeyEvent e) {
    	KeyListener listener = peekKeyListener();
        if(listener != null) {
            listener.keyReleased(e);
        }
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keyStatus.length) {
            keyStatus[e.getKeyCode()] = false;
        }
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if(event instanceof KeyEvent) {
            KeyEvent e = (KeyEvent)event;
            int id = e.getID();
            switch (id) {
            case KeyEvent.KEY_TYPED:
                onKeyTyped(e);
                break;
            case KeyEvent.KEY_PRESSED:
                onKeyPressed(e);
                break;
            case KeyEvent.KEY_RELEASED:
                onKeyRelease(e);
                break;
            default:
                break;
            }
        }
        
    }
}
