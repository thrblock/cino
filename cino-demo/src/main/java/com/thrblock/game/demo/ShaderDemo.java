package com.thrblock.game.demo;

import java.io.File;
import java.io.IOException;

import org.springframework.context.support.AbstractApplicationContext;

import com.jogamp.opengl.GL2;
import com.thrblock.cino.frame.AWTFrameFactory;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLShapeFactory;
import com.thrblock.cino.shader.GLProgram;
import com.thrblock.cino.shader.GLShader;
import com.thrblock.springcontext.CinoInitor;

public class ShaderDemo {
    public static void main(String[] args) throws IOException {
        AbstractApplicationContext context = CinoInitor.getContextByXml();
        AWTFrameFactory config = context.getBean(AWTFrameFactory.class);
        config.buildFrame();

        GLShader vertex = new GLShader(GL2.GL_VERTEX_SHADER, new File("./shaders/demo/Vertex.txt"));
        GLShader fragment = new GLShader(GL2.GL_FRAGMENT_SHADER, new File("./shaders/demo/Frag_blocks.txt"));

        GLProgram program = new GLProgram(vertex, fragment);

        GLShapeFactory builder = context.getBean(GLShapeFactory.class);
        GLRect bg = builder.buildGLRect(0, 0, 800f, 600f);
        bg.setFill(true);
        bg.useGLProgram(program);
        bg.show();
    }
}
