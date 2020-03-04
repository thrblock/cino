package com.thrblock.cino.function;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CFunctions {
    private CFunctions() {
    }

    public static <T> Predicate<T> alwaysFalse() {
        return x -> false;
    }

    public static <T> Predicate<T> alwaysTrue() {
        return x -> true;
    }

    public static Runnable doNothing() {
        return () -> {
        };
    }

    public static <T> Function<T, T> identity() {
        return Function.identity();
    }

    public static <T> Supplier<T> use(T obj) {
        return () -> obj;
    }

    public static <T> BinaryOperator<T> firstOne() {
        return (x, y) -> x;
    }
    
    public static <T> BinaryOperator<T> secondOne() {
        return (x, y) -> y;
    }
}
