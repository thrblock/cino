package com.thrblock.cino.function;

/**
 * 输入float并得到float的方法
 * @author lizepu
 */
@FunctionalInterface
public interface FloatFunction {
    /**
     * 根据输入得到结果
     * @param v 输入
     * @return 结果
     */
    float apply(float v);
}
