package com.thrblock.rectbase.container;

import java.awt.Color;
import java.util.ArrayList;

import com.thrblock.cino.glshape.GLRect;
import com.thrblock.rectbase.GLRectBase;
import com.thrblock.rectbase.layout.GLRectBasedLayout;
import com.thrblock.rectbase.layout.HorizontalAdaptLayout;

public class GLContainer extends GLRectBase {
    protected GLRectBasedLayout layout;
    protected float w;
    protected float h;
    protected ArrayList<GLRectBase> subs = new ArrayList<>();

    public GLContainer(float w, float h) {
        this(w, h, new HorizontalAdaptLayout());
    }

    public GLContainer(float w, float h, GLRectBasedLayout layout) {
        this.w = w;
        this.h = h;
        this.layout = layout;
        layout.setBase(this);
    }

    public void add(GLRectBase r, Object layoutData) {
        subs.add(r);
        layout.manage(r, layoutData);
        layout.noticeCalc();
    }

    public void add(GLRectBase r) {
        add(r, null);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        layout.noticeCalc();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        layout.noticeCalc();
    }

    @Override
    public void setHeight(float h) {
        super.setHeight(h);
        layout.noticeCalc();
    }

    @Override
    public void setWidth(float w) {
        super.setWidth(w);
        layout.noticeCalc();
    }

    @Override
    protected GLRect buildBase() {
        return shapeFactory.buildGLRect(0, 0, w, h);
    }

    @Override
    protected void afterBaseBuild() {
        super.afterBaseBuild();
        base.setFill(true);
        base.setAllPointColor(Color.GRAY);
        GLRect r = shapeFactory.buildGLRect(0, 0, w, h);
        r.setAllPointColor(Color.BLACK);
        auto(() -> r.sameStatusOf(base));
    }
}
