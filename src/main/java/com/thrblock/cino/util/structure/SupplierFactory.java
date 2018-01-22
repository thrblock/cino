package com.thrblock.cino.util.structure;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import com.thrblock.cino.glshape.GLShape;
import com.thrblock.cino.util.math.CubeBezier;

public class SupplierFactory {
    private SupplierFactory() {
    }

    public static <T> Supplier<T> cycleArray(T[] arr) {
        return new Supplier<T>() {
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
        double step = Math.PI * 2 / accur;
        Double[] arr = new Double[accur];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.sin(start + step * i);
        }
        return cycleArray(arr);
    }

    public static Supplier<Double> cycleOfCos(int accur) {
        double start = 0;
        double step = Math.PI * 2 / accur;
        Double[] arr = new Double[accur];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.cos(start + step * i);
        }
        return cycleArray(arr);
    }

    public BooleanSupplier centralMotion(GLShape shape, CubeBezier bezier, int time) {
        float[] xs = new float[time + 1];
        float[] ys = new float[time + 1];
        float add = 1f / time;
        for (int i = 0; i < time; i++) {
            xs[i] = bezier.bezierX(add * i);
            ys[i] = bezier.bezierY(add * i);
        }
        xs[time] = bezier.bezierX(1.0f);
        ys[time] = bezier.bezierY(1.0f);

        return new BooleanSupplier() {
            private int index = 0;
            @Override
            public boolean getAsBoolean() {
                if (index < xs.length) {
                    shape.setCentralX(xs[index]);
                    shape.setCentralY(ys[index]);
                    index++;
                    return false;
                } else {
                    index = 0;
                    return true;
                }
            }
        };
    }
}
