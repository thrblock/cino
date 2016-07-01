package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * LinkedGLFragment 链式条件逻辑结构 <br />
 * 此结构由一个或多个条件逻辑构成，每次运行一个逻辑，得到true时跳转至下一个逻辑
 * @author lizepu
 *
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class LinkedGLFragment extends AbstractGLFragment {
    private static class Node{
        ConditionGLFragment fragment;
        Node next;
    }
    private Node node = new Node();
    private Node initNode = node;
    private Node current = node;
    
    protected LinkedGLFragment(){
    }
    @Override
    public void fragment() {
        if(current.fragment != null) {
            if(current.fragment.fragmentCondition()) {
                current = current.next;
            }
        } else {
            this.current = initNode;
            disable();
        }
    }
    
    /**
     * 以纯粹片段增加为一个条件逻辑，此片段仅会执行一次
     * @param frag 纯片段逻辑
     * @return 实体自身，以使用add构造链式结构
     */
    public LinkedGLFragment add(IPureFragment frag) {
        node.fragment = new ConditionGLFragment(new OneceGLFragment(frag));
        node.next = new Node();
        node = node.next;
        return this;
    }
    
    /**
     * 增加一个条件逻辑
     * @param frag 条件逻辑
     * @return 实体自身，以使用add构造链式结构
     */
    public LinkedGLFragment add(IConditionFragment frag) {
        node.fragment = new ConditionGLFragment(frag);
        node.next = new Node();
        node = node.next;
        return this;
    }
}
