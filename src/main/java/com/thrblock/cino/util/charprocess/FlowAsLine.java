package com.thrblock.cino.util.charprocess;

import com.thrblock.cino.glshape.GLImage;

public class FlowAsLine extends PositionSynchronizer {

    @Override
    public void synPosition() {
        GLImage prev = imgs[0];
        prev.leftOfInner(rect);
        for (int i = 1; i < imgs.length; i++) {
            imgs[i].rightOf(prev);
            prev = imgs[i];
        }
    }

}
