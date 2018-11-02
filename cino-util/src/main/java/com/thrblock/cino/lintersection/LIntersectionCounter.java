package com.thrblock.cino.lintersection;

import com.thrblock.cino.util.math.CMath;
import com.thrblock.cino.util.structure.CrudeLinkedList;

/**
 * 线段交叉计算
 * @author zepu.li
 */
public class LIntersectionCounter<T extends AbstractVector> {
    private CrudeLinkedList<T> vectors = new CrudeLinkedList<>();
    private CrudeLinkedList<T>.CrudeIter vectorIter = vectors.genCrudeIter();

    /**
     * 添加一个线段到检测池中
     * @param line
     */
    public void addLine(T line) {
        vectors.add(line);
    }

    /**
     * 判断给定的线段是否与检测池中的线段相交
     * @param velocity 给定线段
     * @param resultHolder 结果集
     */
    public void intersection(AbstractVector velocity, InstersectionResultHolder<T> resultHolder) {
        float minDistance = Float.MAX_VALUE;
        T ref = null;
        boolean found = false;
        float minx = -1;
        float miny = -1;
        while(vectorIter.hasNext()) {
            T crt = vectorIter.next();
            vectorTest(crt,velocity,resultHolder);
            if(resultHolder.isIntersect()) {
                found = true;
                float distance = CMath.getDistance(resultHolder.getX(),resultHolder.getY(), velocity.getStartX(),velocity.getStartY());
                if(distance < minDistance) {
                    minDistance = distance;
                    minx = resultHolder.getX();
                    miny = resultHolder.getY();
                    ref = crt;
                }
            }
        }
        vectorIter.reset();
        resultHolder.setX(minx);
        resultHolder.setY(miny);
        resultHolder.setIntersect(found);
        resultHolder.setInsterLine(ref);
    }

    private void vectorTest(T crt, AbstractVector velocity, InstersectionResultHolder<T> resultHolder) {
        // crt向量ab velocity向量cd
        float ax = crt.getStartX();
        float ay = crt.getStartY();
        float bx = crt.getEndX();
        float by = crt.getEndY();
        float cx = velocity.getStartX();
        float cy = velocity.getStartY();
        float dx = velocity.getEndX();
        float dy = velocity.getEndY();
        // 三角形abc 面积的2倍
        float sabc = (ax - cx) * (by - cy) - (ay - cy) * (bx - cx);
        // 三角形abd 面积的2倍
        float sabd = (ax - dx) * (by - dy) - (ay - dy) * (bx - dx);
        // 面积符号相同则两点在线段同侧,不相交 (对点在线段上的情况,本例当作不相交处理)
        if (sabc * sabd >= 0) {
            resultHolder.setIntersect(false);
            return;
        }

        // 三角形cda 面积的2倍
        float scda = (cx - ax) * (dy - ay) - (cy - ay) * (dx - ax);
        // 三角形cdb 面积的2倍
        // 注意: 这里有一个小优化.不需要再用公式计算面积,而是通过已知的三个面积加减得出.
        float scdb = scda + sabc - sabd;
        if (scda * scdb >= 0) {
            resultHolder.setIntersect(false);
            return;
        }

        // 计算交点坐标
        float t = scda / (sabd - sabc);
        float detax = t * (bx - ax);
        float detay = t * (by - ay);
        resultHolder.setIntersect(true);
        resultHolder.setInsterLine(crt);
        resultHolder.setX(ax + detax);
        resultHolder.setY(ay + detay);
    }

}
