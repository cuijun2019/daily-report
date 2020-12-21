/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.project.base.dao.UserDao;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.BaseManager;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.collections.Collections3;
import com.etone.project.core.db.CustomerContextHolder;
import com.etone.project.core.exception.DaoException;
import com.etone.project.core.exception.ServiceException;
import com.etone.project.core.exception.SystemException;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteMrAdvanceMapper;
import com.etone.project.utils.AppConstants;
import com.etone.project.utils.BrowserAndIPAddrUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 登录账号
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 140218 $$
 * @date 2014-02-18
 */
@Service
@Transactional
public class UserManager extends BaseManager<User, Long> {
    // members

    public static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    @Autowired
    private UserDao userDao;

   /* @Autowired
	private LteMrAdvanceMapper lteMrAdvanceMapper;*/

    // static block
    // constructors
    // properties
    @Override
    public void setRepository() {
        super.baseRepository = userDao;
    }

    public  void uploadData(ArrayList<User> userList){
        this.save(userList);
    }

    /**
     * 根据用户名查找用户信息
     *
     * @param account 账号，用户名
     * @return
     */
    public User getAccount(String account) {
        return userDao.findByAccount(account);
    }

    /**
     * 得到当前登录用户.
     *
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(AppConstants.SESSION_USER);
        return user;
    }

    /**
     * 得到超级用户.
     *
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public User getSuperUser() throws DaoException, SystemException, ServiceException {
        User superUser = this.findOne(1l);//超级用户ID为1
        if (superUser == null) {
            throw new SystemException("系统未设置超级用户.");
        }
        return superUser;
    }

    /**
     * 删除
     *
     * @param ids
     */
    public void delete(List<Long> ids) throws DaoException, SystemException, ServiceException {
        if (!Collections3.isEmpty(ids)) {
            for (Long id : ids) {
                User superUser = this.getSuperUser();
                if (id.equals(superUser.getId())) {
                    throw new SystemException("不允许删除超级用户!");
                }
                User user = this.findOne(id);
                if (user != null) {
                    //设置角色为空
                    user.setRoles(null);
                    //逻辑删除
                    //手工方式(此处不使用 由注解方式实现逻辑删除)
//					user.setStatus(StatusState.delete.getValue());
                    //注解方式 由注解设置用户状态
                    this.delete(user);
                    this.dropLteTableByUserId(user.id); //删除用户对应的LTE表
                }
            }
        } else {
            logger.warn("参数[ids]为空.");
        }
    }

    /**
     * 根据用户ID创建LTE表：tableName_userid
     *
     * @param id
     */
    public void createLteTableByUserId(Long id) {
        this.userDao.createLteTableByUserId(id);
    }

    /**
     * 根据用户ID删除LTE表：tableName_userid
     *
     * @param id
     */
    public void dropLteTableByUserId(Long id) {
        this.userDao.dropLteTableByUserId(id);
    }
    
    /**
	 * ftp配置插入
	 * 
	 * @param
	 * @return
	 */
	public void insertSysLog(String sysType,User user1,ServletRequest request) {
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
	    QueryCriteria criteria=new QueryCriteria();
        criteria.put("vcUrl","/login");
        criteria.put("vcModuleCode","");
        criteria.put("vcModule","系统管理");
        criteria.put("vcContent","系统登录");
        criteria.put("vcClassName","LoginController_login");
        //时间信息
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        criteria.put("intYear",cal.get(Calendar.YEAR));
        criteria.put("intMonth",cal.get(Calendar.MONTH)+1);//Calendar中月份从0开始，所以+1
        criteria.put("intDay",cal.get(Calendar.DAY_OF_MONTH));
        criteria.put("intHour",cal.get(Calendar.HOUR_OF_DAY));
        criteria.put("dtCreateTime",format.format(new Date()));
        //用户信息
        User user=this.getCurrentUser();
        criteria.put("vcAccount",user.getAccount());
        criteria.put("intCityId",user.getIntCityId());
        criteria.put("vcRoom",user.getRoom());
        criteria.put("vcUserName",user.getFullName());
        criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr( (HttpServletRequest)request));//IP
        criteria.put("vcBrowser", BrowserAndIPAddrUtils.checkBrowse( (HttpServletRequest)request)); //浏览器类型
        criteria.put("vcExpInfo","");
       // this.lteMrAdvanceMapper.insertSysLog(criteria);
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

    public List<User> getAllUser() {
        return userDao.findAll();
    }




}
