package com.thrblock.cino.concept;

public class Polygon extends MultiPoint {
    public Polygon(Point... multi) {
        super(multi);
    }
    
    /**
     * 判断 此多边形与另一多边形是否发生碰撞
     * 
     * @param another 另一个多边形
     * @return 是否发生碰撞的布尔值
     */
    public boolean isSquareableCollide(Polygon another) {
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            if (another.isPointInside(point.getX(), point.getY())) {
                return true;
            }
        }
        for (int i = 0; i < another.points.length; i++) {
            Point point = another.points[i];
            if (isPointInside(point.getX(), point.getY())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isPointInside(float px, float py) {
        int nCount = points.length;
        int nCross = 0;
        for (int i = 0; i < nCount; i++) {
            float px1 = points[i].getX();
            float py1 = points[i].getY();

            float px2 = points[(i + 1) % nCount].getX();
            float py2 = points[(i + 1) % nCount].getY();

            if (contCheck(py, py1, py2)) {
                continue;
            }
            float x = (py - py1) * (px2 - px1) / (py2 - py1) + px1;
            if (x > px) {
                nCross++;
            }
        }
        return nCross % 2 == 1;
    }
    
    private boolean contCheck(float py, float py1, float py2) {
        return Float.compare(py1, py2) == 0 || py < min(py1, py2) || py >= max(py1, py2);
    }
    
    private float min(float f1, float f2) {
        return f1 > f2 ? f2 : f1;
    }

    private float max(float f1, float f2) {
        return f1 > f2 ? f1 : f2;
    }
}
