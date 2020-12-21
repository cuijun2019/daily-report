/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.web;

import com.etone.commons.util.Encodes;
import com.etone.project.base.entity.share.Role;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.manager.RoleManager;
import com.etone.project.base.manager.UserManager;
import com.etone.project.base.support.BaseConstants;
import com.etone.project.base.support.BaseController;
import com.etone.project.base.support.log.LogContext;
import com.etone.project.base.support.meta.MetaInfo;
import com.etone.project.base.support.meta.MetaScanner;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.base.support.security.ShiroDbRealm.HashPassword;
import com.etone.project.core.model.ExceptionReturn;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.utils.encode.Encrypt;
import com.etone.project.modules.lte.manager.ILteFazhiManager;
import com.etone.project.utils.AppConstants;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.WebUtils;

/**
 * 用户控制器
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 140218 $$
 * @date 2014-02-18
 */
@Controller
@RequestMapping("/base/security/user")
@Auditmeta(code = "003", name = "用户管理", symbol = "user")
public class UserController extends BaseController {
    // members

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String VIEW_PREFIX = "base/security/user/";
    private static final String VIEW_INDEX = VIEW_PREFIX + BaseConstants.VIEW_INDEX;
    private static final String VIEW_EDIT = VIEW_PREFIX + BaseConstants.VIEW_EDIT;
    @Autowired
    private UserManager userManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private ShiroDbRealm shiroDbRealm;
    @Autowired
    private ILteFazhiManager lteFazhiManager;

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
    public Results<User> query(HttpServletRequest request) {
        Page<User> page = userManager.findPage(getParameters(request), User.class);
        // 日志参数
        LogContext.putArgs(this.getUser().account);
        MetaScanner mts = new MetaScanner();
        mts.setScan("");
        List<MetaInfo> meta = mts.getMetadata();
        // 构建返回，包括列头信息，Message在LogIntercepter中构建
        return Results.getPage(page, User.class);
    }

