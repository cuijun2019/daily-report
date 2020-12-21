/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.modules.lte.web;

import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.utils.DateUtil;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.dao.LteMrAdvanceMapper;
import com.etone.project.modules.lte.entity.LteMrDto;
import com.etone.project.modules.lte.entity.LteMrLoadRunner;
import com.etone.project.modules.lte.manager.ILteMrAdvanceManager;
import com.google.common.collect.Lists;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
@Controller
@RequestMapping("/modules/ltemr/advance")
@Auditmeta(code = "003", name = "Mr预统计模块", symbol = "")
public class LteMrAdvanceController extends GenericController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(LteMrAdvanceController.class);
    private static final String VIEW_PREFIX = "/modules/ltemr/";
    private static final String VIEW_INDEX = VIEW_PREFIX + "ltemradvance_index";

    @Autowired
    private ILteMrAdvanceManager lteMrAdvanceManager;
    @Autowired
	private LteMrAdvanceMapper lteMrAdvanceMapper;

    /**
     * 原始数据小时粒度记录数查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findhournum/{type}", method = RequestMethod.POST)
    public Object findHourNum(@PathVariable String type,HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        this.initParameters(criteria, query);
        criteria.put("type", type);
        List<Map> result = lteMrAdvanceManager.findHourNum(criteria);
        return result;
    }
    
    /**
     * 预统计小时粒度记录数查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findadvance", method = RequestMethod.POST)
    public Object findAdvance(HttpServletRequest request) {
    	//Map<String, Object> query1 = getParameters(request);
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        String type = (String)query.get("type");
        String cellId = (String)query.get("cellId");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("type", type);
        if(cellId != null){
        	List<String> cellIdList = Arrays.asList(cellId.split(","));
        	criteria.put("cellId", cellIdList);
        }
        PageResult<Map> page = lteMrAdvanceManager.findAdvance(criteria);
        return Results.getPage(page, LteMrDto.class);
    }
    
    
    
    /**
     * 预统计进程
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/advancecalcul", method = RequestMethod.POST)
    public Object advanceCalcul(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
		Result result = null;
		ExecutorService exec = Executors.newFixedThreadPool(1);
		User user = lteMrAdvanceManager.getUser();
		try{
			QueryCriteria criteriaResult = new QueryCriteria();
                        this.initParameters(criteriaResult, query); //初始化查询参数
			criteriaResult.put("vcResultCode", "0");
			PageResult<Map> page = lteMrAdvanceManager.loadADdvanceLog(criteriaResult);
			List<Map> resultLogList = page.getResult();
			if(resultLogList == null || resultLogList.size()==0){
				//预统计并入库并删除原始数据
				QueryCriteria criteria = new QueryCriteria();
                                this.initParameters(criteria, query);
				criteria.put("timeStart", query.get("timeStart"));
				criteria.put("timeEnd", query.get("query"));
				criteria.put("userId", user.getId());
				List<Map> dateList = lteMrAdvanceMapper.findMinMaxTime(criteria);
				Map dateMap = new HashMap();
				if(dateList.size()>0){
					dateMap = dateList.get(0);
					List<QueryCriteria> dateQueryList = Lists.newArrayList();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
					String minDate = df.format((Date)dateMap.get("minTime"));
					String maxDate = df.format((Date)dateMap.get("maxTime"));
					String mineNodeBID = (String)dateMap.get("mineNodeBID");
					String maxeNodeBID = (String)dateMap.get("maxeNodeBID");
					long hours = DateUtil.dateHoursub((Date)dateMap.get("minTime"),(Date)dateMap.get("maxTime"));
					if(hours == 0l){
						String minDateAdd = DateUtil.addHours(minDate, 1);
                                                
						QueryCriteria criteriaOne = new QueryCriteria();
						criteriaOne.put("timeStart",minDate);
						criteriaOne.put("timeEnd", minDateAdd);
						criteriaOne.put("mineNodeBID", mineNodeBID);
						criteriaOne.put("maxeNodeBID", maxeNodeBID);
						criteriaOne.put("userId", user.getId());   
						setFaZhitoQueryCriteria(criteriaOne);
						dateQueryList.add(criteriaOne);
						exec.execute(new LteMrLoadRunner(lteMrAdvanceManager,dateQueryList));
					}else {
						for(int i=1;i<=hours;i++){
							QueryCriteria criteriaMany = new QueryCriteria();
                                                        criteriaMany.getCondition().putAll(criteria.getCondition());
							String minDateAdd1 = DateUtil.addHours(minDate, i-1);
							String minDateAdd2 = DateUtil.addHours(minDate, i);
							if(!minDate.equals(minDateAdd2)){
								criteriaMany.put("timeStart",minDateAdd1);
								criteriaMany.put("timeEnd", minDateAdd2);
								criteriaMany.put("mineNodeBID", mineNodeBID);
								criteriaMany.put("maxeNodeBID", maxeNodeBID);
								criteriaMany.put("userId", user.getId());  
								setFaZhitoQueryCriteria(criteriaMany);
								dateQueryList.add(criteriaMany);
							}
						}
						exec.execute(new LteMrLoadRunner(lteMrAdvanceManager,dateQueryList));
					}
				}
			} else {
				new Result(Result.WARN, "已有统计正在进行,请不要重复提交",null);
			}
			//result = lteMrAdvanceManager.advanceCalcul(criteria);
    	}catch(Exception e){
    		result = new Result(Result.ERROR, "预统计出现异常",
                    e.getMessage());
    		logger.error("Exception: ", e);
    		logger.debug(result.toString());
			return result;
    	}
        return new Result(Result.SUCCESS, "已经开始预统计",null);
    }
    
    /**
     * 预统日志查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadADdvanceLog", method = RequestMethod.POST)
    public Object loadADdvanceLog(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        //List<Map> result = lteMrAdvanceManager.loadADdvanceLog(criteria);
        PageResult<Map> page = lteMrAdvanceManager.loadADdvanceLog(criteria);
        return Results.getPage(page, LteMrDto.class);
    }
    
    /**
     * ftp配置查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryFtpConfig", method = RequestMethod.POST)
    public Object queryFtpConfig(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("vcSystemCode", "ftp");
        PageResult<Map> page = lteMrAdvanceManager.queryFtpConfig(criteria);
        return Results.getPage(page, LteMrDto.class);
    }
    
    /**
     * 日志查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/querySysLog", method = RequestMethod.POST)
    public Object querySysLog(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("vcSystemCode", "sys");
        PageResult<Map> page = lteMrAdvanceManager.queryFtpConfig(criteria);
        return Results.getPage(page, LteMrDto.class);
    }
    
    /**
     * ftp配置删除
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteFtpConfig", method = RequestMethod.POST)
    public Object deleteFtpConfig(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        //List<Map> result = lteMrAdvanceManager.loadADdvanceLog(criteria);
        Result result = lteMrAdvanceManager.deleteFtpConfig(criteria);
        return result;
    }
    
    /**
     * ftp配置修改
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateFtpConfig", method = RequestMethod.POST)
    public Object updateFtpConfig(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        this.initParameters(criteria, query);
        criteria.put("bigId", query.get("bigId"));
        criteria.put("vcModuleCode", query.get("vcModuleCode"));
        criteria.put("vcModule", query.get("vcModule"));
        criteria.put("vcLogLevelName", query.get("vcLogLevelName"));
        criteria.put("vcLogTypeName", query.get("vcLogTypeName"));
        criteria.put("vcContent", query.get("vcContent"));
        //List<Map> result = lteMrAdvanceManager.loadADdvanceLog(criteria);
        Result result = lteMrAdvanceManager.updateFtpConfig(criteria);
        return result;
    }
    
    /**
     * ftp配置插入
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertFtpConfig", method = RequestMethod.POST)
    public Object insertFtpConfig(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        //List<Map> result = lteMrAdvanceManager.loadADdvanceLog(criteria);
        Result result = lteMrAdvanceManager.insertFtpConfig(criteria);
        return result;
    }
    
    /**
     * 小区列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findCellInfo",  method = {RequestMethod.POST, RequestMethod.GET})
    public Object findCellInfo(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        this.initParameters(criteria, query);
        criteria.put("cellId", query.get("cellId"));
        List<Map> result = lteMrAdvanceManager.findCellInfo(criteria);
        return result;
    }
}
