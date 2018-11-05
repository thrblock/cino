package com.thrblock.cino.glshape;

import com.thrblock.cino.concept.Point;
import com.thrblock.cino.concept.Polygon;

/**
 * 一个三角形图形对象
 * 
 * @author thrblock
 */
public class GLTriangle extends GLPolygonShape<Polygon> {

    /**
     * 构造一个三角形
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
    public GLTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        super(new Polygon(new Point(x1,y1),new Point(x2,y2),new Point(x3,y3)));
    }

}
