package com.thrblock.cino.util.structure;

import com.jogamp.opengl.GLContext;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.gltexture.GLFont;

public class CharSynchronizer {
    private GLImage[] imgs;
    private char[] src;
    private GLFont f;

    public CharSynchronizer(GLFont font, char[] src, GLImage... imgs) {
        this.imgs = imgs;
        this.src = src;
        this.f = font;
    }

    public void syn() {
        for (int i = 0; i < src.length; i++) {
//            imgs[i].setTexture(f.getCharTexture(GLContext.getCurrentGL(), src[i]), true);
        }
    }
}
