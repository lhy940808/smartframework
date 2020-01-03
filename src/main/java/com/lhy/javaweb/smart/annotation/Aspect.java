package com.lhy.javaweb.smart.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面注解
 * @author liuhaiyan
 * @date 2020-01-02 19:29
 */

@Target(ElementType.TYPE) // 只能作用在类上
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> values();
}
