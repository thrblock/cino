package com.thrblock.cino.mathutil;

public class CMath {
    /**
     * 以src点为原点，向量(1,0)沿+X轴至+Y轴方向旋转至目标点所需转动的角度
     * @param srcX 原点横坐标
     * @param srcY 原点纵坐标
     * @param tgtX 目标点横坐标
     * @param tgtY 目标点纵坐标
     * @return 所需旋转的方向角，单位弧度
     */
    public static float getQuadrantTheta(float srcX,float srcY,float tgtX,float tgtY) {
        float vecx = tgtX - srcX;
        float vecy = tgtY - srcY;
        double cos = vecx / Math.sqrt(vecx * vecx + vecy * vecy);
        if(cos > 1) {
        	cos = 1;
        } else if(cos < -1) {
        	cos = -1;
        }
        double theta = Math.acos(cos);
        if(tgtY > srcY) {
            return (float)theta;
        } else {
            return (float)(2 * Math.PI - theta);
        }
    }
    
    public static float getDistance(float x1,float y1,float x2,float y2) {
    	float s1 = x2 - x1;
    	float s2 = y2 - y1;
    	return (float)Math.sqrt(s1 * s1 + s2 * s2);
    }
    
    /**
     * 快速倒数开方，虽然在高速的计算机下可能并没有什么乱用<br />
     * 加入此方法出于对其本身的敬意
     * @see <a href="https://zh.wikipedia.org/wiki/%E5%B9%B3%E6%96%B9%E6%A0%B9%E5%80%92%E6%95%B0%E9%80%9F%E7%AE%97%E6%B3%95">Wiki-平方根倒数速算法</a>
     * @param x input
     * @return 1 / √(x)
     */
    public static float invSqrt(float x) {
        float xhalf = 0.5f*x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i>>1);
        x = Float.intBitsToFloat(i);
        x = x*(1.5f - xhalf*x*x);
        return x;
    }
    
    public static double invSqrt(double x) {
        double xhalf = 0.5d*x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i>>1);
        x = Double.longBitsToDouble(i);
        x = x*(1.5d - xhalf*x*x);
        return x;
    }
}
