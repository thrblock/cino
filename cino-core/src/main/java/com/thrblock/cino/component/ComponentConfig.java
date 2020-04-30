package com.thrblock.cino.component;

import com.thrblock.cino.annotation.CinoComponentConfiguration;
import com.thrblock.cino.annotation.CinoSubComponentConfiguration;

import lombok.Data;

@Data
public class ComponentConfig {
    private boolean autoShowHide = true;
    private boolean autoAsSub = true;
    private boolean inheritAnimation = true;
    private boolean inheritLayer = true;
    private boolean inheritShapeNode = true;
    
    public static ComponentConfig fromAnnotation(CinoComponentConfiguration anno) {
        ComponentConfig result = new ComponentConfig();
        result.setAutoAsSub(anno.autoAsSub());
        result.setAutoShowHide(anno.autoShowHide());
        result.setInheritAnimation(anno.inheritAnimation());
        result.setInheritLayer(anno.inheritLayer());
        result.setInheritShapeNode(anno.inheritShapeNode());
        return result;
    }
    
    public static ComponentConfig fromAnnotation(CinoSubComponentConfiguration anno) {
        ComponentConfig result = new ComponentConfig();
        result.setAutoAsSub(anno.autoAsSub());
        result.setAutoShowHide(anno.autoShowHide());
        result.setInheritAnimation(anno.inheritAnimation());
        result.setInheritLayer(anno.inheritLayer());
        result.setInheritShapeNode(anno.inheritShapeNode());
        return result;
    }
}
