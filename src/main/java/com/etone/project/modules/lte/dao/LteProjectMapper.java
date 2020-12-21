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
public interface LteProjectMapper {

	public List<Map> queryContractReview(QueryCriteria criteria);

    public int countContractReview(QueryCriteria criteria);

    public List<Map> queryEmployeeInfo();

    public List<Map> queryLogInfo(QueryCriteria criteria);

    public int countLogInfo(QueryCriteria criteria);

    public int validProjectCode(String projectCode);

    public int validProjectName(String projectName);

    public int validReporter(String reporter);

    public String validEmployeeCode(String employeeCode);

    public String validEmployee(String employee);

    public List<Map> validProportion(QueryCriteria criteria);

    public List<String> queryReporter(String projectName);

    public List<String> queryEmployeeByCode(String employeeCode);

    public void saveLogInfo(QueryCriteria criteria);

    public void updateLogInfo(QueryCriteria criteria);

    public List<Map> queryContractReviewInfo(QueryCriteria criteria);

    public void deleteData(QueryCriteria criteria);

    public void saveData(QueryCriteria criteria);

    public void saveLogData(QueryCriteria criteria);

    public List<Map> exportLogList(QueryCriteria criteria);

    public List<Map> exportContractReviewList(QueryCriteria criteria);

    public List<Map> queryEmptyWorkLog(QueryCriteria criteria);

    public List<Map> queryNotAllWorkLog(QueryCriteria criteria);

    public String queryHolidays(String year);

    public String queryMakeUpClassDays(String year);

    public void deleteProjectInfo(Long id);

    public void deleteLogInfo(QueryCriteria criteria);

    public void saveProjectInfo(QueryCriteria criteria);

    public void updateProjectInfo(QueryCriteria criteria);

    public List<Map> queryCodeAndReporter(String projectName);

    public List<Map> queryNameAndReporter(String projectCode);

    public List<Map> queryEmployeeProject(QueryCriteria criteria);

    public List<Map> queryFinalStatisticsInfo(QueryCriteria criteria);

    public int countFinalStatisticsInfo(QueryCriteria criteria);

    public int countRepeatLogInfo(QueryCriteria criteria);

    public List<String> queryProjectCode();

    public Double querySumProportion(QueryCriteria criteria);

    public List<Map> queryProjectLine(QueryCriteria criteria);

    public List<Map> queryProjectByLine(QueryCriteria criteria);

    public String queryLineNameByCode(String projectCode);

    public List<Map> queryEmployeeLog(QueryCriteria criteria);

    public int countRepeatEmployeeInfo(QueryCriteria criteria);
}