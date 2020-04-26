package com.thrblock.cino.glshape.factory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.thrblock.cino.vec.Vec2;

/**
 * GLShapeNode 图形节点 GLNode的纯结构实现
 * 
 * @author lizepu
 */
public class GLShapeNode implements GLNode {
    private static final GLNode GL_NOP = new GLNop();// 防止basic为空
    private GLNode basic = GL_NOP;
    private List<GLNode> subList = new CopyOnWriteArrayList<>();
    private GLShapeNode parent;

    /**
     * 获得父节点
     * 
     * @return 父节点
     */
    public GLShapeNode getParent() {
        return parent;
    }

    /**
     * 设置父节点
     * 
     * @param parent 父节点
     */
    public void setParent(GLShapeNode parent) {
        this.parent = parent;
    }

    /**
     * 增加一个子节点
     * 
     * @param node 子节点
     */
    public void addSubNode(GLNode node) {
        if (basic == GL_NOP) {
            basic = node;
        } else {
            subList.add(node);
        }
    }

    public void destroyNode(GLNode node) {
        if (basic == node) {
            basic.destroy();
            if (subList.isEmpty()) {
                basic = GL_NOP;
            } else {
                basic = subList.remove(0);
            }
        } else {
            node.destroy();
            if (subList.remove(node)) {
                return;
            }
            for (GLNode sub : subList) {
                if (sub instanceof GLShapeNode) {
                    ((GLShapeNode) sub).destroyNode(node);
                }
            }
        }
    }

    @Override
    public void show() {
        basic.show();
        subList.forEach(GLNode::show);
    }

    @Override
    public void hide() {
        basic.hide();
        subList.forEach(GLNode::hide);
    }

    @Override
    public void destroy() {
        basic.destroy();
        subList.forEach(GLNode::destroy);
        subList.clear();
        basic = GL_NOP;
    }

    @Override
    public void setX(float x) {
        float offset = x - basic.getX();
        basic.setX(x);
        subList.forEach(e -> e.setXOffset(offset));
    }

    @Override
    public void setY(float y) {
        float offset = y - basic.getY();
        basic.setY(y);
        subList.forEach(e -> e.setYOffset(offset));
    }

    @Override
    public float getX() {
        return basic.getX();
    }

    @Override
    public float getY() {
        return basic.getY();
    }

    @Override
    public float getCentralX() {
        return basic.getCentralX();
    }

    @Override
    public float getCentralY() {
        return basic.getCentralY();
    }

    @Override
    public void setCentralX(float x) {
        float offset = x - basic.getCentralX();
        basic.setCentralX(x);
        subList.forEach(e -> e.setXOffset(offset));
    }

    @Override
    public void setCentralY(float y) {
        float offset = y - basic.getCentralY();
        basic.setCentralY(y);
        subList.forEach(e -> e.setYOffset(offset));
    }

    @Override
    public void setAlpha(float alpha) {
        basic.setAlpha(alpha);
        subList.forEach(e -> e.setAlpha(alpha));
    }

    @Override
    public float getAlpha() {
        return basic.getAlpha();
    }

    @Override
    public void setXOffset(float offset) {
        basic.setXOffset(offset);
        subList.forEach(e -> e.setXOffset(offset));
    }

    @Override
    public void setYOffset(float offset) {
        basic.setYOffset(offset);
        subList.forEach(e -> e.setYOffset(offset));
    }

    @Override
    public float getRadian() {
        return basic.getRadian();
    }

    @Override
    public void setRadian(float dstTheta) {
        float offset = dstTheta - basic.getRadian();
        basic.setRadian(dstTheta);
        float rolx = basic.getCentralX();
        float roly = basic.getCentralY();
        subList.forEach(e -> e.revolve(offset, rolx, roly));
    }

    @Override
    public void revolve(float dstTheta, float x, float y) {
        basic.revolve(dstTheta, x, y);
        subList.forEach(e -> e.revolve(dstTheta, x, y));
    }

    @Override
    public void setXy(Vec2 xy) {
        setX(xy.getX());
        setY(xy.getY());
    }

    @Override
    public Vec2 getXy() {
        return new Vec2(getX(), getY());
    }

    @Override
    public Vec2 getCentral() {
        return new Vec2(getCentralX(), getCentralY());
    }

    @Override
    public void setCentral(Vec2 xy) {
        setCentralX(xy.getX());
        setCentralY(xy.getY());
    }
    
    public Stream<GLNode> stream() {
        return Optional.ofNullable(basic).map(b -> {
            List<GLNode> lst = Lists.newArrayList(b);
            lst.addAll(subList);
            return lst.stream();
        }).orElse(Stream.empty());
    }
}

/**
 * GLNop 是GLNode的空白实现<br />
 * 当一个ShapeBuilder构造任何图形之前，其base由Nop充当。
 * 
 * @author lizepu
 */
class GLNop implements GLNode {

    @Override
    public void show() {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void hide() {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void destroy() {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setX(float x) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setY(float y) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public float getCentralX() {
        return 0;
    }

    @Override
    public float getCentralY() {
        return 0;
    }

    @Override
    public void setCentralX(float x) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setCentralY(float y) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setAlpha(float alpha) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public float getAlpha() {
        return 0;
    }

    @Override
    public void setXOffset(float offset) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setYOffset(float offset) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public float getRadian() {
        return 0;
    }

    @Override
    public void setRadian(float dstTheta) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void revolve(float dstTheta, float x, float y) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setXy(Vec2 xy) {
        // Nop 不会对任何操作进行实际处理
    }

    @Override
    public Vec2 getXy() {
        // Nop 不会对任何操作进行实际处理
        return new Vec2(0);
    }

    @Override
    public Vec2 getCentral() {
        // Nop 不会对任何操作进行实际处理
        return new Vec2(0);
    }

    @Override
    public void setCentral(Vec2 xy) {
        // Nop 不会对任何操作进行实际处理
    }

}