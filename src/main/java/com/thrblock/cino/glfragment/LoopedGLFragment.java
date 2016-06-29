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
    }
    private Node node = null;
    protected LoopedGLFragment(){
    }
    @Override
    public void fragment() {
        if(node != null) {
            if(node.fragment.fragmentCondition()) {
                node = node.next;
            }
        } else {
            disable();
        }
    }
    
    /**
     * 以纯逻辑作为条件链的一部分，此逻辑仅执行一次
     * @param frag 纯逻辑片段
     * @return 自身实例，已使用链式调用
     */
    public LoopedGLFragment add(IPureFragment frag) {
        ConditionGLFragment condition = new ConditionGLFragment(new OneceGLFragment(frag));
        add(()->condition.fragmentCondition());
        return this;
    }
    
    /**
     * 以条件逻辑作为条件链的一部分
     * @param frag
     * @return 自身实例，已使用链式调用
     */
    public LoopedGLFragment add(IConditionFragment frag) {
        ConditionGLFragment condition = new ConditionGLFragment(frag);
        if(node == null) {
            node = new Node();
            node.next = node;
            node.fragment = condition;
        } else {
            Node nextNode = new Node();
            nextNode.next = node.next;
            node.next = nextNode;
            nextNode.fragment = condition;
        }
        return this;
    }
}
