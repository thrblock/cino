package com.thrblock.cino.util.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 简单链表<br />
 * 提供迭代器遍历<br />
 * 提供迭代器重置<br />
 * 默认迭代方式不支持并发遍历<br />
 * - 适合在帧逻辑中 省去频繁的迭代器实例化<-但实际效果有待于进一步测试,总之一秒实例化60个迭代器还是让人火大
 * @author lizepu
 *
 * @param <T>
 */
public class CrudeLinkedList<T> implements Iterable<T>{
    private Node head = null;
    private Node tail = null;
    private CrudeIter defaultIter = new CrudeIter();
    
    /**给链表增加一个元素
     * @param t
     */
    public void add(T t) {
        if(head == null) {
            head = new Node();
            head.object = t;
            tail = head;
        } else {
            Node n = new Node();
            n.object = t;
            tail.next = n;
            n.prev = tail;
            tail = n;
        }
    }
    
    /**
     * 将集合内的元素全部加入
     * @param c 集合
     */
    public void addAll(Collection<? extends T> c) {
        for(T t:c) {
            add(t);
        }
    }
    
    /**
     * 创建一个迭代器
     * @return 迭代器
     */
    public CrudeIter genCrudeIter() {
        return new CrudeIter();
    }
    class Node {
        T object;
        Node next;
        Node prev;
    }
    /**
     * 简单链表的迭代器
     * @author lizepu
     */
    public class CrudeIter implements Iterator<T>{
        private Node begin;
        private Node current = null;
        private CrudeIter() {
            begin = new Node();
            begin.next = head;
            current = begin;
        }
        @Override
        public boolean hasNext() {
            return current.next != null;
        }
        
        @Override
        public T next() {
            current = current.next;
            if(current.object == null) {
                throw new NoSuchElementException();
            }
            return current.object;
        }
        
        @Override
        public void remove() {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        
        /**
         * 重置迭代器状态，使之重新迭代
         */
        public void reset() {
            begin.next = head;
            current = begin;
        }
    }
    @Override
    public Iterator<T> iterator() {
        defaultIter.reset();
        return defaultIter;
    }
}
