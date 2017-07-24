package com.thrblock.cino.function;

/**
 * 虚空消费者
 * @author thrblock
 *
 */
@FunctionalInterface
public interface VoidConsumer {
    /**
     * 消费空参
     */
    public void accept();
}
