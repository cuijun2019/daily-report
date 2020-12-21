package com.etone.project.modules.lte.dao;

import com.etone.project.core.model.QueryCriteria;

import java.util.List;
import java.util.Map;

/**
 * Created by Rock on 2015/8/11.
 */
public interface LteMod3Mapper {

    /**
     * 模三干扰小区列表查询
     * @param criteria
     * @return
     */
    public List<Map> findMod3CellList(QueryCriteria criteria);
    int countMod3CellList(QueryCriteria criteria);


    public List<Map> findMod3CellListEx(QueryCriteria criteria);
    /**
     *模三小区占比-全网列表
     * */
    public List<Map> findMod3WholeCellList(QueryCriteria criteria);
    int countMode3WholeCellList(QueryCriteria criteria);

    /**
     *模三小区占比-行政区列表
     * */
    public List<Map> findMod3AreaCellList(QueryCriteria criteria);
    int countMode3AreaCellList(QueryCriteria criteria);

    /**
     *模三小区占比-网格列表
     * */
    public List<Map> findMod3GridCellList(QueryCriteria criteria);
    int countMode3GridCellList(QueryCriteria criteria);

    /**
     *模三区间分布-全网列表
     * */
    public List<Map> findMod3WholeList(QueryCriteria criteria);
    int countMode3WholeList(QueryCriteria criteria);

    /**
     *模三区间分布-行政区列表
     * */
    public List<Map> findMod3AreaList(QueryCriteria criteria);
    int countMode3AreaList(QueryCriteria criteria);

    /**
     *模三区间分布-网格列表
     * */
    public List<Map> findMod3GridList(QueryCriteria criteria);
    int countMode3GridList(QueryCriteria criteria);

    /*
    * 获取表名
    * **/
    public List<Map> getTableName(QueryCriteria criteria);
    /*
      * 获取表名
      * **/
    public List<Map> getTables(QueryCriteria criteria);
    /*
    * 创建视图
    * */
    public void createView(QueryCriteria criteria);

    /*
    * 删除视图
    * */
    public void dropView(QueryCriteria criteria);

    /**全网柱状图*/
    public List<Map> wholeNetColumn(QueryCriteria criteria);

    /**行政区柱状图*/
    public List<Map> areaColumn(QueryCriteria criteria);

    /**网格柱状图*/
    public List<Map> gridColumn(QueryCriteria criteria);

    /**天粒度模三干扰趋势分析*/
    public List<Map> getDayLine(QueryCriteria criteria);

    /**小时粒度模三干扰趋势分析*/
    public List<Map> getHourLine(QueryCriteria criteria);

    /**模三干扰领取查询*/
    public List<Map> findNcCellList(QueryCriteria criteria);

}
