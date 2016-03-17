package com.thrblock.cino.glshape.builder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.thrblock.cino.gllayer.IGLLayerContainer;
import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.IGLTextureContainer;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class GLShapeBuilder implements IGLShapeBuilder{
    private static final Logger LOG = LogManager.getLogger(GLShapeBuilder.class);
    private int layer = 0;
    @Autowired
    IGLLayerContainer layerContainer;
    
    @Autowired
    IGLTextureContainer textureContainer;
    
    @Override
    public void setLayer(int layerIndex) {
        this.layer = layerIndex;
    }

    @Override
    public GLPoint buildGLPoint(float x, float y) {
        GLPoint point = new GLPoint(x,y);
        layerContainer.addShapeToSwap(layer, point);
        return point;
    }

    @Override
    public GLLine buildGLLine(float x1, float y1, float x2, float y2) {
        GLLine line = new GLLine(x1,y1,x2,y2);
        layerContainer.addShapeToSwap(layer, line);
        return line;
    }

    @Override
    public GLRect buildGLRect(float x, float y, float width, float height) {
        GLRect rect = new GLRect(x,y,width,height);
        layerContainer.addShapeToSwap(layer,rect);
        return rect;
    }

    @Override
    public GLOval buildGLOval(float x, float y, float axisA, float axisB,
            int accuracy) {
        GLOval oval = GLOval.generate(x, y, axisA, axisB, accuracy);
        layerContainer.addShapeToSwap(layer, oval);
        return oval;
    }

    @Override
    public GLImage buildGLImage(float x, float y, float width, float height,
            String textureName) {
        GLImage image = new GLImage(x,y,width,height,textureName);
        layerContainer.addShapeToSwap(layer, image);
        return image;
    }

    @Override
    public GLImage buildGLImage(float x, float y, float width, float height,
            File imgFile) {
        String textureName;
        try {
            textureName = imgFile.getCanonicalPath();
        } catch (IOException e) {
            textureName = imgFile.getAbsolutePath();
            LOG.warn("IOException in build GLImage:" + e);
            LOG.warn("error in get canonical name of file:" + imgFile + ", the texture may be unable to reuse");
        }
        textureContainer.registerTexture(textureName, imgFile);
        GLImage image = new GLImage(x,y,width,height,textureName);
        layerContainer.addShapeToSwap(layer, image);
        return image;
    }

    @Override
    public GLImage buildGLImage(float x, float y, float width, float height,
            InputStream imgInputStream,String imgType) {
        String textureName = imgInputStream.toString();
        textureContainer.registerTexture(textureName, imgType, imgInputStream);
        GLImage image = new GLImage(x,y,width,height,textureName);
        layerContainer.addShapeToSwap(layer, image);
        return image;
    }

    @Override
    public GLCharArea buildGLCharLine(String fontName, float x, float y,
            String initStr) {
        GLCharArea charLine = new GLCharArea(fontName,x,y,initStr);
        layerContainer.addShapeToSwap(layer, charLine);
        return charLine;
    }

}
