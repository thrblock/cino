package com.thrblock.cino.glcomponentv2;

import java.util.LinkedList;
import java.util.List;

import com.thrblock.cino.glcomponentv2.func.EventPerformed;

/**
 * 纯逻辑组件
 * Logic组件仅包含逻辑部分，视图部分由闭包及函数式设计代替
 * 2018年3月29日：这个设计对组件行为进行抽象而完全不管展示逻辑，感觉不是很有效
 * @author zepu.li
 */
public class FComponent {
    private boolean enable = true;
    private List<FComponent> subs = new LinkedList<>();
    private List<EventPerformed> showEvents = new LinkedList<>();
    private List<EventPerformed> hideEvents = new LinkedList<>();
    private List<EventPerformed> enableEvents = new LinkedList<>();
    private List<EventPerformed> disableEvents = new LinkedList<>();
    
    /**
     * show定义为组件显示 请把控制显示的逻辑挂载于此
     */
    public void show() {
        showEvents.forEach(e -> e.perform());
        subs.forEach(e -> e.show());
    }
    
    /**
     * hide定义为组件隐藏（及清除一切视觉元素） 请在此处挂载隐藏逻辑
     */
    public void hide() {
        hideEvents.forEach(e -> e.perform());
        subs.forEach(e -> e.hide());
    }
    
    /**
     * 挂载一个显示逻辑
     * @param ep
     */
    public void onComponentShow(EventPerformed ep) {
        showEvents.add(ep);
    }
    
    /**
     * 挂载一个隐藏逻辑
     * @param ep
     */
    public void onComponentHide(EventPerformed ep) {
        hideEvents.add(ep);
    }
    
    public boolean isEnable() {
        return this.enable;
    }
    
    /**
     * 启用组件，组件默认是启用状态的
     * 例如 按钮从灰色变为可用
     */
    public void enable() {
        this.enable = true;
        subs.forEach(e -> e.enable());
        enableEvents.forEach(e -> e.perform());
    }
    
    /**
     * 挂载一个启用逻辑
     * @param ep
     */
    public void onComponentEnable(EventPerformed ep) {
        enableEvents.add(ep);
    }
    
    /**
     * 无效化组件
     * 例如把按钮置灰
     */
    public void disable() {
        this.enable = false;
        subs.forEach(e -> e.disable());
        disableEvents.forEach(e -> e.perform());
    }
    
    /**
     * 挂载一个无效化逻辑
     * @param ep
     */
    public void onComponentDisable(EventPerformed ep) {
        disableEvents.add(ep);
    }
    
    /**
     * 增加一个子元素
     * @param sub 子元素
     */
    public void addSubComponent(FComponent sub) {
        subs.add(sub);
    }
}
