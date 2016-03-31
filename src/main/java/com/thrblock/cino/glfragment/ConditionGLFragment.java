package com.thrblock.cino.glfragment;

public class ConditionGLFragment extends AbstractGLFragment {
	/** 
	* 叙述:获得终止条件，为true时终止执行<br />
	* @return boolean 代表是否终止执行的条件
	*/
	private IConditionFragment condition;
	
	public ConditionGLFragment(IConditionFragment condition) {
		this.condition = condition;
	}
	
	public boolean fragmentCondition() {
		return condition.fragment();
	}
	
	@Override
	public void fragment() {
		if(condition.fragment()) {
			destory();
		}
	}
}
