package com.thrblock.cino.util.charprocess;

import com.thrblock.cino.glshape.GLImage;

@FunctionalInterface
public interface Style {
    public void setStyle(char[] src,int index,GLImage img);
}
