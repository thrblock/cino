package com.thrblock.cino.shader.data;

/**
 * GLSL uniform int 变量
 * @author thrblock
 */
public class GLUniformInt {
    private final String name;
    private int value;

    /**
     * 构造 GLSL uniform int 变量
     * @param name 对应shader中的变量名
     */
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
