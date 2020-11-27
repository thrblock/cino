package com.thrblock.cino.lnode;

import java.util.function.Consumer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.concept.Line;
import com.thrblock.cino.concept.Point;
import com.thrblock.cino.concept.Polygon;
import com.thrblock.cino.concept.Rect;
import com.thrblock.cino.concept.Rect.MBRSession;
import com.thrblock.cino.fbo.GLFrameBufferObject;
import com.thrblock.cino.fbo.GLFrameBufferObjectManager;
import com.thrblock.cino.gllifecycle.CycleArray;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLNode;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLPolygonShape;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.GLShape;
import com.thrblock.cino.glshape.GLTriangle;
import com.thrblock.cino.gltexture.GLTexture;
import com.thrblock.cino.gltransform.GLTransform;
import com.thrblock.cino.vec.Vec2;

import lombok.Getter;
import lombok.Setter;

public class LNode implements GLNode, Comparable<LNode> {

    /**
     * parent
     */
    @Setter
    @Getter
    private LNode node;
    private Rect mbr = new Rect(new Point(-1, 1), new Point(1, 1), new Point(1, -1), new Point(-1, -1));
    private float radian;
    private float alpha;

    private CycleArray<GLNode> subNodes = new CycleArray<>(GLNode[]::new);

    @Setter
    private Integer mixA;

    @Setter
    private Integer mixB;

    @Setter
    @Getter
    private GLTransform transform;

    @Setter
    @Getter
    private int order;

    @Setter
    @Getter
    private String name;

    @Setter
    private GLFrameBufferObjectManager fboManager;

    @Setter
    private ShapeBeanFactory shapeFactory;

    private CycleArray<GLFrameBufferObject> fboCycle = new CycleArray<>(GLFrameBufferObject[]::new);

    LNode(GLFrameBufferObjectManager fboManager, ShapeBeanFactory shapeFactory) {
        this.shapeFactory = shapeFactory;
        this.fboManager = fboManager;

        this.mixA = GL.GL_SRC_ALPHA;
        this.mixB = GL.GL_ONE_MINUS_SRC_ALPHA;
    }

    LNode(LNode parent) {
        this.node = parent;
        this.fboManager = parent.fboManager;
        this.shapeFactory = parent.shapeFactory;
        parent.subNodes.safeAdd(this);
    }

    public Integer getMixA() {
        if (mixA != null) {
            return mixA;
        } else if (node != null) {
            return node.getMixA();
        }
        return GL.GL_SRC_ALPHA;
    }

    public Integer getMixB() {
        if (mixB != null) {
            return mixB;
        } else if (node != null) {
            return node.getMixB();
        }
        return GL.GL_ONE_MINUS_SRC_ALPHA;
    }

    public void drawShape(GL2 gl2) {
        gl2.glBlendFunc(getMixA(), getMixB());
        GLFrameBufferObject[] fbos = fboCycle.safeHold();
        fboManager.bind(fbos, gl2);
        GLNode[] arr = subNodes.safeHold();
        for (int i = 0; i < arr.length; i++) {
            GLNode n = arr[i];
            if (GLShape.class.isInstance(n)) {
                GLShape<?> sp = GLShape.class.cast(n);
                if (sp.isVisible() && sp.isDisplay()) {
                    sp.beforeDraw(gl2);
                    sp.drawShape(gl2);
                    sp.afterDraw(gl2);
                }
            } else {
                LNode.class.cast(n).drawShape(gl2);
            }
        }
        fboManager.unBind(fbos,gl2);
    }

    public GLFrameBufferObject createFBO() {
        GLFrameBufferObject result = fboManager.generateFBO();
        fboCycle.safeAdd(result);
        return result;
    }

    public void destroyFBO(GLFrameBufferObject fbo) {
        fboCycle.safeRemove(fbo);
        fboManager.destroyFBO(fbo);
    }

    GLTransform currentTransform() {
        if (node == null) {
            return transform;
        }
        return transform == null ? node.currentTransform() : transform;
    }

    public float[] unprojectedMouse() {
        return currentTransform().getUnprojectedMouseData();
    }

    public float getMouseX() {
        return currentTransform().getUnprojectedMouseData()[0];
    }

    public float getMouseY() {
        return currentTransform().getUnprojectedMouseData()[1];
    }

    private void appendShape(GLNode s) {
        if (LNode.class.isInstance(s)) {
            LNode.class.cast(s).setNode(this);
        } else {
            GLShape.class.cast(s).setNode(this);
        }
        subNodes.safeAdd(s);
    }

    public void syncMbr() {
        GLNode[] node = subNodes.safeHold();
        MBRSession mbrSession = mbr.mbrSession();
        for (int i = 0; i < node.length; i++) {
            GLNode crt = node[i];
            if (LNode.class.isInstance(crt)) {
                LNode sub = LNode.class.cast(crt);
                sub.syncMbr();
                mbrSession.auto(sub.mbr);
            } else {
                GLShape<?> subSp = GLShape.class.cast(crt);
                mbrSession.auto(subSp.exuviate());
            }
        }
        mbrSession.complete();
    }

    public void destroyGLNode(GLNode s) {
        GLNode[] nodes = subNodes.safeHold();
        for (int i = 0; i < nodes.length; i++) {
            if (LNode.class.isInstance(nodes[i])) {
                LNode.class.cast(nodes[i]).destroyGLNode(s);
            }
        }
        subNodes.safeRemove(s);
    }

