package com.thrblock.cino.glshape;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.gltexture.GLCharTextureGenerater;
import com.thrblock.cino.gltexture.FontGenNode;

/**
 * 文字区域 以单个字符为基础结构进行的字库构建，图形对象，可以定义一个矩形文字区域，进行预定义字体的展示，设置颜色、缩进等样式
 * @author lizepu
 */
public class GLCharArea extends GLShape {
    /**
     * 左对齐
     */
    public static final int HOR_LEFT = 0;
    /**
     * 右对齐
     */
    public static final int HOR_RIGHT = 1;
    /**
     * 水平居中
     */
    public static final int HOR_CENTRAL = 2;
    
    /**
     * 顶部对齐
     */
    public static final int VER_UP = 0;
    /**
     * 底部对齐
     */
    public static final int VER_BOTTOM = 1;
    /**
     * 中部对齐
     */
    public static final int VER_CENTRAL = 2;
    
    /**
     * 垂直对齐方式，默认为靠上
     */
    private int verAlia = VER_UP;
    /**
     * 水平对齐方式，默认为左对齐
     */
    private int horAlia = HOR_LEFT;
    private float x;
    private float y;
    private float width;
    private float height;
    
    
    private GLCharTextureGenerater textureContainer;
    private char[] str;
    private Font f;
    private final ArrayList<GLPoint> points;
    private boolean recalcPoint = true;
    private boolean recalcOffset = true;
    private float r = 1.0f;
    private float g = 1.0f;
    private float b = 1.0f;
    private float alpha = 1.0f;
    
    private float theta = 0;

    /**
     * 构造一个文字区域
     * @param textureContainer 纹理管理容器实例
     * @param fontName 字体名称
     * @param x 区域中心坐标
     * @param y 区域中心坐标
     * @param w 区域宽度
     * @param h 区域高度
     * @param initStr 初始文字显示
     */
    public GLCharArea(GLCharTextureGenerater textureContainer,Font f, float x, float y,float w,float h, String initStr) {
        this(textureContainer,f, x, y, w, h, initStr.toCharArray());
    }

    /**
     * 构造一个文字区域
     * @param textureContainer 纹理管理容器实例
     * @param fontName 字体名称
     * @param x 区域中心坐标
     * @param y 区域中心坐标
     * @param w 区域宽度
     * @param h 区域高度
     * @param charmap 映射字符数组，对字符数组的改变将直接反映在显示上
     */
    public GLCharArea(GLCharTextureGenerater textureContainer,Font f, float x, float y,float w,float h, char[] charmap) {
        this.textureContainer = textureContainer;
        this.f = f;
        this.str = charmap;
        
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        points = new ArrayList<>(charmap.length * 4 > 16 ? charmap.length * 4 : 16);
        points.add(new GLPoint(this.x, this.y));
    }

    /**
     * 设置水平方式
     * @param horAlia 水平对齐方式
     * @see #HOR_LEFT
     * @see #HOR_CENTRAL
     * @see #HOR_RIGHT
     */
    public void setHorAlia(int horAlia) {
        this.horAlia = horAlia;
        reOffset();
    }
    
    /**
     * 设置垂直对齐方式
     * @param verAlia 垂直对齐方式
     * @see #VER_BOTTOM
     * @see #VER_CENTRAL
     * @see #VER_UP
     */
    public void setVerAlia(int verAlia) {
        this.verAlia = verAlia;
        reOffset();
    }
    
    /**
     * 设置区域宽度，区域宽度将作为文字的水平对齐标准
     * @param width 区域宽度
     */
    public void setWidth(float width) {
        this.width = width;
        reOffset();
    }
    
    /**
     * 设置区域高度，区域宽度将作为文字的垂直对齐标准
     * @param height 区域高度
     */
    public void setHeight(float height) {
        this.height = height;
        reOffset();
    }
    /**
     * {@inheritDoc}<br />
     * 获得 位置量x，定义为区域的中心横坐标
     * */
    @Override
    public float getX() {
        return x;
    }
    /**
     * {@inheritDoc}<br />
     * 获得 位置量y，定义为区域的中心纵坐标
     * */
    @Override
    public float getY() {
        return y;
    }
    /**
     * {@inheritDoc}<br />
     * 设置 位置量x，定义为区域的中心横坐标
     * */
    @Override
    public void setX(float x) {
        float offset = x - getX();
        setXOffset(offset);
    }
    /**
     * {@inheritDoc}<br />
     * 设置 位置量y，定义为区域的中心纵坐标
     * */
    @Override
    public void setY(float y) {
        float offset = y - getY();
        setYOffset(offset);
    }

