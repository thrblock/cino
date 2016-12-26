package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.glshape.builder.GLNode;
import com.thrblock.cino.shader.GLProgram;
import com.thrblock.cino.shader.GLProgramHandler;

/**
 * 图形对象(抽象) 定义了抽象图形对象
 * @author lizepu
 *
 */
public abstract class GLShape implements GLNode{
    private boolean visible = false;
    private boolean destory = false;
    private int mixAlpha = GL.GL_SRC_ALPHA;
    private int mixBeta = GL.GL_ONE_MINUS_SRC_ALPHA;
    protected GLProgram program;
    protected GLProgramHandler programHandler;
    /**
     * 获得混合模式系数A
     * @return 混合模式系数A
     */
    public int getMixAlpha() {
        return mixAlpha;
    }
    /**
     * 设置 混合模式系数A
     * @param mixAlpha 混合模式系数A
     */
    public void setMixAlpha(int mixAlpha) {
        this.mixAlpha = mixAlpha;
    }
    /**
     * 获得混合模式系数B
     * @return 混合模式系数B
     */
    public int getMixBeta() {
        return mixBeta;
    }
    /**
     * 设置 混合模式系数B
     * @param mixBeta 混合模式系数B
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
    public void destory() {
        this.destory = true;
    }
    /**
     * 定义图形对象是否可见
     * @return 是否可见的布尔值
     */
    public boolean isVisible() {
        return visible;
    }
    /**
     * 定义图形对象的销毁标记
     * @return 是否进行销毁的布尔值
     */
    public boolean isDestory() {
        return destory;
    }
    
    /**
     * 使用指定的OpenGL程序
     * @param program OpenGL程序
     */
    public void useGLProgram(GLProgram program) {
        this.program = program;
    }
    
    /**
     * 使用指定的OpenGL程序
     * @param program
     * @param handler
     */
    public void useGLProgram(GLProgram program,GLProgramHandler handler) {
        this.program = program;
        this.programHandler = handler;
    }
    
    protected float revolveX(float x, float y, float cx, float cy, float theta) {
        float cdx = x - cx;
        float cdy = y - cy;
        return (float) (cdx * Math.cos(theta) - cdy * Math.sin(theta)) + cx;
    }

    protected float revolveY(float x, float y, float cx, float cy, float theta) {
        float cdx = x - cx;
        float cdy = y - cy;
        return (float) (cdx * Math.sin(theta) + cdy * Math.cos(theta)) + cy;
    }
    /**
     * 绘制前处理
     * @param gl
     */
    public void beforeDraw(GL2 gl) {
        if(program != null&&!program.isLinkError()) {
            if(!program.isLinked()) {
                program.initByGLContext(gl);
            }
            gl.glUseProgram(program.getProgramCode());
            if(programHandler != null) {
                programHandler.setUniformValue(program,gl);
            }
        }
    }
    /**
     * 图形对象的绘制方法
     * @param gl OpenGL 绘制对象
     */
    public abstract void drawShape(GL2 gl);
    /**
     * 绘制后处理
     * @param gl
     */
    public void afterDraw(GL2 gl){
        if(program != null&&!program.isLinkError()) {
            gl.glUseProgram(0);
        }
    }
}
