package com.etone.project.modules.lte.dao;

import java.util.List;
import java.util.Map;

import com.etone.project.core.model.QueryCriteria;

public interface LteMrMapper {
	/**全网覆盖类查询*/
	public List<Map> findFgList(QueryCriteria criteria);
	int countFgList(QueryCriteria criteria); //表格查询总记录
	
	/**全网结构干扰查询*/
	public List<Map> findGrList(QueryCriteria criteria);
	int countGrList(QueryCriteria criteria); //表格查询总记录
	
	/**RSRP小区数查询*/
	public List<Map> rsrpCell(QueryCriteria criteria);
	int countRsrpCell(QueryCriteria criteria); //表格查询总记录
	
	/**PHR小区数查询*/
	public List<Map> phrCell(QueryCriteria criteria);
	int countPhrCell(QueryCriteria criteria); //表格查询总记录
	
	/**RIP小区数查询*/
	public List<Map> ripCell(QueryCriteria criteria);
	int countRipCell(QueryCriteria criteria); //表格查询总记录
	
	/**RSRQ小区数查询*/
	public List<Map> rsrqCell(QueryCriteria criteria);
	int countRsrqCell(QueryCriteria criteria); //表格查询总记录
	
	/**重叠覆盖度小区数查询*/
	public List<Map> mcCell(QueryCriteria criteria);
	int countMcCell(QueryCriteria criteria); //表格查询总记录
	
	/**模三干扰小区数查询*/
	public List<Map> mo3Cell(QueryCriteria criteria);
	int countMo3Cell(QueryCriteria criteria); //表格查询总记录
	
	/**小区级列表查询*/
	public List<Map> findCellList(QueryCriteria criteria);
	int countCellList(QueryCriteria criteria); //表格查询总记录
}