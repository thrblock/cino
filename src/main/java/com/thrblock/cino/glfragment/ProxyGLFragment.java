package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class ProxyGLFragment extends AbstractGLFragment {
    private IPureFragment pureFragment;

    protected ProxyGLFragment() {
    }

    protected ProxyGLFragment(IPureFragment pureFragment) {
        this.pureFragment = pureFragment;
    }

    public void setFragment(IPureFragment pureFragment) {
        this.pureFragment = pureFragment;
    }

    @Override
    public void fragment() {
        pureFragment.fragment();
    }
}
