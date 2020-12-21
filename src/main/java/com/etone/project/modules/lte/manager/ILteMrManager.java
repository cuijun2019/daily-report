package com.etone.project.modules.lte.manager;

import java.util.Map;

import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;

public interface ILteMrManager {
	/**全网覆盖类查询*/
	public PageResult<Map> findFgList(QueryCriteria criteria);
	
	/**全网结构干扰查询*/
	public PageResult<Map> findGrList(QueryCriteria criteria);
	
	/**RSRP小区数查询*/
	public PageResult<Map> rsrpCell(QueryCriteria criteria);
	
	/**PHR小区数查询*/
	public PageResult<Map> phrCell(QueryCriteria criteria);
	
	/**RIP小区数查询*/
	public PageResult<Map> ripCell(QueryCriteria criteria);
	
	/**RSRQ小区数查询*/
	public PageResult<Map> rsrqCell(QueryCriteria criteria);
	
	/**重叠覆盖度小区数查询*/
	public PageResult<Map> mcCell(QueryCriteria criteria);
	
	/**模三干扰小区数查询*/
	public PageResult<Map> mo3Cell(QueryCriteria criteria);
	
	/**小区级列表查询*/
	public PageResult<Map> findCellList(QueryCriteria criteria);
	
}