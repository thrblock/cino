package com.thrblock.cino.lintersection;

public class InstersectionResultHolder<T extends AbstractVector> {
    private boolean intersect;
    private float x;
    private float y;
    private T insterLine;

    public boolean isIntersect() {
        return intersect;
    }

    protected void setIntersect(boolean intersect) {
        this.intersect = intersect;
    }

    public T getInsterLine() {
        return insterLine;
    }

    protected void setInsterLine(T insterLine) {
        this.insterLine = insterLine;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
