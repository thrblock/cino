package com.thrblock.cino.function;
/**
 * 输入char并得到指定结果的方法
 * @param <R> 期待的返回值结果
 * @author lizepu
 */
@FunctionalInterface
public interface CharFunction<R> {

    /**
     * 根据输入得到结果
     * @param v 输入
     * @return 结果
     */
    R apply(char value);
}
