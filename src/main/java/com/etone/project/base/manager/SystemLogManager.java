/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.project.base.dao.SystemLogDao;
import com.etone.project.base.entity.share.SystemLog;
import com.etone.project.base.support.BaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统日志业务类
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14087 $$
 */
@Service
@Transactional
public class SystemLogManager extends BaseManager<SystemLog, Long> {
    // members
    public static final Logger logger = LoggerFactory.getLogger(SystemLogManager.class);

    @Autowired
    private SystemLogDao systemLogDao;
    // static block

    // constructors

    // properties

    @Override
    public void setRepository() {
        super.baseRepository = systemLogDao;
    }

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
