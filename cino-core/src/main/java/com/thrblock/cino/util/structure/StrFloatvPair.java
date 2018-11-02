package com.thrblock.cino.util.structure;

/**
 * 字符串-浮点数组对
 * @author thrblock
 *
 */
public class StrFloatvPair {
    private final String key;
    private float[] value;

    /**
     * 构造字符串-浮点数组
     * @param key 作为key的字符串
     */
    public StrFloatvPair(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public float[] getValue() {
        return value;
    }

    public void setValue(float[] value) {
        this.value = value;
    }
}
