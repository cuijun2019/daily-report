package com.etone.project.modules.lte.manager;

import com.etone.project.core.model.QueryCriteria;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
public interface ILteWeeklyManager {

    public String getToken(String corpid, String corpsecret);

    public String getUserId(String token, String code);

    public Map<String, String> queryEmployeeByCode(String employeeCode);

    public void saveWeeklyInfo(HttpServletRequest request);

    public String validWeekly(HttpServletRequest request);

    public List<Map> queryWeeklyInfo(QueryCriteria criteria);

    public String queryLogTime(QueryCriteria criteria);

    public List<Map> queryProject(QueryCriteria criteria);

    public List<Map> queryWeeklySummary(QueryCriteria criteria);

    public List<Map> queryProjectSummary(QueryCriteria criteria);

    public List<Map> queryWeekSummary(QueryCriteria criteria);

    public List<Map> queryCodeAndReporter(String projectName);

    public List<Map> queryNameAndReporter(String projectCode);

    public String getTarget(String projectCode, String logTime);

    public String queryEmployeeProvinces(String employeeCode);

    public List<Map> queryContractReview(QueryCriteria criteria);

    public List<Map> queryProLogTime(QueryCriteria criteria);

    public boolean validProjectCode(String projectCode);

    public boolean validProjectName(String projectName);
}