package com.thrblock.cino.glinitable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;

/**
 * 用于加载 GLInitializable 的组件
 * @author zepu.li
 */
@Component
public class GLInitor {
    private Semaphore sp = new Semaphore(1);
    private Set<GLInitializable> swapSet = new HashSet<>();
    
    /**
     * 加载一个组件，一般来讲，使用此方法的目的是预加载；
     * 此方法可以在非GL上下文所在线程调用
     * @param initable 可加载组件
     */
    public void addGLInitializable(GLInitializable initable) {
        sp.acquireUninterruptibly();
        swapSet.add(initable);
        sp.release();
    }
    
    /**
     * GL上下文实际调用的方法，对组件进行加载
     * @param gl
     */
    public void glInitializing(GL gl) {
        if(!swapSet.isEmpty()) {
            sp.acquireUninterruptibly();
            swapSet.forEach(e->e.initByGLContext(gl));
            swapSet.clear();
            sp.release();
        }
    }
    
}
