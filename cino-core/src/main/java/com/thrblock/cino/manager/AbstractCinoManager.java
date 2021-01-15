package com.thrblock.cino.manager;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.thrblock.cino.annotation.ScreenSizeChangeListener;
import com.thrblock.cino.gllifecycle.CycleArray;
import com.thrblock.cino.util.ex.CinoException;

public class AbstractCinoManager<T extends CinoManageable> {

    protected Supplier<T> supplier = () -> {
        throw new CinoException();
    };
    @Value("${cino.frame.screen.width:800}")
    protected int frameSizeW;
    @Value("${cino.frame.screen.height:600}")
    protected int frameSizeH;
    
    protected CycleArray<CinoManageable> transCycle = new CycleArray<>(CinoManageable[]::new);

    public AbstractCinoManager() {
        
    }
    
    public AbstractCinoManager(Supplier<T> sup) {
        this.supplier = sup;
    }

    public T create() {
        T result = supplier.get();
        transCycle.safeAdd(result);
        result.onCreate();
        return result;
    }

    public T create(Consumer<T> cons) {
        T result = create();
        cons.accept(result);
        return result;
    }
    
    public void add(T t) {
        transCycle.safeAdd(t);
    }

    public void destroy(T t) {
        t.onDestroy();
        transCycle.safeRemove(t);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public T createProtoType() {
        return create();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public T createProtoType(Consumer<T> cons) {
        return create(cons);
    }

    @ScreenSizeChangeListener
    public void noticeScreenChange(int w, int h) {
        this.frameSizeH = h;
        this.frameSizeW = w;
        transCycle.safeOperation(s -> s.forEach(t -> t.onScreenChange(w, h)));
    }
}
