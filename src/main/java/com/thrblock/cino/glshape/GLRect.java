package com.thrblock.cino.glshape;

import com.thrblock.cino.glshape.builder.GLNode;

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
        super(new GLPoint[]{
                new GLPoint(x - width / 2,y - height / 2),
                new GLPoint(x + width / 2,y - height / 2),
                new GLPoint(x + width / 2,y + height / 2),
                new GLPoint(x - width / 2,y + height / 2)
                });
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
    
    public PositionProxy getPositionProxy (){
        return new PositionProxy();
    }
    
    public class PositionProxy {
        public void sameCentrerOf(GLNode another) {
            setCentralX(another.getCentralX());
            setCentralY(another.getCentralY());
        }
        
        public void leftOf(GLRect another,float margin) {
            leftOf(another);
            setXOffset(-margin);
        }
        
        public void leftOf(GLRect another) {
            float cx = another.getCentralX() - another.getWidth() / 2 - getWidth() / 2;
            setCentralX(cx);
            setCentralY(another.getCentralY());
        }
        
        public void rightOf(GLRect another,float margin) {
            rightOf(another);
            setXOffset(margin);
        }
        
        public void rightOf(GLRect another) {
            float cx = another.getCentralX() + another.getWidth() / 2 + getWidth() / 2;
            setCentralX(cx);
            setCentralY(another.getCentralY());
        }
        
        public void topOf(GLRect another,float margin) {
            topOf(another);
            setYOffset(-margin);
        }
        
        public void topOf(GLRect another) {
            float cy = another.getCentralY() - another.getWidth() / 2 - getWidth() / 2;
            setCentralX(another.getCentralX());
            setCentralY(cy);
        }
        
        public void bottomOf(GLRect another,float margin) {
            bottomOf(another);
            setYOffset(margin);
        }
        
        public void bottomOf(GLRect another) {
            float cy = another.getCentralY() + another.getWidth() / 2 + getWidth() / 2;
            setCentralX(another.getCentralX());
            setCentralY(cy);
        }
        
        public void marginLeft(float margin) {
            setCentralX(getWidth() / 2 + margin);
        }
        
        public void marginTop(float margin) {
            setCentralY(getHeight() / 2 + margin);
        }
    }
}
