package com.thrblock.cino.fragment;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Component;

@Component
public class GLFragmentContainer implements IGLFragmentContainer{
	private List<IGLFragment> frags = new LinkedList<>();
	private Set<IGLFragment> swap = new HashSet<IGLFragment>();
	private Semaphore swapSp = new Semaphore(1);
	@Override
	public void allFragment() {
		for(IGLFragment frag:frags){
			frag.fragment();
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
