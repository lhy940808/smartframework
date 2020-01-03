package com.lhy.javaweb.smart.proxy;

import com.lhy.javaweb.smart.annotation.Transaction;
import com.lhy.javaweb.smart.helper.DatabaseHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 事务代理
 * @author liuhaiyan
 * @date 2020-01-03 20:16
 */
@Slf4j
public class TransactionProxy implements Proxy{

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<>(){
        @Override
        protected  Boolean initialValue() {
            return false;
        }
    }
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                log.debug("begin transaction ");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                log.debug("commit transaction");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                log.debug("rollback transaction ");
                throw e;
            } finally {
                FLAG_HOLDER.remove();
            }
        }
        return result;
    }
}
