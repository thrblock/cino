package com.thrblock.game.demo.component.lines;

import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.lintersection.AbstractVector;

public class GLVector extends AbstractVector {
    private GLLine line;

    public GLVector(GLLine line) {
        this.line = line;
    }

    @Override
    public float getStartX() {
        return line.getPointX(0);
    }

    @Override
    public float getStartY() {
        return line.getPointY(0);
    }

    @Override
    public float getEndX() {
        return line.getPointX(1);
    }

    @Override
    public float getEndY() {
        return line.getPointY(1);
    }

}
