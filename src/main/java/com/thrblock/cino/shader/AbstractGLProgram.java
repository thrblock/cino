package com.thrblock.cino.shader;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.shader.data.GLUniformFloat;
import com.thrblock.cino.shader.data.GLUniformInt;
import com.thrblock.cino.util.structure.CrudeLinkedList;

/**
 * A GLSL Program
 * @author zepu.li
 *
 */
public abstract class AbstractGLProgram {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractGLProgram.class);
    private CrudeLinkedList<Consumer<GL2>> uniforms = new CrudeLinkedList<>();
    private CrudeLinkedList<Consumer<GL2>>.CrudeIter uniIter = uniforms.genCrudeIter();
    private Set<String> names = new HashSet<>();
    /**
     * bind the program to gl context
     * @param gl
     */
    public abstract void bind(GL2 gl);

    /**
     * unbind the program from gl context
     * @param gl
     */
    public abstract void unBind(GL2 gl);

    /**
     * get glsl code in gl context
     * @param gl
     * @return
     */
    public abstract int getProgramCode(GL gl);

    protected void setUniformValue(GL gl) {
        GL2 gl2 = gl.getGL2();
        while (uniIter.hasNext()) {
            Consumer<GL2> uniform = uniIter.next();
            uniform.accept(gl2);
        }
        uniIter.reset();
    }

    private void checkName(String name) {
        if(!names.add(name)) {
            LOG.warn("uniform name conflict in shader,the name is {}",name);
        }
    }
    /**
     * bind a int data source to uniform int
     * @param data
     */
    public void bindDataAsInt(GLUniformInt data) {
        checkName(data.getName());
        uniforms.add(gl2 -> {
            int pgcode = getProgramCode(gl2);
            int loc = gl2.glGetUniformLocation(pgcode, data.getName());
            gl2.glUniform1i(loc, data.getValue());
        });
    }

    /**
     * bind a float data source to uniform float
     * @param data
     */
    public void bindDataAsFloat(GLUniformFloat data) {
        checkName(data.getName());
        uniforms.add(gl2 -> {
            int pgcode = getProgramCode(gl2);
            int loc = gl2.glGetUniformLocation(pgcode, data.getName());
            gl2.glUniform1f(loc, data.getValue());
        });
    }

    /**
     * bind a float array data source to uniform float array
     * @param name
     * @param data
     */
    public void bindDataAsIntArray(String name, int[] data) {
        checkName(name);
        uniforms.add(gl2 -> {
            int pgcode = getProgramCode(gl2);
            int loc = gl2.glGetUniformLocation(pgcode, name);
            gl2.glUniform1iv(loc, data.length, data, 0);
        });
    }

    /**
     * bind a float array data source to uniform float vec
     * @param name
     * @param data
     */
    public void bindDataAsIntVec(String name, int[] data) {
        checkName(name);
        uniforms.add(gl2 -> {
            int pgcode = getProgramCode(gl2);
            int loc = gl2.glGetUniformLocation(pgcode, name);
            switch (data.length) {
            case 2:
                gl2.glUniform2i(loc, data[0], data[1]);
                break;
            case 3:
                gl2.glUniform3i(loc, data[0], data[1], data[2]);
                break;
            case 4:
                gl2.glUniform4i(loc, data[0], data[1], data[2], data[3]);
                break;
            default:
                break;
            }
        });
    }
    
    /**
     * bind a uniform int array
     * @param name
     * @param data
     */
    public void bindDataAsFloatArray(String name, float[] data) {
        checkName(name);
        uniforms.add(gl2 -> {
            int pgcode = getProgramCode(gl2);
            int loc = gl2.glGetUniformLocation(pgcode, name);
            gl2.glUniform1fv(loc, data.length, data, 0);
        });
    }
    
    /**
     * bind a uniform float array
     * @param name
     * @param data
     */
    public void bindDataAsFloatVec(String name, float[] data) {
        checkName(name);
        uniforms.add(gl2 -> {
            int pgcode = getProgramCode(gl2);
            int loc = gl2.glGetUniformLocation(pgcode, name);
            switch (data.length) {
            case 2:
                gl2.glUniform2f(loc, data[0], data[1]);
                break;
            case 3:
                gl2.glUniform3f(loc, data[0], data[1], data[2]);
                break;
            case 4:
                gl2.glUniform4f(loc, data[0], data[1], data[2], data[3]);
                break;
            default:
                break;
            }
        });
    }

}
