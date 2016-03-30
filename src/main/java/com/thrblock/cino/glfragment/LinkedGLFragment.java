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
	
	public LinkedGLFragment add(ConditionGLFragment frag) {
		node.fragment = frag;
		node.next = new Node();
		node = node.next;
		return this;
	}
}
