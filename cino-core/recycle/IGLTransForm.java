package com.thrblock.cino.gltransform;

public interface IGLTransForm {

    /**
     * 删除指定层的变换设置 该操作不考虑变换的层级继承
     * @param layerIndex
     * @return
     */
    GLTransform removeTransform(int layerIndex);

    /**
     * 讲变换插入到指定层级 后续层级中若无变换设置 则会进行继承
     * @param trans
     * @param layerIndex
     * @return
     */
    boolean addBeforeLayer(GLTransform trans, int layerIndex);

    /**
     * 获得指定绘制层 的变换对象 考虑变换的层级继承
     * @param layerIndex
     * @return
     */
    GLTransform getGLTransform(int layerIndex);

}
