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
        int fixedDelay = 0;
        int currentDelay = 0;
    }
    private Node node = new Node();
    private Node initNode = node;
    private Node current = node;
    protected LinkedGLFragment(){
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
        ConditionGLFragment condition =  new ConditionGLFragment(new OneceGLFragment(frag));
        add(condition::fragmentCondition);
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
    
    /**
     * 加入一段片段延时 单位 帧数，例如FPS设定为60时，count ＝ 60即为延时1秒
     * @param count 要延时的帧数
     * @return 实体自身，方便使用链式构造
     */
    public LinkedGLFragment delay(int count) {
        node.fixedDelay = count;
        node.currentDelay = node.fixedDelay;
        return this;
    }
}
