package com.lhy.javaweb.smart.helper;

import com.lhy.javaweb.smart.annotation.Controller;
import com.lhy.javaweb.smart.annotation.Service;
import com.lhy.javaweb.smart.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 * @author liuhaiyan
 * @date 2019-12-31 14:17
 */

public class ClassHelper {

    /**
     * 定义类集合（用于存放索加载的类）
     * */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * @Description :获取应用包下的所有类
     * @params []
     * @return java.util.Set<java.lang.Class<?>>
     * @author liuhaiyan
     * @date 2019-12-31 14:25
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * @Description :获取应用包下所有sevice注解的类
     * @params []
     * @return java.util.Set<java.lang.Class<?>>
     * @author liuhaiyan
     * @date 2019-12-31 14:36
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * @Description : 获取应用包下所有controller注解的类
     * @params []
     * @return java.util.Set<java.lang.Class<?>>
     * @author liuhaiyan
     * @date 2019-12-31 14:40
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * @Description :获取应用包下所有bean类（包括：service controller等）
     * @params []
     * @return java.util.Set<java.lang.Class<?>>
     * @author liuhaiyan
     * @date 2019-12-31 14:43
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * @Description : 获取应用包名下某父类（或接口）的所有子类（或实现类）
     * @params [superClass]
     * @return java.util.Set<java.lang.Class<?>>
     * @author liuhaiyan
     * @date 2020-01-02
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * @Description : 获取应用包名下带有某注解的所有类
     * @params [annotationClass]
     * @return java.util.Set<java.lang.Class<?>>
     * @author liuhaiyan
     * @date 2020-01-03 10:55
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
