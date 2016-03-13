package com.thrblock.cino.gllayer;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thrblock.cino.glshape.GLShape;

@Component
public class GLLayerContainer implements IGLLayerContainer {
	private static final Logger LOG = LogManager.getLogger(GLLayerContainer.class);
	private List<GLLayer> layerList = new CopyOnWriteArrayList<>();
	
	@Override
	public void addLayer(GLLayer layer) {
		layerList.add(layer);
	}
	
	@Override
	public void addShapeToSwap(int index,GLShape shape) {
		if(layerList.size() > index) {
			layerList.get(index).addShapeToSwap(shape);
		} else {
			LOG.warn("layer not found:" + index);
			for(int i = index;i >= layerList.size();i--) {
				LOG.warn("layer auto generated:" + i);
				GLLayer gen = new GLLayer();
				layerList.add(gen);
				if(i == index) {
					gen.addShapeToSwap(shape);
				}
			}
		}
	}
	
	@Override
	public void swap() {
		for(GLLayer layer:layerList) {
			layer.swap();
		}
	}

	@Override
	public Iterator<GLLayer> iterator() {
		return layerList.iterator();
	}
}