    /* shapeBuilders */
    /**
     * 构造一个点
     * 
     * @param x 横坐标
     * @param y 纵坐标
     * @return 点图形
     */
    public GLPoint glPoint(float x, float y) {
        GLPoint point = shapeFactory.glPoint(x, y);
        appendShape(point);
        return point;
    }

    /**
     * 构造一个点
     * 
     * @param p 概念点
     * @return
     */
    public GLPoint glPoint(Point pt) {
        GLPoint point = shapeFactory.glPoint(pt);
        appendShape(point);
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
    public GLLine glLine(float x1, float y1, float x2, float y2) {
        GLLine line = shapeFactory.glLine(x1, y1, x2, y2);
        appendShape(line);
        return line;
    }

    /**
     * 构造一条直线
     * 
     * @param l 概念线
     * @return
     */
    public GLLine glLine(Line l) {
        GLLine line = shapeFactory.glLine(l);
        appendShape(line);
        return line;
    }

    /**
     * 构造一个矩形
     * 
     * @param r 概念矩形
     * @return
     */
    public GLRect glRect(Rect r) {
        GLRect rect = shapeFactory.glRect(r);
        appendShape(rect);
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
    public GLOval glOval(float x, float y, float axisA, float axisB, int accuracy) {
        GLOval oval = shapeFactory.glOval(x, y, axisA, axisB, accuracy);
        appendShape(oval);
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
    public GLImage glImage(float x, float y, float width, float height, GLTexture texture) {
        GLImage image = shapeFactory.glImage(x, y, width, height, texture);
        appendShape(image);
        return image;
    }

    /**
     * 创建一个贴图对象
     * 
     * @param r       概念矩形
     * @param texture 使用的贴图纹理
     * @return
     */
    public GLImage glImage(Rect r, GLTexture texture) {
        GLImage image = shapeFactory.glImage(r, texture);
        appendShape(image);
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
    public GLTriangle glTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        GLTriangle triangle = shapeFactory.glTriangle(x1, y1, x2, y2, x3, y3);
        appendShape(triangle);
        return triangle;
    }

    /**
     * 创建一个封闭多边形
     * 
     * @param xs
     * @param ys
     * @return
     */
    public GLPolygonShape<Polygon> glPolygon(float[] xs, float[] ys) {
        GLPolygonShape<Polygon> polygon = shapeFactory.glPolygon(xs, ys);
        appendShape(polygon);
        return polygon;
    }

    public GLPolygonShape<Polygon> glPolygon(Vec2... points) {
        GLPolygonShape<Polygon> polygon = shapeFactory.glPolygon(points);
        appendShape(polygon);
        return polygon;
    }

    public GLRect glRect(float x, float y, float width, float height) {
        GLRect rect = shapeFactory.glRect(x, y, width, height);
        appendShape(rect);
        return rect;
    }

    public GLImage glImage() {
        GLImage r = shapeFactory.glImage();
        appendShape(r);
        return r;
    }

    public GLImage glImage(float w, float h) {
        GLImage r = shapeFactory.glImage(w, h);
        appendShape(r);
        return r;
    }

    public LNode wrapNode(Consumer<LNode> cons) {
        LNode sub = new LNode(this);
        cons.accept(sub);
        sub.syncMbr();
        appendShape(sub);
        return sub;
    }

    @Override
    public int compareTo(LNode o) {
        return order - o.order;
    }

    /* operators */
    @Override
    public void show() {
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].show();
        }
    }

    @Override
    public void hide() {
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].hide();
        }
    }

    @Override
    public void setCentralX(float x) {
        float offset = x - getCentralX();
        mbr.setCentralX(x);
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].setXOffset(offset);
        }
    }

    @Override
    public void setCentralY(float y) {
        float offset = y - getCentralY();
        mbr.setCentralY(y);
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].setYOffset(offset);
        }
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].setAlpha(alpha);
        }
    }

    @Override
    public void setXOffset(float offset) {
        mbr.setXOffset(offset);
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].setXOffset(offset);
        }
    }

    @Override
    public void setYOffset(float offset) {
        mbr.setYOffset(offset);
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].setYOffset(offset);
        }
    }

    @Override
    public void setRadian(float dstTheta) {
        float offset = dstTheta - getRadian();
        this.radian = dstTheta;
        float rolx = getCentralX();
        float roly = getCentralY();
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].revolve(offset, rolx, roly);
        }
    }

    @Override
    public void revolve(float radian, float x, float y) {
        GLNode[] subs = subNodes.safeHold();
        for (int i = 0; i < subs.length; i++) {
            subs[i].revolve(radian, x, y);
        }
    }

    @Override
    public void setX(float x) {
        setCentralX(x);
    }

    @Override
    public void setY(float y) {
        setCentralY(y);
    }

    @Override
    public void setXy(Vec2 xy) {
        setCentral(xy);
    }

    @Override
    public float getX() {
        return mbr.getCentralX();
    }

    @Override
    public float getY() {
        return mbr.getCentralY();
    }

    @Override
    public Vec2 getXy() {
        return new Vec2(mbr.getCentralX(), mbr.getCentralY());
    }

    @Override
    public float getCentralX() {
        return mbr.getCentralX();
    }

    @Override
    public float getCentralY() {
        return mbr.getCentralY();
    }

    @Override
    public Vec2 getCentral() {
        return new Vec2(mbr.getCentralX(), mbr.getCentralY());
    }

    @Override
    public void setCentral(Vec2 xy) {
        setCentralX(xy.getX());
        setCentralY(xy.getY());
    }

    @Override
    public float getAlpha() {
        return this.alpha;
    }

    @Override
    public float getRadian() {
        return this.radian;
    }
}
