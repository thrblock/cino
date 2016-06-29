package com.thrblock.cino.function;

/**
 * 基础类型float的生产者<br />
 * 函数式接口 - 参照java.util.function.DoubleSupplier进行等价设计
 * @author lizepu
 */
@FunctionalInterface
public interface FloatSupplier {
    /**
     * 获得一个float值
     * @return 生产出的float
     */
    public float get();
}
