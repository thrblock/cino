package com.thrblock.cino.glshape;

import com.thrblock.cino.concept.Point;
import com.thrblock.cino.concept.Rect;

/**
 * 一个矩形图形对象
 * 
 * @author lizepu
 */
public class GLRect extends GLPolygonShape<Rect> {

    public GLRect(Rect r) {
        super(r);
    }

    /**
     * 构造一个矩形图形对象
     * 
     * @param x      中心横坐标
     * @param y      中心纵坐标
     * @param width  宽度
     * @param height 高度
     */
    public GLRect(float x, float y, float width, float height) {
        super(new Rect(new Point(x - width / 2, y + height / 2), new Point(x + width / 2, y + height / 2),
                new Point(x + width / 2, y - height / 2), new Point(x - width / 2, y - height / 2)));
    }

    /**
     * 获得矩形宽度
     * 
     * @return 矩形宽度
     */
    public float getWidth() {
        return concept.getWidth();
    }

    /**
     * 获得矩形高度
     * 
     * @return 矩形高度
     */
    public float getHeight() {
        return concept.getHeight();
    }

    /**
     * 设置 矩形宽度
     * 
     * @param width 矩形宽度
     */
    public void setWidth(float width) {
        concept.setWidth(width);
    }

    /**
     * 设置 矩形高度
     * 
     * @param height 矩形高度
     */
    public void setHeight(float height) {
        concept.setHeight(height);
    }

    public void leftOf(GLRect another) {
        concept.leftOf(another.concept, 0);
    }

    public void leftOfInner(GLRect another) {
        concept.leftOfInner(another.concept, 0);
    }

    /**
     * 将此矩形放置于另一矩形的左侧
     * 
     * @param another 另一矩形
     */
    public void leftOf(GLRect another, float margin) {
        concept.leftOf(another.concept, margin);
    }

    /**
     * 将此矩形放置于另一矩形的内左侧
     * 
     * @param another 另一矩形
     */
    public void leftOfInner(GLRect another, float margin) {
        concept.leftOfInner(another.concept, margin);
    }

    public void rightOf(GLRect another) {
        concept.rightOf(another.concept, 0);
    }

    /**
     * 将此矩形放置于另一矩形的右侧
     * 
     * @param another 另一矩形
     */
    public void rightOf(GLRect another, float margin) {
        concept.rightOf(another.concept, margin);
    }

    public void rightOfInner(GLRect another) {
        concept.rightOfInner(another.concept);
    }

    /**
     * 将此矩形放置于另一矩形的右侧
     * 
     * @param another 另一矩形
     */
    public void rightOfInner(GLRect another, float margin) {
        concept.rightOfInner(another.concept, margin);
    }

    public void topOf(GLRect another) {
        concept.topOf(another.concept, 0);
    }

    /**
     * 将此矩形放置于另一矩形的上边
     * 
     * @param another 另一矩形
     */
    public void topOf(GLRect another, float margin) {
        concept.topOf(another.concept, margin);
    }

    public void topOfInner(GLRect another) {
        concept.topOfInner(another.concept, 0);
    }

    /**
     * 将此矩形放置于另一矩形的上边
     * 
     * @param another 另一矩形
     */
    public void topOfInner(GLRect another, float margin) {
        concept.topOfInner(another.concept, margin);
    }

    public void bottomOf(GLRect another) {
        concept.bottomOf(another.concept, 0);
    }

    /**
     * 将此矩形放置于另一矩形的上边
     * 
     * @param another 另一矩形
     */
    public void bottomOf(GLRect another, float margin) {
        concept.bottomOf(another.concept, margin);
    }

    public void bottomOfInner(GLRect another) {
        concept.bottomOfInner(another.concept, 0);
    }

    /**
     * 将此矩形放置于另一矩形的上边
     * 
     * @param another 另一矩形
     */
    public void bottomOfInner(GLRect another, float margin) {
        concept.bottomOfInner(another.concept, margin);
    }
}
