package com.etone.project.modules.lte.manager.impl;

import java.util.Map;

import com.etone.project.utils.ImportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etone.project.base.support.BaseManager;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteFazhiMapper;
import com.etone.project.modules.lte.manager.ILteFazhiManager;

@Service
@Transactional
public class LteFazhiManager extends BaseManager<Map, Long> implements ILteFazhiManager {
	
	@Autowired
	private LteFazhiMapper lteFazhiMapper;
	
	@Override
	public void setRepository() {
	}

	/**删除MR阈值*/
	@Override
	public void deleteMrlteFazhi(QueryCriteria criteria) {
		lteFazhiMapper.deleteMrlteFazhi(criteria);
	}

    /**更新用户ID*/
    @Override
    public void updateUserID(QueryCriteria criteria) {
        lteFazhiMapper.updateUserID(criteria);
    }

    @Override
    public void addNewUserFazhi(QueryCriteria criteria) {
        lteFazhiMapper.addNewUserFazhi(criteria);
        lteFazhiMapper.addUserID(criteria);
    }

    @Override
    public String queryImportPercent(Long importKey) {
        if (importKey == null) {
            return "100";
        }
        Object percent = ImportDetail.importPercentMap.get(importKey);

        if(percent != null && Double.parseDouble(percent.toString()) == 100) {
            ImportDetail.importPercentMap.remove(importKey);
        }

        return (percent == null? 100 : percent) + "";

    }

}
