package com.thrblock.rectbase.button;

import java.awt.Color;

import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.util.charprocess.CentralInLine;
import com.thrblock.cino.util.charprocess.CharAreaConfig;
import com.thrblock.cino.util.charprocess.CharRectArea;
import com.thrblock.rectbase.GLRectBase;

public class GLTextButton extends GLRectBase {

    protected CharAreaConfig config;
    protected CharRectArea charArea;
    
    float w;
    float h;

    public GLTextButton(CharAreaConfig config, float w, float h) {
        this.config = config;
        this.w = w;
        this.h = h;
    }
    
    public GLTextButton(String text, float w, float h) {
        this(CharAreaConfig.builder().charArray(text.toCharArray()).build(), w, h);
        config.setPositionSyn(new CentralInLine());
        config.setStyle((s, i, m) -> m.setAllPointColor(Color.BLACK));
    }
    
    public CharRectArea getCharArea() {
        return charArea;
    }

    @Override
    protected GLRect buildBase() {
        return rootNode().glRect(0, 0, w, h);
    }
    
    @Override
    protected void afterBaseBuild() {
        super.afterBaseBuild();
        decorate();
        this.charArea = buildCharArea();
        auto(() -> synBaseStatus(charArea.base()));
    }

    protected void decorate() {
        base.setFill(true);
        base.setAllPointColor(Color.GRAY);

        GLLine top = rootNode().glLine(-w / 2, h / 2, w / 2, h / 2);
        top.setAllPointColor(Color.WHITE);
        top.setLineWidth(2f);

        GLLine left = rootNode().glLine(-w / 2, h / 2, -w / 2, -h / 2);
        left.setAllPointColor(Color.WHITE);
        left.setLineWidth(2f);

        GLLine bottom = rootNode().glLine(-w / 2, -h / 2, w / 2, -h / 2);
        bottom.setAllPointColor(Color.DARK_GRAY);
        bottom.setLineWidth(2f);

        GLLine right = rootNode().glLine(w / 2, h / 2, w / 2, -h / 2);
        right.setAllPointColor(Color.DARK_GRAY);
        right.setLineWidth(2f);

        auto(() -> {
            top.setStartX(getX() - getWidth() / 2);
            top.setEndX(getX() + getWidth() / 2);
            top.setStartY(getY() + getHeight() / 2);
            top.setEndY(getY() + getHeight() / 2);
            
            left.setStartX(getX() - getWidth() / 2);
            left.setEndX(getX() - getWidth() / 2);
            left.setStartY(getY() + getHeight() / 2);
            left.setEndY(getY() - getHeight() / 2);
            
            bottom.setStartX(getX() - getWidth() / 2);
            bottom.setEndX(getX() + getWidth() / 2);
            bottom.setStartY(getY() - getHeight() / 2);
            bottom.setEndY(getY() - getHeight() / 2);
            
            right.setStartX(getX() + getWidth() / 2);
            right.setEndX(getX() + getWidth() / 2);
            right.setStartY(getY() + getHeight() / 2);
            right.setEndY(getY() - getHeight() / 2);
        });

        autoShapePressed(base, e -> {
            top.setAllPointColor(Color.DARK_GRAY);
            left.setAllPointColor(Color.DARK_GRAY);
            bottom.setAllPointColor(Color.WHITE);
            right.setAllPointColor(Color.WHITE);
        });

        autoShapeReleased(base, e -> moveout(top, left, bottom, right));
        addMouseMoveout(() -> moveout(top, left, bottom, right));
    }

    private void moveout(GLLine top, GLLine left, GLLine bottom, GLLine right) {
        top.setAllPointColor(Color.WHITE);
        left.setAllPointColor(Color.WHITE);

        bottom.setAllPointColor(Color.DARK_GRAY);
        right.setAllPointColor(Color.DARK_GRAY);
    }

    protected CharRectArea buildCharArea() {
        return charRectFactory.charRectArea(0, 0, w, h, config);
    }
}
