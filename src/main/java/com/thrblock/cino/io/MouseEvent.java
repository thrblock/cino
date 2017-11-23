package com.thrblock.cino.io;

public class MouseEvent {
//    {
//        com.jogamp.newt.event.MouseEvent e;
//        com.jogamp.newt.event.MouseListener el;
//        java.awt.event.MouseEvent s;
//        java.awt.event.MouseListener sl;
//    }
    private Object src;
    private int x;
    private int y;
    public MouseEvent(java.awt.event.MouseEvent e) {
        this.src = e;
        this.x = e.getX();
        this.y = e.getY();
    }
    public MouseEvent(com.jogamp.newt.event.MouseEvent e) {
        this.src = e;
        this.x = e.getX();
        this.y = e.getY();
    }
    public Object getSrc() {
        return src;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
