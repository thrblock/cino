package com.thrblock.cino.eventbus;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.util.structure.CrudeLinkedList;

@Component
public class EventBus {
    private class MapEntry<T> {
        Class<T> clz;
        Object obj;
        Consumer<T> con;
        public boolean check(Object obj) {
            if(this.clz != null) {
                return this.clz.equals(obj.getClass());
            }
            if(this.obj != null) {
                return this.obj == obj;
            }
            return false;
        }
    }

    private CrudeLinkedList<MapEntry<?>> lst = new CrudeLinkedList<>();
    private CrudeLinkedList<MapEntry<?>>.CrudeIter iter = lst.genCrudeIter();

    public <T> void mapEvent(Class<T> clz, Consumer<T> con) {
        MapEntry<T> ent = new MapEntry<>();
        ent.clz = clz;
        ent.con = con;
        lst.add(ent);
    }
    
    public void mapEvent(Object obj, VoidConsumer con) {
        MapEntry<Object> ent = new MapEntry<>();
        ent.obj = obj;
        ent.con = e -> con.accept();
        lst.add(ent);
    }

    @SuppressWarnings("unchecked")
    public void pushEvent(Object event) {
        while (iter.hasNext()) {
            MapEntry<Object> ent = (MapEntry<Object>)iter.next();
            if (ent.check(event)) {
                ent.con.accept(event);
            }
        }
        iter.reset();
    }
}
