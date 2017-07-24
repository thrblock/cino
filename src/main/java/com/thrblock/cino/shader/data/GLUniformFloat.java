package com.thrblock.cino.shader.data;

/**
 * GLSL uniform float变量
 * @author thrblock
 */
public class GLUniformFloat {
    private final String name;
    private float value;

    /**
     * 构造 GLSL uniform 变量
     * @param name 对应shader中的变量名称
     */
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
