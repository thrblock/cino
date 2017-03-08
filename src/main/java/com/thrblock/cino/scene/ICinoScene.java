package com.thrblock.cino.scene;

import java.awt.event.KeyListener;

public interface ICinoScene extends KeyListener {
	/** 
	* 叙述:本场景进入栈顶时调用
	*/
	public void enable();
	/** 
	* 叙述:本场景从栈中弹出时调用
	*/
	public void destory();
	
	/** 
	* 叙述:本场景被其它场景覆盖时调用
	*/
	public void covered();
	/** 
	* 叙述:因其它场景出栈，本场景恢复为栈顶时调用
	*/
	public void recover();
}
