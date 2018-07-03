package com.thrblock.cino.glprocessor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class GLScreenSizeChangeListenerHolder {
    List<BiConsumer<Integer, Integer>> screenChangeListener;

    @PostConstruct
    void init() {
        screenChangeListener = new CopyOnWriteArrayList<>();
    }
    
    @PreDestroy
    void destroy() {
        screenChangeListener.clear();
    }
    
    public List<BiConsumer<Integer, Integer>> getScreenChangeListener() {
        return screenChangeListener;
    }
    
    /**
     * 注册窗体大小变化监听器
     * 
     * @param listener
     */
    public void addScreenSizeChangeListener(BiConsumer<Integer, Integer> listener) {
        screenChangeListener.add(listener);
    }
}
