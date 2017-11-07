package com.thrblock.cino.util.structure;

import java.util.function.Supplier;

public class SupplierFactory {
    private SupplierFactory() {
    }
    
    public static <T> Supplier<T> cycleArray(T[] arr) {
        return new Supplier<T>(){
            private int i = 0;
            @Override
            public T get() {
                T result = arr[i];
                i = (i + 1) % arr.length;
                return result;
            }
        };
    }
    
    public static Supplier<Double> cycleOfSin(int accur) {
        double start = 0;
        double step = 1.0 / accur;
        Double[] arr = new Double[accur];
        for(int i = 0;i < arr.length;i++) {
            arr[i] = Math.sin(start + step * i);
        }
        return cycleArray(arr);
    }
    
    public static Supplier<Double> cycleOfCos(int accur) {
        double start = 0;
        double step = 1.0 / accur;
        Double[] arr = new Double[accur];
        for(int i = 0;i < arr.length;i++) {
            arr[i] = Math.cos(start + step * i);
        }
        return cycleArray(arr);
    }
}
