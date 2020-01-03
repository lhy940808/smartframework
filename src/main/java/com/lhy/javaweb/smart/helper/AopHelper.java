package com.lhy.javaweb.smart.helper;

import com.lhy.javaweb.smart.annotation.Aspect;
import com.lhy.javaweb.smart.annotation.Service;
import com.lhy.javaweb.smart.proxy.AspectProxy;
import com.lhy.javaweb.smart.proxy.Proxy;
import com.lhy.javaweb.smart.proxy.ProxyManager;
import com.lhy.javaweb.smart.proxy.TransactionProxy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 方法拦截助手类
 * @author liuhaiyan
 * @date 2020-01-03 10:57
 */
@Slf4j
public final class AopHelper {


    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        }catch (Exception e) {
            log.error("aop failure , ", e);
        }
    }
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
        Set<Class<?>> targetClassSet =new HashSet<>();
        Class<? extends Annotation>  annotation = aspect.values();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * @Description : 获取代理类和目标类之间的映射关系
     * 代理类需要扩展AspectProxy抽象类，还需要带有Aspect注解，只有满足这两个条件，才能根据Aspect注解中的所定义的注解属性去获取该注解所对应的目标类集合
     * @params \
     * @return java.util.Map<java.lang.Class<?>,java.util.Set<java.lang.Class<?>>>
     * @author liuhaiyan
     * @date 2020-01-03 11:08
     */
//    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
//        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
//        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
//        for (Class<?> proxyClass : proxyClassSet) {
//            if (proxyClass.isAnnotationPresent(Aspect.class)) {
//                  Aspect aspect = proxyClass.getAnnotation(Aspect.class);
//                  Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
//                  proxyMap.put(proxyClass, targetClassSet);
//            }
//        }
//        return proxyMap;
//    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }

    }

    public static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetBySuper(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }

    /**
     * @Description :分析目标类与代理对象列表之间的映射关系
     * @params [proxyMap]
     * @return java.util.Map<java.lang.Class<?>,java.util.List<com.lhy.javaweb.smart.proxy.Proxy>>
     * @author liuhaiyan
     * @date 2020-01-03 11:31
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy)proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }

}
