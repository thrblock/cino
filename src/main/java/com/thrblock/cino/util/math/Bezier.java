package com.thrblock.cino.util.math;

import com.thrblock.cino.util.structure.Point2D;

public class Bezier {
    private final Point2D start;
    private final Point2D end;
    private final Point2D c1;
    private final Point2D c2;

    public Bezier(Point2D c1, Point2D c2) {
        this.start = new Point2D(0f, 0f);
        this.c1 = c1;
        this.c2 = c2;
        this.end = new Point2D(1f, 1f);
    }

    public void computeBezier(Point2D result,float t) {
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
