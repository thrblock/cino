package com.thrblock.cino.shader.data;

public class GLUniformFloat {
    private final String name;
    private float value;

    public GLUniformFloat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
