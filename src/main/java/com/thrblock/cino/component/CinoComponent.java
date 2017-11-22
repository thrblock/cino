package com.thrblock.cino.component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.CinoFrameConfig;
import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glfragment.EveryGLFragment;
import com.thrblock.cino.glfragment.ForeverFragment;
import com.thrblock.cino.glfragment.FragmentFactory;
import com.thrblock.cino.glfragment.GLFragmentManager;
import com.thrblock.cino.glfragment.IPureFragment;
import com.thrblock.cino.gllayer.IGLFrameBufferObjectManager;
import com.thrblock.cino.glshape.factory.GLNode;
import com.thrblock.cino.glshape.factory.GLShapeFactory;
import com.thrblock.cino.io.KeyControlStack;
import com.thrblock.cino.io.KeyEvent;
import com.thrblock.cino.io.KeyListener;

/**
 * 提供设计相关的主要成员
 * 
 * @author zepu.li
 */
@Component
public abstract class CinoComponent implements KeyListener {
    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

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
     * 根片段容器
     */
    @Autowired
    protected GLFragmentManager rootFrag;

    /**
     * 组件片段容器
     */
    protected GLFragmentManager compFrag;
    /**
     * 片段构造器
     */
    @Autowired
    protected FragmentFactory fragFactory;

    /**
     * 帧缓冲管理器
     */
    @Autowired
    protected IGLFrameBufferObjectManager fboManager;

    /**
     * sceneRoot是场景自动创建的GLNode根节点
     */
    protected GLNode sceneRoot;

    private List<VoidConsumer> onActivited = new LinkedList<>();
    private List<VoidConsumer> onDeactivited = new LinkedList<>();

    @PostConstruct
    private final void postConstruct() throws Exception {
        compFrag = rootFrag.generateSubContainer();
        compFrag.pause();
        fragFactory.setContainer(compFrag);
        sceneRoot = shapeFactory.createNode();
        init();
    }

    /**
     * 初始化时调用一次
     */
    public void init() throws Exception {
    }

    protected final void onActivited(VoidConsumer v) {
        onActivited.add(v);
    }

    protected final void onDeactivited(VoidConsumer v) {
        onDeactivited.add(v);
    }

    /**
     * 激活组件
     */
    public final void activited() {
        compFrag.remuse();
        onActivited.forEach(e -> e.accept());
    }

    /**
     * 停止组件
     */
    public final void deactivited() {
        compFrag.pause();
        onDeactivited.forEach(e -> e.accept());
    }

    /**
     * 伴随组件自动的帧片段逻辑
     * 
     * @param pure
     */
    protected final void auto(IPureFragment pure) {
        compFrag.addFragment(new ForeverFragment(pure));
    }

    protected final <T> void auto(Supplier<T> sup, Consumer<T> cons) {
        compFrag.addFragment(new EveryGLFragment(1, () -> cons.accept(sup.get())));
    }

    protected final void autoEvery(int count, IPureFragment pure) {
        compFrag.addFragment(new EveryGLFragment(count, pure));
    }

    protected final <T> void autoEvery(int count, Supplier<T> sup, Consumer<T> cons) {
        compFrag.addFragment(new EveryGLFragment(count, () -> cons.accept(sup.get())));
    }
    
    /**
     * 伴随组件自动的帧片段逻辑
     * 
     * @param pure
     */
    protected final void auto(BooleanSupplier condition,IPureFragment pure) {
        compFrag.addFragment(new ForeverFragment(pure.mergeCondition(condition)));
    }

    protected final <T> void auto(BooleanSupplier condition,Supplier<T> sup, Consumer<T> cons) {
        IPureFragment frag = () -> cons.accept(sup.get());
        compFrag.addFragment(new EveryGLFragment(1, frag.mergeCondition(condition)));
    }

    protected final void autoEvery(int count,BooleanSupplier condition, IPureFragment pure) {
        compFrag.addFragment(new EveryGLFragment(count, pure.mergeCondition(condition)));
    }

    protected final <T> void autoEvery(int count,BooleanSupplier condition, Supplier<T> sup, Consumer<T> cons) {
        IPureFragment frag = () -> cons.accept(sup.get());
        compFrag.addFragment(new EveryGLFragment(count, frag.mergeCondition(condition)));
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
