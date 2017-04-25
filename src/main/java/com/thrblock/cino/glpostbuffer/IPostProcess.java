package com.thrblock.cino.glpostbuffer;

import com.thrblock.cino.shader.AbstractGLProgram;

public interface IPostProcess {
    public AbstractGLProgram getProgram();
    public void setProgram(AbstractGLProgram program);
}
