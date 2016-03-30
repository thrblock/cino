package com.thrblock.cino.io;

import java.awt.event.KeyListener;


/** 
* 类: IKeyControlStack <br />
* 叙述:键盘监听器栈<br />
* @author 三向板砖 thrblock badteeth@qq.com
* 注释日期 2016年3月20日 下午8:23:53 
*/
public interface IKeyControlStack {
	public void pushKeyListener(KeyListener keyListener);
	public KeyListener popKeyListener();
	public void replaceKeyListener(KeyListener keyListener);
	public boolean isKeyDown(int code);
}
