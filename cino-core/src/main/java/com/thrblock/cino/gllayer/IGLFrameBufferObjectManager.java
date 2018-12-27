package com.thrblock.cino.gllayer;

/**
 * OpenGL 帧缓冲对象管理器
 * @author zepu.li
 */
public interface IGLFrameBufferObjectManager {
    /**
     * 创建一个层次的帧缓冲对象
     * @param index 所属层次
     * @return
     */
    public GLFrameBufferObject generateLayerFBO(int index);
    /**
     * 创建一个全局帧缓冲对象(PostProcess)
     * @return
     */
    public GLFrameBufferObject generateGlobalFBO();
    
    /**
     * 删除一个fbo对象
     * @param fbo
     */
    public void removeFBO(GLFrameBufferObject fbo);
}
