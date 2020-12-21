package com.etone.project.core.db;

/**
 * Created with IntelliJ IDEA.  多数据源
 * User: Guojian
 * Date: 14-6-25
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public abstract class CustomerContextHolder {

    public final static String SESSION_FACTORY_MYSQL = "mysql";
    public final static String SESSION_FACTORY_IQ = "iq";
    public final static String SESSION_FACTORY_MYSQL2 = "mysql2";
    public final static String SESSION_FACTORY_GP = "gp";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setContextType(String contextType) {
        contextHolder.set(contextType);
    }

    public static String getContextType() {
        return contextHolder.get();
    }

    public static void clearContextType() {
        contextHolder.remove();
    }
}
