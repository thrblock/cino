package com.thrblock.cino.glshape;

import java.util.concurrent.atomic.AtomicLong;

import com.jogamp.opengl.GL2;
import com.thrblock.cino.concept.GeometricConcept;
import com.thrblock.cino.debug.GLDebugHelper;
import com.thrblock.cino.lnode.LNode;
import com.thrblock.cino.shader.AbstractGLProgram;

/**
 * 图形对象(抽象) 定义了抽象图形对象
 * 
 * @author lizepu
 *
 */
public abstract class GLShape<T extends GeometricConcept> implements GLNode {
    private static final AtomicLong ATOMIC_UUID = new AtomicLong();
    private final long uuid = ATOMIC_UUID.incrementAndGet();
    private boolean visible = false;
    private boolean display = true;
    protected AbstractGLProgram program;

    protected T concept;
    protected LNode node;
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GLShape(T concept) {
        this.concept = concept;
    }
    
    public LNode getNode() {
        return node;
    }

    public void setNode(LNode node) {
        this.node = node;
    }

    public T exuviate() {
        return concept;
    }

    public long getUUID() {
        return uuid;
    }

    @Override
    public void show() {
        this.visible = true;
    }

    @Override
    public void hide() {
        this.visible = false;
    }

    /**
     * 定义图形对象是否可见
     * 
     * @return 是否可见的布尔值
     */
    public boolean isVisible() {
        return visible;
    }
    
    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    /**
     * 使用指定的OpenGL程序
     * 
     * @param program OpenGL程序
     */
    public void useGLProgram(AbstractGLProgram program) {
        this.program = program;
    }

    /**
     * 绘制前处理
     * 
     * @param gl
     */
    public void beforeDraw(GL2 gl) {
        if (program != null) {
            program.bind(gl);
        }
        GLDebugHelper.logIfError(gl, "before shape draw:" + this.getClass().getSimpleName());
    }

    /**
     * 图形对象的绘制方法
     * 
     * @param gl OpenGL 绘制对象
     */
    public abstract void drawShape(GL2 gl);

    /**
     * 绘制后处理
     * 
     * @param gl
     */
    public void afterDraw(GL2 gl) {
        if (program != null) {
            program.unBind(gl);
        }
        GLDebugHelper.logIfError(gl, "after shape draw:" + this.getClass().getSimpleName());
    }

}
