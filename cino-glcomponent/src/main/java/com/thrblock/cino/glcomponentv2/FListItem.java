package com.thrblock.cino.glcomponentv2;

import java.util.LinkedList;
import java.util.List;

import com.thrblock.cino.glcomponentv2.func.EventPerformed;

/**
 * 列表项
 * @author zepu.li
 * @param <T> 列表所容纳的抽象数据
 */
public class FListItem extends FComponent {
    private List<EventPerformed> select = new LinkedList<>();
    private List<EventPerformed> reject = new LinkedList<>();
    private List<EventPerformed> activited = new LinkedList<>();

    /**
     * 选择此item
     */
    public void select() {
        select.forEach(e -> e.perform());
    }
    
    /**
     * 取消选定此item
     */
    public void reject() {
        reject.forEach(e -> e.perform());
    }
    
    /**
     * 挂载一个监听选择逻辑
     * @param ep
     */
    public void onSelect(EventPerformed ep) {
        select.add(ep);
    }
    
    /**
     * 挂载一个监听取消选择逻辑
     * @param ep
     */
    public void onReject(EventPerformed ep) {
        reject.add(ep);
    }
    
    /**
     * 激活模式
     * @param ep
     */
    public void onActivited(EventPerformed ep) {
        activited.add(ep);
    }
    
    /**
     * 同时绑定两种逻辑
     * @param se
     * @param re
     */
    public void selectAndReject(EventPerformed se,EventPerformed re) {
        onSelect(se);
        onReject(re);
    }

    /**
     * 激活当前模式
     */
    public void activited() {
        activited.forEach(e -> e.perform());
    }
}
