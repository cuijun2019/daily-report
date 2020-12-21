package com.etone.project.modules.lte.web;

import Alert.weChat.send_weChatMsg;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.ILteWeeklyManager;
import com.etone.project.utils.ResponseUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modules/lteWeekly")
@Auditmeta(code = "003", name = "运营周报", symbol = "")
public final class LteWeeklyController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(LteWeeklyController.class);

    @Autowired
    private ILteWeeklyManager lteWeeklyManager;

    @ResponseBody
    @RequestMapping(value = "/getUserId", method = {RequestMethod.GET, RequestMethod.POST})
    public void getUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String corpid = request.getParameter("corpid");
        String corpsecret = request.getParameter("corpsecret");
        String code = request.getParameter("code");
        send_weChatMsg sw = new send_weChatMsg();
        String token = lteWeeklyManager.getToken(corpid, corpsecret);
        String userId = lteWeeklyManager.getUserId(token, code);
        JSONObject json = new JSONObject();
        json.put("userId", userId);
        Map<String, String> map = lteWeeklyManager.queryEmployeeByCode(userId);
        json.put("employee", map.get("employee"));
        json.put("position", map.get("position"));
        json.put("provinces", map.get("provinces"));

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
     @RequestMapping(value = "/saveWeeklyInfo", method = {RequestMethod.GET, RequestMethod.POST})
     public void saveWeeklyInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");

        try {
            lteWeeklyManager.saveWeeklyInfo(request);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            result = new Result(Result.ERROR, "保存失败", "保存失败");
        } finally {
            writer = response.getWriter();
            System.out.println(result.toString());
            writer.println(result.toString());  //想办法把map转成json
            writer.flush();
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/validWeekly", method = {RequestMethod.GET, RequestMethod.POST})
    public void validWeekly(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = lteWeeklyManager.validWeekly(request);
        JSONObject json = new JSONObject();
        json.put("message", message);

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryWeeklyInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryWeeklyInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employeeCode", request.getParameter("employeeCode"));
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        List<Map> list = lteWeeklyManager.queryWeeklyInfo(criteria);

        ResponseUtils.printArrayList(response, list);
    }

    @RequestMapping(value = "/queryLogTime", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryLogTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("projectCode", request.getParameter("projectCode"));
        criteria.put("logTime", request.getParameter("logTime"));
        String logTime = lteWeeklyManager.queryLogTime(criteria);

        JSONObject json = new JSONObject();
        json.put("logTime", logTime);

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryProject", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employeeCode", request.getParameter("employeeCode"));
        criteria.put("position", request.getParameter("position"));
        criteria.put("provinces", request.getParameter("provinces"));
        criteria.put("searchContext", request.getParameter("searchContext"));
        List<Map> list = lteWeeklyManager.queryProject(criteria);

        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryWeeklySummary", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryWeeklySummary(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("type", request.getParameter("type"));
        criteria.put("employeeCode", request.getParameter("employeeCode"));
        criteria.put("logTime", request.getParameter("logTime"));
        criteria.put("province", request.getParameter("province"));
        criteria.put("position", request.getParameter("position"));
        List<Map> list = lteWeeklyManager.queryWeeklySummary(criteria);

        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryProjectSummary", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryProjectSummary(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("type", request.getParameter("type"));
        criteria.put("logTime", request.getParameter("logTime"));
        criteria.put("province", request.getParameter("province"));
        criteria.put("projectCode", request.getParameter("projectCode"));
        List<Map> list = lteWeeklyManager.queryProjectSummary(criteria);

        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryWeekSummary", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryWeekSummary(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employeeCode", request.getParameter("employeeCode"));
        criteria.put("logTime", request.getParameter("logTime"));
        criteria.put("province", request.getParameter("province"));
        criteria.put("position", request.getParameter("position"));
        criteria.put("projectCode", request.getParameter("projectCode"));
        List<Map> list = lteWeeklyManager.queryWeekSummary(criteria);

        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/validProjectCode", method = {RequestMethod.GET, RequestMethod.POST})
    public void validProjectCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String projectCode = "";
        if (8080 == request.getServerPort()) {
            projectCode = new String(request.getParameter("projectCode").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectCode = request.getParameter("projectCode");
        }
        List<Map> nameAndReporter = lteWeeklyManager.queryNameAndReporter(projectCode);
        JSONObject json = new JSONObject();
        if (nameAndReporter != null && !nameAndReporter.isEmpty()) {
            json.put("valid", true);
            json.put("projectName", nameAndReporter.get(0).get("projectName"));
            json.put("manager", nameAndReporter.get(0).get("manager"));
            json.put("managerCode", nameAndReporter.get(0).get("managerCode"));
            json.put("province", nameAndReporter.get(0).get("province"));
            json.put("target", lteWeeklyManager.getTarget(projectCode, request.getParameter("logTime")));
        } else {
            json.put("valid", false);
            json.put("projectName", "");
            json.put("manager", "");
            json.put("target", "");
        }

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/validProjectName", method = {RequestMethod.GET, RequestMethod.POST})
    public void validProjectName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String projectName = request.getParameter("projectName");
//        if (8080 == request.getServerPort()) {
//            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
//        } else {
//            projectName = request.getParameter("projectName");
//        }
        List<Map> codeAndReporter = lteWeeklyManager.queryCodeAndReporter(projectName);
        JSONObject json = new JSONObject();
        if (codeAndReporter != null && !codeAndReporter.isEmpty()) {
            String projectCode = String.valueOf(codeAndReporter.get(0).get("projectCode"));
            json.put("valid", true);
            json.put("projectCode", projectCode);
            json.put("manager", codeAndReporter.get(0).get("manager"));
            json.put("managerCode", codeAndReporter.get(0).get("managerCode"));
            json.put("province", codeAndReporter.get(0).get("province"));
            json.put("target", lteWeeklyManager.getTarget(projectCode, request.getParameter("logTime")));
        } else {
            json.put("valid", false);
            json.put("projectCode", "");
            json.put("manager", "");
            json.put("target", "");
        }

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/getTarget", method = {RequestMethod.GET, RequestMethod.POST})
    public void getTarget(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String projectCode = request.getParameter("projectCode");
        String logTime = request.getParameter("logTime");
        JSONObject json = new JSONObject();
        json.put("target", lteWeeklyManager.getTarget(projectCode, logTime));

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeProvinces", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryEmployeeProvinces(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String employeeCode = request.getParameter("employeeCode");
        JSONObject json = new JSONObject();
        json.put("provinces", lteWeeklyManager.queryEmployeeProvinces(employeeCode));

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryContractReview", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryContractReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("managerCode", request.getParameter("managerCode"));
        List<Map> list = lteWeeklyManager.queryContractReview(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryProLogTime", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryProLogTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employee", request.getParameter("employee"));
        criteria.put("time", request.getParameter("time"));
        List<Map> list = lteWeeklyManager.queryProLogTime(criteria);
        ResponseUtils.printArrayList(response, list);
    }
}