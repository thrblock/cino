package com.thrblock.cino.gltexture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.thrblock.cino.glfont.GLFontTexture;
import com.thrblock.cino.glshape.builder.GifBuilder;
import com.thrblock.cino.glshape.builder.GifBuilder.GifData;

@Component
public class GLTextureContainer implements IGLTextureContainer {
    private static final Logger LOG = LogManager.getLogger(GLTextureContainer.class);
    
    public static class StreamPair {
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
            stream.close();
            return texture;
        }
    }
    
    private Map<String, Texture> imgTextureMap = new HashMap<>();
    
    private Map<String, StreamPair> streamSwap = new HashMap<>();
    private Semaphore streamSwapSp = new Semaphore(1);
    
    private Map<String, BufferedImage> bufferedImageSwap = new HashMap<>();
    private Semaphore bufferedImageSwapSp = new Semaphore(1);
    

    private Map<String, GLFontTexture> fontTextureMap = new HashMap<>();
    private Map<String, GLFontTexture> fontSwap = new HashMap<>();
    private Semaphore fontSwapSp = new Semaphore(1);
    @Override
    public Texture getTexture(String name) {
        return imgTextureMap.get(name);
    }
    @Override
    public void registerTexture(String name, InputStream srcStream) {
        registerTexture(name,null,srcStream);
    }
    @Override
    public void registerTexture(String name,String imgType,InputStream srcStream) {
        streamSwapSp.acquireUninterruptibly();
        if(!streamSwap.containsKey(name) && !imgTextureMap.containsKey(name)) {
            LOG.info("registerTexture name:" + name + ",type:" + imgType + ",stream:" + srcStream);
            streamSwap.put(name, new StreamPair(imgType,srcStream));
        } else {
            try {
                srcStream.close();
            } catch (IOException e) {
                LOG.warn("IOException in closing reuse file:" + e);
            }
        }
        streamSwapSp.release();
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
        if (!streamSwap.isEmpty()) {
            streamSwapSp.acquireUninterruptibly();
            for (Entry<String, StreamPair> entry : streamSwap.entrySet()) {
                StreamPair pair = entry.getValue();
                try {
                    imgTextureMap.put(entry.getKey(),pair.getTexture(gl));
                } catch (GLException | IOException e) {
                    LOG.warn("Exception in parseTexture:" + e);
                }
            }
            gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
            streamSwap.clear();
            streamSwapSp.release();
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
        if(!bufferedImageSwap.isEmpty()) {
            bufferedImageSwapSp.acquireUninterruptibly();
            for (Entry<String, BufferedImage> entry : bufferedImageSwap.entrySet()) {
                try{
                    Texture texture =  AWTTextureIO.newTexture(gl.getGLProfile(), entry.getValue(), false);
                    texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
                    texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
                    imgTextureMap.put(entry.getKey(), texture);
                    entry.getValue().flush();
                } catch (GLException e) {
                    LOG.warn("Exception in parseTexture:" + e);
                }
            }
            gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
            bufferedImageSwap.clear();
            bufferedImageSwapSp.release();
        }
    }
    
    @Override
    public void registerFont(String name, GLFontTexture fontTexture) {
        fontSwapSp.acquireUninterruptibly();
        if(!fontSwap.containsKey(name) && !fontTextureMap.containsKey(name)) {
            fontSwap.put(name, fontTexture);
        }
        fontSwapSp.release();
    }

    @Override
    public GLFontTexture getGLFontTexture(String name) {
        return fontTextureMap.get(name);
    }

    @Override
    public GifMetaData registerGifAsTexture(String name, File gifFile) {
        try {
            return registerGifAsTexture(name,new FileInputStream(gifFile));
        } catch (FileNotFoundException e) {
            LOG.warn("Exception in  registerGifAsTexture:" + e);
            return null;
        }
    }

    @Override
    public GifMetaData registerGifAsTexture(String name, InputStream srcStream) {
        bufferedImageSwapSp.acquireUninterruptibly();
        GifMetaData result = null;
        try {
            GifData data = GifBuilder.buildGifData(srcStream);
            String[] names = new String[data.getNumber()];
            BufferedImage[] images = data.getImages();
            int[] widths = new int[data.getNumber()];
            int[] heights = new int[data.getNumber()];
            for(int i = 0;i < data.getNumber();i++) {
                names[i] = name + "_" + i;
                if(!bufferedImageSwap.containsKey(names[i]) && !imgTextureMap.containsKey(names[i])) {
                    widths[i] = images[i].getWidth();
                    heights[i] = images[i].getHeight();
                	bufferedImageSwap.put(names[i], images[i]);
                }
            }
            result = new GifMetaData();
            result.setTextureGroup(names);
            result.setRate(data.getRate());
            result.setWidths(widths);
            result.setHeights(heights);
        } catch (IOException e) {
            LOG.warn("Exception in  registerGifAsTexture:" + e);
        }
        bufferedImageSwapSp.release();
        return result;
    }
}