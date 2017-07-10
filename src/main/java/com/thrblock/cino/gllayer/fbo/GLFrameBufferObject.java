package com.thrblock.cino.gllayer.fbo;

import java.nio.FloatBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.shader.AbstractGLProgram;

/**
 * OpenGL 帧缓冲对象
 * @author zepu.li
 *
 */
public class GLFrameBufferObject {
    private static final Logger LOG = LoggerFactory.getLogger(GLFrameBufferObject.class);
    
    private static final int[] FBO = new int[1];
    private static final int[] FBO_TEXTURE = new int[1];
    private static final int[] RBO_DEPTH = new int[1];

    private static final float[] FBO_VECS = { -1, -1, 1, -1, -1, 1, 1, 1, };
    private static final FloatBuffer FBO_VECS_BUF = FloatBuffer.allocate(FBO_VECS.length);
    
    private GLPoint[] points;
    private static final float[] beta = { 0, 1f, 1f, 0 };
    private static final float[] gama = { 1f, 1f, 0, 0 };
    
    private int frameSizeW;
    private int frameSizeH;
    
    private boolean resize = false;
    private boolean inited = false;
    
    protected AbstractGLProgram program;
    
    /**
     * OpenGL PostProcessing Buffer
     * @param frameSizeW
     * @param frameSizeH
     */
    public GLFrameBufferObject(int frameSizeW,int frameSizeH) {
        this.frameSizeW = frameSizeW;
        this.frameSizeH = frameSizeH;
        FBO_VECS_BUF.put(FBO_VECS);
        initPointPosition();
    }
    
    private void initPointPosition() {
        points = new GLPoint[] { new GLPoint(-frameSizeW / 2, +frameSizeH / 2),
                new GLPoint(+frameSizeW / 2, +frameSizeH / 2), new GLPoint(+frameSizeW / 2, -frameSizeH / 2),
                new GLPoint(-frameSizeW / 2, -frameSizeH / 2) };
    }
    
    /**
     * 在OpenGL环境下初始化此缓冲区
     * @param gl
     */
    public void initFBOByGLContext(GL gl) {
        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glGenTextures(1, FBO_TEXTURE, 0);
        // 准备纹理
        gl.glBindTexture(GL.GL_TEXTURE_2D, FBO_TEXTURE[0]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, frameSizeW, frameSizeH, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,
                null);
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);

        // 准备深度缓冲区
        gl.glGenRenderbuffers(1, RBO_DEPTH, 0);
        gl.glBindRenderbuffer(GL.GL_RENDERBUFFER, RBO_DEPTH[0]);
        gl.glRenderbufferStorage(GL.GL_RENDERBUFFER, GL.GL_DEPTH_COMPONENT16, frameSizeW, frameSizeH);
        gl.glBindRenderbuffer(GL.GL_RENDERBUFFER, 0);

        // 将纹理与深度缓冲绑定在帧缓冲中
        gl.glGenFramebuffers(1, FBO, 0);
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, FBO[0]);
        gl.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, GL.GL_TEXTURE_2D, FBO_TEXTURE[0], 0);
        gl.glFramebufferRenderbuffer(GL.GL_FRAMEBUFFER, GL.GL_DEPTH_ATTACHMENT, GL.GL_RENDERBUFFER, RBO_DEPTH[0]);
        int status;
        if ((status = gl.glCheckFramebufferStatus(GL.GL_FRAMEBUFFER)) != GL.GL_FRAMEBUFFER_COMPLETE) {
            LOG.warn("glCheckFramebufferStatus error,status:" + status);
        }
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
    }
    
    /**
     * 在OpenGL环境下销毁此缓冲区
     * @param gl
     */
    public void destroyFBOByGLContext(GL gl) {
        gl.glDeleteRenderbuffers(1, RBO_DEPTH, 0);
        gl.glDeleteTextures(1, FBO_TEXTURE, 0);
        gl.glDeleteFramebuffers(1, FBO, 0);
    }
    
    /**
     * 改变帧缓冲区大小 例如屏幕窗体改变
     * @param frameW
     * @param frameH
     */
    public void resize(int frameW,int frameH) {
        this.resize = true;
        this.frameSizeW = frameW;
        this.frameSizeH = frameH;
    }
    
    /**
     * 将当前绘制上下文绑定在此缓冲区上
     * @param gl
     */
    public void bindFBO(GL gl) {
        if(resize) {
            destroyFBOByGLContext(gl);
            resize = false;
            inited = false;
        }
        if(!inited) {
            initFBOByGLContext(gl);
            inited = true;
        }
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, FBO[0]);
    }
    
    /**
     * 将当前上下文由此缓冲区解绑（重置为默认帧缓冲对象）
     * @param gl
     */
    public void unBindFBO(GL gl) {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
    }
    
    /**
     * 将此缓冲区对象以矩形纹理绘制
     * @param gl
     */
    public void drawAsTexture(GL gl) {
        GL2 gl2 = gl.getGL2();
        bindProgram(gl2);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl2.glBindTexture(GL.GL_TEXTURE_2D, FBO_TEXTURE[0]);
        gl2.glEnable(GL.GL_TEXTURE_2D);

        gl2.glBegin(GL2.GL_QUADS);
        for (int i = 0; i < 4; i++) {
            gl2.glColor4f(points[i].getR(), points[i].getG(), points[i].getB(), points[i].getAlpha());
            gl2.glTexCoord2f(beta[i], gama[i]);
            gl2.glVertex2f(points[i].getX(), points[i].getY());
        }
        gl2.glEnd();

        gl2.glBindTexture(GL.GL_TEXTURE_2D, 0);
        gl2.glDisable(GL.GL_TEXTURE_2D);
        unBindProgram(gl2);
    }
    
    private void bindProgram(GL2 gl) {
        if(program != null) {
            program.bind(gl);
        }
    }
    
    private void unBindProgram(GL2 gl){
        if(program != null) {
            program.unBind(gl);
        }
    }
    
    /**
     * 为此帧缓冲区对象指定着色程序
     * @param program
     */
    public void setGLProgram(AbstractGLProgram program) {
        this.program = program;
    }
    
}
