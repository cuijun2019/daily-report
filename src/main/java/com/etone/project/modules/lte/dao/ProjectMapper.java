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
public interface ProjectMapper {

	List<Map> queryContractReview(QueryCriteria criteria);

    int countContractReview(QueryCriteria criteria);

    List<Map> queryEmployeeInfo();

    List<Map> queryLatestLogInfo(String employeeCode);

    List<Map> queryLogInfo(QueryCriteria criteria);

    int countLogInfo(QueryCriteria criteria);

    int validProjectCode(String projectCode);

    int validProjectName(String projectName);

    int validReporter(String reporter);

    String validEmployeeCode(String employeeCode);

    String validEmployee(String employee);

    List<Map> validProportion(QueryCriteria criteria);

    List<String> queryReporter(String projectName);

    void saveLogInfo(QueryCriteria criteria);

    void updateLogInfo(QueryCriteria criteria);

    List<Map> queryContractReviewInfo(QueryCriteria criteria);

    void deleteData(QueryCriteria criteria);

    void saveData(QueryCriteria criteria);

    void saveLogData(QueryCriteria criteria);

    List<Map> exportLogList(QueryCriteria criteria);

    List<Map> exportLogListSortByEmployee(QueryCriteria criteria);

    List<Map> exportContractReviewList(QueryCriteria criteria);

    List<Map> exportReporterEmployeeList(QueryCriteria criteria);

    List<Map> queryEmptyWorkLog(QueryCriteria criteria);

    List<Map> queryNotAllWorkLog(QueryCriteria criteria);

    String queryHolidays(String year);

    String queryMakeUpClassDays(String year);

    void deleteProjectInfo(Long id);

    void deleteReporterEmployeeInfo(Long id);

    void deleteLogInfo(QueryCriteria criteria);

    void saveProjectInfo(QueryCriteria criteria);

    void updateProjectInfo(QueryCriteria criteria);

    void saveReporterEmployeeInfo(QueryCriteria criteria);

    void updateReporterEmployeeInfo(QueryCriteria criteria);

    List<Map> queryCodeAndReporter(String projectName);

    List<Map> queryNameAndReporter(String projectCode);

    List<Map> queryEmployeeProject(QueryCriteria criteria);

    List<Map> queryFinalStatisticsInfo(QueryCriteria criteria);

    int countRepeatLogInfo(QueryCriteria criteria);

    List<String> queryProjectCode();

    Double querySumProportion(QueryCriteria criteria);

    List<Map> queryProjectLine(QueryCriteria criteria);

    List<Map> queryProjectByLine(QueryCriteria criteria);

    String queryLineNameByCode(String projectCode);

    List<Map> queryEmployeeLog(QueryCriteria criteria);

    int countRepeatEmployeeInfo(QueryCriteria criteria);

    List<Map> queryReporterEmployeeInfo(QueryCriteria criteria);

    int countReporterEmployee(QueryCriteria criteria);
}