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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.thrblock.cino.glshape.builder.GifBuilder;
import com.thrblock.cino.glshape.builder.GifBuilder.GifData;

/**
 * 纹理容器管理器
 * @author lizepu
 */
@Component
public class GLTextureContainer {
    private static final Logger LOG = LoggerFactory.getLogger(GLTextureContainer.class);
    
    /**
     * 流-类型 数据，储存了流与类型的关系数据
     * @author lizepu
     */
    public static class StreamPair {
        private final String imgType;
        private final InputStream stream;
        /**构造一个流-类型数据
         * @param imgType 类型
         * @param stream 流
         */
        public StreamPair(String imgType,InputStream stream) {
            this.imgType = imgType;
            this.stream = stream;
        }
        /**
         * 使用OpenGL 对象得到纹理
         * @param gl OpenGL 对象
         * @return 纹理
         * @throws IOException 当IO异常时
         */
        public Texture getTexture(GL2 gl) throws IOException {
            Texture texture = TextureIO.newTexture(stream,false,imgType);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
            texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
            stream.close();
            return texture;
        }
    }
    /**
     * Gif 源信息
     * @author lizepu
     */
    public static class GifMetaData {
        private int[] widths;
        private int[] heights;
        private String[] textureGroup;
        private int rate;
        /**
         * 获得Gif纹理名称数组
         * @return 纹理名称数组
         */
        public String[] getTextureGroup() {
            return textureGroup;
        }
        /**
         * 设置Gif纹理名称数组
         * @param textureGroup 纹理名称数组
         */
        public void setTextureGroup(String[] textureGroup) {
            this.textureGroup = textureGroup;
        }
        /**
         * 获得 Gif帧播放频率
         * @return Gif帧播放频率
         */
        public int getRate() {
            return rate;
        }
        /**
         * 设置Gif帧播放频率
         * @param rate Gif帧播放频率
         */
        public void setRate(int rate) {
            this.rate = rate;
        }
        /**
         * 获得Gif帧数
         * @return Gif帧数
         */
        public int getFrameNum() {
            return textureGroup.length;
        }
        /**
         * 获得宽度数组
         * @return 宽度数组
         */
        public int[] getWidths() {
            return widths;
        }
        /**
         * 设置宽度数组
         * @param widths 宽度数组
         */
        public void setWidths(int[] widths) {
            this.widths = widths;
        }
        /**
         * 获得高度数组
         * @return 高度数组
         */
        public int[] getHeights() {
            return heights;
        }
        /**
         * 设置高度数组
         * @param heights 高度数组
         */
        public void setHeights(int[] heights) {
            this.heights = heights;
        }
    }
    
    private Map<String, Texture> imgTextureMap = new HashMap<>();
    
    private Map<String, StreamPair> streamSwap = new HashMap<>();
    private Semaphore streamSwapSp = new Semaphore(1);
    
    private Map<String, BufferedImage> bufferedImageSwap = new HashMap<>();
    private Semaphore bufferedImageSwapSp = new Semaphore(1);
    
    /**
     * 由名称获得纹理对象
     * @param name 纹理名称
     * @return 纹理对象
     */
    public Texture getTexture(String name) {
        return imgTextureMap.get(name);
    }
    
    /**
     * 注册一个纹理对象
     * @param name 纹理名称
     * @param srcStream 资源流
     */
    public void registerTexture(String name, InputStream srcStream) {
        registerTexture(name,null,srcStream);
    }
    
    /**
     * 注册一个纹理对象
     * @param name 纹理名称
     * @param imgType 资源类型 如‘png’
     * @param srcStream 资源流
     */
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
    
    /**
     * 注册一个纹理对象，注意，与文件拓展名敏感
     * @param name 纹理名称
     * @param imgFile 图像文件
     */
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
    
    /**
     * 注册一个纹理对象使用bufferedimage
     * @param name
     * @param bufferedImage
     */
    public void registerTexture(String name,BufferedImage bufferedImage) {
        bufferedImageSwapSp.acquireUninterruptibly();
        if(!bufferedImageSwap.containsKey(name) && !imgTextureMap.containsKey(name)) {
            bufferedImageSwap.put(name, BufferedImageUtil.reverse(bufferedImage));
        }
        bufferedImageSwapSp.release();
    }

    
    /**
     * 使用Gif文件注册一组纹理对象
     * @param name Gif纹理名称
     * @param gifFile Gif文件
     * @return GifMetaData Gif源信息
     */
    public GifMetaData registerGifAsTexture(String name, File gifFile) {
        try {
            return registerGifAsTexture(name,new FileInputStream(gifFile));
        } catch (FileNotFoundException e) {
            LOG.warn("Exception in  registerGifAsTexture:" + e);
            return null;
        }
    }

    /**
     * 使用Gif流注册一组纹理对象
     * @param name Gif纹理名称
     * @param srcStream Gif流
     * @return GifMetaData Gif源信息
     */
    public GifMetaData registerGifAsTexture(String name, InputStream srcStream) {
        GifMetaData result = null;
        try {
            GifData data = GifBuilder.buildGifData(srcStream);
            String[] names = new String[data.getNumber()];
            BufferedImage[] images = data.getImages();
            int[] widths = new int[data.getNumber()];
            int[] heights = new int[data.getNumber()];
            for(int i = 0;i < data.getNumber();i++) {
                names[i] = name + "_" + i;
                widths[i] = images[i].getWidth();
                heights[i] = images[i].getHeight();
                registerTexture(names[i],BufferedImageUtil.reverse(images[i]));
            }
            result = new GifMetaData();
            result.setTextureGroup(names);
            result.setRate(data.getRate());
            result.setWidths(widths);
            result.setHeights(heights);
        } catch (IOException e) {
            LOG.warn("Exception in  registerGifAsTexture:" + e);
        }
        return result;
    }
    
    /**
     * 进行 资源（文件、流等）到纹理的转换，由于使用OpenGL上下文，该过程尽在绘制结束后进行<br />
     * 由于生成纹理需要时间较多，因此当需要载入较大或较多的资源时，要使用预加载方式
     * @param gl OpenGL 对象
     * @see #registerTexture(String, File)
     * @see #registerTexture(String, InputStream)
     * @see #registerTexture(String, String, InputStream)
     */
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
        if(!bufferedImageSwap.isEmpty()) {
            bufferedImageSwapSp.acquireUninterruptibly();
            for (Entry<String, BufferedImage> entry : bufferedImageSwap.entrySet()) {
                try{
                    Texture texture = AWTTextureIO.newTexture(gl.getGLProfile(), entry.getValue(), false);
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
}