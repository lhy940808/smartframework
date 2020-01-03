package com.lhy.javaweb.smart.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;

/**
 * @author liuhaiyan
 * @date 2019-12-31 15:40
 */
public class ArrayUtil {

    /**
     * @Description : 判断数组是否非空
     * @params [array]
     * @return boolean
     * @author liuhaiyan
     * @date 2019-12-31 15:42
     */
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    /**
     * @Description : 判断数组是否为空
     * @params [array]
     * @return boolean
     * @author liuhaiyan
     * @date 2019-12-31 15:42
     */
    public static boolean isEmpyt(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
