package com.thrblock.cino.glshape.factory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.google.common.collect.Iterables;
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
import com.thrblock.cino.vec.Vec2;

@Configuration
class ShapeBeanFactory {

    /**
     * 构造一个点
     * 
     * @param x 横坐标
     * @param y 纵坐标
     * @return 点图形
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLPoint glPoint(int layer, float x, float y) {
        GLPoint point = new GLPoint(x, y);
        point.setLayerIndex(layer);
        return point;
    }

    /**
     * 构造一个点
     * 
     * @param p 概念点
     * @return
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLPoint glPoint(int layer, Point pt) {
        GLPoint point = new GLPoint(pt);
        point.setLayerIndex(layer);
        return point;
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
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLLine glLine(int layer, float x1, float y1, float x2, float y2) {
        GLLine line = new GLLine(x1, y1, x2, y2);
        line.setLayerIndex(layer);
        return line;
    }

    /**
     * 构造一条直线
     * 
     * @param l 概念线
     * @return
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLLine glLine(int layer, Line l) {
        GLLine line = new GLLine(l);
        line.setLayerIndex(layer);
        return line;
    }

    /**
     * 构造一个矩形
     * 
     * @param r 概念矩形
     * @return
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLRect glRect(int layer, Rect r) {
        GLRect rect = new GLRect(r);
        rect.setLayerIndex(layer);
        return rect;
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
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLOval glOval(int layer, float x, float y, float axisA, float axisB, int accuracy) {
        GLOval oval = GLOval.generate(x, y, axisA, axisB, accuracy);
        oval.setLayerIndex(layer);
        return oval;
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
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLImage glImage(int layer, float x, float y, float width, float height, GLTexture texture) {
        GLImage image = new GLImage(x, y, width, height, texture);
        image.setLayerIndex(layer);
        return image;
    }

    /**
     * 创建一个贴图对象
     * 
     * @param r       概念矩形
     * @param texture 使用的贴图纹理
     * @return
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLImage glImage(int layer, Rect r, GLTexture texture) {
        GLImage image = new GLImage(r, texture);
        image.setLayerIndex(layer);
        return image;
    }

    /**
     * 使用空纹理构造一个贴图对象
     * 
     * @return 贴图对象
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLImage glImage(int layer) {
        GLImage image = new GLImage();
        image.setLayerIndex(layer);
        return image;
    }

    /**
     * 使用空纹理构造一个贴图对象
     * 
     * @return 贴图对象
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLImage glImage(int layer, float w, float h) {
        GLImage image = new GLImage(w, h);
        image.setLayerIndex(layer);
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
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLTriangle glTriangle(int layer, float x1, float y1, float x2, float y2, float x3, float y3) {
        GLTriangle triangle = new GLTriangle(x1, y1, x2, y2, x3, y3);
        triangle.setLayerIndex(layer);
        return triangle;
    }

    /**
     * 创建一个封闭多边形
     * 
     * @param xs
     * @param ys
     * @return
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLPolygonShape<Polygon> glPolygon(int layer, float[] xs, float[] ys) {
        List<Point> pointList = new LinkedList<>();
        for (int i = 0; i < xs.length; i++) {
            pointList.add(new Point(xs[i], ys[i]));
        }
        Polygon concept = new Polygon(Iterables.toArray(pointList, Point.class));
        GLPolygonShape<Polygon> polygon = new GLPolygonShape<>(concept);
        polygon.setLayerIndex(layer);
        return polygon;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLPolygonShape<Polygon> glPolygon(int layer, Vec2... points) {
        Polygon concept = new Polygon(Arrays.stream(points).map(Point::new).toArray(Point[]::new));
        GLPolygonShape<Polygon> polygon = new GLPolygonShape<>(concept);
        polygon.setLayerIndex(layer);
        return polygon;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GLRect glRect(int layer, float x, float y, float width, float height) {
        GLRect rect = new GLRect(x, y, width, height);
        rect.setLayerIndex(layer);
        return rect;
    }
}
