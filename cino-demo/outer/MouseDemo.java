package com.thrblock.game.demo.component;

import java.awt.Color;

import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.SubCompOf;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLRect;

@Component
@SubCompOf(SmtPhy.class)
public class MouseDemo extends CinoComponent {
    @Override
    public void init() throws Exception {
        shapeFactory.setLayer(-1);
        autoShowHide();
        
        GLRect rect = shapeFactory.buildGLRect(0, 0, 12, 12);
        rect.setAllPointColor(Color.GREEN);
        rect.setFill(true);
        auto(() -> {
            rect.setCentralX(mouseIO.getMouseX(-1));
            rect.setCentralY(mouseIO.getMouseY(-1));
        });
    }
}
