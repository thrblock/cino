package com.thrblock.game.demo;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.glanimate.GLAnimateFactory;
import com.thrblock.cino.frame.AWTFrameFactory;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLShapeFactory;
import com.thrblock.cino.io.KeyControlStack;
import com.thrblock.springcontext.CinoInitor;

public class RoteDemo {
    public static void main(String[] args) {
        AbstractApplicationContext context = CinoInitor.getContextByXml();
        AWTFrameFactory config = context.getBean(AWTFrameFactory.class);
        config.buildFrame();

        KeyControlStack keyIO = context.getBean(KeyControlStack.class);
        GLShapeFactory builder = context.getBean(GLShapeFactory.class);

        GLRect cRect = builder.buildGLRect(400f, 300f, 100f, 30f);
        cRect.setAllPointColor(Color.RED);
        cRect.show();

        GLRect directRef = builder.buildGLRect(cRect.getPointX(0) - 50f, cRect.getPointY(0) - 50, 40f, 20f);
        directRef.setAllPointColor(Color.GREEN);
        directRef.show();

        context.getBean(GLAnimateFactory.class).build(() -> {
            if (keyIO.isKeyDown(KeyEvent.VK_A)) {
                cRect.setRadian(cRect.getRadian() + 0.05f);
                directRef.revolve(0.05f, cRect.getCentralX(), cRect.getCentralY());
            } else if (keyIO.isKeyDown(KeyEvent.VK_S)) {
                cRect.setRadian(cRect.getRadian() - 0.05f);
                directRef.revolve(-0.05f, cRect.getCentralX(), cRect.getCentralY());
            }
        }).enable();
    }
}
