package com.thrblock.cino.util.structure;

import java.util.function.Supplier;

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

    public static Supplier<Point2D> beizerSmooth(float x1, float y1, float x2, float y2, int step) {
        return beizerSmooth(new Point2D(x1, y1), new Point2D(x2, y2), step);
    }

    public static Supplier<Point2D> beizerSmooth(Point2D start, Point2D end, int step) {
        CubeBezier bezier = new CubeBezier(start, end, start, end);
        Point2D[] result = new Point2D[step];
        for (int i = 1; i <= result.length; i++) {
            result[i - 1] = new Point2D(bezier.bezierX(i / (float) step), bezier.bezierY(i / (float) step));
        }
        return cycleArray(result);
    }

}
