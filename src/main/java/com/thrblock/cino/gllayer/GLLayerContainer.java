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
	public GLLayer getLayer(int index) {
		if(layerList.size() <= index) {
			LOG.warn("layer not found:" + index);
			for(int i = layerList.size();i <= index;i++) {
				LOG.info("layer auto generated:" + i);
				GLLayer gen = new GLLayer();
				layerList.add(gen);
			}
		}
		return layerList.get(index);
	}
	
	@Override
	public int addLayer(GLLayer layer) {
		layerList.add(layer);
		return layerList.size() - 1;
	}
	
	@Override
	public void addShapeToSwap(int index,GLShape shape) {
		if(layerList.size() <= index) {
			LOG.warn("layer not found:" + index);
			for(int i = layerList.size();i <= index;i++) {
				LOG.info("layer auto generated:" + i);
				GLLayer gen = new GLLayer();
				layerList.add(gen);
			}
		}
		layerList.get(index).addShapeToSwap(shape);
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
