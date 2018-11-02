package com.thrblock.ui;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

public abstract class AbstractUIComponent {
	protected List<AbstractUIComponent> childList = new LinkedList<>();
	protected int x;
	protected int y;
	protected int width = 100;
	protected int height = 100;
	public void setBounds(int x,int y,int width,int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		update();
	}
	/**
	 * 当范围参数变化时 更新
	 */
	public void update(){
	}
	/**
	 * 初始化组件
	 */
	@PostConstruct
	public abstract void init();
	/**
	 * 显示组件
	 */
	public abstract void show();
	/**
	 * 隐藏组件
	 */
	public abstract void hide();
	/**
	 * 销毁组件
	 */
	public abstract void destory();
	
	/**
	 * 将组件设定为该组件的子成员，被添加的组件 其显示、隐藏、销毁操作由父级组件控制
	 * @param comp 托管的子组件
	 */
	protected void add(AbstractUIComponent comp) {
		childList.add(comp);
	}
}
