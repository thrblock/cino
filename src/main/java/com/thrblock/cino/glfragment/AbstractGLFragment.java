package com.thrblock.cino.glfragment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGLFragment implements IGLFragment {
	private boolean enable = false;
	private boolean destory = false;
	
	@Autowired
    private IGLFragmentContainer container;
	
    @PostConstruct
    private void addToContainer() {
        container.addFragment(this);
    }
    
	protected AbstractGLFragment(){
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
