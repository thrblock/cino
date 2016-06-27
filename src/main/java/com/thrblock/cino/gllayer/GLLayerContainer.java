package com.thrblock.cino.gllayer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.thrblock.cino.glshape.GLShape;

@Component
public class GLLayerContainer implements IGLLayerContainer {
	private static final Logger LOG = LoggerFactory.getLogger(GLLayerContainer.class);
	private List<GLLayer> layerList = new CopyOnWriteArrayList<>();
	private GLLayer topLayer = new GLLayer();
	@Override
	public GLLayer getLayer(int index) {
		if(index == -1) {
			return topLayer;
		}
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
		if(index == -1) {
			topLayer.addShapeToSwap(shape);
			return;
		}
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
		topLayer.swap();
	}

	@Override
	public int size() {
		return layerList.size();
	}

}
