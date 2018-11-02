package com.thrblock.rectbase.button;

import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.GLTexture;
import com.thrblock.rectbase.GLRectBase;

public class GLImgButton extends GLRectBase {
    protected GLTexture buttonUp;
    protected GLTexture buttonDown;
    protected GLImage baseImg;
    protected boolean pressing = false;

    float w;
    float h;

    public GLImgButton(GLTexture up, GLTexture down, float w, float h) {
        this.buttonUp = up;
        this.buttonDown = down;
        this.w = w;
        this.h = h;
    }

    @Override
    protected GLRect buildBase() {
        GLImage img = shapeFactory.buildGLImage(0, 0, w, h, GLImage.EMPTY_TEXTURE);
        this.baseImg = img;
        return img;
    }

    @Override
    protected void afterBaseBuild() {
        super.afterBaseBuild();
        baseImg.setTexture(buttonUp);

        addMousePressed(e -> {
            pressing = true;
            baseImg.setTexture(buttonDown);
        });
        addMouseReleased(e -> {
            pressing = false;
            baseImg.setTexture(buttonUp);
        });
        addMouseMoveout(() -> {
            if (pressing) {
                baseImg.setTexture(buttonUp);
            }
            pressing = false;
        });
    }

    public GLTexture getButtonUp() {
        return buttonUp;
    }

    public void setButtonUp(GLTexture buttonUp) {
        this.buttonUp = buttonUp;
        if (!pressing) {
            baseImg.setTexture(buttonUp);
        }
    }

    public GLTexture getButtonDown() {
        return buttonDown;
    }

    public void setButtonDown(GLTexture buttonDown) {
        this.buttonDown = buttonDown;
        if (pressing) {
            baseImg.setTexture(buttonDown);
        }
    }

    public void fixedButton(GLTexture fixedTexture) {
        setButtonUp(fixedTexture);
        setButtonDown(fixedTexture);
    }
}
