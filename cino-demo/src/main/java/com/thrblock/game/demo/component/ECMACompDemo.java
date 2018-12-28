package com.thrblock.game.demo.component;

import java.awt.event.KeyEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.ECMAComponent;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.debug.DebugPannel;
import com.thrblock.cino.shader.data.GLCommonUniform;

@Component
@ECMAComponent("ecma.js")
public class ECMACompDemo extends CinoComponent {
    @Autowired
    GLCommonUniform commonsUniform;
    
    @Autowired
    DebugPannel pannel;
    
    @Override
    public void init() throws Exception {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R && keyIO.isKeyDown(KeyEvent.VK_CONTROL)) {
            try {
                this.reload();
            } catch (Exception ex) {
                logger.error("Exception in reload:{}", ex.getMessage());
            }
        }
    }
}
