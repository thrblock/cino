package com.thrblock.cino.glanimate;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.thrblock.cino.glanimate.fragment.IPureFragment;
import com.thrblock.cino.gllifecycle.CycleArray;

import lombok.Getter;

/**
 * GLAnimateManager 片段逻辑容器，为各类片段逻辑提供线程安全的插入、删除、遍历操作
 * 
 * @author lizepu
 */
@Component
public class GLFragmentManager {
    private CycleArray<GLFragmentManager> subs = new CycleArray<>(GLFragmentManager[]::new);
    private CycleArray<IPureFragment> aniCycle = new CycleArray<>(IPureFragment[]::new);
    private boolean pause = false;
    @Getter
    private long clock;

    private GLFragmentManager() {
    }

    public void runAll() {
        if (pause) {
            return;
        }
        clock++;
        IPureFragment[] frags = aniCycle.safeHold();
        for (int i = 0; i < frags.length; i++) {
            frags[i].fragment();
        }
    }

    public void addFragment(IPureFragment frag) {
        aniCycle.safeAdd(frag);
    }

    public void removeFragment(IPureFragment frag) {
        aniCycle.safeRemove(frag);
    }

    /**
     * 暂停此容器的片段逻辑
     */
    public void pause() {
        pause = true;
    }

    /**
     * 恢复此容器片段逻辑的执行
     */
    public void remuse() {
        pause = false;
    }

    /**
     * 构造子集
     * 
     * @return 子集容器，树状容器结构适合游戏暂停效果的实现，你可以有选择的暂停某个子集或是根来实现暂停效果
     */
    public GLFragmentManager generateSubContainer() {
        GLFragmentManager result = new GLFragmentManager();
        subs.safeAdd(result);
        this.addFragment(result::runAll);
        return result;
    }

    @PreDestroy
    public void destroy() {
        aniCycle.safeRemoveAll();
        subs.safeOperation(s -> s.forEach(GLFragmentManager::destroy));
        subs.safeRemoveAll();
    }
}
