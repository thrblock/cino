package com.thrblock.scollider;

public interface SimpleCollidable<T extends SimpleBullet> {
    public void collideBy(T bullet);
    public boolean isCollide(T bullet);
    public boolean isAvailable();
}
