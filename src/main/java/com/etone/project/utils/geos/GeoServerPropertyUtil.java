package com.etone.project.utils.geos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 * 获取GeoServer配置
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">
 */
public class GeoServerPropertyUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GeoServerPropertyUtil.class);
    private static Properties props = null;

    public static void setProp() {
        props = new Properties();
        try {
            props.load(GeoServerPropertyUtil.class.getClassLoader().getResourceAsStream("application.properties"));
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

        logger.error("没有geoServer配置");
        return false;
    }

    public static String getUrl() {
        String url = null;

        if (checkProperty() == false) {
            return url;
        }

        try {
            url = new String(props.getProperty("geoserver.rest.url").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        return url;
    }

    public static String getPort() {
        String port = null;

        if (checkProperty() == false) {
            return port;
        }

        try {
            port = new String(props.getProperty("geoserver.rest.port").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        return port;
    }

    public static String getUserName() {
        String userName = null;

        if (checkProperty() == false) {
            return userName;
        }

        try {
            userName = new String(props.getProperty("geoserver.rest.username").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        return userName;
    }

    public static String getPassword() {
        String password = null;

        if (checkProperty() == false) {
            return password;
        }

        try {
            password = new String(props.getProperty("geoserver.rest.password").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        return password;
    }

    public static String getDataPath() {
        String path = null;

        if (checkProperty() == false) {
            return path;
        }

        try {
            path = new String(props.getProperty("geoserver.date.dir").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        return path;
    }
    
    public static String getFtpStartStatus() {
        String url = null;

        if (checkProperty() == false) {
            return url;
        }

        try {
            url = new String(props.getProperty("ftp.thread.status").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        return url;
    }
    
    /**
     * 开始时间
     * @return
     */
    public static String getStaticStartTime() {
        String url = null;

        if (checkProperty() == false) {
            return url;
        }

        try {
            url = new String(props.getProperty("system.startTime").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        return url;
    }
    
    /**
     * 结束时间
     * @return
     */
    public static String getStaticEndTime() {
        String url = null;

        if (checkProperty() == false) {
            return url;
        }

        try {
            url = new String(props.getProperty("system.endTime").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        return url;
    }

    public static void main(String[] args) {
        logger.info("url: " + getUrl());
        logger.info("userName: " + getUserName());
        logger.info("password: " + getPassword());
        logger.info("dataPath: " + getDataPath());
    }
}
