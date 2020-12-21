package com.etone.project.modules.lte.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etone.project.base.support.BaseManager;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteMrMapper;
import com.etone.project.modules.lte.manager.ILteMrManager;

@Service
@Transactional
public class LteMrManager extends BaseManager<Map, Long> implements ILteMrManager{
	
	@Autowired
	private LteMrMapper lteMrMapper;

	@Override
	public void setRepository() {
		
	}
	
	/**全网覆盖类查询*/
	@Override
	public PageResult<Map> findFgList(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.findFgList(criteria);
		int total = lteMrMapper.countFgList(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**全网结构干扰查询*/
	@Override
	public PageResult<Map> findGrList(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.findGrList(criteria);
		int total = lteMrMapper.countGrList(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**RSRP小区数查询*/
	@Override
	public PageResult<Map> rsrpCell(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.rsrpCell(criteria);
		int total = lteMrMapper.countRsrpCell(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**PHR小区数查询*/
	@Override
	public PageResult<Map> phrCell(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.phrCell(criteria);
		int total = lteMrMapper.countPhrCell(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**RIP小区数查询*/
	@Override
	public PageResult<Map> ripCell(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.ripCell(criteria);
		int total = lteMrMapper.countRipCell(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**RSRQ小区数查询*/
	@Override
	public PageResult<Map> rsrqCell(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.rsrqCell(criteria);
		int total = lteMrMapper.countRsrqCell(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**重叠覆盖度小区数查询*/
	@Override
	public PageResult<Map> mcCell(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.mcCell(criteria);
		int total = lteMrMapper.countMcCell(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**模三干扰小区数查询*/
	@Override
	public PageResult<Map> mo3Cell(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.mo3Cell(criteria);
		int total = lteMrMapper.countMo3Cell(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**小区级列表查询*/
	@Override
	public PageResult<Map> findCellList(QueryCriteria criteria) {
		List<Map> actual = lteMrMapper.findCellList(criteria);
		int total = lteMrMapper.countCellList(criteria);
		PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

}
