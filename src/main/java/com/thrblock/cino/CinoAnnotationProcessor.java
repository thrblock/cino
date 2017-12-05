package com.thrblock.cino;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
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
import com.thrblock.cino.glprocessor.GLEventProcessor;
import com.thrblock.cino.storage.Storage;
import com.thrblock.cino.util.ReflactUtils;

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
    
    @Autowired
    private Storage storage;
    
    @Autowired
    private ComponentAnnotationProcessor cinoComponentAnnoProc;

    private ApplicationContext applicationContext;
    
    private List<VoidConsumer> lst = new LinkedList<>();
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        LOG.info("processing annotation of beans");
        this.applicationContext = applicationContext;
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanNames).map(applicationContext::getBean).forEach(this::processBean);
        lst.forEach(e -> e.accept());
    }

    private void processBean(Object o) {
        Arrays.stream(ReflactUtils.findAnnotationMethod(ScreenSizeChangeListener.class, o))
                .forEach(m -> glEventProcessor.addScreenSizeChangeListener((w, h) -> ReflactUtils.invokeMethod(o, m, w, h)));
        Arrays.stream(ReflactUtils.findAnnotationMethod(GLAutoInit.class, o))
                .forEach(m -> glInitor.addGLInitializable(gl -> ReflactUtils.invokeMethod(o, m, gl)));

        BootComponent boot = AnnotationUtils.findAnnotation(o.getClass(), BootComponent.class);
        if (boot != null && o instanceof CinoComponent) {
            CinoComponent comp = (CinoComponent) o;
            lst.add(comp::activited);
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

}
