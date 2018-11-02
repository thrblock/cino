package com.thrblock.game.demo.component;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL2;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.debug.DebugPannel;
import com.thrblock.cino.gllayer.GLFrameBufferObject;
import com.thrblock.cino.shader.GLProgram;
import com.thrblock.cino.shader.GLShader;
import com.thrblock.cino.shader.data.GLCommonUniform;

@Component
public class ShaderToy extends CinoComponent {
    @Autowired
    private GLCommonUniform commonsUniform;
    @Autowired
    DebugPannel pannel;

    @Override
    public void init() throws Exception {
        GLShader vex = new GLShader(GL2.GL_VERTEX_SHADER, new File("./shadersV2/demo/Vertex.txt"));
        GLShader frg = new GLShader(GL2.GL_FRAGMENT_SHADER, new File("./shadersV2/demo/Frag_49899.0"));
        GLProgram program = new GLProgram(vex, frg);
        auto(commonsUniform.setCommonUniform(program));
        GLFrameBufferObject fbo = fboManager.generateLayerFBO(0);
        pannel.activited();
        onActivited(() -> fbo.setGLProgram(program));
        onDeactivited(() -> fbo.setGLProgram(null));
    }
}
