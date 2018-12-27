package com.thrblock.game.demo.component.pojoinject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.thrblock.cino.io.KeyControlStack;

@Configurable
public class DemoPojo {
    @Autowired
    KeyControlStack keyIO;

    public void test() {
        System.out.println(keyIO);
    }
}
