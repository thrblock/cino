package com.thrblock.cino.glshape.builder;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.thrblock.cino.gllayer.IGLLayerContainer;
import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.GLCharTextureGenerater;
import com.thrblock.cino.gltexture.GLTextureContainer;

/**
 * 图形构造器
 * 
 * @author lizepu
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class GLShapeBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(GLShapeBuilder.class);
	private int layer = 0;
	private GLShapeNode currentNode = null;
	@Autowired
	IGLLayerContainer layerContainer;

	@Autowired
	GLTextureContainer textureContainer;
	
	@Autowired
	GLCharTextureGenerater charTexture;
	
	/**
	 * 设置此构造器的图像层索引，该构造器所构造的图形会处于索引层次
	 * @param layerIndex 图像层索引
	 */
	public void setLayer(int layerIndex) {
		this.layer = layerIndex;
	}

	/**
	 * 构造一个点
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @return 点图形
	 */
	public GLPoint buildGLPoint(float x, float y) {
		GLPoint point = new GLPoint(x, y);
		layerContainer.addShapeToSwap(layer, point);
		if (currentNode != null) {
			currentNode.addSubNode(point);
		}
		return point;
	}

	/**
	 * 构造一条直线
	 * @param x1 横坐标1
	 * @param y1 纵坐标1
	 * @param x2 横坐标2
	 * @param y2 纵坐标2
	 * @return 直线图形
	 */
	public GLLine buildGLLine(float x1, float y1, float x2, float y2) {
		GLLine line = new GLLine(x1, y1, x2, y2);
		layerContainer.addShapeToSwap(layer, line);
		if (currentNode != null) {
			currentNode.addSubNode(line);
		}
		return line;
	}

	/**
	 * 构造一个矩形
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param width 宽度
	 * @param height 高度
	 * @return 矩形图形
	 */
	public GLRect buildGLRect(float x, float y, float width, float height) {
		GLRect rect = new GLRect(x, y, width, height);
		layerContainer.addShapeToSwap(layer, rect);
		if (currentNode != null) {
			currentNode.addSubNode(rect);
		}
		return rect;
	}

	/**
	 * 构造一个椭圆
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param axisA 半长轴
	 * @param axisB 半短轴
	 * @param accuracy 精度，即使用点的个数
	 * @return 椭圆对象
	 */
	public GLOval buildGLOval(float x, float y, float axisA, float axisB, int accuracy) {
		GLOval oval = GLOval.generate(x, y, axisA, axisB, accuracy);
		layerContainer.addShapeToSwap(layer, oval);
		if (currentNode != null) {
			currentNode.addSubNode(oval);
		}
		return oval;
	}

	/**
	 * 创建一个贴图对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param width 宽度
	 * @param height 高度
	 * @param textureName 使用的贴图纹理
	 * @return 贴图对象
	 */
	public GLImage buildGLImage(float x, float y, float width, float height, String textureName) {
		GLImage image = new GLImage(textureContainer, x, y, width, height, textureName);
		layerContainer.addShapeToSwap(layer, image);
		if (currentNode != null) {
			currentNode.addSubNode(image);
		}
		return image;
	}

	/**
	 * 创建一个贴图对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param width 宽度
	 * @param height 高度
	 * @param imgFile 图像文件，使用其文件系统的范式名称作为纹理名称
	 * @return 贴图对象
	 */
	public GLImage buildGLImage(float x, float y, float width, float height, File imgFile) {
		String textureName;
		try {
			textureName = imgFile.getCanonicalPath();
		} catch (IOException e) {
			textureName = imgFile.getAbsolutePath();
			LOG.warn("IOException in build GLImage:" + e);
			LOG.warn("error in get canonical name of file:" + imgFile + ", the texture may be unable to reuse");
		}
		textureContainer.registerTexture(textureName, imgFile);
		GLImage image = new GLImage(textureContainer, x, y, width, height, textureName);
		layerContainer.addShapeToSwap(layer, image);
		if (currentNode != null) {
			currentNode.addSubNode(image);
		}
		return image;
	}

	/**
	 * 创建一个贴图对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param width 宽度
	 * @param height 高度
	 * @param imgInputStream 图像输入流，<b>使用此种方式不会复用纹理</b>
	 * @param imgType 图像类型，如"png"
	 * @return 贴图对象
	 */
	public GLImage buildGLImage(float x, float y, float width, float height, InputStream imgInputStream,
			String imgType) {
		String textureName = imgInputStream.toString();
		textureContainer.registerTexture(textureName, imgType, imgInputStream);
		GLImage image = new GLImage(textureContainer, x, y, width, height, textureName);
		layerContainer.addShapeToSwap(layer, image);
		if (currentNode != null) {
			currentNode.addSubNode(image);
		}
		return image;
	}

	
	/**
	 * 创建一个文字区
	 * @param f 字体
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param w 宽度（水平校准依据）
	 * @param h 高度（垂直校准依据）
	 * @param initStr 初始文字
	 * @return GLCharArea 文字区对象
	 */
	public GLCharArea buildGLCharArea(Font f, float x, float y, float w, float h, String initStr) {
		GLCharArea charLine = new GLCharArea(charTexture, f, x, y, w, h, initStr);
		layerContainer.addShapeToSwap(layer, charLine);
		if (currentNode != null) {
			currentNode.addSubNode(charLine);
		}
		return charLine;
	}
	
	/**
	 * 创建文字区
	 * @param f 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param initStr
	 * @return
	 */
	public GLCharArea buildGLCharArea(Font f, float x, float y, float w, float h, char[] initStr) {
		GLCharArea charLine = new GLCharArea(charTexture, f, x, y, w, h, initStr);
		layerContainer.addShapeToSwap(layer, charLine);
		if (currentNode != null) {
			currentNode.addSubNode(charLine);
		}
		return charLine;
	}


	/**
	 * 创建新的树结构节点
	 * @return GLShapeNode 图像节点
	 */
	public GLShapeNode createNewNode() {
		currentNode = new GLShapeNode();
		return currentNode;
	}

	/**
	 * 在现有节点的基础上延伸一级子节点
	 * @return GLShapeNode 图像节点，当前的子节点
	 */
	public GLShapeNode createNode() {
		if (currentNode == null) {
			currentNode = new GLShapeNode();
		} else {
			GLShapeNode nNode = new GLShapeNode();
			nNode.setParent(currentNode);
			currentNode.addSubNode(nNode);
			currentNode = nNode;
		}
		return currentNode;
	}

	/**
	 * 将当前节点置空
	 */
	public void clearNode() {
		currentNode = null;
	}

	/**
	 * 设置当前操作节点
	 * @param node 
	 */
	public void setNode(GLShapeNode node) {
		currentNode = node;
	}

	/**
	 * 根据当前节点，回溯至上一层
	 */
	public void backtrack() {
		if (currentNode != null) {
			currentNode = currentNode.getParent();
		}
	}
}
