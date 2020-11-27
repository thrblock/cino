package com.thrblock.cino.glinitable;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.thrblock.cino.gllifecycle.CycleArray;

/**
 * 用于加载 GLInitializable 的组件
 * 
 * @author zepu.li
 */
@Component
public class GLInitor {
    private CycleArray<GLInitializable> initCycle = new CycleArray<>(GLInitializable[]::new);

    /**
     * 加载一个组件，一般来讲，使用此方法的目的是预加载； 此方法可以在非GL上下文所在线程调用
     * 
     * @param initable 可加载组件
     */
    public void addGLInitializable(GLInitializable initable) {
        initCycle.safeAdd(initable);
    }

    /**
     * GL上下文实际调用的方法，对组件进行加载
     * 
     * @param gl
     */
    public void glInitializing(GL gl) {
        Arrays.stream(initCycle.safeHold(true)).forEach(i -> i.initByGLContext(gl));
    }

}
