package com.thrblock.cino.glfragment;

/**
 * IStatusGLFragment 自动机状态逻辑 包括了此状态下应执行的逻辑及下次片段应运行的状态逻辑实例<br />
 * 函数式接口，尽可能使用lambda进行构造
 * @author lizepu
 */
@FunctionalInterface
public interface IStatusGLFragment {
    /**
     * 状态逻辑，当绘制结束后进行执行，并返回下次绘制结束应执行的片段逻辑
     * @return 下次绘制结束应执行的片段逻辑 或 null 进行停机
     */
    public IStatusGLFragment statusFragment();
}
