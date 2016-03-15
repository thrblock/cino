package com.thrblock.cino.gltexture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

@Component
public class GLTextureContainer implements IGLTextureContainer {
	private static final Logger LOG = LogManager.getLogger(GLTextureContainer.class);
	private Map<String, Texture> imgTextureMap = new HashMap<>();
	private Map<String, StreamPair> swap = new HashMap<>();
	private Semaphore swapSp = new Semaphore(1);

	@Override
	public Texture getTexture(String name) {
		return imgTextureMap.get(name);
	}
	
	@Override
	public void registerTexture(String name,String imgType,InputStream srcStream) {
		LOG.info("registerTexture name:" + name + ",type:" + imgType + ",stream:" + srcStream);
		swapSp.acquireUninterruptibly();
		if(!swap.containsKey(name) && !imgTextureMap.containsKey(name)) {
			swap.put(name, new StreamPair(imgType,srcStream));
		}
		swapSp.release();
	}
	
	@Override
	public void registerTexture(String name,File imgFile) {
		try {
			String fileName = imgFile.getCanonicalPath();
			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				String extName = fileName.substring(i+1);
				InputStream srcStream = new FileInputStream(imgFile);
				registerTexture(name,extName,srcStream);
			} else {
				LOG.warn("no extension name found from file:" + imgFile);
			}
		} catch (IOException e) {
			LOG.warn("IOException in registerTexture by file:" + e);
		}
	}

	@Override
	public void parseTexture(GL2 gl) {
		if (!swap.isEmpty()) {
			swapSp.acquireUninterruptibly();
			for (Entry<String, StreamPair> entry : swap.entrySet()) {
				StreamPair pair = entry.getValue();
				try {
					Texture texture = TextureIO.newTexture(pair.stream,false,pair.imgType);
					texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
					texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
					imgTextureMap.put(entry.getKey(),texture);
				} catch (GLException | IOException e) {
					LOG.warn("Exception in parseTexture:" + e);
				}
			}
			gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
			swap.clear();
			swapSp.release();
		}
	}
	private static class StreamPair {
		private final String imgType;
		private final InputStream stream;
		public StreamPair(String imgType,InputStream stream) {
			this.imgType = imgType;
			this.stream = stream;
		}
	}
}