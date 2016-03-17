package com.thrblock.cino.glfragment;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.CinoInitor;

public abstract class AbstractGLFragment implements IGLFragment {
	private IGLFragmentContainer container;
	private boolean enable = false;
	private boolean destory = false;
	public AbstractGLFragment(){
		AbstractApplicationContext context = CinoInitor.getCinoContext();
		container = context.getBean(IGLFragmentContainer.class);
		container.addFragment(this);
	}
	@Override
	public boolean isDestory() {
		return destory;
	}
	@Override
	public boolean isEnable() {
		return enable;
	}
	public void enable() {
		this.enable = true;
	}
	public void disable() {
		this.enable = false;
	}
	public void destory() {
		this.destory = true;
	}
}
