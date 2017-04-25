package com.thrblock.cino.util.structure;

public class StrFloatvPair {
    private final String key;
    private float[] value;

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
