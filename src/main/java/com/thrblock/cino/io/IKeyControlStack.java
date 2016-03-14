package com.thrblock.cino.io;

import java.awt.event.KeyListener;

public interface IKeyControlStack {
	public void pushKeyListener(KeyListener keyListener);
	public KeyListener popKeyListener();
	public boolean isKeyDown(int keyCode);
}
