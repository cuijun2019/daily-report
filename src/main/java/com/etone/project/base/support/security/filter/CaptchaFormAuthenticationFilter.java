/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.security.filter;

import com.etone.project.base.support.BaseConstants;
import com.etone.project.base.support.security.CaptchaUsernamePasswordToken;
import com.etone.project.core.utils.encode.Encrypt;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 登录操作过滤器（POST）,带验证码处理
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14158 $$
 */
public class CaptchaFormAuthenticationFilter extends BaseFormAuthenticationFilter {
    // members
    public static final Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);
    private String captchaParam = BaseConstants.CAPTCHA_KEY;
    // static block

    // constructors

    // properties
    public String getCaptchaParam() {
        return captchaParam;
    }
    // public methods

    // protected methods

    /**
     * 获取验证码信息
     *
     * @param request
     * @return
     */
    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    /**
     * 创建带验证码的登录令牌
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        //return new CaptchaUsernamePasswordToken(username, Encrypt.e(password), rememberMe, host, captcha);
        return new CaptchaUsernamePasswordToken(username, password, rememberMe, host, captcha);
    }
    // friendly methods

    // private methods

    // inner class

    // test main
}
