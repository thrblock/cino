package com.thrblock.game.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.thrblock.game.demo.component.CharAreaDemo;
import com.thrblock.game.demo.component.MandelbrotExplorer;
import com.thrblock.game.demo.component.SmtPhy;
import com.thrblock.springcontext.CinoConfig;

@SpringBootApplication
@Configuration
@Import(CinoConfig.class)
public class SpringBootEntry implements CommandLineRunner {

    @Autowired
    MandelbrotExplorer mainCom;
    
    @Autowired
    SmtPhy phy;
    
    @Autowired
    CharAreaDemo area;
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        new SpringApplicationBuilder(SpringBootEntry.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        phy.activited();
    }
    
}
