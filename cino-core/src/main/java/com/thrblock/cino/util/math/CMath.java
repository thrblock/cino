package com.thrblock.cino.util.math;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * 这里放置了一些数学相关方法<br />
 * 不推荐使用静态方法，必要时，会把特定用途的方法移植为OO格式 <br />
 * <img src=
 * "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE8AAABPCAMAAACd6mi0AAAA4VBMVEX///9bYn36umrZxdr16u1NTW8yK0nAdUv6uGT8165UW3ilqLUvJkVYXnknH0GOhpP78PNOVnTa2+B0bnv8uWGjkrD/v2nfyt/dxM9JSW1qaoRKWn6xtL9DQ2ifn66srLnh0ODas6E9PWW+b0LElmw2MU44OGHGhWOciaq4psD959D7wn7BdktXVXb0tGeAc4nHjn6GaWyebmA5Qm9oaIIaEzm7aDWwpLorK1qhhnZqZHjmo2D+8eMJADHasp/8tlfEdT65jWu/jl12Z3POztXq2+XFs8pAPlx5fpL7ypEdKmGPZ+BnAAACBElEQVRYhe3Xa1OCQBSA4ZIghDApwrA009JUTLvfs4uW9f9/UOcchd020xVtmoznE8LM67Liui4sxGKxKNa+F6lnlYvAFnhw7rITpXfqqWBRYMO5YqQB/s1eI0Kvs4MWDST28Jydx+umdC9fzgFLTDEqXr+U7+XwTkf08KbV8u/2PDZf/CQaW8A7meh+TZtFLhAdPx+jx73Ak3yPG1BGUTIKvT4q+b6/fOPqup5wQTtqTxn0/GVwkwg489kDwfzNopdF9KBc6YcgwdTR24Q99uBduYmv9Hrck+0Jqf5HI9Pj1wNk0UqtboVO0LtEietx65VFOZu9wZTr38/0GnRoRel5Qg9fe0YvG2ignHTPFJZ6awWkVtOoi1/o6gY4kO8ZYk/TNOgtgQKuN3FPomeEP5V8r1sAL1U0YY8eOlXsLYWDVOLez/c6edM0e/SUvJIpe9t2uBPa19C0PfYo/7sernpDe+lIPYto+wAHmmrSVgsNDibs9a1oA1xPmeNeCj+VM3DH92iXObq0llMDYjMrjqyFW9/zMb2idC/TKs24N+vxyfTKXsAe25O5XyvcnqxzrkGzUMjAr9pmqAX/vPwH2n+kR1dJ0nFDJVChs7W2E6Cdr3uOKjI9ne1q8T/MoOcIG16cRH8ueo4e4udP/4SmtiTTqyWZXURnb5MCvHK/K9GLxYb7ABnTZlY0pkoiAAAAAElFTkSuQmCC"/>
 * 
 * @author lizepu
 */
public class CMath {
    public static final float FLOAT_EQUAL_SENCITIVE = 1.4E-45f;

    private CMath() {
    }

    /**
     * float冗余相等性判断
     * Math.abs(a - b) < FLOAT_EQUAL_SENCITIVE;
     * @param a
     * @param b
     * @see CMath#FLOAT_EQUAL_SENCITIVE
     * @return 代表是否相等的布尔值
     */
    public static boolean floatEqual(float a, float b) {
        return Math.abs(a - b) < FLOAT_EQUAL_SENCITIVE;
    }

    /**
     * 以src点为原点，向量(1,0)沿+X轴至+Y轴方向旋转至目标点方向所需转动的角度
     * 
     * @param srcX
     *            原点横坐标
     * @param srcY
     *            原点纵坐标
     * @param tgtX
     *            目标点横坐标
     * @param tgtY
     *            目标点纵坐标
     * @return 所需旋转的方向角，单位弧度
     */
    public static float getQuadrantTheta(float srcX, float srcY, float tgtX, float tgtY) {
        float vecx = tgtX - srcX;
        float vecy = tgtY - srcY;
        double sqrt = Math.sqrt(vecx * vecx + vecy * vecy);
        double cos = sqrt <= FLOAT_EQUAL_SENCITIVE ? 1 : vecx / sqrt;
        if (cos > 1) {
            cos = 1;
        } else if (cos < -1) {
            cos = -1;
        }
        double theta = Math.acos(cos);
        if (tgtY > srcY) {
            return (float) theta;
        } else {
            return (float) (2 * Math.PI - theta);
        }
    }

    /**
     * 获得两点的距离
     * 
     * @param x1
     *            点1横坐标
     * @param y1
     *            点1纵坐标
     * @param x2
     *            点2横坐标
     * @param y2
     *            点2纵坐标
     * @return 两点距离
     */
    public static float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(getDistanceSqare(x1, y1, x2, y2));
    }

    /**
     * 获得两点的距离的平方
     * 
     * @param x1
     *            点1横坐标
     * @param y1
     *            点1纵坐标
     * @param x2
     *            点2横坐标
     * @param y2
     *            点2纵坐标
     * @return 两点距离的平方
     */
    public static float getDistanceSqare(float x1, float y1, float x2, float y2) {
        float s1 = x2 - x1;
        float s2 = y2 - y1;
        return s1 * s1 + s2 * s2;
    }

    /**
     * this is one of GLSL build-in function
     * 
     * @param min
     * @param max
     * @param value
     *            return value < min?min:(value > max?max:value);
     * @return
     */
    public static float clamp(float value, float min, float max) {
        float m = value > max ? max : value;
        return value < min ? min : m;
    }

    /**
     * this is one of GLSL build-in function
     * 
     * @param x
     * @param y
     * @param a
     * @return Returns the linear blend of x and y, i.e., x⋅(1−a)+ y⋅a
     */
    public static float mix(float x, float y, float a) {
        return x * (1 - a) + y * a;
    }

    /**
     * this is one of GLSL build-in function
     * 
     * @param edge
     * @param x
     * @return Returns 0.0 if x < edge; otherwise it returns 1.0.
     */
    public static float step(float edge, float x) {
        return x < edge ? 0 : 1f;
    }

    /**
     * this is one of GLSL build-in function
     * 
     * @param edge0
     * @param edge1
     * @param x
     * @return Returns 0.0 if x <= edge0 and 1.0 if x >= edge1 and performs
     *         smooth Hermite interpolation between 0 and 1 when edge0 < x <
     *         edge1. This is useful in cases where you would want a threshold
     *         function with a smooth transition. This is equivalent to: genType
     *         t; t = clamp ((x – edge0) / (edge1 – edge0), 0, 1); return t * t
     *         * (3 – 2 * t); Results are undefined if edge0 >= edge1.
     */
    public static float smoothstep(float edge0, float edge1, float x) {
        float t = clamp((x - edge0) / (edge1 - edge0), 0, 1);
        return t * t * (3 - 2 * t);
    }
    
    public static <A, B> Stream<SimpleEntry<A, B>> cartesianProduct(Collection<A> as, Collection<B> bs) {
        return as.stream().flatMap(a -> bs.stream().map(b -> new SimpleEntry<>(a, b)));
    }
}
