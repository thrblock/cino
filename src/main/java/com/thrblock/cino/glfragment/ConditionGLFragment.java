package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ConditionGLFragment 基于条件的片段逻辑实现<br />
 * @author lizepu
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class ConditionGLFragment extends AbstractGLFragment {
    private IConditionFragment condition;
    protected ConditionGLFragment(){
    }
    
    protected ConditionGLFragment(IConditionFragment condition) {
        this.condition = condition;
    }

    /**
     * 设置当前的条件逻辑
     * @param condition 条件片段逻辑
     */
    public void setFragment(IConditionFragment condition) {
        this.condition = condition;
    }
    
    /**
     * 单纯执行条件逻辑的内容
     * @return 条件逻辑返回结果
     */
    public boolean fragmentCondition() {
        return condition.fragment();
    }
    
    @Override
    public void fragment() {
        if(condition.fragment()) {
            disable();
        }
    }
}
