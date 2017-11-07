package com.thrblock.cino.glfragment;

/**
 * 此结构永久执行
 * @author user
 *
 */
public class ForeverFragment implements IGLFragment {
    protected IPureFragment pure;
    /**
     * 构造执行片段
     * @param pure
     */
    public ForeverFragment(IPureFragment pure) {
        this.pure = pure;
    }
    
    @Override
    public void fragment() {
        pure.fragment();
    }

    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public boolean isDestory() {
        return false;
    }

}
