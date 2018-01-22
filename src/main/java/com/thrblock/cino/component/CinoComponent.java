package com.thrblock.cino.component;

import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.io.KeyEvent;
import com.thrblock.cino.io.KeyListener;

/**
 * 提供设计相关的主要成员,可以以此类为单位进行逻辑上的粒度控制
 * 此类用于构造逻辑上的单例结构，如特定的游戏场景或一个场景的子场景
 * @author zepu.li
 */
public abstract class CinoComponent extends CinoComponentContext implements KeyListener {

    protected boolean activited = false;
    
    @PostConstruct
    private final void postConstruct() throws Exception {
        activitedSupplier = () -> activited;
        activitedHolder = new LinkedList<>();
        deactivitedHolder = new LinkedList<>();
        eventHolder = new LinkedList<>();
        mouseHolder = new LinkedList<>();
        compAni = rootAni.generateSubContainer();
        compAni.pause();
        animateFactory.setContainer(compAni);
        sceneRoot = shapeFactory.createNode();
        init();
    }

    private void removeMouseHolder() {
        mouseHolder.forEach(mouseIO::removeMouseHolder);
        mouseHolder.clear();
    }
    
    private void removeEventHolder() {
        eventHolder.forEach(eventBus::removeEvent);
        eventHolder.clear();
    }

    @PreDestroy
    private final void preDestroy() throws Exception {
        storage.save(this);
        destory();
    }

    /**
     * 初始化时调用一次
     */
    public void init() throws Exception {
    }

    /**
     * 销毁时调用一次
     */
    public void destory() throws Exception {
        removeMouseHolder();
        removeEventHolder();
        compAni.destroy();
        sceneRoot.destroy();
        keyIO.removeKeyListener(this);
        activitedHolder.clear();
        deactivitedHolder.clear();
    }

    /**
     * 激活组件
     */
    public final void activited() {
        activited = true;
        compAni.remuse();
        activitedHolder.forEach(VoidConsumer::accept);
    }

    /**
     * 停止组件
     */
    public final void deactivited() {
        compAni.pause();
        deactivitedHolder.forEach(VoidConsumer::accept);
        activited = false;
    }

    /**
     * auto show/hide scene root when component activited/deactivited
     */
    protected final void autoShowHide() {
        onActivited(sceneRoot::show);
        onDeactivited(sceneRoot::hide);
    }

    protected final void autoKeyPushPop() {
        onActivited(() -> keyIO.pushKeyListener(this));
        onDeactivited(keyIO::popKeyListener);
    }

    protected final <T extends CinoInstance> T injectInstance(T obj) {
        obj.sameContextOf(this);
        obj.init();
        return obj;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
