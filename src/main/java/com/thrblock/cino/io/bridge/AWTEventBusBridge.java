package com.thrblock.cino.io.bridge;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.eventbus.EventBus;

@Component
public class AWTEventBusBridge {
    @Autowired
    EventBus eventBus;

    AWTEventListener listener;

    @PostConstruct
    void init() {
        this.listener = eventBus::pushEvent;
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
        tk.addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK);
        tk.addAWTEventListener(listener, AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    @PreDestroy
    void destroy() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
    }
}
