package com.thrblock.cino.component;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.thrblock.cino.CinoFrameConfig;
import com.thrblock.cino.eventbus.EventBus;
import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glanimate.GLAnimate;
import com.thrblock.cino.glanimate.GLAnimateFactory;
import com.thrblock.cino.glanimate.GLAnimateManager;
import com.thrblock.cino.glanimate.IPureFragment;
import com.thrblock.cino.gllayer.GLLayer;
import com.thrblock.cino.gllayer.GLLayerManager;
import com.thrblock.cino.gllayer.IGLFrameBufferObjectManager;
import com.thrblock.cino.glshape.GLPolygonShape;
import com.thrblock.cino.glshape.factory.GLNode;
import com.thrblock.cino.glshape.factory.GLShapeFactory;
import com.thrblock.cino.io.KeyControlStack;
import com.thrblock.cino.io.MouseControl;
import com.thrblock.cino.io.MouseEvent;
import com.thrblock.cino.storage.Storage;

/**
 * 组件上下文 提供一套标准cino api
 * 
 * @author zepu.li
 *
 */
abstract class CinoComponentContext {
    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${cino.frame.screen.width:800}")
    protected int screenW;

    @Value("${cino.frame.screen.height:600}")
    protected int screenH;
    /**
     * local storage
     */
    @Autowired
    protected Storage storage;
    /**
     * frame中包括了当前绘制框架的大部分信息（宽高、垂直同步是否开启，是否全屏等）
     */
    @Autowired
    protected CinoFrameConfig frame;

    /**
     * 图形构造器，用来绘制图形
     */
    @Autowired
    protected GLShapeFactory shapeFactory;

    /**
     * 键盘IO控制器，可以挂载监听器捕获键盘事件或是读取某一指定按键的状态
     */
    @Autowired
    protected KeyControlStack keyIO;

    /**
     * 鼠标IO控制器，可以设置鼠标监听器捕获鼠标事件 或读取某一鼠标按键的状态
     */
    @Autowired
    protected MouseControl mouseIO;
    /**
     * 根片段容器
     */
    @Autowired
    protected GLAnimateManager rootAni;

    /**
     * 组件片段容器
     */
    protected GLAnimateManager compAni;
    /**
     * 片段构造器
     */
    @Autowired
    protected GLAnimateFactory animateFactory;

    /**
     * 帧缓冲管理器
     */
    @Autowired
    protected IGLFrameBufferObjectManager fboManager;

    @Autowired
    protected GLLayerManager layerManager;

    @Autowired
    protected EventBus eventBus;

    /**
     * sceneRoot是场景自动创建的GLNode根节点
     */
    protected GLNode sceneRoot;

    protected BooleanSupplier activitedSupplier = () -> false;

    protected List<VoidConsumer> activitedHolder;
    protected List<VoidConsumer> deactivitedHolder;
    protected List<Object> mouseHolder;
    protected List<Object> eventHolder;

    /**
     * 伴随组件自动的帧片段逻辑
     * 
     * @param pure
     */
    protected final void auto(IPureFragment pure) {
        GLAnimate ani = animateFactory.build();
        ani.add(pure);
        ani.enable();
    }

    protected final <T> void auto(Supplier<T> sup, Consumer<T> cons) {
        GLAnimate ani = animateFactory.build();
        ani.add(() -> cons.accept(sup.get()));
        ani.enable();
    }

    protected final void autoEvery(int count, IPureFragment pure) {
        GLAnimate ani = animateFactory.build();
        ani.add(pure.mergeDelay(count));
        ani.enable();
    }

    protected final <T> void autoEvery(int count, Supplier<T> sup, Consumer<T> cons) {
        GLAnimate ani = animateFactory.build();
        IPureFragment pure = () -> cons.accept(sup.get());
        ani.add(pure.mergeDelay(count));
        ani.enable();
    }

    /**
     * 伴随组件自动的帧片段逻辑
     * 
     * @param pure
     */
    protected final void auto(BooleanSupplier condition, IPureFragment pure) {
        GLAnimate ani = animateFactory.build();
        ani.add(pure.mergeCondition(condition));
        ani.enable();
    }

    protected final <T> void auto(BooleanSupplier condition, Supplier<T> sup, Consumer<T> cons) {
        GLAnimate ani = animateFactory.build();
        IPureFragment pure = () -> cons.accept(sup.get());
        ani.add(pure.mergeCondition(condition));
        ani.enable();
    }

    protected final void autoEvery(int count, BooleanSupplier condition, IPureFragment pure) {
        GLAnimate ani = animateFactory.build();
        ani.add(pure.mergeDelay(count).mergeCondition(condition));
        ani.enable();
    }

