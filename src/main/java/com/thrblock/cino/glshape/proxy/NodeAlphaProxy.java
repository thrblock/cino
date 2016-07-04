package com.thrblock.cino.glshape.proxy;

import com.thrblock.cino.glshape.builder.GLNode;

/**
 * GLNode节点的通道操作类
 * @author lizepu
 */
public class NodeAlphaProxy {
	private GLNode node;
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
}
