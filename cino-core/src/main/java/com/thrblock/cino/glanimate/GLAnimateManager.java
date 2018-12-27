package com.thrblock.cino.glanimate;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.thrblock.cino.gllifecycle.GLCycle;

/**
 * GLAnimateManager 片段逻辑容器，为各类片段逻辑提供线程安全的插入、删除、遍历操作
 * @author lizepu
 */
@Component
public class GLAnimateManager {
    private GLCycle<GLAnimate> aniCycle = new GLCycle<>(GLAnimate[]::new);
    private boolean pause = false;
    private boolean destroy = false;
    private GLAnimateManager(){
    }

    public void runAll() {
        if(pause) {
            return;
        }
        for(GLAnimate frag : aniCycle.safeHold()) {
            if(frag.isDestory()) {
                aniCycle.safeRemove(frag);
            } else if(frag.isEnable()) {
                frag.animate();
            }
        }
    }

    public void addAnimate(GLAnimate frag) {
        aniCycle.safeAdd(frag);
    }
    
    /**
     * 暂停此容器的片段逻辑
     */
    public void pause() {
        pause = true;
    }
    
    /**
     * 恢复瓷容器片段逻辑的执行
     */
    public void remuse() {
        pause = false;
    }
    
    /**
     * 构造子集
     * @return 子集容器，树状容器结构适合游戏暂停效果的实现，你可以有选择的暂停某个子集或是根来实现暂停效果
     */
    public GLAnimateManager generateSubContainer() {
        GLAnimateManager result = new GLAnimateManager();
        this.addAnimate(new GLAnimate(){
            @Override
            public void animate() {
                result.runAll();
            }
            @Override
            public boolean isEnable() {
                return !result.pause;
            }
            @Override
            public boolean isDestory() {
                return result.destroy;
            }
        });
        return result;
    }
    
    @PreDestroy
    public void destroy() {
        this.destroy = true;
    }
}
