package com.thrblock.cino.scene;

import java.awt.event.KeyEvent;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.CinoFrameConfig;
import com.thrblock.cino.glfragment.FragmentBuilder;
import com.thrblock.cino.glfragment.GLFragmentContainer;
import com.thrblock.cino.glshape.builder.GLNode;
import com.thrblock.cino.glshape.builder.GLShapeBuilder;
import com.thrblock.cino.io.IKeyControlStack;
/**
 * 场景（抽象层）<br />
 * 场景提供了绘制图像所需的主要成员并提供一系列lifecycle方法
 * @author lizepu
 */
@Component
public abstract class AbstractCinoScene implements ICinoScene {
    /**
     * 场景日志
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * frame中包括了当前绘制框架的大部分信息（宽高、垂直同步是否开启，是否全屏等）
     */
    @Autowired
    protected CinoFrameConfig frame;
    
    
    /**
     * 导演，控制不同场景间的切换
     */
    @Autowired
    protected ICinoDirector director;
    
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
     * 根片段容器，对此容器的操作会影响到其所有子集，因此不推荐直接操作此对象，而是操作具有实际抽象意义的子集对象<br />
     * 你可以通过方法generateSubContainer来构造一个子集
     * @see com.thrblock.cino.glfragment.GLFragmentContainer#generateSubContainer()
     */
    @Autowired
    protected GLFragmentContainer rootFrag;
    
    /**
     * 属于场景的片段容器
     */
    protected GLFragmentContainer sceneFrag;
    
    /**
     * 片段构造器
     */
    @Autowired
    protected FragmentBuilder fragBuilder;
    /**
     * sceneRoot是场景自动创建的GLNode根节点
     */
    protected GLNode sceneRoot;
    
    @PostConstruct
    private void postConstruct() throws Exception {
        sceneFrag = rootFrag.generateSubContainer();
        fragBuilder.setContainer(sceneFrag);
        sceneRoot = shapeBuilder.createNode();
        init();
    }
    
    /**
     * 初始化时调用一次
     */
    public void init() throws Exception{
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
    
    @Override
    public void recover() {
        enable();
    }
    
    @Override
    public void covered() {
    }
}
