package com.thrblock.cino.eventbus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.thrblock.cino.function.VoidConsumer;

@Component
public class EventBus {
    private class MapEntry<T> {
        Class<T> clz;
        Object obj;
        Consumer<T> con;
        public boolean check(Object obj) {
            if(this.clz != null) {
                return this.clz.isAssignableFrom(obj.getClass());
            }
            if(this.obj != null) {
                return this.obj.equals(obj);
            }
            return false;
        }
    }

    private LinkedList<MapEntry<?>> lst = new LinkedList<>();

    public <T> Object mapEvent(Class<T> clz, Consumer<T> con) {
        MapEntry<T> ent = new MapEntry<>();
        ent.clz = clz;
        ent.con = con;
        lst.add(ent);
        return ent;
    }
    
    public Object mapEvent(Object obj, VoidConsumer con) {
        MapEntry<Object> ent = new MapEntry<>();
        ent.obj = obj;
        ent.con = e -> con.accept();
        lst.add(ent);
        return ent;
    }
    
    public void removeEvent(Object holder) {
        lst.remove(holder);
    }

    @SuppressWarnings("unchecked")
    public void pushEvent(Object event) {
        Iterator<MapEntry<?>> iter = lst.iterator();
        while (iter.hasNext()) {
            MapEntry<Object> ent = (MapEntry<Object>)iter.next();
            if (ent.check(event)) {
                ent.con.accept(event);
            }
        }
    }
}
