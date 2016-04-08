package com.thrblock.cino.glfragment;

public class ProxyGLFragment extends AbstractGLFragment {
	private IPureFragment pureFragment;
	protected ProxyGLFragment(IPureFragment pureFragment){
		this.pureFragment = pureFragment;
	}
	@Override
	public void fragment() {
		pureFragment.fragment();
	}
}
