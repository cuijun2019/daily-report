package com.etone.project.modules.lte.web;

import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.ILteMod3Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;
import org.w3c.dom.NodeList;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Rock on 2015/8/11.
 */

@Controller
@RequestMapping("/modules/ltemod3")
@Auditmeta(code = "003", name = "专题分析", symbol = "")
public class LteMod3Controller extends GenericController{

    private static final Logger logger = LoggerFactory.getLogger(LteMod3Controller.class);

    @Autowired
    private ILteMod3Manager lteMod3Manager;

    /**
     * 模三干扰小区列表查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findMod3CellList", method = {RequestMethod.POST, RequestMethod.GET})
    public Results<Map> findMod3CellList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("cellID", query.get("cellID"));
        criteria.put("area", query.get("area"));
        criteria.put("columnSort", query.get("columnSort"));
        criteria.put("mod3RateCheck", query.get("mod3RateCheck"));
        criteria.put("mod3SpCountCheck", query.get("mod3SpCountCheck"));
        criteria.put("totalSpCountCheck", query.get("totalSpCountCheck"));
        criteria.put("mod3SpCountOperation", query.get("mod3SpCountOperation"));
        criteria.put("totalSpCountOperation", query.get("totalSpCountOperation"));
        criteria.put("mod3RateOperation", query.get("mod3RateOperation"));
        criteria.put("mod3RateVal", query.get("mod3RateVal"));
        criteria.put("totalSpCountVal", query.get("totalSpCountVal"));
        criteria.put("mod3SpCountVal", query.get("mod3SpCountVal"));
        criteria.put("sortType", query.get("sortType"));
        criteria.put("userID",getUser().getTipAnswer());
        PageResult<Map> page = lteMod3Manager.findMod3CellList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 模三小区占比-全网列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findMod3WholeCellList", method = {RequestMethod.POST, RequestMethod.GET})
    public Results<Map> findMod3WholeCellList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        PageResult<Map> page = lteMod3Manager.findMod3WholeCellList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 模三小区占比-行政区列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findMod3AreaCellList", method = RequestMethod.POST)
    public Results<Map> findMod3AreaCellList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        PageResult<Map> page = lteMod3Manager.findMod3AreaCellList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 模三区间分布-全网列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findMod3WholeList", method = RequestMethod.POST)
    public Results<Map> findMod3WholeList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        PageResult<Map> page = lteMod3Manager.findMod3WholeList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 模三区间分布-行政区列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findMod3AreaList", method = RequestMethod.POST)
    public Results<Map> findMod3AreaList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        PageResult<Map> page = lteMod3Manager.findMod3AreaList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 模三小区占比-网格列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findMod3GridCellList", method = RequestMethod.POST)
    public Results<Map> findMod3GridCellList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        PageResult<Map> page = lteMod3Manager.findMod3GridCellList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 模三区间分布-网格列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findMod3GridList", method = RequestMethod.POST)
    public Results<Map> findMod3GridList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart", query.get("timeStart"));
        criteria.put("timeEnd", query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        PageResult<Map> page = lteMod3Manager.findMod3GridList(criteria);
        return Results.getPage(page, Map.class);
    }

    /**
     * 全网柱状图
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/wholeNetColumn", method = RequestMethod.GET)
    public List<Map> wholeNetColumn(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart",query.get("timeStart"));
        criteria.put("timeEnd",query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        if(query.get("area").toString().equals("86020")){
            String areanum = "3";
            criteria.put("areanum", areanum);
        }
        criteria.put("userID",getUser().getTipAnswer());
        List<Map> list = lteMod3Manager.wholeNetColumn(criteria);
        return list;
    }

    /**
     * 行政区柱状图
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/areaColumn", method = RequestMethod.GET)
    public List<Map> areaColumn(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart",query.get("timeStart"));
        criteria.put("timeEnd",query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        if(query.get("area").toString().equals("86020")){
            String areanum = "3";
            criteria.put("areanum", areanum);
        }
        criteria.put("userID",getUser().getTipAnswer());
        List<Map> list = lteMod3Manager.areaColumn(criteria);
        return list;
    }

    /**
     * 网格柱状图
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/gridColumn", method = RequestMethod.GET)
    public List<Map> gridColumn(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart",query.get("timeStart"));
        criteria.put("timeEnd",query.get("timeEnd"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        List<Map> list = lteMod3Manager.gridColumn(criteria);
        return list;
    }

    /**
     * 天粒度模三干扰趋势分析
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getDayLine", method = RequestMethod.GET)
    public List<Map> getDayLine(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart",query.get("timeStart"));
        criteria.put("timeEnd",query.get("timeEnd"));
        criteria.put("cellID", query.get("cellID"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        List<Map> list = lteMod3Manager.getDayLine(criteria);
        return list;
    }

    /**
     * 小时粒度模三干扰趋势分析
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getHourLine", method = RequestMethod.GET)
    public List<Map> getHourLine(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart",query.get("timeStart"));
        criteria.put("timeEnd",query.get("timeEnd"));
        criteria.put("cellID", query.get("cellID"));
        criteria.put("area", query.get("area"));
        criteria.put("userID",getUser().getTipAnswer());
        List<Map> list = lteMod3Manager.getHourLine(criteria);
        return list;
    }

    /**
     * 模三干扰邻区查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/findNcCellList",method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> findNcCellList(HttpServletRequest request){
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.put("timeStart",query.get("timeStart"));
        criteria.put("timeEnd",query.get("timeEnd"));
        criteria.put("intENbID", query.get("intENbID"));
        criteria.put("cellID", query.get("cellID"));
        criteria.put("userID",getUser().getTipAnswer());
        List<Map> list = lteMod3Manager.findNcCellList(criteria);
        return list;
    }

}
