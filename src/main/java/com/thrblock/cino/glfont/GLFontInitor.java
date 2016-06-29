package com.thrblock.cino.glfont;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.gltexture.IGLTextureContainer;
/**
 * 字体的Spring加载器
 * 字体被定义为png的精确切片集合，程序在初始化时会将所有png块加载为纹理，并被相同的文字共用<br />
 * 切片的设计并不够好，增加了打包负担，使用切片的主要原因是ImageIO需要使用图片流进行解码操作<br />
 * 目前并未提供动态文字生成特性，即程序内的文字要么为图片，要么属于提前定义的文字集合，这影响了程序拓展性<br />
 * @author lizepu
 */
@Component
public abstract class GLFontInitor {
    protected Logger logger;
    @Autowired
    private IGLTextureContainer textureContainer;
    /**
     * 构造一个initor,初始化logger
     */
    public GLFontInitor() {
        logger = LoggerFactory.getLogger(getClass());
    }
    /**
     * 将png切片转换为纹理
     */
    @PostConstruct
    public void init() {
        for(String name:getName()) {
            GLFontTexture ft = getFontTexture(name);
            if(ft != null) {
                textureContainer.registerFont(name, ft);
            }
        }
    }
    /**
     * 根据名称，获得对应的GLFontTexture对象<br />
     * 名称，通常情况下包括字体、样式及字号，一个initor可包含多个名称<br />
     * 一个名称应能关联到一个GLFontTexture对象的创建过程
     * @param name 名称<br />
     * @return GLFontTexture对象
     * @see CinoTagFontInitor 一个名称转换的加载实例
     */
    protected abstract GLFontTexture getFontTexture(String name);
    /**
     * 获得initor包含的全部名称
     * @return
     */
    protected abstract String[] getName();
}
