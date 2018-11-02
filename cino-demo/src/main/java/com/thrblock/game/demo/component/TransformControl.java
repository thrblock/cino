package com.thrblock.game.demo.component;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.gltransform.GLTransform;

@Component
public class TransformControl extends CinoComponent {
    @Override
    public void init() {
        GLTransform t = new GLTransform(GLTransform.TSR);
        transformManager.addBeforeLayer(t, 0);

        transformManager.addBeforeLayer(new GLTransform(), -1);

        autoMapEvent(MouseWheelEvent.class, e -> {
            if (e.getWheelRotation() > 0) {
                t.setScale(t.getScaleX() * 0.9f);
            } else {
                t.setScale(t.getScaleX() * 1.1f);
            }
        });
        auto(() -> {
            if (keyIO.isKeyDown(KeyEvent.VK_UP)) {
                t.setTranslateY(t.getTranslateY() + 5f);
            } else if (keyIO.isKeyDown(KeyEvent.VK_DOWN)) {
                t.setTranslateY(t.getTranslateY() - 5f);
            }

            if (keyIO.isKeyDown(KeyEvent.VK_LEFT)) {
                t.setTranslateX(t.getTranslateX() - 5f);
            } else if (keyIO.isKeyDown(KeyEvent.VK_RIGHT)) {
                t.setTranslateX(t.getTranslateX() + 5f);
            }
        });
        activited();
    }
}
