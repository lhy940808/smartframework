package com.lhy.javaweb.smart.bean;

import com.lhy.javaweb.smart.utils.CastUtil;

import java.util.Map;

/**
 *
 * 请求参数对象
 * @author liuhaiyan
 * @date 2019-12-31 17:08
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * @Description : 根据参数名获取long型参数值
     * @params [name]
     * @return long
     * @author liuhaiyan
     * @date 2019-12-31 17:12
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * @Description : 获取所有字段信息
     * @params []
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author liuhaiyan
     * @date 2019-12-31 17:13
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }
}
