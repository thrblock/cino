package com.thrblock.cino.glshape;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.gltexture.GLBufferedTexture;
import com.thrblock.cino.gltexture.GLTexture;
import com.thrblock.cino.util.BufferedImageUtil;

/**
 * 贴图图形对象 是一个矩形的贴图
 * @author lizepu
 *
 */
public class GLImage extends GLRect {
    public static final int EMPTY_WH = 4;
    public static final GLTexture EMPTY_TEXTURE = new GLBufferedTexture(BufferedImageUtil.genEmptyImage(EMPTY_WH, EMPTY_WH));
    private static final int MODE_NORMAL = 0b00;
    private static final int MODE_X_VERT = 0b01;
    private static final int MODE_Y_VERT = 0b10;
    
    private static final float[] alph = {1f,0,0,1f};
    private static final float[] beta = {0,1f,1f,0};
    private static final float[] gama = {1f,1f,0,0};
    private static final float[] zeta = {0,0,1f,1f};
    
    private int mode = MODE_NORMAL;
    private GLTexture gltexture;
    private boolean resize = false;
    /**
     * build a empty image with input size;
     */
    public GLImage() {
        this(0, 0, EMPTY_WH ,EMPTY_WH,EMPTY_TEXTURE);
    }
    /**
     * 构造一个贴图图形对象
     * @param x 中心坐标x
     * @param y 中心坐标y
     * @param width 宽度
     * @param height 高度
     * @param textureName 纹理名称
     */
    public GLImage(float x, float y, float width, float height,GLTexture texture) {
        super(x, y, width, height);
        this.gltexture = texture;
    }
    
    /**
     * 获得 当前纹理
     * @return 纹理
     */
    public GLTexture getTexture() {
        return gltexture;
    }

    /**
     * 设置纹理，新的纹理将会反映在绘图中
     * @param texture 纹理
     */
    public void setTexture(GLTexture texture) {
        setTexture(texture,false);
    }
    
    /**
     * 设置纹理名称，新的名称所关联的纹理将会反映在绘图中
     * @param texture 纹理
     * @param resize 是否根据纹理大小调整宽高
     */
    public void setTexture(GLTexture texture,boolean resize) {
        this.gltexture = texture;
        this.resize = resize;
    }

    /**
     * 将贴图沿x轴翻转（竖直翻转）
     */
    public void vertX() {
        mode = mode | MODE_X_VERT;
    }
    
    /**
     * 将图像沿y轴旋转（水平翻转）
     */
    public void vertY() {
        mode = mode | MODE_Y_VERT;
    }
    
    /**
     * 撤销竖直翻转效果
     */
    public void unVertX() {
        mode = mode &~MODE_X_VERT;
    }
    
    /**
     * 撤销水平翻转效果
     */
    public void unVertY() {
        mode = mode &~MODE_Y_VERT;
    }
    @Override
    public void drawShape(GL2 gl) {
        Texture texture = this.gltexture.getTexture(gl);
        if(texture != null) {
            texture.bind(gl);
            if(resize) {
                setWidth(texture.getWidth());
                setHeight(texture.getHeight());
                resize = false;
            }
        }
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBegin(GL2.GL_QUADS);
        if(mode == MODE_NORMAL) {
            normalTexCoord(gl);
        } else if(mode == MODE_X_VERT) {
            xvertTexCoord(gl);
        } else if(mode == MODE_Y_VERT) {
            yvertTexCoord(gl);
        } else if(mode == (MODE_X_VERT | MODE_Y_VERT)) {
            xyvertTexCoord(gl);
        }
        
        gl.glEnd();
        gl.glBindTexture(GL.GL_TEXTURE_2D,0);
        gl.glDisable(GL.GL_TEXTURE_2D);
    }
    
    private void normalTexCoord(GL2 gl) {
        for(int i = 0;i < 4;i++) {
            gl.glColor4f(points[i].getR(), points[i].getG(),points[i].getB(),points[i].getAlpha());
            gl.glTexCoord2f(beta[i],gama[i]);
            gl.glVertex2f(points[i].getX(),points[i].getY());
        }
    }
    private void xyvertTexCoord(GL2 gl) {
        for(int i = 0;i < 4;i++) {
            gl.glColor4f(points[i].getR(), points[i].getG(),points[i].getB(),points[i].getAlpha());
            gl.glTexCoord2f(alph[i],zeta[i]);
            gl.glVertex2f(points[i].getX(),points[i].getY());
        }
    }
    private void xvertTexCoord(GL2 gl) {
        for(int i = 0;i < 4;i++) {
            gl.glColor4f(points[i].getR(), points[i].getG(),points[i].getB(),points[i].getAlpha());
            gl.glTexCoord2f(beta[i],zeta[i]);
            gl.glVertex2f(points[i].getX(),points[i].getY());
        }
    }
    private void yvertTexCoord(GL2 gl) {
        for(int i = 0;i < 4;i++) {
            gl.glColor4f(points[i].getR(), points[i].getG(),points[i].getB(),points[i].getAlpha());
            gl.glTexCoord2f(alph[i],gama[i]);
            gl.glVertex2f(points[i].getX(),points[i].getY());
        }
    }
}
