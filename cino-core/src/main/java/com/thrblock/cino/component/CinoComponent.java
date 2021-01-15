package com.thrblock.cino.component;

import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.script.ScriptEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.thrblock.cino.CinoFrameConfig;
import com.thrblock.cino.eventbus.EventBus;
import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glanimate.GLFragmentManager;
import com.thrblock.cino.glanimate.fragment.IPureFragment;
import com.thrblock.cino.glprocessor.Clock;
import com.thrblock.cino.glshape.GLPolygonShape;
import com.thrblock.cino.io.KeyControlStack;
import com.thrblock.cino.io.MouseControl;
import com.thrblock.cino.lnode.LNode;
import com.thrblock.cino.lnode.LNodeManager;
import com.thrblock.cino.storage.Storage;
import com.thrblock.cino.util.charprocess.CharRectAreaFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 提供设计相关的主要成员,可以以此类为单位进行逻辑上的粒度控制<br />
 * 定义了init -> activited -> deactivited -> destroy -> reload 生命周期 <br />
 * 
 * @author zepu.li
 */
@Slf4j
public abstract class CinoComponent implements KeyListener {

    /**
     * 加载树
     */
    private static Deque<CinoComponent> initingTree = new ConcurrentLinkedDeque<>();
    private static Set<String> initingTreeSet = new HashSet<>();
    /**
     * 日志
     */
    public final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${cino.frame.screen.width:800}")
    protected int screenW;

    @Value("${cino.frame.screen.height:600}")
    protected int screenH;
    
    /**
     * 时钟
     */
    @Autowired
    protected Clock clock;
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
     * 基于矩形结构的文字区域工厂类
     */
    @Autowired
    protected CharRectAreaFactory charRectFactory;

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
    protected GLFragmentManager rootAni;

    /**
     * 组件片段容器
     */
    protected GLFragmentManager compAni;

    @Autowired
    protected EventBus eventBus;

    @Autowired
    protected ScriptEngine scriptEngine;

    private LNode rootNode;

    @Autowired
    protected LNodeManager nodeManager;

    protected BooleanSupplier activitedSupplier = () -> false;

    protected CinoComponent parent;

    private List<VoidConsumer> activitedHolder;
    private List<VoidConsumer> deactivitedHolder;
    private List<VoidConsumer> destroyHolder;
    private List<AWTEventListener> mouseHolder;
    private List<Object> eventHolder;

    private boolean activited = false;
    private List<CinoComponent> subComps = new CopyOnWriteArrayList<>();

    private static String printInitTree() {
        StringBuilder builder = new StringBuilder();
        builder.append("<root>");
        initingTree.descendingIterator().forEachRemaining(e -> builder.append("->" + e.getClass().getSimpleName()));
        return builder.toString();
    }

    @PostConstruct
    private final void postConstruct() throws Exception {
        parent = initingTree.peek();
        initingTree.push(this);

        String initTree = printInitTree();
        if (initingTreeSet.add(initTree)) {
            logger.info("init-tree:{}", initTree);
        }

        activitedSupplier = () -> activited;
        activitedHolder = new LinkedList<>();
        deactivitedHolder = new LinkedList<>();
        destroyHolder = new LinkedList<>();
        eventHolder = new LinkedList<>();
        mouseHolder = new LinkedList<>();

        compAni = Optional.ofNullable(parent)
                .map(p -> p.rootAni.generateSubContainer())
                .orElse(rootAni.generateSubContainer());
        compAni.pause();
        
        Optional.ofNullable(parent).ifPresent(p -> {
            rootNode = Optional.ofNullable(rootNode).orElse(p.rootNode);
        });
        
        Optional.ofNullable(parent).ifPresent(p -> p.sameLifeCycleOf(this));

        init();
        rootNode.syncMbr();
        initingTree.pop();
    }

    public final LNode rootNode() {
        if (this.rootNode == null) {
            this.rootNode = nodeManager.createRootNode();
        }
        return rootNode;
    }

    public final void setRootNode(LNode node) {
        this.rootNode = node;
    }

    /**
     * auto show/hide scene root when component activited/deactivited
     */
    public final void autoShowHide() {
        onActivited(() -> rootNode().show());
        onDeactivited(() -> rootNode().hide());
    }

    public final void autoKeyPushPop() {
        onActivited(() -> keyIO.pushKeyListener(this));
        onDeactivited(keyIO::popKeyListener);
    }

    /**
     * 伴随组件自动的帧片段逻辑
     * 
     * @param pure
     */
    public final void auto(IPureFragment pure) {
        compAni.addFragment(pure);
    }

    public final <T> void auto(Supplier<T> sup, Consumer<T> cons) {
        compAni.addFragment(() -> cons.accept(sup.get()));
    }

    public final void autoEvery(int count, IPureFragment pure) {
        compAni.addFragment(pure.delay(count));
    }

    public final <T> void autoEvery(int count, Supplier<T> sup, Consumer<T> cons) {
        IPureFragment pure = () -> cons.accept(sup.get());
        compAni.addFragment(pure.delay(count));
    }

