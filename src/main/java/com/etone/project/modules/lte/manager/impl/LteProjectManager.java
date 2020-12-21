package com.etone.project.modules.lte.manager.impl;

import Alert.weChat.send_weChatMsg;
import Alert.weChat.urlData;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteProjectMapper;
import com.etone.project.modules.lte.manager.ILteProjectManager;
import com.etone.project.utils.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.Collator;
import java.text.ParseException;
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
public final class LteProjectManager implements ILteProjectManager {

	private static final Logger logger = LoggerFactory.getLogger(LteProjectManager.class);

	@Autowired
	private LteProjectMapper lteProjectMapper;

	@Override
	public List<Map> queryContractReview(QueryCriteria criteria) {
		return lteProjectMapper.queryContractReview(criteria);
	}

    @Override
    public int countContractReview(QueryCriteria criteria) {
        return lteProjectMapper.countContractReview(criteria);
    }

    @Override
    public List<Map> queryEmployeeInfo() {
        return lteProjectMapper.queryEmployeeInfo();
    }

    @Override
    public boolean validProjectCode(String projectCode) {
        return lteProjectMapper.validProjectCode(projectCode) > 0 ? true : false;
    }

    @Override
    public boolean validProjectName(String projectName) {
        return lteProjectMapper.validProjectName(projectName) > 0 ? true : false;
    }

    @Override
    public boolean validReporter(String reporter) {
        return lteProjectMapper.validReporter(reporter) > 0 ? true : false;
    }

    @Override
     public String validEmployeeCode(String employeeCode) {
        return lteProjectMapper.validEmployeeCode(employeeCode);
    }

    @Override
    public String validEmployee(String employee) {
        return lteProjectMapper.validEmployee(employee);
    }

