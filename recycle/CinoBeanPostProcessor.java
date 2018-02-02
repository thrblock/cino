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
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.BootComponent;
import com.thrblock.cino.annotation.EnableLocalStorage;
import com.thrblock.cino.annotation.GLAutoInit;
import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.annotation.SubCompOf;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.component.ComponentAnnotationProcessor;
import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glinitable.GLInitor;
import com.thrblock.cino.glprocessor.GLEventProcessor;
import com.thrblock.cino.storage.Storage;
import com.thrblock.cino.util.ReflactUtils;

@Component
public class CinoBeanPostProcessor implements BeanPostProcessor,ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(CinoBeanPostProcessor.class);

    @Autowired
    private GLEventProcessor glEventProcessor;

    @Autowired
    private GLInitor glInitor;
    
    @Autowired
    private Storage storage;
    
    @Autowired
    private ComponentAnnotationProcessor cinoComponentAnnoProc;

    private ApplicationContext applicationContext;
    
    private List<VoidConsumer> afterProcess = new LinkedList<>();
    
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOG.info("processing annotation of bean:{}",beanName);
        processBean(bean);
        return bean;
    }
    
    private void processBean(Object o) {
        Arrays.stream(ReflactUtils.findAnnotationMethod(ScreenSizeChangeListener.class, o))
                .forEach(m -> glEventProcessor.addScreenSizeChangeListener((w, h) -> ReflactUtils.invokeMethod(o, m, w, h)));
        Arrays.stream(ReflactUtils.findAnnotationMethod(GLAutoInit.class, o))
                .forEach(m -> glInitor.addGLInitializable(gl -> ReflactUtils.invokeMethod(o, m, gl)));

        BootComponent boot = AnnotationUtils.findAnnotation(o.getClass(), BootComponent.class);
        if (boot != null && o instanceof CinoComponent) {
            CinoComponent comp = (CinoComponent) o;
            afterProcess.add(comp::activited);
        }
        
        EnableLocalStorage st = AnnotationUtils.findAnnotation(o.getClass(), EnableLocalStorage.class);
        if(st != null) {
            storage.load(o);
        }
        
        SubCompOf sub = AnnotationUtils.findAnnotation(o.getClass(), SubCompOf.class);
        if(sub != null) {
            Arrays.stream(sub.value()).forEach(clazz -> {
                CinoComponent masterComp = applicationContext.getBean(clazz);
                cinoComponentAnnoProc.asSub(masterComp, (CinoComponent)o);
            });
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        afterProcess.forEach(VoidConsumer::accept);
        afterProcess.clear();
    }
}