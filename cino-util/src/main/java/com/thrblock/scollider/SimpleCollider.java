package com.thrblock.scollider;

import java.util.LinkedList;
import java.util.List;

public class SimpleCollider<C extends SimpleCollidable<B>, B extends SimpleBullet> {
    private List<C> collidableLst = new LinkedList<>();
    private List<B> bltLst = new LinkedList<>();

    public void addCollidable(C collidable) {
        collidableLst.add(collidable);
    }

    public void addBullet(B bullet) {
        bltLst.add(bullet);
    }

    public void fragment() {
        for (B b : bltLst) {
            if (b.isAvailable()) {
                checkCollide(b);
            }
        }
    }

    private void checkCollide(B b) {
        for (C c : collidableLst) {
            if (c.isAvailable() && c.isCollide(b)) {
                c.collideBy(b);
                b.hit();
            }
        }
    }
}
