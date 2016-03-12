package com.thrblock.cino.glshape.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.gllayer.IGLLayerContainer;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLPoint;

@Component
public class GLShapeBuilder implements IGLShapeBuilder{
	private int layer = 0;
	@Autowired
	IGLLayerContainer container;
	
	@Override
	public void setLayer(int layerIndex) {
		this.layer = layerIndex;
	}


	@Override
	public GLPoint buildGLPoint(float x, float y) {
		GLPoint point = new GLPoint(x,y);
		container.addShapeToSwap(layer, point);
		return point;
	}

	@Override
	public GLLine buildGLLine(float x1, float y1, float x2, float y2) {
		GLLine line = new GLLine(x1,y1,x2,y2);
		container.addShapeToSwap(layer, line);
		return line;
	}

}
