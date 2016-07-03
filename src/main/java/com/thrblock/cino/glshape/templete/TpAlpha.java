package com.thrblock.cino.glshape.templete;

import com.thrblock.cino.glshape.builder.GLNode;

/**
 * 一些关于图形混合模式系数的模板方法，用于早期动画实现<br />
 * 静态方法，不推荐使用，今后有高可能性被替换<br />
 * "不可以 这不面向对象"
 * @author lizepu
 */
public class TpAlpha {
    private TpAlpha() {
    }
    /**
     * 淡出
     * @param node 节点
     * @param step 每帧淡出量
     * @return 是否完成淡出的布尔值
     */
    public static boolean tearOut(GLNode node, float step) {
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
     * @param node 节点
     * @param step 每帧淡入量
     * @return 是否完成淡入的布尔值
     */
    public static boolean tearIn(GLNode node, float step) {
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
