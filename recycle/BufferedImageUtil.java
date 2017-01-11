package com.thrblock.cino.gltexture;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class BufferedImageUtil {
    private BufferedImageUtil(){
    }
    static BufferedImage reverse(BufferedImage image) {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }
}
