package com.thrblock.cino.glfragment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * AbstractGLFragment 抽象片段逻辑<br />
 * 片段逻辑的简单实现，包括了enable及destory的默认实现<br />
 * 初始化时将自动加入绘制后的执行队列中
 * @author lizepu
 */
public abstract class AbstractGLFragment implements IGLFragment {
    private boolean enable = false;
    private boolean destory = false;
    
    @Autowired
    private IGLFragmentContainer container;
    
    protected AbstractGLFragment(){
    }
    
    @PostConstruct
    private void addToContainer() {
        container.addFragment(this);
    }
    
    @Override
    public boolean isDestory() {
        return destory;
    }
    @Override
    public boolean isEnable() {
        return enable;
    }
    /**
     * 开启此片段逻辑
     */
    public void enable() {
        this.enable = true;
    }
    /**
     * 关闭此片段逻辑
     */
    public void disable() {
        this.enable = false;
    }
    /**
     * 销毁此片段逻辑
     */
    public void destory() {
        this.destory = true;
    }
}
