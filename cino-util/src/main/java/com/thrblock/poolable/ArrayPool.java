package com.thrblock.poolable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ArrayPool<T extends Poolable> {
    private ArrayList<T> lst;
    private int crt = -1;
    private Supplier<T> generator;
    public ArrayPool() {
        this(2 << 8);
    }

    public ArrayPool(int initSize) {
        lst = new ArrayList<>(initSize);
    }
    
    public ArrayPool(int initSize,Supplier<T> generator) {
        this(initSize,generator,true);
    }
    
    public ArrayPool(int initSize,Supplier<T> generator,boolean createWhenInsufficient) {
        this(initSize);
        Stream.iterate(0,i -> i + 1).limit(initSize).forEach(e -> addPoolable(generator.get()));
        if(createWhenInsufficient) {
            this.generator = generator;
        }
    }

    public void addPoolable(T poolable) {
        lst.add(poolable);
        if (crt == -1) {
            crt = 0;
        }
    }

    public T getAvailable() {
        T result = null;
        if (crt != -1) {
            for (int count = 0; count < lst.size(); count++) {
                T current = lst.get(crt);
                if (current.isAvailable()) {
                    result = current;
                    break;
                }
                crt = (crt + 1) % lst.size();
            }
        }
        if(result == null && generator != null) {
            result = generator.get();
            addPoolable(result);
        }
        return result;
    }

    public T getUnAvailable() {
        T result = null;
        if (crt != -1) {
            for (int count = 0; count < lst.size(); count++) {
                T current = lst.get(crt);
                if (!current.isAvailable()) {
                    result = current;
                    break;
                }
                crt = (crt + 1) % lst.size();
            }
        }
        return result;
    }

    public void interruptAll() {
        lst.forEach(Poolable::interrupt);
    }

    public void empty() {
        lst.clear();
        crt = -1;
    }

    public List<T> snapShot() {
        return new LinkedList<>(lst);
    }
}
