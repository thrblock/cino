package com.thrblock.cino.frame;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AWTFrameManager {
    @Value("${cino.frame.type:manual}")
    private String type;

    @Autowired
    private AWTFrameFactory awt;

    private JFrame jFrame;

    @PostConstruct
    void init() {
        if ("awt".equals(type)) {
            this.jFrame = awt.buildFrame();
        }
    }

    public JFrame getJFrame() {
        return jFrame;
    }
}
