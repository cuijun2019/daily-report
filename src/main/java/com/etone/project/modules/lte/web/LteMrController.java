/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.modules.lte.web;

import com.etone.project.base.support.BaseConstants;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.ILteMrManager;
import com.etone.project.modules.lte.manager.ILteSysLogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * MR工具
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-8-22 上午9:57:59
 */
@Controller
@RequestMapping("/modules/ltemr")
@Auditmeta(code = "003", name = "MR工具", symbol = "")
public class LteMrController extends GenericController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(LteMrController.class);
    private static final String VIEW_PREFIX = "modules/ltemr/";
    private static final String VIEW_INDEX = VIEW_PREFIX + BaseConstants.VIEW_INDEX;
    private static final String VIEW_EDIT = VIEW_PREFIX + BaseConstants.VIEW_EDIT;
    
    @Autowired
	private ILteMrManager lteMrManager;
    @Autowired
    private ILteSysLogManager lteSysLogManager;
    /**
     * 小区级列表分页查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryc", method = RequestMethod.POST)
    public Result queryC(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
        
        return Result.successResult();
    }
    
    
    /**
     * 全网覆盖类列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findfglist", method = RequestMethod.POST)
    public Results<Map> findFgList(HttpServletRequest request) {
    	lteSysLogManager.insertSysLog("network_query",request);
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        PageResult<Map> page = lteMrManager.findFgList(criteria);
        return Results.getPage(page, Map.class);
    }
    
    /**
     * 全网干扰类列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findgrlist", method = RequestMethod.POST)
    public Results<Map> findGrList(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        PageResult<Map> page = lteMrManager.findGrList(criteria);
        return Results.getPage(page, Map.class);
    }
    
    /**
     * RSRP小区数查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rsrpcell", method = RequestMethod.POST)
    public Results<Map> rsrpCell(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        PageResult<Map> page = lteMrManager.rsrpCell(criteria);
        return Results.getPage(page, Map.class);
    }
    
    /**
     * PHR小区数查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/phrcell", method = RequestMethod.POST)
    public Results<Map> phrCell(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        PageResult<Map> page = lteMrManager.phrCell(criteria);
        return Results.getPage(page, Map.class);
    }
    
    /**
     * RIP小区数查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ripcell", method = RequestMethod.POST)
    public Results<Map> ripCell(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        PageResult<Map> page = lteMrManager.ripCell(criteria);
        return Results.getPage(page, Map.class);
    }

    
    /**
     * RSRQ小区数查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rsrqcell", method = {RequestMethod.POST, RequestMethod.GET})
    public Results<Map> rsrqCell(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        PageResult<Map> page = lteMrManager.rsrqCell(criteria);
        return Results.getPage(page, Map.class);
    }
    
    /**
     * 重叠覆盖度小区数查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mccell", method = RequestMethod.POST)
    public Results<Map> mcCell(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        PageResult<Map> page = lteMrManager.mcCell(criteria);
        return Results.getPage(page, Map.class);
    }
    
    /**
     * 模三干扰小区数查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mo3cell", method = RequestMethod.POST)
    public Results<Map> mo3Cell(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        PageResult<Map> page = lteMrManager.mo3Cell(criteria);
        return Results.getPage(page, Map.class);
    }
    
    /**
     * 小区级列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findcelllist", method = {RequestMethod.POST, RequestMethod.GET})
    public Results<Map> findCellList(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeCellStart", query.get("timeCellStart"));
        criteria.put("timeCellEnd", query.get("timeCellEnd"));
        PageResult<Map> page = lteMrManager.findCellList(criteria);
        return Results.getPage(page, Map.class);
    }
}
