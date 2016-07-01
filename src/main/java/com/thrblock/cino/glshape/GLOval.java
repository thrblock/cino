package com.thrblock.cino.glshape;

/**
 * 椭圆图形对象
 * @author lizepu
 */
public class GLOval extends GLPolygonShape {
	/**
	 * 由顶点数组构造一个椭圆，仅供内部实例化使用
	 * @param points 顶点数组
	 */
	private GLOval(GLPoint[] points) {
		super(points);
	}
	
	/**
	 * 构造一个椭圆对象
	 * @param x 中心坐标x
	 * @param y 中心坐标y
	 * @param axisA 半长轴
	 * @param axisB 半短轴
	 * @param accuracy 精度，即使用点的数量
	 * @return 椭圆图形对象
	 */
	public static GLOval generate(float x,float y,float axisA,float axisB,int accuracy) {
		double thetaAcc = 2*Math.PI/accuracy;
		GLPoint[] ovalpoints = new GLPoint[accuracy];
		for(int i = 0;i < ovalpoints.length;i++) {
			float px = (axisA/2)*(float)Math.cos(thetaAcc*i);
			float py = (axisB/2)*(float)Math.sin(thetaAcc*i);
			ovalpoints[i] = new GLPoint(px,py);
		}
		GLOval result = new GLOval(ovalpoints);
		result.setCentralX(x);
		result.setCentralY(y);
		return result;
	}
}
