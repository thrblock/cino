package com.thrblock.cino.glfragment;

/**
 * GL draw call片段，每次绘制后的执行逻辑
 * @author lizepu
 */
public interface IGLFragment {
    /**
     * 插入在每次OpenGL绘制结束后的逻辑
     */
    public void fragment();
    /**
     * 是否启用此逻辑
     * @return true 则每次绘制后调用fragment
     */
    public boolean isEnable();
    /**
     * 是否销毁此逻辑
     * @return true 则在绘制完成后将此片段逻辑永久移出执行队列
     */
    public boolean isDestory();
}