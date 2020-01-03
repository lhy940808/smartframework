package com.lhy.javaweb.smart.helper;

import com.lhy.javaweb.smart.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * bean助手类
 * @author liuhaiyan
 * @date 2019-12-31 15:07
 */
public final class BeanHelper {

    /**
     * 定义bean映射（用于存放bean类与bean实例的映射关系）
     * */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    public static  Map<Class<?>, Object> getBeanMap () {
        return BEAN_MAP;
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class  " + cls);
        }
        return (T)BEAN_MAP.get(cls);
    }
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }

}
