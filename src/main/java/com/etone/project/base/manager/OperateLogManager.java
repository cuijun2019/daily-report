/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.project.base.dao.OperateLogDao;
import com.etone.project.base.entity.share.OperateLog;
import com.etone.project.base.support.BaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日志业务类
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14087 $$
 */
@Service
@Transactional
public class OperateLogManager extends BaseManager<OperateLog, Long> {
    // members
    public static final Logger logger = LoggerFactory.getLogger(OperateLogManager.class);

    @Autowired
    private OperateLogDao operateLogDao;
    // static block

    // constructors

    // properties

    // public methods

    @Override
    public void setRepository() {
        super.baseRepository = operateLogDao;
    }

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
