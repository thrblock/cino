package com.thrblock.game.demo.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.FontsFromWindows;
import com.thrblock.cino.gltexture.GLFont;
import com.thrblock.cino.util.charprocess.CharAreaConfig;
import com.thrblock.cino.util.charprocess.CharRectArea;
import com.thrblock.cino.util.charprocess.CharUtils;
import com.thrblock.cino.util.charprocess.ControlAsArea;
import com.thrblock.cino.util.math.CRand;

@Component
public class CharAreaDemo extends CinoComponent {

    StringBuilder builder = new StringBuilder();
    CharRectArea area;
    
    @Override
    public void init() throws Exception {
        builder.append("OpenGL is Open Graphics Library");
        GLRect bg = shapeFactory.buildGLRect(0, 0, screenW, screenH);
        bg.setAllPointColor(Color.GRAY);
        bg.setFill(true);

        CharAreaConfig config = new CharAreaConfig(256);
        config.setPositionSyn(new ControlAsArea());
        config.setFont(new GLFont(new Font(FontsFromWindows.MICROSOFT_YAHEI_UI, Font.PLAIN, 28)));
        
        Color[] colors = Stream.iterate(0,i -> i + 1).limit(10).map(i -> CRand.getRandomWarmColor()).toArray(Color[]::new);
        
        config.setStyle((arr,i,img) -> {
            img.setAllPointColor(colors[i % colors.length]);
        });

        area = charRectFactory.charRectArea(0, 0, 800, 600, config);
        area.setContent(builder.toString());
        
        autoShowHide();
        autoKeyPushPop();
        auto(() -> {
            if (keyIO.isKeyDown(KeyEvent.VK_LEFT)) {
                area.setRadian(area.getRadian() + 0.03f);
            } else if (keyIO.isKeyDown(KeyEvent.VK_RIGHT)) {
                area.setRadian(area.getRadian() - 0.03f);
            }
            if (keyIO.isKeyDown(KeyEvent.VK_SPACE)) {
                area.setRadian(0);
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(area == null) {
            return;
        }
        if(e.getKeyCode() == KeyEvent.VK_X) {
            try {
                area.preDestroy();
                area = null;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            builder.setLength(Math.max(builder.length() - 1, 0));
            area.setContent(builder.toString());
        }
        char c = (char) e.getKeyCode();
        if (CharUtils.isPrintableChar(c) || c == '\n') {
            if (keyIO.isKeyDown(KeyEvent.VK_SHIFT)) {
                c = CharUtils.tryConvertToSimbol(c);
            }
            if (keyIO.useCapital()) {
                builder.append(Character.toUpperCase(c));
            } else {
                builder.append(Character.toLowerCase(c));
            }
            area.setContent(builder.toString());
        }

    }
}
