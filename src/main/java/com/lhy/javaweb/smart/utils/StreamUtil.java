package com.lhy.javaweb.smart.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 * @author liuhaiyan
 * @date 2020-01-02 13:58
 */
@Slf4j
public final class StreamUtil {

    public static String getString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            log.error("get string error, ", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
