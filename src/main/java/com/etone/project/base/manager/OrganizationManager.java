/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.project.base.dao.OrganizationDao;
import com.etone.project.base.entity.share.Organization;
import com.etone.project.base.support.BaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录账号
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14087 $$
 */
@Service
@Transactional
public class OrganizationManager extends BaseManager<Organization, Long> {
    // members
    public static final Logger logger = LoggerFactory.getLogger(OrganizationManager.class);
    // 使用的加密算法
    public static final String HASH_ALGORITHM = "SHA-1";
    // 加密迭代次数
    public static final int HASH_INTERATIONS = 1024;
    // 加密盐的大小
    private static final int SALT_SIZE = 8;

    @Autowired
    private OrganizationDao organizationDao;
    // static block

    // constructors

    // properties

    @Override
    public void setRepository() {
        super.baseRepository = organizationDao;
    }

    // public methods

    /**
     * 根据ouguid查询组织
     *
     * @param ouguid 账号，用户名
     * @return
     */
    public Organization getOrganization(String ouguid) {
        return organizationDao.findByOuguid(ouguid);
    }

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
