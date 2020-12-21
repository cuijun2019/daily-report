/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support;

import com.etone.ee.modules.web.Servlets;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.security.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 基础控制器
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14093 $$
 */
public abstract class BaseController {
    // members

    // static block

    // constructors

    // properties

    /**
     * 取得当前用户信息
     *
     * @return
     */
    public User getUser() {
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUser();
    }


    // public methods

    // protected methods

    /**
     * 提取查询参数
     *
     * @param request
     * @return
     */
    protected Map<String, Object> getParameters(HttpServletRequest request) {
        return Servlets.getParameters(request, "");
    }
    // friendly methods

    // private methods

    // inner class

    // test main
}
