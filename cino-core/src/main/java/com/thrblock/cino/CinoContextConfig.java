package com.thrblock.cino;

import java.io.IOException;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.thrblock.cino.util.SmartConfig;

/**
 * cino spring 上下文配置
 * 
 * @author lizepu
 */
@Configuration
public class CinoContextConfig {
    /**
     * 将cino-frame配置纳入spring容器管理 可使用注解‘Value’进行引用
     * 
     * @return PropertyPlaceholderConfigurer spring管理的properties
     * @throws IOException 当配置文件缺失时
     */
    @Bean
    public static PropertyPlaceholderConfigurer cinoFramePropertie() throws IOException {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        SmartConfig frameConfig = new SmartConfig("frame.smt");
        Properties prop = new Properties();
        prop.load(frameConfig.getConfigAsInputStream());
        ppc.setProperties(prop);
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

    @Bean
    @Primary
    public static ScriptEngine nashornEngine() {
        return new ScriptEngineManager().getEngineByName("nashorn");
    }

    @Override
    public String toString() {
        return "CinoConfiguration";
    }
}
