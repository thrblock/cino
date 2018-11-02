package com.thrblock.cino.shader.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.glanimate.IPureFragment;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.shader.AbstractGLProgram;

/**
 * Shader用 Commons Uniform
 * @author zepu.li
 */
@Component
public class GLCommonUniform {

    @Value("${cino.frame.fps:60}")
    private int fps;
    @Value("${cino.frame.screen.width:800}")
    private int frameSizeW;
    @Value("${cino.frame.screen.height:600}")
    private int frameSizeH;

    public IPureFragment setCommonUniform(AbstractGLProgram program) {
        //时间 单位 秒
        GLUniformFloat iGlobalTime = new GLUniformFloat("iGlobalTime");
        //渲染次数
        GLUniformInt iFrame = new GLUniformInt("iFrame");
        
        //视窗尺寸 单位 像素
        float[] iResolution = new float[2];
        
        IPureFragment res = () -> {
            iGlobalTime.setValue(iGlobalTime.getValue() + 1f / fps);
            iFrame.setValue(iFrame.getValue() + 1);
            iResolution[0] = frameSizeW;
            iResolution[1] = frameSizeH;
        };
        
        program.bindDataAsFloat(iGlobalTime);
        program.bindDataAsFloatVec("iResolution", iResolution);
        program.bindDataAsInt(iFrame);
        return res;
    }
    
    public IPureFragment setCommonUniform(AbstractGLProgram program,GLRect r) {
        //时间 单位 秒
        GLUniformFloat iGlobalTime = new GLUniformFloat("iGlobalTime");
        //渲染次数
        GLUniformInt iFrame = new GLUniformInt("iFrame");
        
        //视窗尺寸 单位 像素
        float[] iResolution = new float[2];
        IPureFragment res = () -> {
            iGlobalTime.setValue(iGlobalTime.getValue() + 1f / fps);
            iFrame.setValue(iFrame.getValue() + 1);
            iResolution[0] = r.getWidth();
            iResolution[1] = r.getHeight();
        };
        
        program.bindDataAsFloat(iGlobalTime);
        program.bindDataAsFloatVec("iResolution", iResolution);
        program.bindDataAsInt(iFrame);
        return res;
    }

    /**
     * 窗体大小变更通知
     * @param w
     * @param h
     */
    @ScreenSizeChangeListener
    public void onchange(int w, int h) {
        this.frameSizeW = w;
        this.frameSizeH = h;
    }
}
