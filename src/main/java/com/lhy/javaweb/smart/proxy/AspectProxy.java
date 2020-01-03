package com.lhy.javaweb.smart.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 切面代理
 * @author liuhaiyan
 * @date 2020-01-02 20:44
 */
@Slf4j
public abstract class AspectProxy implements Proxy {


    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            }else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            log.error("proxy error, ", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }

    public void begin() {}

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before (Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

    }

    public void error (Class<?> cls, Method method, Object[] params, Throwable e) {

    }

    public void end() {

    }


}
