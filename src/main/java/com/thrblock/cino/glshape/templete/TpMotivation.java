package com.thrblock.cino.glshape.templete;

import com.thrblock.cino.glshape.builder.GLNode;

public class TpMotivation {
	/**
	 * 向屏幕上方匀速飞出（y轴负向）
	 * @param node GLNode实体
	 * @param speed 飞行速度 像素/帧 大于0
	 * @param yLimit 中心Y坐标极限
	 * @return 代表是否到达极限的布尔值
	 */
	public static boolean flyUp(GLNode node,float speed,float yLimit) {
		float next = node.getCentralY() - speed;
		if(next > yLimit) {
			node.setCentralY(next);
			return false;
		} else {
			node.setCentralY(yLimit);
			return true;
		}
	}
	
	/**
	 * 向屏幕下方匀速飞出（y轴正向）
	 * @param node GLNode实体
	 * @param speed 飞行速度 像素/帧 大于0
	 * @param yLimit 中心Y坐标极限
	 * @return 代表是否到达极限的布尔值
	 */
	public static boolean driveDown(GLNode node,float speed,float yLimit) {
		float next = node.getCentralY() + speed;
		if(next < yLimit) {
			node.setCentralY(next);
			return false;
		} else {
			node.setCentralY(yLimit);
			return true;
		}
	}
	
	/**
	 * 向屏幕左侧匀速飞出（x轴负向）
	 * @param node GLNode实体
	 * @param speed 飞行速度 像素/帧 大于0
	 * @param xLimit 中心X坐标极限
	 * @return 代表是否到达极限的布尔值
	 */
	public static boolean moveLeft(GLNode node,float speed,float xLimit) {
		float next = node.getCentralX() - speed;
		if(next > xLimit) {
			node.setCentralX(next);
			return false;
		} else {
			node.setCentralX(xLimit);
			return true;
		}
	}
	
	/**
	 * 向屏幕右侧匀速飞出（x轴负向）
	 * @param node GLNode实体
	 * @param speed 飞行速度 像素/帧 大于0
	 * @param xLimit 中心X坐标极限
	 * @return 代表是否到达极限的布尔值
	 */
	public static boolean moveRight(GLNode node,float speed,float xLimit) {
		float next = node.getCentralX() + speed;
		if(next < xLimit) {
			node.setCentralX(next);
			return false;
		} else {
			node.setCentralX(xLimit);
			return true;
		}
	}
}
