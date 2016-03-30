package com.thrblock.cino.glfragment;

public abstract class OneceGLFragment extends ConditionGLFragment {
	public abstract void fragmentOnce();
	@Override
	public boolean fragmentCondition() {
		fragmentOnce();
		return true;
	}
}
