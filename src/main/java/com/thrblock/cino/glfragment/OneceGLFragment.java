package com.thrblock.cino.glfragment;

public class OneceGLFragment implements IConditionFragment{
	private IOneceFragment once;
	public OneceGLFragment(IOneceFragment once) {
		this.once = once;
	}
	@Override
	public boolean fragment() {
		once.fragment();
		return true;
	}
}
