package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.IntFunction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * 键盘堆栈控制器,维护着一个键盘监听器栈结构，会把按键事件传给栈的顶层
 * 
 * @author lizepu
 */
@Component
public class KeyControlStack implements AWTEventListener {
    private boolean[] keyStatus = new boolean[1024];
    private Deque<KeyListener> listenerStack = new ConcurrentLinkedDeque<>();
    private IntFunction<Boolean> getLockingKeyState;

    @PostConstruct
    void init() {
        Toolkit tookit = Toolkit.getDefaultToolkit();
        tookit.addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
        getLockingKeyState = tookit::getLockingKeyState;
    }
    
    @PreDestroy
    void destroy() {
        listenerStack.clear();
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    }

    public void pushKeyListener(KeyListener keyListener) {
        listenerStack.push(keyListener);
    }

    public KeyListener popKeyListener() {
        if (!listenerStack.isEmpty()) {
            return listenerStack.pop();
        } else {
            return null;
        }
    }

    public void removeKeyListener(KeyListener keyListener) {
        listenerStack.remove(keyListener);
    }

    public boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keyStatus.length) {
            return keyStatus[keyCode];
        } else {
            return false;
        }
    }
    
    public boolean getLockingKeyState(int keyCode) {
        return getLockingKeyState.apply(keyCode);
    }
    
    public boolean useCapital() {
        return getLockingKeyState(KeyEvent.VK_CAPS_LOCK) ^ isKeyDown(KeyEvent.VK_SHIFT);
    }

    private KeyListener peekKeyListener() {
        return listenerStack.peek();
    }

    private void onKeyPressed(KeyEvent e) {
        KeyListener listener = peekKeyListener();
        if (listener != null) {
            listener.keyPressed(e);
        }
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keyStatus.length) {
            keyStatus[e.getKeyCode()] = true;
        }
    }

    private void onKeyRelease(KeyEvent e) {
        KeyListener listener = peekKeyListener();
        if (listener != null) {
            listener.keyReleased(e);
        }
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keyStatus.length) {
            keyStatus[e.getKeyCode()] = false;
        }
    }
    
    private void onKeyTyped(KeyEvent e) {
        KeyListener listener = peekKeyListener();
        if (listener != null) {
            listener.keyTyped(e);
        }
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof java.awt.event.KeyEvent) {
            java.awt.event.KeyEvent e = (java.awt.event.KeyEvent) event;
            int id = e.getID();
            switch (id) {
            case KeyEvent.KEY_PRESSED:
                onKeyPressed(e);
                break;
            case KeyEvent.KEY_RELEASED:
                onKeyRelease(e);
                break;
            case KeyEvent.KEY_TYPED:
                onKeyTyped(e);
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
