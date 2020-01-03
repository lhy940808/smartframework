package com.lhy.javaweb.smart.proxy;

/**
 * 代理接口
 * @author liuhaiyan
 * @date 2020-01-02 19:33
 */
public interface Proxy {

    /*
    * 执行链式代理（可将多个代理通过一条链子串起来，一个个去执行，执行顺序取决于链上的先后顺序）
    * **/
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
