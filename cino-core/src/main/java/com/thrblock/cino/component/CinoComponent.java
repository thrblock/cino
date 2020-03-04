package com.thrblock.cino.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.script.Bindings;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import com.thrblock.cino.function.VoidConsumer;

/**
 * 提供设计相关的主要成员,可以以此类为单位进行逻辑上的粒度控制 此类用于构造逻辑上的单例结构，如特定的游戏场景或一个场景的子场景
 * 
 * @author zepu.li
 */
public abstract class CinoComponent extends CinoComponentContext implements KeyListener {

    private boolean activited = false;
    private List<CinoComponent> subComps = new CopyOnWriteArrayList<>();
    private Resource scriptResouce;
    private String scriptBackup;

    @PostConstruct
    private final void postConstruct() throws Exception {
        activitedSupplier = () -> activited;
        activitedHolder = new LinkedList<>();
        deactivitedHolder = new LinkedList<>();
        destroyHolder = new LinkedList<>();
        eventHolder = new LinkedList<>();
        mouseHolder = new LinkedList<>();
        compAni = rootAni.generateSubContainer();
        compAni.pause();
        animateFactory.setContainer(compAni);
        sceneRoot = shapeFactory.createNode();
        Optional.ofNullable(scriptResouce).ifPresent(this::applyWithECMA);
        init();
    }

    protected final void applyWithECMA(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            Bindings param = scriptEngine.createBindings();
            putField(param);
            String sc = IOUtils.toString(is, StandardCharsets.UTF_8);
            scriptEngine.eval(sc, param);
            this.scriptBackup = sc;
        } catch (Exception e) {
            logger.error("exception in eval script {}:{}", e.getClass().getSimpleName(), e.getMessage());
            tryBackup();
        }
    }

    private void putField(Bindings param) throws IllegalAccessException {
        param.put("$", new ECMAWrapper(this));
        for (Field f : CinoComponentContext.class.getDeclaredFields()) {
            f.setAccessible(true);
            param.put(f.getName(), f.get(this));
        }
        for (Field f : CinoComponent.class.getDeclaredFields()) {
            f.setAccessible(true);
            param.put(f.getName(), f.get(this));
        }
        for (Field f : getClass().getDeclaredFields()) {
            f.setAccessible(true);
            param.put(f.getName(), f.get(this));
        }
    }

    private void tryBackup() {
        if (scriptBackup != null) {
            try {
                Bindings param = scriptEngine.createBindings();
                putField(param);
                scriptEngine.eval(scriptBackup, param);
            } catch (Exception e) {
                logger.error("Exception in eval backup script:{}", e.getMessage());
            }
        }
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
        destroy();
    }

    /**
     * 初始化时调用一次
     */
    public void init() throws Exception {
    }

    /**
     * for debug only.
     * @throws Exception
     */
    public final void reload() throws Exception {
        deactivited();
        preDestroy();
        postConstruct();
        activited();
    }

    /**
     * 销毁时调用一次
     */
    public void destroy() throws Exception {
        destroyHolder.forEach(VoidConsumer::accept);
        removeMouseHolder();
        removeEventHolder();
        compAni.destroy();
        sceneRoot.destroy();
        shapeFactory.clearNode();
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
        subComps.forEach(CinoComponent::activited);
    }

    /**
     * 停止组件
     */
    public final void deactivited() {
        compAni.pause();
        subComps.forEach(CinoComponent::deactivited);
        deactivitedHolder.forEach(VoidConsumer::accept);
        activited = false;
    }

    public final void registerSub(CinoComponent sub) {
        this.subComps.add(sub);
    }

    public final void setScriptResouce(Resource r) {
        this.scriptResouce = r;
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
        autowireCapable.autowireBean(obj);
        obj.sameContextOf(this);
        obj.init();
        return obj;
    }

    protected final <T extends CinoInstance> T injectInstance(Supplier<T> sup) {
        return injectInstance(sup.get());
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
