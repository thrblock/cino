package com.thrblock.cino.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.EnableLocalStorage;
import com.thrblock.cino.annotation.Saveable;
import com.thrblock.cino.util.ReflactUtils;

@Component
public class Storage {
    private static final Logger LOG = LoggerFactory.getLogger(Storage.class);
    @Value("${cino.storage.location:./}")
    private String location;

    public void save(Object o) {
        EnableLocalStorage anno = AnnotationUtils.getAnnotation(o.getClass(), EnableLocalStorage.class);
        if(anno == null) {
            return;
        }
        Properties prop = genProp(anno.value());
        if(prop == null) {
            return;
        }
        Arrays.stream(ReflactUtils.findAnnotationField(Saveable.class, o))
        .peek(f -> f.setAccessible(true))
        .forEach(f -> setProp(o, prop, f));
        File saveFile = createFileIfNeed(location + anno.value() + ".properties");
        try(OutputStream os = new FileOutputStream(saveFile)) {
            prop.store(os, null);
        } catch (IOException e) {
            LOG.warn("error when write file:{}", e);
        }
    }

    private void setProp(Object o, Properties prop, Field f) {
        try {
            prop.setProperty(f.getName(), f.get(o).toString());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOG.warn("error when set storage data from field:{}", e);
        }
    }

    public void load(Object o) {
        EnableLocalStorage anno = AnnotationUtils.getAnnotation(o.getClass(), EnableLocalStorage.class);
        Properties prop = genProp(anno.value());
        if(prop == null) {
            return;
        }
        Arrays.stream(ReflactUtils.findAnnotationField(Saveable.class, o))
        .peek(f -> f.setAccessible(true))
        .forEach(f -> setField(o, prop, f));
    }

    private void setField(Object o, Properties prop,Field f) {
        try {
            String data = prop.getProperty(f.getName());
            if(data != null) {
                f.set(o, ReflactUtils.toObject(f.getType(), data));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOG.warn("error when set storage data to field:{}", e);
        }
    }
    

    private Properties genProp(String name) {
        File f = createFileIfNeed(location + name + ".properties");
        Properties prop = new Properties();
        try(InputStream is = new FileInputStream(f)) {
            prop.load(is);
            return prop;
        } catch (IOException e) {
            LOG.warn("error when read file:{}", e);
            return null;
        }
    }

    private File createFileIfNeed(String file) {
        try {
            File f = new File(file);
            if (!f.exists()) {
                LOG.info("new file create for storage:{}", f.createNewFile());
            }
            return f;
        } catch (IOException e) {
            LOG.warn("error when create file:{}", e);
            return null;
        }
    }
}
