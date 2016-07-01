package com.thrblock.cino.glshape;

import com.thrblock.cino.gltexture.IGLTextureContainer;

/**
 * 一个精灵图形对象，精灵对象包含多个贴图纹理分组，并提供组切换和纹理切换的功能
 * @author lizepu
 */
public class GLSprite extends GLImage {
    private String[][] textureName;
    private int currentTextureGroup = 0;
    private int[] rate;
    private int[] crtTextureIndex;
    private int fragCount = 0;
    /**
     * 构造一个精灵图形对象
     * @param textureContainer 纹理容器管理器
     * @param x 中心坐标x
     * @param y 中心坐标y
     * @param width 宽度
     * @param height 高度
     * @param textureName 初始纹理名称
     * @param rate 组帧间隔，定义各组的播放频率，以一个整数定义下次切换纹理的跳过帧数
     */
    public GLSprite(IGLTextureContainer textureContainer, float x, float y, float width, float height,
            String[][] textureName,int[] rate) {
        super(textureContainer, x, y, width, height, textureName[0][0]);
        this.textureName = textureName;
        this.rate = rate;
        this.crtTextureIndex = new int[textureName.length];
        this.vertX();//使用了不同于图片的纹理加载器，绑定的定点有所不同
    }
    
    /**
     * 设置 纹理组 索引
     * @param index 纹理组索引
     */
    public void setTextureGroupIndex(int index) {
        if(index >= 0 && index < textureName.length) {
            this.currentTextureGroup = index;
        }
    }
    
    private boolean skipFrag() {
        if(fragCount < rate[currentTextureGroup]) {
            fragCount ++;
            return false;
        } else {
            fragCount = 0;
            return true;
        }
    }
    
    /**
     * 进行纹理切换，此逻辑需要挂载至同步逻辑
     * @return 是否切换完成的布尔值
     */
    public boolean fragment() {
        boolean result = false;
        if(skipFrag()) {
            crtTextureIndex[currentTextureGroup]++;
            if(crtTextureIndex[currentTextureGroup] >= textureName[currentTextureGroup].length) {
                result = true;
                crtTextureIndex[currentTextureGroup] = 0;
            }
            this.setTextureName(textureName[currentTextureGroup][crtTextureIndex[currentTextureGroup]]);
        }
        return result;
    }

}
