package com.largebug.test.transform;

import java.awt.event.KeyEvent;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.thrblock.cino.annotation.BootComponent;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.gltransform.GLTransform;
import com.thrblock.cino.util.math.CMath;
import com.thrblock.cino.util.math.CRand;

@Component
@BootComponent
public class TransformDemo extends CinoComponent {

    @Override
    public void init() throws Exception {
        autoShowHide();
        CMath.cartesianProduct(Sets.newHashSet(-100, 100), Lists.newArrayList(-100, 100))
                .map(e -> rootNode().glRect(e.getKey(), e.getValue(), 100, 100)).forEach(r -> {
                    r.setFill(true);
                    r.setAllPointColor(CRand.getRandomWarmColor());
                });
        
        GLTransform tf = rootNode().currentTransform();
        rootNode().wrapNode(c -> {
            c.createTransform();
            c.glLine(0, 100, 0, -100);
            c.glLine(-100, 0, 100, 0);
        });
        auto(() -> {
            if (keyIO.isKeyDown(KeyEvent.VK_1)) {
                tf.setTranslateX(-100f);
                tf.setTranslateY(100f);
            }
            if (keyIO.isKeyDown(KeyEvent.VK_2)) {
                tf.setTranslateX(100f);
                tf.setTranslateY(100f);
            }
            if (keyIO.isKeyDown(KeyEvent.VK_3)) {
                tf.setTranslateX(100f);
                tf.setTranslateY(-100f);
            }
            if (keyIO.isKeyDown(KeyEvent.VK_4)) {
                tf.setTranslateX(-100f);
                tf.setTranslateY(-100f);
            }
            if (keyIO.isKeyDown(KeyEvent.VK_5)) {
                tf.setTranslateX(0f);
                tf.setTranslateY(0f);
            }
        });
    }
}