    /**
     * 伴随组件自动的帧片段逻辑
     * 
     * @param pure
     */
    public final void auto(BooleanSupplier condition, IPureFragment pure) {
        compAni.addFragment(pure.withCondition(condition));
    }

    public final <T> void auto(BooleanSupplier condition, Supplier<T> sup, Consumer<T> cons) {
        IPureFragment pure = () -> cons.accept(sup.get());
        compAni.addFragment(pure.withCondition(condition));
    }

    public final void autoEvery(int count, BooleanSupplier condition, IPureFragment pure) {
        compAni.addFragment(pure.delay(count).withCondition(condition));
    }

    public final <T> void autoEvery(int count, BooleanSupplier condition, Supplier<T> sup, Consumer<T> cons) {
        IPureFragment pure = () -> cons.accept(sup.get());
        compAni.addFragment(pure.delay(count).withCondition(condition));
    }

    public final void autoMouseClicked(Consumer<MouseEvent> e) {
        mouseHolder.add(mouseIO.addMouseClicked(e));
    }

    public final void autoMousePressed(Consumer<MouseEvent> e) {
        mouseHolder.add(mouseIO.addMousePressed(e));
    }

    public final void autoMouseReleased(Consumer<MouseEvent> e) {
        mouseHolder.add(mouseIO.addMouseReleased(e));
    }

    public final void autoShapeClicked(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        autoMouseHook(shape, e, mouseIO::addMouseClicked);
    }

    public final void autoShapePressed(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        autoMouseHook(shape, e, mouseIO::addMousePressed);
    }

    public final void autoShapeReleased(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        autoMouseHook(shape, e, mouseIO::addMouseReleased);
    }

    public void autoMouseHook(GLPolygonShape<?> shape, Consumer<MouseEvent> e,
            Function<Consumer<MouseEvent>, AWTEventListener> funs) {
        onActivited(() -> mouseHolder.add(funs.apply(event -> {
            if (activitedSupplier.getAsBoolean() && shape.isMouseInside()) {
                e.accept(event);
            }
        })));
    }

    public final void sameLifeCycleOf(CinoComponent... comps) {
        Arrays.stream(comps).forEach(c -> {
            onActivited(c::activited);
            onDeactivited(c::deactivited);
        });
    }

    public final void onActivited(VoidConsumer v) {
        activitedHolder.add(v);
    }

    public final void onDeactivited(VoidConsumer v) {
        deactivitedHolder.add(v);
    }

    public final void onDestroy(VoidConsumer v) {
        destroyHolder.add(v);
    }

    protected AWTEventListener shapeClicked(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        return mouseHook(shape, e, mouseIO::addMouseClicked);
    }

    protected AWTEventListener shapePressed(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        return mouseHook(shape, e, mouseIO::addMousePressed);
    }

    protected AWTEventListener shapeReleased(GLPolygonShape<?> shape, Consumer<MouseEvent> e) {
        return mouseHook(shape, e, mouseIO::addMouseReleased);
    }

    private AWTEventListener mouseHook(GLPolygonShape<?> shape, Consumer<MouseEvent> e,
            Function<Consumer<MouseEvent>, AWTEventListener> funs) {
        return funs.apply(event -> {
            if (activitedSupplier.getAsBoolean() && shape.isMouseInside()) {
                e.accept(event);
            }
        });
    }

    public final <T> void autoMapEvent(Class<T> clazz, Consumer<T> cons) {
        eventHolder.add(eventBus.mapEvent(clazz, e -> {
            if (activitedSupplier.getAsBoolean()) {
                cons.accept(e);
            }
        }));
    }

    public final void autoMapEvent(Object o, VoidConsumer cons) {
        eventHolder.add(eventBus.mapEvent(o, () -> {
            if (activitedSupplier.getAsBoolean()) {
                cons.accept();
            }
        }));
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
    public final void preDestroy() throws Exception {
        storage.save(this);
        Optional.ofNullable(parent).ifPresent(p -> p.subComps.remove(this));
        destroyHolder.forEach(VoidConsumer::accept);
        removeMouseHolder();
        removeEventHolder();
        compAni.destroy();
        keyIO.removeKeyListener(this);

        activitedHolder.clear();
        deactivitedHolder.clear();
        destroyHolder.clear();

        destroy();
    }

    /**
     * 初始化时调用一次
     */
    public void init() throws Exception {
    }

    /**
     * for debug only.
     * 
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

    }

    /**
     * 激活组件
     */
    public final void activited() {
        log.debug("{} activited.", getClass().getSimpleName());

        activited = true;
        compAni.remuse();
        activitedHolder.forEach(VoidConsumer::accept);

        subComps.forEach(CinoComponent::activited);
    }

    /**
     * 停止组件
     */
    public final void deactivited() {
        log.info("{} deactivited.", getClass().getSimpleName());

        subComps.forEach(CinoComponent::deactivited);

        deactivitedHolder.forEach(VoidConsumer::accept);
        compAni.pause();
        activited = false;
    }

    public final void registerSub(CinoComponent sub) {
        subComps.add(sub);
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
