package com.thrblock.cino.glfragment;

public class AutoMachine extends AbstractGLFragment {
	@FunctionalInterface
	public static interface IStatusGLFragment {
		public IStatusGLFragment statusFragment();
	}
	private IStatusGLFragment current;
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
