package com.thrblock.cino.glshape.proxy;

import com.thrblock.cino.glshape.builder.GLNode;

/**
 * GLNode节点的通道操作类
 * @author lizepu
 */
public class NodeAlphaProxy {
	private GLNode node;
	/**
	 * 构造一个节点通道操作代理
	 * @param node 要代理的节点
	 */
	public NodeAlphaProxy(GLNode node) {
		this.node = node;
	}
	/**
     * 淡出
     * @param step 每帧淡出量
     * @return 是否完成淡出的布尔值
     */
    public boolean tearOut(float step) {
        float calc = node.getAlpha() - step;
        if(calc > 0) {
            node.setAlpha(calc);
            return false;
        } else {
            node.setAlpha(0);
            return true;
        }
    }
    /**
     * 淡入
     * @param step 每帧淡入量
     * @return 是否完成淡入的布尔值
     */
    public boolean tearIn(float step) {
        float calc = node.getAlpha() + step;
        if(calc < 1f) {
            node.setAlpha(calc);
            return false;
        } else {
            node.setAlpha(1f);
            return true;
        }
    }
    
    /**
     * alph通道线性过度到指定值
     * @param step 每帧变化量
     * @param dst 目标值
     * @return
     */
    public boolean tearTo(float step,float dst) {
        float crt = node.getAlpha();
        if(dst > crt) {
            if(dst - crt < step) {
                node.setAlpha(dst);
                return true;
            } else {
                node.setAlpha(crt + step);
                return false;
            }
        } else if(dst < crt){
            if(crt - dst < step) {
                node.setAlpha(dst);
                return true;
            } else {
                node.setAlpha(crt - step);
                return false;
            }
        } else {
            return true;
        }
    }
}
