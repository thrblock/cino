package com.thrblock.cino.glpostbuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.glfragment.IGLFragment;
import com.thrblock.cino.glfragment.IGLFragmentContainer;
import com.thrblock.cino.shader.AbstractGLProgram;
import com.thrblock.cino.shader.data.GLUniformFloat;
import com.thrblock.cino.shader.data.GLUniformInt;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GLCommonUniform {
    @Autowired
    private IGLFragmentContainer rootContainer;
    
    @Value("${cino.frame.fps:60}")
    private int fps;
    @Value("${cino.frame.screen.width:800}")
    private int frameSizeW;
    @Value("${cino.frame.screen.height:600}")
    private int frameSizeH;
    /**
     * 全局时间 单位 秒
     */
    private GLUniformFloat iGlobalTime = new GLUniformFloat("iGlobalTime");
    /**
     * 渲染次数
     */
    private GLUniformInt iFrame = new GLUniformInt("iFrame");
    /**
     * 视窗尺寸 单位 像素
     */
    private float[] iResolution = new float[2];
    
    public void setCommonUniform(AbstractGLProgram program) {
        iResolution[0] = frameSizeW;
        iResolution[1] = frameSizeH;
        program.bindDataAsFloatVec("iResolution", iResolution);
        program.bindDataAsFloat(iGlobalTime);
        program.bindDataAsInt(iFrame);
        rootContainer.addFragment(new IGLFragment(){
            @Override
            public void fragment() {
                iGlobalTime.setValue(iGlobalTime.getValue() + 1f/fps);
                iFrame.setValue(iFrame.getValue() + 1);
            }

            @Override
            public boolean isEnable() {
                return true;
            }

            @Override
            public boolean isDestory() {
                return false;
            }
            
        });
    }
    
    @ScreenSizeChangeListener
    public void onchange(int w,int h) {
        iResolution[0] = w;
        iResolution[1] = h;
    }
}
