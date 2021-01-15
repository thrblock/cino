package com.largebug.test.animate;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.BootComponent;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLRect;

@Component
@BootComponent
public class AnimateDemo extends CinoComponent {
    @Override
    public void init() throws Exception {
        autoShowHide();
        GLRect rect = rootNode().glRect(0, 0, 100, 100);
        rect.setPointColor(0, Color.RED);
        rect.setPointColor(1, Color.GREEN);
        rect.setPointColor(2, Color.BLUE);
        rect.setPointColor(3, Color.CYAN);
        rect.setFill(true);
        
        auto(() -> {
            float y = 400 * (float) Math.sin((compAni.getClock() % 180) * 2 * Math.PI / 180);
            rect.setY(y);
        });
        
        autoMapEvent(KeyEvent.class, k -> {
            if (k.getKeyChar() == 'p') {
                compAni.pause();
            } else if (k.getKeyChar() == 'r') {
                compAni.remuse();
            }
        });
    }
}
