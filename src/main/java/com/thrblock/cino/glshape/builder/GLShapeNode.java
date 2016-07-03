package com.thrblock.cino.glshape.builder;

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
    @Override
    public void show() {
        basic.show();
        subList.forEach(e -> e.show());
    }

    @Override
    public void hide() {
        basic.hide();
        subList.forEach(e -> e.hide());
    }

    @Override
    public void destory() {
        basic.destory();
        subList.forEach(e -> e.destory());
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
    public float getTheta() {
        return basic.getTheta();
    }

    @Override
    public void setTheta(float dstTheta) {
        basic.setTheta(dstTheta);
        float rolx = basic.getCentralX();
        float roly = basic.getCentralY();
        subList.forEach(e -> e.setTheta(dstTheta,rolx,roly));
    }

    @Override
    public void setTheta(float dstTheta, float x, float y) {
        basic.setTheta(dstTheta,x,y);
        subList.forEach(e -> e.setTheta(dstTheta,x,y));
    }
}
class GLNop implements GLNode {

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void destory() {
    }

    @Override
    public void setX(float x) {
    }

    @Override
    public void setY(float y) {
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
    }

    @Override
    public void setCentralY(float y) {
    }

    @Override
    public void setAlpha(float alpha) {
    }

    @Override
    public float getAlpha() {
        return 0;
    }

    @Override
    public void setXOffset(float offset) {
    }

    @Override
    public void setYOffset(float offset) {
    }

    @Override
    public float getTheta() {
        return 0;
    }

    @Override
    public void setTheta(float dstTheta) {
    }

    @Override
    public void setTheta(float dstTheta, float x, float y) {
    }
    
}