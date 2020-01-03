package com.lhy.javaweb.smart.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * @author liuhaiyan
 * @date 2019-12-31 14:45
 */
@Slf4j
public final class ReflectionUtil {

    /**
     * @Description :创建实例
     * @params [cls]
     * @return java.lang.Object
     * @author liuhaiyan
     * @date 2019-12-31 14:51
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            log.error("new instance failure , ", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * @Description : 调用方法
     * @params [obj, method, args]
     * @return java.lang.Object
     * @author liuhaiyan
     * @date 2019-12-31 14:55
     */
    public static Object invokeMethod(Object obj, Method method, Object ... args) {

        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            log.error("invoke method error, ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * @Description : 设置成员变量的值
     * @params [obj, field, value]
     * @return void
     * @author liuhaiyan
     * @date 2019-12-31 14:58
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            log.error("set field error, ", e);
            throw new RuntimeException(e);
        }
    }
}
