package com.thrblock.cino.glshape;

import com.thrblock.cino.glshape.proxy.NodeAlphaProxy;
import com.thrblock.cino.glshape.proxy.NodeMotivationProxy;
import com.thrblock.cino.vec.Vec2;

/**
 * 一个抽象节点，一个节点大可代表一个场景，小可代表一个图形<br />
 * 节点所具有的意义是实际构造过程中定义的
 * @author lizepu
 *
 */
public interface GLNode {
    /**
     * 显示节点
     */
    public void show();
    /**
     * 隐藏节点
     */
    public void hide();
    
    /**
     * 设置位置坐标 x,具体效果依赖于GLNode实现类
     * @param x 横坐标x
     */
    public void setX(float x);
    /**
     * 设置位置坐标 y,具体效果依赖于GLNode实现类
     * @param y 纵坐标y
     */
    public void setY(float y);
    
    /**
     * 设置位置坐标 xy,具体效果依赖于GLNode实现类
     * @param xy vec2
     */
    public void setXy(Vec2 xy);
    /**
     * 获得位置坐标x
     * @return 横坐标x
     */
    public float getX();
    /**
     * 获得位置坐标y
     * @return 横坐标y
     */
    public float getY();
    
    /**
     * 获得位置坐标xy
     * @return
     */
    public Vec2 getXy();
    
    /**
     * 获得 中心坐标x,具体效果依赖于GLNode实现类
     * @return 中心坐标x
     */
    public float getCentralX();
    /**
     * 获得 中心坐标y,具体效果依赖于GLNode实现类
     * @return 中心坐标y
     */
    public float getCentralY();
    
    /**
     * 获得 中心坐标xy,具体效果依赖于GLNode实现类
     * @return
     */
    public Vec2 getCentral();
    /**
     * 设置 中心坐标x,具体效果依赖于GLNode实现类
     * @param x 中心坐标x
     */
    public void setCentralX(float x);
    /**
     * 设置 中心坐标x,具体效果依赖于GLNode实现类
     * @param y 中心坐标y
     */
    public void setCentralY(float y);
    
    /**
     * 设置 中心坐标xy,具体效果依赖于GLNode实现类
     * @param xy
     */
    public void setCentral(Vec2 xy);
    
    /**
     * 设置 通道参数alpha,具体效果依赖于GLNode实现类
     * @param alpha 通道量
     */
    public void setAlpha(float alpha);
    /**
     * 获得 通道参数alpha,具体效果依赖于GLNode实现类
     * @return 通道参数alpha
     */
    public float getAlpha();
    
    /**
     * 设置 节点横向偏移量offset,具体效果依赖于GLNode实现类
     * @param offset 横向偏移量
     */
    public void setXOffset(float offset);
    /**
     * 设置 节点纵向偏移量offset,具体效果依赖于GLNode实现类
     * @param offset 纵向偏移量
     */
    public void setYOffset(float offset);
    
    /**
     * 获得 旋转角（自旋角） 弧度制 具体效果依赖于GLNode实现类
     * @return 旋转角
     */
    public float getRadian();
    /**
     * 设置 旋转角（自旋角） 弧度制 具体效果依赖于GLNode实现类
     * @param dstTheta 要设置的绝对自旋角
     */
    public void setRadian(float dstTheta);
    /**
     * 设置 旋转角 弧度制 以指定转轴进行旋转变换,具体效果依赖于GLNode实现类
     * @param radian 要设置的相对角度
     * @param x 转轴x
     * @param y 转轴y
     */
    public void revolve(float radian,float x,float y);
    
    /**
     * 得到自身的通道操作类
     * @return NodeAlphaProxy 通道操作类
     */
    default NodeAlphaProxy proxyNodeAlpha() {
        return new NodeAlphaProxy(this);
    }
    
    /**
     * 得到自身移动操作类
     * @return NodeMotivationProxy 移动操作类
     */
    default NodeMotivationProxy proxyNodeMotivation() {
        return new NodeMotivationProxy(this);
    }
}
