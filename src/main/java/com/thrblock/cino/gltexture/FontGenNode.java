package com.thrblock.cino.gltexture;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

/**
 * 可生成固定字体的纹理
 * @author zepu.li
 */
public class FontGenNode {
    private FontMetrics fm;
    private Texture[] textures = new Texture[65535];//用512KB换取O(1)性能
    
    /**
     * 字体节点
     * @param fm 文字韵
     */
    public FontGenNode(FontMetrics fm) {
        this.fm = fm;
    }

    private BufferedImage genImage(char c) {
        BufferedImage charBuffer = new BufferedImage(fm.charWidth(c), fm.getAscent() + fm.getDescent(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) charBuffer.getGraphics();
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        g2.setFont(fm.getFont());
        g2.setColor(Color.WHITE);
        g2.drawString(Character.toString(c), 0, fm.getAscent());
        
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -charBuffer.getHeight()));
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(charBuffer, null);
    }
    
    /**
     * 创建文字纹理，当存在时直接由数组给出
     * @param gl GL对象
     * @param c 文字
     * @return 纹理对象
     */
    public Texture genTexture(GL gl,char c) {
        if(textures[c] == null) {
            textures[c] = AWTTextureIO.newTexture(gl.getGLProfile(), genImage(c), false);
            textures[c].setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
            textures[c].setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
        }
        return textures[c];
    }
    
    /**
     * 预加载
     * @param gl
     * @param arr
     */
    public void load(GL gl,char[] arr) {
        for(int i = 0;i < arr.length;i++) {
            genTexture(gl,arr[i]);
        }
    }
}
