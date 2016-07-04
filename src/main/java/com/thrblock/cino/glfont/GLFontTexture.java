package com.thrblock.cino.glfont;

import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * 一个字库纹理，包括了多个文字及对应的纹理
 * @author lizepu
 */
public class GLFontTexture {
    private Texture[] tarray;
    private char max;
    private char min;
    private Class<?> clazz;
    private char[] chars;
    private String name;
    /**
     * 字库纹理
     * @param clazz 字库纹理所在class
     * @param name 字库名称
     * @param chars 文字
     * @param max chars中的最小值
     * @param min chars中的最大值
     */
    public GLFontTexture(Class<?> clazz,String name,char[] chars,char max,char min) {
        tarray = new Texture[max - min + 1];
        this.name = name;
        this.clazz = clazz;
        this.chars = chars;
        this.max = max;
        this.min = min;
    }
    /**
     * 返回给定char类型字符的纹理
     * @param c 字符
     * @return 字符对应的纹理
     */
    public Texture getTexture(char c){
        if(c >= min && c <= max) {
            return tarray[c - min];
        } else {
            return tarray[min];
        }
    }
    /**
     * 将文字转换为纹理，一般在完成单次绘制后执行
     * @param gl OpenGL对象
     * @throws IOException 当IO异常时发生
     */
    public void loadFontAsTexture(GL2 gl) throws IOException {
        for(char c:chars) {
            try(InputStream is = clazz.getResourceAsStream(name + "/" + Integer.valueOf(c))){
                Texture texture = TextureIO.newTexture(is,false,"png");
                texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
                texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
                tarray[c - min] = texture;
            }
        }
    }
}
