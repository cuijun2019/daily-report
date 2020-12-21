/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.utils;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 全局可变参数
 * @author guojian
 * @version $$Revision: 1.1 $$
 * @date 2014-04-03
 */
public class Global {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Global.class);
    private static Properties props = null;

    public static void setProp() {
        props = new Properties();
        try {
            props.load(Global.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    public static boolean checkProperty() {
        if (props == null) {
            setProp();
        }
        if (props != null) {
            return true;
        }
        return false;
    }
    /************************************************************
     *MySQL连接池配置
     ************************************************************/
    /**
     * 数据库名称
     */
    public static String MYSQL_CONN_POOL_NAME;
    /**
     * 数据库驱动
     */
    public static String MYSQL_CONN_POOL_DRIVER;
    /**
     * 连接URL
     */
    public static String MYSQL_CONN_POOL_URL;
    /**
     * 连接USER
     */
    public static String MYSQL_CONN_POOL_USER;
    /**
     * 连接密码
     */
    public static String MYSQL_CONN_POOL_PASSWORD;
    /**
     * 初始连接数
     */
    public static int MYSQL_CONN_POOL_INIT_CONN_NUM;
    /**
     * 最大连接数
     */
    public static int MYSQL_CONN_POOL_MAX_CONN_NUM;

    /**
     * 工参文件目录
     */
    public static  String MRDATA_ENV_FROMZIP_PATH;
    /**
     * 生成文件路径
     */
    public static  String MRDATA_ENV_TOCSV_PATH  ;

    /**
     * 生成楼宇问题信息文件路径
     */
    public static  String FLOOR_QUESTION_PATH  ;



    static {
        try {
            checkProperty();
            MYSQL_CONN_POOL_NAME = new String(props.getProperty("lte.mysql.name").getBytes("ISO-8859-1"), "utf-8");
            MYSQL_CONN_POOL_DRIVER = new String(props.getProperty("lte.mysql.driver").getBytes("ISO-8859-1"), "utf-8");
            MYSQL_CONN_POOL_URL = new String(props.getProperty("lte.mysql.url").getBytes("ISO-8859-1"), "utf-8");
            MYSQL_CONN_POOL_USER = new String(props.getProperty("lte.mysql.user").getBytes("ISO-8859-1"), "utf-8");
            MYSQL_CONN_POOL_PASSWORD = new String(props.getProperty("lte.mysql.passwd").getBytes("ISO-8859-1"), "utf-8");
            MYSQL_CONN_POOL_INIT_CONN_NUM = Integer.parseInt(new String(props.getProperty("lte.mysql.initConnNum").getBytes("ISO-8859-1"), "utf-8"));
            MYSQL_CONN_POOL_MAX_CONN_NUM = Integer.parseInt(new String(props.getProperty("lte.mysql.maxConnNum").getBytes("ISO-8859-1"), "utf-8"));
            MRDATA_ENV_FROMZIP_PATH =new String(props.getProperty("mrdata.env.fromzip.path").getBytes("ISO-8859-1"), "utf-8");
            MRDATA_ENV_TOCSV_PATH = new String(props.getProperty("mrdata.env.tocsv.path").getBytes("ISO-8859-1"), "utf-8");
            FLOOR_QUESTION_PATH = new String(props.getProperty("floor.data").getBytes("ISO-8859-1"), "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /************************************************************
     *IQ连接池配置
     ************************************************************/
    /**
     * 数据库名称
     */
    public static String IQ_CONN_POOL_NAME;
    /**
     * 数据库驱动
     */
    public static String IQ_CONN_POOL_DRIVER;
    /**
     * 连接URL
     */
    public static String IQ_CONN_POOL_URL;
    /**
     * 连接USER
     */
    public static String IQ_CONN_POOL_USER;
    /**
     * 连接密码
     */
    public static String IQ_CONN_POOL_PASSWORD;
    /**
     * 初始连接数
     */
    public static int IQ_CONN_POOL_INIT_CONN_NUM;
    /**
     * 最大连接数
     */
    public static int IQ_CONN_POOL_MAX_CONN_NUM;

    static {
        try {
            checkProperty();
            IQ_CONN_POOL_NAME = new String(props.getProperty("lte.iq.name").getBytes("ISO-8859-1"), "utf-8");
            IQ_CONN_POOL_DRIVER = new String(props.getProperty("lte.iq.driver").getBytes("ISO-8859-1"), "utf-8");
            IQ_CONN_POOL_URL = new String(props.getProperty("lte.iq.url").getBytes("ISO-8859-1"), "utf-8");
            IQ_CONN_POOL_USER = new String(props.getProperty("lte.iq.user").getBytes("ISO-8859-1"), "utf-8");
            IQ_CONN_POOL_PASSWORD = new String(props.getProperty("lte.iq.passwd").getBytes("ISO-8859-1"), "utf-8");
            IQ_CONN_POOL_INIT_CONN_NUM = Integer.parseInt(new String(props.getProperty("lte.iq.initConnNum").getBytes("ISO-8859-1"), "utf-8"));
            IQ_CONN_POOL_MAX_CONN_NUM = Integer.parseInt(new String(props.getProperty("lte.iq.maxConnNum").getBytes("ISO-8859-1"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**************************** 任务配置 **************************************/
    private final static String START_PART = "ltenoa/";
    private final static String DEV_PART = "webapps/ltenoa/WEB-INF/classes/";
    public final static String BIN_PATH = Global.class.getResource("/").getPath();


    /**
     * 获取项目根目录
     *
     * @return
     */
    public synchronized static String getRoot() {
        String root;
        if (BIN_PATH.endsWith(START_PART)) {
            root = BIN_PATH.substring(0, BIN_PATH.lastIndexOf(START_PART));
        }
        if (BIN_PATH.endsWith(DEV_PART)) {
            root = BIN_PATH.substring(0, BIN_PATH.lastIndexOf(DEV_PART));
        } else {
            // # bin
            // # cd ..
            root = BIN_PATH;
        }
        return root;
    }
}
