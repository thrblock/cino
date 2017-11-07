package com.thrblock.cino.glshape;

import com.thrblock.cino.glshape.proxy.RectPositionProxy;

/**
 * 一个矩形图形对象
 * @author lizepu
 */
public class GLRect extends GLPolygonShape {
    /**
     * 构造一个矩形图形对象
     * @param x 中心横坐标
     * @param y 中心纵坐标
     * @param width 宽度
     * @param height 高度
     */
    public GLRect(float x,float y,float width,float height) {
        super(new GLPoint(x - width / 2,y + height / 2),
              new GLPoint(x + width / 2,y + height / 2),
              new GLPoint(x + width / 2,y - height / 2),
              new GLPoint(x - width / 2,y - height / 2));
    }
    
    /**
     * 获得矩形宽度
     * @return 矩形宽度
     */
    public float getWidth() {
        return points[0].getDistance(points[1]);
    }
    
    /**
     * 获得矩形高度
     * @return 矩形高度
     */
    public float getHeight() {
        return points[1].getDistance(points[2]);
    }
    
    /**
     * 设置 矩形宽度
     * @param width 矩形宽度
     */
    public void setWidth(float width) {
        if(width > 0) {
            float m = getWidth();
            float k = width / m;
            
            points[1].setX(k * (points[1].getX() - points[0].getX()) + points[0].getX());
            points[2].setX(k * (points[2].getX() - points[3].getX()) + points[3].getX());
            points[1].setY(k * (points[1].getY() - points[0].getY()) + points[0].getY());
            points[2].setY(k * (points[2].getY() - points[3].getY()) + points[3].getY());
        }
    }
    
    /**
     * 设置 矩形高度
     * @param height 矩形高度
     */
    public void setHeight(float height) {
        if(height > 0) {
            float m = getHeight();
            float k = height / m;
            
            points[2].setX(k * (points[2].getX() - points[1].getX()) + points[1].getX());
            points[3].setX(k * (points[3].getX() - points[0].getX()) + points[0].getX());
            points[2].setY(k * (points[2].getY() - points[1].getY()) + points[1].getY());
            points[3].setY(k * (points[3].getY() - points[0].getY()) + points[0].getY());
        }
    }
    
    /**
     * 获得 矩形位置操作代理类
     * @return 矩形位置操作代理类
     */
    public RectPositionProxy proxyRectPosition (){
        return new RectPositionProxy(this);
    }
}
