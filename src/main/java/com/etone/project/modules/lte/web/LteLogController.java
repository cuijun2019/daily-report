package com.etone.project.modules.lte.web;

import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.ILteLogManager;
import com.etone.project.modules.lte.manager.impl.LteLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: YuYuYu
 * Date: 16-8-25
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/modules/ltelog")
@Auditmeta(code = "003", name = "专题分析", symbol = "")
public class LteLogController extends GenericController{

    @Autowired
    private ILteLogManager logManager;

    /**
     * 查询各地市登录次数列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getLoadCounts", method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> getLoadCounts( ){
        QueryCriteria criteria=new QueryCriteria();
        Calendar cal=Calendar.getInstance();
        Integer intYear=cal.get(Calendar.YEAR);
        Integer intMonth1=cal.get(Calendar.MONTH);//上月
        Integer intMonth2=intMonth1+1;//本月
        criteria.put("intYear",intYear);
        criteria.put("intMonth1",intMonth1);
        criteria.put("intMonth2",intMonth2);
        List<Map> result=logManager.getLoadCounts(criteria);
        List<Map> resultList=new ArrayList<Map>();
        HashMap resultMap1=new HashMap();
        resultMap1.put("dishi","上月");
        resultMap1.putAll(getSortedMap(intYear,intMonth1,result));
        resultList.add(resultMap1);
        HashMap resultMap2=new HashMap();
        resultMap2.put("dishi","本月");
        resultMap2.putAll(getSortedMap(intYear,intMonth2,result));
         resultList.add(resultMap2);
        return  resultList;
    }

    /**
     * 返回各地市一个月的数据
     * @param  intYear,intMonth,result
     * @return  HashMap
     */
    private HashMap getSortedMap(Integer intYear,Integer intMonth,List<Map> result){
        HashMap resultMap=new HashMap();
        for(int j=0;j<result.size();j++){
            if((intYear+"-"+intMonth).equals(result.get(j).get("date1"))){
                if("86020".equals(result.get(j).get("intcityid").toString())){  //广州
                    resultMap.put("guangzhou",result.get(j).get("sumcount"));
                }else if("860755".equals(result.get(j).get("intcityid").toString())){//深圳
                    resultMap.put("shenzhen",result.get(j).get("sumcount"));
                }else if("860756".equals(result.get(j).get("intcityid").toString())){//珠海
                    resultMap.put("zhuhai",result.get(j).get("sumcount"));
                }else if("860769".equals(result.get(j).get("intcityid").toString())){//东莞
                    resultMap.put("dongguan",result.get(j).get("sumcount"));
                }else if("860757".equals(result.get(j).get("intcityid").toString())){//佛山
                    resultMap.put("foshan",result.get(j).get("sumcount"));
                }else if("860760".equals(result.get(j).get("intcityid").toString())){//中山
                    resultMap.put("zhongshan",result.get(j).get("sumcount"));
                }else if("860752".equals(result.get(j).get("intcityid").toString())){//惠州
                    resultMap.put("huizhou",result.get(j).get("sumcount"));
                }else if("860754".equals(result.get(j).get("intcityid").toString())){//汕头
                    resultMap.put("shantou",result.get(j).get("sumcount"));
                }else if("860750".equals(result.get(j).get("intcityid").toString())){//江门
                    resultMap.put("jiangmen",result.get(j).get("sumcount"));
                }else if("860668".equals(result.get(j).get("intcityid").toString())){//茂名
                    resultMap.put("maoming",result.get(j).get("sumcount"));
                }else if("860758".equals(result.get(j).get("intcityid").toString())){//肇庆
                    resultMap.put("zhaoqing",result.get(j).get("sumcount"));
                }else if("860759".equals(result.get(j).get("intcityid").toString())){//湛江
                    resultMap.put("zhanjiang",result.get(j).get("sumcount"));
                }else if("860753".equals(result.get(j).get("intcityid").toString())){//梅州
                    resultMap.put("meizhou",result.get(j).get("sumcount"));
                }else if("860660".equals(result.get(j).get("intcityid").toString())){//汕尾
                    resultMap.put("shanwei",result.get(j).get("sumcount"));
                }else if("860762".equals(result.get(j).get("intcityid").toString())){//河源
                    resultMap.put("heyuan",result.get(j).get("sumcount"));
                }else if("860763".equals(result.get(j).get("intcityid").toString())){//清远
                    resultMap.put("qingyuan",result.get(j).get("sumcount"));
                }else if("860751".equals(result.get(j).get("intcityid").toString())){//韶关
                    resultMap.put("shaoguan",result.get(j).get("sumcount"));
                }else if("860663".equals(result.get(j).get("intcityid").toString())){//揭阳
                    resultMap.put("jieyang",result.get(j).get("sumcount"));
                }else if("860662".equals(result.get(j).get("intcityid").toString())){//阳江
                    resultMap.put("yangjiang",result.get(j).get("sumcount"));
                }else if("860768".equals(result.get(j).get("intcityid").toString())){//潮州
                    resultMap.put("chaozhou",result.get(j).get("sumcount"));
                }else if("860766".equals(result.get(j).get("intcityid").toString())){//云浮
                    resultMap.put("yunfu",result.get(j).get("sumcount"));
                }else if("20".equals(result.get(j).get("intcityid").toString())){//云浮
                    resultMap.put("qita",result.get(j).get("sumcount"));
                }
            }
        }
        return resultMap;
    }

