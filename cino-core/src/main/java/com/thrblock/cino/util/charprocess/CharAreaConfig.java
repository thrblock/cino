package com.thrblock.cino.util.charprocess;

import java.awt.Color;
import java.awt.Font;
import java.util.function.Consumer;

import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.FontsInCommon;
import com.thrblock.cino.gltexture.GLFont;

import lombok.Data;

@Data
public class CharAreaConfig {
    private static final GLFont DEFAULT_FONT = new GLFont(new Font(FontsInCommon.GNU_FREE_MONO, Font.PLAIN, 12));
    private char[] charArray;
    private GLFont font;
    private PositionSynchronizer positionSyn = new FlowAsLine();
    private CharStyle style = (arr, i, img) -> {
    };
    private Consumer<GLRect> rectStyle = r -> {
        r.setPointColor(0, Color.WHITE);
        r.setPointColor(1, Color.GRAY);
        r.setPointColor(2, Color.BLACK);
        r.setPointColor(3, Color.GRAY);
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
        return font == null ? DEFAULT_FONT : font;
    }

}
