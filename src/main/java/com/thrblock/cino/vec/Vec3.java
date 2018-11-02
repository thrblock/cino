package com.thrblock.cino.vec;

import java.util.Arrays;

public class Vec3 {
    float[] value = new float[3];

    public Vec3(float a) {
        Arrays.fill(value, a);
    }

    public Vec3(float a, float b, float c) {
        value[0] = a;
        value[1] = b;
        value[2] = c;
    }

    public Vec3(Vec2 v, float f) {
        System.arraycopy(v.value, 0, value, 0, v.value.length);
        value[value.length - 1] = f;
    }

    public Vec3(float f, Vec2 v) {
        System.arraycopy(v.value, 0, value, 1, v.value.length);
        value[0] = f;
    }

    public Vec3(Vec3 v) {
        System.arraycopy(v.value, 0, value, 0, value.length);
    }

    public Vec3(Vec4 v) {
        System.arraycopy(v.value, 0, value, 0, value.length);
    }

    public float getX() {
        return value[0];
    }

    public float getY() {
        return value[1];
    }

    public float getZ() {
        return value[2];
    }

    public void setX(float x) {
        value[0] = x;
    }

    public void setY(float y) {
        value[1] = y;
    }

    public void setZ(float z) {
        value[2] = z;
    }

    public float getR() {
        return value[0];
    }

    public float getG() {
        return value[1];
    }

    public float getB() {
        return value[2];
    }

    public void setR(float r) {
        value[0] = r;
    }

    public void setG(float g) {
        value[1] = g;
    }

    public void setB(float b) {
        value[2] = b;
    }

    public Vec3 getRgb() {
        return new Vec3(this);
    }

    public void setRgb(Vec3 rgb) {
        System.arraycopy(rgb.value, 0, value, 0, rgb.value.length);
    }

    public Vec2 getXy() {
        return new Vec2(this);
    }

    public void setXy(Vec2 xy) {
        System.arraycopy(xy.value, 0, value, 0, xy.value.length);
    }
    
    @Override
    public String toString() {
        return "Vec3" + Arrays.toString(value);
    }
}
