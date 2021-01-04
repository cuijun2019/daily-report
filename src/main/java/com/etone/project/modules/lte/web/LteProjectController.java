package com.etone.project.modules.lte.web;

import Alert.weChat.send_weChatMsg;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.utils.StringUtils;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.ILteProjectManager;
import com.etone.project.utils.Common;
import com.etone.project.utils.ResponseUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/modules/lteproject")
@Auditmeta(code = "003", name = "项目信息", symbol = "")
public final class LteProjectController extends GenericController {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    private static final Logger logger = LoggerFactory.getLogger(LteProjectController.class);

    private final static String ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    private final static String USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s";
    private final static String USER_DETAIL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";

    @Autowired
    private ILteProjectManager lteProjectManager;

    @Autowired
    private RestTemplate restTemplate;

    @ResponseBody
    @RequestMapping(value = "/getAccessToken", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String corpid = request.getParameter("corpid");
        String corpsecret = request.getParameter("corpsecret");

        send_weChatMsg sw = new send_weChatMsg();
        String accesstoken = sw.getToken(corpid,corpsecret);
        JSONObject json = new JSONObject();
        json.put("accesstoken", accesstoken);

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/getUserId", method = {RequestMethod.GET, RequestMethod.POST})
    public void getUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String corpid = request.getParameter("corpid");
        String corpsecret = request.getParameter("corpsecret");
        String code = request.getParameter("code");

        ResponseEntity<String> re = restTemplate.exchange(String.format(ACCESS_TOKEN, corpid, corpsecret), HttpMethod.GET, null, String.class);
        com.alibaba.fastjson.JSONObject result = re.getStatusCode() == HttpStatus.OK ? com.alibaba.fastjson.JSONObject.parseObject(re.getBody()) : null;
        String token = result.getString("access_token");

        re = restTemplate.exchange(String.format(USER_INFO, token, code), HttpMethod.GET, null, String.class);
        result = re.getStatusCode() == HttpStatus.OK ? com.alibaba.fastjson.JSONObject.parseObject(re.getBody()) : null;
        String userId = result.getString("UserId");

        re = restTemplate.exchange(String.format(USER_DETAIL, token, userId), HttpMethod.GET, null, String.class);
        result = re.getStatusCode() == HttpStatus.OK ? com.alibaba.fastjson.JSONObject.parseObject(re.getBody()) : null;
        String employee = result.getString("name");

        JSONObject json = new JSONObject();
        json.put("userId", userId);
        json.put("employee", employee);

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryContractReview", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryContractReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employee", URLDecoder.decode(request.getParameter("employee"), "UTF-8"));
        List<Map> list = lteProjectManager.queryContractReview(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryEmployeeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map> list = lteProjectManager.queryEmployeeInfo();
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryLatestLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryLatestLogInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map> list = lteProjectManager.queryLatestLogInfo(request.getParameter("employeeCode"));
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeProject", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryEmployeeProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employeeCode", request.getParameter("employeeCode"));
        criteria.put("startDate", request.getParameter("staffStartDate"));
        criteria.put("endDate", request.getParameter("staffEndDate"));
        List<Map> list = lteProjectManager.queryEmployeeProject(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/validProjectCode", method = {RequestMethod.GET, RequestMethod.POST})
    public void validProjectCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String projectCode = "";
        if (443 == request.getServerPort()) {
            projectCode = new String(request.getParameter("projectCode").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectCode = request.getParameter("projectCode");
        }
        List<Map> nameAndReporter = lteProjectManager.queryNameAndReporter(projectCode);
        JSONObject json = new JSONObject();
        if (nameAndReporter != null && !nameAndReporter.isEmpty()) {
            json.put("valid", true);
            json.put("projectName", nameAndReporter.get(0).get("projectName"));
            json.put("reporter", nameAndReporter.get(0).get("reporter"));
        } else {
            json.put("valid", false);
            json.put("projectName", "");
            json.put("reporter", "");
        }

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/validProjectName", method = {RequestMethod.GET, RequestMethod.POST})
    public void validProjectName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String projectName = "";
        if (443 == request.getServerPort()) {
            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectName = request.getParameter("projectName");
        }
        List<Map> codeAndReporter = lteProjectManager.queryCodeAndReporter(projectName);
        JSONObject json = new JSONObject();
        if (codeAndReporter != null && !codeAndReporter.isEmpty()) {
            json.put("valid", true);
            json.put("projectCode", codeAndReporter.get(0).get("projectCode"));
            json.put("reporter", codeAndReporter.get(0).get("reporter"));
        } else {
            json.put("valid", false);
            json.put("projectCode", "");
            json.put("reporter", "");
        }

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/validReporter", method = {RequestMethod.GET, RequestMethod.POST})
    public String validReporter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reporter = "";
        if (443 == request.getServerPort()) {
            reporter = new String(request.getParameter("reporter").getBytes("ISO8859-1"), "UTF-8");
        } else {
            reporter = request.getParameter("reporter");
        }
        boolean isExist = lteProjectManager.validReporter(reporter);
        if (isExist) {
            return "{\"valid\":true}";
        }
        return "{\"valid\":false}";
    }

    @ResponseBody
    @RequestMapping(value = "/validEmployeeCode", method = {RequestMethod.GET, RequestMethod.POST})
    public void validEmployeeCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String employee = lteProjectManager.validEmployeeCode(request.getParameter("employeeCode"));
        JSONObject json = new JSONObject();
        json.put("valid", Common.judgeString(employee));
        json.put("employee", employee);

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/validEmployee", method = {RequestMethod.GET, RequestMethod.POST})
    public void validEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String employee = "";
        if (443 == request.getServerPort()) {
            employee = new String(request.getParameter("employee").getBytes("ISO8859-1"), "UTF-8");
        } else {
            employee = request.getParameter("employee");
        }
        String employeeCode = lteProjectManager.validEmployee(employee);
        JSONObject json = new JSONObject();
        json.put("valid", Common.judgeString(employeeCode));
        json.put("employee", employeeCode);

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/validProportion", method = {RequestMethod.GET, RequestMethod.POST})
    public String validProportion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String proportion = "";
        if (443 == request.getServerPort()) {
            proportion = new String(request.getParameter("proportion").getBytes("ISO8859-1"), "UTF-8");
        } else {
            proportion = request.getParameter("proportion");
        }
        boolean isExist = lteProjectManager.validProportion(proportion);
        if (isExist) {
            return "{\"valid\":true}";
        }
        return "{\"valid\":false}";
    }

