package com.thrblock.game.demo.component;

import java.awt.Color;
import java.awt.Font;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLNode;
import com.thrblock.cino.gltexture.GLFont;

@Component
public class FontImport extends CinoComponent {
    
    private String defaultFont = "Console";
    
    @Override
    public void init() throws Exception {
        GLNode node = shapeFactory.createNode();
        
        GLRect bg = shapeFactory.buildGLRect(0, 0, 800f, 600f);
        bg.setFill(true);
        bg.setAllPointColor(Color.GRAY);
        
        GLFont glfont = new GLFont(new Font(defaultFont,Font.PLAIN, 23));
        GLCharArea area = shapeFactory.buildGLCharArea(glfont, 0, 0, 750,20, "The quick brown fox jumps over the lazy dog. 0123456789[你好 中文！]");
        area.setHorAlia(GLCharArea.HOR_CENTRAL);
        area.setColor(Color.BLACK);
        shapeFactory.backtrack();
        onActivited(node::show);
        onDeactivited(node::hide);
    }
}
