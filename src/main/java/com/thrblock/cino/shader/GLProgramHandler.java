package com.thrblock.cino.shader;

import java.util.Arrays;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.util.structure.StrFloatPair;
import com.thrblock.cino.util.structure.StrIntPair;

/**
 * 着色器控制器 BETA功能，是一个着色程序的控制面板
 * 
 * @author user
 *
 */
public class GLProgramHandler {
    protected GLProgram program;
    protected StrIntPair[] intPairs = new StrIntPair[4];
    protected StrFloatPair[] floatPairs = new StrFloatPair[4];

    public GLProgramHandler(GLProgram program) {
        this.program = program;
    }

    public void setUniformf(String argName, float value) {
        int i = 0;
        for(;i < floatPairs.length; i++) {
            if(floatPairs[i] == null) {
                floatPairs[i] = new StrFloatPair(argName);
                floatPairs[i].setValue(value);
                return;
            } else if(floatPairs[i].getKey().equals(argName)) {
                floatPairs[i].setValue(value);
                return;
            }
        }
        this.floatPairs = Arrays.copyOf(floatPairs, floatPairs.length * 2);
        floatPairs[i] = new StrFloatPair(argName);
        floatPairs[i].setValue(value);
    }

    public void setUniformi(String argName, int value) {
        int i = 0;
        for(;i < intPairs.length; i++) {
            if(intPairs[i] == null) {
                intPairs[i] = new StrIntPair(argName);
                intPairs[i].setValue(value);
                return;
            } else if(intPairs[i].getKey().equals(argName)) {
                intPairs[i].setValue(value);
                return;
            }
        }
        this.intPairs = Arrays.copyOf(intPairs, intPairs.length * 2);
        intPairs[i] = new StrIntPair(argName);
        intPairs[i].setValue(value);
    }

    public void setUniformValue(GL gl) {
        GL2 gl2 = gl.getGL2();
        for (int i = 0; i < intPairs.length; i++) {
            if (intPairs[i] == null) {
                break;
            }
            int loc = gl2.glGetUniformLocation(program.getProgramCode(gl), intPairs[i].getKey());
            gl2.glUniform1i(loc, intPairs[i].getValue());
        }
        for (int i = 0; i < floatPairs.length; i++) {
            if (floatPairs[i] == null) {
                break;
            }
            int loc = gl2.glGetUniformLocation(program.getProgramCode(gl), floatPairs[i].getKey());
            gl2.glUniform1f(loc, floatPairs[i].getValue());
        }
    }

    public GLProgram getProgram() {
        return program;
    }
}
