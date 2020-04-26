package com.thrblock.cino.glshape.factory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.thrblock.cino.concept.Line;
import com.thrblock.cino.concept.Point;
import com.thrblock.cino.concept.Polygon;
import com.thrblock.cino.concept.Rect;
import com.thrblock.cino.gllayer.GLLayerManager;
import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLPolygonShape;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.GLTriangle;
import com.thrblock.cino.gltexture.GLTexture;
import com.thrblock.cino.util.charprocess.CharAreaConfig;
import com.thrblock.cino.vec.Vec2;

/**
 * 图形构造器
 * 
 * @author lizepu
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class GLShapeFactory {
    private int layer = 0;
    private GLShapeNode currentNode = null;
    @Autowired
    GLLayerManager layerContainer;

    /**
     * 设置此构造器的图像层索引，该构造器所构造的图形会处于索引层次
     * 
     * @param layerIndex 图像层索引
     */
    public void setLayer(int layerIndex) {
        this.layer = layerIndex;
    }

    public int getLayer() {
        return layer;
    }

    /**
     * 构造一个点
     * 
     * @param x 横坐标
     * @param y 纵坐标
     * @return 点图形
     */
    public GLPoint buildGLPoint(float x, float y) {
        GLPoint point = new GLPoint(x, y);
        layerContainer.addShapeToSwap(layer, point);
        if (currentNode != null) {
            currentNode.addSubNode(point);
        }
        return point;
    }

    /**
     * 构造一个点
     * 
     * @param p 概念点
     * @return
     */
    public GLPoint buildGLPoint(Point p) {
        GLPoint point = new GLPoint(p);
        layerContainer.addShapeToSwap(layer, point);
        if (currentNode != null) {
            currentNode.addSubNode(point);
        }
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
        GLLine line = new GLLine(x1, y1, x2, y2);
        layerContainer.addShapeToSwap(layer, line);
        if (currentNode != null) {
            currentNode.addSubNode(line);
        }
        return line;
    }

    /**
     * 构造一条直线
     * 
     * @param l 概念线
     * @return
     */
    public GLLine buildGLLine(Line l) {
        GLLine line = new GLLine(l);
        layerContainer.addShapeToSwap(layer, line);
        if (currentNode != null) {
            currentNode.addSubNode(line);
        }
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
        GLRect rect = new GLRect(x, y, width, height);
        layerContainer.addShapeToSwap(layer, rect);
        if (currentNode != null) {
            currentNode.addSubNode(rect);
        }
        return rect;
    }

    /**
     * 构造一个矩形
     * 
     * @param r 概念矩形
     * @return
     */
    public GLRect buildGLRect(Rect r) {
        GLRect rect = new GLRect(r);
        layerContainer.addShapeToSwap(layer, rect);
        if (currentNode != null) {
            currentNode.addSubNode(rect);
        }
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
        GLOval oval = GLOval.generate(x, y, axisA, axisB, accuracy);
        layerContainer.addShapeToSwap(layer, oval);
        if (currentNode != null) {
            currentNode.addSubNode(oval);
        }
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
        GLImage image = new GLImage(x, y, width, height, texture);
        layerContainer.addShapeToSwap(layer, image);
        if (currentNode != null) {
            currentNode.addSubNode(image);
        }
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
        GLImage image = new GLImage(r, texture);
        layerContainer.addShapeToSwap(layer, image);
        if (currentNode != null) {
            currentNode.addSubNode(image);
        }
        return image;
    }

    /**
     * 使用空纹理构造一个贴图对象
     * 
     * @return 贴图对象
     */
    public GLImage buildGLImage() {
        GLImage image = new GLImage();
        layerContainer.addShapeToSwap(layer, image);
        if (currentNode != null) {
            currentNode.addSubNode(image);
        }
        return image;
    }

    /**
     * 使用空纹理构造一个贴图对象
     * 
     * @return 贴图对象
     */
    public GLImage buildGLImage(float w, float h) {
        GLImage image = new GLImage(w, h);
        layerContainer.addShapeToSwap(layer, image);
        if (currentNode != null) {
            currentNode.addSubNode(image);
        }
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
        GLTriangle triangle = new GLTriangle(x1, y1, x2, y2, x3, y3);
        layerContainer.addShapeToSwap(layer, triangle);
        if (currentNode != null) {
            currentNode.addSubNode(triangle);
        }
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
        List<Point> pointList = new LinkedList<>();
        for (int i = 0; i < xs.length; i++) {
            pointList.add(new Point(xs[i], ys[i]));
        }
        Polygon concept = new Polygon(Iterables.toArray(pointList, Point.class));
        GLPolygonShape<Polygon> polygon = new GLPolygonShape<>(concept);
        layerContainer.addShapeToSwap(layer, polygon);
        if (currentNode != null) {
            currentNode.addSubNode(polygon);
        }
        return polygon;
    }

    public GLPolygonShape<Polygon> buildGLPolygon(Vec2... points) {
        Polygon concept = new Polygon(Arrays.stream(points).map(Point::new).toArray(Point[]::new));
        GLPolygonShape<Polygon> polygon = new GLPolygonShape<>(concept);
        layerContainer.addShapeToSwap(layer, polygon);
        if (currentNode != null) {
            currentNode.addSubNode(polygon);
        }
        return polygon;
    }

    public GLCharArea buildGLCharArea(float x, float y, float w, float h, CharAreaConfig conf) {
        GLCharArea area = new GLCharArea(x, y, w, h, conf);
        layerContainer.addShapeToSwap(layer, area);
        if (currentNode != null) {
            currentNode.addSubNode(area);
        }
        return area;
    }

    public GLCharArea buildGLCharArea(float x, float y, float w, CharAreaConfig conf) {
        return buildGLCharArea(x, y, w, conf.getFont().getFmheight(), conf);
    }

    public GLCharArea buildGLCharArea(float w, CharAreaConfig conf) {
        return buildGLCharArea(0, 0, w, conf);
    }

    /**
     * 将当前节点置空
     */
    public void clearNode() {
        setNode(null);
    }

    /**
     * 设置当前操作节点
     * 
     * @param node
     */
    public void setNode(GLShapeNode node) {
        currentNode = node;
    }

    public NodeSession<?> nodeSession() {
        return new NodeSession<>();
    }

    public final class NodeSession<T extends GLNode> {
        private T latest;
        private GLShapeNode node;
        private GLShapeFactory factory0;

        NodeSession() {
            this(new GLShapeNode(), new GLShapeFactory());
        }

        NodeSession(GLShapeNode node, GLShapeFactory factory) {
            this.node = node;
            this.factory0 = factory;
            factory0.layer = GLShapeFactory.this.layer;
            factory0.layerContainer = GLShapeFactory.this.layerContainer;
            factory0.setNode(node);
        }

        private <S extends GLNode> NodeSession<S> apply(S s) {
            NodeSession<S> result = new NodeSession<>(node, factory0);
            result.latest = s;
            return result;
        }

        public NodeSession<T> then(Consumer<T> cons) {
            Optional.ofNullable(latest).ifPresent(cons);
            return this;
        }

        public NodeSession<GLImage> glImage() {
            return apply(factory0.buildGLImage());
        }

        public NodeSession<GLImage> glImage(float w, float h) {
            return apply(factory0.buildGLImage(w, h));
        }

        public NodeSession<GLImage> glImage(float x, float y, float w, float h, GLTexture t) {
            return apply(factory0.buildGLImage(x, y, w, h, t));
        }

        public NodeSession<GLImage> glImage(Rect r, GLTexture t) {
            return apply(factory0.buildGLImage(r, t));
        }

        public NodeSession<GLLine> glLine(float x1, float y1, float x2, float y2) {
            return apply(factory0.buildGLLine(x1, y1, x2, y2));
        }

        public NodeSession<GLLine> glLine(Line l) {
            return apply(factory0.buildGLLine(l));
        }

        public NodeSession<GLLine> glLine(Vec2 p1, Vec2 p2) {
            return apply(factory0.buildGLLine(p1, p2));
        }

        public NodeSession<GLOval> glOval(float x, float y, float axisA, float axisB, int accuracy) {
            return apply(factory0.buildGLOval(x, y, axisA, axisB, accuracy));
        }

        public NodeSession<GLOval> glOval(float axisA, float axisB, int accuracy) {
            return apply(factory0.buildGLOval(axisA, axisB, accuracy));
        }

        public NodeSession<GLOval> glOval(float diameter, int accuracy) {
            return apply(factory0.buildGLOval(diameter, accuracy));
        }

        public NodeSession<GLPoint> glPoint(float x, float y) {
            return apply(factory0.buildGLPoint(x, y));
        }

        public NodeSession<GLPoint> glPoint(Point p) {
            return apply(factory0.buildGLPoint(p));
        }

        public NodeSession<GLPoint> glPoint(Vec2 v) {
            return apply(factory0.buildGLPoint(v));
        }

        public NodeSession<GLPolygonShape<Polygon>> glPolygon(float[] xs, float[] ys) {
            return apply(factory0.buildGLPolygon(xs, ys));
        }

        public NodeSession<GLPolygonShape<Polygon>> glPolygon(Vec2... p) {
            return apply(factory0.buildGLPolygon(p));
        }

        public NodeSession<GLRect> glRect(float w, float h) {
            return apply(factory0.buildGLRect(w, h));
        }

        public NodeSession<GLRect> glRect(float x, float y, float w, float h) {
            return apply(factory0.buildGLRect(x, y, w, h));
        }

        public NodeSession<GLRect> glRect(Rect r) {
            return apply(factory0.buildGLRect(r));
        }

        public NodeSession<GLTriangle> glTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
            return apply(factory0.buildGLTriangle(x1, y1, x2, y2, x3, y3));
        }

        public NodeSession<GLTriangle> glTriangle(Vec2 p1, Vec2 p2, Vec2 p3) {
            return apply(factory0.buildGLTriangle(p1, p2, p3));
        }

        public NodeSession<T> consumeStream(Consumer<Stream<GLNode>> cons) {
            cons.accept(node.stream());
            return this;
        }

        public <S extends GLNode> NodeSession<T> consumeStream(Consumer<Stream<S>> cons, Class<S> clazz) {
            cons.accept(node.stream().filter(clazz::isInstance).map(clazz::cast));
            return this;
        }

        public GLShapeNode get() {
            Optional.ofNullable(currentNode).ifPresent(n -> n.addSubNode(node));
            return node;
        }
    }
}
