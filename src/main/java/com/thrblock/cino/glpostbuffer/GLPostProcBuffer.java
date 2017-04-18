package com.thrblock.cino.glpostbuffer;

import java.nio.FloatBuffer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.annotation.GLAutoInit;
import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.glshape.GLPoint;

/**
 * OpenGL PostProcessing Buffer
 * 
 * @author zepu.li
 */
@Component
public class GLPostProcBuffer {
    private static final Logger LOG = LoggerFactory.getLogger(GLPostProcBuffer.class);

    private static final int[] FBO = new int[1];
    private static final int[] FBO_TEXTURE = new int[1];
    private static final int[] RBO_DEPTH = new int[1];

    private static final float[] FBO_VECS = { -1, -1, 1, -1, -1, 1, 1, 1, };
    private static final FloatBuffer FBO_VECS_BUF = FloatBuffer.allocate(FBO_VECS.length);

    private GLPoint[] points;
    private static final float[] beta = { 0, 1f, 1f, 0 };
    private static final float[] gama = { 1f, 1f, 0, 0 };

    @Value("${cino.frame.screen.width:800}")
    private int frameSizeW;
    @Value("${cino.frame.screen.height:600}")
    private int frameSizeH;
    
    private boolean resize = false;
    
    /**
     * OpenGL PostProcessing Buffer
     */
    public GLPostProcBuffer() {
        FBO_VECS_BUF.put(FBO_VECS);
    }

    @PostConstruct
    public void init() {
        points = new GLPoint[] { new GLPoint(-frameSizeW / 2, +frameSizeH / 2),
                new GLPoint(+frameSizeW / 2, +frameSizeH / 2), new GLPoint(+frameSizeW / 2, -frameSizeH / 2),
                new GLPoint(-frameSizeW / 2, -frameSizeH / 2) };
    }

    @GLAutoInit
    public void initByGLContext(GL gl) {
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

    public void bind(GL gl) {
        if(resize) {
            destroy(gl);
            initByGLContext(gl);
            resize = false;
        }
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, FBO[0]);
    }

    public void unBindAndDraw(GL gl) {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
        drawAsTexture(gl);
    }

    public void destroy(GL gl) {
        gl.glDeleteRenderbuffers(1, RBO_DEPTH, 0);
        gl.glDeleteTextures(1, FBO_TEXTURE, 0);
        gl.glDeleteFramebuffers(1, FBO, 0);
    }

    private void drawAsTexture(GL gl) {
        GL2 gl2 = gl.getGL2();
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
    }

    @ScreenSizeChangeListener
    public void noticeScreenChange(int w, int h) {
        this.frameSizeW = w;
        this.frameSizeH = h;
        resize = true;
    }
}
