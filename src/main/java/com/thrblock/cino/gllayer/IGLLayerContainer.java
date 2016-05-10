package com.thrblock.cino.gllayer;

import com.thrblock.cino.glshape.GLShape;

public interface IGLLayerContainer {
	public int addLayer(GLLayer layer);
	public GLLayer getLayer(int index);
	public int size();
	public void addShapeToSwap(int index,GLShape shape);
	public void swap();
}
