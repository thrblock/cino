package com.thrblock.cino.glfragment;


public class LoopedGLFragment extends AbstractGLFragment {
	private static class Node{
		ConditionGLFragment fragment;
		Node next;
	}
	private Node node = null;
	protected LoopedGLFragment(){
	}
	@Override
	public void fragment() {
		if(node != null) {
			if(node.fragment.fragmentCondition()) {
				node = node.next;
			}
		} else {
			disable();
		}
	}
	
	public LoopedGLFragment add(IPureFragment frag) {
		ConditionGLFragment condition = new ConditionGLFragment(new OneceGLFragment(frag));
		if(node == null) {
			node = new Node();
			node.next = node;
			node.fragment = condition;
		} else {
			Node nextNode = new Node();
			nextNode.next = node.next;
			node.next = nextNode;
			nextNode.fragment = condition;
		}
		return this;
	}
	
	public LoopedGLFragment add(IConditionFragment frag) {
		ConditionGLFragment condition = new ConditionGLFragment(frag);
		if(node == null) {
			node = new Node();
			node.next = node;
			node.fragment = condition;
		} else {
			Node nextNode = new Node();
			nextNode.next = node.next;
			node.next = nextNode;
			nextNode.fragment = condition;
		}
		return this;
	}
}
