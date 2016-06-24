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
}
