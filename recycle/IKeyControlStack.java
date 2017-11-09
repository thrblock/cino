package com.thrblock.cino.io;

import java.awt.event.KeyListener;
import java.util.function.BooleanSupplier;


/** 
* 类: IKeyControlStack <br />
* 叙述:键盘监听器栈<br />
* @author 三向板砖 thrblock badteeth@qq.com
* 注释日期 2016年3月20日 下午8:23:53 
*/
public interface IKeyControlStack {
	/**
	 * 将一个键盘监听器压入栈顶
	 * @param keyListener 键盘监听器实例
	 */
	public void pushKeyListener(KeyListener keyListener);
	/**
	 * 将栈顶的键盘监听器弹出
	 * @return 被弹出的键盘监听器
	 */
	public KeyListener popKeyListener();
	/**
	 * 将栈顶的键盘监听器替换
	 * @param keyListener 要替换的键盘监听器实例
	 */
	public void replaceKeyListener(KeyListener keyListener);
	/**
	 * 判断指定的keyCode是否处于按下状态，一般在帧绘制逻辑中使用
	 * @param code KeyCode 键盘码，参考ASCII
	 * @return 是否被按下的布尔值
	 */
	public boolean isKeyDown(int code);
	
	/**
	 * 增加一个忽略KeyEvent的条件
	 * @param blocker
	 */
	public void addBlocker(BooleanSupplier blocker);
}
