package com.thrblock.cino.glfragment;

public class LinkedGLFragment extends AbstractGLFragment {
	private static class Node{
		ConditionGLFragment fragment;
		Node next;
	}
	private Node node = new Node();
	private Node current = node;
	@Override
	public void fragment() {
		if(current.fragment != null) {
			if(current.fragment.fragmentCondition()) {
				current = current.next;
			}
		} else {
			destory();
		}
	}
	
	public LinkedGLFragment add(IOneceFragment frag) {
		node.fragment = new ConditionGLFragment(new OneceGLFragment(frag));
		node.next = new Node();
		node = node.next;
		return this;
	}
	
	public LinkedGLFragment add(IConditionFragment frag) {
		node.fragment = new ConditionGLFragment(frag);
		node.next = new Node();
		node = node.next;
		return this;
	}
}
