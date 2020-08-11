package com.thrblock.cino.glanimate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;

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
public class GLAnimateFactory {
    @Autowired
    private GLAnimateManager container;

    /**
     * 使用指定的片段容器
     * @param container 指定的片段容器
     */
    public void setContainer(GLAnimateManager container) {
        this.container = container;
    }
    
    public GLAnimate build() {
        GLAnimate animate = new GLAnimate();
        container.addAnimate(animate);
        return animate;
    }
    
    public GLAnimate build(IPureFragment... frags) {
        GLAnimate animate = new GLAnimate();
        container.addAnimate(animate);
        Arrays.stream(frags).forEach(animate::add);
        return animate;
    }
    
    class BuildNode {
        
        GLAnimate ani = new GLAnimate();
        Runnable finish;
        
        BooleanSupplier currentCondition;
        List<Runnable> runs = new LinkedList<>();
        
        public BuildNode when(BooleanSupplier condition) {
            this.currentCondition = condition;
            return this;
        }
        
        public BuildNode then(Runnable r) {
            runs.add(r);
            return this;
        }
        
        public BuildNode whenFinish(Runnable r) {
            
            return this;
        }
        
        public GLAnimate build() {
            container.addAnimate(ani); 
            return ani;
        }
    }

}
