package com.thrblock.cino.glshape.templete;

import com.thrblock.cino.glshape.builder.GLNode;
import com.thrblock.cino.mathutil.CMath;

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
	
	/**
	 * 以匀速移动节点
	 * @param node 要移动的节点
	 * @param dstX 目标点x
	 * @param dstY 目标点y
	 * @param speed 速度 单位 像素/帧 横正
	 * @return 是否移动到位置的布尔值
	 */
	public static boolean moveTo(GLNode node,float dstX,float dstY,float speed) {
		float dist = CMath.getDistance(node.getCentralX(),node.getCentralY(),dstX,dstY);
		if(dist > speed) {
			float theta = CMath.getQuadrantTheta(node.getCentralX(),node.getCentralY(),dstX, dstY);
			node.setXOffset((float)Math.cos(theta) * speed);
			node.setYOffset((float)Math.sin(theta) * speed);
			return false;
		} else {
			node.setCentralX(dstX);
			node.setCentralY(dstY);
			return true;
		}
	}
	
	public static boolean moveToSmoothly(GLNode node,float dstX,float dstY,float speed) {
		return moveToSmoothly(node, dstX, dstY, speed, speed * 5, speed * 0.2f);
	}
	
	public static boolean moveToSmoothly(GLNode node,float dstX,float dstY,float speed,float smoothRange,float speedMini) {
		float dist = CMath.getDistance(node.getCentralX(),node.getCentralY(),dstX,dstY);
		float spd;
		if(dist < smoothRange) {
			spd = dist * speed / smoothRange;
			if(spd <= speedMini) {
				spd = speedMini;
			}
		} else {
			spd = speed;
		}
		if(dist > speedMini) {
			float theta = CMath.getQuadrantTheta(node.getCentralX(),node.getCentralY(),dstX, dstY);
			node.setXOffset((float)Math.cos(theta) * spd);
			node.setYOffset((float)Math.sin(theta) * spd);
			return false;
		} else {
			node.setCentralX(dstX);
			node.setCentralY(dstY);
			return true;
		}
	}
}
