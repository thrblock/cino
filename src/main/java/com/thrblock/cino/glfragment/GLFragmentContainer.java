package com.thrblock.cino.glfragment;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Component;

@Component
public class GLFragmentContainer implements IGLFragmentContainer{
	private List<IGLFragment> frags = new LinkedList<>();
	private List<IGLFragment> swap = new LinkedList<>();
	private Semaphore swapSp = new Semaphore(1);
	@Override
	public void allFragment() {
		Iterator<IGLFragment> fragIt = frags.iterator();
		while(fragIt.hasNext()) {
			IGLFragment frag = fragIt.next();
			if(frag.isDestory()) {
				fragIt.remove();
			} else if(frag.isEnable()) {
				frag.fragment();
			}
		}
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