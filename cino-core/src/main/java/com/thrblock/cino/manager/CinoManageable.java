package com.thrblock.cino.manager;

public interface CinoManageable {
    default void onCreate() {
    }

    default void onDestroy() {
    }

    default void onScreenChange(int w, int h) {
    }
}
