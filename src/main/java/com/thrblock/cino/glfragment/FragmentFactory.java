package com.thrblock.cino.glfragment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 片段逻辑构造器，如果不习惯对非单例对象使用依赖注入，可以考虑使用Builder建造片段逻辑
 * @author lizepu
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class FragmentFactory {
    @Autowired
    private IGLFragmentManager container;

    /**
     * 使用指定的片段容器
     * @param container 指定的片段容器
     */
    public void setContainer(IGLFragmentManager container) {
        this.container = container;
    }

    /**
     * 构造一个代理片段逻辑
     * @param pure 纯粹的片段逻辑
     * @return 代理片段逻辑
     */
    public ProxyGLFragment buildProxyFragment(IPureFragment pure) {
        ProxyGLFragment result = new ProxyGLFragment(pure);
        container.addFragment(result);
        return result;
    }
    
    /**
     * 构造一个自动机
     * @return 自动机实例
     */
    public Automata buildAutoMachine() {
        Automata result = new Automata();
        container.addFragment(result);
        return result;
    }
    
    /**
     * 构造一个条件片段逻辑
     * @param condition 纯粹的条件逻辑
     * @return 条件片段逻辑实例
     */
    public ConditionGLFragment buildConditionGLFragment(IConditionFragment condition) {
        ConditionGLFragment result = new ConditionGLFragment(condition);
        container.addFragment(result);
        return result;
    }
    
    /**
     * 构造一个链式逻辑
     * @return 链式逻辑实例
     */
    public LinkedGLFragment buildLinkedGLFragment() {
        LinkedGLFragment result = new LinkedGLFragment();
        container.addFragment(result);
        return result;
    }
    
    /**
     * 构造一个循环逻辑
     * @return 循环逻辑实例
     */
    public LoopedGLFragment buildLoopedGLFragment() {
        LoopedGLFragment result = new LoopedGLFragment();
        container.addFragment(result);
        return result;
    }
    
    /**
     * 构造一个条件逻辑
     * @param pure 纯粹的片段逻辑
     * @return 条件逻辑 只执行一次
     */
    public ConditionGLFragment buildOneceGLFragment(IPureFragment pure) {
        OneceGLFragment result = new OneceGLFragment(pure);
        return buildConditionGLFragment(result);
    }
}
