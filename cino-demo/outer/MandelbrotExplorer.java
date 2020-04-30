package com.thrblock.game.demo.component;

import java.awt.event.KeyEvent;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL2ES2;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.debug.DebugPannel;
import com.thrblock.cino.shader.GLProgram;
import com.thrblock.cino.shader.GLShader;
import com.thrblock.cino.shader.data.GLCommonUniform;
import com.thrblock.cino.shader.data.GLUniformFloat;

@Component
public class MandelbrotExplorer extends CinoComponent {
    @Autowired
    GLCommonUniform commonsUniform;
    
    @Autowired
    DebugPannel pannel;
    
    @Override
    public void init() throws Exception {
        this.autoKeyPushPop();
        var vex = new GLShader(GL2ES2.GL_VERTEX_SHADER, new File("./shadersV2/demo/Vertex.txt"));
        var frg = new GLShader(GL2ES2.GL_FRAGMENT_SHADER, new File("./shadersV2/demo/Frag_Mandelbrot_Explorer"));
        var program = new GLProgram(vex, frg);
        auto(commonsUniform.setCommonUniform(program));
        
        var scale = new GLUniformFloat("sc");
        scale.setValue(1.0f);
        var maxIter = new GLUniformFloat("max_iter");
        maxIter.setValue(28.f);
        program.bindDataAsFloat(scale);
        var offset = new float[2];
        program.bindDataAsFloatVec("offset", offset);
        program.bindDataAsFloat(maxIter);
        auto(() -> {
            if(keyIO.isKeyDown(KeyEvent.VK_EQUALS)) {
                scale.setValue(scale.getValue() * 0.99f);
            } else if(keyIO.isKeyDown(KeyEvent.VK_MINUS)) {
                scale.setValue(scale.getValue() * 1.01f);
            }
            var scaleNum = Math.log(scale.getValue()) / Math.log(0.5) + 1;
            var offsetSpd = 0.03f;
            if(scaleNum > 1) {
                offsetSpd /= scaleNum;
            }
            if(keyIO.isKeyDown(KeyEvent.VK_W)) {
                offset[1] += offsetSpd;
            } else if (keyIO.isKeyDown(KeyEvent.VK_S)) {
                offset[1] -= offsetSpd;
            }
            if(keyIO.isKeyDown(KeyEvent.VK_A)) {
                offset[0] -= offsetSpd;
            } else if (keyIO.isKeyDown(KeyEvent.VK_D)) {
                offset[0] += offsetSpd;
            }
            if(keyIO.isKeyDown(KeyEvent.VK_Z)) {
                maxIter.setValue(maxIter.getValue() + 1);
            } else if (keyIO.isKeyDown(KeyEvent.VK_X)) {
                maxIter.setValue(maxIter.getValue() - 1);
            }
        });
        var fbo = fboManager.generateLayerFBO(0);
        this.onDestroy(() -> fboManager.removeFBO(fbo));
        this.onActivited(() -> fbo.setGLProgram(program));
        this.onDeactivited(() -> fbo.setGLProgram(null));
        pannel.activited();
    }
}
