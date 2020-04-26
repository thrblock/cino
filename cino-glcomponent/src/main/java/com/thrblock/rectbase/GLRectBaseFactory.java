package com.thrblock.rectbase;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GLRectBaseFactory {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public <T extends GLRectBase> T warp(T base) {
        return base;
    }

}
