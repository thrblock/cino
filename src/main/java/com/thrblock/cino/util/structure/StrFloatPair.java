package com.thrblock.cino.util.structure;

/**
 * 字符串-浮点对
 * @author thrblock
 *
 */
public class StrFloatPair {
    private final String key;
    private float value;

    /**
     * 构造字符串-浮点对
     * @param key
     */
    public StrFloatPair(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
