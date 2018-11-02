package com.thrblock.game.demo;

import java.awt.Color;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.frame.AWTFrameFactory;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLShapeFactory;
import com.thrblock.springcontext.CinoInitor;

public class MACRectFillDemo {
    public static void main(String[] args) {
        AbstractApplicationContext context = CinoInitor.getContextByAnnotationConfig();
        AWTFrameFactory frameConf = context.getBean(AWTFrameFactory.class);
        frameConf.buildFrame();
        
        GLShapeFactory builder = context.getBean(GLShapeFactory.class);
        GLRect rect = builder.buildGLRect(0, 0, 100f, 100f);
        rect.setAllPointColor(Color.DARK_GRAY);
        rect.show();
//        rect.setRadian((float)Math.PI / 4);
        
        GLRect rect2 = builder.buildGLRect(0, 0, 100f, 50f);
        rect2.setAllPointColor(Color.RED);
        rect2.show();
        rect2.rightOf(rect,10f);
        
        GLRect rect3 = builder.buildGLRect(0, 0, 100f, 50f);
        rect3.setAllPointColor(Color.GREEN);
        rect3.show();
        rect3.topOf(rect,10f);
        
        GLRect rect4 = builder.buildGLRect(0, 0, 100f, 50f);
        rect4.setAllPointColor(Color.BLUE);
        rect4.show();
        rect4.leftOf(rect,10f);
        
        GLRect rect5 = builder.buildGLRect(0, 0, 100f, 50f);
        rect5.setAllPointColor(Color.YELLOW);
        rect5.show();
        rect5.bottomOf(rect,10f);
    }
}
