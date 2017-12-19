package com.thrblock.cino.util.charprocess;

import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * 一个使用char数组维护的输入区
 * @author zepu.li
 */
public class CharArrayInput {
	private char cursor = '_';
    private char[] src;
    private int currentIndex = 0;
    /**
     * @param src
     */
    public CharArrayInput(char[] src) {
        this.src = src;
    }
    
    /**
     * @param init
     */
    public CharArrayInput(String init) {
        this.src = init.toCharArray();
    }
    
    /**
     * @param length
     */
    public CharArrayInput(int length) {
        this.src = new char[length];
        clear();
    }
    
    /**
     * 清除
     */
    public void clear() {
        this.currentIndex = 0;
        Arrays.fill(src,' ');
        src[currentIndex] = cursor;
    }
    
    /**
     * 退格
     */
    public void backspace() {
        if(currentIndex > 0) {
            if(currentIndex < src.length) {
                src[currentIndex] = ' ';
            }
            currentIndex --;
            src[currentIndex] = cursor;
        }
    }
    
    /**
     * 末尾追加
     * @param c
     */
    public void append(char c) {
        if(isPrintableChar(c) && currentIndex < src.length) {
            src[currentIndex] = c;
            currentIndex ++;
            if(currentIndex < src.length) {
                src[currentIndex] = cursor;
            }
        }
    }
    
    /**
     * 获得char数组
     * @return char数组
     */
    public char[] getArray() {
        return src;
    }
    
    private boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) && c != KeyEvent.CHAR_UNDEFINED && block != null
                && block != Character.UnicodeBlock.SPECIALS;
    }
}
