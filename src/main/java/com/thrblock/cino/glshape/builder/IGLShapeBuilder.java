package com.thrblock.cino.glshape.builder;

import java.io.File;
import java.io.InputStream;

import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.GLSprite;
import com.thrblock.cino.glshape.GLTextArea;

/**
 * 图形构造器（抽象）<br />
 * 这个抽象可能毫无意义...
 * @author lizepu
 *
 */
public interface IGLShapeBuilder {
	/**
	 * 设置此构造器的图像层索引，该构造器所构造的图形会处于索引层次
	 * @param layerIndex 图像层索引
	 */
	public void setLayer(int layerIndex);
	/**
	 * 构造一个点
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @return 点图形
	 */
	public GLPoint buildGLPoint(float x,float y);
	/**
	 * 构造一条直线
	 * @param x1 横坐标1
	 * @param y1 纵坐标1
	 * @param x2 横坐标2
	 * @param y2 纵坐标2
	 * @return 直线图形
	 */
	public GLLine buildGLLine(float x1,float y1,float x2,float y2);
	/**
	 * 构造一个矩形
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param width 宽度
	 * @param height 高度
	 * @return 矩形图形
	 */
	public GLRect buildGLRect(float x,float y,float width,float height);
	/**
	 * 构造一个椭圆
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param axisA 半长轴
	 * @param axisB 半短轴
	 * @param accuracy 精度，即使用点的个数
	 * @return 椭圆对象
	 */
	public GLOval buildGLOval(float x,float y,float axisA,float axisB,int accuracy);
	/**
	 * 创建一个贴图对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param width 宽度
	 * @param height 高度
	 * @param textureName 使用的贴图纹理
	 * @return 贴图对象
	 */
	public GLImage buildGLImage(float x,float y,float width,float height,String textureName);
	/**
	 * 创建一个贴图对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param width 宽度
	 * @param height 高度
	 * @param imgFile 图像文件，使用其文件系统的范式名称作为纹理名称
	 * @return 贴图对象
	 */
	public GLImage buildGLImage(float x,float y,float width,float height,File imgFile);
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
	public GLImage buildGLImage(float x,float y,float width,float height,InputStream imgInputStream,String imgType);
	/**
	 * 构造一个文字区域
	 * @param fontName 字体名-没有自定义字体时可使用BuildInFont的内置字体
	 * @param x 文字区开始位置x
	 * @param y 文字区开始位置y
	 * @param initStr 初始化文字
	 * @return 文字区域对象
	 */
	public GLCharArea buildGLCharLine(String fontName,float x,float y,String initStr);
	/**
	 * 构造一个文字区域
	 * @param fontName 字体名-没有自定义字体时可使用BuildInFont的内置字体
	 * @param x 文字区开始位置x
	 * @param y 文字区开始位置y
	 * @param w 文字区宽度 定义此值后文字超出范围会自动换行
	 * @param h 文字区高度
	 * @param initStr 初始化文字
	 * @return 文字区域对象
	 */
	public GLCharArea buildGLCharLine(String fontName,float x,float y,float w,float h,String initStr);
	public GLTextArea buildGLTextArea(float x,float y,float w,float h);
	/**
	 * 创建一个精灵对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param w 宽度
	 * @param h 高度
	 * @param textureNames 使用的纹理数组
	 * @param rate 间隔频率
	 * @return 精灵对象
	 */
	public GLSprite buildeGLSprite(float x, float y, float w, float h, String[] textureNames, int rate);
	/**
	 * 创建一个精灵对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param w 宽度
	 * @param h 高度
	 * @param textureNames 使用的二维纹理数组
	 * @param rate 间隔频率数组
	 * @return 精灵对象
	 */
	public GLSprite buildeGLSprite(float x,float y,float w,float h,String[][] textureNames,int[] rate);
	/**
	 * 创建一个精灵对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param gifFile Gif文件
	 * @return 精灵对象
	 */
	public GLSprite buildeGLSprite(float x,float y,File gifFile);
	/**
	 * 创建一个精灵对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param gifFile Gif输入流
	 * @return 精灵对象
	 */
	public GLSprite buildeGLSprite(float x,float y,InputStream gifFile);
	
	/**
	 * 创建下一层图像节点
	 * @return GLShapeNode 图像节点
	 */
	public GLShapeNode createNode();
	/**
	 * 创建平级图像节点
	 * @return GLShapeNode 图像节点
	 */
	public GLShapeNode createNewNode();
	/**
	 * 回溯至上一层的节点
	 */
	public void backtrack();
	/**
	 * 完成当前节点树的构造，并准备下一个节点数
	 */
	public void clearNode();
	/**
	 * 设置当前节点
	 * @param node 当前节点
	 */
	public void setNode(GLShapeNode node);
}
