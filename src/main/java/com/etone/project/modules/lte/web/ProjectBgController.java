package com.etone.project.modules.lte.web;

import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.IProjectManager;
import com.etone.project.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modules/projectbg")
@Auditmeta(code = "003", name = "项目信息", symbol = "")
public final class ProjectBgController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectBgController.class);

    @Autowired
    private IProjectManager projectManager;

    @ResponseBody
    @RequestMapping(value = "/queryContractReviewInfo", method = RequestMethod.POST)
    public Object queryContractReviewInfo(HttpServletRequest request) {
        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        criteria.put("projectName", String.valueOf(query.get("projectName")));
        criteria.put("reporter", String.valueOf(query.get("reporter")));
        criteria.setPageSize(Integer.parseInt(String.valueOf(query.get("limit"))));
        criteria.setRowStart(Integer.parseInt(String.valueOf(query.get("offset"))));
        PageResult<Map> page = projectManager.queryContractReviewInfo(criteria);
        Results results = Results.getPage(page, Map.class);
        results.setTotal(page.getTotalItems());
        return results;
    }

    @ResponseBody
    @RequestMapping(value = "/queryLogInfo", method = RequestMethod.POST)
    public Object queryLogInfo(HttpServletRequest request) throws InterruptedException {
        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        criteria.put("projectName", String.valueOf(query.get("projectName")));
        criteria.put("employee", String.valueOf(query.get("employee")));
        criteria.put("startDate", String.valueOf(query.get("startDate")));
        criteria.put("endDate", String.valueOf(query.get("endDate")));
        criteria.setPageSize(Integer.parseInt(String.valueOf(query.get("limit"))));
        criteria.setRowStart(Integer.parseInt(String.valueOf(query.get("offset"))));
        PageResult<Map> page = projectManager.queryLogInfo(criteria);
        Results results = Results.getPage(page, Map.class);
        results.setTotal(page.getTotalItems());

        return results;
    }

    @ResponseBody
    @RequestMapping(value = "/queryStatisticsInfo", method = RequestMethod.POST)
    public Object queryStatisticsInfo(HttpServletRequest request) throws IOException {
        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        String employee = request.getParameter("employee");
        if (Common.judgeString(employee)) {
            criteria.put("employee", employee);
        }
        criteria.put("startDate", String.valueOf(query.get("startDate")));
        criteria.put("endDate", String.valueOf(query.get("endDate")));
        criteria.put("payCondition", String.valueOf(query.get("payCondition")));
        criteria.setPageSize(Integer.parseInt(String.valueOf(query.get("limit"))));
        criteria.setRowStart(Integer.parseInt(String.valueOf(query.get("offset"))));
        PageResult<Map> page = projectManager.queryStatisticsInfo(criteria);
        Results results = Results.getPage(page, Map.class);
        results.setTotal(page.getTotalItems());
        return results;
    }

    @ResponseBody
    @RequestMapping(value = "/queryFinalStatisticsInfo", method = RequestMethod.POST)
    public Object queryFinalStatisticsInfo(HttpServletRequest request) {
        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        criteria.put("orgCode", String.valueOf(query.get("orgCode")));
        criteria.put("projectName", String.valueOf(query.get("projectName")));
        criteria.put("employee", String.valueOf(query.get("employee")));
        criteria.put("startDate", String.valueOf(query.get("startDate")));
        criteria.put("endDate", String.valueOf(query.get("endDate")));
        criteria.setPageSize(Integer.parseInt(String.valueOf(query.get("limit"))));
        criteria.setRowStart(Integer.parseInt(String.valueOf(query.get("offset"))));
        PageResult<Map> page = projectManager.queryFinalStatisticsInfo(criteria);
        Results results = Results.getPage(page, Map.class);
        results.setTotal(page.getTotalItems());
        return results;
    }

    @ResponseBody
    @RequestMapping(value = "/queryReporterEmployeeInfo", method = RequestMethod.POST)
    public Object queryReporterEmployeeInfo(HttpServletRequest request) {
        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        criteria.put("projectCode", String.valueOf(query.get("projectCode")));
        criteria.put("projectName", String.valueOf(query.get("projectName")));
        criteria.put("reporter", String.valueOf(query.get("reporter")));
        criteria.put("employee", String.valueOf(query.get("employee")));
        criteria.setPageSize(Integer.parseInt(String.valueOf(query.get("limit"))));
        criteria.setRowStart(Integer.parseInt(String.valueOf(query.get("offset"))));
        PageResult<Map> page = projectManager.queryReporterEmployeeInfo(criteria);
        Results results = Results.getPage(page, Map.class);
        results.setTotal(page.getTotalItems());
        return results;
    }

    @ResponseBody
    @RequestMapping(value = "/exportData", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName = "日志信息";
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        String projectName;
        String employee;
        if (443 == request.getServerPort()) {
            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
            employee = new String(request.getParameter("employee").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectName = request.getParameter("projectName");
            employee = request.getParameter("employee");
        }
        criteria.put("projectName", projectName);
        criteria.put("employee", employee);
        criteria.put("startDate", String.valueOf(query.get("timeStart")));
        criteria.put("endDate", String.valueOf(query.get("timeEnd")));
        String ids = (String) query.get("ids");
        if (Common.judgeString(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            criteria.put("id", idList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".xls");
            OutputStream os = response.getOutputStream();

            projectManager.exportData(os, criteria);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/exportWorkDayStatData", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportWorkDayStatData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName = "员工日志信息";
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        String projectName;
        String employee;
        if (443 == request.getServerPort()) {
            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
            employee = new String(request.getParameter("employee").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectName = request.getParameter("projectName");
            employee = request.getParameter("employee");
        }
        criteria.put("projectName", projectName);
        criteria.put("employee", employee);
        criteria.put("startDate", String.valueOf(query.get("timeStart")));
        criteria.put("endDate", String.valueOf(query.get("timeEnd")));
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".xls");
            OutputStream os = response.getOutputStream();

            projectManager.exportWorkDayStatData(os, criteria);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/exportReporterEmployeeData", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportReporterEmployeeData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName = "项目经理员工关系信息";
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        String projectName;
        String reporter;
        String employee;
        if (443 == request.getServerPort()) {
            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
            reporter = new String(request.getParameter("reporter").getBytes("ISO8859-1"), "UTF-8");
            employee = new String(request.getParameter("employee").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectName = request.getParameter("projectName");
            reporter = request.getParameter("reporter");
            employee = request.getParameter("employee");
        }
        criteria.put("projectName", projectName);
        criteria.put("reporter", reporter);
        criteria.put("employee", employee);
        String ids = (String) query.get("ids");
        if (Common.judgeString(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            criteria.put("id", idList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".xls");
            OutputStream os = response.getOutputStream();

            projectManager.exportReporterEmployeeData(os, criteria);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/exportContractReviewData", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportContractReviewData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName = "项目信息";
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        String projectName;
        String reporter;
        if (443 == request.getServerPort()) {
            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
            reporter = new String(request.getParameter("reporter").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectName = request.getParameter("projectName");
            reporter = request.getParameter("reporter");
        }
        criteria.put("projectName", projectName);
        criteria.put("reporter", reporter);
        String ids = (String) query.get("ids");
        if (Common.judgeString(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            criteria.put("id", idList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".xls");
            OutputStream os = response.getOutputStream();

            projectManager.exportContractReviewData(os, criteria);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/exportStatisticsData", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportStatisticsData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName = "日志统计信息";
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        String employee = "";
        String employeeCodes = "";
        if (443 == request.getServerPort()) {
            employee = new String(request.getParameter("employee").getBytes("ISO8859-1"), "UTF-8");
            employeeCodes = new String(request.getParameter("employeeCodes").getBytes("ISO8859-1"), "UTF-8");
        } else {
            employee = request.getParameter("employee");
            employeeCodes = request.getParameter("employeeCodes");
        }
        criteria.put("payCondition", request.getParameter("payCondition"));
        criteria.put("employee", employee);
        criteria.put("startDate", String.valueOf(query.get("timeStart")));
        criteria.put("endDate", String.valueOf(query.get("timeEnd")));
        if (Common.judgeString(employeeCodes)) {
            criteria.put("employee", "");
            List<String> employeeCodeList = Arrays.asList(employeeCodes.split(","));
            criteria.put("employeeCodeList", employeeCodeList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".xls");
            OutputStream os = response.getOutputStream();

            projectManager.exportStatisticsData(os, criteria);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/exportFinalStatisticsData", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportFinalStatisticsData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName = "工时统计信息";
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        String projectName = "";
        String employee = "";
        if (443 == request.getServerPort()) {
            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
            employee = new String(request.getParameter("employee").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectName = request.getParameter("projectName");
            employee = request.getParameter("employee");
        }
        criteria.put("projectName", projectName);
        criteria.put("employee", employee);
        criteria.put("startDate", String.valueOf(query.get("timeStart")));
        criteria.put("endDate", String.valueOf(query.get("timeEnd")));
        criteria.put("orgCode", query.get("orgCode"));
        String ids = (String) query.get("ids");
        if (Common.judgeString(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            criteria.put("id", idList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".xls");
            OutputStream os = response.getOutputStream();

            projectManager.exportFinalStatisticsData(os, criteria);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateReporterEmployee", method = {RequestMethod.GET, RequestMethod.POST})
    public void saveOrUpdateProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");
        QueryCriteria criteria = new QueryCriteria();
        try {
            criteria.put("id", request.getParameter("id"));
            criteria.put("reporter", request.getParameter("reporter"));
            criteria.put("employee", request.getParameter("employee"));
            criteria.put("projectCode", request.getParameter("projectCode"));
            criteria.put("projectName", request.getParameter("projectName"));
            projectManager.saveOrUpdateReporterEmployee(criteria);
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
    @RequestMapping(value = "/deleteReporterEmployeeInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteReporterEmployeeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "删除成功！", "删除成功！");
        QueryCriteria criteria = new QueryCriteria();
        try {
            List<Long> ids = Common.stringToList(request.getParameter("ids"));
            projectManager.deleteReporterEmployeeInfo(ids);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            result = new Result(Result.ERROR, "删除失败", "删除失败");
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
}