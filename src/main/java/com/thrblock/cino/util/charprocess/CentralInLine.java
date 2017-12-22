package com.thrblock.cino.util.charprocess;

import com.thrblock.cino.glshape.GLImage;

public class CentralInLine extends PositionSynchronizer {

    @Override
    public void synPosition() {
        GLImage prev = imgs[0];
        float w = 0;
        for (int i = 0; i < src.length && src[i] != '\0'; i++) {
            w += imgs[i].getWidth();
        }
        prev.leftOfInner(rect, (rect.getWidth() - w) / 2);
        for (int i = 1; i < imgs.length; i++) {
            imgs[i].rightOf(prev);
            prev = imgs[i];
        }
    }

}
