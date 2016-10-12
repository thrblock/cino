package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * LoopedGLFragment 循环条件逻辑 <br />
 * 与链式逻辑相似，此类逻辑首尾相连
 * @author lizepu
 *
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class LoopedGLFragment extends AbstractGLFragment {
    private static class Node{
        ConditionGLFragment fragment;
        Node next;
        int fixedDelay = 0;
        int currentDelay = 0;
    }
    private Node node = new Node();
    private Node initNode = node;
    private Node current = node;
    {
        node.next = node;
    }
    protected LoopedGLFragment(){
    }
    @Override
    public void fragment() {
        if(current.currentDelay > 0) {
            current.currentDelay --;
        } else if(current.fragment != null) {
            if(current.fragment.fragmentCondition()) {
                current.currentDelay = current.fixedDelay;
                current = current.next;
            }
        } else {
            current.currentDelay = current.fixedDelay;
            current = initNode;
        }
    }
    
    /**
     * 以纯逻辑作为条件链的一部分，此逻辑仅执行一次
     * @param frag 纯逻辑片段
     * @return 自身实例，已使用链式调用
     */
    public LoopedGLFragment add(IPureFragment frag) {
        ConditionGLFragment condition = new ConditionGLFragment(new OneceGLFragment(frag));
        add(condition::fragmentCondition);
        return this;
    }
    
    /**
     * 以条件逻辑作为条件链的一部分
     * @param frag
     * @return 自身实例，已使用链式调用
     */
    public LoopedGLFragment add(IConditionFragment frag) {
        node.fragment = new ConditionGLFragment(frag);
        Node nextNode = new Node();
        nextNode.next = node.next;
        node.next = nextNode;
        node = node.next;
        return this;
    }
    
    /**
     * 加入一段片段延时 单位 帧数，例如FPS设定为60时，count ＝ 60即为延时1秒
     * @param count 要延时的帧数
     * @return 实体自身，方便使用链式构造
     */
    public LoopedGLFragment delay(int count) {
        node.fixedDelay = count;
        node.currentDelay = node.fixedDelay;
        return this;
    }
}