    /**
     * 查询各地市各模块访问次数列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getModuleCounts", method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> getModuleCounts( ){
        QueryCriteria criteria=new QueryCriteria();
        Calendar cal=Calendar.getInstance();
        Integer intYear=cal.get(Calendar.YEAR);
        Integer intMonth1=cal.get(Calendar.MONTH);//上月
        Integer intMonth2=intMonth1+1;//本月
        criteria.put("intYear",intYear);
        criteria.put("intMonth1",intMonth1);
        criteria.put("intMonth2",intMonth2);
        List<Map> result=logManager.getModuleCounts(criteria);
        List<Map> resultList=new ArrayList<Map>();
        HashMap resultMap1=new HashMap();
        resultMap1.put("dishi","上月");
        resultMap1.putAll(getSortedMap(intYear,intMonth1,result));
        resultList.add(resultMap1);
        HashMap resultMap2=new HashMap();
        resultMap2.put("dishi","本月");
        resultMap2.putAll(getSortedMap(intYear,intMonth2,result));
        resultList.add(resultMap2);
        return  resultList;
    }

    /**
     * 查询各地市各模块上月份访问次数条形图
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/fillTop5Chart_lastMonth", method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> fillTop5Chart_lastMonth( ){
        QueryCriteria criteria=new QueryCriteria();
        Calendar cal=Calendar.getInstance();
        Integer intYear=cal.get(Calendar.YEAR);
        Integer intMonth=cal.get(Calendar.MONTH);
        criteria.put("intYear",intYear);
        criteria.put("intMonth",intMonth);
        List<Map> result=this.logManager.fillTop5Chart_lastMonth(criteria);
        return  result;
    }

    /**
     * 查询各地市各模块本月份访问次数条形图
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/fillTop5Chart_thisMonth", method = {RequestMethod.POST, RequestMethod.GET})
    public List<Map> fillTop5Chart_thisMonth( ){
        QueryCriteria criteria=new QueryCriteria();
        Calendar cal=Calendar.getInstance();
        Integer intYear=cal.get(Calendar.YEAR);
        Integer intMonth=cal.get(Calendar.MONTH)+1;
        criteria.put("intYear",intYear);
        criteria.put("intMonth",intMonth);
        List<Map> result=this.logManager.fillTop5Chart_thisMonth(criteria);
        return  result;
    }

}
