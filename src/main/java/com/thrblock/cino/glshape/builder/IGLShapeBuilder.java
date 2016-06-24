package com.thrblock.cino.glshape.builder;

import java.io.File;
import java.io.InputStream;

import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLPoint;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.GLSprite;

public interface IGLShapeBuilder {
	public void setLayer(int layerIndex);
	public GLPoint buildGLPoint(float x,float y);
	public GLLine buildGLLine(float x1,float y1,float x2,float y2);
	public GLRect buildGLRect(float x,float y,float width,float height);
	public GLOval buildGLOval(float x,float y,float axisA,float axisB,int accuracy);
	public GLImage buildGLImage(float x,float y,float width,float height,String textureName);
	public GLImage buildGLImage(float x,float y,float width,float height,File imgFile);
	public GLImage buildGLImage(float x,float y,float width,float height,InputStream imgInputStream,String imgType);
	public GLCharArea buildGLCharLine(String fontName,float x,float y,String initStr);
	public GLCharArea buildGLCharLine(String fontName,float x,float y,float w,float h,String initStr);
	
	public GLSprite buildeGLSprite(float x, float y, float w, float h, String[] textureNames, int rate);
	public GLSprite buildeGLSprite(float x,float y,float w,float h,String[][] textureNames,int[] rate);
	public GLSprite buildeGLSprite(float x,float y,File gifFile);
	public GLSprite buildeGLSprite(float x,float y,InputStream gifFile);
	
	public GLShapeNode createNode();
	public GLShapeNode createNewNode();
	public void backtrack();
	public void clearNode();
	public void setNode(GLShapeNode node);
}
