package com.thrblock.cino.util.charprocess;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CharRectAreaFactory {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CharRectArea charRectArea(float x, float y, float w, float h, CharAreaConfig conf) {
        return new CharRectArea(x, y, w, h, conf);
    }

    public CharRectArea charRectArea(float x, float y, float w, CharAreaConfig conf) {
        return charRectArea(x, y, w, conf.getFont().getFmheight(), conf);
    }

    public CharRectArea charRectArea(float w, CharAreaConfig conf) {
        return charRectArea(0, 0, w, conf);
    }
}
