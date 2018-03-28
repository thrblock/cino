package com.thrblock.cino.eventbus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.thrblock.cino.function.VoidConsumer;

@Component
public class EventBus {

    private ArrayList<MapEntry<?>> lst = new ArrayList<>();
    private List<Object> removeLst = new LinkedList<>();
    private Semaphore removeSp = new Semaphore(1);

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
        removeSp.acquireUninterruptibly();
        removeLst.add(holder);
        removeSp.release();
    }

    @PreDestroy
    public void clear() {
        removeSp.acquireUninterruptibly();
        lst.clear();
        removeLst.clear();
        removeSp.release();
    }

    @SuppressWarnings("unchecked")
    public void pushEvent(Object event) {
        if (!removeLst.isEmpty()) {
            removeSp.acquireUninterruptibly();
            lst.removeAll(removeLst);
            removeLst.clear();
            removeSp.release();
        }
        for (int i = 0; i < lst.size(); i++) {
            MapEntry<Object> ent = (MapEntry<Object>) lst.get(i);
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
