package com.etone.project.modules.lte.manager;

import java.util.List;
import java.util.Map;

import com.etone.project.base.entity.share.User;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.modules.lte.entity.LteMrDto;
/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
public interface ILteMrAdvanceManager {
	/**小时粒度记录数查询*/
	public List<Map> findHourNum(QueryCriteria criteria);
	
	/**预统计表查询*/
	public PageResult<Map> findAdvance(QueryCriteria criteria);
	
	/**预统计并入库*/
	public Result advanceCalcul(QueryCriteria criteria) throws Exception;
	
	/**预统计并入库 对接接口*/
	public List<QueryCriteria> getQueryCriteriaList(User user) throws Exception;
	
	/**并清除数据 */
	public Result truncateDate() throws Exception;
	
	/**
	 * 预统计日志刷新
	 * @param criteria
	 * @return
	 */
	public PageResult<Map> loadADdvanceLog(QueryCriteria criteria);
	
	/**
	 * ftp配置查询
	 * @param criteria
	 * @return
	 */
	public PageResult<Map> queryFtpConfig(QueryCriteria criteria);
	
	/**
	 * ftp配置删除
	 * @param criteria
	 * @return
	 */
	public Result deleteFtpConfig(QueryCriteria criteria);
	/**
	 * ftp配置修改
	 * @param criteria
	 * @return
	 */
	public Result updateFtpConfig(QueryCriteria criteria);
	
	/**
	 * ftp配置插入
	 * @param criteria
	 * @return
	 */
	public Result insertFtpConfig(QueryCriteria criteria);
	
	/**
	 * 小区列表查询
	 * @param criteria
	 * @return
	 */
	public List<Map> findCellInfo(QueryCriteria criteria);
	
	public User getUser();
}