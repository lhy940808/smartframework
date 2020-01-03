package com.lhy.javaweb.smart.bean;

import java.lang.reflect.Method;

/**
 * 封装action信息
 * @author liuhaiyan
 * @date 2019-12-31 16:06
 */
public class Handler {

    /**
     * controller类
     * */
    private Class<?> controllerClass;


    /**
     * action 方法
     * */
    private Method actionMethod;

    public  Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return this.actionMethod;
    }


}
