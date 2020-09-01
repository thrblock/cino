package com.thrblock.cino.glshape;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.concept.GeometricConcept;
import com.thrblock.cino.debug.GLDebugHelper;
import com.thrblock.cino.gllayer.GLLayerManager;
import com.thrblock.cino.glshape.factory.GLNode;
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
    private boolean destory = false;
    private int layerIndex;
    private int mixAlpha = GL.GL_SRC_ALPHA;
    private int mixBeta = GL.GL_ONE_MINUS_SRC_ALPHA;
    protected AbstractGLProgram program;
    
    @Autowired
    protected GLLayerManager layerContainer;
    
    protected T concept;
    
    public GLShape(T concept) {
        this.concept = concept;
    }
    
    @PostConstruct
    protected void init() {
        layerContainer.addShapeToSwap(layerIndex, this);
    }
    
    public T exuviate() {
        return concept;
    }

    public int getLayerIndex() {
        return layerIndex;
    }

    public void setLayerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
    }
    
    public long getUUID() {
        return uuid;
    }

    /**
     * 获得混合模式系数A
     * 
     * @return 混合模式系数A
     */
    public int getMixAlpha() {
        return mixAlpha;
    }

    /**
     * 设置 混合模式系数A
     * 
     * @param mixAlpha
     *            混合模式系数A
     */
    public void setMixAlpha(int mixAlpha) {
        this.mixAlpha = mixAlpha;
    }

    /**
     * 获得混合模式系数B
     * 
     * @return 混合模式系数B
     */
    public int getMixBeta() {
        return mixBeta;
    }

    /**
     * 设置 混合模式系数B
     * 
     * @param mixBeta
     *            混合模式系数B
     */
    public void setMixBeta(int mixBeta) {
        this.mixBeta = mixBeta;
    }

    @Override
    public void show() {
        this.visible = true;
    }

    @Override
    public void hide() {
        this.visible = false;
    }

    @Override
    public void destroy() {
        this.destory = true;
    }

    /**
     * 定义图形对象是否可见
     * 
     * @return 是否可见的布尔值
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * 定义图形对象的销毁标记
     * 
     * @return 是否进行销毁的布尔值
     */
    public boolean isDestory() {
        return destory;
    }

    /**
     * 使用指定的OpenGL程序
     * 
     * @param program
     *            OpenGL程序
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
        GLDebugHelper.logIfError(gl,"before shape draw:" + this.getClass().getSimpleName());
    }

    /**
     * 图形对象的绘制方法
     * 
     * @param gl
     *            OpenGL 绘制对象
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
        GLDebugHelper.logIfError(gl,"after shape draw:" + this.getClass().getSimpleName());
    }
    
    /**
     * 当前图形是否绘制于指定图形之上
     * @param shape
     * @return
     */
    public boolean isTopThan(GLShape<?> shape) {
        if(getLayerIndex() != shape.getLayerIndex()) {
            return getLayerIndex() > shape.getLayerIndex();
        } else {
            return getUUID() > shape.getUUID();
        }
    }
}
