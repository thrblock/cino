package com.thrblock.cino.glshape;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.gltexture.CharTextureGenerater;
import com.thrblock.cino.gltexture.FontGenNode;

/**
 * 文字区域 以单个字符为基础结构进行的字库构建，图形对象，可以定义一个矩形文字区域，进行预定义字体的展示，设置颜色、缩进等样式
 * <br /><img src="data:image/png;base64,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AuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAf12umAAAGEUlEQVQcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAiSAVncPwSGAAAIIIIAAAuYFSADNm1IiAggggAACCCBgtQAJoNXdQ3AIIIAAAggggIB5ARJA86aUiAACCCCAAAIIWC1AAmh19xAcAggggAACCCBgXoAE0LwpJSKAAAIIIIAAAlYLkABa3T0EhwACCCCAAAIImBcgATRvSokIIIAAAggggIDVAv8fDSo30qb1ZSAAAAAASUVORK5CYII="/>
 * @author lizepu
 */
public class GLCharAreaV2 extends GLShape {
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
    
    
    private CharTextureGenerater textureContainer;
    private float widthLimit = -1f;
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
     * @param x 区域左上x坐标
     * @param y 区域左上y坐标
     * @param w 区域宽度
     * @param h 区域高度
     * @param initStr 初始文字显示
     */
    public GLCharAreaV2(CharTextureGenerater textureContainer,Font f, float x, float y,float w,float h, String initStr) {
        this(textureContainer,f, x, y, w, h, initStr.toCharArray());
    }

    /**
     * 构造一个文字区域
     * @param textureContainer 纹理管理容器实例
     * @param fontName 字体名称
     * @param x 区域左上x坐标
     * @param y 区域左上y坐标
     * @param w 区域宽度
     * @param h 区域高度
     * @param charmap 映射字符数组，对字符数组的改变将直接反映在显示上
     */
    public GLCharAreaV2(CharTextureGenerater textureContainer,Font f, float x, float y,float w,float h, char[] charmap) {
        this.textureContainer = textureContainer;
        this.f = f;
        this.str = charmap;
        
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        if(width > 1) {
            setWidthLimit(w);
        }
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
        if(this.width > -1) {
            setWidthLimit(this.width);
        }
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
     * 获得 位置量x，定义为区域的左上定点的横坐标
     * */
    @Override
    public float getX() {
        return x;
    }
    /**
     * {@inheritDoc}<br />
     * 获得 位置量y，定义为区域的左上定点的纵坐标
     * */
    @Override
    public float getY() {
        return y;
    }
    /**
     * {@inheritDoc}<br />
     * 设置 位置量x，定义为区域的左上定点的横坐标
     * */
    @Override
    public void setX(float x) {
        float offset = x - getX();
        setXOffset(offset);
    }
    /**
     * {@inheritDoc}<br />
     * 设置 位置量y，定义为区域的左上定点的纵坐标
     * */
    @Override
    public void setY(float y) {
        float offset = y - getY();
        setYOffset(offset);
    }

    /**
     * {@inheritDoc}<br />
     * 获得 中心位置x，定义为区域的中心位置<br />
     * 注意，当文字不需要对齐功能时，区域的width与height可能与实际文字显示不符
     * */
    @Override
    public float getCentralX() {
        return x + width / 2;
    }

    /**
     * {@inheritDoc}<br />
     * 获得 中心位置y，定义为区域的中心位置<br />
     * 注意，当文字不需要对齐功能时，区域的width与height可能与实际文字显示不符
     * */
    @Override
    public float getCentralY() {
        return y + height / 2;
    }
    /**
     * {@inheritDoc}<br />
     * 设置 中心位置x，定义为区域的中心位置<br />
     * 注意，当文字不需要对齐功能时，区域的width与height可能与实际文字显示不符
     * */
    @Override
    public void setCentralX(float x) {
        float offset = x - getCentralX();
        setXOffset(offset);
    }

    /**
     * {@inheritDoc}<br />
     * 设置 中心位置y，定义为区域的中心位置<br />
     * 注意，当文字不需要对齐功能时，区域的width与height可能与实际文字显示不符
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
     * 获得文字区域的宽度限制
     * @return 宽度
     */
    public float getWidthLimit() {
        return widthLimit;
    }

    /**
     * 设置文字区域的宽度限制
     * @param widthLimit 宽度限制
     */
    public void setWidthLimit(float widthLimit) {
        this.widthLimit = widthLimit;
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
     * 设置文字
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
        this.widthLimit = (int)widthLimit;
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
            if (widthLimit > 0) {
                recalcWithLimit(fg,gl);
            } else {
                recalc(fg,gl);
            }
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
        pre.setX(x);
        pre.setY(y);
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
                } else if (crtWidth + t.getWidth() > widthLimit) {
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

    private void recalc(FontGenNode tx,GL gl) {
        char[] local = this.str;
        GLPoint pre = points.get(0);
        pre.setX(x);
        pre.setY(y);
        if (points.size() < local.length * 4) {
            for (int i = points.size(); i < local.length * 4; i++) {
                GLPoint npt = new GLPoint(pre.getX(), pre.getY());
                points.add(npt);
            }
        }
        for (int i = 0; i < local.length; i++) {
            Texture t = tx.genTexture(gl, local[i]);
            if (t != null) {
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
