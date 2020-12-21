package com.etone.project.modules.lte.manager.impl;

import Alert.weChat.send_weChatMsg;
import Alert.weChat.urlData;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteWeeklyMapper;
import com.etone.project.modules.lte.manager.ILteWeeklyManager;
import com.etone.project.utils.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
@Service
@Transactional
public final class LteWeeklyManager implements ILteWeeklyManager {

	private static final Logger logger = LoggerFactory.getLogger(LteWeeklyManager.class);

	@Autowired
	private LteWeeklyMapper lteWeeklyMapper;

    @Cacheable(value="accessTokenCache")
    public String getToken(String corpid, String corpsecret) {
        send_weChatMsg sw = new send_weChatMsg();
        Gson gson = new Gson();
        urlData uData = new urlData();
        try {
            uData.setGet_Token_Url(corpid,corpsecret);
            String resp = sw.toAuth(uData.getGet_Token_Url());

            Map<String, Object> map = gson.fromJson(resp,
                    new TypeToken<Map<String, Object>>() {
                    }.getType());
            return map.get("access_token").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getUserId(String token, String code) {
        send_weChatMsg sw = new send_weChatMsg();
        Gson gson = new Gson();
        urlData uData = new urlData();
        try {
            uData.setGet_User_Url(token,code);
            String resp = sw.toUserAuth(uData.getGet_User_Url());

            Map<String, Object> map = gson.fromJson(resp,
                    new TypeToken<Map<String, Object>>() {
                    }.getType());
            return "ok".equals(map.get("errmsg").toString()) ? map.get("UserId").toString() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Map<String, String> queryEmployeeByCode(String employeeCode) {
        List<Map<String, String>> list = lteWeeklyMapper.queryEmployeeByCode(employeeCode);
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    public void saveWeeklyInfo(HttpServletRequest request) {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("projectCode", request.getParameter("projectCode"));
        criteria.put("projectName", request.getParameter("projectName"));
        criteria.put("manager", request.getParameter("manager"));
        criteria.put("target", request.getParameter("target").replace("次", ""));
        criteria.put("dispatch", request.getParameter("dispatch").replace("次", ""));
        criteria.put("archive", request.getParameter("archive").replace("次", ""));
        criteria.put("logTime", request.getParameter("logTime"));
        criteria.put("employeeCode", request.getParameter("employeeCode"));
        criteria.put("employee", request.getParameter("employee"));
        criteria.put("managerCode", request.getParameter("managerCode"));
        criteria.put("province", request.getParameter("province"));
        lteWeeklyMapper.saveWeeklyInfo(criteria);
    }

    @Override
    public String validWeekly(HttpServletRequest request) {
        String currentWeek =  request.getParameter("currentWeek");
        String projectCode = request.getParameter("projectCode");
        String projectName = request.getParameter("projectName");
        String dispatch = request.getParameter("dispatch");
        String archive = request.getParameter("archive");
        String target = request.getParameter("target");
        String employeeCode = request.getParameter("employeeCode");
        String employee = request.getParameter("employee");
        String logTime = request.getParameter("logTime");
        String logTimeLow = logTime.replace("一", "1").replace("二", "2").replace("三", "3").replace("四", "4").replace("五", "5");

        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employeeCode", employeeCode);
        criteria.put("employee", employee);
        criteria.put("logTime", logTime);
        String preLogTime = getPreLogTime(logTime);
        criteria.put("preLogTime", preLogTime);
        criteria.put("projectCode", projectCode);
        criteria.put("position", request.getParameter("position"));
        criteria.put("province", request.getParameter("province"));

        if (!Common.judgeString(employeeCode)) {
            return "员工工号不能为空,请退出,再进入";
        }
        if (logTimeLow.compareTo(currentWeek) > 0) {
            return "还没到" + logTime.substring(9) + ",不能填写" + logTime + "的周报";
        }
        if (lteWeeklyMapper.countSameWeekly(criteria) > 0) {
            return "同一周，同一个项目只能有一个人填写";
        }
        if (logTime.indexOf("第一周") == -1) {
            if (lteWeeklyMapper.countPreWeekly(criteria) <= 0) {
                return "请先填写" + preLogTime + "的周报";
            }
            int flag = validDispatchAndArchive(criteria, dispatch, archive);
            if (flag == 1) {
                return "月度累计派单量不能小于上周的月度累计派单量";
            }
            if (flag == 2) {
                return "月度累计归档量不能小于上周的月度累计归档量";
            }
        }
        if (!Common.judgeString(projectCode)) {
            return "项目编码不能为空";
        }
        if (!this.validProjectCode(projectCode)) {
            return "项目编码不正确";
        }
        if (!Common.judgeString(projectName)) {
            return "项目名称不能为空";
        }
        if (!this.validProjectName(projectName)) {
            return "项目名称不正确";
        }
        if (!Common.judgeString(dispatch)) {
            return "月度累计派单量（次）不能为空";
        }
        if (!Common.judgeString(archive)) {
            return "月度累计归档量（次）不能为空";
        }
        if (!Common.judgeString(target)) {
            return "月度按次归档目标（次）不能为空";
        }
        return "";
    }

    @Override
    public List<Map> queryWeeklyInfo(QueryCriteria criteria) {
        setStartEndDate(criteria);
        return lteWeeklyMapper.queryWeeklyInfo(criteria);
    }

    @Override
    public String queryLogTime(QueryCriteria criteria) {
        String logTime = lteWeeklyMapper.queryLogTime(criteria);
        if ("第一周".equals(logTime)) {
            return "第二周";
        }
        if ("第二周".equals(logTime)) {
            return "第三周";
        }
        if ("第三周".equals(logTime)) {
            return "第四周";
        }
        if ("第四周".equals(logTime)) {
            return "第五周";
        }
        if ("第五周".equals(logTime)) {
            return "done";
        }
        return "第一周";
    }

    @Override
    public List<Map> queryProject(QueryCriteria criteria) {
        return lteWeeklyMapper.queryProject(criteria);
    }

    @Override
    public List<Map> queryWeeklySummary(QueryCriteria criteria) {
        String type = String.valueOf(criteria.get("type"));
        String logTime = String.valueOf(criteria.get("logTime"));
        String preLogTime = "";
        if ("all".equals(type)) {
            return lteWeeklyMapper.queryAllWeekly(criteria);
        } else {
            double weekDispatch = 0;
            double weekArchive = 0;
            if ("1".equals(type)) {
                logTime = logTime + " 第一周";
            } else if ("2".equals(type)) {
                preLogTime = logTime + " 第一周";
                logTime = logTime + " 第二周";
            } else if ("3".equals(type)) {
                preLogTime = logTime + " 第二周";
                logTime = logTime + " 第三周";
            } else if ("4".equals(type)) {
                preLogTime = logTime + " 第三周";
                logTime = logTime + " 第四周";
            } else if ("5".equals(type)) {
                preLogTime = logTime + " 第四周";
                logTime = logTime + " 第五周";
            }
            criteria.put("logTime", logTime);
            criteria.put("preLogTime", preLogTime);
            criteria.put("firstLogTime", logTime.substring(0, logTime.indexOf(" ")) + " 第一周");
            Map<String, Double> map = lteWeeklyMapper.queryWeeklyByWeek(criteria);
            Map<String, Double> preDispatchArchive = lteWeeklyMapper.queryPreDispatchArchive(criteria);
            if (map == null || map.get("dispatch") == null) {
                return null;
            } else {
                double dispatch = map.get("dispatch");
                double archive = map.get("archive");
                if (preDispatchArchive == null) {
                    weekDispatch = dispatch;
                    weekArchive = archive;
                } else {
                    weekDispatch = dispatch - preDispatchArchive.get("dispatch");
                    weekArchive = archive - preDispatchArchive.get("archive");
                }
                map.put("weekDispatch", weekDispatch);
                map.put("weekArchive", weekArchive);
                map.put("archiveRate", Common.roundDouble(weekArchive / (weekDispatch == 0 ? 1 : weekDispatch) * 100));
                map.put("targetRate", Common.roundDouble(archive / (map.get("target") == 0 ? 1 : (map.get("target"))) * 100));
            }
            List<Map> list = new ArrayList<Map>();
            list.add(map);

            return list;
        }
    }

    @Override
    public List<Map> queryProjectSummary(QueryCriteria criteria) {
        String type = String.valueOf(criteria.get("type"));
        String logTime = String.valueOf(criteria.get("logTime"));
        String preLogTime = "";
        if ("all".equals(type)) {
            criteria.put("isSingle", "true");
            return lteWeeklyMapper.queryAllWeeklyProject(criteria);
        } else {
            double weekDispatch = 0;
            double weekArchive = 0;
            if ("1".equals(type)) {
                logTime = logTime + " 第一周";
            } else if ("2".equals(type)) {
                preLogTime = logTime + " 第一周";
                logTime = logTime + " 第二周";
            } else if ("3".equals(type)) {
                preLogTime = logTime + " 第二周";
                logTime = logTime + " 第三周";
            } else if ("4".equals(type)) {
                preLogTime = logTime + " 第三周";
                logTime = logTime + " 第四周";
            } else if ("5".equals(type)) {
                preLogTime = logTime + " 第四周";
                logTime = logTime + " 第五周";
            }
            criteria.put("logTime", logTime);
            criteria.put("preLogTime", preLogTime);
            Map map = lteWeeklyMapper.queryWeeklyProjectByWeek(criteria);
            Map preDispatchArchive = lteWeeklyMapper.queryPreDispatchArchiveProject(criteria);
            if (map == null || map.get("dispatch") == null) {
                return null;
            } else {
                double dispatch = Double.parseDouble(String.valueOf(map.get("dispatch")));
                double archive = Double.parseDouble(String.valueOf(map.get("archive")));
                double target = Double.parseDouble(String.valueOf(map.get("target")));
                if (preDispatchArchive == null) {
                    weekDispatch = dispatch;
                    weekArchive = archive;
                } else {
                    weekDispatch = dispatch - Double.parseDouble(String.valueOf(preDispatchArchive.get("dispatch")));
                    weekArchive = archive - Double.parseDouble(String.valueOf(preDispatchArchive.get("archive")));
                }
                map.put("weekDispatch", weekDispatch);
                map.put("weekArchive", weekArchive);
                map.put("archiveRate", Common.roundDouble(weekArchive / (weekDispatch == 0 ? 1 : weekDispatch) * 100));
                map.put("targetRate", Common.roundDouble(archive / (target == 0 ? 1 : target) * 100));
            }
            List<Map> list = new ArrayList<Map>();
            list.add(map);

            return list;
        }
    }

    @Override
    public List<Map> queryWeekSummary(QueryCriteria criteria) {
        String projectCode = String.valueOf(criteria.get("projectCode"));
        List<Map> list = null;
        if (Common.judgeString(projectCode)) {
            list = lteWeeklyMapper.queryAllWeeklyProject(criteria);
        } else {
            list = lteWeeklyMapper.queryLineWeekly(criteria);
        }

        List<Map> resultList = new ArrayList<Map>();
        if (list != null && !list.isEmpty()) {
            Map resultMap = null;
            int size = list.size();
            if (size < 5) {
                for (int i = 0; i < 5 - size; i++) {
                    resultMap = new HashMap();
                    resultMap.put("logTime", "第" + (5-i) + "周");
                    resultMap.put("dispatch", 0);
                    resultMap.put("archive", 0);
                    resultList.add(resultMap);
                }
            }
            for (int i = 0; i < (size-1); i++) {
                resultMap = new HashMap();
                resultMap.put("logTime", "第" + (size-i) + "周");
                resultMap.put("dispatch", (Double.parseDouble(String.valueOf(list.get(i).get("dispatch")))-Double.parseDouble(String.valueOf(list.get(i+1).get("dispatch")))));
                resultMap.put("archive", (Double.parseDouble(String.valueOf(list.get(i).get("archive")))-Double.parseDouble(String.valueOf(list.get(i+1).get("archive")))));
                resultList.add(resultMap);
            }
            resultMap = new HashMap();
            resultMap.put("logTime", "第1周");
            resultMap.put("dispatch", Double.parseDouble(String.valueOf(list.get(size - 1).get("dispatch"))));
            resultMap.put("archive", Double.parseDouble(String.valueOf(list.get(size - 1).get("archive"))));
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public List<Map> queryCodeAndReporter(String projectName) {
        return lteWeeklyMapper.queryCodeAndReporter(projectName);
    }

    @Override
    public List<Map> queryContractReview(QueryCriteria criteria) {
        return lteWeeklyMapper.queryContractReview(criteria);
    }

    @Override
    public List<Map> queryNameAndReporter(String projectCode) {
        return lteWeeklyMapper.queryNameAndReporter(projectCode);
    }

    @Override
    public String getTarget(String projectCode, String logTime) {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("projectCode", projectCode);
        criteria.put("logTime", logTime.substring(0, 8));
        String target = lteWeeklyMapper.getTarget(criteria);
        return Common.judgeString(target) ? target : "";
    }

    @Override
    public String queryEmployeeProvinces(String employeeCode) {
        String provinces = lteWeeklyMapper.queryEmployeeProvinces(employeeCode);
        return Common.judgeString(provinces) ? provinces : "";
    }

    private int validDispatchAndArchive(QueryCriteria criteria, String dispatch, String archive) {
        Map<String, Double> preDispatchArchive = lteWeeklyMapper.queryPreDispatchArchive(criteria);
        if (preDispatchArchive != null) {
            if (Common.judgeString(dispatch) && preDispatchArchive.get("dispatch") > Double.parseDouble(dispatch.replace("次", ""))) {
                return 1;
            }
            if (Common.judgeString(archive) && preDispatchArchive.get("archive") >  Double.parseDouble(archive.replace("次", ""))) {
                return 2;
            }
        }
        return 0;
    }

    private void setStartEndDate(QueryCriteria criteria) {
        String startDate = String.valueOf(criteria.get("startDate"));
        String endDate = String.valueOf(criteria.get("endDate"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        List<String> list = new ArrayList<String>();
        try {
            if (Common.judgeString(startDate) && !Common.judgeString(endDate)) {
                endDate = sdf.format(new Date());
            }
            if (!Common.judgeString(startDate) && Common.judgeString(endDate)) {
                Date endDat = sdf.parse(endDate);
                Calendar c = Calendar.getInstance();
                c.setTime(endDat);
                startDate = c.get(Calendar.YEAR) + "-" + String.format("%02d", (c.get(Calendar.MONTH)));
            }
            if (!Common.judgeString(startDate) && !Common.judgeString(endDate)) {
                endDate = sdf.format(new Date());
                Date endDat = sdf.parse(endDate);
                Calendar c = Calendar.getInstance();
                c.setTime(endDat);
                startDate = c.get(Calendar.YEAR) + "-" + String.format("%02d", (c.get(Calendar.MONTH)));
            }
            criteria.put("startDate", startDate);
            criteria.put("endDate", endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPreLogTime(String logTime) {
        String preLogTime = logTime.substring(0, 9);
        String sufLogTime = logTime.substring(9);
        if ("第二周".equals(sufLogTime)) {
            return preLogTime + "第一周";
        }
        if ("第三周".equals(sufLogTime)) {
            return preLogTime + "第二周";
        }
        if ("第四周".equals(sufLogTime)) {
            return preLogTime + "第三周";
        }
        if ("第五周".equals(sufLogTime)) {
            return preLogTime + "第四周";
        }
        return preLogTime + "第一周";
    }

    @Override
    public boolean validProjectCode(String projectCode) {
        return lteWeeklyMapper.validProjectCode(projectCode) > 0 ? true : false;
    }

    @Override
    public boolean validProjectName(String projectName) {
        return lteWeeklyMapper.validProjectName(projectName) > 0 ? true : false;
    }

    @Override
    public List<Map> queryProLogTime(QueryCriteria criteria) {
        return lteWeeklyMapper.queryProLogTime(criteria);
    }
}
