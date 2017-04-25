package com.thrblock.cino.scene;

import com.thrblock.cino.glfragment.IGLFragment;
import com.thrblock.cino.glfragment.IPureFragment;

public class CinoScene extends AbstractCinoScene {
    @Override
    public final void sceneEnable() {
        sceneFrag.remuse();
        sceneRoot.show();
        enable();
    }
    
    @Override
    public final void sceneDestroy() {
        sceneFrag.destroy();
        sceneRoot.destroy();
        destroy();
    }
    
    @Override
    public final void sceneRecover() {
        sceneFrag.remuse();
        sceneRoot.show();
        recover();
    }
    
    @Override
    public final void sceneCovered() {
        sceneFrag.pause();
        sceneRoot.hide();
        covered();
    }
    
    /**
     * 构造一个片段逻辑 起开关伴随场景自动完成
     * @param frag
     */
    public void auto(IPureFragment frag) {
        sceneFrag.addFragment(new IGLFragment(){
            @Override
            public void fragment() {
                frag.fragment();
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
}
