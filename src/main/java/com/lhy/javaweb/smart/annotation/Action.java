package com.lhy.javaweb.smart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Action方法注解
 * @author liuhaiyan
 * @date 2019-12-31 14:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    /**
     * 请求类型与路径
     * */
    String value();
}
