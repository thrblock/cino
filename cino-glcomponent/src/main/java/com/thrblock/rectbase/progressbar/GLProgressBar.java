package com.thrblock.rectbase.progressbar;

import java.awt.Color;

import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.util.math.CMath;
import com.thrblock.rectbase.GLRectBase;

public class GLProgressBar extends GLRectBase {
    float w;
    float h;

    protected float progress = 0.0f;
    
    public GLProgressBar(float w, float h) {
        this.w = w;
        this.h = h;
    }

    @Override
    protected GLRect buildBase() {
        return rootNode().glRect(0, 0, w, h);
    }

    @Override
    protected void afterBaseBuild() {
        super.afterBaseBuild();
        base.setAllPointColor(Color.WHITE);
        base.setFill(true);

        GLRect progressBar = rootNode().glRect(0, 0, w, h);
        progressBar.setFill(true);
        progressBar.setAllPointColor(new Color(81, 205, 91));
        
        GLRect border = rootNode().glRect(0, 0, w, h);
        border.setAllPointColor(Color.BLACK);
        
        auto(() -> {
            border.sameStatusOf(base);
            float prow = CMath.clamp(getProgress() * getWidth(), 1f, getWidth());
            progressBar.setWidth(prow);
            progressBar.leftOfInner(border);
        });

    }

    public float getProgress() {
        return CMath.clamp(progress, 0.0f, 1.0f);
    }

    public void setProgress(float p) {
        this.progress = CMath.clamp(p, 0.0f, 1.0f);
    }

}
