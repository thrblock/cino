package com.thrblock.cino.io;

public class MouseEvent {
    public static final int BUTTON1 = 1;
    public static final int BUTTON2 = 2;
    public static final int BUTTON3 = 3;
    
    private Object src;
    private int x;
    private int y;
    private int button;
    public MouseEvent(java.awt.event.MouseEvent e) {
        this.src = e;
        this.x = e.getX();
        this.y = e.getY();
        this.button = e.getButton();
    }
    public MouseEvent(com.jogamp.newt.event.MouseEvent e) {
        this.src = e;
        this.x = e.getX();
        this.y = e.getY();
        this.button = e.getButton();
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
    public int getButton() {
        return button;
    }
}
