package com.thrblock.rectbase.checkbox;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.rectbase.GLRectBase;

public class GLCheckBox extends GLRectBase {

    protected float w;
    protected float h;
    protected float margin;

    protected boolean check;

    protected List<VoidConsumer> onCheck = new LinkedList<>();
    protected List<VoidConsumer> onUnCheck = new LinkedList<>();

    public GLCheckBox(float w) {
        this(w, w, (w / 5f) > 2f ? (w / 5f) : 2f);
    }

    public GLCheckBox(float w, float h) {
        this(w, h, (w / 5f) > 2f ? (w / 5f) : 2f);
    }

    public GLCheckBox(float w, float h, float margin) {
        this.w = w;
        this.h = h;
        this.margin = margin;
    }

    @Override
    protected GLRect buildBase() {
        return shapeFactory.buildGLRect(0, 0, w, h);
    }

    @Override
    protected void afterBaseBuild() {
        super.afterBaseBuild();
        addMouseClicked(e -> {
            if (isCheck()) {
                check = false;
            } else {
                check = true;
            }
            (check ? onCheck : onUnCheck).forEach(VoidConsumer::accept);
        });
        decorate();
    }

    protected void decorate() {
        base.setAllPointColor(Color.WHITE);
        base.setFill(true);

        GLRect border = shapeFactory.buildGLRect(0, 0, w, h);
        border.setAllPointColor(Color.BLACK);
        
        GLRect innerRect = shapeFactory.buildGLRect(0, 0, w - margin * 2, h - margin * 2);
        innerRect.setAllPointColor(Color.DARK_GRAY);
        
        auto(() -> {
            innerRect.sameCentralOf(base);
            border.sameCentralOf(base);
            innerRect.setFill(check);
        });
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void addOnCheck(VoidConsumer v) {
        onCheck.add(v);
    }

    public void addOnUnCheck(VoidConsumer v) {
        onUnCheck.add(v);
    }
}
