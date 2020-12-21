package com.etone.project.modules.lte.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etone.project.utils.BrowserAndIPAddrUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.BaseManager;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.utils.DateUtil;
import com.etone.project.modules.lte.dao.LteMrAdvanceMapper;
import com.etone.project.modules.lte.dao.LteMrMapper;
import com.etone.project.modules.lte.entity.LteMrDto;
import com.etone.project.modules.lte.entity.LteMrLoadRunner;
import com.etone.project.modules.lte.manager.ILteMrAdvanceManager;
import com.etone.project.modules.lte.manager.ILteMrManager;
import com.etone.project.modules.lte.manager.ILteSysLogManager;
import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletRequest;

/**
 * Mr预统计模块
 * 
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
@Service
@Transactional
public class LteSysLogManager implements ILteSysLogManager {

	@Autowired
	private LteMrAdvanceMapper lteMrAdvanceMapper;

	/**
	 * ftp配置插入
	 * 
	 * @param criteria
	 * @return
	 */
	public void insertSysLog(String sysType,HttpServletRequest request) {
		QueryCriteria criteria = new QueryCriteria();
		User user = this.getUser();
		if("login".equals(sysType)){
			criteria.put("vcSystemCode", "sys");
			criteria.put("vcModuleCode", user.getAccount());
			criteria.put("vcModule", "系统管理");
			criteria.put("vcContent", "登陆系统!");
			criteria.put("dtCreateTime", new Date());
            criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr((HttpServletRequest) request));//IP
            criteria.put("vcRemark", BrowserAndIPAddrUtils.checkBrowse((HttpServletRequest)request)); //浏览器类型
			lteMrAdvanceMapper.insertSysLog(criteria);
		}else if("network_query".equals(sysType)){
			criteria.put("vcSystemCode", "sys");
			criteria.put("vcModuleCode", user.getAccount());
			criteria.put("vcModule", "网络整体评估");
			criteria.put("vcContent", "网络整体评估查询");
			criteria.put("dtCreateTime", new Date());
            criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr((HttpServletRequest)request));//IP
            criteria.put("vcRemark", BrowserAndIPAddrUtils.checkBrowse((HttpServletRequest)request)); //浏览器类型
			lteMrAdvanceMapper.insertSysLog(criteria);
		}else if("rfg_query".equals(sysType)){
			criteria.put("vcSystemCode", "sys");
			criteria.put("vcModuleCode", user.getAccount());
			criteria.put("vcModule", "弱覆盖分析");
			criteria.put("vcContent", "弱覆盖查询");
			criteria.put("dtCreateTime", new Date());
            criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr((HttpServletRequest)request));//IP
            criteria.put("vcRemark", BrowserAndIPAddrUtils.checkBrowse((HttpServletRequest)request)); //浏览器类型
			lteMrAdvanceMapper.insertSysLog(criteria);
		}else if("upgr_query".equals(sysType)){
			criteria.put("vcSystemCode", "sys");
			criteria.put("vcModuleCode", user.getAccount());
			criteria.put("vcModule", "上行干扰分析");
			criteria.put("vcContent", "上行干扰分析查询");
			criteria.put("dtCreateTime", new Date());
            criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr((HttpServletRequest)request));//IP
            criteria.put("vcRemark", BrowserAndIPAddrUtils.checkBrowse((HttpServletRequest)request)); //浏览器类型
			lteMrAdvanceMapper.insertSysLog(criteria);
		}else if("downgr_query".equals(sysType)){
			criteria.put("vcSystemCode", "sys");
			criteria.put("vcModuleCode", user.getAccount());
			criteria.put("vcModule", "下行干扰分析");
			criteria.put("vcContent", "下行干扰分析");
			criteria.put("dtCreateTime", new Date());
            criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr((HttpServletRequest)request));//IP
            criteria.put("vcRemark", BrowserAndIPAddrUtils.checkBrowse((HttpServletRequest)request)); //浏览器类型
			lteMrAdvanceMapper.insertSysLog(criteria);
		}else if("acess_query".equals(sysType)){
			criteria.put("vcSystemCode", "sys");
			criteria.put("vcModuleCode", user.getAccount());
			criteria.put("vcModule", "接入问题分析");
			criteria.put("vcContent", "接入问题分析查询");
			criteria.put("dtCreateTime", new Date());
            criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr((HttpServletRequest)request));//IP
            criteria.put("vcRemark", BrowserAndIPAddrUtils.checkBrowse((HttpServletRequest)request)); //浏览器类型
			lteMrAdvanceMapper.insertSysLog(criteria);
		}else if("switch_query".equals(sysType)){
			criteria.put("vcSystemCode", "sys");
			criteria.put("vcModuleCode", user.getAccount());
			criteria.put("vcModule", "切换问题分析");
			criteria.put("vcContent", "切换问题分析");
			criteria.put("dtCreateTime", new Date());
            criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr((HttpServletRequest)request));//IP
            criteria.put("vcRemark", BrowserAndIPAddrUtils.checkBrowse((HttpServletRequest)request)); //浏览器类型
			lteMrAdvanceMapper.insertSysLog(criteria);
		}else if("offline_query".equals(sysType)){
			criteria.put("vcSystemCode", "sys");
			criteria.put("vcModuleCode", user.getAccount());
			criteria.put("vcModule", "掉线问题分析");
			criteria.put("vcContent", "掉线问题分析");
			criteria.put("dtCreateTime", new Date());
            criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr((HttpServletRequest)request));//IP
            criteria.put("vcRemark", BrowserAndIPAddrUtils.checkBrowse((HttpServletRequest)request)); //浏览器类型
			lteMrAdvanceMapper.insertSysLog(criteria);
		}
	    
	}

	/**
	 * 取得当前用户信息
	 * 
	 * @return
	 */
	public User getUser() {
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return shiroUser.getUser();
	}

}
