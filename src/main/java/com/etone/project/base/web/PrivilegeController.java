/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.web;

import com.etone.project.base.entity.share.Privilege;
import com.etone.project.base.entity.share.Role;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.manager.PrivilegeManager;
import com.etone.project.base.manager.UserManager;
import com.etone.project.base.support.BaseConstants;
import com.etone.project.base.support.BaseController;
import com.etone.project.base.support.log.LogContext;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.base.type.PrivilegeKind;
import com.etone.project.core.model.Combobox;
import com.etone.project.core.model.ExceptionReturn;
import com.etone.project.core.model.Result;
import com.etone.project.core.model.TreeNode;
import com.etone.project.core.utils.StringUtils;
import com.etone.project.utils.AppConstants;
import com.etone.project.utils.SelectType;
import com.google.common.collect.Lists;

import org.apache.commons.collections.ListUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 模块控制器
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 140218 $$
 * @date 2014-02-18 
 */
@Controller
@RequestMapping("/base/security/privilege")
@Auditmeta(code = "003", name = "权限管理", symbol = "privilege")
public class PrivilegeController extends BaseController {
    // members
    private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);
    private static final String VIEW_PREFIX = "base/security/privilege/";
    private static final String VIEW_INDEX = VIEW_PREFIX + BaseConstants.VIEW_INDEX;
    private static final String VIEW_EDIT = VIEW_PREFIX + BaseConstants.VIEW_EDIT;

    @Autowired
    private PrivilegeManager privilegeManager;

    // 账号服务
    @Autowired
    protected UserManager userManager;
    /**
     * 列表分页查询
     *
     * @param request
     * @return
    
    @ResponseBody
    //@RequiresPermissions("privilege:list")
    @Auditmeta(code = "01", name = "查询", symbol = "list", message = "{0}进行了分页查询", url = VIEW_PREFIX + "/query")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Results<Privilege> query(HttpServletRequest request) {
        Page<Privilege> page = privilegeManager.findPage(getParameters(request), Privilege.class);
        // 日志参数
        LogContext.putArgs(this.getUser().account);
        // 构建返回，包括列头信息，Message在LogIntercepter中构建
        return Results.getPage(page, Privilege.class);
    }
     */
    
    /**
     * jsp页面重定向
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"redirect/{toPage}"}, method = RequestMethod.GET)
    public String redirect(@PathVariable String toPage,HttpServletRequest request, Model model) {
        return VIEW_PREFIX+toPage;
    }
    
    /**
     * jsp页面重定向
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/redirect/{toPage}"}, method = RequestMethod.POST)
    public String redirectp(@PathVariable String toPage,HttpServletRequest request, Model model) {
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
        return VIEW_PREFIX+"resource_input";


    }

    /**
     * 根据用户权限展开权限树
     * @param parentId
     * @return
     */
    @ResponseBody
    //@RequiresPermissions("privilege:menu")
    @Auditmeta(code = "11", name = "菜单树查询", symbol = "menu")
    @RequestMapping(value = "/menu", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getMenu(HttpServletRequest request) {
    	//获取会话信息
    	User user = (User) SecurityUtils.getSubject().getSession().getAttribute(AppConstants.SESSION_USER);
    	List<TreeNode> treeNodes = privilegeManager.getResourceTree(user);
        return treeNodes;
        // 只查询非叶子节点
//        List<Privilege> actual = privilegeManager.findByParentIdAndLeaf(parentId, false);
//        return actual;
    }

    /**
     * 根据用户权限展开权限树 ( 根据新界面需要的格式转换json)
     * @param request
     * @return
     */
    @ResponseBody
    //@RequiresPermissions("privilege:menu")
    @Auditmeta(code = "11", name = "菜单树查询", symbol = "menu")
    @RequestMapping(value = "/menu_new", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getMenu_zj(HttpServletRequest request) {
        //获取会话信息
        User user_old = (User) SecurityUtils.getSubject().getSession().getAttribute(AppConstants.SESSION_USER);
       // if(user.roles== null || !(user.roles.size()>0)){ //如果roles中没有权限列表
             //上面里拿到的user中没有权限roles。【因为在 ShiroDbRealm 类的 doGetAuthenticationInfo方法中，手动设置为null了】所以这里重新读取一次数据库
             User user = userManager.getAccount(user_old.account);
            // user = user_old;
            // SecurityUtils.getSubject().getSession().setAttribute(AppConstants.SESSION_USER,user_old); //设置至缓存中
     //   }
        List<TreeNode> treeNodes = privilegeManager.getResourceTree(user);

        List r = new ArrayList();  //因为这里只有两级目录，所以就直接解析，如果是多层的，可以使用递归
        for(TreeNode node : treeNodes){
            Map<String,Object> m = new HashMap<String, Object>();

            m.put("id",node.getId());
            m.put("text",node.getText());
            m.put("icon",node.getIconCls());
            m.put("url",node.getAttributes().get("url"));

            List r1 = new ArrayList(); //子菜单集合
            for(TreeNode n : node.getChildren()){
                Map<String,Object> m1 = new HashMap<String, Object>();
                m1.put("id",n.getId());
                m1.put("text",n.getText());
                m1.put("icon",n.getIconCls());
                m1.put("url",n.getAttributes().get("url"));
                r1.add(m1);
            }
            m.put("menus",r1);
            r.add(m);
        }

        return r;
        // 只查询非叶子节点
//        List<Privilege> actual = privilegeManager.findByParentIdAndLeaf(parentId, false);
//        return actual;
    }

    /**
     * 权限  列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Results<Privilege> queryType(HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        //Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
        
        List<Privilege> list = privilegeManager.findAll();
        // 日志参数
        LogContext.putArgs(this.getUser().account);
        // 构建返回，包括列头信息，Message在LogIntercepter中构建
        return Results.getList(list, Privilege.class);
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
    	Integer maxSort = privilegeManager.getMaxSort();
		Result result = new Result(Result.SUCCESS, null, maxSort);
        return result;
    }
 
    /**
     * 父级资源下拉列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/parentresource/{selectType}", method = RequestMethod.POST)
    public List<TreeNode> parentResource(@PathVariable String selectType,HttpServletRequest request,Privilege model) {
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
		List<TreeNode> treeNodes = privilegeManager.getResourceTree(model.getId(),true);
        List<TreeNode> unionList = ListUtils.union(titleList, treeNodes);
        return unionList;
    }
    
    
    /**
     * 资源类型下拉列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resourcetypecombobox", method = RequestMethod.POST)
    public Object resourceTypeCombobox(HttpServletRequest request,Privilege model) {
		List<Combobox> cList = Lists.newArrayList();

		PrivilegeKind[] rss = PrivilegeKind.values();
		for (int i = 0; i < rss.length; i++) {
			Combobox combobox = new Combobox();
			combobox.setValue((rss[i].getValue()).toString());
			combobox.setText(rss[i].getDescription());
			cList.add(combobox);
		}
		return cList;
    }
    
    /**
	 * 删除
	 */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
	public Object delete(HttpServletRequest request) {
    	Result result = null;
    	try{
	    	Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
	    	List<Long> idss = new ArrayList();
	    	Iterator iter = filterParamMap.entrySet().iterator();
	    	while (iter.hasNext()) {
	    		Map.Entry entry = (Map.Entry) iter.next();
	    		idss.add(Long.valueOf((String) entry.getValue()));
	    	}
    		privilegeManager.delete(idss);
    	}catch(Exception e){
    		result = new Result(Result.ERROR, e.getMessage(),
                    e.getMessage());
    		logger.error("Exception: ", e);
    		logger.debug(result.toString());
			return result;
			//return new ExceptionReturn(e);
    	}
		return Result.successResult();
    }
    
    
    /**
	 * 保存
	 */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Result> save(HttpServletRequest request,Privilege model){
    	Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
    	Result result = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		try {
			
			Map<String, Object> params = new HashMap();
            // 名称是否重复校验
			params.put("EQ_name", model.getName());
			Privilege Privilege = privilegeManager.findOne(params);
			if (Privilege != null
					&& !Privilege.getId().equals(model.getId())) {
				result = new Result(Result.WARN, "名称为[" + model.getName()
						+ "]已存在,请修正!", "name");
				logger.debug(result.toString());
				return new ResponseEntity<Result>(result, headers, HttpStatus.OK);
			}
			
			// 设置上级节点
			if (model.getParentId() != null) {
				params.clear();
				params.put("EQ_id", model.getParentId().toString());
				Privilege parentPrivilege = privilegeManager.findOne(params);
				if(parentPrivilege == null){
					logger.error("父级资源[{}]已被删除.",model.getParentId());
					result = new Result(Result.ERROR, "父级资源[{" + model.getParentId()
							+ "}]已被删除.", "name");
					return new ResponseEntity<Result>(result, headers, HttpStatus.OK);
				}
				model.setParent(parentPrivilege);
			}else{
				model.setParentId(0l);
			}
			
			if (model.getId() != null) {
				if (model.getId().equals(model.getParentId())) {
					result = new Result(Result.ERROR, "[上级资源]不能与[资源名称]相同.",
							null);
					logger.debug(result.toString());
					return new ResponseEntity<Result>(result, headers, HttpStatus.OK);
				}
			}
            
			privilegeManager.saveAndFlush(model);
			result = Result.successResult();
			logger.debug(result.toString());
		} catch (Exception e) {
			result = new Result(Result.ERROR, "保存异常",
                    e.getMessage());
			logger.error("Exception: ", e);
    		logger.debug(result.toString());
    		return new ResponseEntity<Result>(result, headers, HttpStatus.OK);
		}
		return new ResponseEntity<Result>(result, headers, HttpStatus.OK);
	}
    
    /**
	 * 资源树.
	 */
    @ResponseBody
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public Object tree(HttpServletRequest request,Privilege model) {
		try {
			List<TreeNode> treeNodes = privilegeManager.getResourceTree(model.getId(),true);
			for(TreeNode treeNode : treeNodes){
				treeNode.setState(TreeNode.STATE_OPEN);
			}
	        return treeNodes;
		} catch (Exception e) {
			//异常处理，封装返回异常
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
			//new Result(Result.SUCCESS, null, maxSort);
		}
	}
}
