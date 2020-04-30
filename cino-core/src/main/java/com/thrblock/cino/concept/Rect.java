package com.thrblock.cino.concept;

public class Rect extends Polygon {
    public Rect(Point... points) {
        super(points);
        if (points.length != 4) {
            throw new IllegalArgumentException("rect must have 4 points");
        }
    }

    public Rect(float x, float y, float w, float h) {
        this(new Point(x - w / 2, y + h / 2), 
             new Point(x + w / 2, y + h / 2), 
             new Point(x + w / 2, y - h / 2),
             new Point(x - w / 2, y - h / 2));
    }

    /**
     * 获得矩形宽度
     * 
     * @return 矩形宽度
     */
    public float getWidth() {
        return points[0].getDistance(points[1]);
    }

    /**
     * 获得矩形高度
     * 
     * @return 矩形高度
     */
    public float getHeight() {
        return points[1].getDistance(points[2]);
    }

    /**
     * 设置 矩形宽度
     * 
     * @param width 矩形宽度
     */
    public void setWidth(float width) {
        if (width > 0) {
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
     * 
     * @param height 矩形高度
     */
    public void setHeight(float height) {
        if (height > 0) {
            float m = getHeight();
            float k = height / m;

            points[2].setX(k * (points[2].getX() - points[1].getX()) + points[1].getX());
            points[3].setX(k * (points[3].getX() - points[0].getX()) + points[0].getX());
            points[2].setY(k * (points[2].getY() - points[1].getY()) + points[1].getY());
            points[3].setY(k * (points[3].getY() - points[0].getY()) + points[0].getY());
        }
    }

    public void leftOf(Rect another) {
        leftOf(another, 0);
    }

    public void leftOfInner(Rect another) {
        leftOfInner(another, 0);
    }

    /**
     * 将此矩形放置于另一矩形的左侧
     * 
     * @param another 另一矩形
     */
    public void leftOf(Rect another, float margin) {
        sameStatusOf(another);
        float w = (getWidth() + another.getWidth()) / 2 + margin;
        float yoffset = (float) Math.sin(getRadian()) * w;
        float xoffset = (float) Math.cos(getRadian()) * w;
        setCentralX(getCentralX() - xoffset);
        setCentralY(getCentralY() - yoffset);
    }

    /**
     * 将此矩形放置于另一矩形的内左侧
     * 
     * @param another 另一矩形
     */
    public void leftOfInner(Rect another, float margin) {
        sameStatusOf(another);
        float w = (another.getWidth() - getWidth()) / 2 - margin;
        float yoffset = (float) Math.sin(getRadian()) * w;
        float xoffset = (float) Math.cos(getRadian()) * w;
        setCentralX(getCentralX() - xoffset);
        setCentralY(getCentralY() - yoffset);
    }

    public void rightOf(Rect another) {
        rightOf(another, 0);
    }

    /**
     * 将此矩形放置于另一矩形的右侧
     * 
     * @param another 另一矩形
     */
    public void rightOf(Rect another, float margin) {
        sameStatusOf(another);
        float w = (getWidth() + another.getWidth()) / 2 + margin;
        float yoffset = (float) Math.sin(getRadian()) * w;
        float xoffset = (float) Math.cos(getRadian()) * w;
        setCentralX(getCentralX() + xoffset);
        setCentralY(getCentralY() + yoffset);
    }

    public void rightOfInner(Rect another) {
        rightOfInner(another, 0);
    }

    /**
     * 将此矩形放置于另一矩形的右侧
     * 
     * @param another 另一矩形
     */
    public void rightOfInner(Rect another, float margin) {
        sameStatusOf(another);
        float w = (another.getWidth() - getWidth()) / 2 - margin;
        float yoffset = (float) Math.sin(getRadian()) * w;
        float xoffset = (float) Math.cos(getRadian()) * w;
        setCentralX(getCentralX() + xoffset);
        setCentralY(getCentralY() + yoffset);
    }

    public void topOf(Rect another) {
        topOf(another, 0);
    }

    /**
     * 将此矩形放置于另一矩形的上边
     * 
     * @param another 另一矩形
     */
    public void topOf(Rect another, float margin) {
        sameStatusOf(another);
        float h = (getHeight() + another.getHeight()) / 2 + margin;
        float yoffset = (float) Math.cos(getRadian()) * h;
        float xoffset = (float) Math.sin(getRadian()) * h;
        setCentralX(getCentralX() - xoffset);
        setCentralY(getCentralY() + yoffset);
    }

    public void topOfInner(Rect another) {
        topOfInner(another, 0);
    }

    /**
     * 将此矩形放置于另一矩形的上边
     * 
     * @param another 另一矩形
     */
    public void topOfInner(Rect another, float margin) {
        sameStatusOf(another);
        float h = (another.getHeight() - getHeight()) / 2 - margin;
        float yoffset = (float) Math.cos(getRadian()) * h;
        float xoffset = (float) Math.sin(getRadian()) * h;
        setCentralX(getCentralX() - xoffset);
        setCentralY(getCentralY() + yoffset);
    }

    public void bottomOf(Rect another) {
        bottomOf(another, 0);
    }

    /**
     * 将此矩形放置于另一矩形的上边
     * 
     * @param another 另一矩形
     */
    public void bottomOf(Rect another, float margin) {
        sameStatusOf(another);
        float h = (getHeight() + another.getHeight()) / 2 + margin;
        float yoffset = (float) Math.cos(getRadian()) * h;
        float xoffset = (float) Math.sin(getRadian()) * h;
        setCentralX(getCentralX() + xoffset);
        setCentralY(getCentralY() - yoffset);
    }

    public void bottomOfInner(Rect another) {
        bottomOfInner(another, 0);
    }

    /**
     * 将此矩形放置于另一矩形的上边
     * 
     * @param another 另一矩形
     */
    public void bottomOfInner(Rect another, float margin) {
        sameStatusOf(another);
        float h = (another.getHeight() - getHeight()) / 2 - margin;
        float yoffset = (float) Math.cos(getRadian()) * h;
        float xoffset = (float) Math.sin(getRadian()) * h;
        setCentralX(getCentralX() + xoffset);
        setCentralY(getCentralY() - yoffset);
    }
}
