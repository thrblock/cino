package com.thrblock.cino.concept;

import com.thrblock.cino.util.math.CMath;
import com.thrblock.cino.vec.Vec2;

public class Line extends MultiPoint {

    public Line(Point[] multi) {
        super(multi);
    }

    public Line(Vec2 start, Vec2 end) {
        super(new Point(start), new Point(end));
    }

    @Override
    public float getRadian() {
        return CMath.getQuadrantTheta(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY());
    }
}
