package com.thrblock.cino.frame;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FrameManager {
    @Value("${cino.frame.type:manual}")
    private String type;

    @Autowired
    private AWTFrameFactory awt;

    @Autowired
    private NEWTFrameFactory newt;

    @PostConstruct
    void init() {
        if ("awt".equals(type)) {
            awt.buildFrame();
        } else if ("newt".equals(type)) {
            newt.buildFrame();
        }
    }
}
