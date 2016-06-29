package com.thrblock.cino.glfragment;

/**
 * 片段逻辑容器的抽象层，提供执行全部逻辑以及增加逻辑的方法
 * @author lizepu
 */
public interface IGLFragmentContainer {
    /**
     * 运行全部片段逻辑，应在每次绘制完成后运行
     */
    public void allFragment();
    /**
     * 增加一个片段逻辑，会在下次绘制完后开始运行
     * @param frag 片段逻辑
     */
    public void addFragment(IGLFragment frag);
}
