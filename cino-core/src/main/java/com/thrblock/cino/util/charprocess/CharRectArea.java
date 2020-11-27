package com.thrblock.cino.util.charprocess;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Consumer;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.GLFont;

public class CharRectArea extends CinoComponent {
    private GLRect base;
    private GLFont font;
    private GLImage[] imgs;
    private char[] charArray;
    private PositionSynchronizer positionSyn;
    private CharStyle charStyle;
    private Consumer<GLRect> rectStyle;

    private float initX;
    private float initY;
    private float initWidth;
    private float initHeight;
    private CharAreaConfig config;

    public CharRectArea(float x, float y, float width, float height, CharAreaConfig config) {
        this.initX = x;
        this.initY = y;
        this.initWidth = width;
        this.initHeight = height;
        this.config = config;
        Optional.ofNullable(config.getNode()).ifPresent(this::setRootNode);
    }

    @Override
    public void init() throws Exception {
        autoShowHide();

        this.base = rootNode().glRect(initX, initY, initWidth, initHeight);
        this.font = config.getFont();
        this.charArray = config.getCharArray();

        this.imgs = new GLImage[charArray.length];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = rootNode().glImage();
        }

        this.positionSyn = config.getPositionSyn();
        positionSyn.setImgs(imgs);
        positionSyn.setRect(base);
        positionSyn.setSrc(charArray);

        this.charStyle = config.getStyle();
        this.rectStyle = config.getRectStyle();

        fillContent();
        setStyle();

        auto(this::drawShape);
    }

    public void drawShape() {
        fillContent();
        setStyle();
        positionSyn.synPosition();
    }

    private void setStyle() {
        rectStyle.accept(base);
        for (int i = 0; i < charArray.length && charArray[i] != '\0'; i++) {
            charStyle.setStyle(charArray, i, imgs[i]);
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

    public void setStyle(CharStyle style) {
        this.charStyle = style;
    }

    public void setSimpleStyle(Consumer<GLImage> st) {
        this.charStyle = (arr, i, img) -> st.accept(img);
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

    public void setAllPointColor(Color c) {
        base.setAllPointColor(c);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setAllPointColor(c);
        }
    }

    public void setAlpha(float alpha) {
        base.setAlpha(alpha);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setAlpha(alpha);
        }
    }

    public void setRadian(float dstTheta) {
        float offset = dstTheta - base.getRadian();
        base.setRadian(dstTheta);
        float rolx = base.getCentralX();
        float roly = base.getCentralY();
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].revolve(offset, rolx, roly);
        }
    }

    public float getRadian() {
        return base.getRadian();
    }

    public GLRect base() {
        return base;
    }
}
