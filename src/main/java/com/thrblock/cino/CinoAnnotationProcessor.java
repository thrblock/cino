package com.thrblock.cino;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import com.thrblock.cino.annotation.GLAutoInit;
import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.glinitable.GLInitor;
import com.thrblock.cino.glprocessor.GLEventProcessor;

/**
 * Cino 注解处理器
 * 
 * @author zepu.li
 *
 */
@Configuration
class CinoAnnotationProcessor implements ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(CinoAnnotationProcessor.class);

    @Autowired
    private GLEventProcessor glEventProcessor;

    @Autowired
    private GLInitor glInitor;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanNames).map(applicationContext::getBean).forEach(this::processBean);
    }

    private void processBean(Object o) {
        Arrays.stream(findAnnotationMethod(ScreenSizeChangeListener.class, o))
                .forEach(m -> glEventProcessor.addScreenSizeChangeListener((w, h) -> invokeMethod(o, m, w, h)));
        Arrays.stream(findAnnotationMethod(GLAutoInit.class, o))
                .forEach(m -> glInitor.addGLInitializable(gl -> invokeMethod(o, m, gl)));
    }

    private void invokeMethod(Object o, Method m, Object... params) {
        try {
            m.invoke(o, params);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOG.error("error when invoke annotation method:" + e);
        }
    }

    private Method[] findAnnotationMethod(Class<? extends Annotation> annotationClass, Object o) {
        Method[] all = o.getClass().getMethods();
        return Arrays.stream(all).filter(method -> AnnotationUtils.findAnnotation(method, annotationClass) != null)
                .toArray(size -> new Method[size]);
    }
}
