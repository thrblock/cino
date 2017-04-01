package com.thrblock.cino.util.structure;

public class StrIntPair {
    private final String key;
    private int value;

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
