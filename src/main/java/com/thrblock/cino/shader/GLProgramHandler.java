package com.thrblock.cino.shader;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * 着色器控制器 BETA功能，是一个着色程序的控制面板
 * @author user
 *
 */
public abstract class GLProgramHandler {
    protected void setUniform(GLProgram program,GL gl,String argName,float value) {
        GL2 gl2 = gl.getGL2();
        int loc = gl2.glGetUniformLocation(program.getProgramCode(),argName);
        gl2.glUniform1f(loc, value);
    }
    
    public abstract void setUniformValue(GLProgram program,GL gl);
}
