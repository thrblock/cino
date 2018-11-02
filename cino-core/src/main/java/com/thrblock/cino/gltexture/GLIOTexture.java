package com.thrblock.cino.gltexture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * GLIOTexture 此类纹理对象以外部文件或流作为资源
 * 
 * @author zepu.li
 */
public class GLIOTexture implements GLTexture {
    private static final Logger LOG = LoggerFactory.getLogger(GLIOTexture.class);
    private InputStream stream;
    private String imgType;
    private Texture texture;
    private boolean fatalInLoad = false;

    /**
     * 使用文件构造一个纹理资源，将文件拓展名视为类型标识
     * 
     * @param imgFile
     */
    public GLIOTexture(File imgFile) {
        try {
            String fileName = imgFile.getCanonicalPath();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                this.imgType = fileName.substring(i + 1);
                this.stream = new FileInputStream(imgFile);
            } else {
                LOG.warn("no extension name found from file:{}", imgFile);
            }
        } catch (IOException e) {
            LOG.warn("IOException in registerTexture by file:{}", e);
        }
    }

    /**
     * 使用流与指定的类型标识进行纹理资源构造，错误的类型将导致加载异常或纹理走样
     * 
     * @param is
     * @param type
     */
    public GLIOTexture(InputStream is, String type) {
        this.stream = is;
        this.imgType = type;
    }

    @Override
    public void initByGLContext(GL gl) {
        try {
            this.texture = TextureIO.newTexture(stream, false, imgType);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            stream.close();
        } catch (GLException | IOException e) {
            fatalInLoad = true;
            LOG.warn("Exception in generate gl texture:{}", e);
        }
    }

    @Override
    public Texture getTexture(GL gl) {
        if (this.texture == null && !fatalInLoad) {
            initByGLContext(gl);
        }
        return texture;
    }
}
