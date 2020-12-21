package com.etone.project.modules.lte.manager;

import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;

import java.util.List;
import java.util.Map;

public interface ILteUserExperManager {

    /**
     * 查询指标树
     * @param criteria
     * @return
     */
    public List<Map> findKpiTree(QueryCriteria criteria);

    public List<Map> findSlaveKpiTree(QueryCriteria criteria);

    /**
     * 查询颜色区间
     * @param criteria
     * @return
     */
    public List<Map> findKpiColor(QueryCriteria criteria);

    /**
     * 用户透视图查询
     * @param criteria
     * @return
     */
    public Map<String,List> findUserMsgChart(QueryCriteria criteria);

    /**
     * 用户饼图
     * @param criteria
     * @return
     */
    public List<Map> findUserPieChart(QueryCriteria criteria);

    /*
    * 行政区指标查询
    * */
    public PageResult<Map> findAreaKpiList(QueryCriteria criteria);

    /*
    * 网格指标查询
    * */
    public PageResult<Map> findGridKpiList(QueryCriteria criteria);

    /*
    * 小区指标查询
    * */
    public PageResult<Map> findCellKpiList(QueryCriteria criteria);

    /*
    * 获取表名
    * **/
    public List<Map> getTableName(QueryCriteria criteria);

}
