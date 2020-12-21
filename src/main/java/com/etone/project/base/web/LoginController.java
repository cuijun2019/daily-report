/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.web;

import com.etone.commons.util.Exceptions;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.base.support.security.IncorrectCaptchaException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14093 $$
 */
@Controller
@RequestMapping("/login")
@Auditmeta(code = "000", name = "登录管理", symbol = "login")
public class LoginController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    public static final String VIEW_LOGIN = "login";
    public static final String VIEW_DIALOG = "main/index/modify";
    // static block

    // constructors

    // properties

    // public methods

    /**
     * 绑定登录视图
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"login", ""}, method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        return VIEW_LOGIN;
    }

    /**
     * 登录失败处理
     *
     * @param account 用户输入的用户名
     * @param model 视图通信模型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String failure(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String account, Model model, HttpServletRequest request) {
        // 提取登录失败原因
        String reason = extractReason(request);
        model.addAttribute("reason", reason);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, account);
        return VIEW_LOGIN;
//        return "redirect:/main";
    }
    // protected methods

    // friendly methods

    // private methods

    /**
     * 提取登录异常原因
     *
     * @param request 请求
     * @return
     */
    private String extractReason(HttpServletRequest request){
        String reasonName = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        logger.info(reasonName);
      		Class<?> reasonType = null;
      		try {
      			if (reasonName != null) {
      				reasonType = Class.forName(reasonName);
      			}
      		} catch (ClassNotFoundException e) {
      			logger.error(Exceptions.getStackTraceAsString(e));
      		}

      		String reason = "其他错误！";
      		if (reasonType != null) {
      			if (reasonType.equals(UnknownAccountException.class))
      				reason = "用户不存在！";
      			else if (reasonType.equals(IncorrectCredentialsException.class))
      				reason = "密码错误！";
      			else if (reasonType.equals(IncorrectCaptchaException.class))
      				reason = "验证码错误！";
      			else if (reasonType.equals(AuthenticationException.class))
      				reason = "认证失败！";
      			else if (reasonType.equals(DisabledAccountException.class))
      				reason = "用户已被冻结！";
      		}
      		return "登录失败，" + reason;
    }
    // inner class

    // test main
}
