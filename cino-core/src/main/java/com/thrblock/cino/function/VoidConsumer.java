package com.thrblock.cino.function;

/**
 * 虚空消费者 此结构形同Runnable 使用此结构的目的是为了克服将Runnable与多线程进行潜意识上的关联
 * 
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
