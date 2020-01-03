package com.lhy.javaweb.smart.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 * @author liuhaiyan
 * @date 2020-01-02 14:09
 */
@Slf4j
public final class CodeUtil {

    /**
     * @Description : 将url编码
     * @params [source]
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2020-01-02 14:14
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            log.error("encode url error, ", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * @Description :  将url解码
     * @params [source]
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2020-01-02 14:16
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            log.error("decode url error , ", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
