package com.thrblock.cino.component;

import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glanimate.IPureFragment;
import com.thrblock.cino.glshape.GLPolygonShape;

/**
 * to be replaced by BCE
 * 
 * @author lizepu
 *
 */
public class ECMAWrapper {
    private CinoComponent c;

    ECMAWrapper(CinoComponent c) {
        this.c = c;
    }

    public void auto(IPureFragment pure) {
        c.auto(pure);
    }

    public <T> void auto(Supplier<T> sup, Consumer<T> cons) {
        c.auto(sup, cons);
    }

    public void autoEvery(int count, IPureFragment pure) {
        c.autoEvery(count, pure);
    }

    public <T> void autoEvery(int count, Supplier<T> sup, Consumer<T> cons) {
        c.autoEvery(count, sup, cons);
    }

    /**
     * 伴随组件自动的帧片段逻辑
     * 
     * @param pure
     */
    public void auto(BooleanSupplier condition, IPureFragment pure) {
        c.auto(condition, pure);
    }

    public <T> void auto(BooleanSupplier condition, Supplier<T> sup, Consumer<T> cons) {
        c.auto(condition, sup, cons);
    }

    public void autoEvery(int count, BooleanSupplier condition, IPureFragment pure) {
        c.autoEvery(count, condition, pure);
    }

    public <T> void autoEvery(int count, BooleanSupplier condition, Supplier<T> sup, Consumer<T> cons) {
        c.autoEvery(count, condition, sup, cons);
    }

    public void autoMouseClicked(Consumer<MouseEvent> e) {
        c.autoMouseClicked(e);
    }

    public void autoMousePressed(Consumer<MouseEvent> e) {
        c.autoMousePressed(e);
    }

    public void autoMouseReleased(Consumer<MouseEvent> e) {
        c.autoMouseReleased(e);
    }

    public void autoShapeClicked(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        c.autoShapeClicked(shape, e);
    }

    public void autoShapePressed(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        c.autoShapePressed(shape, e);
    }

    public void autoShapeReleased(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        c.autoShapeReleased(shape, e);
    }

    public void onActivited(VoidConsumer v) {
        c.onActivited(v);
    }

    public void onDeactivited(VoidConsumer v) {
        c.onDeactivited(v);
    }
    
    public void onDestroy(VoidConsumer v) {
        c.onDestroy(v);
    }

    public AWTEventListener shapeClicked(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        return c.shapeClicked(shape, e);
    }

    public AWTEventListener shapePressed(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        return c.shapePressed(shape, e);
    }

    public AWTEventListener shapeReleased(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        return c.shapeReleased(shape, e);
    }

    public <T> void autoMapEvent(Class<T> clazz, Consumer<T> cons) {
        c.autoMapEvent(clazz, cons);
    }

    public void autoMapEvent(Object o, VoidConsumer cons) {
        c.autoMapEvent(o, cons);
    }

    public boolean isMouseInside(GLPolygonShape<?> shape) {
        return c.isMouseInside(shape);
    }
    
    public void autoShowHide() {
        c.autoShowHide();
    }

    public void autoKeyPushPop() {
        c.autoKeyPushPop();
    }

    public <T extends CinoInstance> T injectInstance(T obj) {
        return c.injectInstance(obj);
    }
    
    public <T extends CinoInstance> T injectInstance(Supplier<T> sup) {
        return c.injectInstance(sup);
    }
}
