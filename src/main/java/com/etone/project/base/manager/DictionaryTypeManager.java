/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.project.base.dao.DictionaryTypeDao;
import com.etone.project.base.dao.PrivilegeDao;
import com.etone.project.base.entity.share.DictionaryType;
import com.etone.project.base.entity.share.Privilege;
import com.etone.project.base.support.BaseManager;
import com.etone.project.base.support.meta.MetaScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典业务逻辑处理
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-8-22 上午9:57:59
 */
@Service
@Transactional
public class DictionaryTypeManager extends BaseManager<DictionaryType, Integer> {
    // members
    public static final Logger logger = LoggerFactory.getLogger(DictionaryTypeManager.class);

    @Autowired
    private DictionaryTypeDao dictionaryTypeDao;

    @Autowired
    private MetaScanner scanner;
    // static block

    // constructors

    // properties

    @Override
    public void setRepository() {
        super.baseRepository = dictionaryTypeDao;
    }

    /**
	 * 得到排序字段的最大值.
	 * 
	 * @return 返回排序字段的最大值
	 */
	public Integer getMaxSort(){
		Integer max = dictionaryTypeDao.findMaxSort();
		if (max == null) {
			max = 0;
		}
		return max;
	}
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(List<Long> ids){
		for (Long id : ids) {
			dictionaryTypeDao.deleteById(id);
		}
	}
    // public methods

    

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
