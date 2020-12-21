/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.log;

import com.etone.commons.util.Exceptions;
import com.etone.project.base.support.BaseConstants;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.base.type.LogLevel;
import com.etone.project.base.type.LogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

/**
 * 各种拦截，实现自动日志登记
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14159 $$
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
    // members
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private static final String START_INVOKE_TIME = "startInvokeTime";
    private static final String END_INVOKE_TIME = "endInvokeTime";

    /**
     *
     */
    private Log4Database log4Database;
    // static block

    // constructors

    // properties
    public void setLog4Database(Log4Database log4Database) {
        this.log4Database = log4Database;
    }

    public Log4Database getLog4Database() {
        return log4Database;
    }
    // public methods

    /**
     * 存入上下文
     * <p/>
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 存入上下文Request
        LogContext.putRequest(request);
        // 记录调用时间
        request.setAttribute(START_INVOKE_TIME, System.currentTimeMillis());
        return super.preHandle(request, response, handler);
//        return true;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 记录调用结时间
        request.setAttribute(END_INVOKE_TIME, System.currentTimeMillis());
        long costTime = (Long) request.getAttribute(END_INVOKE_TIME) - (Long) request.getAttribute(END_INVOKE_TIME);
        if (modelAndView != null) {
            modelAndView.addObject(BaseConstants.INVOKE_COST, costTime);
            modelAndView.addObject(BaseConstants.CAPTCHABLE, ShiroDbRealm.isCaptchEnable() ? "true" : "false");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("--------------------------{}: cost {}ms---------------------------", handler, costTime);
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 实现异步处理日志
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (handler instanceof ParameterizableViewController || handler instanceof DefaultServletHttpRequestHandler) return;
        try {
            HandlerMethod hm = (HandlerMethod) handler;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }
        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Exception exception = ex;
        Method method = handlerMethod.getMethod();

        // 获得LogMessage相关参数
        final LogMessage message = LogContext.getArgs();
        // 从调用方法中获得模块及日志信息
        final Auditmeta audit = method.getAnnotation(Auditmeta.class);
        if (audit != null && message != null) {
            final long startLogTime = (Long) request.getAttribute("startInvokeTime");
            final long endLogTime = System.currentTimeMillis();
            // 另起线程异步操作
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        LogLevel lastLogLevel = log4Database.getRootLevel();
                        // 先对自定义包等级做判断
                        Map<String, LogLevel> moduleLevel = log4Database.getModuleLevel();

                        // 按package统一进行日志级别配置
                        if (!moduleLevel.isEmpty()) {
                            Class<?> clazz = handlerMethod.getBean().getClass();
                            String packageName = clazz.getPackage().getName();

                            Set<String> keys = moduleLevel.keySet();
                            for (String key : keys) {
                                if (packageName.startsWith(key)) {
                                    lastLogLevel = moduleLevel.get(key);
                                    break;
                                }
                            }
                        }

                        LogStatus status = (exception != null) ? LogStatus.FAILURE : LogStatus.SUCCESS;
                        LogMessage logMessage = message;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(startLogTime);
                        logMessage.startLogTime = calendar.getTime();
                        calendar.setTimeInMillis(endLogTime);
                        logMessage.endLogTime = calendar.getTime();
                        logMessage.respCost = Long.valueOf(endLogTime - startLogTime).intValue();
                        // 判断是否写入log
                        if (logMessage != null) {
                            // 覆盖，直接写入日志
                            if (audit.force()) {
                                log4Database.log(audit.message(), logMessage, audit.level(), audit.type(), status);
                            } else {
                                // 不覆盖，参考方法的日志等级是否大于等于最终的日志等级
                                if (!audit.force() && audit.level().compareTo(lastLogLevel) >= 0) {
                                    log4Database.log(audit.message(), logMessage, audit.level(), audit.type(), status);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error(Exceptions.getStackTraceAsString(e));
                    }
                }
            }).start();
        }
        LogContext.removeRequest();
        super.afterCompletion(request, response, handler, ex);
    }

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
