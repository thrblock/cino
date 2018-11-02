package com.thrblock.cino.util.charprocess;

import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLRect;

public abstract class PositionSynchronizer {
    protected GLImage[] imgs;
    protected GLRect rect;
    protected char[] src;

    public GLImage[] getImgs() {
        return imgs;
    }

    public void setImgs(GLImage[] imgs) {
        this.imgs = imgs;
    }

    public GLRect getRect() {
        return rect;
    }

    public void setRect(GLRect rect) {
        this.rect = rect;
    }

    public char[] getSrc() {
        return src;
    }

    public void setSrc(char[] src) {
        this.src = src;
    }

    public abstract void synPosition();
}
