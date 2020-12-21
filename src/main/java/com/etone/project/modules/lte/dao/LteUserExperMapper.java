package com.etone.project.modules.lte.dao;

import com.etone.project.core.model.QueryCriteria;

import java.util.List;
import java.util.Map;

/**
 * 用户无线体验分析
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2014-06-11 上午9:57:59
 */
public interface LteUserExperMapper {
    /**
     * 查询指标树
     * @param criteria
     * @return
     */
    public List<Map> findKpiTree(QueryCriteria criteria);

    public List<Map> findSlaveKpiTree(QueryCriteria criteria);

    /**
     * 查询栅格
     * @param criteria
     * @return
     */
    public List<Map> findGridTree(QueryCriteria criteria);


    /**
     * 查询颜色区间
     * @param criteria
     * @return
     */
    public List<Map> findKpiColor(QueryCriteria criteria);

    /**
     * 指标查询
     * @param criteria
     * @return
     */
    public List<Map> findKpiList(QueryCriteria criteria);
    int countKpiList(QueryCriteria criteria);

    Map getKpiTreeData(Map kpiId);

    int isUserPieTable(Map map);

    void creatUserPieView(QueryCriteria criteria);

    List<Map> findUserPieList(QueryCriteria criteria);


    void deleUserPieView(QueryCriteria criteria);

    /*
    * 行政区指标查询
    * */
    public List<Map> findAreaKpiList(QueryCriteria criteria);
    int countAreaKpiList(QueryCriteria criteria);

    /*
    * 网格指标查询
    * */
    public List<Map> findGridKpiList(QueryCriteria criteria);
    int countGridKpiList(QueryCriteria criteria);

    /*
    * 小区指标查询
    * */
    public List<Map> findCellKpiList(QueryCriteria criteria);
    int countCellKpiList(QueryCriteria criteria);

    /*
    * 获取表名
    * **/
    public List<Map> getTableName(QueryCriteria criteria);

    /*
    * 创建视图
    * */
    public void createView(QueryCriteria criteria);

    /*
    * 删除视图
    * */
    public void dropView(QueryCriteria criteria);
}