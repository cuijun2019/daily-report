package com.etone.project.modules.lte.manager;

import com.etone.project.core.model.QueryCriteria;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: YuYuYu
 * Date: 16-8-24
 * Time: 下午12:31
 * To change this template use File | Settings | File Templates.
 */
public interface ILteLogManager {

    /**
     * 日志插入
     * @param criteria,request
     * @return
     */
    public void insertLog(QueryCriteria criteria,HttpServletRequest request);

    /**
     * 查询日志
     * @param criteria,request
     * @return
     */
    public List<Map> getLoadCounts(QueryCriteria criteria);
    public List<Map> getModuleCounts(QueryCriteria criteria);
    public List<Map> fillTop5Chart_lastMonth(QueryCriteria criteria);
    public List<Map> fillTop5Chart_thisMonth(QueryCriteria criteria);
}
