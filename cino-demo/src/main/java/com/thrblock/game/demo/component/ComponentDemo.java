package com.thrblock.game.demo.component;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.game.demo.DemoContext;
import com.thrblock.springcontext.CinoInitor;

public class ComponentDemo {
    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = CinoInitor.getCustomContext(DemoContext.class);
        CinoComponent d = context.getBean(SmtPhy.class);
        d.activited();
    }
}
