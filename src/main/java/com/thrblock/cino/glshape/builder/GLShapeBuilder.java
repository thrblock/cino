package com.thrblock.cino.glshape.builder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
import com.thrblock.cino.glshape.GLSprite;
import com.thrblock.cino.gltexture.IGLTextureContainer;
import com.thrblock.cino.gltexture.IGLTextureContainer.GifMetaData;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class GLShapeBuilder implements IGLShapeBuilder{
    private static final Logger LOG = LoggerFactory.getLogger(GLShapeBuilder.class);
    private int layer = 0;
    private GLShapeNode currentNode = null;
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
        if(currentNode != null) {
            currentNode.addSubNode(point);
        }
        return point;
    }

    @Override
    public GLLine buildGLLine(float x1, float y1, float x2, float y2) {
        GLLine line = new GLLine(x1,y1,x2,y2);
        layerContainer.addShapeToSwap(layer, line);
        if(currentNode != null) {
            currentNode.addSubNode(line);
        }
        return line;
    }

    @Override
    public GLRect buildGLRect(float x, float y, float width, float height) {
        GLRect rect = new GLRect(x,y,width,height);
        layerContainer.addShapeToSwap(layer,rect);
        if(currentNode != null) {
            currentNode.addSubNode(rect);
        }
        return rect;
    }

    @Override
    public GLOval buildGLOval(float x, float y, float axisA, float axisB,
            int accuracy) {
        GLOval oval = GLOval.generate(x, y, axisA, axisB, accuracy);
        layerContainer.addShapeToSwap(layer, oval);
        if(currentNode != null) {
            currentNode.addSubNode(oval);
        }
        return oval;
    }

    @Override
    public GLImage buildGLImage(float x, float y, float width, float height,
            String textureName) {
        GLImage image = new GLImage(textureContainer,x,y,width,height,textureName);
        layerContainer.addShapeToSwap(layer, image);
        if(currentNode != null) {
            currentNode.addSubNode(image);
        }
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
        GLImage image = new GLImage(textureContainer,x,y,width,height,textureName);
        layerContainer.addShapeToSwap(layer, image);
        if(currentNode != null) {
            currentNode.addSubNode(image);
        }
        return image;
    }

    @Override
    public GLImage buildGLImage(float x, float y, float width, float height,
            InputStream imgInputStream,String imgType) {
        String textureName = imgInputStream.toString();
        textureContainer.registerTexture(textureName, imgType, imgInputStream);
        GLImage image = new GLImage(textureContainer,x,y,width,height,textureName);
        layerContainer.addShapeToSwap(layer, image);
        if(currentNode != null) {
            currentNode.addSubNode(image);
        }
        return image;
    }

    @Override
    public GLCharArea buildGLCharLine(String fontName, float x, float y,String initStr) {
        GLCharArea charLine = new GLCharArea(textureContainer,fontName,x,y,1,1,initStr);
        layerContainer.addShapeToSwap(layer, charLine);
        if(currentNode != null) {
            currentNode.addSubNode(charLine);
        }
        return charLine;
    }
    
    @Override
    public GLCharArea buildGLCharLine(String fontName, float x, float y,float w,float h,
            String initStr) {
        GLCharArea charLine = new GLCharArea(textureContainer,fontName,x,y,w,h,initStr);
        layerContainer.addShapeToSwap(layer, charLine);
        if(currentNode != null) {
            currentNode.addSubNode(charLine);
        }
        return charLine;
    }
    
    
    @Override
    public GLSprite buildeGLSprite(float x,float y,float w,float h,String[][] textureNames,int[] rate) {
        GLSprite sprite = new GLSprite(textureContainer, x, y, w, h, textureNames, rate);
        layerContainer.addShapeToSwap(layer, sprite);
        if(currentNode != null) {
            currentNode.addSubNode(sprite);
        }
        return sprite;
    }
    
    @Override
    public GLSprite buildeGLSprite(float x,float y,float w,float h,String[] textureNames,int rate) {
        GLSprite sprite = new GLSprite(textureContainer, x, y, w, h, new String[][]{textureNames},new int[]{rate});
        layerContainer.addShapeToSwap(layer, sprite);
        if(currentNode != null) {
            currentNode.addSubNode(sprite);
        }
        return sprite;
    }
    
    @Override
    public GLSprite buildeGLSprite(float x,float y,File gifFile) {
        String name = String.valueOf(gifFile.hashCode());
        GifMetaData metaData = textureContainer.registerGifAsTexture(name, gifFile);
        int frameSkip = metaData.getRate() * 10 * 60 / 1000;
        return buildeGLSprite(x,y,metaData.getWidths()[0],metaData.getHeights()[0],new String[][]{metaData.getTextureGroup()},new int[]{frameSkip});
    }
    

	@Override
	public GLSprite buildeGLSprite(float x, float y, InputStream gifStream) {
		String name = String.valueOf(gifStream.hashCode());
        GifMetaData metaData = textureContainer.registerGifAsTexture(name, gifStream);
        int frameSkip = metaData.getRate() * 10 * 60 / 1000;
        return buildeGLSprite(x,y,metaData.getWidths()[0],metaData.getHeights()[0],new String[][]{metaData.getTextureGroup()},new int[]{frameSkip});
	}

    @Override
    public GLShapeNode createNewNode() {
        currentNode = new GLShapeNode();
        return currentNode;
    }
    
    @Override
    public GLShapeNode createSubNode() {
        if(currentNode == null) {
            currentNode = new GLShapeNode();
        } else {
            GLShapeNode nNode = new GLShapeNode();
            nNode.setParent(currentNode);
            currentNode.addSubNode(nNode);
            currentNode = nNode;
        }
        return currentNode;
    }

    @Override
    public void clearNode() {
        currentNode = null;
    }

    @Override
    public void setNode(GLShapeNode node) {
        currentNode = node;
    }

    @Override
    public void backtrack() {
        if(currentNode != null) {
            currentNode = currentNode.getParent();
        }
    }
}
