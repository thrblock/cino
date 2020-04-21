package com.thrblock.cino.vec;

import java.util.Arrays;

import lombok.Data;

@Data
public class Vec2 {
    float[] value = new float[2];

    public Vec2(float a) {
        Arrays.fill(value, a);
    }

    public Vec2(float a, float b) {
        value[0] = a;
        value[1] = b;
    }

    public Vec2(Vec2 v) {
        System.arraycopy(v.value, 0, value, 0, value.length);
    }

    public Vec2(Vec3 v) {
        System.arraycopy(v.value, 0, value, 0, value.length);
    }

    public Vec2(Vec4 v) {
        System.arraycopy(v.value, 0, value, 0, value.length);
    }

    public float getX() {
        return value[0];
    }

    public float getY() {
        return value[1];
    }

    public void setX(float x) {
        value[0] = x;
    }

    public void setY(float y) {
        value[1] = y;
    }

    public Vec2 getXy() {
        return new Vec2(this);
    }

    public void setXy(Vec2 xy) {
        System.arraycopy(xy.value, 0, value, 0, xy.value.length);
    }

    @Override
    public String toString() {
        return "Vec2" + Arrays.toString(value);
    }
}
