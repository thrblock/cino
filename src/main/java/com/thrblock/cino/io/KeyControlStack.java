package com.thrblock.cino.io;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

public class KeyControlStack implements KeyEventDispatcher{
	Stack<KeyListener> listenerStack = new Stack<>();
	
	public void pushKeyListener(KeyListener keyListener) {
		listenerStack.push(keyListener);
	}
	
	public KeyListener popKeyListener() {
		return listenerStack.pop();
	}
	
	public KeyListener peekKeyListener() {
		return listenerStack.peek();
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		return false;
	}
}
