package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ProxyGLFragment 代理片段逻辑，可使用函数式的纯逻辑构造
 * @author lizepu
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class ProxyGLFragment extends AbstractGLFragment {
    private IPureFragment pureFragment;

    protected ProxyGLFragment() {
    }

    protected ProxyGLFragment(IPureFragment pureFragment) {
        this.pureFragment = pureFragment;
    }

    /**
     * 为此代理逻辑提供纯内容
     * @param pureFragment 纯粹的执行内容
     */
    public void setFragment(IPureFragment pureFragment) {
        this.pureFragment = pureFragment;
    }

    @Override
    public void fragment() {
        pureFragment.fragment();
    }
}
