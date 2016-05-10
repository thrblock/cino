package com.thrblock.cino.structureutil;

import java.util.Iterator;

public class CrudeLinkedList<T> {
	private Node head = null;
	private Node tail = null;
	
	public void add(T t) {
		if(head == null) {
			head = new Node();
			head.object = t;
			tail = head;
		} else {
			Node n = new Node();
			n.object = t;
			tail.next = n;
			n.prev = tail;
			tail = n;
		}
	}
	
	public CrudeIter genCrudeIter() {
		return new CrudeIter();
	}
	class Node {
		T object;
		Node next;
		Node prev;
	}
	public class CrudeIter implements Iterator<T>{
		private Node begin;
		private Node current = null;
		{
			begin = new Node();
			begin.next = head;
			current = begin;
		}
		@Override
		public boolean hasNext() {
			return current.next != null;
		}
		
		@Override
		public T next() {
			current = current.next;
			return current.object;
		}
		
		@Override
		public void remove() {
			current.prev.next = current.next;
			current.next.prev = current.prev;
		}
		
		public void reset() {
			begin.next = head;
			current = begin;
		}
	}
}
