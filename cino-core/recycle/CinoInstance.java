package com.thrblock.cino.component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 实例 - 与组件为一对多关系 共享了组件的上下文
 * 
 * @author zepu.li
 *
 */
public abstract class CinoInstance extends CinoComponentContext {
    public void init() {
    }

    protected void sameContextOf(CinoComponentContext context) {
        Field[] fs = CinoComponentContext.class.getDeclaredFields();
        Arrays.stream(fs).forEach(f -> setField(context, f));
    }

    private void setField(CinoComponentContext context, Field f) {
        try {
            if (!Modifier.isFinal(f.getModifiers())) {
                f.set(this, f.get(context));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.error("Exception in component context filed set:{}", e);
        }
    }
}
