package com.thrblock.cino.shader;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLProgramHandler {
    private float time = 0f;
    
    public void addTime() {
        time += 0.01f;
    }
    
    public void settingUniform(GLProgram program,GL gl) {
        GL2 gl2 = gl.getGL2();
        int loc = gl2.glGetUniformLocation(program.getProgramCode(),"time");
        gl2.glUniform1f(loc, time);
    }
}
