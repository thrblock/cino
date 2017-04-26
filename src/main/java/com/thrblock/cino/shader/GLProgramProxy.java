package com.thrblock.cino.shader;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * 程序代理 使用相同的程序 但带有独立的参数空间
 * @author zepu.li
 *
 */
public class GLProgramProxy extends AbstractGLProgram {
    private final GLProgram src;
    public GLProgramProxy(GLProgram src) {
        this.src = src;
    }
    @Override
    public void bind(GL2 gl) {
        src.bind(gl);
    }

    @Override
    public void unBind(GL2 gl) {
        src.unBind(gl);
    }

    @Override
    public int getProgramCode(GL gl) {
        return src.getProgramCode(gl);
    }

}
