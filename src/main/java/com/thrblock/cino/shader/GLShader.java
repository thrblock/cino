package com.thrblock.cino.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL2;

/**
 * 着色程序
 * @author zepu.li
 *
 */
public class GLShader {
	protected int shaderCode = -1;
	private int shaderType;
	private String[] srcs = {};
	/**
	 * 构造一个着色器Shader
	 * @param src 着色器源程序
	 * @param shaderType 着色器类型
	 * @see GL2#GL_VERTEX_SHADER
	 * @see GL2#GL_FRAGMENT_SHADER
	 */
	public GLShader(int shaderType,String[] src) {
		this.shaderType = shaderType;
		this.srcs = src;
	}
	
	/**
	 * 构造一个着色器Shader
	 * @param shaderType 着色器类型
	 * @param reader 着色器源reader
	 * @see GL2#GL_VERTEX_SHADER
	 * @see GL2#GL_FRAGMENT_SHADER
	 * @throws IOException 当reader读取异常时抛出
	 */
	public GLShader(int shaderType,BufferedReader reader) throws IOException {
		this.shaderType = shaderType;
		this.srcs = convert(reader);
	}
	
	/**
	 * 构造一个着色器Shader
	 * @param shaderType 着色器类型
	 * @param shaderFile 着色器源文件
	 * @see GL2#GL_VERTEX_SHADER
	 * @see GL2#GL_FRAGMENT_SHADER
	 * @throws IOException 当文件读取异常时抛出
	 */
	public GLShader(int shaderType,File shaderFile) throws IOException {
		this.shaderType = shaderType;
		try(BufferedReader reader = new BufferedReader(new FileReader(shaderFile))) {
			this.srcs = convert(reader);
		}
	}
	
	/**
	 * 构造一个着色器Shader
	 * @param shaderType 着色器类型
	 * @param is 着色器源数据流
	 * @see GL2#GL_VERTEX_SHADER
	 * @see GL2#GL_FRAGMENT_SHADER
	 * @throws IOException 当流读取异常时抛出
	 */
	public GLShader(int shaderType,InputStream is) throws IOException {
		this.shaderType = shaderType;
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			this.srcs = convert(reader);
		}
	}
	
	protected void parse(GL2 gl2) {
		if(shaderCode == -1) {
			shaderCode = gl2.glCreateShader(shaderType);
			gl2.glShaderSource(shaderCode, 1, srcs, null, 0);
			gl2.glCompileShader(shaderCode);
		}
	}
	
	private String[] convert(BufferedReader reader) throws IOException {
		List<String> srcLines = new LinkedList<>();
		String line = reader.readLine();
		while(line != null) {
			srcLines.add(line);
			line = reader.readLine();
		}
		String[] arr = new String[srcLines.size()];
		return srcLines.toArray(arr);
	}
}
