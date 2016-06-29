package com.thrblock.cino.glshape.templete;

import com.thrblock.cino.glshape.builder.GLNode;

public class TpAlpha {
    private TpAlpha() {
    }
    public static boolean tearOut(GLNode node, float step) {
        float calc = node.getAlpha() - step;
        if(calc > 0) {
            node.setAlpha(calc);
            return false;
        } else {
            node.setAlpha(0);
            return true;
        }
    }
    public static boolean tearIn(GLNode node, float step) {
        float calc = node.getAlpha() + step;
        if(calc < 1f) {
            node.setAlpha(calc);
            return false;
        } else {
            node.setAlpha(1f);
            return true;
        }
    }
}
