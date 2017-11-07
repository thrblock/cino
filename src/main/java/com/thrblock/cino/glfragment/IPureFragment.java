package com.thrblock.cino.glfragment;

import java.util.function.BooleanSupplier;

/**
 * 一个纯粹的片段逻辑 函数式接口
 * 
 * @author lizepu
 */
@FunctionalInterface
public interface IPureFragment {
    /**
     * 执行逻辑内容
     */
    public void fragment();

    public default IPureFragment mergeCondition(BooleanSupplier condition) {
        return () -> {
            if (condition.getAsBoolean()) {
                fragment();
            }
        };
    }
}
