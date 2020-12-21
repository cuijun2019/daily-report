package com.etone.project.modules.ltequality.manager;

import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;

import java.util.List;
import java.util.Map;

/**
 * Created by 90463 on 2016-12-15.
 */
public interface ILteIndexManager {

    /**
     * 二级联动一级下拉获取
     */
    public List<Map> findFirstModule(QueryCriteria criteria);
    /**
     * 二级联动二级下拉获取
     */
    public List<Map> findSecondModule(QueryCriteria criteria);
    /**
     * 可查询数据SQL
     */
    public List<Map> findSecondTable(QueryCriteria criteria);
    /**
     * 广东21个地市
     */
    List findCity(QueryCriteria criteria);
    /**
     * 查询模块用表的表名
     */
    public String findTableName(QueryCriteria criteria);

}
