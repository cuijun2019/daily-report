/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.web;

import com.etone.commons.json.JsonUtil;
import com.etone.project.base.support.security.Auditmeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/base/sign")
@Auditmeta(code = "003", name = "签到管理", symbol = "sign")
public class SignController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(SignController.class);
    public static final String VIEW_SIGN = "sign";

    /**
     * 绑定签到视图
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"sign", ""}, method = RequestMethod.GET)
    public String sign(HttpServletRequest request, Model model) {
        return VIEW_SIGN;
    }

    @ResponseBody
    @RequestMapping(value = "/getQuest", method = {RequestMethod.POST, RequestMethod.GET})
    public String getQuest(HttpServletRequest request) {
        String date = request.getParameter("date");
        String quest = date+"quest is not done";
        return JsonUtil.toJson(quest);
    }

}
