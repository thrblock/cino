package com.thrblock.cino.fbo;

import java.util.ArrayDeque;
import java.util.Deque;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL2;
import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.gllifecycle.CycleArray;

@Component
public class GLFrameBufferObjectManager {
    @Value("${cino.frame.screen.width:800}")
    private int frameSizeW;
    @Value("${cino.frame.screen.height:600}")
    private int frameSizeH;
    @Value("${cino.frame.flexmode:0}")
    private int flexmode;

    private Deque<GLFrameBufferObject[]> stack = new ArrayDeque<>();
    private CycleArray<GLFrameBufferObject> fboCycle = new CycleArray<>(GLFrameBufferObject[]::new);

    private CycleArray<GLFrameBufferObject> globalFboCycle = new CycleArray<>(GLFrameBufferObject[]::new);
    
    public GLFrameBufferObject generateFBO() {
        GLFrameBufferObject result = new GLFrameBufferObject(frameSizeW, frameSizeH, flexmode);
        fboCycle.safeAdd(result);
        return result;
    }
    
    public GLFrameBufferObject generateGlobalFBO() {
        GLFrameBufferObject result = generateFBO();
        globalFboCycle.safeAdd(result);
        return result;
    }

    public void destroyFBO(GLFrameBufferObject fbo) {
        fboCycle.safeRemove(fbo);
        globalFboCycle.safeRemove(fbo);
    }

    /**
     * 通知 屏幕尺寸变更
     * 
     * @param w
     * @param h
     */
    @ScreenSizeChangeListener
    public void noticeScreenChange(int w, int h) {
        this.frameSizeW = w;
        this.frameSizeH = h;
        fboCycle.safeUpdate(fbo -> fbo.resize(w, h));
    }

    public void bindGlobal(GL2 gl2) {
        
    }
    
    public void unbindGlobal(GL2 gl2) {
        
    }
    
    public void bind(GLFrameBufferObject[] fboArr, GL2 gl2) {
        if (fboArr.length > 0) {
            stack.push(fboArr);
            fboArr[fboArr.length - 1].bindFBO(gl2, true);
        }
    }

    public void unBind(GLFrameBufferObject[] fbos, GL2 gl2) {
        if (stack.isEmpty() || fbos.length == 0 || stack.peek() != fbos) {
            return;
        }
        GLFrameBufferObject[] fboArr = stack.pop();
        for (int i = fboArr.length - 1; i >= 0; i--) {
            GLFrameBufferObject crt = fboArr[i];
            if (i - 1 >= 0) {
                GLFrameBufferObject next = fboArr[i - 1];
                next.bindFBO(gl2, true);
            } else if (!stack.isEmpty()) {
                GLFrameBufferObject[] arr = stack.peek();
                GLFrameBufferObject next = arr[arr.length - 1];
                next.bindFBO(gl2, true);
            } else {
                crt.unBindFBO(gl2);
            }
            crt.drawAsTexture(gl2);
        }
    }
}
