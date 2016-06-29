package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Automata 有限状态自动机的片段逻辑实现<br />
 * 一个Automata以一个IStatusGLFragment作为运行开端，并以某个特定状态或null作为停机标识。<br />
 * @author lizepu
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Automata extends AbstractGLFragment {
    private IStatusGLFragment current;
    protected Automata() {
    }
    /**
     * 输入自动机起始逻辑并启动
     * @param fragment 起始逻辑
     */
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
