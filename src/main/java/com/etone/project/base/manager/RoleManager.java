/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.project.base.dao.RoleDao;
import com.etone.project.base.entity.share.Role;
import com.etone.project.base.support.BaseManager;
import com.etone.project.core.collections.Collections3;
import com.etone.project.core.exception.DaoException;
import com.etone.project.core.exception.ServiceException;
import com.etone.project.core.exception.SystemException;
import com.etone.project.utils.AppConstants;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 140218 $$
 * @date 2014-02-18 
 */
@Service
@Transactional
public class RoleManager extends BaseManager<Role, Long> {
    // members
    public static final Logger logger = LoggerFactory.getLogger(RoleManager.class);

    @Autowired
    private RoleDao roleDao;

    // static block

    // constructors

    // properties

    @Override
    public void setRepository() {
        super.baseRepository = roleDao;
    }
	
	/**
	 * 得到超级用户.
	 * 
	 * @return
	 * @throws DaoException
	 * @throws SystemException
	 * @throws ServiceException
	 */
	public Role getSuperRole() throws DaoException,SystemException,ServiceException {
        Role superRole = this.findOne(1l);//超级用户ID为1
        if(superRole == null){
            throw new SystemException("系统未设置超级用户.");
        }
        return superRole;
	}
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(List<Long> ids) throws DaoException,SystemException,ServiceException{
		if(!Collections3.isEmpty(ids)){
			for(Long id :ids){
				Role superRole = this.getSuperRole();
				if (id.equals(superRole.getId())) {
					throw new SystemException("不允许删除超级用户!");
				}
				Role role = this.findOne(id);
				if(role != null){
					//设置角色为空
					//逻辑删除
					//手工方式(此处不使用 由注解方式实现逻辑删除)
//					role.setStatus(StatusState.delete.getValue());
					//注解方式 由注解设置用户状态
					this.delete(role);
				}
			}
		}else{
			logger.warn("参数[ids]为空.");
		}
	}
}
