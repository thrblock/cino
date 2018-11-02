package com.thrblock.cino.vec;

import java.util.Arrays;

public class Vec4 {
    float[] value = new float[4];

    public Vec4(float a) {
        Arrays.fill(value, a);
    }

    public Vec4(float a, float b, float c, float d) {
        value[0] = a;
        value[1] = b;
        value[2] = c;
        value[3] = d;
    }

    public Vec4(Vec3 v, float f) {
        System.arraycopy(v.value, 0, value, 0, v.value.length);
        value[value.length - 1] = f;
    }

    public Vec4(float f, Vec3 v) {
        System.arraycopy(v.value, 0, value, 1, v.value.length);
        value[0] = f;
    }

    public Vec4(Vec2 a, Vec2 b) {
        System.arraycopy(a.value, 0, value, 0, a.value.length);
        System.arraycopy(b.value, a.value.length, value, 0, b.value.length);
    }

    public Vec4(Vec4 v) {
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

    public float getW() {
        return value[3];
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

    public void setW(float w) {
        value[3] = w;
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

    public float getA() {
        return value[3];
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

    public void setA(float a) {
        value[3] = a;
    }

    public Vec4 getRgba() {
        return new Vec4(this);
    }

    public void setRgba(Vec4 rgba) {
        System.arraycopy(rgba.value, 0, value, 0, value.length);
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
        return "Vec4" + Arrays.toString(value);
    }
}
