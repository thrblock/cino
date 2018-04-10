package com.thrblock.cino.gltexture;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;
import com.thrblock.cino.function.CharFunction;
import com.thrblock.cino.glprocessor.GLEventProcessor;

/**
 * 可生成固定字体的纹理
 * 
 * @author zepu.li
 */
public class FontGenNode {
    private static final Logger LOG = LoggerFactory.getLogger(GLEventProcessor.class);
    private FontMetrics fm;
    private GLTexture[] textures = new GLTexture[128];
    private CharFunction<BufferedImage> imgGenerator;
    private int fmheight;

    /**
     * 字体节点
     * 
     * @param fm
     *            文字韵
     */
    public FontGenNode(FontMetrics fm) {
        this.fm = fm;
        this.fmheight = fm.getAscent() + fm.getDescent();
        this.fmheight = fmheight <= 0 ? 1 : fmheight;
        this.imgGenerator = this::genImage;
    }

    /**
     * 字体节点
     * 
     * @param fm
     *            文字韵
     * @param imgGenerator
     *            文字到图像的映射
     */
    public FontGenNode(CharFunction<BufferedImage> imgGenerator,char lenDetect) {
        this.imgGenerator = imgGenerator;
        this.fmheight = imgGenerator.apply(lenDetect).getHeight();
    }

    private BufferedImage genImage(char c) {
        int fmwidth = fm.charWidth(c) <= 0 ? 1 : fm.charWidth(c);
        BufferedImage charBuffer = new BufferedImage(fmwidth, fmheight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) charBuffer.getGraphics();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        g2.setFont(fm.getFont());
        g2.setColor(Color.WHITE);
        g2.drawString(Character.toString(c), 0, fm.getAscent());
        return charBuffer;
    }

    /**
     * 创建文字纹理，当存在时直接由数组给出
     * 
     * @param gl
     *            GL对象
     * @param c
     *            文字
     * @return 纹理对象
     */
    public GLTexture genTexture(char c) {
        checkRange(c);
        if (textures[c] == null) {
            textures[c] = new GLBufferedTexture(imgGenerator.apply(c));
        }
        return textures[c];
    }

    private void checkRange(char c) {
        if (c > textures.length) {// 若超出ASCII标准字符集
            GLTexture[] fullArr = new GLTexture[65535];// 用512KB换取O(1)性能
            System.arraycopy(textures, 0, fullArr, 0, textures.length);
            this.textures = fullArr;
            LOG.info("texture array expand to 65535 for font:{}",fm.getFont());
        }
    }

    /**
     * 预加载
     * 
     * @param gl
     * @param arr
     */
    public void load(GL gl, char[] arr) {
        for (int i = 0; i < arr.length; i++) {
            genTexture(arr[i]).initByGLContext(gl);
        }
    }

    public int getFmheight() {
        return fmheight;
    }

}
