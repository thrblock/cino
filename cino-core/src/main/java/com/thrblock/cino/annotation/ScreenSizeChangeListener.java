package com.thrblock.cino.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 窗体大小变化监听方法，要求有两个Integer型参 即形同 BiConsumer&lt;Integer,Integer&gt;
 * 
 * @author zepu.li
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ScreenSizeChangeListener {
}