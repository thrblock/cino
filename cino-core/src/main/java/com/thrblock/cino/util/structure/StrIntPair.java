package com.thrblock.cino.util.structure;

/**
 * 字符串-整形对
 * @author thrblock
 *
 */
public class StrIntPair {
    private final String key;
    private int value;

    /**
     * 构造字符串-整形对
     * @param key
     */
    public StrIntPair(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
