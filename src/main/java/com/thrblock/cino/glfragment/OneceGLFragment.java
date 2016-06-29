package com.thrblock.cino.glfragment;

/**
 * OneceGLFragment 单次条件逻辑<br />
 * 此逻辑在执行一次后必定返回false
 * @author lizepu
 *
 */
public class OneceGLFragment implements IConditionFragment{
    private IPureFragment once;
    protected OneceGLFragment(IPureFragment once) {
        this.once = once;
    }
    @Override
    public final boolean fragment() {
        once.fragment();
        return true;
    }
}
