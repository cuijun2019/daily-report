/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.etone.project.base.entity.share.Privilege;
import com.etone.project.base.entity.share.Role;
import com.etone.project.base.manager.PrivilegeManager;
import com.etone.project.base.manager.RoleManager;
import com.etone.project.base.support.BaseController;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.Combobox;
import com.etone.project.core.model.ExceptionReturn;
import com.etone.project.core.model.Result;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.SystemException;


/**
 * 角色控制器
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 140218 $$
 * @date 2014-02-18 
 */
@Controller
@RequestMapping("/base/security/role")
@Auditmeta(code = "003", name = "角色管理", symbol = "role")
public class RoleController extends BaseController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private static final String VIEW_PREFIX = "base/security/role/";

    @Autowired
    private RoleManager roleManager;
    @Autowired
    private PrivilegeManager privilegeManager;
    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    //@RequiresPermissions("privilege:list")
    @Auditmeta(code = "01", name = "查询", symbol = "list", message = "{0}进行了分页查询", url = VIEW_PREFIX + "/query")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Results<Role> query(HttpServletRequest request) {
    	Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
        Page<Role> page = roleManager.findPage(filterParamMap, Role.class);
        // 日志参数
        //LogContext.putArgs(this.getUser().account);
        // 构建返回，包括列头信息，Message在LogIntercepter中构建
        return Results.getPage(page, Role.class);
    }
    
    
    /**
     * jsp页面重定向
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"redirect/{toPage}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String redirect(@PathVariable String toPage,HttpServletRequest request, Model model) {
        return VIEW_PREFIX+toPage;
    }
    
    
    /**
     * 绑定编辑界面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/input", ""}, method = RequestMethod.POST)
    public String input(@RequestParam(value="id", required=false) String id,HttpServletRequest request) {
    	request.setAttribute("id", id);
        return VIEW_PREFIX+"role_input";
    }
    
    /**
	 * 保存
	 */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(HttpServletRequest request,Role model) {
    	Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
    	Result result = null;
		try {
			// 名称重复校验
			// 名称重复校验
			Map<String, Object> params = new HashMap();
			params.put("EQ_name", model.getName());
			Role role = roleManager.findOne(params);
            if (role != null && !role.getId().equals(model.getId())) {
            	result = new Result(Result.WARN,"名称为["+model.getName()+"]已存在,请修正!", "name");
                logger.debug(result.toString());
                return result;
            }
            
            //设置用户角色信息
			List<Privilege> privilegesList = Lists.newArrayList();
			String[] resourceIds = request.getParameterValues("resourceIds");
	    	for (String resourceId : resourceIds) {
	    		Privilege resource = privilegeManager.findOne(Long.valueOf(resourceId));
	    		privilegesList.add(resource);
	    	}
			model.setPrivileges(privilegesList);
            if(role != null){
             model.setUsers(role.getUsers());
            }
            roleManager.saveAndFlush(model);
            result = Result.successResult();
            logger.debug(result.toString());
		} catch (Exception e) {
			//异常处理，封装返回异常
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		return result;
	}
    
    
    /**
	 * 删除
	 */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
	public Result delete(HttpServletRequest request,int[] ids) throws Exception {
    	Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
    	try{
	    	List<Long> idss = new ArrayList();
	    	Iterator iter = filterParamMap.entrySet().iterator();
	    	while (iter.hasNext()) {
	    		Map.Entry entry = (Map.Entry) iter.next();
	    		idss.add(Long.valueOf((String) entry.getValue()));
	    	}
	    	roleManager.delete(idss);
    	}catch(Exception e){
    		return new Result(Result.ERROR, e.getMessage(),null);
    	}
		return Result.successResult();
    }
    
    /**
	 * 角色下拉框列表.
	 */
    @ResponseBody
    @RequestMapping(value = "/combobox", method = RequestMethod.POST)
	public Object combobox() throws Exception {
		try {
			List<Role> list = roleManager.findAll();
            List<Combobox> cList = Lists.newArrayList();
            
            for(Role r:list){
                Combobox combobox = new Combobox(r.getId()+"", r.getName());
                cList.add(combobox);
            }
            return cList;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
}
