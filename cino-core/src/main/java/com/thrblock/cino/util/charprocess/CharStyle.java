package com.thrblock.cino.util.charprocess;

import com.thrblock.cino.glshape.GLImage;

@FunctionalInterface
public interface CharStyle {
    public void setStyle(char[] src,int index,GLImage img);
}