    /**
     * jsp页面重定向
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"redirect/{toPage}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String redirect(@PathVariable String toPage, HttpServletRequest request, Model model){
        return VIEW_PREFIX + toPage;
    }

    /**
     * jsp页面重定向
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadData", method = {RequestMethod.GET, RequestMethod.POST})
    public void uploadData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        if (request instanceof ShiroHttpServletRequest) {
            return ;
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Set<String> fileNameSet = fileMap.keySet();
        for (String fileName : fileNameSet) {
            CommonsMultipartFile file = (CommonsMultipartFile) fileMap.get(fileName);
            if (file == null) {
                logger.info("file not found!");
                return ;
            }
            byte[] bytes = new byte[0];
            //获取文件流
            bytes = file.getBytes();
            String res = new String(bytes);
            String[] tempList = res.split("\r\n");
            ArrayList<User> dataList = new ArrayList<User>();
            for (int i=1;i<tempList.length;i++){
                User user=new User();
                String[] strArr=tempList[i].split(",");
                user.setAccount(strArr[4]);
                user.setCityName(strArr[0]);
                user.setFullName(strArr[3]);
                user.setPassword(shiroDbRealm.getEncrypt(strArr[5]).password);
                user.setSalt(shiroDbRealm.getEncrypt(strArr[5]).salt);
                user.setTelephone(strArr[6]);
                user.setEmail(strArr[7]);
                user.setCreateTime(new Date());
                user.setModifyTime(new Date());
                user.setTips("ltemr_"+strArr[0]);
                user.setFactory(strArr[1]);
                user.setRoom(strArr[2]);
                user.setIntCityId(strArr[8]);
                user.setTipAnswer("110");
                user.setOuPath("113.3215558534,23.1338044796");
                dataList.add(user);
                if(dataList.size()!=0){
                if(dataList.size()%1000==0||i==tempList.length-1){
                    userManager.uploadData(dataList);
                    dataList = new ArrayList();
                }
                }
            }
    }
       this.query(request);
        return;
    }

    /**
     * 绑定编辑界面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/input", ""}, method = RequestMethod.POST)
    public String input(@RequestParam(value = "id", required = false) String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return VIEW_PREFIX + "user_input";
    }

    /**
     * 绑定编辑界面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    public String batchAdd(HttpServletRequest request) {
        return VIEW_PREFIX + "user_batchAdd";
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(HttpServletRequest request, User model) {
        Result result;
        QueryCriteria criteria = new QueryCriteria();
        try {
            // 名称重复校验
            Map<String, Object> params = new HashMap();
            params.put("EQ_account", model.getAccount());
            User user = userManager.findOne(params);
            if (user != null && !user.getId().equals(model.getId())) {
                result = new Result(Result.WARN, "登录名为[" + model.getAccount() + "]已存在,请修正!", "account");
                logger.debug(result.toString());
                return result;
            }

            boolean weatherCreateLteTable = false;
            if (model.getId() == null) {// 新增
                weatherCreateLteTable = true;   //是新增用户，就新增对应的LTE表
//                model.setPassword(Encrypt.e(model.getPassword()));
                HashPassword hashPassword = shiroDbRealm.getEncrypt(model.getPassword());
                model.setPassword(hashPassword.password);
                model.setSalt(hashPassword.salt);


                model.setCreateTime(new Date());
                model.setModifyTime(new Date());
            } else {// 修改
                User superUser = userManager.findOne(1l);
                User sessionUser = userManager.getCurrentUser();
                if (sessionUser.getId().equals(superUser.getId())) {
                    throw new SystemException("超级用户信息仅允许自己修改!");
                }
            }
            
            User newUser = userManager.saveAndFlush(model);
            criteria.put("newID", newUser.id);
//            if(weatherCreateLteTable){
//            	lteFazhiManager.addNewUserFazhi(criteria);
//            }
            
            if (weatherCreateLteTable) {// 新增
                //创建用户的时候顺手调用存储过程创建LTE表
                logger.debug("创建用户的时候顺手调用存储过程创建LTE表,用户ID：" + newUser.id);
                //this.userManager.createLteTableByUserId(newUser.id);
            }
                
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
     * 修改用户角色.
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
    public Object updateUserRole(HttpServletRequest request, User model) {
        Result result;
        try {
            User user = userManager.findOne(model.getId());
            List<Role> rs = Lists.newArrayList();
            String[] roleIds = request.getParameterValues("roleIds");
            for (String id : roleIds) {
                Role role = roleManager.findOne(Long.valueOf(id));
                rs.add(role);
            }
            user.setRoles(rs);
            userManager.saveAndFlush(user);
            result = Result.successResult();
        } catch (Exception e) {
            logger.error("Exception: ", e);
            return new ExceptionReturn(e);
        }
        return result;
    }

    /**
     * 修改用户密码. <br>参数upateOperate 需要密码"1" 不需要密码"0".
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
    public Object updateUserPassword(HttpServletRequest request, @RequestParam(value = "newPassword", required = false) String newPassword, User model) {
        Result result;
        try {
            Map<String, Object> params = new HashMap();
            params.put("EQ_id", model.getId());
            User user = userManager.findOne(model.getId());
            Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
            String upateOperate = (String)query.get("upateOperate");
            String newPassword2 = (String)query.get("newPassword2");//再次确认的密码
            if (user != null) {
                  if( newPassword.equals(newPassword2)){
                            boolean isCheck = true;
                            //需要输入原始密码
                            if (AppConstants.USER_UPDATE_PASSWORD_YES.equals(upateOperate)) {
                                //获取盐，都需要进行一个Encodes.decodeHex。
                                byte[] salt = Encodes.decodeHex(user.salt);
                                SimpleHash  sh = new SimpleHash("SHA-1", model.getPassword(), ByteSource.Util.bytes(salt), 1024);

                                //if (!user.getPassword().equals(Encrypt.e(model.getPassword()))) {
                                if (!user.getPassword().equals(sh.toHex())) {
                                    isCheck = false;
                                }
                            }
            //				//不需要输入原始密码
            //				if (AppConstants.USER_UPDATE_PASSWORD_NO.equals(upateOperate)) {
            //					isCheck = true;
            //				}
                            if (isCheck) {
                                HashPassword hashPassword = shiroDbRealm.getEncrypt(newPassword);
                                user.setPassword(hashPassword.password);
                                user.setSalt(hashPassword.salt);
                                userManager.saveAndFlush(user);
                                result = Result.successResult();
                            } else {
                                result = new Result(Result.WARN, "原始密码输入错误.", "password");
                            }
                  } else{
                      result = new Result(Result.ERROR, "两次输入的密码不一致..", null);
                  }
            } else {
                result = new Result(Result.ERROR, "修改的用户不存在或已被删除.", null);
            }
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
    public Result delete(HttpServletRequest request, int[] ids) throws Exception {
        Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, "");
        try {
            List<Long> idss = new ArrayList();
            Iterator iter = filterParamMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                idss.add(Long.valueOf((String) entry.getValue()));
            }
            userManager.delete(idss);
        } catch (Exception e) {
            return new Result(Result.ERROR, e.getMessage(), null);
        }
        return Result.successResult();
    }
}
