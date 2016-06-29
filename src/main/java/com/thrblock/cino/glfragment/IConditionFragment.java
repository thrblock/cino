package com.thrblock.cino.glfragment;

/**
 * IConditionFragment 条件片段逻辑<br />
 * 条件判断逻辑会在每次片段执行后返回布尔值，并根据此值影响下次drawcall的执行
 * @author lizepu
 */
@FunctionalInterface
public interface IConditionFragment {
    /** 
    * 叙述:获得终止条件，为true时终止执行<br />
    * @return boolean 代表是否终止执行的条件
    */
    public boolean fragment();
}
