/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码登录验证异常
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14064 $$
 */
public class IncorrectCaptchaException extends AuthenticationException {
    // members
    private static final long serialVersionUID = 3675801475398850921L;

    // static block

    // constructors
    public IncorrectCaptchaException() {
   		super();
   	}

   	public IncorrectCaptchaException(String message, Throwable cause) {
   		super(message, cause);
   	}

   	public IncorrectCaptchaException(String message) {
   		super(message);
   	}

   	public IncorrectCaptchaException(Throwable cause) {
   		super(cause);
   	}
    // properties

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