    protected final <T> void autoEvery(int count, BooleanSupplier condition, Supplier<T> sup, Consumer<T> cons) {
        GLAnimate ani = animateFactory.build();
        IPureFragment pure = () -> cons.accept(sup.get());
        ani.add(pure.mergeDelay(count).mergeCondition(condition));
        ani.enable();
    }

    protected final void autoMouseClicked(Consumer<MouseEvent> e) {
        mouseHolder.add(mouseIO.addMouseClicked(e));
    }

    protected final void autoMousePressed(Consumer<MouseEvent> e) {
        mouseHolder.add(mouseIO.addMousePressed(e));
    }

    protected final void autoMouseReleased(Consumer<MouseEvent> e) {
        mouseHolder.add(mouseIO.addMouseReleased(e));
    }

    protected final void autoShapeClicked(GLPolygonShape shape, Consumer<MouseEvent> e) {
        GLLayer layer = layerManager.getLayer(shape.getLayerIndex());
        Function<Consumer<MouseEvent>, Object> funs = mouseIO::addMouseClicked;
        autoMouseHook(shape, e, layer, funs);
    }

    protected final void autoShapePressed(GLPolygonShape shape, Consumer<MouseEvent> e) {
        GLLayer layer = layerManager.getLayer(shape.getLayerIndex());
        Function<Consumer<MouseEvent>, Object> funs = mouseIO::addMousePressed;
        autoMouseHook(shape, e, layer, funs);
    }

    protected final void autoShapeReleased(GLPolygonShape shape, Consumer<MouseEvent> e) {
        GLLayer layer = layerManager.getLayer(shape.getLayerIndex());
        Function<Consumer<MouseEvent>, Object> funs = mouseIO::addMouseReleased;
        autoMouseHook(shape, e, layer, funs);
    }

    private void autoMouseHook(GLPolygonShape shape, Consumer<MouseEvent> e, GLLayer layer,
            Function<Consumer<MouseEvent>, Object> funs) {
        onActivited(() -> mouseHolder.add(funs.apply(event -> {
            if (activitedSupplier.getAsBoolean() && shape.isVisible() && !shape.isDestory() && shape.isPointInside(
                    mouseIO.getMouseX() - layer.getViewXOffset(), mouseIO.getMouseY() - layer.getViewYOffset())) {
                e.accept(event);
            }
        })));
    }

    protected final void onActivited(VoidConsumer v) {
        activitedHolder.add(v);
    }

    protected final void onDeactivited(VoidConsumer v) {
        deactivitedHolder.add(v);
    }

    protected Object shapeClicked(GLPolygonShape shape, Consumer<MouseEvent> e) {
        GLLayer layer = layerManager.getLayer(shape.getLayerIndex());
        return mouseHook(shape, e, layer, mouseIO::addMouseClicked);
    }

    protected Object shapePressed(GLPolygonShape shape, Consumer<MouseEvent> e) {
        GLLayer layer = layerManager.getLayer(shape.getLayerIndex());
        return mouseHook(shape, e, layer, mouseIO::addMousePressed);
    }

    protected Object shapeReleased(GLPolygonShape shape, Consumer<MouseEvent> e) {
        GLLayer layer = layerManager.getLayer(shape.getLayerIndex());
        return mouseHook(shape, e, layer, mouseIO::addMouseReleased);
    }

    private Object mouseHook(GLPolygonShape shape, Consumer<MouseEvent> e, GLLayer layer,
            Function<Consumer<MouseEvent>, Object> funs) {
        return funs.apply(event -> {
            if (activitedSupplier.getAsBoolean() && shape.isVisible() && !shape.isDestory() && shape.isPointInside(
                    mouseIO.getMouseX() - layer.getViewXOffset(), mouseIO.getMouseY() - layer.getViewYOffset())) {
                e.accept(event);
            }
        });
    }

    protected final <T> void autoMapEvent(Class<T> clazz, Consumer<T> cons) {
        eventHolder.add(eventBus.mapEvent(clazz, e -> {
            if (activitedSupplier.getAsBoolean()) {
                cons.accept(e);
            }
        }));
    }

    protected final void autoMapEvent(Object o, VoidConsumer cons) {
        eventHolder.add(eventBus.mapEvent(o, () -> {
            if (activitedSupplier.getAsBoolean()) {
                cons.accept();
            }
        }));
    }

    protected final boolean isMouseInside(GLPolygonShape shape) {
        GLLayer layer = layerManager.getLayer(shape.getLayerIndex());
        return shape.isVisible() && !shape.isDestory() && shape.isPointInside(
                mouseIO.getMouseX() - layer.getViewXOffset(), mouseIO.getMouseY() - layer.getViewYOffset());
    }
}
