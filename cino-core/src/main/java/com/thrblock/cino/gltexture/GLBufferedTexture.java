package com.thrblock.cino.gltexture;

import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.thrblock.cino.util.BufferedImageUtil;

/**
 * GLIOTexture 此类纹理对象以内存中的图像缓冲区作为资源
 * 
 * @author zepu.li
 */
public class GLBufferedTexture implements GLTexture {
    private Texture texture;
    private BufferedImage imageBuffer;

    /**
     * 使用BufferdImage构造纹理资源
     * 
     * @param image
     */
    public GLBufferedTexture(BufferedImage image) {
        this.imageBuffer = image;
    }

    @Override
    public void initByGLContext(GL gl) {
        this.texture = AWTTextureIO.newTexture(gl.getGLProfile(), BufferedImageUtil.reverse(imageBuffer), false);
        texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    }

    @Override
    public Texture getTexture(GL gl) {
        if (this.texture == null) {
            initByGLContext(gl);
        }
        return texture;
    }

}
