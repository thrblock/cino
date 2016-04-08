package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FragmentBuilder {
	@Autowired
	private IGLFragmentContainer container;
	
	public ProxyGLFragment buildProxyFragment(IPureFragment pure) {
		ProxyGLFragment result = new ProxyGLFragment(pure);
		container.addFragment(result);
		return result;
	}
	
	public AutoMachine buildAutoMachine() {
		AutoMachine result = new AutoMachine();
		container.addFragment(result);
		return result;
	}
	
	public ConditionGLFragment buildConditionGLFragment(IConditionFragment condition) {
		ConditionGLFragment result = new ConditionGLFragment(condition);
		container.addFragment(result);
		return result;
	}
	
	public LinkedGLFragment buildLinkedGLFragment() {
		LinkedGLFragment result = new LinkedGLFragment();
		container.addFragment(result);
		return result;
	}
	
	public LoopedGLFragment buildLoopedGLFragment() {
		LoopedGLFragment result = new LoopedGLFragment();
		container.addFragment(result);
		return result;
	}
	
	public ConditionGLFragment buildOneceGLFragment(IPureFragment pure) {
		OneceGLFragment result = new OneceGLFragment(pure);
		return buildConditionGLFragment(result);
	}
}
