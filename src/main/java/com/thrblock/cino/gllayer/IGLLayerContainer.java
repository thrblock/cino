package com.thrblock.cino.gllayer;

import com.thrblock.cino.glshape.GLShape;

public interface IGLLayerContainer extends Iterable<GLLayer>{
	public int addLayer(GLLayer layer);
	public GLLayer getLayer(int index);
	public void addShapeToSwap(int index,GLShape shape);
	public void swap();
}
