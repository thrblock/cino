package com.thrblock.cino.annotation;

public @interface CinoSubComponentConfiguration {
    /**
     * rootNode is show/hide by Component activited/deactivited
     * 
     * @return
     */
    boolean autoShowHide() default true;
    
    /**
     * register this component as it's parent's sub
     * @return
     */
    boolean autoAsSub() default true;

    /**
     * GLAnimateManager is auto generate by parent manager generateSubContainer()
     * 
     * @return
     */
    boolean inheritAnimation() default true;

    /**
     * shapeFactory layer auto setted same as parent shapeFactory
     * 
     * @return
     */
    boolean inheritLayer() default true;

    /**
     * rootNode auto setted as subNode of parent rootNode
     * 
     * @return
     */
    boolean inheritShapeNode() default true;
}
