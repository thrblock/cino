package com.thrblock.cino.gllayer;

import com.thrblock.cino.glshape.GLShape;

/**
 * 绘制层容器抽象 - 定义了绘制层管理容器的接口
 * @author lizepu
 *
 */
public interface IGLLayerContainer {
	/**
	 * 根据索引 获得一个绘制层结构
	 * @param index 索引 大于等于0的数字代表层数，高层覆盖底层，不存在的层将会自动创建。<br />
	 * 特别的:使用-1作为索引，会放置图形对象到预定义的最顶层，该层不会被任意其它层覆盖
	 * @return 层次对象
	 */
	public GLLayer getLayer(int index);
	/**
	 * 返回当前的层次数量（不包含预定义的最顶层）
	 * @return 层次数量
	 */
	public int size();
	/**
	 * 将一个图形对象加入交换区，当绘制结束后会加入下次绘制的列表中
	 * @param index 层次索引
	 * @param shape 图形对象
	 */
	public void addShapeToSwap(int index,GLShape shape);
	/**
	 * 将交换区内的图形对象加入绘制队列，一般由绘制同步逻辑进行调用
	 */
	public void swap();
}
