package com.thrblock.game.demo.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.FontsFromWindows;
import com.thrblock.cino.gltexture.GLFont;
import com.thrblock.cino.util.charprocess.CentralInLine;
import com.thrblock.cino.util.charprocess.CharAreaConfig;

@Component
public class CharAreaDemo extends CinoComponent {

    StringBuilder builder = new StringBuilder();
    GLCharArea area;

    @Override
    public void init() throws Exception {
        builder.append("OpenGL is Open Graphics Library");
        GLRect bg = shapeFactory.buildGLRect(0, 0, screenW, screenH);
        bg.setAllPointColor(Color.GRAY);
        bg.setFill(true);

        CharAreaConfig config = new CharAreaConfig(128);
        config.setPositionSyn(new CentralInLine());
        config.setFont(new GLFont(new Font(FontsFromWindows.MICROSOFT_YAHEI_UI, Font.PLAIN, 16)));

        area = shapeFactory.buildGLCharArea(0, 0, 400, 300, config);
        area.setSimpleStyle(img -> img.setAllPointColor(Color.BLACK));
        area.setContent(builder.toString());
        autoShowHide();
        autoKeyPushPop();
        auto(() -> {
            if (keyIO.isKeyDown(KeyEvent.VK_LEFT)) {
                area.setRadian(area.getRadian() + 0.03f);
            } else if (keyIO.isKeyDown(KeyEvent.VK_RIGHT)) {
                area.setRadian(area.getRadian() - 0.03f);
            }
            if(keyIO.isKeyDown(KeyEvent.VK_SPACE)) {
                area.setRadian(0);
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            builder.setLength(Math.max(builder.length() - 1, 0));
            area.setContent(builder.toString());
        }
        if (e.getKeyCode() == KeyEvent.VK_1) {
            area.hide();
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
            area.show();
        }
        char c = (char) e.getKeyCode();
        if (c == ' ' || (c >= 65 && c <= 90)) {
            builder.append(c);
            area.setContent(builder.toString());
        }

    }
}
