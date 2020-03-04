package com.thrblock.game.demo.component;

import java.io.File;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.gltexture.GLIOTexture;
import com.thrblock.cino.gltexture.GLTexture;

@Component
public class HatComponent extends CinoComponent {
    @Override
    public void init() throws Exception {
        this.autoShowHide();
        
        GLTexture tx = new GLIOTexture(new File("./hat.png"));
        GLImage img = shapeFactory.buildGLImage(0, 0, screenW, screenW, tx);
        
        auto(() -> img.setRadian(img.getRadian() + 0.03f));
    }
}
