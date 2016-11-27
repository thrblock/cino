package com.thrblock.cino.shader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL2;

/**
 * OpenGL编译程序，包括多个着色器程序
 * @author zepu.li
 *
 */
public class GLProgram {
	private static final Logger LOG = LoggerFactory.getLogger(GLProgram.class);
	private int programCode;
	private GLShader[] shaders;

	/**
	 * 由复数个着色器构造一个OpenGL程序
	 * @param shaders 着色器数组
	 */
	public GLProgram(GLShader... shaders) {
		this.shaders = shaders;
	}

	/**
	 * 使用OpenGL上下文编译着色器程序
	 * @param gl2
	 */
	public void parse(GL2 gl2) {
		this.programCode = gl2.glCreateProgram();
		for (GLShader shader : shaders) {
			shader.parse(gl2);
			gl2.glAttachShader(programCode, shader.shaderCode);
		}
		gl2.glLinkProgram(programCode);
		gl2.glValidateProgram(programCode);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl2.glGetProgramiv(programCode, GL2.GL_LINK_STATUS, intBuffer);

		if (intBuffer.get(0) != 1) {
			gl2.glGetProgramiv(programCode, GL2.GL_INFO_LOG_LENGTH, intBuffer);
			int size = intBuffer.get(0);
			LOG.warn("GLProgram Link Error!");
			if (size > 0) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl2.glGetProgramInfoLog(programCode, size, intBuffer, byteBuffer);
				LOG.warn("GLProgram Link Error:" + byteBuffer.asCharBuffer().toString());
			} else {
				LOG.warn("GLProgram Link Error,Because of Unknow error");
			}
		}
	}
}
