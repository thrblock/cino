package com.thrblock.cino.gllayer;

import com.thrblock.cino.glshape.GLShape;

public interface IGLLayerContainer extends Iterable<GLLayer>{
	public void addLayer(GLLayer layer);
	public void addShapeToSwap(int index,GLShape shape);
	public void swap();
}
