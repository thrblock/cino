package com.thrblock.cino.shader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.glinitable.GLInitializable;

/**
 * OpenGL编译程序，包括多个着色器程序
 * 
 * @author zepu.li
 *
 */
public class GLProgram extends AbstractGLProgram implements GLInitializable {
    private static final Logger LOG = LoggerFactory.getLogger(GLProgram.class);
    private int programCode = 0;
    private GLShader[] shaders;
    private boolean linked = false;
    private boolean linkError = false;

    /**
     * 由复数个着色器构造一个OpenGL程序
     * 
     * @param shaders 着色器数组
     */
    public GLProgram(GLShader... shaders) {
        this.shaders = shaders;
    }

    /**
     * {@inheritDoc}<br />
     * 使用OpenGL上下文编译着色器程序
     * 
     * @param gl2
     */
    @Override
    public void initByGLContext(GL gl) {
        GL2 gl2 = gl.getGL2();
        this.programCode = gl2.glCreateProgram();
        for (GLShader shader : shaders) {
            shader.parse(gl2);
            gl2.glAttachShader(programCode, shader.shaderCode);
        }
        gl2.glLinkProgram(programCode);
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl2.glGetProgramiv(programCode, GL2.GL_LINK_STATUS, intBuffer);

        if (intBuffer.get(0) != GL.GL_TRUE) {
            linkError = true;
            gl2.glGetProgramiv(programCode, GL2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            LOG.warn("GLProgram Link Error!");
            if (size > 0 && LOG.isWarnEnabled()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                gl2.glGetProgramInfoLog(programCode, size, intBuffer, byteBuffer);
                LOG.warn("GLProgram Link Error:{}", new String(byteBuffer.array()));
            } else {
                LOG.warn("GLProgram Link Error,Because of Unknow error");
            }
        } else {
            linked = true;
        }
    }

    @Override
    public int getProgramCode(GL gl) {
        if (!linked && !linkError) {
            initByGLContext(gl);
        }
        return programCode;
    }

    public boolean isLinkError() {
        return linkError;
    }

    public boolean isLinked() {
        return linked;
    }

    @Override
    public void bind(GL2 gl) {
        if (!isLinkError()) {
            gl.glUseProgram(programCode);
            setUniformValue(gl);
        }
    }

    @Override
    public void unBind(GL2 gl) {
        if (!isLinkError()) {
            gl.glUseProgram(0);
        }
    }

    /**
     * 创建一个着色程序代理-使用相同着色程序但具有独立参数空间的结构
     * 
     * @return
     */
    public GLProgramProxy genProxy() {
        return new GLProgramProxy(this);
    }
}
