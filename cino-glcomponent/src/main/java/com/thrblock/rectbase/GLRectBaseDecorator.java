package com.thrblock.rectbase;

import com.thrblock.cino.glshape.GLRect;

public class GLRectBaseDecorator extends GLRectBase {

    protected GLRect rectSrc;

    public GLRectBaseDecorator(GLRect rect) {
        this.rectSrc = rect;
    }

    @Override
    protected void initShowHide() {
        onShow(rectSrc::show);
        onHide(rectSrc::hide);
    }

    @Override
    protected GLRect buildBase() {
        return rectSrc;
    }

}
