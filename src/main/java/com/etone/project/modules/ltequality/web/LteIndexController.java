package com.etone.project.modules.ltequality.web;

import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.utils.UTF2GBK;
import com.etone.project.core.web.control.GenericController;
//import com.etone.project.modules.lte.manager.ILteBugManager;
import com.etone.project.modules.ltequality.manager.ILteIndexManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 90463 on 2016-12-15.
 */


@Controller
@RequestMapping("/modules/lteIndex")
@Auditmeta(code = "003", name = "可查询数据说明", symbol = "")
public class LteIndexController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(LteIndexController.class);

    @Autowired
    private ILteIndexManager iLteIndexManager;

    /**
     * 二级联动一级下拉获取
     */
    @ResponseBody
    @RequestMapping(value = "/findFirstModule", method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> findFirstModule(HttpServletRequest request, HttpServletResponse response) {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("userid", getUser().getId());
        return iLteIndexManager.findFirstModule(criteria);
    }
    /**
     * 二级联动二级下拉获取
     */
    @ResponseBody
    @RequestMapping(value = "/findSecondModule", method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> findSecondModule(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("findFirstM",query.get("findFirstM"));
        return iLteIndexManager.findSecondModule(criteria);
    }
    /**
     * 可查询数据SQL
     */
    @ResponseBody
    @RequestMapping(value = "/findSecondTable", method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> findSecondTable(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("choseTime", query.get("choseTime"));
        SimpleDateFormat fat = new SimpleDateFormat("yyyy-MM-dd");
        //页面传过来的当前日期和倒数前6天的时间
        String day7=fat.format(new Date(Long.parseLong((String) query.get("day7"))));
        String day6=fat.format(new Date(Long.parseLong((String) query.get("day6"))));
        String day5=fat.format(new Date(Long.parseLong((String) query.get("day5"))));
        String day4=fat.format(new Date(Long.parseLong((String) query.get("day4"))));
        String day3=fat.format(new Date(Long.parseLong((String) query.get("day3"))));
        String day2=fat.format(new Date(Long.parseLong((String) query.get("day2"))));
        String day1=fat.format(new Date(Long.parseLong((String) query.get("day1"))));
        criteria.put("day7", day7);
        criteria.put("SecondModule", UTF2GBK.urlToUTF8( query.get("SecondModule").toString()));
        String ModuletableName = iLteIndexManager.findTableName(criteria);
        criteria.put("ModuletableName", ModuletableName);
        if(ModuletableName==null||ModuletableName.equals("")){
            return null;
        }else {
            List<Map> page = iLteIndexManager.findSecondTable(criteria);
            List list = iLteIndexManager.findCity(criteria);
       /* if(page!=null&&page.size()>0){*/
            List<Map> quality = new ArrayList<Map>();
            Boolean flag = false;
            for (int i = 0; i < list.size(); i++) { //循环21个地市
                Map resultMap = new HashMap();
                resultMap.put("city", list.get(i));
                boolean flag7 = false;
                boolean flag6 = false;
                boolean flag5 = false;
                boolean flag4 = false;
                boolean flag3 = false;
                boolean flag2 = false;
                boolean flag1 = false;

                for (int j7 = 0; j7 < page.size(); j7++) {  //7天的数据开始循环第一天
                    Map map = page.get(j7);
                    if (map.get("intdateid").toString().equals(day7) && map.get("intcityname").equals(list.get(i))) {
                        resultMap.put("one1", "V");
                        flag7 = true;
                        break;
                    }
                }
                if (!flag7) {
                    resultMap.put("one1", "X");
                }
                for (int j6 = 0; j6 < page.size(); j6++) {
                    Map map = page.get(j6);
                    if (map.get("intdateid").toString().equals(day6) && map.get("intcityname").equals(list.get(i))) {
                        resultMap.put("two2", "V");
                        flag6 = true;
                    }
                }
                if (!flag6) {
                    resultMap.put("two2", "X");
                }
                for (int j5 = 0; j5 < page.size(); j5++) {
                    Map map = page.get(j5);
                    if (map.get("intdateid").toString().equals(day5) && map.get("intcityname").equals(list.get(i))) {
                        resultMap.put("three3", "V");
                        flag5 = true;
                    }
                }
                if (!flag5) {
                    resultMap.put("three3", "X");
                }
                for (int j4 = 0; j4 < page.size(); j4++) {
                    Map map = page.get(j4);
                    if (map.get("intdateid").toString().equals(day4) && map.get("intcityname").equals(list.get(i))) {
                        resultMap.put("four4", "V");
                        flag4 = true;
                    }
                }
                if (!flag4) {
                    resultMap.put("four4", "X");
                }
                for (int j3 = 0; j3 < page.size(); j3++) {
                    Map map = page.get(j3);
                    if (map.get("intdateid").toString().equals(day3) && map.get("intcityname").equals(list.get(i))) {
                        resultMap.put("five5", "V");
                        flag3 = true;
                    }
                }
                if (!flag3) {
                    resultMap.put("five5", "X");
                }
                for (int j2 = 0; j2 < page.size(); j2++) {
                    Map map = page.get(j2);
                    if (map.get("intdateid").toString().equals(day2) && map.get("intcityname").equals(list.get(i))) {
                        resultMap.put("six6", "V");
                        flag2 = true;
                    }
                }
                if (!flag2) {
                    resultMap.put("six6", "X");
                }
                for (int j1 = 0; j1 < page.size(); j1++) {
                    Map map = page.get(j1);
                    if (map.get("intdateid").toString().equals(day1) && map.get("intcityname").equals(list.get(i))) {
                        resultMap.put("seven7", "V");
                        flag1 = true;
                    }
                }
                if (!flag1) {
                    resultMap.put("seven7", "X");
                }
                quality.add(resultMap);
            }
            return quality;
        }
    }

}