package com.thrblock.cino.glshape.factory;

import com.thrblock.cino.concept.Line;
import com.thrblock.cino.concept.Point;
import com.thrblock.cino.concept.Polygon;
import com.thrblock.cino.concept.Rect;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLPolygonShape;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.GLTriangle;
import com.thrblock.cino.gltexture.GLTexture;
import com.thrblock.cino.lnode.LNode;
import com.thrblock.cino.vec.Vec2;

/**
 * 图形构造器
 * 
 * @author lizepu
 */
public class GLShapeFactory {
    private ShapeBeanFactory beanFactory;

    private LNode node;

    public GLShapeFactory(LNode node, ShapeBeanFactory beanFactory) {
        this.node = node;
        this.beanFactory = beanFactory;
    }

    /**
     * 构造一个点
     * 
     * @param x 横坐标
     * @param y 纵坐标
     * @return 点图形
     */
    public GLPoint buildGLPoint(float x, float y) {
        GLPoint point = beanFactory.glPoint(node, x, y);
        return point;
    }

    /**
     * 构造一个点
     * 
     * @param p 概念点
     * @return
     */
    public GLPoint buildGLPoint(Point pt) {
        GLPoint point = beanFactory.glPoint(node, pt);
        return point;
    }

    /**
     * 构造一个点
     * 
     * @param xy vec2
     * @return
     */
    public GLPoint buildGLPoint(Vec2 xy) {
        return buildGLPoint(xy.getX(), xy.getY());
    }

    /**
     * 构造一条直线
     * 
     * @param x1 横坐标1
     * @param y1 纵坐标1
     * @param x2 横坐标2
     * @param y2 纵坐标2
     * @return 直线图形
     */
    public GLLine buildGLLine(float x1, float y1, float x2, float y2) {
        GLLine line = beanFactory.glLine(node, x1, y1, x2, y2);
        return line;
    }

    /**
     * 构造一条直线
     * 
     * @param l 概念线
     * @return
     */
    public GLLine buildGLLine(Line l) {
        GLLine line = beanFactory.glLine(node, l);
        return line;
    }

    /**
     * 构造一条直线
     * 
     * @param start 起始点
     * @param end   终点
     * @return
     */
    public GLLine buildGLLine(Vec2 start, Vec2 end) {
        return buildGLLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    /**
     * 构造一个矩形
     * 
     * @param x      中心坐标x
     * @param y      中心坐标y
     * @param width  宽度
     * @param height 高度
     * @return 矩形图形
     */
    public GLRect buildGLRect(float x, float y, float width, float height) {
        GLRect rect = beanFactory.glRect(node, x, y, width, height);
        return rect;
    }

    /**
     * 构造一个矩形
     * 
     * @param r 概念矩形
     * @return
     */
    public GLRect buildGLRect(Rect r) {
        GLRect rect = beanFactory.glRect(node, r);
        return rect;
    }

    /**
     * 在原点构造一个矩形
     * 
     * @param width  宽度
     * @param height 高度
     * @return
     */
    public GLRect buildGLRect(float width, float height) {
        return buildGLRect(0, 0, width, height);
    }

    /**
     * 构造一个椭圆
     * 
     * @param x        中心坐标x
     * @param y        中心坐标y
     * @param axisA    长轴
     * @param axisB    短轴
     * @param accuracy 精度，即使用点的个数
     * @return 椭圆对象
     */
    public GLOval buildGLOval(float x, float y, float axisA, float axisB, int accuracy) {
        GLOval oval = beanFactory.glOval(node, x, y, axisA, axisB, accuracy);
        return oval;
    }

    /**
     * 在原点构造一个椭圆
     * 
     * @param axisA    长轴
     * @param axisB    短轴
     * @param accuracy 精确度
     * @return
     */
    public GLOval buildGLOval(float axisA, float axisB, int accuracy) {
        return buildGLOval(0, 0, axisA, axisB, accuracy);
    }

    /**
     * 在原点构造一个圆
     * 
     * @param diameter 直径
     * @param accuracy 精确度
     * @return
     */
    public GLOval buildGLOval(float diameter, int accuracy) {
        return buildGLOval(diameter, diameter, accuracy);
    }

    /**
     * 创建一个贴图对象
     * 
     * @param x           中心坐标x
     * @param y           中心坐标y
     * @param width       宽度
     * @param height      高度
     * @param textureName 使用的贴图纹理
     * @return 贴图对象
     */
    public GLImage buildGLImage(float x, float y, float width, float height, GLTexture texture) {
        GLImage image = beanFactory.glImage(node, x, y, width, height, texture);
        return image;
    }

    /**
     * 创建一个贴图对象
     * 
     * @param r       概念矩形
     * @param texture 使用的贴图纹理
     * @return
     */
    public GLImage buildGLImage(Rect r, GLTexture texture) {
        GLImage image = beanFactory.glImage(node, r, texture);
        return image;
    }

    /**
     * 使用空纹理构造一个贴图对象
     * 
     * @return 贴图对象
     */
    public GLImage buildGLImage() {
        GLImage image = beanFactory.glImage(node);
        return image;
    }

    /**
     * 使用空纹理构造一个贴图对象
     * 
     * @return 贴图对象
     */
    public GLImage buildGLImage(float w, float h) {
        GLImage image = beanFactory.glImage(node, w, h);
        return image;
    }

    /**
     * 创建一个三角形
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @return
     */
    public GLTriangle buildGLTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        GLTriangle triangle = beanFactory.glTriangle(node, x1, y1, x2, y2, x3, y3);
        return triangle;
    }

    public GLTriangle buildGLTriangle(Vec2 p1, Vec2 p2, Vec2 p3) {
        return buildGLTriangle(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
    }

    /**
     * 创建一个封闭多边形
     * 
     * @param xs
     * @param ys
     * @return
     */
    public GLPolygonShape<Polygon> buildGLPolygon(float[] xs, float[] ys) {
        GLPolygonShape<Polygon> polygon = beanFactory.glPolygon(node, xs, ys);
        return polygon;
    }

    public GLPolygonShape<Polygon> buildGLPolygon(Vec2... points) {
        GLPolygonShape<Polygon> polygon = beanFactory.glPolygon(node, points);
        return polygon;
    }
}
