package com.etone.project.modules.lte.manager;

import com.etone.project.base.entity.share.User;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
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
public interface ILteProjectManager {

	public List<Map> queryContractReview(QueryCriteria criteria);

    public int countContractReview(QueryCriteria criteria);

    public List<Map> queryEmployeeInfo();

    public List<Map> queryLatestLogInfo(String employeeCode);

    public boolean validProjectCode(String projectCode);

    public boolean validProjectName(String projectName);

    public boolean validReporter(String reporter);

    public String validEmployeeCode(String employeeCode);

    public String validEmployee(String employee);

    public boolean validProportion(String proportion);

    public List<String> queryReporter(String projectName);

    public void saveLogInfo(QueryCriteria criteria);

    public PageResult queryContractReviewInfo(QueryCriteria criteria);

    public PageResult queryLogInfo(QueryCriteria criteria);

    public PageResult queryStatisticsInfo(QueryCriteria criteria);

    public String queryEmployeeByCode(String employeeCode);

    public InputStream validExcelFile(Map<String, MultipartFile> fileMap) throws IOException;

    public String validExcelTitle(org.apache.poi.ss.usermodel.Sheet sheet,String [] titleArr);

    public String validExcelData(Integer rows);

    public Map getExcelData(int row, Sheet sheet, List<Map> dataList);

    public Map getLogExcelData(int row, Sheet sheet, List<Map> dataList);

    public void deleteData(QueryCriteria criteria);

    public void saveData(QueryCriteria criteria);

    public void saveLogData(QueryCriteria criteria);

    public void exportData(OutputStream os, QueryCriteria param);

    public void exportContractReviewData(OutputStream os, QueryCriteria param);

    public void exportStatisticsData(OutputStream os, QueryCriteria param);

    public void exportFinalStatisticsData(OutputStream os, QueryCriteria param);

    public void deleteProjectInfo(List<Long> ids);

    public String deleteLogInfo(QueryCriteria criteria);

    public void saveOrUpdateProjectInfo(QueryCriteria criteria);

    public void saveOrUpdateLogInfo(QueryCriteria criteria);

    public List<Map> queryCodeAndReporter(String projectName);

    public List<Map> queryNameAndReporter(String projectCode);

    public List<Map> queryOwnLogInfo(QueryCriteria param);

    public List<Map> queryEmployeeProject(QueryCriteria criteria);

    public PageResult queryFinalStatisticsInfo(QueryCriteria criteria);

    public boolean validRepeatLogInfo(String projectCode, String employeeCode, String logTime);

    public void saveLogInfo(String logInfoListJson);

    public String validLogInfo(HttpServletRequest request);

    public List<String> queryProjectCode();

    public Double querySumProportion(QueryCriteria criteria);

    public String getToken(String corpid, String corpsecret);

    public String getUserId(String token, String code);

    public String createRingChart(QueryCriteria criteria);

    public List<Map> queryAppraiseEmployee(QueryCriteria criteria);

    public List<Map> queryProjectLine(QueryCriteria criteria);

    public List<Map> queryProjectByLine(QueryCriteria criteria);

    public String queryLineNameByCode(String projectCode);

    public List<Map> queryEmployeeLog(QueryCriteria criteria);
}