    /**
     * {@inheritDoc}<br />
     * 获得 中心位置x，定义为区域的中心位置<br />
     * */
    @Override
    public float getCentralX() {
        return x;
    }

    /**
     * {@inheritDoc}<br />
     * 获得 中心位置y，定义为区域的中心位置<br />
     * */
    @Override
    public float getCentralY() {
        return y;
    }
    /**
     * {@inheritDoc}<br />
     * 设置 中心位置x，定义为区域的中心位置<br />
     * */
    @Override
    public void setCentralX(float x) {
        float offset = x - getCentralX();
        setXOffset(offset);
    }

    /**
     * {@inheritDoc}<br />
     * 设置 中心位置y，定义为区域的中心位置<br />
     * */
    @Override
    public void setCentralY(float y) {
        float offset = y - getCentralY();
        setYOffset(offset);
    }
    /**
     * {@inheritDoc}<br />
     * 获得 通道参数，文字区域不提供对指定点的alpha设定
     * */
    @Override
    public float getAlpha() {
        return alpha;
    }
    /**
     * {@inheritDoc}<br />
     * 获得 旋转角度
     * */
    @Override
    public float getTheta() {
        return theta;
    }
    /**
     * {@inheritDoc}<br />
     * 设置 旋转角度<br />
     * 注意，低分辨率下扭曲可能影响文字质量
     * */
    @Override
    public void setTheta(float dstTheta) {
        setTheta(dstTheta,getCentralX(),getCentralY());
    }
    /**
     * {@inheritDoc}<br />
     * 设置 旋转角度，以指定轴x,y<br />
     * 注意，低分辨率下扭曲可能影响文字质量
     * */
    @Override
    public void setTheta(float dstTheta, float x, float y) {
        float offset = dstTheta - this.theta;
        float nx = revolveX(this.x,this.y,x,y,offset);
        float ny = revolveY(this.x,this.y,x,y,offset);
        for (GLPoint point : points) {
            float dx = revolveX(point.getX(),point.getY(),x,y,offset);
            float dy = revolveY(point.getX(),point.getY(),x,y,offset);
            point.setX(dx);
            point.setY(dy);
        }
        this.theta = dstTheta;
        this.x = nx;
        this.y = ny;
    }
    


    /**
     * 设置字体库使用名称
     * @param fontName 字体库名称
     */
    public void setFontName(Font f) {
        this.f = f;
    }

    /**
     * 获得当前字体库名称
     * @return 字体库名称
     */
    public Font getFont() {
        return f;
    }

    /**
     * 设置文字<br />
     * 注意，重置文字后旋转特性将丢失
     * @param str 文字字符串
     */
    public void setFontString(String str) {
        setFontString(str.toCharArray());
    }

    /**
     * 使用char数组设置文字，外部对此数组的修改将直接反映在显示上
     * 注意，重置文字后旋转特性将丢失
     * @param str 文字字符数组
     */
    public void setFontString(char[] str) {
        this.str = str;
        resize();
        reOffset();
    }

    /**
     * 获得当前文字内容
     * @return 当前文字内容
     */
    public String getFontString() {
        return new String(str);
    }

    /**
     * 重新计算文字点，角度丢失
     */
    public void resize() {
        this.theta = 0;
        recalcPoint = true;
    }
    
    /**
     * 全部坐标计算数据向下取整，防止因浮点数引起的纹理走样
     */
    public void floor() {
        this.x = (int)x;
        this.y = (int)y;
        this.width = (int)width;
        this.height = (int)height;
        recalcPoint = true;
        reOffset();
    }
    
