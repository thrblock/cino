package com.thrblock.springcontext;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;

import com.thrblock.cino.gltexture.FontsInCommon;

@Configuration
@ComponentScan(value = "com.thrblock.cino", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = FontsInCommon.class) })
public class CinoConfig {
    @Autowired
    FontsInCommon common;

    CinoConfig() {
    }

    @Bean
    FontsInCommon getFontsInCommon() {
        return new FontsInCommon();
    }

    @Bean
    @Primary
    public static ScriptEngine nashornEngine() {
        System.setProperty("nashorn.args", "--language=es6");
        return new ScriptEngineManager().getEngineByName("nashorn");
    }
}
