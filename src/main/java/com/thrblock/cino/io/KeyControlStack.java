package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.jogamp.newt.event.KeyAdapter;

/**
 * 键盘堆栈控制器,维护着一个键盘监听器栈结构，会把按键事件传给栈的顶层
 * @author lizepu
 */
@Component
public class KeyControlStack implements AWTEventListener {
    private boolean[] keyStatus = new boolean[1024];
    private Deque<KeyListener> listenerStack = new ConcurrentLinkedDeque<>();

    private class NEWTAdapter extends KeyAdapter {
        @Override
        public void keyPressed(com.jogamp.newt.event.KeyEvent e) {
            onKeyPressed(new KeyEvent(e));
        }
        @Override
        public void keyReleased(com.jogamp.newt.event.KeyEvent e) {
            onKeyRelease(new KeyEvent(e));
        }
    }
    
    private NEWTAdapter keyAdapter;
    @PostConstruct
    void init() {
        this.keyAdapter = new NEWTAdapter();
    }
    
    public NEWTAdapter newtKeyListener() {
        return keyAdapter;
    }



    public void pushKeyListener(KeyListener keyListener) {
        listenerStack.push(keyListener);
    }

    public KeyListener popKeyListener() {
        if(!listenerStack.isEmpty()) {
            return listenerStack.pop();
        } else {
            return null;
        }
    }

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
        if(event instanceof java.awt.event.KeyEvent) {
            java.awt.event.KeyEvent e = (java.awt.event.KeyEvent)event;
            int id = e.getID();
            switch (id) {
            case KeyEvent.KEY_PRESSED:
                onKeyPressed(new KeyEvent(e));
                break;
            case KeyEvent.KEY_RELEASED:
                onKeyRelease(new KeyEvent(e));
                break;
            default:
                break;
            }
        }
        
    }

    public void replaceKeyListener(KeyListener keyListener) {
        popKeyListener();
        pushKeyListener(keyListener);
    }
    
}