    private void reOffset() {
        recalcOffset = true;
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * 设置文字颜色
     * @param c 文字颜色
     */
    public void setColor(Color c) {
        this.r = c.getRed() / 255f;
        this.g = c.getGreen() / 255f;
        this.b = c.getBlue() / 255f;
    }
    /**
     * {@inheritDoc}<br />
     * 设置 横向偏移量
     * */
    @Override
    public void setXOffset(float offset) {
        x += offset;
        setPointXOffset(offset);
    }
    
    private void setPointXOffset(float offset) {
        for (GLPoint point : points) {
            point.setXOffset(offset);
        }
    }
    /**
     * {@inheritDoc}<br />
     * 设置 纵向偏移量
     * */
    @Override
    public void setYOffset(float offset) {
        y += offset;
        setPointYOffset(offset);
    }
    
    private void setPointYOffset(float offset) {
        for (GLPoint point : points) {
            point.setYOffset(offset);
        }
    }

    @Override
    public void drawShape(GL2 gl) {
        char[] local = this.str;
        FontGenNode fg = textureContainer.getFontGenNode(f);
        if (fg == null) {
            return;
        }
        if (recalcPoint) {
            recalcWithLimit(fg,gl);
            recalcPoint = false;
        }
        if(recalcOffset) {
            recalcOffset();
            recalcOffset = false;
        }
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glColor4f(r, g, b, alpha);
        for (int i = 0; i < local.length; i++) {
            if (local[i] == '\n') {
                continue;
            }
            Texture t = fg.genTexture(gl, local[i]);
            t.bind(gl);
            gl.glBegin(GL2.GL_QUADS);
            GLPoint p0 = points.get(i * 4 + 0);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex2f(p0.getX(), p0.getY());

            GLPoint p1 = points.get(i * 4 + 1);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex2f(p1.getX(), p1.getY());

            GLPoint p2 = points.get(i * 4 + 2);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex2f(p2.getX(), p2.getY());

            GLPoint p3 = points.get(i * 4 + 3);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex2f(p3.getX(), p3.getY());

            gl.glEnd();
            gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
        }
        gl.glDisable(GL.GL_TEXTURE_2D);
    }

    private void recalcOffset() {
        float minDx = points.get(0).getX();
        float maxDx = minDx;
        
        float maxDy = points.get(0).getY();
        float minDy = maxDy;
        for(GLPoint p:points) {
            if(p.getX() > maxDx) {
                maxDx = p.getX();
            }
            if(p.getY() < minDy) {
                minDy = p.getY();
            }
        }
        
        float rw = maxDx - minDx;
        float rh = maxDy - minDy;
        
        float offsetX;
        float offsetY;
        
        if(horAlia == HOR_LEFT) {
            offsetX = 0;
        } else if(horAlia == HOR_RIGHT) {
            offsetX = width - rw;
        } else {
            offsetX = (width - rw) / 2;
        }
        
        if(verAlia == VER_UP) {
            offsetY = 0;
        } else if(verAlia == VER_BOTTOM) {
            offsetY = height - rh;
        } else {
            offsetY = (height - rh) / 2;
        }
        setPointXOffset(offsetX);
        setPointYOffset(-offsetY);
    }

    private void recalcWithLimit(FontGenNode tx,GL gl) {
        char[] local = this.str;
        GLPoint pre = points.get(0);
        pre.setX(x - width / 2);
        pre.setY(y + height / 2);
        if (points.size() < local.length * 4) {
            for (int i = points.size(); i < local.length * 4; i++) {
                GLPoint npt = new GLPoint(pre.getX(), pre.getY());
                points.add(npt);
            }
        }
        GLPoint linePoint = points.get(3);
        int crtWidth = 0;
        for (int i = 0; i < local.length; i++) {
            Texture t = tx.genTexture(gl, local[i]);
            if (t != null) {
                if (local[i] == '\n') {
                    crtWidth = 0;
                    pre = linePoint;
                    linePoint = points.get(i * 4 + 3);
                } else if (crtWidth + t.getWidth() > width) {
                    crtWidth = t.getWidth();
                    pre = linePoint;
                    linePoint = points.get(i * 4 + 3);
                } else {
                    crtWidth += t.getWidth();
                }
                positionPoints(pre, i, t, local);
            }
            pre = points.get(i * 4 + 1);
        }
    }

    private void positionPoints(GLPoint pre, int i, Texture t, char[] local) {
        int w = local[i] == '\n' ? 0 : t.getWidth();
        GLPoint p0 = points.get(i * 4 + 0);
        p0.setX(pre.getX());
        p0.setY(pre.getY());

        GLPoint p1 = points.get(i * 4 + 1);
        p1.setX(pre.getX() + w);
        p1.setY(pre.getY());

        GLPoint p2 = points.get(i * 4 + 2);
        p2.setX(pre.getX() + w);
        p2.setY(pre.getY() - t.getHeight());

        GLPoint p3 = points.get(i * 4 + 3);
        p3.setX(pre.getX());
        p3.setY(pre.getY() - t.getHeight());
    }
}
