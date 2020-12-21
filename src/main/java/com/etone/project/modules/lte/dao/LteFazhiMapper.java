package com.etone.project.modules.lte.dao;

import java.util.List;
import java.util.Map;

import com.etone.project.core.model.QueryCriteria;

public interface LteFazhiMapper {
	
	/**阈值列表查询*/
	public List<Map> findMrlteFazhiList(QueryCriteria criteria);
	
	/**删除MR阈值*/
	public void deleteMrlteFazhi(QueryCriteria criteria);

    /**更新用户ID*/
    public void updateUserID(QueryCriteria criteria);

    /**新增用户阈值*/
    public void addNewUserFazhi(QueryCriteria criteria);

    /**新增用户ID*/
    public void addUserID(QueryCriteria criteria);

}
