package com.thrblock.cino.glshape;

import java.awt.Color;
import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.glfont.GLFontTexture;
import com.thrblock.cino.gltexture.IGLTextureContainer;

public class GLCharArea extends GLShape {
    public static final int HOR_LEFT = 0;
    public static final int HOR_RIGHT = 1;
    public static final int HOR_CENTRAL = 2;
    
    public static final int VER_UP = 0;
    public static final int VER_BOTTOM = 1;
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
    
    
    private IGLTextureContainer textureContainer;
    private float widthLimit = -1f;
    private char[] str;
    private String fontName;
    private final ArrayList<GLPoint> points;
    private boolean recalcPoint = true;
    private float r = 1.0f;
    private float g = 1.0f;
    private float b = 1.0f;
    private float alpha = 1.0f;
    
    private float theta = 0;

    public GLCharArea(IGLTextureContainer textureContainer,String fontName, float x, float y,float w,float h, String initStr) {
        this(textureContainer,fontName, x, y, w, h, initStr.toCharArray());
    }

    public GLCharArea(IGLTextureContainer textureContainer,String fontName, float x, float y,float w,float h, char[] charmap) {
        this.textureContainer = textureContainer;
        this.fontName = fontName;
        this.str = charmap;
        
        this.x = (int)x;
        this.y = (int)y;
        this.width = (int)w;
        this.height = (int)h;
        
        points = new ArrayList<>(charmap.length * 4 > 16 ? charmap.length * 4 : 16);
        points.add(new GLPoint(this.x, this.y));
    }

    public void setHorAlia(int horAlia) {
        this.horAlia = horAlia;
        resize();
    }
    
    public void setVerAlia(int verAlia) {
        this.verAlia = verAlia;
        resize();
    }
    
    /**
     * 设置区域宽度，区域宽度将作为文字的水平对齐标准
     * @param width 区域宽度
     */
    public void setWidth(float width) {
        this.width = (int)width;
        resize();
    }
    
    /**
     * 设置区域高度，区域宽度将作为文字的垂直对齐标准
     * @param height 区域高度
     */
    public void setHeight(float height) {
        this.height = (int)height;
        resize();
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
        float offset = (int)x - getX();
        setXOffset(offset);
    }
    /**
     * {@inheritDoc}<br />
     * 设置 位置量y，定义为区域的左上定点的纵坐标
     * */
    @Override
    public void setY(float y) {
        float offset = (int)y - getY();
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
		float offset = (int)x - getCentralX();
		setXOffset(offset);
	}

	/**
     * {@inheritDoc}<br />
     * 设置 中心位置y，定义为区域的中心位置<br />
     * 注意，当文字不需要对齐功能时，区域的width与height可能与实际文字显示不符
     * */
	@Override
	public void setCentralY(float y) {
		float offset = (int)y - getCentralY();
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
	
    public float getWidthLimit() {
        return widthLimit;
    }

    public void setWidthLimit(float widthLimit) {
        this.widthLimit = (int)widthLimit;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
    }

    /**
     * 设置文字<br />
     * 注意，重置文字后旋转特性将丢失
     * @param str 文字字符串
     */
    public void setFontString(String str) {
        this.str = str.toCharArray();
        resize();
    }

    /**
     * 设置文字
     * 注意，重置文字后旋转特性将丢失
     * @param str 文字字符数组
     */
    public void setFontString(char[] str) {
        this.str = str;
        resize();
    }

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

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

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
        x += (int)offset;
        setPointXOffset((int)offset);
    }
    
    private void setPointXOffset(float offset) {
        for (GLPoint point : points) {
            point.setXOffset((int)offset);
        }
    }
    /**
     * {@inheritDoc}<br />
     * 设置 纵向偏移量
     * */
    @Override
    public void setYOffset(float offset) {
        y += (int)offset;
        setPointYOffset((int)offset);
    }
    
    private void setPointYOffset(float offset) {
        for (GLPoint point : points) {
            point.setYOffset((int)offset);
        }
    }

    @Override
    public void drawShape(GL2 gl) {
        char[] local = this.str;
        GLFontTexture tx = textureContainer.getGLFontTexture(fontName);
        if (tx == null) {
            return;
        }
        if (recalcPoint) {
            if (widthLimit > 0) {
                recalcWithLimit(tx);
            } else {
                recalc(tx);
            }
            recalcPoint = false;
            recalcOffset();
        }
        gl.glColor4f(r, g, b, alpha);
        for (int i = 0; i < local.length; i++) {
            if (local[i] == '\n') {
                continue;
            }
            Texture t = tx.getTexture(local[i]);
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
    }

    private void recalcOffset() {
        float minDx = points.get(0).getX();
        float maxDx = minDx;
        
        float minDy = points.get(0).getY();
        float maxDy = minDy;
        for(GLPoint p:points) {
            if(p.getX() > maxDx) {
                maxDx = p.getX();
            }
            if(p.getY() > maxDy) {
                maxDy = p.getY();
            }
        }
        
        float rw = maxDx - minDx;
        float rh = maxDy - minDy;
        
        float offsetX,offsetY;
        
        if(horAlia == HOR_LEFT) {
            offsetX = 0;
        } else if(horAlia == HOR_LEFT) {
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
        setPointYOffset(offsetY);
    }

    private void recalcWithLimit(GLFontTexture tx) {
        char[] local = this.str;
        GLPoint pre = points.get(0);
        if (points.size() < local.length * 4) {
            for (int i = points.size(); i < local.length * 4; i++) {
                GLPoint npt = new GLPoint(pre.getX(), pre.getY());
                points.add(npt);
            }
        }
        GLPoint linePoint = points.get(3);
        int crtWidth = 0;
        for (int i = 0; i < local.length; i++) {
            Texture t = tx.getTexture(local[i]);
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

    private void recalc(GLFontTexture tx) {
        char[] local = this.str;
        GLPoint pre = points.get(0);
        if (points.size() < local.length * 4) {
            for (int i = points.size(); i < local.length * 4; i++) {
                GLPoint npt = new GLPoint(pre.getX(), pre.getY());
                points.add(npt);
            }
        }
        for (int i = 0; i < local.length; i++) {
            Texture t = tx.getTexture(local[i]);
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
        p2.setY(pre.getY() + t.getHeight());

        GLPoint p3 = points.get(i * 4 + 3);
        p3.setX(pre.getX());
        p3.setY(pre.getY() + t.getHeight());
    }
}
