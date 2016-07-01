package com.thrblock.cino.util.structure;

import java.util.Arrays;

public class CharArrayInt {
    private char[] arr;
    private int current = 0;
    
    public CharArrayInt(String init) {
        this.arr = init.toCharArray();
    }
    
    public CharArrayInt(char[] array) {
        this.arr = array;
        Arrays.fill(arr, '0');
    }
    
    public void setByInt(int value) {
        this.current = value;
        if(arr.length < getLength(value)) {
            throw new IllegalArgumentException("value out of range.");
        }
        int number = value;
        for (int i = arr.length - 1; i >= 0; i--) {
            arr[i] = (char) ('0' + (number % 10));
            number /= 10;
        }
    }
    
    public void addByInt(int value) {
        setByInt(current + value);
    }
    
    public char[] getArray() {
        return arr;
    }
    
    private int getLength(int src) {
        if(src == 0) {
            return 1;
        } else {
            return (int) (Math.log10(src) + 1);
        }
    }
}
