package com.lhy.javaweb.smart.aspect;

import com.lhy.javaweb.smart.proxy.AspectProxy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 拦截controller所有方法
 * @author liuhaiyan
 * @date 2020-01-02 21:16
 */
@Slf4j
public class ControllerAspect extends AspectProxy {


    private long begin;


    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        log.debug("-----------begin-------");
        log.debug(String.format("class : %s", cls.getName()));
        log.debug(String.format("method : %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        log.debug(String.format("time : %dms", System.currentTimeMillis() - begin));
        log.debug("----------end------------");
    }

}
