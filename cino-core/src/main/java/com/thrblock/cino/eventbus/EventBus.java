package com.thrblock.cino.eventbus;

import java.util.function.Consumer;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.gllifecycle.GLCycle;

@Component
public class EventBus {

    private GLCycle<Object> entryCycle = new GLCycle<>(Object[]::new);

    public <T> Object mapEvent(Class<T> clz, Consumer<T> con) {
        MapEntry<T> ent = new MapEntry<>();
        ent.clz = clz;
        ent.con = con;
        entryCycle.safeAdd(ent);
        return ent;
    }

    public Object mapEvent(Object obj, VoidConsumer con) {
        MapEntry<Object> ent = new MapEntry<>();
        ent.obj = obj;
        ent.con = e -> con.accept();
        entryCycle.safeAdd(ent);
        return ent;
    }

    public void removeEvent(Object holder) {
        entryCycle.safeRemove(holder);
    }

    @PreDestroy
    public void clear() {
        entryCycle.safeHold(true);
    }

    @SuppressWarnings("unchecked")
    public void pushEvent(Object event) {
        Object[] lst = entryCycle.safeHold();
        for (int i = 0; i < lst.length; i++) {
            MapEntry<Object> ent = (MapEntry<Object>) lst[i];
            if (ent.check(event)) {
                ent.con.accept(event);
            }
        }
    }
    
    private class MapEntry<T> {
        Class<T> clz;
        Object obj;
        Consumer<T> con;

        public boolean check(Object obj) {
            if (this.clz != null) {
                return this.clz.isAssignableFrom(obj.getClass());
            }
            if (this.obj != null) {
                return this.obj.equals(obj);
            }
            return false;
        }
    }
}
