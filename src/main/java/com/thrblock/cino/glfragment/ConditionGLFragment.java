package com.thrblock.cino.glfragment;

public abstract class ConditionGLFragment extends AbstractGLFragment {
	/** 
	* 叙述:获得终止条件，为true时终止执行<br />
	* @return boolean 代表是否终止执行的条件
	*/
	public abstract boolean fragmentCondition();
	@Override
	public void fragment() {
		if(fragmentCondition()) {
			destory();
		}
	}
}
