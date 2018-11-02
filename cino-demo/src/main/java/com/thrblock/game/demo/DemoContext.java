package com.thrblock.game.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.thrblock.game","com.thrblock.aria"})
public class DemoContext {
}
