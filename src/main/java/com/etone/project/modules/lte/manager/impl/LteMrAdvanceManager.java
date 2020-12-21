package com.etone.project.modules.lte.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.BaseManager;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.utils.DateUtil;
import com.etone.project.modules.lte.dao.LteMrAdvanceMapper;
import com.etone.project.modules.lte.entity.LteMrDto;
import com.etone.project.modules.lte.entity.LteMrLoadRunner;
import com.etone.project.modules.lte.manager.ILteMrAdvanceManager;
import com.etone.project.modules.lte.manager.ILteMrManager;
import com.etone.project.modules.lte.web.LteMrAdvanceController;
import com.google.common.collect.Lists;

/**
 * Mr预统计模块
 * 
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
@Service
@Transactional
public class LteMrAdvanceManager extends BaseManager<LteMrDto, Long> implements ILteMrAdvanceManager {
	
	private static final Logger logger = LoggerFactory.getLogger(LteMrAdvanceManager.class);
	@Autowired
	private LteMrAdvanceMapper lteMrAdvanceMapper;

	@Override
	public void setRepository() {

	}

	/** 全网覆盖类查询 */
	@Override
	public PageResult<Map> findAdvance(QueryCriteria criteria) {
		List<Map> actual = null;
		int total = 0;
		if ("mrokpi".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findMroKpi(criteria);
			total = lteMrAdvanceMapper.countMroKpi(criteria);
		}
		if ("mrekpi".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findMreKpi(criteria);
			total = lteMrAdvanceMapper.countMreKpi(criteria);
		}
		if ("mrskpi".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findMrsKpi(criteria);
			total = lteMrAdvanceMapper.countMrsKpi(criteria);
		}
		if ("mrocell".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findMroCell(criteria);
			total = lteMrAdvanceMapper.countMroCell(criteria);
		}
		if ("mrecell".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findMreCell(criteria);
			total = lteMrAdvanceMapper.countMreCell(criteria);
		}
		PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**
	 * mro小时粒度记录数查询
	 */
	@Override
	public List<Map> findHourNum(QueryCriteria criteria) {
		List<Map> actual = Lists.newArrayList();
		if ("mro".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findMroHourNum(criteria);
		}
		if ("mre".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findMreHourNum(criteria);
		}
		if ("rsrp".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findRsrpHourNum(criteria);
		}
		if ("rsrq".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findRsrqHourNum(criteria);
		}
		if ("powerheadroom".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findPowerHeadRoomHourNum(criteria);
		}
		if ("receivedipower".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findReceivedIPowerHourNum(criteria);
		}
		if ("sinrul".equals((String) criteria.get("type"))) {
			actual = lteMrAdvanceMapper.findSinrULHourNum(criteria);
		}

		return actual;
	}

	/*
	 * @Override public Result advanceCalcul(QueryCriteria criteria) throws
	 * Exception{ Result result = null; String msg = ""; try{ //mrokpi预统计入库
	 * lteMrAdvanceMapper.loadMroKpi(criteria); msg = msg +"MroKpi预统计完成; ";
	 * //mrskpi预统计入库 lteMrAdvanceMapper.loadMrsKpi(criteria); msg = msg
	 * +"MrsKpi预统计完成; "; //mrekpi预统计入库 lteMrAdvanceMapper.loadMreKpi(criteria);
	 * msg = msg +"MreKpi预统计完成; ";
	 * 
	 * //stbMr预统计入库 lteMrAdvanceMapper.loadMr_Mrs(criteria);
	 * lteMrAdvanceMapper.loadMr_Mro(criteria);
	 * lteMrAdvanceMapper.loadMr_Mre(criteria); msg = msg +"stbMr预统计完成; ";
	 * //mrocell统计入库 lteMrAdvanceMapper.loadMr_MroCell_1(criteria);
	 * lteMrAdvanceMapper.loadMr_MroCell_2(criteria);
	 * lteMrAdvanceMapper.loadMr_MroCell_3(criteria);
	 * lteMrAdvanceMapper.loadMr_MroCell_4(criteria);
	 * lteMrAdvanceMapper.loadMr_MroCell_5(criteria);
	 * lteMrAdvanceMapper.loadMr_MroCell_6(criteria); msg = msg
	 * +"MroCell预统计完成; "; //mrecell统计入库
	 * lteMrAdvanceMapper.loadMr_MreCell_1(criteria);
	 * lteMrAdvanceMapper.loadMr_MreCell_2(criteria); msg = msg
	 * +"MreCell预统计完成; ";
	 * 
	 * //清除原始数据表 //lteMrAdvanceMapper.deleteMr(criteria); result = new
	 * Result(Result.SUCCESS, msg,""); }catch(Exception e){ e.printStackTrace();
	 * return new Result(Result.ERROR, msg,""); } return result; }
	 */

	@Override
	public Result advanceCalcul(QueryCriteria criteria) throws Exception {
		List<Map> logList = lteMrAdvanceMapper.loadADdvanceLog(criteria);
		Result result = null;
		boolean next = true;
		String vcLogLevelName = ((String) criteria.get("mineNodeBID")).replace("-", "");
		String vcLogTypeName = ((String) criteria.get("maxeNodeBID")).replace("-", "");
		for (Map map : logList) {
			int vcModule1 = Integer.parseInt((String) map.get("vcLogTypeName"));
			int vcModule2 = Integer.parseInt(((String) criteria.get("mineNodeBID")).replace("-", ""));
			if (criteria.get("timeStart").equals((String) map.get("vcModuleCode")) && vcModule2 < vcModule1) {
				next = false;
				result = new Result(Result.ERROR, "数据库中的基站已经统计完成，请导入新数据！", "");
				break;
			} else if ("0".equals((String) map.get("vcModuleCode"))) {
				next = false;
				result = new Result(Result.ERROR, "正在统计，请不要重复提交！", "");
				break;
			}
		}

		if (next) {
			QueryCriteria logInsert = new QueryCriteria();
			logInsert.getCondition().putAll(criteria.getCondition());
			logInsert.put("vcSystemCode", "1001");
			logInsert.put("vcSystem", "预统计模块");
			logInsert.put("vcModuleCode", criteria.get("timeStart"));
			logInsert.put("vcModule", "");
			logInsert.put("dtStartLogTime", new Date());
			logInsert.put("vcResultCode", 0);
			logInsert.put("vcLogLevelName", vcLogLevelName);
			logInsert.put("vcLogTypeName", vcLogTypeName);
			logInsert.put("intStatus", 0);
			logInsert.put("vcModuleCode", criteria.get("timeStart"));
			lteMrAdvanceMapper.insertADdvanceLog(logInsert);
			String msg = "";
			try {
				// mrskpi预统计入库
				lteMrAdvanceMapper.loadMrsKpi(criteria);
				msg = msg + "MrsKpi预统计完成; ";
				// mrekpi预统计入库
				lteMrAdvanceMapper.loadMreKpi(criteria);
				msg = msg + "MreKpi预统计完成; ";
				// mrokpi预统计入库
				lteMrAdvanceMapper.loadMroKpi(criteria);
				msg = msg + "MroKpi预统计完成; ";
				// mrocell统计入库
				lteMrAdvanceMapper.loadMr_MroCell_1(criteria);
				lteMrAdvanceMapper.loadMr_MroCell_2(criteria);
				lteMrAdvanceMapper.loadMr_MroCell_3(criteria);
				lteMrAdvanceMapper.loadMr_MroCell_4(criteria);
				lteMrAdvanceMapper.loadMr_MroCell_5(criteria);
				lteMrAdvanceMapper.loadMr_MroCell_6(criteria);
				msg = msg + "MroCell预统计完成; ";
				// mrecell统计入库
				lteMrAdvanceMapper.loadMr_MreCell_1(criteria);
				lteMrAdvanceMapper.loadMr_MreCell_2(criteria);
				msg = msg + "MreCell预统计完成; ";
				// 清除原始数据表
				// lteMrAdvanceMapper.deleteMr(criteria);
				QueryCriteria logUpdate = new QueryCriteria();
				logUpdate.getCondition().putAll(criteria.getCondition());
				logUpdate.put("dtEndLogTime", new Date());
				logUpdate.put("dtModifyTime", new Date());
				logUpdate.put("vcResultCode", 1);
				logUpdate.put("vcContent", msg);
				logUpdate.put("vcModuleCode", criteria.get("timeStart"));
				lteMrAdvanceMapper.updateADdvanceLog(logUpdate);
				result = new Result(Result.SUCCESS, msg, "");
			} catch (Exception e) {
				logger.error("Exception: ", e);
				return new Result(Result.ERROR, msg, "");
			}
		}
		return result;
	}

	@Override
	public List<QueryCriteria> getQueryCriteriaList(User user) throws Exception {
		List<QueryCriteria> dateQueryList = null;
		try {
			// 预统计并入库并删除原始数据
			QueryCriteria criteria = new QueryCriteria();
			criteria.put("userId", user.getId());
			List<Map> dateList = lteMrAdvanceMapper.findMinMaxTime(criteria);
			Map dateMap = new HashMap();
			if (dateList.size() > 0) {
				dateMap = dateList.get(0);
				dateQueryList = Lists.newArrayList();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
				String minDate = df.format((Date) dateMap.get("minTime"));
				String maxDate = df.format((Date) dateMap.get("maxTime"));
				String mineNodeBID = (String) dateMap.get("mineNodeBID");
				String maxeNodeBID = (String) dateMap.get("maxeNodeBID");
				long hours = DateUtil.dateHoursub((Date) dateMap.get("minTime"), (Date) dateMap.get("maxTime"));
				if (hours == 0l) {
					String minDateAdd = DateUtil.addHours(minDate, 1);
					QueryCriteria criteriaOne = new QueryCriteria();
					criteriaOne.put("timeStart", minDate);
					criteriaOne.put("timeEnd", minDateAdd);
					criteriaOne.put("mineNodeBID", mineNodeBID);
					criteriaOne.put("maxeNodeBID", maxeNodeBID);
					criteriaOne.put("userId", user.getId());
					dateQueryList.add(criteriaOne);
				} else {
					for (int i = 1; i <= hours; i++) {
						QueryCriteria criteriaMany = new QueryCriteria();
						String minDateAdd1 = DateUtil.addHours(minDate, i - 1);
						String minDateAdd2 = DateUtil.addHours(minDate, i);
						if (!minDate.equals(minDateAdd2)) {
							criteriaMany.put("timeStart", minDateAdd1);
							criteriaMany.put("timeEnd", minDateAdd2);
							criteriaMany.put("mineNodeBID", mineNodeBID);
							criteriaMany.put("maxeNodeBID", maxeNodeBID);
							criteriaMany.put("userId", user.getId());
							dateQueryList.add(criteriaMany);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateQueryList;
	}

	@Override
	public Result truncateDate() throws Exception {
		QueryCriteria criteria = new QueryCriteria();
		String[] table = new String[] { "Mro_1", "Mre_1", "PowerHeadRoom_1", "PrachTDD_1", "ReceivedIPower_1", "RSRP_1", "RSRQ_1", "SinrUL_1" };
		for (int i = 0; i < table.length; i++) {
			criteria.put("sql", "truncate table " + table[i]);
			lteMrAdvanceMapper.truncateDate(criteria);
		}
		return null;
	}

	/**
	 * 预统计日志刷新
	 * 
	 * @param criteria
	 * @return
	 */
	@Override
	public PageResult loadADdvanceLog(QueryCriteria criteria) {
		List<Map> actual = lteMrAdvanceMapper.loadADdvanceLog(criteria);
		int total = lteMrAdvanceMapper.countLoadADdvanceLog(criteria);
		PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**
	 * ftp配置查询
	 * 
	 * @param criteria
	 * @return
	 */
	@Override
	public PageResult queryFtpConfig(QueryCriteria criteria) {
		List<Map> actual = lteMrAdvanceMapper.queryFtpConfig(criteria);
		int total = lteMrAdvanceMapper.countFtpConfig(criteria);
		PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
		page.setResult(actual);
		page.setTotalItems(total);
		return page;
	}

	/**
	 * ftp配置删除
	 * 
	 * @param criteria
	 * @return
	 */
	public Result deleteFtpConfig(QueryCriteria criteria) {
		return null;

	}

	/**
	 * ftp配置修改
	 * 
	 * @param criteria
	 * @return
	 */
	public Result updateFtpConfig(QueryCriteria criteria) {
		Result result;
		try {
			lteMrAdvanceMapper.updateFtpConfig(criteria);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(Result.ERROR, "修改失败", e.getMessage());
		}
		result = new Result(Result.SUCCESS, "修改成功", "");
		return result;
	}

	/**
	 * ftp配置插入
	 * 
	 * @param criteria
	 * @return
	 */
	public Result insertFtpConfig(QueryCriteria criteria) {
		return null;
	}

	/**
	 * 小区列表查询
	 * 
	 * @param criteria
	 * @return
	 */
	@Override
	public List<Map> findCellInfo(QueryCriteria criteria) {
		return lteMrAdvanceMapper.findCellInfo(criteria);
	}

	/**
	 * 取得当前用户信息
	 * 
	 * @return
	 */
	public User getUser() {
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return shiroUser.getUser();
	}
}
