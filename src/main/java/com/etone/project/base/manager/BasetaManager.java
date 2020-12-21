/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.project.base.dao.BasetaDao;
import com.etone.project.base.entity.ui.Baseta;
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
public class BasetaManager extends BaseManager<Baseta, Long> {
    // members
    public static final Logger logger = LoggerFactory.getLogger(BasetaManager.class);

    @Autowired
    private BasetaDao basetaDao;

    // static block

    // constructors

    // properties

    @Override
    public void setRepository() {
        super.baseRepository = basetaDao;
    }

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
