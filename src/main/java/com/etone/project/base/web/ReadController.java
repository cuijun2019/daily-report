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
@RequestMapping("/base/read")
@Auditmeta(code = "003", name = "阅读管理", symbol = "read")
public class ReadController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(ReadController.class);
    public static final String VIEW_SIGN = "read";

    /**
     * 绑定阅读视图
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"read", ""}, method = RequestMethod.GET)
    public String read(HttpServletRequest request, Model model) {
        return VIEW_SIGN;
    }

//    @ResponseBody
//    @RequestMapping(value = "/getQuest", method = {RequestMethod.POST, RequestMethod.GET})
//    public String getQuest(HttpServletRequest request) {
//        String date = request.getParameter("date");
//        String quest = date+"quest is not done";
//        return JsonUtil.toJson(quest);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/getSignFun", method = {RequestMethod.POST, RequestMethod.GET})
//    public String getSignFun(HttpServletRequest request) {
////        String date = request.getParameter("date");
//        int sign[] = {1,2,3,5,6,9,11,15,19,20,21,22,23,26,28};
//        return JsonUtil.toJson(sign);
//    }
}
