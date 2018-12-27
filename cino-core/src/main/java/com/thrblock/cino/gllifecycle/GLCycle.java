package com.thrblock.cino.gllifecycle;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public class GLCycle<T> {

    IntFunction<T[]> arraySupplier;
    T[] cache;

    Semaphore operationSp = new Semaphore(1);
    Set<Consumer<Set<T>>> operations = new LinkedHashSet<>();

    public GLCycle(IntFunction<T[]> supplier) {
        this.arraySupplier = supplier;
        this.cache = arraySupplier.apply(0);
    }

    public void safeAdd(T t) {
        safeOperation(s -> s.add(t));
    }

    public void safeRemove(T t) {
        safeOperation(s -> s.remove(t));
    }

    public void safeUpdate(Consumer<T> cons) {
        safeOperation(s -> s.forEach(cons));
    }

    public void safeOperation(Consumer<Set<T>> op) {
        operationSp.acquireUninterruptibly();
        operations.add(op);
        operationSp.release();
    }

    public T[] safeHold() {
        return safeHold(false);
    }

    public T[] safeHold(boolean clear) {
        if (!operations.isEmpty()) {
            operationSp.acquireUninterruptibly();
            LinkedHashSet<T> reBuild = new LinkedHashSet<>(Arrays.asList(cache));
            operations.forEach(c -> c.accept(reBuild));
            T[] result = reBuild.toArray(arraySupplier.apply(reBuild.size()));
            operations.clear();
            if (clear) {
                this.cache = arraySupplier.apply(0);
            } else {
                this.cache = result;
            }
            operationSp.release();
            return result;
        } else {
            return cache;
        }
    }
}
