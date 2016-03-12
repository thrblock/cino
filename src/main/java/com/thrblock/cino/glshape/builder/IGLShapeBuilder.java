package com.thrblock.cino.glshape.builder;

import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLPoint;

public interface IGLShapeBuilder {
	public void setLayer(int layerIndex);
	public GLPoint buildGLPoint(float x,float y);
	public GLLine buildGLLine(float x1,float y1,float x2,float y2);
}
