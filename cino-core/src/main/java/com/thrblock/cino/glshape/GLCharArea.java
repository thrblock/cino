package com.thrblock.cino.glshape;

import java.awt.Color;
import java.util.function.Consumer;

import com.jogamp.opengl.GL2;
import com.thrblock.cino.gltexture.GLFont;
import com.thrblock.cino.util.charprocess.CharAreaConfig;
import com.thrblock.cino.util.charprocess.PositionSynchronizer;
import com.thrblock.cino.util.charprocess.Style;

public class GLCharArea extends GLRect {
    private GLFont font;
    private GLImage[] imgs;
    private char[] charArray;
    private PositionSynchronizer positionSyn;
    private Style style;

    public GLCharArea(float x, float y, float width, float height, CharAreaConfig config) {
        super(x, y, width, height);
        this.font = config.getFont();
        this.charArray = config.getCharArray();
        this.imgs = new GLImage[charArray.length];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = new GLImage();
        }
        this.positionSyn = config.getPositionSyn();
        positionSyn.setImgs(imgs);
        positionSyn.setRect(this);
        positionSyn.setSrc(charArray);

        this.style = config.getStyle();
        fillContent();
        setStyle();
    }

    @Override
    public void drawShape(GL2 gl) {
        fillContent();
        setStyle();
        positionSyn.synPosition();
        drawContent(gl);
    }

    private void drawContent(GL2 gl) {
        for (int i = 0; i < imgs.length && charArray[i] != '\0'; i++) {
            imgs[i].drawShape(gl);
        }
    }

    private void setStyle() {
        for (int i = 0; i < charArray.length && charArray[i] != '\0'; i++) {
            style.setStyle(charArray, i, imgs[i]);
        }
    }

    private void fillContent() {
        int i = 0;
        for (; i < charArray.length && charArray[i] != '\0'; i++) {
            imgs[i].setTexture(font.getCharTexture(charArray[i]), true);
        }
        for (; i < imgs.length; i++) {
            imgs[i].setTexture(GLImage.EMPTY_TEXTURE, true);
        }
    }

    public GLFont getFont() {
        return font;
    }

    public void setFont(GLFont font) {
        this.font = font;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setSimpleStyle(Consumer<GLImage> st) {
        this.style = (arr, i, img) -> st.accept(img);
    }

    public void setPositionSyn(PositionSynchronizer positionSyn) {
        this.positionSyn = positionSyn;
    }

    public void setContent(String str) {
        char[] strA = str.toCharArray();
        int i = 0;
        for (; i < strA.length && i < charArray.length; i++) {
            this.charArray[i] = strA[i];
        }
        for (; i < charArray.length; i++) {
            this.charArray[i] = '\0';
        }
    }
    
    public String getContent() {
        return new String(charArray);
    }

    @Override
    public void setAllPointColor(Color c) {
        super.setAllPointColor(c);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setAllPointColor(c);
        }
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setAlpha(alpha);
        }
    }

    @Override
    public void setRadian(float dstTheta) {
        float offset = dstTheta - getRadian();
        super.setRadian(dstTheta);
        float rolx = getCentralX();
        float roly = getCentralY();
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].revolve(offset, rolx, roly);
        }
    }
}
