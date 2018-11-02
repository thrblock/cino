package com.thrblock.cino.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class TextureSaver {
    private static final Logger LOG = LoggerFactory.getLogger(TextureSaver.class);

    public static void saveCurrentBindTexture(GL2 gl2, int w, int h) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(w * h * 4);
            gl2.glGetTexImage(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buffer);
            BufferedImage bfi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            byte[] array = buffer.array();
            DataInputStream is = new DataInputStream(new ByteArrayInputStream(array));
            for (int j = h - 1; j >= 0; j--) {
                for (int i = 0; i < w; i++) {
                    bfi.setRGB(i, j, is.readInt() >> 8);
                }
            }
            ImageIO.write(bfi, "png", new File("./" + System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            LOG.warn("IOException in saveCurrentBindTexture:{}", e);
        }
    }

    private TextureSaver() {
    }
}
