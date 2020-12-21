/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.log;

import com.etone.project.base.support.BaseConstants;
import com.etone.project.base.support.SpringContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志上下文，用于异步日志处理
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14159 $$
 */
public class LogContext {
    // members
    /**
     * 用于存储每个线程的request请求
     */
    private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<HttpServletRequest>();
    // static block

    // constructors

    // properties

    // public methods
    public static void putRequest(HttpServletRequest request) {
        REQUEST_THREAD_LOCAL.set(request);
    }

    public static HttpServletRequest getRequest() {
        return REQUEST_THREAD_LOCAL.get();
    }

    public static void removeRequest() {
        REQUEST_THREAD_LOCAL.remove();
    }

    /**
     * 注入动态日志参数
     *
     * @param args 参数
     */
    public static void putArgs(Object... args) {
        HttpServletRequest request = getRequest();
        LogMessage message = getBean(args);
        if (request != null && message != null) {
            request.setAttribute(BaseConstants.LOG_ARGS, message);
        }
    }

    /**
     * 获取上下文中的logMessage
     */
    public static LogMessage getArgs() {
        HttpServletRequest request = getRequest();
        return (LogMessage) request.getAttribute(BaseConstants.LOG_ARGS);
    }
    // protected methods

    // friendly methods

    // private methods

    /**
     * 初始日志信息实体，给合注解@Auditmeta.message使用
     *
     * @param args 参数
     * @return
     */
    private static LogMessage getBean(Object[] args) {
        LogMessage message = (LogMessage) SpringContext.getBean("logMessage");
        message.setArgs(args);
        return message;
    }
    // inner class

    // test main
}
