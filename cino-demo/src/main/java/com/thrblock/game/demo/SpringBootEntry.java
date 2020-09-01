package com.thrblock.game.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.thrblock.game.demo.component.SmtPhy;

@SpringBootApplication
@Configuration
@ComponentScan(value = { "com.thrblock.game", "com.thrblock.aria" })
@EnableAutoConfiguration
public class SpringBootEntry implements CommandLineRunner {

    @Autowired
    SmtPhy demo;
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        new SpringApplicationBuilder(SpringBootEntry.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        demo.activited();
    }

}
