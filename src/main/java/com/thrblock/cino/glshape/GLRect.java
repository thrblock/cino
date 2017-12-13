package com.thrblock.cino.glshape;

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
    
    public void rightOf(GLRect another) {
        rightOf(another,0);
    }
    /**
     * 将此矩形放置于另一矩形的左侧
     * @param another 另一矩形
     */
    public void rightOf(GLRect another,float margin) {
        sameStatusOf(another);
        float w = (getWidth() + another.getWidth()) / 2 + margin;
        float yoffset = (float) Math.sin(getRadian()) * w;
        float xoffset = (float) Math.cos(getRadian()) * w;
        setCentralX(getCentralX() - xoffset);
        setCentralY(getCentralY() - yoffset);
    }
    
    public void leftOf(GLRect another) {
        leftOf(another,0);
    }
    
    /**
     * 将此矩形放置于另一矩形的右侧
     * @param another 另一矩形
     */
    public void leftOf(GLRect another,float margin) {
        sameStatusOf(another);
        float w = (getWidth() + another.getWidth()) / 2 + margin;
        float yoffset = (float) Math.sin(getRadian()) * w;
        float xoffset = (float) Math.cos(getRadian()) * w;
        setCentralX(getCentralX() + xoffset);
        setCentralY(getCentralY() + yoffset);
    }
    
    public void topOf(GLRect another) {
        topOf(another,0);
    }
    /**
     * 将此矩形放置于另一矩形的上边
     * @param another 另一矩形
     */
    public void topOf(GLRect another,float margin) {
        sameStatusOf(another);
        float h = (getHeight() + another.getHeight()) / 2 + margin;
        float yoffset = (float) Math.cos(getRadian()) * h;
        float xoffset = (float) Math.sin(getRadian()) * h;
        setCentralX(getCentralX() - xoffset);
        setCentralY(getCentralY() + yoffset);
    }
    
    public void bottomOf(GLRect another) {
        bottomOf(another,0);
    }
    /**
     * 将此矩形放置于另一矩形的上边
     * @param another 另一矩形
     */
    public void bottomOf(GLRect another,float margin) {
        sameStatusOf(another);
        float h = (getHeight() + another.getHeight()) / 2 + margin;
        float yoffset = (float) Math.cos(getRadian()) * h;
        float xoffset = (float) Math.sin(getRadian()) * h;
        setCentralX(getCentralX() + xoffset);
        setCentralY(getCentralY() - yoffset);
    }
}
