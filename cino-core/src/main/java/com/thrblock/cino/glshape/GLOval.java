package com.thrblock.cino.glshape;

import java.util.Arrays;

import com.thrblock.cino.concept.Point;
import com.thrblock.cino.concept.Polygon;
import com.thrblock.cino.vec.Vec2;

/**
 * 椭圆图形对象
 * 
 * @author lizepu
 */
public class GLOval extends GLPolygonShape<Polygon> {

    public GLOval(Vec2... points) {
        super(new Polygon(Arrays.stream(points).map(Point::new).toArray(Point[]::new)));
    }

    /**
     * 构造一个椭圆对象
     * 
     * @param x        中心坐标x
     * @param y        中心坐标y
     * @param axisA    长轴
     * @param axisB    短轴
     * @param accuracy 精度，即使用点的数量
     * @return 椭圆图形对象
     */
    public static GLOval generate(float x, float y, float axisA, float axisB, int accuracy) {
        double thetaAcc = 2 * Math.PI / accuracy;
        Vec2[] ovalpoints = new Vec2[accuracy];
        for (int i = 0; i < ovalpoints.length; i++) {
            float px = (axisA / 2) * (float) Math.cos(thetaAcc * i);
            float py = (axisB / 2) * (float) Math.sin(thetaAcc * i);
            ovalpoints[i] = new Vec2(px, py);
        }
        GLOval result = new GLOval(ovalpoints);
        result.setCentralX(x);
        result.setCentralY(y);
        return result;
    }
}
