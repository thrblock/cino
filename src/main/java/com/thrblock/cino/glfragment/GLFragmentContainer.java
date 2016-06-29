package com.thrblock.cino.glfragment;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Component;

import com.thrblock.cino.util.structure.CrudeLinkedList;

/**
 * GLFragmentContainer 片段逻辑容器，为各类片段逻辑提供线程安全的插入、删除、遍历操作
 * @author lizepu
 */
@Component
public class GLFragmentContainer implements IGLFragmentContainer{
    private CrudeLinkedList<IGLFragment> frags = new CrudeLinkedList<>();
    private CrudeLinkedList<IGLFragment>.CrudeIter fragIt = frags.genCrudeIter();
    private List<IGLFragment> swap = new LinkedList<>();
    private Semaphore swapSp = new Semaphore(1);
    @Override
    public void allFragment() {
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
}
