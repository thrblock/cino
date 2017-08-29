package com.thrblock.cino.glfragment;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.thrblock.cino.util.structure.CrudeLinkedList;

/**
 * GLFragmentContainer 片段逻辑容器，为各类片段逻辑提供线程安全的插入、删除、遍历操作
 * @author lizepu
 */
@Component
public class GLFragmentManager implements IGLFragmentManager{
    private CrudeLinkedList<IGLFragment> frags = new CrudeLinkedList<>();
    private CrudeLinkedList<IGLFragment>.CrudeIter fragIt = frags.genCrudeIter();
    private List<IGLFragment> swap = new LinkedList<>();
    private Semaphore swapSp = new Semaphore(1);
    private boolean pause = false;
    private boolean destroy = false;
    private GLFragmentManager(){
    }
    @Override
    public void allFragment() {
        if(destroy) {
            this.frags = new CrudeLinkedList<>();
            this.fragIt = frags.genCrudeIter();
            return;
        }
        if(pause) {
            return;
        }
        while(fragIt.hasNext()) {
            IGLFragment frag = fragIt.next();
            if(frag.isDestory()) {
                fragIt.remove();
            } else if(frag.isEnable()) {
                frag.fragment();
            }
        }
        fragIt.reset();
        if(!swap.isEmpty()) {
            swapSp.acquireUninterruptibly();
            frags.addAll(swap);
            swap.clear();
            swapSp.release();
        }
    }
    @Override
    public void addFragment(IGLFragment frag) {
        swapSp.acquireUninterruptibly();
        swap.add(frag);
        swapSp.release();
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
    public GLFragmentManager generateSubContainer() {
        GLFragmentManager result = new GLFragmentManager();
        this.addFragment(new IGLFragment(){
            @Override
            public void fragment() {
                result.allFragment();
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
    
    @PreDestroy @Override
    public void destroy() {
        this.destroy = true;
    }
}
