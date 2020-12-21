/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 扩展Shiro的Token, 使其附带验证码信息
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14064 $$
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
    // members
    private static final long serialVersionUID = -6187036376774777020L;
    private String captcha;
    // static block

    // constructors
    public CaptchaUsernamePasswordToken() {
        super();
    }

    /**
     * 带参构造函数
     *
     * @param username 用户名
     * @param password 密码
     * @param rememberMe 是否添加到Cookie
     * @param host 客户机信息
     * @param captcha 验证码
     */
    public CaptchaUsernamePasswordToken(String username, String password, boolean rememberMe, String host, String captcha) {
   		super(username, password, rememberMe, host);
   		this.captcha = captcha;
   	}

    // properties

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
