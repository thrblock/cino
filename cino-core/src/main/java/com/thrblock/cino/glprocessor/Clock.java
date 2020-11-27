package com.thrblock.cino.glprocessor;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class Clock {
    @Getter
    long count;

    void clock() {
        count++;
    }
}
