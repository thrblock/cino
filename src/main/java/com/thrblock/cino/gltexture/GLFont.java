package com.thrblock.cino.gltexture;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL;
import com.thrblock.cino.function.CharFunction;
import com.thrblock.cino.glinitable.GLInitializable;

/**
 * 字体字库对应的实体累，此类规定的使用的字体、预加载的内容以及将char类型映射到OpenGL Texture的方法过程
 * 
 * @author zepu.li
 */
public class GLFont implements GLInitializable {
    private static final Canvas CANVAS = new Canvas();
    private StringBuilder preLoadBuilder = new StringBuilder();
    private FontGenNode genNode;

    /**
     * 基于awt字体对象 实例化GLFont 此对象代表着字体及伴随此字体设置对应的纹理
     * 
     * @param f
     *            awt字体对象
     */
    public GLFont(Font f) {
        this.genNode = new FontGenNode(CANVAS.getFontMetrics(f));
    }

    /**
     * 基于awt字体对象 预加载的字符集 实例化GLFont
     * 
     * @param f
     *            awt字体对象
     * @param preLoad
     *            预加载的字符集
     */
    public GLFont(Font f, char[] preLoad) {
        this(f);
        preLoadBuilder.append(preLoad);
    }
    
    public GLFont(CharFunction<BufferedImage> mapper,char[] preLoad) {
        this.genNode = new FontGenNode(mapper,preLoad[0]);
        preLoadBuilder.append(preLoad);
    }

    /**
     * 追加一组预加载文字
     * 
     * @param preLoad
     */
    public void append(char[] preLoad) {
        preLoadBuilder.append(preLoad);
    }

    @Override
    public void initByGLContext(GL gl) {
        genNode.load(gl, preLoadBuilder.toString().toCharArray());
    }

    /**
     * 透传
     * 
     * @param gl
     * @param c
     * @return
     */
    public GLTexture getCharTexture(char c) {
        return genNode.genTexture(c);
    }
    
    public int getFmheight() {
        return genNode.getFmheight();
    }
}