package com.thrblock.cino;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * cino spring 上下文配置
 * 
 * @author lizepu
 */
@Configuration
public class CinoContextConfig {
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer cinoFramePropertie() throws IOException {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    @Primary
    public static ScriptEngine nashornEngine() {
        System.setProperty("nashorn.args", "--language=es6");
        return new ScriptEngineManager().getEngineByName("nashorn");
    }

    @Override
    public String toString() {
        return "CinoConfiguration";
    }
}
