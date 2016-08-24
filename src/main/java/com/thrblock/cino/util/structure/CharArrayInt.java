package com.thrblock.cino.util.structure;

import java.util.Arrays;

/**
 * 一个使用char数组维护的整数数据，一般配合字库完成积分等功能
 * @author lizepu
 */
public class CharArrayInt {
    private char[] arr;
    private int current = 0;
    
    /**
     * 以初始字符串对char数组进行初始化
     * @param init 字符串
     */
    public CharArrayInt(String init) {
        this.arr = init.toCharArray();
    }
    
    /**
     * 以初始char数组进行初始化
     * @param array
     */
    public CharArrayInt(char[] array) {
        this.arr = array;
        Arrays.fill(arr, '0');
    }
    
    /**
     * 将此char数组设置为指定的整数，中间不会产生String碎片
     * @param value 要设置的整数
     */
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
    
    /**
     * 增加一个整数
     * @param value 要增加的数
     */
    public void addByInt(int value) {
        setByInt(current + value);
    }
    
    /**
     * 获得char数组
     * @return char数组
     */
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