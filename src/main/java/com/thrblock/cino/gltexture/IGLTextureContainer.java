package com.thrblock.cino.gltexture;

import java.io.File;
import java.io.InputStream;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.glfont.GLFontTexture;

/**
 * 纹理容器管理器（抽象层）<br />
 * 同样感觉没什么用的抽象...
 * @author lizepu
 */
public interface IGLTextureContainer {
    /**
     * 由名称获得纹理对象
     * @param name 纹理名称
     * @return 纹理对象
     */
    public Texture getTexture(String name);
    /**
     * 进行 资源（文件、流等）到纹理的转换，由于使用OpenGL上下文，该过程尽在绘制结束后进行<br />
     * 由于生成纹理需要时间较多，因此当需要载入较大或较多的资源时，要使用预加载方式
     * @param gl OpenGL 对象
     * @see #registerTexture(String, File)
     * @see #registerTexture(String, InputStream)
     * @see #registerTexture(String, String, InputStream)
     */
    public void parseTexture(GL2 gl);
    /**
     * 获得文字纹理
     * @param name 文字名称
     * @return 文字纹理对象，包括多个纹理
     */
    public GLFontTexture getGLFontTexture(String name);
    
    /**
     * 注册一个纹理对象
     * @param name 纹理名称
     * @param srcStream 资源流
     */
    public void registerTexture(String name,InputStream srcStream);
    /**
     * 注册一个纹理对象
     * @param name 纹理名称
     * @param imgType 资源类型 如‘png’
     * @param srcStream 资源流
     */
    public void registerTexture(String name,String imgType,InputStream srcStream);
    /**
     * 注册一个纹理对象
     * @param name 纹理名称
     * @param imgFile 图像文件
     */
    public void registerTexture(String name,File imgFile);
    
    /**
     * 使用Gif文件注册一组纹理对象
     * @param name Gif纹理名称
     * @param gifFile Gif文件
     * @return GifMetaData Gif源信息
     */
    public GifMetaData registerGifAsTexture(String name,File gifFile);
    /**
     * 使用Gif流注册一组纹理对象
     * @param name Gif纹理名称
     * @param srcStream Gif流
     * @return GifMetaData Gif源信息
     */
    public GifMetaData registerGifAsTexture(String name,InputStream srcStream);
    
    /**
     * 注册一个字体
     * @param name 字体名称
     * @param fontTexture 字体纹理对象
     */
    public void registerFont(String name,GLFontTexture fontTexture);
    
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
}
