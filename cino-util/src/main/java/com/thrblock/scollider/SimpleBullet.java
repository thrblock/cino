package com.thrblock.scollider;

public interface SimpleBullet {
    public boolean isAvailable();
    public default void hit() {}
}