    @Override
    public boolean validProportion(String proportion) {
        try {
            if (Common.judgeString(proportion)) {
                if (proportion.indexOf("%") != -1) {
                    proportion = proportion.replace("%", "");
                    double proportionD = Double.parseDouble(proportion);
                    if (proportionD == 100) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public String validLogInfo(HttpServletRequest request) {
        String id = request.getParameter("id");
        String employeeCode = request.getParameter("employeeCode");
        String logTime = request.getParameter("logTime");
        String workNature = request.getParameter("workNature");
        String context = request.getParameter("context");
        String logInfoListJson = request.getParameter("logInfoList");
        String[] workNatures = new String[]{"开发","测试","维护","生产","培训","部署","策划","数据分析","文档撰写","内部交流","外部交流","商务","服务交付","研究","休假","其他"};
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray jsonArray = JSONArray.fromObject(logInfoListJson);//把String转换为json
        list = JSONArray.toList(jsonArray, Map.class);//这里的t是Class<T>
        String reg = "^\\+?[1-9][0-9]*$";// 正整数
        String floagReg = "^(-?\\d+)(\\.\\d+)?$";// 数值
        String projectCode = "";
        String projectName = "";
        String proportion = "";
        int proportions = 0;
        String projectCodes = "";
        if (list == null || list.isEmpty() || list.get(0) == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectCode", request.getParameter("projectCode"));
            map.put("projectName", request.getParameter("projectName"));
            map.put("proportion", request.getParameter("proportion"));
            list.add(map);
        }
        for (Map<String, Object> map : list) {
            if (map != null) {
                projectCode = String.valueOf(map.get("projectCode"));
                projectName = String.valueOf(map.get("projectName"));
                proportion = String.valueOf(map.get("proportion"));

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
                if (!Common.judgeString(proportion)) {
                    return "项目占比不能为空";
                }
                if (!proportion.endsWith("%")) {
                    return "项目占比应以英文%结尾";
                }
                if (!proportion.substring(0, proportion.length() - 1).matches(floagReg)) {
                    return "项目占比格式不正确";
                }
                if (!proportion.substring(0, proportion.length() - 1).matches(reg)) {
                    return "项目占比应为正整数";
                }
//            跟数据库验证是否存在同一时间，同一项目，同一个人的日志
                if (!Common.judgeString(id) && this.validRepeatLogInfo("", employeeCode, logTime)) {
                    return "已经写过" + logTime + "的日志";
                }
                projectCodes += ";" + projectCode + "," + logTime;
                proportions += Double.parseDouble(proportion.replace("%", ""));
            }
        }
        if (this.checkRepeat(projectCodes.split(";"))) {
            return "同一时间不能写项目一样的日志";
        }
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("employeeCode", employeeCode);
        criteria.put("logTime", logTime);
        if (!Common.judgeString(id) && lteProjectMapper.querySumProportion(criteria) + proportions != 100) {
            return "项目占比之和只能等于100";
        }
        if (!Common.judgeString(employeeCode)) {
            return "员工工号不能为空,请退出,再进入";
        }
        if (!Common.judgeString(workNature)) {
            return "工作性质不能为空";
        } else if (!Arrays.asList(workNatures).contains(workNature)) {
            return "工作性质只能为开发、测试、维护、生产、培训、部署、策划、数据分析、文档撰写、内部交流、外部交流、商务、服务交付、研究、休假或其他";
        }
        if (!Common.judgeString(context)) {
            return "工作日志内容不能为空";
        }
        return "";
    }

    @Override
    public List<String> queryReporter(String projectName) {
        return lteProjectMapper.queryReporter(projectName);
    }

    @Override
    public String queryEmployeeByCode(String employeeCode) {
        List<String> employee = lteProjectMapper.queryEmployeeByCode(employeeCode);
        return (employee != null && !employee.isEmpty()) ? employee.get(0) : "";
    }

    @Override
    public void saveLogInfo(QueryCriteria criteria) {
        lteProjectMapper.saveLogInfo(criteria);
    }

    @Override
    public void saveLogInfo(String logInfoListJson) {
        QueryCriteria criteria = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray jsonArray = JSONArray.fromObject(logInfoListJson);//把String转换为json
        list = JSONArray.toList(jsonArray, Map.class);//这里的t是Class<T>
        for (Map map : list) {
            criteria = new QueryCriteria();
            criteria.setCondition(map);
            lteProjectMapper.saveLogInfo(criteria);
        }
    }

    @Override
    public PageResult queryContractReviewInfo(QueryCriteria criteria) {
        List<Map> actual = lteProjectMapper.queryContractReviewInfo(criteria);
        int total = lteProjectMapper.countContractReview(criteria);
        PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        return page;
    }

    @Override
    public PageResult queryLogInfo(QueryCriteria criteria) {
        List<Map> actual = lteProjectMapper.queryLogInfo(criteria);
        int total = lteProjectMapper.countLogInfo(criteria);
        PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        return page;
    }

    @Override
    public PageResult queryStatisticsInfo(QueryCriteria criteria) {
        List<Map> actual = queryStatistics(criteria);
        int rowStart = criteria.getRowStart();
        int pageSize = criteria.getPageSize();
        int size = actual.size();
        PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
        if ((pageSize + rowStart) > size) {
            page.setResult(actual.subList(rowStart, size));
        } else {
            page.setResult(actual.subList(rowStart, pageSize + rowStart));
        }
        page.setTotalItems(size);
        return page;
    }

    @Override
    public PageResult queryFinalStatisticsInfo(QueryCriteria criteria) {
        setStartEndDate(criteria);
        List<Map> actual = lteProjectMapper.queryFinalStatisticsInfo(criteria);
        List<String> workDateList = queryWorkDay(criteria);
        int workDate = workDateList.size();
        int i = 1;
        double shareWork;
        int projectCount;
        double nowManHour = 0;
        double tempHour = 1;
        for (Map map : actual) {
            projectCount = Integer.parseInt(String.valueOf(map.get("projectCount")));
            if (projectCount == 1) {
                shareWork = 22;
            } else {
                nowManHour = Double.parseDouble(String.valueOf(map.get("nowManHour")));
                tempHour = Double.parseDouble(String.valueOf(map.get("tempHour")));
                shareWork = Double.parseDouble(formatDouble(nowManHour / tempHour * 22));
            }

            map.put("id", i++);
            map.put("actualHour", workDate);
            map.put("shareWork", shareWork);
        }
        int rowStart = criteria.getRowStart();
        int pageSize = criteria.getPageSize();
        int size = actual.size();
        PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
        if ((pageSize + rowStart) > size) {
            page.setResult(actual.subList(rowStart, size));
        } else {
            page.setResult(actual.subList(rowStart, pageSize + rowStart));
        }
        page.setTotalItems(size);
        return page;
    }

    private String formatDouble(Double value) {
        String str = String.valueOf(value);
        int index = str.indexOf(".");
        int dot = 0;
        if (index != -1) {
            dot = Integer.parseInt(str.substring(index + 1, index + 2));
            if (dot < 5) {
                str = str.substring(0, index);
            } else if (dot > 5) {
                str = (Integer.parseInt(str.substring(0, index)) + 1) + ".0";
            } else {
                str = str.substring(0, index) + ".5";
            }
        }
        if ("0".equals(str)) {
            return "0.5";
        }
        return str;
    }

    private List<Map> queryStatistics(QueryCriteria criteria) {
        setStartEndDate(criteria);

        List<String> workDateList = queryWorkDay(criteria);
        String workDate = listToString(workDateList);
//        int allCount = workDateList.size();
        int allCount = countNotWorkLogs(workDate);
        String payCondition = String.valueOf(criteria.get("payCondition"));
        List<Map> list = new ArrayList<Map>();
        List<Map> payList = new ArrayList<Map>();
        String remark = "从" + criteria.get("startDate")  + "到" + criteria.get("endDate") + "，共" + allCount + "天没有交日志，具体时间分别是" + workDate.replace(",", "、") + "。";
        criteria.put("remark", remark);
        criteria.put("notLogCount", allCount);
        criteria.put("workDate", workDate);
        List<Map> unpaidList = lteProjectMapper.queryEmptyWorkLog(criteria);

        List<Map> notAllWorkLogList = lteProjectMapper.queryNotAllWorkLog(criteria);
        String employeeCode = "";
        String employee = "";
//        int count = 0;
        Map statistics = null;
        String logTimes = "";
        String notWorkLogs = "";
        for (Map notAllWorkLog : notAllWorkLogList) {
            employeeCode = String.valueOf(notAllWorkLog.get("employeeCode"));
            employee = String.valueOf(notAllWorkLog.get("employee"));
            if (Common.judgeString(employeeCode)) {
//                count = Integer.parseInt(String.valueOf(notAllWorkLog.get("count")));
                logTimes = String.valueOf(notAllWorkLog.get("logTimes"));
                statistics = new HashMap();
                if (!workDate.equals(logTimes)) {
                    notWorkLogs = queryNotWorkLog(workDateList, stringToList(logTimes));
                    if (Common.judgeString(notWorkLogs)) {
                        Integer notWorkLogsCount = countNotWorkLogs(notWorkLogs);
//                        remark = "从" + criteria.get("startDate")  + "到" + criteria.get("endDate") + "，共" + notWorkLogs.split(",").length + "天没有交日志，具体时间分别是" + notWorkLogs.replace(",", "、") + "。";
                        if (notWorkLogsCount.equals(0)){
                            remark = "从" + criteria.get("startDate")  + "到" + criteria.get("endDate") + "，共" + allCount + "天都交了日志。";
                            statistics.put("employeeCode", employeeCode);
                            statistics.put("employee", employee);
                            statistics.put("remark", remark);
                            statistics.put("notLogCount", 0);
                            statistics.put("workDate", workDate);
                            payList.add(statistics);
                        }else{
                            remark = "从" + criteria.get("startDate")  + "到" + criteria.get("endDate") + "，共" + notWorkLogsCount + "天没有交日志，具体时间分别是" + notWorkLogs.replace(",", "、") + "。";
                            statistics.put("employeeCode", employeeCode);
                            statistics.put("employee", employee);
                            statistics.put("remark", remark);
//                        statistics.put("notLogCount", notWorkLogs.split(",").length);
                            statistics.put("notLogCount", notWorkLogsCount);
                            statistics.put("workDate", workDate);
                            unpaidList.add(statistics);
                        }
                    } else {
                        remark = "从" + criteria.get("startDate")  + "到" + criteria.get("endDate") + "，共" + allCount + "天都交了日志。";
                        statistics.put("employeeCode", employeeCode);
                        statistics.put("employee", employee);
                        statistics.put("remark", remark);
                        statistics.put("notLogCount", 0);
                        statistics.put("workDate", workDate);
                        payList.add(statistics);
                    }
                } else {
                    remark = "从" + criteria.get("startDate")  + "到" + criteria.get("endDate") + "，共" + allCount + "天都交了日志。";
                    statistics.put("employeeCode", employeeCode);
                    statistics.put("employee", employee);
                    statistics.put("remark", remark);
                    statistics.put("notLogCount", 0);
                    statistics.put("workDate", workDate);
                    payList.add(statistics);
                }
            }
        }
        list.addAll(unpaidList);
        list.addAll(payList);
        if (Common.judgeString(payCondition)) {
            if ("0".equals(payCondition)) {
                return list;
            }
            if ("1".equals(payCondition)) {
                return payList;
            }
            if ("2".equals(payCondition)) {
                return unpaidList;
            }
        }
        return list;
    }

    private List<Map> queryNotAllLog(QueryCriteria criteria) {
        setStartEndDate(criteria);
        List<String> workDateList = queryWorkDay(criteria);
        String workDate = listToString(workDateList);
        int allCount = workDateList.size();
        criteria.put("notLogCount", allCount);
        criteria.put("isStaff", "true");
        criteria.put("remark", "");
        List<Map> list = lteProjectMapper.queryEmptyWorkLog(criteria);

        List<Map> notAllWorkLogList = lteProjectMapper.queryNotAllWorkLog(criteria);
        String employeeCode = "";
        String employee = "";
        Map statistics = null;
        String logTimes = "";
        String notWorkLogs = "";
        for (Map notAllWorkLog : notAllWorkLogList) {
            employeeCode = String.valueOf(notAllWorkLog.get("employeeCode"));
            employee = String.valueOf(notAllWorkLog.get("employee"));
            if (Common.judgeString(employeeCode)) {
                logTimes = String.valueOf(notAllWorkLog.get("logTimes"));
                if (!workDate.equals(logTimes)) {
                    notWorkLogs = queryNotWorkLog(workDateList, stringToList(logTimes));
                    if (Common.judgeString(notWorkLogs)) {
                        statistics = new HashMap();
                        statistics.put("employeeCode", employeeCode);
                        statistics.put("employee", employee);
                        statistics.put("notLogCount", notWorkLogs.split(",").length);
                        list.add(statistics);
                    }
                }
            }
        }
        return list;
    }

    private int countNotWorkLogs(String datelist){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int j = 0;
        String workDate = "";
        try {
            for (int i = 0; i < datelist.split(",").length; i++) {
                workDate = datelist.split(",")[i];
                Calendar cal = Calendar.getInstance();
                cal.setTime(df.parse(workDate));
                if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                    j++;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return (datelist.split(",").length-j);
    }

    private String queryNotWorkLog(List<String> workDateList, List<String> logTimeList) {
        String notWorkLog = "";
        if (workDateList != null && !workDateList.isEmpty()) {
            for (String workDate : workDateList) {
                 if (!logTimeList.contains(workDate)) {
                     notWorkLog += "," + workDate;
                 }
            }
        }
        return Common.judgeString(notWorkLog) ? notWorkLog.substring(1) : "";
    }

    private void setStartEndDate(QueryCriteria criteria) {
        String startDate = String.valueOf(criteria.get("startDate"));
        String endDate = String.valueOf(criteria.get("endDate"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<String>();
        try {
            if (Common.judgeString(startDate) && !Common.judgeString(endDate)) {
                endDate = sdf.format(new Date());
            }
            if (!Common.judgeString(startDate) && Common.judgeString(endDate)) {
                Date endDat = sdf.parse(endDate);
                Calendar c = Calendar.getInstance();
                c.setTime(endDat);
                startDate = c.get(Calendar.YEAR) + "-" + String.format("%02d", (c.get(Calendar.MONTH) + 1)) + "-01";
            }
            if (!Common.judgeString(startDate) && !Common.judgeString(endDate)) {
                endDate = sdf.format(new Date());
                Date endDat = sdf.parse(endDate);
                Calendar c = Calendar.getInstance();
                c.setTime(endDat);
                startDate = c.get(Calendar.YEAR) + "-" + String.format("%02d", (c.get(Calendar.MONTH) + 1)) + "-01";
            }
            criteria.put("startDate", startDate);
            criteria.put("endDate", endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPreStartEndDate(QueryCriteria criteria) {
        String startDate = String.valueOf(criteria.get("startDate"));
        String endDate = String.valueOf(criteria.get("endDate"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (Common.judgeString(startDate) && !Common.judgeString(endDate)) {
                endDate = sdf.format(new Date());
            }
            if (!Common.judgeString(startDate) && Common.judgeString(endDate)) {
                Date endDat = sdf.parse(endDate);
                Calendar c = Calendar.getInstance();
                c.setTime(endDat);
                c.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
                startDate = c.get(Calendar.YEAR) + "-" + String.format("%02d", (c.get(Calendar.MONTH) + 1)) + "-" + String.format("%02d", (c.get(Calendar.DATE)));
            }
            if (!Common.judgeString(startDate) && !Common.judgeString(endDate)) {
                endDate = sdf.format(new Date());
                Date endDat = sdf.parse(endDate);
                Calendar c = Calendar.getInstance();
                c.setTime(endDat);
                c.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
                startDate = c.get(Calendar.YEAR) + "-" + String.format("%02d", (c.get(Calendar.MONTH) + 1)) + "-" + String.format("%02d", (c.get(Calendar.DATE)));
                endDate = startDate;
            }
            criteria.put("startDate", startDate);
            criteria.put("endDate", endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> queryWorkDay(QueryCriteria criteria) {
        List<String> dates = new ArrayList<String>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = String.valueOf(criteria.get("startDate"));
        String endDate = String.valueOf(criteria.get("endDate"));
        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(df.parse(startDate));
            Calendar c2 = Calendar.getInstance();
            c2.setTime(df.parse(endDate));
            int dayOfWeek = 0;
            String year = startDate.substring(0, 4);
            String holidays = lteProjectMapper.queryHolidays(year);
            String makeUpClassDays = lteProjectMapper.queryMakeUpClassDays(year);
            String dateStr = "";
            while(c1.compareTo(c2) != 1) {
                dayOfWeek = c1.get(Calendar.DAY_OF_WEEK);
                dateStr = df.format(c1.getTime());
//                判断是否跨年，跨年要重新获取节假日
                if (!dateStr.substring(0, 4).equals(year)) {
                    year = String.valueOf(Integer.parseInt(year) + 1);
                    holidays = lteProjectMapper.queryHolidays(year);
                    makeUpClassDays = lteProjectMapper.queryMakeUpClassDays(year);
                }
//                判断是否节假日
                if (isHoliday(dayOfWeek, dateStr, holidays, makeUpClassDays)) {
                    c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
                    continue;
                } else {
                    dates.add(dateStr);
                    c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    private boolean isHoliday(int dayOfWeek, String dateStr, String holidays, String makeUpClassDays) {
//        if (type.equals("export")){
            if ((Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) && !makeUpClassDays.contains(dateStr)) {
                return true;
            }
//        }
        if (holidays.contains(dateStr)) {
            return true;
        }
        return false;
    }

    private boolean isFinishWork(String dateStr, String LogTimes) {
        if (LogTimes.equals("0")) {
            return false;
        }
        if(LogTimes.contains(",")){
            for (int i = 0; i < LogTimes.split(",").length; i++) {
                if (dateStr.contains(LogTimes.split(",")[i])){
                    return true;
                }
            }
        }else{
            if (dateStr.contains(LogTimes)){
                return true;
            }
        }
        return false;
    }

    @Override
    public InputStream validExcelFile(Map<String, MultipartFile> fileMap) throws IOException {
        String message = "";
        if (fileMap.size() <= 0) {
            message = "导入失败，请先添加导入文件！";
        }
        InputStream inputStream = fileMap.values().iterator().next().getInputStream();
        return inputStream;
    }

    @Override
    public String validExcelTitle(org.apache.poi.ss.usermodel.Sheet sheet,String [] titleArr) {
        String message = "";
        for(int i = 0,length=titleArr.length - 1; i < length; i++) {
            if(!getCellValue(sheet,0,i).toString().contains(titleArr[i])) {
                message = "导入失败，excel表中第" + (i + 1) + "列的标题应为" + titleArr[i];
                break;
            }
        }
        return message;
    }

    @Override
    public String validExcelData(Integer rows) {
        String message = "";
        if (rows <= 1) {
            message = "导入失败，excel表中没有导入数据！";
        }
        return message;
    }

    public Object getCellValue(org.apache.poi.ss.usermodel.Sheet sheet,int row,int column) {
        Object sheetTitle = null;
        if(sheet != null) {
            Row rows = sheet.getRow(row);
            if (rows != null) {
                org.apache.poi.ss.usermodel.Cell cell = rows.getCell(column);
                if (cell != null) {
                    int cellType = cell.getCellType();
                    if (cellType == XSSFCell.CELL_TYPE_STRING) {
                        sheetTitle = cell.getStringCellValue();
                    }
                    if(cellType == XSSFCell.CELL_TYPE_NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            sheetTitle = Common.getDateString(cell.getDateCellValue(), "yyyy-MM-dd");
                        } else {
                            sheetTitle = Common.convertInteger(String.valueOf(cell.getNumericCellValue()));
                        }
                    }
                }
            }
        }
        return sheetTitle;
    }

    @Override
    public Map getExcelData(int row, Sheet sheet, List dataList) {
        Map dataMap = new HashMap();
        String cellContent = "";
        Row rows = sheet.getRow(row - 1);
        int column = 0;
        org.apache.poi.ss.usermodel.Cell cell = null;
        int cellType = 0;
        String columnName = "";
        Map<Integer, String> map = getColumnMap();
        String message = "";
        int size = map.size();
        for (int i = 1; i < size + 1; i++) {
            if (rows != null) {
                columnName = map.get(i);
                if (rows.getPhysicalNumberOfCells() > column) {
                    cell = rows.getCell(column++);
                    cellType = cell.getCellType();
                    if(cellType == XSSFCell.CELL_TYPE_NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellContent = Common.getDateString(cell.getDateCellValue(), "yyyy-MM-dd");
                        } else {
                            cellContent = String.valueOf(cell.getNumericCellValue());
                        }
                    } else if (cellType == XSSFCell.CELL_TYPE_FORMULA) {
                        try {
                            cellContent = String.valueOf(cell.getNumericCellValue());
                        } catch (Exception e) {
                            cellContent = "0";
                        }
                    } else {
                        cellContent = cell.getStringCellValue().trim();
                    }
                    if ("projectCode".equals(columnName) && cellType == XSSFCell.CELL_TYPE_NUMERIC) {
                        cellContent = cellContent.substring(0, cellContent.lastIndexOf("."));
                    }
                    message = validDataFormat(row, dataMap, columnName, cellContent, dataList);
                    if (Common.judgeString(message)) {
                        dataMap.put("message", message);
                        return dataMap;
                    }
                    dataMap.put(columnName, cellContent);
                } else {
                    for (int j = size - rows.getPhysicalNumberOfCells(); j >= 0;  j--) {
                        dataMap.put(map.get(map.size()-(j-1)), "");
                    }
                }
            }
        }
        return dataMap;
    }

    @Override
    public Map getLogExcelData(int row, Sheet sheet, List dataList) {
        Map dataMap = new HashMap();
        String cellContent = "";
        Row rows = sheet.getRow(row - 1);
        int column = 0;
        org.apache.poi.ss.usermodel.Cell cell = null;
        int cellType = 0;
        String columnName = "";
        Map<Integer, String> map = getLogColumnMap();
        String message = "";
        int size = map.size();
        for (int i = 1; i < size + 1; i++) {
            if (rows != null) {
                columnName = map.get(i);
                if (rows.getPhysicalNumberOfCells() > column) {
                    cell = rows.getCell(column++);
                    if (cell != null) {
                        cellType = cell.getCellType();
                        if(cellType == XSSFCell.CELL_TYPE_NUMERIC) {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellContent = Common.getDateString(cell.getDateCellValue(), "yyyy-MM-dd");
                            } else {
                                cellContent = String.valueOf(cell.getNumericCellValue());
                            }
                        } else if (cellType == XSSFCell.CELL_TYPE_FORMULA) {
                            try {
                                cellContent = String.valueOf(cell.getNumericCellValue());
                            } catch (Exception e) {
                                cellContent = "0";
                            }
                        } else {
                            cellContent = cell.getStringCellValue().trim();
                        }
                        if ("projectCode".equals(columnName) && cellType == XSSFCell.CELL_TYPE_NUMERIC) {
                            cellContent = cellContent.substring(0, cellContent.lastIndexOf("."));
                        }
                        if ("proportion".equals(columnName) && cellType == XSSFCell.CELL_TYPE_NUMERIC) {
                            cellContent = Double.parseDouble(cellContent) * 100 + "%";
                        }
                        message = validLogDataFormat(row, dataMap, columnName, cellContent, dataList);
                        if (Common.judgeString(message)) {
                            dataMap.put("message", message);
                            return dataMap;
                        }
                        dataMap.put(columnName, cellContent);
                    }
                } else {
                    for (int j = size - rows.getPhysicalNumberOfCells(); j >= 0;  j--) {
                        dataMap.put(map.get(map.size()-j), "");
                    }
                }
            }
        }
        return dataMap;
    }

    @Override
    public void deleteData(QueryCriteria criteria) {
        lteProjectMapper.deleteData(criteria);
    }

    @Override
    public void saveData(QueryCriteria criteria) {
        lteProjectMapper.saveData(criteria);
    }

    @Override
    public void saveLogData(QueryCriteria criteria) {
        lteProjectMapper.saveLogData(criteria);
    }

    /**
     * 导出数据
     * @param os
     * @param param
     */
    @Override
    public void exportData(OutputStream os, QueryCriteria param) {
        try {
            this.export(os, param);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void exportContractReviewData(OutputStream os, QueryCriteria param) {
        try {
            this.exportContractReview(os, param);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void exportStatisticsData(OutputStream os, QueryCriteria param) {
        try {
            this.exportStatistics(os, param);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void exportFinalStatisticsData(OutputStream os, QueryCriteria param) {
        try {
            this.exportFinalStatistics(os, param);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteProjectInfo(List<Long> ids) {
        for (Long id : ids) {
            lteProjectMapper.deleteProjectInfo(id);
        }
    }

    @Override
    public String deleteLogInfo(QueryCriteria criteria) {
        List<Map> list = lteProjectMapper.validProportion(criteria);
        for (Map map : list) {
            if (Double.parseDouble(map.get("count").toString()) != 100) {
                return "不能删除" + map.get("employee") + "，" + map.get("logTime").toString().substring(0, 10) + "的部分日志信息";
            }
        }
        lteProjectMapper.deleteLogInfo(criteria);
        return "";
    }

    @Override
    public void saveOrUpdateProjectInfo(QueryCriteria criteria) {
        if (Common.judgeString(String.valueOf(criteria.get("id")))) {
            lteProjectMapper.updateProjectInfo(criteria);
        } else {
            lteProjectMapper.saveProjectInfo(criteria);
        }
    }

    @Override
    public void saveOrUpdateLogInfo(QueryCriteria criteria) {
        if (Common.judgeString(String.valueOf(criteria.get("id")))) {
            lteProjectMapper.updateLogInfo(criteria);
        } else {
            lteProjectMapper.saveLogInfo(criteria);
        }
    }

    @Override
    public List<Map> queryCodeAndReporter(String projectName) {
        return lteProjectMapper.queryCodeAndReporter(projectName);
    }

    @Override
    public List<Map> queryNameAndReporter(String projectCode) {
        return lteProjectMapper.queryNameAndReporter(projectCode);
    }

    @Override
    public List<Map> queryOwnLogInfo(QueryCriteria criteria) {
        setStartEndDate(criteria);
        criteria.setRowStart(null);
        criteria.setPageSize(null);
        return lteProjectMapper.queryLogInfo(criteria);
    }

    @Override
    public List<Map> queryEmployeeProject(QueryCriteria criteria) {
        setStartEndDate(criteria);
        List<Map> list = lteProjectMapper.queryEmployeeProject(criteria);
        return list;
    }

    @Override
    public boolean validRepeatLogInfo(String projectCode, String employeeCode, String logTime) {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("projectCode", projectCode);
        criteria.put("employeeCode", employeeCode);
        criteria.put("logTime", logTime);
        return lteProjectMapper.countRepeatLogInfo(criteria) > 0 ? true : false;
    }

    @Override
    public List<String> queryProjectCode() {
        return lteProjectMapper.queryProjectCode();
    }

    @Override
    public Double querySumProportion(QueryCriteria criteria) {
        return lteProjectMapper.querySumProportion(criteria);
    }

    private void export(OutputStream os, QueryCriteria criteria) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("日志信息表");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 10 * 256);
        sheet.setColumnWidth(4, 13 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 50 * 256);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("项目编码");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("项目名称");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("项目经理");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("项目占比");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("日志时间");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("员工工号");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("员工姓名");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("工作性质");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("日志内容");
        cell.setCellStyle(style);

        List<Map> mapList = lteProjectMapper.exportLogList(criteria);
        String proportion = "";
        String logTime = "";
        String employeeCode = "";
        String employee = "";
        String workNature = "";
        for(int i = 0; i < mapList.size(); i++) {
            row = sheet.createRow((int) i + 1);
            // 第四步，创建单元格，并设置值
            Map map = mapList.get(i);
            proportion = map.get("项目占比").toString();
            logTime = map.get("日志时间").toString();
            employeeCode = map.get("员工工号").toString();
            employee = map.get("员工姓名").toString();
            workNature = String.valueOf(map.get("工作性质"));
            row.createCell(0).setCellValue(map.get("项目编码").toString());
            row.createCell(1).setCellValue(map.get("项目名称").toString());
            row.createCell(2).setCellValue(map.get("项目经理").toString());
            row.createCell(3).setCellValue(Common.judgeString(proportion) ? proportion : "");
            row.createCell(4).setCellValue(Common.judgeString(logTime) ? logTime : "");
            row.createCell(5).setCellValue(Common.judgeString(employeeCode) ? employeeCode : "");
            row.createCell(6).setCellValue(Common.judgeString(employee) ? employee : "");
            row.createCell(7).setCellValue(Common.judgeString(workNature) ? workNature : "");
            row.createCell(8).setCellValue(map.get("日志内容").toString());
        }
        wb.write(os);
    }

    private void exportContractReview(OutputStream os, QueryCriteria criteria) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("项目信息表");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 13 * 256);
        sheet.setColumnWidth(4, 13 * 256);
        sheet.setColumnWidth(5, 11 * 256);
        sheet.setColumnWidth(6, 13 * 256);
        sheet.setColumnWidth(7, 13 * 256);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("项目编码");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("项目名称");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("项目经理");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("合同签订金额");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("合同签订时间");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("市场负责人");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("项目开始时间");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("项目结束时间");
        cell.setCellStyle(style);

        List<Map> mapList = lteProjectMapper.exportContractReviewList(criteria);
        String contractAmount = "";
        String contractTime = "";
        String marketLeader = "";
        String startTime = "";
        String endTime = "";
        for(int i = 0; i < mapList.size(); i++) {
            row = sheet.createRow((int) i + 1);
            // 第四步，创建单元格，并设置值
            Map map = mapList.get(i);
            contractAmount = String.valueOf(map.get("合同签订金额"));
            contractTime = String.valueOf(map.get("合同签订时间"));
            marketLeader = String.valueOf(map.get("市场负责人"));
            startTime = String.valueOf(map.get("项目开始时间"));
            endTime = String.valueOf(map.get("项目结束时间"));
            row.createCell(0).setCellValue(String.valueOf(map.get("项目编码")));
            row.createCell(1).setCellValue(String.valueOf(map.get("项目名称")));
            row.createCell(2).setCellValue(String.valueOf(map.get("项目经理")));
            row.createCell(3).setCellValue(Common.judgeString(contractAmount) ? contractAmount : "");
            row.createCell(4).setCellValue(Common.judgeString(contractTime) ? contractTime : "");
            row.createCell(5).setCellValue(Common.judgeString(marketLeader) ? contractTime : "");
            row.createCell(6).setCellValue(Common.judgeString(startTime) ? startTime : "");
            row.createCell(7).setCellValue(Common.judgeString(endTime) ? endTime : "");
        }
        wb.write(os);
    }

    private void exportStatistics(OutputStream os, QueryCriteria criteria) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("日志统计");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 50 * 256);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("员工工号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("员工姓名");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("日志提交详情");
        cell.setCellStyle(style);

        List<Map> mapList = queryStatistics(criteria);
        for(int i = 0; i < mapList.size(); i++) {
            row = sheet.createRow((int) i + 1);
            // 第四步，创建单元格，并设置值
            Map map = mapList.get(i);
            row.createCell(0).setCellValue(String.valueOf(map.get("employeeCode")));
            row.createCell(1).setCellValue(String.valueOf(map.get("employee")));
            row.createCell(2).setCellValue(String.valueOf(map.get("remark")));
        }
        wb.write(os);
    }

    private void exportFinalStatistics(OutputStream os, QueryCriteria criteria) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("工时统计");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 8 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 50 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 13 * 256);
        sheet.setColumnWidth(8, 13 * 256);
        sheet.setColumnWidth(9, 13 * 256);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("员工工号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("员工姓名");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("项目经理");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("评分");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("项目编码");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("项目名称");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("工时（天）");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("真实工时（天）");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("固定工时（天）");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("分摊工时（天）");
        cell.setCellStyle(style);

        String ids = String.valueOf(criteria.get("id"));
        setStartEndDate(criteria);
        List<Map> mapList = lteProjectMapper.queryFinalStatisticsInfo(criteria);
        List<String> workDateList = queryWorkDay(criteria);
        int workDate = workDateList.size();
        List<Map> list = new ArrayList<Map>();
        int j = 0;
        double shareWork;
        int projectCount;
        double nowManHour = 0;
        double tempHour = 1;
        for (Map map : mapList) {
            projectCount = Integer.parseInt(String.valueOf(map.get("projectCount")));
            if (projectCount == 1) {
                shareWork = 22;
            } else {
                nowManHour = Double.parseDouble(String.valueOf(map.get("nowManHour")));
                tempHour = Double.parseDouble(String.valueOf(map.get("tempHour")));
                shareWork = Double.parseDouble(formatDouble(nowManHour / tempHour * 22));
            }

//            shareWork = Double.parseDouble(String.valueOf(map.get("nowManHour"))) / Double.parseDouble(String.valueOf(map.get("tempHour"))) * 22;
            map.put("id", ++j);
            map.put("shareWork", shareWork);
            if (Common.judgeString(ids)) {
                if (ids.contains(", " + j + ",") || ids.contains(", " + j + "]") || ids.contains("[" + j + ",")) {
                    list.add(map);
                }
            }
        }
        if (!Common.judgeString(ids)) {
            list.clear();
            list.addAll(mapList);
        }

        for(int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            // 第四步，创建单元格，并设置值
            Map map = list.get(i);
            row.createCell(0).setCellValue(String.valueOf(map.get("employeeCode")));
            row.createCell(1).setCellValue(String.valueOf(map.get("employee")));
            row.createCell(2).setCellValue(String.valueOf(map.get("reporter")));
            row.createCell(3).setCellValue(String.valueOf(map.get("score")));
            row.createCell(4).setCellValue(String.valueOf(map.get("projectCode")));
            row.createCell(5).setCellValue(String.valueOf(map.get("projectName")));
            row.createCell(6).setCellValue(String.valueOf(map.get("nowManHour")));
            row.createCell(7).setCellValue(workDate);
            row.createCell(8).setCellValue("22");
            row.createCell(9).setCellValue(String.valueOf(map.get("shareWork")));
        }
        wb.write(os);
    }

    private Map<Integer, String> getColumnMap() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "projectCode");
        map.put(2, "projectName");
        map.put(3, "reporter");
        map.put(4, "contractAmount");
        map.put(5, "contractTime");
        map.put(6, "marketLeader");
        map.put(7, "startTime");
        map.put(8, "endTime");

        return map;
    }

    private Map<Integer, String> getLogColumnMap() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "projectCode");
        map.put(2, "projectName");
        map.put(3, "reporter");
        map.put(4, "proportion");
        map.put(5, "logTime");
        map.put(6, "employeeCode");
        map.put(7, "employee");
        map.put(8, "workNature");
        map.put(9, "context");

        return map;
    }

    private Map<String, String> getLogColumnNameMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("projectCode", "项目编码");
        map.put("projectName", "项目名称");
        map.put("reporter", "项目经理");
        map.put("proportion", "项目占比");
        map.put("logTime", "日志时间");
        map.put("employeeCode", "员工工号");
        map.put("employee", "员工姓名");
        map.put("workNature", "工作性质");
        map.put("context", "日志内容");

        return map;
    }

    private String validDataFormat(int row, Map dataMap, String columnName, String cellContent, List<Map> dataList) {
        String reg = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        if ("projectCode".equals(columnName) && !Common.judgeString(cellContent)) {
            return "导入失败，第" + row + "行，项目编码不能为空！";
        }
        if ("projectName".equals(columnName) && !Common.judgeString(cellContent)) {
            return "导入失败，第" + row + "行，项目名称不能为空！";
        }
        if ("reporter".equals(columnName)) {
            if (!Common.judgeString(cellContent)) {
                return "导入失败，第" + row + "行，项目经理不能为空！";
            } else {
                String message = validProjectRepeat(dataList, dataMap);
                if (Common.judgeString(message)) {
                    return message;
                }
            }
        }
        if ("contractAmount".equals(columnName)) {
            if (Common.judgeString(cellContent) && !Common.isNumber(cellContent)) {
                return "导入失败，第" + row + "行，合同签订金额必须为数值！";
            }
        }
        if ("contractTime".equals(columnName)) {
            if (Common.judgeString(cellContent)) {
                cellContent = cellContent.replace("/", "-");
                if (cellContent.contains("-")) {
                    if (Common.judgeString(cellContent) && cellContent.substring(0, cellContent.indexOf("-")).length() <= 2) {
                        cellContent = "20" + cellContent;
                    }
                }
                if (!Common.checkReg(cellContent, reg) || !stringToDate("yyyy-MM-dd", cellContent) || cellContent.length() > 10) {
                    return "导入失败，第" + row + "行，合同签订时间格式不正确！";
                }
            }
        }
        if ("startTime".equals(columnName)) {
            if (Common.judgeString(cellContent)) {
                cellContent = cellContent.replace("/", "-");
                if (cellContent.contains("-")) {
                    if (Common.judgeString(cellContent) && cellContent.substring(0, cellContent.indexOf("-")).length() <= 2) {
                        cellContent = "20" + cellContent;
                    }
                }
                if (!Common.checkReg(cellContent, reg) || !stringToDate("yyyy-MM-dd", cellContent) || cellContent.length() > 10) {
                    return "导入失败，第" + row + "行，项目开始时间格式不正确！";
                }
            }
        }
        if ("endTime".equals(columnName)) {
            if (Common.judgeString(cellContent)) {
                cellContent = cellContent.replace("/", "-");
                if (cellContent.contains("-")) {
                    if (Common.judgeString(cellContent) && cellContent.substring(0, cellContent.indexOf("-")).length() <= 2) {
                        cellContent = "20" + cellContent;
                    }
                }
                if (!Common.checkReg(cellContent, reg) || !stringToDate("yyyy-MM-dd", cellContent) || cellContent.length() > 10) {
                    return "导入失败，第" + row + "行，项目结束时间格式不正确！";
                }
            }
        }
        return "";
    }

    private String validProjectRepeat(List<Map> excelList, Map dataMap) {
        for (int i = 0; i < excelList.size(); i++) {
            Map map = excelList.get(i);
            if (map.get("projectCode").toString().equals(dataMap.get("projectCode").toString())
                    && map.get("projectName").toString().equals(dataMap.get("projectName").toString())) {
                return "导入失败，excel表中的第" + (excelList.size() + 2) + "行与第" + (i + 2) + "行的数据不能重复！";
            }
        }
        return "";
    }

    private String validLogDataFormat(int row, Map dataMap, String columnName, String cellContent, List<Map> dataList) {
        String reg = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        String numberReg = "^\\+?[1-9][0-9]*$";
        String floagReg = "^(-?\\d+)(\\.\\d+)?$";// 数值
        String[] workNature = new String[] { "开发","测试","维护","生产","培训","部署","策划","数据分析","文档撰写","内部交流","外部交流","商务","服务交付","研究","休假","其他" };
        QueryCriteria criteria = new QueryCriteria();
        if (!Common.judgeString(cellContent)) {
            return "导入失败，第" + row + "行，" + getLogColumnNameMap().get(columnName) + "不能为空！";
        }
        if ("logTime".equals(columnName)) {
            cellContent = cellContent.replace("/", "-");
            if (cellContent.contains("-")) {
                if (Common.judgeString(cellContent) && cellContent.substring(0, cellContent.indexOf("-")).length() <= 2) {
                    cellContent = "20" + cellContent;
                }
            }
            if (!Common.checkReg(cellContent, reg) || !stringToDate("yyyy-MM-dd", cellContent) || cellContent.length() > 10) {
                return "导入失败，第" + row + "行，日志时间格式不正确！";
            }
        }
        if ("workNature".equals(columnName) && !Arrays.asList(workNature).contains(cellContent)) {
            return "导入失败，第" + row + "行，工作性质填写不正确！";
        }
        if ("proportion".equals(columnName)) {
            if (!cellContent.endsWith("%")) {
                return "导入失败，第" + row + "行，项目占比应以英文%结尾";
            }
            if (!cellContent.substring(0, cellContent.length() - 1).matches(floagReg)) {
                return "导入失败，第" + row + "行，项目占比格式不正确";
            }
            if (!cellContent.substring(0, cellContent.length() - 1).matches(numberReg)) {
                return "导入失败，第" + row + "行，项目占比应为正整数";
            }
        }
        if ("reporter".equals(columnName)) {
            criteria.put("projectCode", String.valueOf(dataMap.get("projectCode")));
            criteria.put("projectName", String.valueOf(dataMap.get("projectName")));
            criteria.put("reporter", cellContent);
            if (lteProjectMapper.countContractReview(criteria) <= 0) {
                return "导入失败，第" + row + "行，项目编码、项目名称和项目经理不匹配！";
            }
        }
        if ("employee".equals(columnName)) {
            criteria.put("projectCode", String.valueOf(dataMap.get("projectCode")));
            criteria.put("projectName", String.valueOf(dataMap.get("projectName")));
            criteria.put("logTime", String.valueOf(dataMap.get("logTime")));
            criteria.put("employeeCode", String.valueOf(dataMap.get("employeeCode")));
            criteria.put("employee", cellContent);
            if (lteProjectMapper.countLogInfo(criteria) > 0) {
                return "导入失败，第" + row + "行，同一时间已经录入了项目一样的日志！";
            }
            if (lteProjectMapper.countRepeatEmployeeInfo(criteria) <= 0) {
                return "导入失败，第" + row + "行，员工工号和员工姓名不匹配！";
            }
            return validLogRepeat(dataList, dataMap);
        }
        return "";
    }

    private String validLogRepeat(List<Map> excelList, Map dataMap) {
        for (int i = 0; i < excelList.size(); i++) {
            Map map = excelList.get(i);
            if (map.get("projectCode").toString().equals(dataMap.get("projectCode").toString())
                    && map.get("logTime").toString().equals(dataMap.get("logTime").toString())
                    && map.get("employeeCode").toString().equals(dataMap.get("employeeCode").toString())) {
                return "导入失败，excel表中的第" + (excelList.size() + 2) + "行与第" + (i + 2) + "行的数据不能重复！";
            }
        }
        return "";
    }

    private boolean stringToDate(String pattern, String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (Common.StringIsNotNull(dateString)) {
            try {
                sdf.parse(dateString);
            } catch (ParseException e) {
                return false;
            }
        }
        return true;
    }

    private String listToString(List<String> list) {
        String remark = "";
        for (String date : list) {
            remark += "," + date;
        }
        return Common.judgeString(remark) ? remark.substring(1) : "";
    }

    private List<String> stringToList(String str) {
        List<String> list = new ArrayList<String>();
        try {
            for (String date : str.split(",")) {
                list.add(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getNotFillLogDate(List<String> allWorkDates, List<String> filledWorkDates) {
        List<String> dic = new ArrayList<String>(Arrays.asList(new String[allWorkDates.size()]));
        Collections.copy(dic, allWorkDates);
        for (String filledWorkDate : filledWorkDates) {
            dic.remove(filledWorkDate);
        }
        return listToString(dic);
    }

    private boolean checkRepeat(String[] array){
        Set<String> set = new HashSet<String>();
        for(String str : array){
            set.add(str);
        }
        if(set.size() != array.length){
            return true;//有重复
        }else{
            return false;//不重复
        }
    }

//    @Cacheable(value="accessTokenCache")
//    public String getAccessToken(String param, String param2) {
//        return String.valueOf(System.currentTimeMillis() + param + param2);
//    }

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


    public static void main(String[] args) {
        String resp = "{\"UserId\":\"3223\",\"DeviceId\":\"159f4f160108bef03d23fc7c029fdced\",\"errcode\":0,\"errmsg\":\"ok\",\"user_ticket\":\"IQqSdId_A7Ws6z3ObVBx6ShMtC44eFVhgGTeKUCHQsCsZn7KkZdMa8qdxOUvmVFM_gm0_JebqKE6AGsR0ZxXXg\",\"expires_in\":1800}";
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        System.out.println(map );
    }

    @Override
    public String createRingChart(QueryCriteria criteria) {
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();
        setPreStartEndDate(criteria);
        int normalLogCount = 0;
        int lateLogCount = 0;
        int notLogCount = 0;

        List<String> workDateList = queryWorkDay(criteria);
        List<String> weekendList = queryWeekend(criteria, workDateList);
        String workDate = listToString(workDateList);
        List<Map> list = lteProjectMapper.queryEmptyWorkLog(criteria);
        if (list != null && !list.isEmpty()) {
            notLogCount = list.size();
        }
        String logTimes = "";
        String createTimes = "";
        List<Map> notAllWorkLogList = lteProjectMapper.queryNotAllWorkLog(criteria);
        for (Map notAllWorkLog : notAllWorkLogList) {
            if (Common.judgeString(String.valueOf(notAllWorkLog.get("employeeCode")))) {
                logTimes = String.valueOf(notAllWorkLog.get("logTimes"));
                createTimes = String.valueOf(notAllWorkLog.get("createTimes"));
                logTimes = removeWeekend(logTimes, weekendList);
                createTimes = removeWeekend(createTimes, weekendList);
                if (workDate.equals(logTimes) && workDate.equals(createTimes)) {
                    ++normalLogCount;
                } else if (workDate.equals(logTimes) && !workDate.equals(createTimes)) {
                    ++lateLogCount;
                } else {
                    ++notLogCount;
                }
            }
        }

        jsonMembers.add(0, normalLogCount);
        jsonMembers.add(1, lateLogCount);
        jsonMembers.add(2, notLogCount);
        json.put("data", jsonMembers);
        return json.toString();
    }

    @Override
    public List<Map> queryAppraiseEmployee(QueryCriteria criteria) {
        setPreStartEndDate(criteria);
        List<Map> logList = new ArrayList<Map>();
        List<Map> notLogList = lteProjectMapper.queryEmptyWorkLog(criteria);
        List<Map> lateLogList = new ArrayList<Map>();
        List<Map> normalLogList = new ArrayList<Map>();
        String type = String.valueOf(criteria.get("type"));

        List<String> workDateList = queryWorkDay(criteria);
        List<String> weekendList = queryWeekend(criteria, workDateList);
        String workDate = listToString(workDateList);

        String employeeCode = "";
        String employee = "";
        Map statisticsMap = null;
        String logTimes = "";
        String createTimes = "";
        List<Map> notAllWorkLogList = lteProjectMapper.queryNotAllWorkLog(criteria);
        for (Map notAllWorkLog : notAllWorkLogList) {
            employeeCode = String.valueOf(notAllWorkLog.get("employeeCode"));
            employee = String.valueOf(notAllWorkLog.get("employee"));
            if (Common.judgeString(employeeCode)) {
                statisticsMap = new HashMap();
                statisticsMap.put("employeeCode", employeeCode);
                statisticsMap.put("employee", employee);
                logTimes = String.valueOf(notAllWorkLog.get("logTimes"));
                createTimes = String.valueOf(notAllWorkLog.get("createTimes"));
                logTimes = removeWeekend(logTimes, weekendList);
                createTimes = removeWeekend(createTimes, weekendList);
                if (workDate.equals(logTimes) && workDate.equals(createTimes)) {
                    statisticsMap.put("type", "normal");
                    normalLogList.add(statisticsMap);
                } else if (workDate.equals(logTimes) && !workDate.equals(createTimes)) {
                    statisticsMap.put("type", "late");
                    lateLogList.add(statisticsMap);
                } else {
                    notLogList.add(statisticsMap);
                }
            }
        }

        Collections.sort(notLogList, new Comparator<Map>() {
            public int compare(Map a, Map b) {
            return Collator.getInstance(Locale.CHINESE).compare(a.get("employee"), b.get("employee"));
            }
        });
        Collections.sort(lateLogList, new Comparator<Map>() {
            public int compare(Map a, Map b) {
                return Collator.getInstance(Locale.CHINESE).compare(a.get("employee"), b.get("employee"));
            }
        });
        Collections.sort(normalLogList, new Comparator<Map>() {
            public int compare(Map a, Map b) {
                return Collator.getInstance(Locale.CHINESE).compare(a.get("employee"), b.get("employee"));
            }
        });
        if (Common.judgeString(type)) {
            if ("not".equals(type)) {
                return notLogList;
            }
            if ("late".equals(type)) {
                return lateLogList;
            }
            if ("normal".equals(type)) {
                return normalLogList;
            }
        }
        logList.addAll(notLogList);
        logList.addAll(lateLogList);
        logList.addAll(normalLogList);

        return logList;
    }

    @Override
    public List<Map> queryProjectLine(QueryCriteria criteria) {
        return lteProjectMapper.queryProjectLine(criteria);
    }

    @Override
    public List<Map> queryProjectByLine(QueryCriteria criteria) {
        return lteProjectMapper.queryProjectByLine(criteria);
    }

    @Override
    public String queryLineNameByCode(String projectCode) {
        return lteProjectMapper.queryLineNameByCode(projectCode);
    }

    @Override
    public List<Map> queryEmployeeLog(QueryCriteria criteria) {
        setPreStartEndDate(criteria);
        return lteProjectMapper.queryEmployeeLog(criteria);
    }

    private List<String> queryWeekend(QueryCriteria criteria, List<String> workDateList) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = String.valueOf(criteria.get("startDate"));
        String endDate = String.valueOf(criteria.get("endDate"));
        List<String> list = new ArrayList<String>();
        String currentTime = "";
        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(df.parse(startDate));
            Calendar c2 = Calendar.getInstance();
            c2.setTime(df.parse(endDate));
            while(c1.compareTo(c2) != 1) {
                currentTime = df.format(c1.getTime());
                if (!workDateList.contains(currentTime)) {
                    list.add(currentTime);
                }
                c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private String removeWeekend(String logTimes, List<String> weekendList) {
        for (String weekend : weekendList) {
            logTimes = logTimes.replace(weekend, "").replace(",,", ",");
            if (logTimes.startsWith(",")) {
                logTimes = logTimes.substring(1);
            }
            if (logTimes.endsWith(",")) {
                logTimes = logTimes.substring(0, logTimes.length() - 1);
            }
        }
        return logTimes;
    }
}
