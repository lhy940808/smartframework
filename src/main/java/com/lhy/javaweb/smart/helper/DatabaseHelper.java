package com.lhy.javaweb.smart.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 * @author liuhaiyan
 * @date 2020-01-03 14:45
 */
@Slf4j
public class DatabaseHelper {

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
//
//    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    /**
     * 确保一个线程只有一个connectiion
     * */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    private static final BasicDataSource DATA_SOURCE;

    static {
        DRIVER = ConfigHelper.getJdbcDriver();
        URL = ConfigHelper.getJdbcUrl();
        USERNAME = ConfigHelper.getJdbcUsername();
        PASSWORD = ConfigHelper.getJdbcPassword();

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD); }

    /**
     * 获取连接
     * */

    public static Connection getConnection () {
        Connection conn = CONNECTION_HOLDER.get();

        if (conn == null) {
            try{
//                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                log.error("get connection error", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }



    /**
     * @Description : 开启事务
     * @params []
     * @return void
     * @author liuhaiyan
     * @date 2020-01-03 14:54
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null ) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                log.error("begin transaction failure, ", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }

        }
    }

    /**
     * @Description : 提交事务
     * @params []
     * @return void
     * @author liuhaiyan
     * @date 2020-01-03 20:05
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                log.error("commit transaction failure, ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * @Description : 回滚事务
     * @params []
     * @return void
     * @author liuhaiyan
     * @date 2020-01-03 20:15
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (Exception e) {
                log.error("rollback transaction failure , ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }


}
