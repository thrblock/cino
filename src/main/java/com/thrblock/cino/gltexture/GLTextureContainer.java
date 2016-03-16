package com.thrblock.cino.gltexture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

@Component
public class GLTextureContainer implements IGLTextureContainer {
    private static final Logger LOG = LogManager.getLogger(GLTextureContainer.class);
    private Map<String, Texture> imgTextureMap = new HashMap<>();
    private Map<String, StreamPair> imageSwap = new HashMap<>();
    private Semaphore imageSwapSp = new Semaphore(1);

    private Map<String, GLFontTexture> fontTextureMap = new HashMap<>();
    private Map<String, GLFontTexture> fontSwap = new HashMap<>();
    private Semaphore fontSwapSp = new Semaphore(1);
    @Override
    public Texture getTexture(String name) {
        return imgTextureMap.get(name);
    }
    
    @Override
    public void registerTexture(String name,String imgType,InputStream srcStream) {
        LOG.info("registerTexture name:" + name + ",type:" + imgType + ",stream:" + srcStream);
        imageSwapSp.acquireUninterruptibly();
        if(!imageSwap.containsKey(name) && !imgTextureMap.containsKey(name)) {
            imageSwap.put(name, new StreamPair(imgType,srcStream));
        }
        imageSwapSp.release();
    }
    
    @Override
    public void registerTexture(String name,File imgFile) {
        try {
            String fileName = imgFile.getCanonicalPath();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                String extName = fileName.substring(i+1);
                InputStream srcStream = new FileInputStream(imgFile);
                registerTexture(name,extName,srcStream);
            } else {
                LOG.warn("no extension name found from file:" + imgFile);
            }
        } catch (IOException e) {
            LOG.warn("IOException in registerTexture by file:" + e);
        }
    }

    @Override
    public void parseTexture(GL2 gl) {
        if (!imageSwap.isEmpty()) {
            imageSwapSp.acquireUninterruptibly();
            for (Entry<String, StreamPair> entry : imageSwap.entrySet()) {
                StreamPair pair = entry.getValue();
                try {
                    imgTextureMap.put(entry.getKey(),pair.getTexture(gl));
                } catch (GLException | IOException e) {
                    LOG.warn("Exception in parseTexture:" + e);
                }
            }
            gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
            imageSwap.clear();
            imageSwapSp.release();
        }
        if(!fontSwap.isEmpty()) {
            fontSwapSp.acquireUninterruptibly();
            for (Entry<String, GLFontTexture> entry : fontSwap.entrySet()) {
                try {
                    entry.getValue().loadFontAsTexture(gl);
                    fontTextureMap.put(entry.getKey(), entry.getValue());
                } catch (IOException e) {
                    LOG.warn("Exception in parseFontTexture:" + e);
                }
            }
            gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
            fontSwap.clear();
            fontSwapSp.release();
        }
    }
    private static class StreamPair {
        private final String imgType;
        private final InputStream stream;
        public StreamPair(String imgType,InputStream stream) {
            this.imgType = imgType;
            this.stream = stream;
        }
        public Texture getTexture(GL2 gl) throws IOException {
            Texture texture = TextureIO.newTexture(stream,false,imgType);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
            return texture;
        }
    }
    @Override
    public void registerFont(String name, GLFontTexture fontTexture) {
        fontSwapSp.acquireUninterruptibly();
        fontSwap.put(name, fontTexture);
        fontSwapSp.release();
    }

    @Override
    public GLFontTexture getGLFontTexture(String name) {
        return fontTextureMap.get(name);
    }
}