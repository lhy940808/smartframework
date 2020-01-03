package com.lhy.javaweb.smart.helper;

import com.lhy.javaweb.smart.annotation.Inject;
import com.lhy.javaweb.smart.utils.CollectionUtil;
import com.lhy.javaweb.smart.utils.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * @author liuhaiyan
 * @date 2019-12-31 15:23
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 在备案map中获取bean field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                // 通过反射初始化beanfield的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