    @ResponseBody
    @RequestMapping(value = "/validRepeatLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void validRepeatLogInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean flag = lteProjectManager.validRepeatLogInfo( request.getParameter("projectCode"), request.getParameter("employeeCode"), request.getParameter("logTime"));
        JSONObject json = new JSONObject();
        json.put("message", flag ? "同一时间不能写项目一样的日志" : "");

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/validLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void validLogInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = lteProjectManager.validLogInfo(request);
        JSONObject json = new JSONObject();
        json.put("message", message);

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryReporter", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryReporter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String projectName = "";
        if (443 == request.getServerPort()) {
            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectName = request.getParameter("projectName");
        }
        List<String> reporters = lteProjectManager.queryReporter(projectName);
        JSONObject json = new JSONObject();
        if (reporters != null && !reporters.isEmpty()) {
            json.put("reporter", reporters.get(0));
        }
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/saveLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void saveLogInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");
        QueryCriteria criteria = new QueryCriteria();
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            criteria.put("projectCode", request.getParameter("projectCode"));
            criteria.put("projectName", request.getParameter("projectName"));
            criteria.put("reporter", request.getParameter("reporter"));
            criteria.put("proportion", request.getParameter("proportion"));
            criteria.put("logTime", request.getParameter("logTime"));
            criteria.put("workNature", request.getParameter("workNature"));
            criteria.put("context", request.getParameter("context"));
            criteria.put("createTime", simpledateformat.format(new Date()));
            criteria.put("employeeCode", request.getParameter("employeeCode"));
            criteria.put("employee", request.getParameter("employee"));
            lteProjectManager.saveLogInfo(criteria);
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
     @RequestMapping(value = "/saveLogInfo2", method = {RequestMethod.GET, RequestMethod.POST})
     public void saveLogInfo2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");
        QueryCriteria criteria = new QueryCriteria();
        String logInfoListJson = request.getParameter("logInfoList");

        try {
            lteProjectManager.saveLogInfo(logInfoListJson);
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
        PageResult<Map> page = lteProjectManager.queryContractReviewInfo(criteria);
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
        PageResult<Map> page = lteProjectManager.queryLogInfo(criteria);
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
        PageResult<Map> page = lteProjectManager.queryStatisticsInfo(criteria);
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
        criteria.put("projectName", String.valueOf(query.get("projectName")));
        criteria.put("employee", String.valueOf(query.get("employee")));
        criteria.put("startDate", String.valueOf(query.get("startDate")));
        criteria.put("endDate", String.valueOf(query.get("endDate")));
        criteria.setPageSize(Integer.parseInt(String.valueOf(query.get("limit"))));
        criteria.setRowStart(Integer.parseInt(String.valueOf(query.get("offset"))));
        PageResult<Map> page = lteProjectManager.queryFinalStatisticsInfo(criteria);
        Results results = Results.getPage(page, Map.class);
        results.setTotal(page.getTotalItems());
        return results;
    }

    @ResponseBody
    @RequestMapping(value = "/queryContractReviewList", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryContractReviewList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int current = Integer.parseInt(request.getParameter("current"));
        int rowCount = Integer.parseInt(request.getParameter("rowCount"));
        String id = request.getParameter("sort");
        int rowStart = current == 1 ? current : (current - 1) * rowCount + 1;
        int pageSize = rowCount;

        QueryCriteria criteria = new QueryCriteria();
        criteria.put("rowStart", rowStart);
        criteria.put("pageSize", pageSize);
        if (StringUtils.isNotEmpty(id)) {
            criteria.put("sort", "order by id " + id);
        }
        List<Map> list = lteProjectManager.queryContractReview(criteria);
        JSONObject json = new JSONObject();
        json.put("current", current);
        json.put("rowCount", rowCount);
        json.put("total", lteProjectManager.countContractReview(criteria));

        JSONArray childJsonMembers = new JSONArray();
        JSONObject childMember = new JSONObject();
        for (Map map : list) {
            childMember.put("id", map.get("id"));
            childMember.put("projectName", map.get("projectName"));
            childMember.put("reporter", map.get("reporter"));
            childJsonMembers.add(childMember);
        }
        json.put("rows", childJsonMembers);

        System.out.println("jsonString:" + json.toString());
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/importContractReview", method = {RequestMethod.GET, RequestMethod.POST})
    public void importContractReview(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileUpload") MultipartFile fileUpload) throws InterruptedException, IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "导入成功！", "导入成功！");
        boolean flag = true;
//        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        InputStream inputStream = null;
        org.apache.poi.ss.usermodel.Workbook book = null;
        org.apache.poi.ss.usermodel.Sheet sheet = null;
        String fileName = "";
        try {
            inputStream = lteProjectManager.validExcelFile(fileMap);

            if(inputStream == null) {
                result = new Result(Result.ERROR, "导入失败，导入的文件错误！", "导入失败，导入的文件错误！");
                flag = false;
            }
            System.out.println("----start-----");
            fileName = fileMap.values().iterator().next().getOriginalFilename();
            if (fileName.endsWith(".xls")) {
                book = new HSSFWorkbook(inputStream);
            } else if(fileName.endsWith(".xlsx")) {
                book = new XSSFWorkbook(inputStream);
            } else {
                result = new Result(Result.ERROR, "导入失败，文件类型错误！", "导入失败，文件类型错误！");
                flag = false;
            }
            System.out.println("-----end------");
            sheet = book.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            String[] titleArr = new String[] { "项目编码", "项目名称", "项目经理", "合同签订金额", "合同签订时间", "市场负责人", "项目开始时间", "项目结束时间" };

            String message = lteProjectManager.validExcelTitle(sheet, titleArr);
            if (Common.judgeString(message)) {
                result = new Result(Result.ERROR, message, message);
                flag = false;
            }
            message = lteProjectManager.validExcelData(rows);
            if (Common.judgeString(message)) {
                result = new Result(Result.ERROR, message, message);
                flag = false;
            }

            if (flag) {
                Map dataMap = null;
                List dataList = new ArrayList();
                String projectCode = "";
                List<String> projectCodes = lteProjectManager.queryProjectCode();
                List<String> repeatCodes = new ArrayList<String>();
                for (int row = 1; row < rows; row++) {
                    dataMap = lteProjectManager.getExcelData(row + 1, sheet, dataList);
                    message = String.valueOf(dataMap.get("message"));
                    if (Common.judgeString(message)) {
                        result = new Result(Result.ERROR, message, message);
                        return;
                    }
                    projectCode = String.valueOf(dataMap.get("projectCode"));
                    if (Common.judgeString(projectCode)) {
                        dataList.add(dataMap);
                    }
                    if (projectCodes.contains(projectCode)) {
                        repeatCodes.add(projectCode);
                    }
                }
                if (repeatCodes != null && !repeatCodes.isEmpty()) {
                    criteria.put("repeatCodes", repeatCodes);
                    lteProjectManager.deleteData(criteria);
                }
                for (int i = 1,length=dataList.size() / 150 + 1; i <= length; i++) {
                    criteria.put("dataList", getPagedList(i, 150, dataList));
                    lteProjectManager.saveData(criteria);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            result = new Result(Result.ERROR, "导入失败", "导入失败");
        } finally {
            writer = response.getWriter();
            System.out.println(result.toString());
            writer.println(result.toString());  //想办法把map转成json
            writer.flush();
            //关闭文件流
            try {
                inputStream.close();
            } catch (Exception  e) {
                logger.error(e.getMessage(), e);
            }
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
    @RequestMapping(value = "/importLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void importLogInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileUpload") MultipartFile fileUpload) throws InterruptedException, IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "导入成功！", "导入成功！");
        boolean flag = true;
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
//        initParameters(criteria, query);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        InputStream inputStream = null;
        org.apache.poi.ss.usermodel.Workbook book = null;
        org.apache.poi.ss.usermodel.Sheet sheet = null;
        try {
            inputStream = lteProjectManager.validExcelFile(fileMap);

            if(inputStream == null) {
                result = new Result(Result.ERROR, "导入失败，导入的文件错误！", "导入失败，导入的文件错误！");
                flag = false;
            }
            book = WorkbookFactory.create(inputStream);
            sheet = book.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            String[] titleArr = new String[] { "项目编码", "项目名称", "项目经理", "项目占比", "日志时间", "员工工号", "员工姓名", "工作性质", "日志内容" };

            String message = lteProjectManager.validExcelTitle(sheet, titleArr);
            if (Common.judgeString(message)) {
                result = new Result(Result.ERROR, message, message);
                flag = false;
            }
            message = lteProjectManager.validExcelData(rows);
            if (Common.judgeString(message)) {
                result = new Result(Result.ERROR, message, message);
                flag = false;
            }

            if (flag) {
                Map dataMap = null;
                List<Map> dataList = new ArrayList<Map>();
                String projectCode = "";
                for (int row = 1; row < rows; row++) {
                    dataMap = lteProjectManager.getLogExcelData(row + 1, sheet, dataList);
                    message = String.valueOf(dataMap.get("message"));
                    if (Common.judgeString(message)) {
                        result = new Result(Result.ERROR, message, message);
                        return;
                    }
                    projectCode = String.valueOf(dataMap.get("projectCode"));
                    if (!Common.judgeString(projectCode)) {
                        break;
                    }
                    criteria.put("employeeCode", dataMap.get("employeeCode").toString());
                    criteria.put("logTime", dataMap.get("logTime").toString());
                    if (lteProjectManager.querySumProportion(criteria) + Double.parseDouble(dataMap.get("proportion").toString().replace("%", "")) != 100) {
                        result = new Result(Result.ERROR, "导入失败，第" + row + "行，项目占比之和应等于100", "导入失败，第" + row + "行，项目占比之和应等于100");
                        return;
                    }
                    if (Common.judgeString(projectCode)) {
                        dataList.add(dataMap);
                    }
                }
                for (int i = 1,length=dataList.size() / 150 + 1; i <= length; i++) {
                    criteria.put("dataList", getPagedList(i, 150, dataList));
                    lteProjectManager.saveLogData(criteria);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            result = new Result(Result.ERROR, "导入失败", "导入失败");
        } finally {
            writer = response.getWriter();
            System.out.println(result.toString());
            writer.println(result.toString());  //想办法把map转成json
            writer.flush();
            //关闭文件流
            try {
                inputStream.close();
            } catch (Exception  e) {
                logger.error(e.getMessage(), e);
            }
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
    @RequestMapping(value = "/exportData", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileName = "日志信息";
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
        String ids = (String)query.get("ids");
        if(Common.judgeString(ids)){
            List<String> idList = Arrays.asList(ids.split(","));
            criteria.put("id", idList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1" ) + ".xls");
            OutputStream os = response.getOutputStream();

            lteProjectManager.exportData(os, criteria);
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
        String projectName = "";
        String reporter = "";
        if (443 == request.getServerPort()) {
            projectName = new String(request.getParameter("projectName").getBytes("ISO8859-1"), "UTF-8");
            reporter = new String(request.getParameter("reporter").getBytes("ISO8859-1"), "UTF-8");
        } else {
            projectName = request.getParameter("projectName");
            reporter = request.getParameter("reporter");
        }
        criteria.put("projectName", projectName);
        criteria.put("reporter", reporter);
        String ids = (String)query.get("ids");
        if (Common.judgeString(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            criteria.put("id", idList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1" ) + ".xls");
            OutputStream os = response.getOutputStream();

            lteProjectManager.exportContractReviewData(os, criteria);
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
        if(Common.judgeString(employeeCodes)){
            criteria.put("employee", "");
            List<String> employeeCodeList = Arrays.asList(employeeCodes.split(","));
            criteria.put("employeeCodeList", employeeCodeList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1" ) + ".xls");
            OutputStream os = response.getOutputStream();

            lteProjectManager.exportStatisticsData(os, criteria);
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
        String ids = (String)query.get("ids");
        if (Common.judgeString(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            criteria.put("id", idList);
        }
        criteria.setPageSize(null);
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO8859-1" ) + ".xls");
            OutputStream os = response.getOutputStream();

            lteProjectManager.exportFinalStatisticsData(os, criteria);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteProjectInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteProjectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "删除成功！", "删除成功！");
        QueryCriteria criteria = new QueryCriteria();
        try {
            List<Long> ids = Common.stringToList(request.getParameter("ids"));
            lteProjectManager.deleteProjectInfo(ids);
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

    @ResponseBody
    @RequestMapping(value = "/deleteLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteLogInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "删除成功！", "删除成功！");
        QueryCriteria criteria = new QueryCriteria();
        String message = "";
        try {
            List<Long> ids = Common.stringToList(request.getParameter("ids"));
            criteria.put("ids", ids);
            message = lteProjectManager.deleteLogInfo(criteria);
            if (Common.judgeString(message)) {
                result = new Result(Result.ERROR, message, message);
            }
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

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateProject", method = {RequestMethod.GET, RequestMethod.POST})
    public void saveOrUpdateProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");
        QueryCriteria criteria = new QueryCriteria();
        try {
            String contractTime = request.getParameter("contractTime");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            criteria.put("id", request.getParameter("id"));
            criteria.put("projectCode", request.getParameter("projectCode"));
            criteria.put("projectName", request.getParameter("projectName"));
            criteria.put("reporter", request.getParameter("reporter"));
            criteria.put("contractAmount", request.getParameter("contractAmount"));
            criteria.put("contractTime", Common.judgeString(contractTime) ? contractTime : "");
            criteria.put("marketLeader", request.getParameter("marketLeader"));
            criteria.put("startTime", Common.judgeString(startTime) ? startTime : "");
            criteria.put("endTime", Common.judgeString(endTime) ? endTime : "");
            lteProjectManager.saveOrUpdateProjectInfo(criteria);
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
    @RequestMapping(value = "/saveOrUpdateLog", method = {RequestMethod.GET, RequestMethod.POST})
    public void saveOrUpdateLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");
        QueryCriteria criteria = new QueryCriteria();
        try {
            String logTime = request.getParameter("logTime");
            criteria.put("id", request.getParameter("id"));
            criteria.put("projectCode", request.getParameter("projectCode"));
            criteria.put("projectName", request.getParameter("projectName"));
            criteria.put("reporter", request.getParameter("reporter"));
            criteria.put("proportion", request.getParameter("proportion"));
            criteria.put("logTime", Common.judgeString(logTime) ? logTime : "");
            criteria.put("employeeCode", request.getParameter("employeeCode"));
            criteria.put("employee", request.getParameter("employee"));
            criteria.put("workNature", request.getParameter("workNature"));
            criteria.put("context", request.getParameter("context"));
            lteProjectManager.saveOrUpdateLogInfo(criteria);
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
    @RequestMapping(value = "/queryOwnLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryOwnLogInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employeeCode", request.getParameter("employeeCode"));
        criteria.put("projectCode", request.getParameter("projectCode"));
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        List<Map> list = lteProjectManager.queryOwnLogInfo(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    private List getPagedList(int pageNum, int pageSize, List list) {
        int pageNumTemp = pageNum;
        int pageSizeTemp = pageSize;
        List listTemp = list;

        int fromIndex = (pageNumTemp - 1) * pageSizeTemp;
        if (fromIndex >= listTemp.size()) {
            return Collections.emptyList();
        }

        int toIndex = pageNumTemp * pageSizeTemp;
        if (toIndex >= listTemp.size()) {
            toIndex = listTemp.size();
        }
        return list.subList(fromIndex, toIndex);
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
    public void upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileUpload") MultipartFile fileUpload) throws InterruptedException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        InputStream inputStream = fileMap.values().iterator().next().getInputStream();
        Common.copyFile(inputStream, request.getParameter("uploadpath"));
    }

    @ResponseBody
    @RequestMapping(value = "/createRingChart", method = {RequestMethod.GET, RequestMethod.POST})
    public void createRingChart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        String jsonString = lteProjectManager.createRingChart(criteria);
        ResponseUtils.print(response, jsonString);
    }

    @ResponseBody
    @RequestMapping(value = "/queryAppraiseEmployee", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryAppraiseEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        criteria.put("type", request.getParameter("type"));
        ResponseUtils.printArrayList(response, lteProjectManager.queryAppraiseEmployee(criteria));
    }

    @ResponseBody
    @RequestMapping(value = "/queryProjectLine", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryProjectLine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        List<Map> list = lteProjectManager.queryProjectLine(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryProjectByLine", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryProjectByLine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("lineName", request.getParameter("lineName"));
        criteria.put("searchData", request.getParameter("searchData"));
        List<Map> list = lteProjectManager.queryProjectByLine(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryLineNameByCode", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryLineNameByCode(HttpServletRequest request, HttpServletResponse response) throws IOException {;
        String lineName = lteProjectManager.queryLineNameByCode(request.getParameter("projectCode"));
        JSONObject json = new JSONObject();
        json.put("lineName", lineName);
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeLog", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryEmployeeLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employeeCode", request.getParameter("employeeCode"));
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        criteria.put("type", request.getParameter("type"));
        List<Map> list = lteProjectManager.queryEmployeeLog(criteria);
        ResponseUtils.printArrayList(response, list);
    }
}