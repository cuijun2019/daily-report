package com.etone.project.modules.lte.manager;

import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
public interface IProjectManager {

    List<Map> queryContractReview(QueryCriteria criteria);

    int countContractReview(QueryCriteria criteria);

    List<Map> queryEmployeeInfo();

    List<Map> queryLatestLogInfo(String employeeCode);

    boolean validProjectCode(String projectCode);

    boolean validProjectName(String projectName);

    boolean validReporter(String reporter);

    String validEmployeeCode(String employeeCode);

    String validEmployee(String employee);

    boolean validProportion(String proportion);

    List<String> queryReporter(String projectName);

    void saveLogInfo(QueryCriteria criteria);

    PageResult queryContractReviewInfo(QueryCriteria criteria);

    PageResult queryLogInfo(QueryCriteria criteria);

    PageResult queryStatisticsInfo(QueryCriteria criteria);

    PageResult queryReporterEmployeeInfo(QueryCriteria criteria);

    InputStream validExcelFile(Map<String, MultipartFile> fileMap) throws IOException;

    String validExcelTitle(org.apache.poi.ss.usermodel.Sheet sheet, String[] titleArr);

    String validExcelData(Integer rows);

    Map getExcelData(int row, Sheet sheet, List<Map> dataList);

    Map getLogExcelData(int row, Sheet sheet, List<Map> dataList);

    void deleteData(QueryCriteria criteria);

    void saveData(QueryCriteria criteria);

    void saveLogData(QueryCriteria criteria);

    void exportData(OutputStream os, QueryCriteria param);

    void exportContractReviewData(OutputStream os, QueryCriteria param);

    void exportReporterEmployeeData(OutputStream os, QueryCriteria param);

    void exportStatisticsData(OutputStream os, QueryCriteria param);

    void exportFinalStatisticsData(OutputStream os, QueryCriteria param);

    void exportWorkDayStatData(OutputStream os, QueryCriteria param);

    void deleteProjectInfo(List<Long> ids);

    void deleteReporterEmployeeInfo(List<Long> ids);

    String deleteLogInfo(QueryCriteria criteria);

    void saveOrUpdateProjectInfo(QueryCriteria criteria);

    void saveOrUpdateReporterEmployee(QueryCriteria criteria);

    void saveOrUpdateLogInfo(QueryCriteria criteria);

    List<Map> queryCodeAndReporter(String projectName);

    List<Map> queryNameAndReporter(String projectCode);

    List<Map> queryOwnLogInfo(QueryCriteria param);

    List<Map> queryEmployeeProject(QueryCriteria criteria);

    PageResult queryFinalStatisticsInfo(QueryCriteria criteria);

    boolean validRepeatLogInfo(String projectCode, String employeeCode, String logTime);

    void saveLogInfo(String logInfoListJson);

    String validLogInfo(HttpServletRequest request);

    List<String> queryProjectCode();

    Double querySumProportion(QueryCriteria criteria);

    String createRingChart(QueryCriteria criteria);

    List<Map> queryAppraiseEmployee(QueryCriteria criteria);

    List<Map> queryProjectLine(QueryCriteria criteria);

    List<Map> queryProjectByLine(QueryCriteria criteria);

    String queryLineNameByCode(String projectCode);

    List<Map> queryEmployeeLog(QueryCriteria criteria);
}