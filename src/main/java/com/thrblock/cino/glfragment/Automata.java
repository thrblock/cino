package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Automata extends AbstractGLFragment {
	@FunctionalInterface
	public static interface IStatusGLFragment {
		public IStatusGLFragment statusFragment();
	}
	private IStatusGLFragment current;
	protected Automata() {
	}
	public void enableWith(IStatusGLFragment fragment){
		this.current = fragment;
		enable();
	}
	@Override
	public void fragment() {
		if(current != null) {
			current = current.statusFragment();
		} else {
			disable();
		}
	}
}
