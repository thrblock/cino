package com.thrblock.cino.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class BufferedImageUtil {
    private BufferedImageUtil() {
    }

    public static BufferedImage reverse(BufferedImage image) {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }

    public static BufferedImage genEmptyImage(int w,int h) {
        return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }
    
    public static BufferedImage getScaledImage(BufferedImage src, int w, int h) {
        int finalw = w;
        int finalh = h;
        double factor;
        if (src.getWidth() > src.getHeight()) {
            factor = ((double) src.getHeight() / (double) src.getWidth());
            finalh = (int) (finalw * factor);
        } else {
            factor = ((double) src.getWidth() / (double) src.getHeight());
            finalw = (int) (finalh * factor);
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();
        return resizedImg;
    }
}
