package com.thrblock.cino;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Cino 初始化类
 * 使用Spring Context初始化各个组件
 * @author thrblock
 */
public class CinoInitor {
    private static AbstractApplicationContext context;
    private CinoInitor(){
    }
    
    /**
     * 获得context 当未加载时进行加载<br />
     * <b>不建议在多个类中进行重复调用，若需要context 请使用Spring依赖注入</b><br />
     * 多个类中的加载控制不当时会引起递归错误，强烈建议使用Spring容器加载您的组件<br />
     * 请使用xml或annotation进行组件设计
     */
    public static AbstractApplicationContext getCinoContext() {
        if(context == null) {
            context = new ClassPathXmlApplicationContext("cino-context.xml");
            context.registerShutdownHook();
        }
        return context;
    }
}
