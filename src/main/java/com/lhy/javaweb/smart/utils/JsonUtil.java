package com.lhy.javaweb.smart.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * json工具类
 * @author liuhaiyan
 * @date 2020-01-02 14:17
 */
@Slf4j
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * @Description : 将pojo转为json
     * @params [obj]
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2020-01-02 14:20
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("convert pojo to json error, ", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * @Description : 将json转换为pojo
     * @params [json, type]
     * @return T
     * @author liuhaiyan
     * @date 2020-01-02 14:23
     */
    public static <T> T fromJsonToPojo(String json, Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch(Exception e) {
            log.error("from json to pojo error, ", e);
            throw new RuntimeException(e);
        }
        return pojo;

    }
}
