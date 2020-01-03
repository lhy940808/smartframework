package com.lhy.javaweb.smart;

import com.lhy.javaweb.smart.helper.AopHelper;
import com.lhy.javaweb.smart.helper.BeanHelper;
import com.lhy.javaweb.smart.helper.ClassHelper;
import com.lhy.javaweb.smart.helper.ControllerHelper;
import com.lhy.javaweb.smart.helper.IocHelper;
import com.lhy.javaweb.smart.utils.ClassUtil;

/**
 * 加载相应的Helper类
 * @author liuhaiyan
 * @date 2019-12-31 16:58
 */
public final class HelperLoader {

    public static void init() {
        /**
         * AopHelper要在iochelper之前加载，因为首先需要通过aophelper获取代理对象，然后才能通过iochelper进行依赖注入
         * */
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, AopHelper.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> cls : classList) {
            ClassUtil.loadClasss(cls.getName(), true);
        }
    }
}
