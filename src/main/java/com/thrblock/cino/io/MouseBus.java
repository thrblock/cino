package com.thrblock.cino.io;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class MouseBus {
    private Function<MouseListener, Object> adder;
    private Consumer<Object> remover;

    public Function<MouseListener, Object> getAdder() {
        return adder;
    }

    public void setAdder(Function<MouseListener, Object> adder) {
        this.adder = adder;
    }

    public Consumer<Object> getRemover() {
        return remover;
    }

    public void setRemover(Consumer<Object> remover) {
        this.remover = remover;
    }
    
    public Object addMouseListener(MouseListener l) {
        return adder.apply(l);
    }
    
    public void removeMouseListener(Object removeHolder) {
        remover.accept(removeHolder);
    }
}
