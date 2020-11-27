package com.largebug.test.shape;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class TestShapeDraw implements CommandLineRunner {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        new SpringApplicationBuilder(TestShapeDraw.class).web(WebApplicationType.NONE).headless(false).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
