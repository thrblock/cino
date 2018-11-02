package com.thrblock.rectbase.slider;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import com.thrblock.cino.function.FloatConsumer;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.util.math.CMath;
import com.thrblock.rectbase.GLRectBase;

public class GLSliderBar extends GLRectBase {

    float w;
    float h;

    protected float progress;

    private boolean incontrol = false;
    private int ctX;
    private float ctProgress;

    private List<FloatConsumer> progressChange = new LinkedList<>();

    public GLSliderBar(float w, float h) {
        this.w = w;
        this.h = h;
    }

    @Override
    protected GLRect buildBase() {
        return shapeFactory.buildGLRect(0, 0, w, h);
    }

    @Override
    protected void afterBaseBuild() {
        super.afterBaseBuild();
        base.setAllPointColor(Color.GRAY);
        base.setFill(true);

        GLRect rLine = shapeFactory.buildGLRect(0, 0, w, 2);
        rLine.setAllPointColor(Color.WHITE);
        rLine.setFill(true);

        float bouyw = getWidth() / 10f > 10f ? 10f : getWidth() / 10f;
        float bouyh = getHeight() - 5f > 15f ? 15f : getHeight() - 5f;
        GLRect bouy = shapeFactory.buildGLRect(0, 0, bouyw, bouyh);
        bouy.setAllPointColor(Color.DARK_GRAY);
        bouy.setFill(true);

        auto(() -> {
            rLine.sameCentralOf(base);
            float bw = getWidth() / 10f > 10f ? 10f : getWidth() / 10f;
            float bh = getHeight() - 5f > 15f ? 15f : getHeight() - 5f;
            float centralOffset = (progress - 0.5f) * getWidth();
            bouy.setWidth(bw);
            bouy.setHeight(bh);

            bouy.setCentralY(getY());
            bouy.setCentralX(getX() + centralOffset);
        });

        autoShapePressed(bouy, e -> {
            ctX = mouseIO.getMouseX();
            ctProgress = progress;
            incontrol = true;
        });

        autoMouseReleased(e -> incontrol = false);
        auto(() -> incontrol, () -> {
            int offset = mouseIO.getMouseX() - ctX;
            float aimProgress = CMath.clamp(ctProgress + offset / getWidth(), 0f, 1f);
            if (!CMath.floatEqual(progress, aimProgress)) {
                progressChange.forEach(f -> f.accept(aimProgress));
                this.progress = aimProgress;
            }
        });

    }
    
    public void addProgressChange(FloatConsumer f) {
        progressChange.add(f);
    }

    public float getProgress() {
        return CMath.clamp(progress, 0f, 1f);
    }

    public void setProgress(float progress) {
        this.progress = CMath.clamp(progress, 0f, 1f);
    }

}