package com.thrblock.cino.glshape.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * GLShapeNode 图形节点 GLNode的纯结构实现
 * @author lizepu
 */
public class GLShapeNode implements GLNode{
    private static final GLNode GL_NOP = new GLNop();//放置basic为空
    private GLNode basic = GL_NOP;
    private List<GLNode> subList = new ArrayList<>();
    private GLShapeNode parent;
    
    /**
     * 获得父节点
     * @return 父节点
     */
    public GLShapeNode getParent() {
        return parent;
    }
    /**
     * 设置父节点
     * @param parent 父节点
     */
    public void setParent(GLShapeNode parent) {
        this.parent = parent;
    }
    /**
     * 增加一个子节点
     * @param node 子节点
     */
    public void addSubNode(GLNode node) {
        if(basic == GL_NOP) {
            basic = node;
        } else {
            subList.add(node);
        }
    }
    
    public void destroyNode(GLNode node) {
        if(basic == node) {
            basic.destroy();
            if(subList.isEmpty()) {
                basic = GL_NOP;
            } else {
                basic = subList.remove(0);
            }
        } else {
            node.destroy();
            if(subList.remove(node)) {
                return;
            }
            for(GLNode sub:subList) {
                if(sub instanceof GLShapeNode) {
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
        basic.setRadian(dstTheta);
        float rolx = basic.getCentralX();
        float roly = basic.getCentralY();
        subList.forEach(e -> e.setRadian(dstTheta,rolx,roly));
    }

    @Override
    public void setRadian(float dstTheta, float x, float y) {
        basic.setRadian(dstTheta,x,y);
        subList.forEach(e -> e.setRadian(dstTheta,x,y));
    }
}
/**
 * GLNop 是GLNode的空白实现<br />
 * 当一个ShapeBuilder构造任何图形之前，其base由Nop充当。
 * @author lizepu
 */
class GLNop implements GLNode {

    @Override
    public void show() {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public void hide() {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public void destroy() {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setX(float x) {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setY(float y) {
        //Nop 不会对任何操作进行实际处理
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
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setCentralY(float y) {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setAlpha(float alpha) {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public float getAlpha() {
        return 0;
    }

    @Override
    public void setXOffset(float offset) {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setYOffset(float offset) {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public float getRadian() {
        return 0;
    }

    @Override
    public void setRadian(float dstTheta) {
        //Nop 不会对任何操作进行实际处理
    }

    @Override
    public void setRadian(float dstTheta, float x, float y) {
        //Nop 不会对任何操作进行实际处理
    }
    
}