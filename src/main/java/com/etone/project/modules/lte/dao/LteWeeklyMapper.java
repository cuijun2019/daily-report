package com.etone.project.modules.lte.dao;

import com.etone.project.core.model.QueryCriteria;
import java.util.List;
import java.util.Map;

/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
public interface LteWeeklyMapper {

    public void saveWeeklyInfo(QueryCriteria criteria);

    public List<Map> queryWeeklyInfo(QueryCriteria criteria);

    public String queryLogTime(QueryCriteria criteria);

    public int countSameWeekly(QueryCriteria criteria);

    public List<Map> queryProject(QueryCriteria criteria);

    public List<Map> queryAllWeekly(QueryCriteria criteria);

    public List<Map> queryAllWeeklyProject(QueryCriteria criteria);

    public Map<String, Double> queryWeeklyByWeek(QueryCriteria criteria);

    public Map queryWeeklyProjectByWeek(QueryCriteria criteria);

    public Map<String, Double> queryPreDispatchArchive(QueryCriteria criteria);

    public Map queryPreDispatchArchiveProject(QueryCriteria criteria);

    public int countPreWeekly(QueryCriteria criteria);

    public List<Map> queryCodeAndReporter(String projectName);

    public List<Map> queryNameAndReporter(String projectCode);

    public String getTarget(QueryCriteria criteria);

    public String queryEmployeeProvinces(String employeeCode);

    public List<Map<String, String>> queryEmployeeByCode(String employeeCode);

    public List<Map> queryLineWeekly(QueryCriteria criteria);

    public List<Map> queryContractReview(QueryCriteria criteria);

    public List<Map> queryProLogTime(QueryCriteria criteria);

    public int validProjectCode(String projectCode);

    public int validProjectName(String projectName);


}