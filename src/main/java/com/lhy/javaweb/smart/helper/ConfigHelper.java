package com.lhy.javaweb.smart.helper;

import com.lhy.javaweb.smart.consts.ConfigConstant;
import com.lhy.javaweb.smart.utils.PropsUtil;

import java.util.Properties;

/**
 * 属性文件助手
 * @author liuhaiyan
 * @date 2019-12-30 21:01
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS  = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * @Description : 获取jdbc驱动
     * @params []
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2019-12-30 21:14
     */
    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * @Description :获取jdbc的url
     * @params []
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2019-12-30 21:16
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * @Description :获取jdbc的username
     * @params []
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2019-12-30 21:17
     */
    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * @Description :获取jdbc的password
     * @params []
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2019-12-30 21:17
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * @Description :获取应用基础包名
     * @params []
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2019-12-30 21:21
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * @Description :获取应用jsp路径
     * @params []
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2019-12-30 21:21
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATh, "/WEB-INF/view/");
    }

    /**
     * @Description :获取应用静态资源路径
     * @params []
     * @return java.lang.String
     * @author liuhaiyan
     * @date 2019-12-30 21:20
     */
    public static String getAppViewPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }

}
