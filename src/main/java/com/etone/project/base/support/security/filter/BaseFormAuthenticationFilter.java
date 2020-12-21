/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.security.filter;

import com.etone.commons.util.Exceptions;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录操作过滤器, 不带验证码，单点登录需要实现独立的Filter
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14158 $$
 */
public class BaseFormAuthenticationFilter extends FormAuthenticationFilter {
    // members
    public static final Logger logger = LoggerFactory.getLogger(BaseFormAuthenticationFilter.class);
    // static block

    // constructors

    // properties

    // public methods

    // protected methods

    /**
     * 覆盖默认实现，打印日志便于调试，查看具体登录是什么错误。
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (logger.isDebugEnabled()) {
            Class<?> clazz = e.getClass();
            if (clazz.equals(AuthenticationException.class)) {
                logger.debug(Exceptions.getStackTraceAsString(e));
            }
        }

        return super.onLoginFailure(token, e, request, response);
    }

    /**
     * 覆盖isAccessAllowed，改变shiro的验证逻辑。<br/>
     * 避免不能多次登录的错误。
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            // 先判断是否是登录操作
            if (isLoginSubmission(request, response)) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Login submission detected.  Attempting to execute login.");
                }
                return false;
            }
        } catch (Exception e) {
            logger.error(Exceptions.getStackTraceAsString(e));
        }

        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 覆盖默认实现，用sendRedirect直接跳出框架，以免造成js框架重复加载js出错。
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 不是Ajax请求
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) || request.getParameter("ajax") == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login/timeout/success");
        }

        return false;
    }
    // friendly methods

    // private methods

    // inner class

    // test main
}
