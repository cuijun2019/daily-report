/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.web;

import com.etone.project.base.entity.share.Dictionary;
import com.etone.project.base.entity.share.DictionaryType;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.manager.DictionaryManager;
import com.etone.project.base.manager.DictionaryTypeManager;
import com.etone.project.base.support.BaseConstants;
import com.etone.project.base.support.log.LogContext;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.Combobox;
import com.etone.project.core.model.ExceptionReturn;
import com.etone.project.core.model.Result;
import com.etone.project.core.model.TreeNode;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.utils.CacheConstants;
import com.etone.project.utils.SelectType;
import com.google.common.collect.Lists;
import java.io.UnsupportedEncodingException;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.cache.annotation.CacheEvict;

/**
 * 数据字典控制器
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-8-22 上午9:57:59
 */
@Controller
@RequestMapping("/base/system/dictionary")
@Auditmeta(code = "003", name = "数据字典管理", symbol = "")
public class DictionaryController extends GenericController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    private static final String VIEW_PREFIX = "base/system/dictionary/";
    private static final String VIEW_INDEX = VIEW_PREFIX + BaseConstants.VIEW_INDEX;
    private static final String VIEW_EDIT = VIEW_PREFIX + BaseConstants.VIEW_EDIT;

    @Autowired
    private DictionaryManager dictionaryManager;
    @Autowired
    private DictionaryTypeManager dictionaryTypeManager;
    
    // static block

    // constructors

    // properties

    // public methods
    /**
     * jsp页面重定向
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"redirect/{toPage}", ""}, method = RequestMethod.GET)
    public String redirect(@PathVariable String toPage,HttpServletRequest request, Model model) {
        return VIEW_PREFIX+toPage;
    }
    
    /**
     * jsp页面重定向 POST方式
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"redirectp/{toPage}", ""}, method = RequestMethod.POST)
    public String redirectp(@PathVariable String toPage,HttpServletRequest request, Model model) {
        return VIEW_PREFIX+toPage;
    }

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/query", method = {RequestMethod.POST,RequestMethod.GET})
    public Results<Dictionary> query(HttpServletRequest request) throws UnsupportedEncodingException {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
//    	Map<String, Object> getParameters = getParameters(request);
//    	User user = getUser();
        Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
        filterParamMap.put("LIKE_createUser", getUser().id);
        this.mapToUtf8(filterParamMap); //转码成UTF8

        Page<Dictionary> page = dictionaryManager.findPage(filterParamMap, Dictionary.class);
        // 日志参数
        LogContext.putArgs(this.getUser().account);
        // 构建返回，包括列头信息，Message在LogIntercepter中构建
        return Results.getPage(page, Dictionary.class);
    }

    /**
     * 数据字典类型    下拉列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    //@RequiresPermissions("privilege:list")
    @Auditmeta(code = "01", name = "查询", symbol = "combobox", message = "{0}进行了分页查询", url = VIEW_PREFIX + "/query")
    @RequestMapping(value = "/combobox/{selectType}", method = RequestMethod.POST)
    public List<Combobox> combobox(@PathVariable String selectType,HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        List<DictionaryType> list = dictionaryTypeManager.findAll();
        List<Combobox> cList = Lists.newArrayList();
        //为combobox添加  "---全部---"、"---请选择---"
//        if(!StringUtils.isBlank(selectType)){
//        	SelectType s = SelectType.getSelectTypeValue(selectType);
//        	if(s!=null){
//        		Combobox selectCombobox = new Combobox("", s.getDescription());
//        		cList.add(selectCombobox);
//        	}
//        }
        for(DictionaryType d:list){
            List<DictionaryType> subDictionaryTypes = d.subDictionaryTypes;
            for(DictionaryType subDictionaryType:subDictionaryTypes){
                Combobox combobox = new Combobox(subDictionaryType.code, subDictionaryType.name,d.name);
                cList.add(combobox);
            }

        }
        // 构建返回，包括列头信息，Message在LogIntercepter中构建
        return cList;
    }
    
    /**
     * 数据字典   下拉列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/combotree/{selectType}", method = RequestMethod.POST)
    public List<TreeNode> combotree(@PathVariable String selectType,HttpServletRequest request,Dictionary dictionary) {
    	Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
    	List<TreeNode> titleList = Lists.newArrayList();
		// 添加 "---全部---"、"---请选择---"
		if (!StringUtils.isBlank(selectType)) {
			SelectType s = SelectType.getSelectTypeValue(selectType);
			if (s != null) {
				TreeNode selectTreeNode = new TreeNode("",
						s.getDescription());
				titleList.add(selectTreeNode);
			}
		}
		List<TreeNode> treeNodes = dictionaryManager
				.getByDictionaryTypeCode(dictionary,dictionary.getDictionaryTypeCode(),dictionary.getId(), true);

		List<TreeNode> unionList = ListUtils.union(titleList, treeNodes);
        return unionList;
    }
    
    /**
     * 排序最大值
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/maxsort", method = RequestMethod.GET)
    public Result maxSort(HttpServletRequest request) {
    	Integer maxSort = dictionaryManager.getMaxSort();
		Result result = new Result(Result.SUCCESS, null, maxSort);
        return result;
    }
    
    /**
	 * 保存
	 */
    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    @CacheEvict(value = { CacheConstants.DICTIONARYS_BY_TYPE_CACHE},allEntries = true)
	public Object save(HttpServletRequest request,Dictionary model) {
//    	基于json传参
//    	Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
//    	String data = request.getParameter("data");
//    	Dictionary model = JsonUtil.fromJson(data, Dictionary.class);
    	Result result = null;
		try {
                        model.createUser = this.getUser().id.toString();
			// 名称是否重复校验
			Map<String, Object> params = new HashMap();
			params.put("EQ_name", model.getName());
			params.put("EQ_createUser", String.valueOf(this.getUser().id));
			Dictionary dictionary = dictionaryManager.findOne(params);
			if (dictionary != null
					&& !dictionary.getId().equals(model.getId())) {
				result = new Result(Result.WARN, "名称为[" + model.getName()
						+ "]已存在,请修正!", "name");
				logger.debug(result.toString());
				return result;
			}

			// 编码是否重复校验
			params.remove("EQ_name");
//			params.put("EQ_code", model.getCode());
//			Dictionary dictionary2 = dictionaryManager.findOne(params);
//			if (dictionary2 != null
//					&& !dictionary2.getId().equals(model.getId())) {
//				result = new Result(Result.WARN, "编码为[" + model.getCode()
//						+ "]已存在,请修正!", "code");
//				logger.debug(result.toString());
//				return result;
//			}

			// 设置上级节点
//			params.put("EQ_code", model.getParentDictionaryCode());
//			if (!StringUtils.isEmpty(model.getParentDictionaryCode())) {
//				Dictionary parentDictionary = dictionaryManager.findOne(params);
//				if(parentDictionary == null){
//					logger.error("上级数据字典[{}]已被删除.",model.getParentDictionaryCode());
//				}
//				model.setParentDictionary(parentDictionary);
//			}else {
//				model.setParentDictionary(null);
//			}

			// 设置字典类型
//			params.remove("code");
//			params.put("EQ_code", model.getDictionaryTypeCode());
//			if (!StringUtils.isEmpty(model.getDictionaryTypeCode())) {
//				DictionaryType dictionaryType3 = dictionaryTypeManager.findOne(params);
//				model.setDictionaryType(dictionaryType3);
//			}else{
//				logger.error("字典类型为空.");
//			}

			dictionaryManager.saveAndFlush(model);
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
    	List<Long> idss = new ArrayList();
    	Iterator iter = filterParamMap.entrySet().iterator();
    	while (iter.hasNext()) {
    		Map.Entry entry = (Map.Entry) iter.next();
    		idss.add(Long.valueOf((String) entry.getValue()));
    	}
    	dictionaryManager.delete(idss);
		return Result.successResult();
    }
    
/****************************************************字典类型管理********************************************/
    
    /**
     * 字典类型管理  列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/querytype", method = RequestMethod.POST)
    public Results<DictionaryType> queryType(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
        
        List<DictionaryType> list = dictionaryTypeManager.findAll();
        //Page<Dictionary> page = (List<DictionaryType>) dictionaryTypeManager.findPage(filterParamMap, DictionaryType.class);
        //Results.getPage(page, Dictionary.class);
        // 日志参数
        LogContext.putArgs(this.getUser().account);
        // 构建返回，包括列头信息，Message在LogIntercepter中构建
        return Results.getList(list, DictionaryType.class);
    }
    
    
    /**
     * 排序最大值
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/maxsorttype", method = RequestMethod.GET)
    public Result maxSortType(HttpServletRequest request) {
    	Integer maxSort = dictionaryTypeManager.getMaxSort();
		Result result = new Result(Result.SUCCESS, null, maxSort);
        return result;
    }
    
    /**
	 * 删除
	 */
    @ResponseBody
    @RequestMapping(value = "/deletetype", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
	public Result deleteType(HttpServletRequest request) throws Exception {
    	Result result = null;
    	try{
	    	Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
	    	List<Long> idss = new ArrayList();
	    	Iterator iter = filterParamMap.entrySet().iterator();
	    	while (iter.hasNext()) {
	    		Map.Entry entry = (Map.Entry) iter.next();
	    		idss.add(Long.valueOf((String) entry.getValue()));
	    	}
    		dictionaryTypeManager.delete(idss);
    	}catch(Exception e){
    		result = new Result(Result.WARN, "字典表已经使用",
                    e.getMessage());
    		logger.error("Exception: ", e);
    		logger.debug(result.toString());
			return result;
    	}
		return Result.successResult();
    }
    
    /**
     * 数据字典类型    下拉列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comboboxtype/{selectType}", method = RequestMethod.POST)
    public List<Combobox> comboboxType(@PathVariable String selectType,HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        List<DictionaryType> list = dictionaryTypeManager.findAll();
        List<Combobox> cList = Lists.newArrayList();
        //为combobox添加  "---全部---"、"---请选择---"
        if(!StringUtils.isBlank(selectType)){
        	SelectType s = SelectType.getSelectTypeValue(selectType);
        	if(s!=null){
        		Combobox selectCombobox = new Combobox("", s.getDescription());
        		cList.add(selectCombobox);
        	}
        }
        for(DictionaryType d:list){
        	DictionaryType subDictionaryType = d.getGroupDictionaryType();
            if(subDictionaryType == null){
            	 Combobox combobox = new Combobox(d.getCode(), d.getName());
                 cList.add(combobox);
            }
        }
        // 构建返回，包括列头信息，Message在LogIntercepter中构建
        return cList;
    }
    
    /**
	 * 保存
	 */
    @ResponseBody
    @RequestMapping(value = "/savetype", method = RequestMethod.POST)
	public Result saveType(HttpServletRequest request,DictionaryType model){
    	Result result = null;
		try {
			
			Map<String, Object> params = new HashMap();
			if(model.getGroupDictionaryTypeCode()!=null&&!"".equals(model.getGroupDictionaryTypeCode())){
				params.put("EQ_code", model.getGroupDictionaryTypeCode());
			}
			DictionaryType groupDictionaryType = dictionaryTypeManager.findOne(params);
            model.setGroupDictionaryType(groupDictionaryType);
			
            // 名称是否重复校验
            params.clear();
			params.put("EQ_name", model.getName());
			DictionaryType dictionaryType = dictionaryTypeManager.findOne(params);
			if (dictionaryType != null
					&& !dictionaryType.getId().equals(model.getId())) {
				result = new Result(Result.WARN, "名称为[" + model.getName()
						+ "]已存在,请修正!", "name");
				logger.debug(result.toString());
				return result;
			}

			// 编码是否重复校验
			params.clear();
			params.put("EQ_code", model.getCode());
			DictionaryType dictionaryType2 = dictionaryTypeManager.findOne(params);
			if (dictionaryType2 != null
					&& !dictionaryType2.getId().equals(model.getId())) {
				result = new Result(Result.WARN, "编码为[" + model.getCode()
						+ "]已存在,请修正!", "code");
				logger.debug(result.toString());
				return result;
			}

			//修改操作 避免自关联数据的产生
            if (model.getId() != null) {
                if (model.getCode().equals(model.getGroupDictionaryTypeCode())) {
                    result = new Result(Result.ERROR, "不允许发生自关联.",
                            null);
                    logger.debug(result.toString());
                    return result;
                }
            }
            
			dictionaryTypeManager.saveAndFlush(model);
			result = Result.successResult();
			logger.debug(result.toString());
		} catch (Exception e) {
			result = new Result(Result.WARN, "字典表已经使用",
                    e.getMessage());
    		logger.debug(result.toString());
			return result;
		}
		return result;

	}
    
    public void mapToUtf8(Map<String, Object> filterParamMap) throws UnsupportedEncodingException {
        for(String key : filterParamMap.keySet()) {
            Object value = filterParamMap.get(key);
            String valueStr = new String(value.toString().getBytes("iso-8859-1"), "utf-8");
            filterParamMap.put(key, valueStr);
        }
    }
}
