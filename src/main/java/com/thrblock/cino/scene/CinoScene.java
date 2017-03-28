package com.thrblock.cino.scene;

import com.thrblock.cino.glfragment.IGLFragment;
import com.thrblock.cino.glfragment.IPureFragment;

public class CinoScene extends AbstractCinoScene {
    @Override
    public void sceneEnable() {
        sceneFrag.remuse();
        sceneRoot.show();
        enable();
    }
    
    @Override
    public void sceneDestroy() {
        sceneFrag.destroy();
        sceneRoot.destroy();
        destroy();
    }
    
    @Override
    public void sceneRecover() {
        sceneFrag.remuse();
        sceneRoot.show();
        recover();
    }
    
    @Override
    public void sceneCovered() {
        sceneFrag.pause();
        sceneRoot.hide();
        covered();
    }
    
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
