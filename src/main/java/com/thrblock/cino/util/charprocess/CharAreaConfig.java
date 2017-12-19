package com.thrblock.cino.util.charprocess;

import java.awt.Font;

import com.thrblock.cino.gltexture.FontsInCommon;
import com.thrblock.cino.gltexture.GLFont;

public class CharAreaConfig {
    private char[] charArray;
    private GLFont font = new GLFont(new Font(FontsInCommon.GNU_FREE_MONO, Font.PLAIN, 12));
    private PositionSynchronizer positionSyn = new FlowAsLine();
    private Style style = (arr, i, img) -> {
    };

    public CharAreaConfig(String str) {
        charArray = str.toCharArray();
    }

    public CharAreaConfig(char[] array) {
        this.charArray = array;
    }

    public CharAreaConfig(int size) {
        this.charArray = new char[size];
    }

    public GLFont getFont() {
        return font;
    }

    public void setFont(GLFont font) {
        this.font = font;
    }

    public char[] getCharArray() {
        return charArray;
    }

    public void setCharArray(char[] charArray) {
        this.charArray = charArray;
    }

    public PositionSynchronizer getPositionSyn() {
        return positionSyn;
    }

    public void setPositionSyn(PositionSynchronizer positionSyn) {
        this.positionSyn = positionSyn;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

}
