package com.thrblock.cino.gllayer.fbo;

import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.ScreenSizeChangeListener;

@Component
public class GLFrameBufferObjectManager {
    
    public GLFrameBufferObject layerFBO(int index) {
        return null;
    }
    
    public GLFrameBufferObject batchFBO(int startIndex,int endIndex) {
        return null;
    }
    
    public void beforeLayerDraw(int index) {
        
    }
    
    public void afterLayerDraw(int index) {
        
    }
    
    @ScreenSizeChangeListener
    public void screenChange(int w,int h) {
        
    }
}
