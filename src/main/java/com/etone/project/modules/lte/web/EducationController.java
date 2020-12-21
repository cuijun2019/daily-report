/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.modules.lte.web;

import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.model.*;
import com.etone.project.modules.lte.manager.ReadInfoManager;
import com.etone.project.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线教育
 *
 */
@Controller
@RequestMapping("/modules/education")
@Auditmeta(code = "003", name = "在线教育", symbol = "education")
public class EducationController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(EducationController.class);

    @Autowired
    private ReadInfoManager readinfo;


    /**
     * 查询任务
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getQuest", method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String,Object> getQuest(HttpServletRequest request) {
        List<ReadInfo> readData = this.getReadMsg(request);
        List<ReadInfo> talkData = this.getTalkMsg(request);
        List<CommentInfo> dianpingData = this.getDianPingMsg(request);
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("read",readData);
        params.put("talk",talkData);
        params.put("dianping",dianpingData);
        return params;
    }

    /**
     * 查询用户阅读信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/getReadMsg"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<ReadInfo> getReadMsg(HttpServletRequest request) {
        String date = request.getParameter("date");
        String studentNo = request.getParameter("studentNo");
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");//可以方便地修改日期格式
//        String searchTime = null;
//        if(Integer.parseInt(date)<10){
//            searchTime = dateFormat.format(now)+"-"+"0"+date;
//        }else{
//            searchTime = dateFormat.format(now)+"-"+date;
//        }
        List<ReadInfo> data = readinfo.selectById(date, studentNo);
        return data;
    }

    /**
     * 查询用户朗读信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/getTalkMsg"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<ReadInfo> getTalkMsg(HttpServletRequest request) {
        String date = request.getParameter("date");
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");//可以方便地修改日期格式
//        String searchTime = null;
//        if(Integer.parseInt(date)<10){
//            searchTime = dateFormat.format(now)+"-"+"0"+date;
//        }else{
//            searchTime = dateFormat.format(now)+"-"+date;
//        }
        List<ReadInfo> data = readinfo.selectRloudById(date);
        return data;
    }

    /**
     * 查询用户点评信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/getDianPingMsg"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<CommentInfo> getDianPingMsg(HttpServletRequest request) {
        String date = request.getParameter("date");
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");//可以方便地修改日期格式
//        String searchTime = null;
//        if(Integer.parseInt(date)<10){
//            searchTime = dateFormat.format(now)+"-"+"0"+date;
//        }else{
//            searchTime = dateFormat.format(now)+"-"+date;
//        }
        List<CommentInfo> data = readinfo.selectCommentById(date);
        return data;
    }

    /**
     * 插入或更新
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/saveOrUpdate"}, method = {RequestMethod.POST, RequestMethod.GET})
    public boolean saveOrUpdate(HttpServletRequest request) {
        String articleid = request.getParameter("articleid");
        String article = request.getParameter("article");
        String thought = request.getParameter("thought");
        String createTime = request.getParameter("createTime");
        String isSave = request.getParameter("isSave");
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("articleid",articleid);
        params.put("article",article);
        params.put("thought",thought);
//        if (createTime == "-1"){
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
            String nowtime = dateFormat.format(now);
            params.put("createTime",nowtime);
//        }else{
//            params.put("createTime",createTime);
//        }
        params.put("isSave",isSave);
        Result result = readinfo.saveOrUpdate(params);
        return true;
    }

    /**
     * 查询未完成阅读的用户人数信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/getReadQuestNum"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> getReadQuestNum(HttpServletRequest request) {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");

        List<Map> data = readinfo.selectReadNum(beginDate,endDate);
        return data;
    }

    /**
     * 查询未完成阅读的用户名称信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/findUnFinishRead"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> findUnFinishRead(HttpServletRequest request) {

        String createTime = request.getParameter("date");

        List<Map> data = readinfo.findUnFinishRead(createTime);
        return data;
    }

    /**
     * 查询未完成朗读的用户人数信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/getTalkQuestNum"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> getTalkQuestNum(HttpServletRequest request) {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");

        List<Map> data = readinfo.selectTalkNum(beginDate,endDate);
        return data;
    }

    /**
     * 查询未完成朗读的用户名称信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/findUnFinishTalk"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> findUnFinishTalk(HttpServletRequest request) {

        String createTime = request.getParameter("date");

        List<Map> data = readinfo.findUnFinishTalk(createTime);
        return data;
    }

    /**
     * 查询未完成点评的用户人数信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/getDpQuestNum"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> getDpQuestNum(HttpServletRequest request) {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");

        List<Map> data = readinfo.selectDpNum(beginDate,endDate);
        return data;
    }

    /**
     * 查询未完成点评的用户名称信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/findUnFinishDp"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> findUnFinishDp(HttpServletRequest request) {

        String createTime = request.getParameter("date");

        List<Map> data = readinfo.findUnFinishDp(createTime);
        return data;
    }

    /**
     * 查询是否完成当天的任务
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/getQuestFinish"}, method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> getQuestFinish(HttpServletRequest request) {
        String date = request.getParameter("date");
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");//可以方便地修改日期格式
//        String searchTime = null;
//        if(Integer.parseInt(date)<10){
//            searchTime = dateFormat.format(now)+"-"+"0"+date;
//        }else{
//            searchTime = dateFormat.format(now)+"-"+date;
//        }

        List<Map> data = readinfo.getQuestFinish(date);

        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/getTaskFinish", method = {RequestMethod.GET, RequestMethod.POST})
    public void getTaskFinish(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        criteria.put("totalDay", request.getParameter("totalDay"));
        criteria.put("studentNo", shiroUser.getUser().getAccount());
        List<Map> list = readinfo.getTaskFinish(criteria);
        ResponseUtils.printArrayList(response, list);
    }
}
