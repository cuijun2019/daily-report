package com.etone.project.modules.lte.dao;

import java.util.List;
import java.util.Map;

import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.entity.LteMrDto;
/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
public interface LteMrAdvanceMapper {
	/**
	 * 原始数据小时粒度记录数查询
	 * @param criteria
	 * @return
	 */
	public List<Map> findMroHourNum(QueryCriteria criteria);
	public List<Map> findMreHourNum(QueryCriteria criteria);
	public List<Map> findRsrpHourNum(QueryCriteria criteria);
	public List<Map> findRsrqHourNum(QueryCriteria criteria);
	public List<Map> findPowerHeadRoomHourNum(QueryCriteria criteria);
	public List<Map> findReceivedIPowerHourNum(QueryCriteria criteria);
	public List<Map> findSinrULHourNum(QueryCriteria criteria);
	
	/**
	 * 预统计数据小时粒度记录数查询
	 * @param criteria
	 * @return
	 */
	public List<Map> findMroKpi(QueryCriteria criteria);
	public int countMroKpi(QueryCriteria criteria);
	public List<Map> findMreKpi(QueryCriteria criteria);
	public int countMreKpi(QueryCriteria criteria);
	public List<Map> findMrsKpi(QueryCriteria criteria);
	public int countMrsKpi(QueryCriteria criteria);
	public List<Map> findMroCell(QueryCriteria criteria);
	public int countMroCell(QueryCriteria criteria);
	public List<Map> findMreCell(QueryCriteria criteria);
	public int countMreCell(QueryCriteria criteria);
	
	/**
	 * 预统计数据并入库
	 * @param criteria
	 * @return
	 */
	public void loadMroKpi(QueryCriteria criteria);
	public void loadMreKpi(QueryCriteria criteria);
	public void loadMrsKpi(QueryCriteria criteria);
	public void loadMroCell(QueryCriteria criteria);
	public void loadMr_Mro(QueryCriteria criteria);
	public void loadMr_Mre(QueryCriteria criteria);
	public void loadMr_Mrs(QueryCriteria criteria);
	
	public void loadMr_MroCell_1(QueryCriteria criteria);
	public void loadMr_MroCell_2(QueryCriteria criteria);
	public void loadMr_MroCell_3(QueryCriteria criteria);
	public void loadMr_MroCell_4(QueryCriteria criteria);
	public void loadMr_MroCell_5(QueryCriteria criteria);
	public void loadMr_MroCell_6(QueryCriteria criteria);
	public void loadMr_MreCell_1(QueryCriteria criteria);
	public void loadMr_MreCell_2(QueryCriteria criteria);
	
	/**
	 * 清除mr表
	 * @param criteria
	 * @return
	 */
	public void truncateDate(QueryCriteria criteria);
	
	/**
	 * 统计原始Mro入库最大 最小日期
	 * @param criteria
	 * @return
	 */
	public List<Map> findMinMaxTime(QueryCriteria criteria);
	
	/**
	 * 预统计日志刷新
	 * @param criteria
	 * @return
	 */
	public List<Map> loadADdvanceLog(QueryCriteria criteria);
	public int countLoadADdvanceLog(QueryCriteria criteria);
	/**
	 * 预统计日志修改
	 * @param criteria
	 * @return
	 */
	public void updateADdvanceLog(QueryCriteria criteria);
	
	/**
	 * 预统计日志插入
	 * @param criteria
	 * @return
	 */
	public void insertADdvanceLog(QueryCriteria criteria);
	
	/**
	 * ftp配置查询
	 * @param criteria
	 * @return
	 */
	public List<Map> queryFtpConfig(QueryCriteria criteria);
	public int countFtpConfig(QueryCriteria criteria);
	/**
	 * ftp配置删除
	 * @param criteria
	 * @return
	 */
	public void deleteFtpConfig(QueryCriteria criteria);
	/**
	 * ftp配置修改
	 * @param criteria
	 * @return
	 */
	public void updateFtpConfig(QueryCriteria criteria);
	
	/**
	 * ftp配置插入
	 * @param criteria
	 * @return
	 */
	public void insertFtpConfig(QueryCriteria criteria);
	
	/**
	 * 日志插入
	 * @param criteria
	 * @return
	 */
	public void insertSysLog(QueryCriteria criteria);
	
	/**
	 * 小区列表查询
	 * @param criteria
	 * @return
	 */
	public List<Map> findCellInfo(QueryCriteria criteria);
	
}