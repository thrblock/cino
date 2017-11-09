package com.thrblock.cino.io;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.util.function.BooleanSupplier;

import org.springframework.stereotype.Component;

import com.thrblock.cino.util.structure.CrudeLinkedList;

/**
 * 键盘堆栈控制器,维护着一个键盘监听器栈结构，会把按键事件传给栈的顶层
 * @author lizepu
 */
@Component
public class KeyControlStack implements AWTEventListener, IKeyControlStack {
    private boolean[] keyStatus = new boolean[1024];
    private Deque<KeyListener> listenerStack = new ConcurrentLinkedDeque<>();
    /**
     * keyEvent 阻塞点
     */
    private CrudeLinkedList<BooleanSupplier> keyEventBlocker = new CrudeLinkedList<>();
    private CrudeLinkedList<BooleanSupplier>.CrudeIter blockIter = keyEventBlocker.genCrudeIter();
    private Semaphore blockSp = new Semaphore(1);
    /**
     * 构造一个键盘堆栈控制器
     */
    public KeyControlStack() {
        Toolkit.getDefaultToolkit().addAWTEventListener(this,AWTEvent.KEY_EVENT_MASK);
    }

    @Override
    public void pushKeyListener(KeyListener keyListener) {
        listenerStack.push(keyListener);
    }

    @Override
    public KeyListener popKeyListener() {
        if(!listenerStack.isEmpty()) {
            return listenerStack.pop();
        } else {
            return null;
        }
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
        if(listener != null && isCurrentEventPassable()) {
            listener.keyTyped(e);
        }
    }
    
    private void onKeyPressed(KeyEvent e) {
        KeyListener listener = peekKeyListener();
        if(listener != null && isCurrentEventPassable()) {
            listener.keyPressed(e);
        }
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keyStatus.length) {
            keyStatus[e.getKeyCode()] = true;
        }
    }
    
    private void onKeyRelease(KeyEvent e) {
        KeyListener listener = peekKeyListener();
        if(listener != null && isCurrentEventPassable()) {
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

    @Override
    public void replaceKeyListener(KeyListener keyListener) {
        popKeyListener();
        pushKeyListener(keyListener);
    }
    
    private boolean isCurrentEventPassable() {
        boolean result = true;
        blockSp.acquireUninterruptibly();
        while(blockIter.hasNext()) {
            BooleanSupplier blocker = blockIter.next();
            if(blocker.getAsBoolean()) {
                result = false;
                break;
            }
        }
        blockIter.reset();
        blockSp.release();
        return result;
    }

    @Override
    public void addBlocker(BooleanSupplier blocker) {
        blockSp.acquireUninterruptibly();
        keyEventBlocker.add(blocker);
        blockSp.release();
    }
}
