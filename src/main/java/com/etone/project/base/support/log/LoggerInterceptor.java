package com.etone.project.base.support.log;

import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.manager.impl.LteLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: YuYuYu
 * Date: 16-8-11
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {
    //记录需要被记录日志的方法名
    private  static List<String> intceptList=new ArrayList<String>();

    private LteLogManager logManager;

    public void setLogManager(LteLogManager logManager) {
        this.logManager = logManager;
    }

    static{
        intceptList.add("/modules/ltegt/findAllNetKpiList.do");//高铁总体评估，当参数type=1时表示查询的是总体评估
        intceptList.add("/modules/ltegt/getGtErrorGridList.do");//高铁覆盖
        intceptList.add("/modules/ltegt/getGtGridsGradingList.do");//高铁干扰
        intceptList.add("/modules/ltegt/getGtCQIGradingList.do");//高铁CQI分析
        intceptList.add("/modules/lteIntegratedKpi/getIndicatrixData_day.do");//一体化指标考核
        intceptList.add("/modules/lteweakcover/getMrWeakCoverList.do");//MR弱覆盖
        intceptList.add("/modules/lteweakcover/getWCListByIds.do");//MR弱覆盖
        intceptList.add("/modules/lterd/getFilterData.do");//道路分析
        intceptList.add("/modules/lteGpsFloorGrid/findFlTager.do");//楼宇评估与分析
        intceptList.add("/modules/pblroad/queryPlbRoadList.do");//问题点聚类
        intceptList.add("/modules/pblroad/queryOptimizeList.do");//根因方案输出
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String methodName=request.getServletPath();
        if(intceptList.contains(methodName)){
            QueryCriteria criteria=new QueryCriteria();
            criteria.put("vcUrl",methodName);
            if("/modules/ltegt/findAllNetKpiList.do".equals(methodName)){
                if("1".equals(request.getParameter("type"))){
                    criteria.put("vcModuleCode","");
                    criteria.put("vcModule","高铁质量测试");
                    criteria.put("vcContent","高铁总体评估");
                    criteria.put("vcClassName","LteGtControler_findAllNetKpiList");
                    logManager.insertLog(criteria,request);
                }
            }else if("/modules/ltegt/getGtErrorGridList.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","高铁质量测试");
                criteria.put("vcContent","高铁覆盖分析");
                criteria.put("vcClassName","LteGtControler_getGtErrorGridList");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/ltegt/getGtGridsGradingList.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","高铁质量测试");
                criteria.put("vcContent","高铁干扰分析");
                criteria.put("vcClassName","LteGtControler_getGtGridsGradingList");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/ltegt/getGtCQIGradingList.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","高铁质量测试");
                criteria.put("vcContent","高铁CQI分析");
                criteria.put("vcClassName","LteGtControler_getGtCQIGradingList");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/lteIntegratedKpi/getIndicatrixData_day.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","一体化无线考核");
                criteria.put("vcContent","一体化指标考核");
                criteria.put("vcClassName","LteIntegrateKpiControl_getIndicatrixDate");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/lteweakcover/getMrWeakCoverList.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","一体化无线考核");
                criteria.put("vcContent","MR弱覆盖分析");
                criteria.put("vcClassName","LteWeakCoverController_getMrWeakCoverList");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/lteweakcover/getWCListByIds.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","一体化无线考核");
                criteria.put("vcContent","MR弱覆盖分析");
                criteria.put("vcClassName","LteWeakCoverController_getWCListByIds");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/lterd/getFilterData.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","路面质量测试");
                criteria.put("vcContent","道路评估与分析");
                criteria.put("vcClassName","LteGpsRoadGridController_getFilterData");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/lteGpsFloorGrid/findFlTager.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","室内质量测试");
                criteria.put("vcContent","楼宇评估与分析");
                criteria.put("vcClassName","LteGpsFloorGridController_findFlTager");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/pblroad/queryPlbRoadList.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","问题点聚类");
                criteria.put("vcContent","问题点聚类");
                criteria.put("vcClassName","LtePlbController_queryPlbRoadList");
                this.logManager.insertLog(criteria,request);
            }else if("/modules/pblroad/queryOptimizeList.do".equals(methodName)){
                criteria.put("vcModuleCode","");
                criteria.put("vcModule","问题点聚类");
                criteria.put("vcContent","根因方案输出");
                criteria.put("vcClassName","LtePlbController_queryOpti");
                this.logManager.insertLog(criteria,request);
            }
        }
    }
}
