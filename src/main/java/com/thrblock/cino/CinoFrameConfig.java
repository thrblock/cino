package com.thrblock.cino;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * GL渲染窗体设置
 * @author thrblock
 *
 */
@Component
public class CinoFrameConfig {
    @Value("${cino.frame.title:title}")
    private String frameTitle = "title";
    /**
     * 开启双缓冲
     */
    @Value("${cino.frame.doublebuffer:true}")
    private boolean doubleBuffer = true;
    /**
     * 开启垂直同步
     */
    @Value("${cino.frame.vsync:false}")
    private boolean vsync = false;
    /**
     * 是否全屏
     */
    @Value("${cino.frame.fullscreen:false}")
    private boolean fullScreen = false;
    /**
     * FPS 每秒绘制速率，垂直同步开启时无效
     */
    @Value("${cino.frame.fps:60}")
    private int framesPerSecond = 60;
    /**
     * 窗体是否可变，全屏时无效
     */
    @Value("${cino.frame.flexible:false}")
    private boolean flexible = false;
    /**
     * 隐藏鼠标
     */
    @Value("${cino.frame.hidemouse:true}")
    private boolean hideMouse = true;
    
    /**
     * 渲染位置宽度 （像素）
     */
    @Value("${cino.frame.screen.width:800}")
    private int screenWidth = 800;
    /**
     * 渲染位置高度（像素）
     */
    @Value("${cino.frame.screen.height:600}")
    private int screenHeight = 600;
    
    public String getFrameTitle() {
        return frameTitle;
    }

    public boolean isDoubleBuffer() {
        return doubleBuffer;
    }

    public boolean isVsync() {
        return vsync;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public boolean isFlexible() {
        return flexible;
    }

    public boolean isHideMouse() {
        return hideMouse;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
