package com.thrblock.cino.glinitable;

import com.jogamp.opengl.GL;

/**
 * 一类需要opengl上下文进行加载的对象
 * @author thrblock
 *
 */
public interface GLInitializable {
    /**
     * 由OpenGL上下文加载
     * @param gl opengl对象
     */
    public void initByGLContext(GL gl);
}
