package com.thrblock.cino;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;

import com.thrblock.cino.annotation.BootComponent;
import com.thrblock.cino.annotation.EnableLocalStorage;
import com.thrblock.cino.annotation.GLAutoInit;
import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.annotation.SubCompOf;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.component.ComponentAnnotationProcessor;
import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glinitable.GLInitor;
import com.thrblock.cino.glprocessor.GLScreenSizeChangeListenerHolder;
import com.thrblock.cino.storage.Storage;
import com.thrblock.cino.util.ReflactUtils;

/**
 * Cino 注解处理器
 * 
 * @author zepu.li
 *
 */
@Configuration
class CinoAnnotationProcessor
        implements BeanPostProcessor, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(CinoAnnotationProcessor.class);
    @Autowired
    private GLScreenSizeChangeListenerHolder screenSizeChanger;

    @Autowired
    private GLInitor glInitor;

    @Autowired
    private Storage storage;

    @Autowired
    private ComponentAnnotationProcessor cinoComponentAnnoProc;

    private ApplicationContext applicationContext;

    private List<VoidConsumer> afterProcessed = new LinkedList<>();
    
    private CinoComponent bootComp;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        processBean(bean);
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        afterProcessed.forEach(VoidConsumer::accept);
        afterProcessed.clear();
        if(bootComp != null) {
            bootComp.activited();
        }
    }

    private void processBean(Object o) {
        Arrays.stream(ReflactUtils.findAnnotationMethod(ScreenSizeChangeListener.class, o)).forEach(
                m -> screenSizeChanger.addScreenSizeChangeListener((w, h) -> ReflactUtils.invokeMethod(o, m, w, h)));
        Arrays.stream(ReflactUtils.findAnnotationMethod(GLAutoInit.class, o))
                .forEach(m -> glInitor.addGLInitializable(gl -> ReflactUtils.invokeMethod(o, m, gl)));

        BootComponent boot = AnnotationUtils.findAnnotation(o.getClass(), BootComponent.class);
        if (boot != null && o instanceof CinoComponent) {
            CinoComponent comp = (CinoComponent) o;
            LOG.info("boot component found:{}", comp);
            this.bootComp = comp;
        }

        EnableLocalStorage st = AnnotationUtils.findAnnotation(o.getClass(), EnableLocalStorage.class);
        if (st != null) {
            afterProcessed.add(() -> storage.load(o));
        }

        SubCompOf sub = AnnotationUtils.findAnnotation(o.getClass(), SubCompOf.class);
        if (sub != null) {
            afterProcessed.add(() -> Arrays.stream(sub.value()).forEach(clazz -> {
                CinoComponent masterComp = applicationContext.getBean(clazz);
                cinoComponentAnnoProc.asSub(masterComp, (CinoComponent) o);
            }));
        }
    }

}
