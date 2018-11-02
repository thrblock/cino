package com.thrblock.cino.glshape.proxy;

import com.thrblock.cino.glshape.GLRect;
/**
 * 矩形位置操作代理类
 * @author lizepu
 */
public class RectPositionProxy {
    private GLRect rect;
    /**已原始矩形构造位置操作代理
     * @param rect
     */
    public RectPositionProxy(GLRect rect) {
        this.rect = rect;
    }
    /**
     * 将此矩形放置于另一矩形的左侧
     * @param another 另一矩形
     * @param margin 间隔
     */
    public void leftOf(GLRect another,float margin) {
        leftOf(another);
        rect.setXOffset(-margin);
    }
    
    /**
     * 将此矩形放置于另一矩形的左侧
     * @param another 另一矩形
     */
    public void leftOf(GLRect another) {
        float cx = another.getCentralX() - another.getWidth() / 2 - rect.getWidth() / 2;
        rect.setCentralX(cx);
        rect.setCentralY(another.getCentralY());
    }
    
    /**
     * 将此矩形放置于另一矩形的右侧
     * @param another 另一矩形
     * @param margin 间隔
     */
    public void rightOf(GLRect another,float margin) {
        rightOf(another);
        rect.setXOffset(margin);
    }
    /**
     * 将此矩形放置于另一矩形的右侧
     * @param another 另一矩形
     */
    public void rightOf(GLRect another) {
        float cx = another.getCentralX() + another.getWidth() / 2 + rect.getWidth() / 2;
        rect.setCentralX(cx);
        rect.setCentralY(another.getCentralY());
    }
    /**
     * 将此矩形放置于另一矩形的上侧
     * @param another 另一矩形
     * @param margin 间隔
     */
    public void topOf(GLRect another,float margin) {
        topOf(another);
        rect.setYOffset(-margin);
    }
    /**
     * 将此矩形放置于另一矩形的上侧
     * @param another 另一矩形
     */
    public void topOf(GLRect another) {
        float cy = another.getCentralY() - another.getWidth() / 2 - rect.getWidth() / 2;
        rect.setCentralX(another.getCentralX());
        rect.setCentralY(cy);
    }
    
    /**
     * 将此矩形放置于另一矩形的底部
     * @param another 另一矩形
     * @param margin 间距
     */
    public void bottomOf(GLRect another,float margin) {
        bottomOf(another);
        rect.setYOffset(margin);
    }
    
    /**
     * 将此矩形放置于另一矩形的底部
     * @param another 另一矩形
     */
    public void bottomOf(GLRect another) {
        float cy = another.getCentralY() + another.getWidth() / 2 + rect.getWidth() / 2;
        rect.setCentralX(another.getCentralX());
        rect.setCentralY(cy);
    }
    
    /**
     * 将此矩形放置于屏幕左侧
     * @param margin 距离左侧的间距
     */
    public void marginLeft(float margin) {
        rect.setCentralX(rect.getWidth() / 2 + margin);
    }
    
    /**
     * 将此矩形放置于屏幕顶部
     * @param margin 距顶部的间距
     */
    public void marginTop(float margin) {
        rect.setCentralY(rect.getHeight() / 2 + margin);
    }
}
