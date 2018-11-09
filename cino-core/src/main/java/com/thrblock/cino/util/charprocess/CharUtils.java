package com.thrblock.cino.util.charprocess;

import java.awt.event.KeyEvent;

public class CharUtils {

    private static final char[] DIGITS_SIMBOL = { ')', '!', '@', '#', '$', '%', '^', '&', '*', '(', };

    /**
     * 提供符号的上档转换
     * @param input 输入的源字符
     * @return 对应按键的上档字符 或 原字符当上档不存在时
     */
    public static char tryConvertToSimbol(char input) {
        if (input >= '0' && input <= '9') {
            return DIGITS_SIMBOL[input - '0'];
        }
        switch (input) {
        case '-':
            return '_';
        case '=':
            return '+';
        case '[':
            return '{';
        case ']':
            return '}';
        case '\\':
            return '|';
        case ';':
            return ':';
        case '\'':
            return '"';
        case ',':
            return '<';
        case '.':
            return '>';
        case '/':
            return '?';
        default:
            return input;
        }
    }

    public static boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) && c != KeyEvent.CHAR_UNDEFINED && block != null
                && block != Character.UnicodeBlock.SPECIALS;
    }

    private CharUtils() {
    }
}
