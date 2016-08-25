package com.thrblock.cino.function;

/**
 * 基础类型float的消费者
 * @author lizepu
 */
@FunctionalInterface
public interface FloatConsumer {
    /**
     * 消费一个float
     * @param v 要消费的float值
     */
    void accept(float v);
}
