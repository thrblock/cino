package com.thrblock.cino.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.CinoFrameConfig;
import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glfragment.ForeverFragment;
import com.thrblock.cino.glfragment.FragmentBuilder;
import com.thrblock.cino.glfragment.GLFragmentContainer;
import com.thrblock.cino.glfragment.IPureFragment;
import com.thrblock.cino.glshape.builder.GLNode;
import com.thrblock.cino.glshape.builder.GLShapeBuilder;
import com.thrblock.cino.io.IKeyControlStack;

/**
 * 提供设计相关的主要成员
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
    protected GLShapeBuilder shapeBuilder;
    
    /**
     * 键盘IO控制器，可以挂载监听器捕获键盘事件或是读取某一指定按键的状态
     */
    @Autowired
    protected IKeyControlStack keyIO;
    
    /**
     * 根片段容器
     */
    @Autowired
    protected GLFragmentContainer rootFrag;
    
    /**
     * 组件片段容器
     */
    protected GLFragmentContainer compFrag;
    /**
     * 片段构造器
     */
    @Autowired
    protected FragmentBuilder fragBuilder;
    
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
        fragBuilder.setContainer(compFrag);
        sceneRoot = shapeBuilder.createNode();
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
        onActivited.forEach(e->e.accept());
    }
    
    /**
     * 停止组件
     */
    public final void deactivited() {
        compFrag.pause();
        onDeactivited.forEach(e->e.accept());
    }
    
    protected final void auto(IPureFragment pure) {
        compFrag.addFragment(new ForeverFragment(pure));
    }
    
    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    public void keyPressed(KeyEvent e){
    }

    @Override
    public void keyReleased(KeyEvent e){
    }
}
