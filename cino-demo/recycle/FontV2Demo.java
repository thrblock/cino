package com.thrblock.game.demo;

import java.awt.Color;
import java.awt.Font;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.glanimate.GLAnimateFactory;
import com.thrblock.cino.frame.AWTFrameFactory;
import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLShapeFactory;
import com.thrblock.cino.gltexture.GLFont;
import com.thrblock.cino.util.structure.CharArrayInt;
import com.thrblock.springcontext.CinoInitor;

public class FontV2Demo {
    public static void main(String[] args) {
        AbstractApplicationContext context = CinoInitor.getContextByXml();
        AWTFrameFactory config = context.getBean(AWTFrameFactory.class);
        config.buildFrame();
        
        GLShapeFactory builder = context.getBean(GLShapeFactory.class);
        GLRect bg = builder.buildGLRect(0, 0, 800f, 600f);
        bg.setFill(true);
        bg.setAllPointColor(Color.DARK_GRAY);
        bg.show();
        
        Font font = new Font("Consolas",Font.PLAIN,45);
        char[] arr = {'0','0','0','0','0','0','0'};
        float w = 200f;
        float h = 50f;
        GLCharArea line = builder.buildGLCharArea(new GLFont(font),0,0,w,h,arr);
        line.setHorAlia(GLCharArea.HOR_CENTRAL);
        line.setVerAlia(GLCharArea.VER_CENTRAL);
        line.show();
        
        GLRect r = builder.buildGLRect(0, 0, w, h);
        r.show();
        GLLine l1 = builder.buildGLLine(0, 300,0,-300);
        l1.setAllPointColor(Color.GREEN);
        l1.show();
        
        GLLine l2 = builder.buildGLLine(-400, 0,400,0);
        l2.setAllPointColor(Color.YELLOW);
        l2.show();
        
        CharArrayInt arrInt = new CharArrayInt(arr);
        arrInt.setByInt(65535);
        context.getBean(GLAnimateFactory.class).build(()->arrInt.addByInt(-1))
        .enable();
    }
}
