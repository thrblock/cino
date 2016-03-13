package com.thrblock.cino.glfragment;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.CinoInitor;

public abstract class AbstractGLFragment implements IGLFragment {
	private IGLFragmentContainer container;
	public AbstractGLFragment(){
		AbstractApplicationContext context = CinoInitor.getCinoContext();
		container = context.getBean(IGLFragmentContainer.class);
		container.addFragment(this);
	}
}
