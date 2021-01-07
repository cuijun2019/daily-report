package com.etone.project.modules.lte.web;

import com.etone.project.base.support.BaseController;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.modules.lte.entity.LogInfoDto;
import com.etone.project.modules.lte.manager.IProjectManager;
import com.etone.project.utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/project")
public class LogAPIController extends BaseController {

    @Autowired
    private IProjectManager lteProjectManager;

    @ResponseBody
    @RequestMapping(value = "/saveLog", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void saveLog(@RequestBody List<LogInfoDto> list, HttpServletResponse response) throws IOException {
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");
        PrintWriter writer;
        try {
            if (list != null && !list.isEmpty()) {
                List<Map> resultList = new ArrayList<>();
                Map<String, Object> map;
                String message;
                for (LogInfoDto logInfoDto : list) {
                    map = new HashMap<>();
                    map.put("projectCode", logInfoDto.getProjectCode());
                    map.put("projectName", logInfoDto.getProjectName());
                    map.put("reporter", logInfoDto.getReporter());
                    map.put("proportion", logInfoDto.getProportion());
                    map.put("logTime", logInfoDto.getLogTime());
                    map.put("workNature", logInfoDto.getWorkNature());
                    map.put("context", logInfoDto.getContext());
                    map.put("employee", logInfoDto.getEmployee());
                    map.put("employeeCode", logInfoDto.getEmployeeCode());
                    message = validLog(logInfoDto);
                    if (Common.judgeString(message)) {
                        result = new Result(Result.ERROR, "保存失败", message);
                        return;
                    }
                    resultList.add(map);
                }
                QueryCriteria criteria = new QueryCriteria();
                criteria.put("dataList", resultList);
                for (int i = 1, length = resultList.size() / 100 + 1; i <= length; i++) {
                    criteria.put("dataList", getPagedList(i, 100, resultList));
                    lteProjectManager.saveLogData(criteria);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.ERROR, "保存失败", e.getMessage());
        } finally {
            writer = response.getWriter();
            System.out.println(result.toString());
            writer.println(result.toString());
            writer.flush();
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String validLog(LogInfoDto logInfoDto) {
        String[] workNatures = new String[]{"开发", "测试", "维护", "生产", "培训", "部署", "策划", "数据分析", "文档撰写", "内部交流", "外部交流", "商务", "服务交付", "研究", "休假", "其他"};
        String reg = "^\\+?[1-9][0-9]*$";// 正整数
        String floagReg = "^(-?\\d+)(\\.\\d+)?$";// 数值
        String proportion = logInfoDto.getProportion();
        String workNature = logInfoDto.getWorkNature();

        if (!Common.judgeString(logInfoDto.getProjectCode())) {
            return "项目编码不能为空";
        }
        if (!Common.judgeString(logInfoDto.getProjectName())) {
            return "项目名称不能为空";
        }
        if (!Common.judgeString(logInfoDto.getProportion())) {
            return "项目占比不能为空";
        }
        if (!Common.judgeString(logInfoDto.getEmployee())) {
            return "员工姓名不能为空";
        }
        if (!Common.judgeString(logInfoDto.getEmployeeCode())) {
            return "员工工号不能为空";
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
        if (!Common.judgeString(workNature)) {
            return "工作性质不能为空";
        } else if (!Arrays.asList(workNatures).contains(workNature)) {
            return "工作性质只能为开发、测试、维护、生产、培训、部署、策划、数据分析、文档撰写、内部交流、外部交流、商务、服务交付、研究、休假或其他";
        }
        if (!Common.judgeString(logInfoDto.getContext())) {
            return "工作日志内容不能为空";
        }

        return "";
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
}
