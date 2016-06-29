package com.thrblock.cino;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.thrblock.cino.util.io.SmartConfig;

/**
 * cino spring 上下文配置
 * @author lizepu
 */
@Component
public class CinoContextConfig {
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
}
