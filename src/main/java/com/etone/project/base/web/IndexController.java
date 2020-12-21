/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.etone.project.base.support.security.Auditmeta;

/**
 * 首页控制器，提供用户信息修改，密码更新服务
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14159 $$
 */
@Controller
@RequestMapping("/base/main")
@Auditmeta(code = "003", name = "首页控制器", symbol = "privilege")
public class IndexController {
	
	private static final String VIEW_NORTH = "north";
	/**
     * jsp页面重定向
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"redirect/{toPage}", ""}, method = RequestMethod.POST)
    public String redirect(@PathVariable String toPage,HttpServletRequest request, Model model) {
    	if(toPage.contains("&")){
    		String[] toPages = toPage.split("&");
    		toPage = "";
    		for(int i=0;i<toPages.length;i++){
    			toPage = toPage +"/"+ toPages[i];
    		}
    	}
        return toPage;
    }
    // members

    // static block

    // constructors

    // properties

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
