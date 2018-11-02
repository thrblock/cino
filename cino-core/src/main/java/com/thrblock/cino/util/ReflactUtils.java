package com.thrblock.cino.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

public class ReflactUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ReflactUtils.class);

    private ReflactUtils() {
    }

    public static void invokeMethod(Object o, Method m, Object... params) {
        try {
            m.invoke(o, params);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOG.error("error when invoke annotation method:{}" ,e);
        }
    }

    public static Field[] findAnnotationField(Class<? extends Annotation> annotationClass, Object o) {
        Field[] all = o.getClass().getDeclaredFields();
        return Arrays.stream(all).filter(field -> AnnotationUtils.findAnnotation(field, annotationClass) != null)
                .toArray(size -> new Field[size]);
    }

    public static Method[] findAnnotationMethod(Class<? extends Annotation> annotationClass, Object o) {
        Method[] all = o.getClass().getMethods();
        return Arrays.stream(all).filter(method -> AnnotationUtils.findAnnotation(method, annotationClass) != null)
                .toArray(size -> new Method[size]);
    }

    public static Object toObject(Class<?> clazz, String value) {
        if (Boolean.class == clazz || boolean.class == clazz)
            return Boolean.parseBoolean(value);
        if (Byte.class == clazz || byte.class == clazz)
            return Byte.parseByte(value);
        if (Short.class == clazz || short.class == clazz)
            return Short.parseShort(value);
        if (Integer.class == clazz || int.class == clazz)
            return Integer.parseInt(value);
        if (Long.class == clazz || long.class == clazz)
            return Long.parseLong(value);
        if (Float.class == clazz || float.class == clazz)
            return Float.parseFloat(value);
        if (Double.class == clazz || double.class == clazz)
            return Double.parseDouble(value);
        return value;
    }
}
