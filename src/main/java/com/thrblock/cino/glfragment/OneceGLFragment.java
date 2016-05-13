package com.thrblock.cino.glfragment;

public class OneceGLFragment implements IConditionFragment{
    private IPureFragment once;
    protected OneceGLFragment(IPureFragment once) {
        this.once = once;
    }
    @Override
    public boolean fragment() {
        once.fragment();
        return true;
    }
}
