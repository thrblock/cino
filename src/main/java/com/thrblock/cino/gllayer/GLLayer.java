package com.thrblock.cino.gllayer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.glshape.GLShape;

public class GLLayer implements Iterable<GLShape> {
	private float viewXOffset;
	private float viewYOffset;
	private int mixA = GL.GL_SRC_ALPHA;
	private int mixB = GL.GL_ONE_MINUS_SRC_ALPHA;
	private List<GLShape> shapeList = new LinkedList<>();
	private List<GLShape> swap = new LinkedList<>();
	private Semaphore swapSp = new Semaphore(1);
	
	public float getViewXOffset() {
		return viewXOffset;
	}
	public void setViewXOffset(float viewXOffset) {
		this.viewXOffset = viewXOffset;
	}
	public float getViewYOffset() {
		return viewYOffset;
	}
	public void setViewYOffset(float viewYOffset) {
		this.viewYOffset = viewYOffset;
	}
	public void viewOffset(GL2 gl) {
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glTranslatef(viewXOffset,viewYOffset,0);
	}
	public int getMixA() {
		return mixA;
	}
	public void setMixA(int mixA) {
		this.mixA = mixA;
	}
	public int getMixB() {
		return mixB;
	}
	public void setMixB(int mixB) {
		this.mixB = mixB;
	}
	public void addShapeToSwap(GLShape shape) {
		swapSp.acquireUninterruptibly();
		swap.add(shape);
		swapSp.release();
	}
	public void swap() {
		if(!swap.isEmpty()) {
			swapSp.acquireUninterruptibly();
			shapeList.addAll(swap);
			swap.clear();
			swapSp.release();
		}
	}
	@Override
	public Iterator<GLShape> iterator() {
		return shapeList.iterator();
	}
}
