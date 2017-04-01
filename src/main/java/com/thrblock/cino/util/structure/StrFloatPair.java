package com.thrblock.cino.util.structure;

public class StrFloatPair {
    private final String key;
    private float value;

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
