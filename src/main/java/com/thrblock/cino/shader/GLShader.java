package com.thrblock.cino.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL2;

/**
 * 着色程序
 * @author zepu.li
 *
 */
public class GLShader {
    private static final Logger LOG = LoggerFactory.getLogger(GLShader.class);
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
            
            IntBuffer intBuffer = IntBuffer.allocate(1);
            gl2.glGetShaderiv(shaderCode, GL2.GL_COMPILE_STATUS, intBuffer);
            if (intBuffer.get(0) != 1) {
                gl2.glGetShaderiv(shaderCode, GL2.GL_INFO_LOG_LENGTH, intBuffer);
                int size = intBuffer.get(0);
                if (size > 0) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                    gl2.glGetShaderInfoLog(shaderCode, size, intBuffer, byteBuffer);
                    LOG.warn("GLShader Compile Error:" + new String(byteBuffer.array()));
                } else {
                    LOG.warn("GLShader Compile Error,Because of Unknow error");
                }
            }
        }
    }
    
    private String[] convert(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while(line != null) {
            builder.append(line);
            builder.append('\n');
            line = reader.readLine();
        }
        return new String[]{builder.toString()};
    }
}
