package com.thrblock.springcontext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Cino 初始化类 使用Spring Context初始化各个组件
 * 
 * @author thrblock
 */
public class CinoInitor {
    private static AbstractApplicationContext context;

    static {
        System.setProperty("sun.java2d.uiScale", "1.0");
    }
    CinoInitor() {
    }

    /**
     * 基于xml配置获得context 当未加载时进行加载 默认加载内容仅限于com.thrblock.cino包<br />
     * <b>不建议在多个类中进行重复调用，若需要context 请使用Spring依赖注入</b><br />
     * 多个类中的加载控制不当时会引起递归错误，强烈建议使用Spring容器加载您的组件<br />
     * 请使用xml或annotation进行组件设计
     */
    public static AbstractApplicationContext getContextByXml() {
        return getContextByXml("cino-context.xml");
    }

    /**
     * 基于xml配置获得context 当未加载时进行加载 加载指定的内容<br />
     * <b>不建议在多个类中进行重复调用，若需要context 请使用Spring依赖注入</b><br />
     * 多个类中的加载控制不当时会引起递归错误，强烈建议使用Spring容器加载您的组件<br />
     * 请使用xml或annotation进行组件设计
     * 
     * @param resource
     *            附加内容的context xml配置文件
     * @return
     */
    public static AbstractApplicationContext getContextByXml(String... resource) {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(resource);
            context.registerShutdownHook();
        }
        return context;
    }

    /**
     * 基于xml配置获得context 当未加载时进行加载 除引擎组件外 追加指定的内容<br />
     * <b>不建议在多个类中进行重复调用，若需要context 请使用Spring依赖注入</b><br />
     * 多个类中的加载控制不当时会引起递归错误，强烈建议使用Spring容器加载您的组件<br />
     * 请使用xml或annotation进行组件设计
     * 
     * @param appResource
     *            除引擎基本组件外追加context文件
     * @return
     */
    public static AbstractApplicationContext getCustomContext(String appResource) {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("cino-context.xml", appResource);
            context.registerShutdownHook();
        }
        return context;
    }

    /**
     * 基于AnnotationConfig获得context 当未加载时进行加载 默认加载内容仅限于com.thrblock.cino包<br />
     * <b>不建议在多个类中进行重复调用，若需要context 请使用Spring依赖注入</b><br />
     * 多个类中的加载控制不当时会引起递归错误，强烈建议使用Spring容器加载您的组件<br />
     * 请使用xml或annotation进行组件设计
     */
    public static AbstractApplicationContext getContextByAnnotationConfig() {
        return getContextByAnnotationConfig(CinoConfig.class);
    }

    /**
     * 基于AnnotationConfig获得context 当未加载时进行加载 加载制定的内容<br />
     * <b>不建议在多个类中进行重复调用，若需要context 请使用Spring依赖注入</b><br />
     * 多个类中的加载控制不当时会引起递归错误，强烈建议使用Spring容器加载您的组件<br />
     * 请使用xml或annotation进行组件设计
     * 
     * @param configClass
     *            指定的annotationConfig
     * @return
     */
    public static AbstractApplicationContext getContextByAnnotationConfig(Class<?>... configClass) {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(configClass);
            context.registerShutdownHook();
        }
        return context;
    }

    /**
     * 基于AnnotationConfig获得context 当未加载时进行加载 加载默认内容外追加的上下文配置<br />
     * <b>不建议在多个类中进行重复调用，若需要context 请使用Spring依赖注入</b><br />
     * 多个类中的加载控制不当时会引起递归错误，强烈建议使用Spring容器加载您的组件<br />
     * 请使用xml或annotation进行组件设计
     * 
     * @param appResource 默认内容外追加的上下文配置
     * @return
     */
    public static AbstractApplicationContext getCustomContext(Class<?> appResource) {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(CinoConfig.class, appResource);
            context.registerShutdownHook();
        }
        return context;
    }
}
