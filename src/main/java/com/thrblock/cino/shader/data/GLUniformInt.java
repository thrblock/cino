package com.thrblock.cino.shader.data;

public class GLUniformInt {
    private final String name;
    private int value;

    public GLUniformInt(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
