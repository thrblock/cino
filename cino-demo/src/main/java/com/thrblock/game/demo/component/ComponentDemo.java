package com.thrblock.game.demo.component;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.frame.AWTFrameFactory;
import com.thrblock.game.demo.DemoContext;
import com.thrblock.springcontext.CinoInitor;

public class ComponentDemo {
    public static void main(String[] args) throws Exception {
        
        AbstractApplicationContext context = CinoInitor.getCustomContext(DemoContext.class);
        AWTFrameFactory builder = context.getBean(AWTFrameFactory.class);
        builder.buildFrame();

        CinoComponent d = context.getBean(CharAreaDemo.class);
        d.activited();
    }
}
