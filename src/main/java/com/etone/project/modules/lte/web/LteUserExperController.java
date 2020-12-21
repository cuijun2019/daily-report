package com.etone.project.modules.lte.web;

import com.etone.commons.json.JsonUtil;
import com.etone.project.base.support.export.impromptuExport.ExportService;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.utils.UTF2GBK;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.ILteUserExperManager;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户无线透视分析
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2014-10-10 上午9:57:59
 */
@Controller
@RequestMapping("/modules/lteuserexper")
@Auditmeta(code = "003", name = "用户无线体验分析", symbol = "")
public class LteUserExperController extends GenericController{
	
	private static final Logger logger = LoggerFactory.getLogger(LteUserExperController.class);
	private static final String VIEW_PREFIX = "modules/ltenoa/";
    
    @Autowired
    private ILteUserExperManager lteUserExperManager;

    /**
     * 指标树查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findKpiTree", method = RequestMethod.POST)
    public List<Map> findKpiTree(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        String intKpiClassify = query.get("intKpiClassify").toString();
        criteria.put("intKpiClassify",intKpiClassify);
        criteria.put("master_kpi",query.get("master_kpi"));
        List<Map> listKpi = lteUserExperManager.findKpiTree(criteria);
        return listKpi;
    }

    @ResponseBody
    @RequestMapping(value="/findSlaveKpiTree", method = RequestMethod.POST)
    public List<Map> findSlaveKpiTree(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        String intKpiClassify = query.get("intKpiClassify").toString();
        criteria.put("intKpiClassify",intKpiClassify);
        criteria.put("master_kpi",query.get("master_kpi"));
        List<Map> listKpi = lteUserExperManager.findSlaveKpiTree(criteria);
        return listKpi;
    }

    /*********************************************LTE软采指标查询********************************************/

    /**
     * 软采指标树查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findKpiTree_ltekpi", method = RequestMethod.POST)
    public Object findLteKpiTree(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        String kpiType = query.get("kpiType").toString();
        criteria.put("intKpiClassify",query.get("intKpiClassify"));
        //String intKpiClassify = (String)query.get("intKpiClassify");
        //criteria.put("intKpiClassify",intKpiClassify);
        List<Map> listKpi = lteUserExperManager.findKpiTree(criteria);
        Map treeMap = new HashMap()     ;
        treeMap.put("id",0);
        treeMap.put("text",kpiType);
        treeMap.put("state","open");
        treeMap.put("checked",false);
        treeMap.put("intSqlType",-1);
        treeMap.put("children",listKpi);
        List<Map> treeNodes = Lists.newArrayList();
        treeNodes.add(treeMap);
        return treeNodes;
    }


    /**
     * 查询颜色区间
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findKpiColor", method = RequestMethod.POST)
    public List<Map> findKpiColor(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("master_kpi", query.get("master_kpi"));
        List<Map> listColor = lteUserExperManager.findKpiColor(criteria);
        return listColor;
    }

    /**
     * 查询用户数饼图
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findUserPieChart", method = RequestMethod.POST)
    public Object findUserPieChart(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("master_kpi", query.get("master_kpi"));
        criteria.put("grid", query.get("grid"));
        criteria.put("area", query.get("area"));
        List chartList = lteUserExperManager.findUserPieChart(criteria);
        return chartList;
    }

    /**
     * 行政区指标查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findAreaKpiList",  method = {RequestMethod.POST, RequestMethod.GET})
    public Results<Map> findAreaKpiList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("intKpiClassify",query.get("intKpiClassify"));
        criteria.put("selectType", query.get("selectType"));
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("master_kpi", query.get("master_kpi"));
        criteria.put("slave_kpi", query.get("slave_kpi"));
        criteria.put("area", query.get("area"));
        initParameters(criteria, query);
        PageResult<Map> page = lteUserExperManager.findAreaKpiList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 网格指标查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findGridKpiList",  method = {RequestMethod.POST, RequestMethod.GET})
    public Results<Map> findGridKpiList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("intKpiClassify",query.get("intKpiClassify"));
        criteria.put("selectType", query.get("selectType"));
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("master_kpi", query.get("master_kpi"));
        criteria.put("slave_kpi", query.get("slave_kpi"));
        criteria.put("area", query.get("area"));
        initParameters(criteria, query);
        PageResult<Map> page = lteUserExperManager.findGridKpiList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 小区指标查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findCellKpiList",  method = {RequestMethod.POST, RequestMethod.GET})
    public Results<Map> findCellKpiList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("intKpiClassify",query.get("intKpiClassify"));
        criteria.put("selectType", query.get("selectType"));
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("master_kpi", query.get("master_kpi"));
        criteria.put("slave_kpi", query.get("slave_kpi"));
        criteria.put("area", query.get("area"));
        initParameters(criteria, query);
        String selectType = (String)query.get("selectType");
        PageResult<Map> page = null;
        if("4".equals(selectType)){
            criteria.setPageSize(10000);
            page =lteUserExperManager.findCellKpiList(criteria);
        }else{
            page =lteUserExperManager.findCellKpiList(criteria);
        }

        return Results.getPage(page, Map.class);
    }

}
