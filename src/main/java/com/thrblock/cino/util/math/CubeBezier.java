package com.thrblock.cino.util.math;

import com.thrblock.cino.util.structure.Point2D;

/**
 * 三次贝塞尔曲线工具类 用来解决一般情况下非线性逻辑到帧的映射
 * 
 * @author thrblock
 *
 */
public class CubeBezier {
    private final Point2D start;
    private final Point2D end;
    private final Point2D c1;
    private final Point2D c2;

    /**
     * 以4点构造三次贝塞尔曲线
     * 
     * @param start
     * @param end
     * @param c1
     * @param c2
     */
    public CubeBezier(Point2D start, Point2D end, Point2D c1, Point2D c2) {
        this.start = start;
        this.end = end;
        this.c1 = c1;
        this.c2 = c2;
    }

    /**
     * 以俩点构造贝塞尔曲线
     * 
     * @param c1
     * @param c2
     */
    public CubeBezier(Point2D c1, Point2D c2) {
        this(new Point2D(0f, 0f), new Point2D(1f, 1f), c1, c2);
    }

    /**
     * @param t
     * @return
     */
    public float bezierX(float input) {
        float t = CMath.clamp(input, 0f, 1f);
        return start.getX() * pow(t, 3) 
                + 3 * c1.getX() * t * pow(1 - t, 2)
                + 3 * c2.getX() * pow(t, 2) * (1 - t)
                + end.getX() * pow(t, 3);
    }
    
    public float bezierY(float input) {
        float t = CMath.clamp(input, 0f, 1f);
        return start.getY() * pow(t, 3) 
                + 3 * c1.getY() * t * pow(1 - t, 2)
                + 3 * c2.getY() * pow(t, 2) * (1 - t)
                + end.getY() * pow(t, 3);
    }

    public float pow(float src, int time) {
        return (float) Math.pow(src, time);
    }

    public Point2D getStart() {
        return start;
    }
    
    public Point2D getEnd() {
        return end;
    }
    /**
     * 计算给定点的贝塞尔曲线值
     * 
     * @param result
     *            计算结果容器
     * @param t
     *            input
     */
    public void computeBezier(Point2D result, float t) {
        float input = CMath.clamp(t, 0f, 1f);

        float cx = 3f * (c1.getX() - start.getX());
        float bx = 3f * (c2.getX() - c1.getX()) - cx;
        float ax = end.getX() - start.getX() - cx - bx;
        float cy = 3f * (c1.getY() - start.getY());
        float by = 3f * (c2.getY() - c1.getY()) - cy;
        float ay = end.getY() - start.getY() - cy - by;

        float tSquared = input * input;
        float tCubed = tSquared * input;
        float resultx = (ax * tCubed) + (bx * tSquared) + (cx * t) + start.getX();
        float resulty = (ay * tCubed) + (by * tSquared) + (cy * t) + start.getY();
        result.setX(resultx);
        result.setY(resulty);
    }
}
