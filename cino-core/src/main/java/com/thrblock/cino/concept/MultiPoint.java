package com.thrblock.cino.concept;

public abstract class MultiPoint extends GeometricConcept {
    protected Point[] points;

    public MultiPoint(Point... multi) {
        this.points = multi;
    }

    public Point[] getPoints() {
        return points;
    }

    @Override
    public void setRadian(float radian) {
        selfRevolve(radian);
    }

    public void revolve(float cx, float cy, float radianOffset) {
        for (int i = 0; i < points.length; i++) {
            points[i].revolve(cx, cy, radianOffset);
        }
        this.radian += radianOffset;
    }

    public void selfRevolve(float dstRadian) {
        float offset = dstRadian - radian;
        revolve(getCentralX(), getCentralY(), offset);
    }

    public void setCentralX(float x) {
        setXOffset(x - getCentralX());
    }

    public float getCentralX() {
        float result = 0;
        for (int i = 0; i < points.length; i++) {
            result += points[i].getX();
        }
        return result / points.length;
    }

    public void setXOffset(float offset) {
        for (int i = 0; i < points.length; i++) {
            points[i].setXOffset(offset);
        }
    }

    public void setCentralY(float y) {
        setYOffset(y - getCentralY());
    }

    public float getCentralY() {
        float result = 0;
        for (int i = 0; i < points.length; i++) {
            result += points[i].getY();
        }
        return result / points.length;
    }

    public void setYOffset(float offset) {
        for (int i = 0; i < points.length; i++) {
            points[i].setYOffset(offset);
        }
    }

    public void sameCentralOf(MultiPoint m) {
        setCentralX(m.getCentralX());
        setCentralY(m.getCentralY());
    }

    public void sameStatusOf(MultiPoint m) {
        sameCentralOf(m);
        setRadian(m.radian);
    }

}
