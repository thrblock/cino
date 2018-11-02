package com.thrblock.game.demo;

import com.thrblock.cino.util.structure.CrudeLinkedList;

public class CrudeSDemo {
    public static void main(String[] args) {
        removeFirst();
        System.out.println("===");
        removeLast();
        System.out.println("===");
        removeMiddle();
    }

    private static void removeMiddle() {
        CrudeLinkedList<String> lst = new CrudeLinkedList<>();
        lst.add("a");
        lst.add("b");
        lst.add("c");
        lst.add("d");
        CrudeLinkedList<String>.CrudeIter iter = lst.genCrudeIter();
        while (iter.hasNext()) {
            String s = iter.next();
            if ("b".equals(s)) {
                iter.remove();
            }
            System.out.println(s);
        }
        iter.reset();
        lst.add("f");
        System.out.println("-------");
        while (iter.hasNext()) {
            String s = iter.next();
            System.out.println(s);
        }
    }
    
    private static void removeFirst() {
        CrudeLinkedList<String> lst = new CrudeLinkedList<>();
        lst.add("a");
        lst.add("b");
        lst.add("c");
        lst.add("d");
        CrudeLinkedList<String>.CrudeIter iter = lst.genCrudeIter();
        while (iter.hasNext()) {
            String s = iter.next();
            if ("a".equals(s)) {
                iter.remove();
            }
            System.out.println(s);
        }
        iter.reset();
        lst.add("f");
        System.out.println("-------");
        while (iter.hasNext()) {
            String s = iter.next();
            System.out.println(s);
        }
    }
    
    private static void removeLast() {
        CrudeLinkedList<String> lst = new CrudeLinkedList<>();
        lst.add("a");
        lst.add("b");
        lst.add("c");
        lst.add("d");
        CrudeLinkedList<String>.CrudeIter iter = lst.genCrudeIter();
        while (iter.hasNext()) {
            String s = iter.next();
            if ("d".equals(s)) {
                iter.remove();
            }
            System.out.println(s);
        }
        iter.reset();
        lst.add("f");
        System.out.println("-------");
        while (iter.hasNext()) {
            String s = iter.next();
            System.out.println(s);
        }
    }
}
