package com.etone.project.modules.lte.manager;

import com.etone.project.core.model.QueryCriteria;

public interface ILteFazhiManager {
	
	/**删除MR阈值*/
	public void deleteMrlteFazhi(QueryCriteria criteria);

    /**更新用户ID*/
    public void updateUserID(QueryCriteria criteria);

    /**新增用户阈值*/
    public void addNewUserFazhi(QueryCriteria criteria);


    public String queryImportPercent(Long importKey);
	
}
