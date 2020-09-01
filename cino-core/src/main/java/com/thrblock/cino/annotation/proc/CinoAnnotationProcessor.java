package com.thrblock.cino.annotation.proc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;

import com.thrblock.cino.annotation.BootComponent;
import com.thrblock.cino.annotation.CinoComponentConfiguration;
import com.thrblock.cino.annotation.CinoSubComponentConfiguration;
import com.thrblock.cino.annotation.EnableLocalStorage;
import com.thrblock.cino.annotation.GLAutoInit;
import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.annotation.SubCompOf;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.component.ComponentConfig;
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
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        CinoComponentConfiguration an = AnnotationUtils.findAnnotation(bean.getClass(),
                CinoComponentConfiguration.class);
        Optional.ofNullable(an).ifPresent(s -> Optional.ofNullable(bean).filter(CinoComponent.class::isInstance)
                .map(CinoComponent.class::cast).ifPresent(comp -> comp.setComponentConfig(ComponentConfig.fromAnnotation(an))));
        
        CinoSubComponentConfiguration subAn = AnnotationUtils.findAnnotation(bean.getClass(),
                CinoSubComponentConfiguration.class);
        Optional.ofNullable(subAn).ifPresent(s -> Optional.ofNullable(bean).filter(CinoComponent.class::isInstance)
                .map(CinoComponent.class::cast).ifPresent(comp -> comp.setComponentConfig(ComponentConfig.fromAnnotation(subAn))));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
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
        Optional.ofNullable(bootComp).ifPresent(CinoComponent::activited);
    }

    private void processBean(Object o) {
        Arrays.stream(ReflactUtils.findAnnotationMethod(ScreenSizeChangeListener.class, o)).forEach(
                m -> screenSizeChanger.addScreenSizeChangeListener((w, h) -> ReflactUtils.invokeMethod(o, m, w, h)));
        Arrays.stream(ReflactUtils.findAnnotationMethod(GLAutoInit.class, o))
                .forEach(m -> glInitor.addGLInitializable(gl -> ReflactUtils.invokeMethod(o, m, gl)));

        Optional.ofNullable(AnnotationUtils.findAnnotation(o.getClass(), BootComponent.class))
                .filter(b -> o instanceof CinoComponent).ifPresent(boot -> {
                    CinoComponent comp = (CinoComponent) o;
                    LOG.info("boot component found:{}", comp);
                    this.bootComp = comp;
                });

        EnableLocalStorage st = AnnotationUtils.findAnnotation(o.getClass(), EnableLocalStorage.class);
        Optional.ofNullable(st).ifPresent(s -> afterProcessed.add(() -> storage.load(o)));

        Optional.ofNullable(AnnotationUtils.findAnnotation(o.getClass(), SubCompOf.class))
                .ifPresent(sub -> afterProcessed.add(() -> Arrays.stream(sub.value()).forEach(clazz -> {
                    CinoComponent masterComp = applicationContext.getBean(clazz);
                    cinoComponentAnnoProc.asSub(masterComp, (CinoComponent) o);
                })));
    }

}
