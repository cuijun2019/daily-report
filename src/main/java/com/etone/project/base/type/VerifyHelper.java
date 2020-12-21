package com.etone.project.base.type;

import com.etone.project.core.model.QueryCriteria;

import java.util.List;

/**
 * 该类描述
 *
 * @author <a href="mailto:417877417@qq.com">liuyixuan</a>
 *         DateTime: 14-10-9  下午4:54
 */
public class VerifyHelper {

    private static  String[] _tbleStrategyRange = new String[]{ "m", "d", "h" };
    private static  String[] _viewRange = new String[]{ "grid", "chart", "gis", "mds" };
    private static  String[] _areaTypeRange = new String[]{ "mme", "sgsn", "sac", "enb", "cell" };
    private static  String[] _sgwmmeFieldRange =new String[] { "intMMEGI+intMMEC", "vcMmeIP", "vcSgsnSgwIP","vcSgwIP" };
    private static  String[] _tacFieldRange = new String[] { "intTAC", "intLacTac" };
    private static  String[] _enbFieldRange =  new String[] { "intEnbID" };
    private static  String[] _cellFieldRange =new String[] { "intEnbID+intCI" };

    public static VerifyResult VerifyReport(TaskReport report){
        VerifyResult ret = new VerifyResult();
        ret.Code = 0;
        if (report.getTableStrategy() != null && !isHave(_tbleStrategyRange,(report.getTableStrategy().toLowerCase())))
        {
            ret.Code = 1;
            ret.Message = "TableStrategy属性取值有误,可取范围:m,d,h";
            return ret;
        }
        if (report.getView() != null)
        {
            if (!isHave(_viewRange,(report.getView().toLowerCase())))
            {
                ret.Code = 1;
                ret.Message = "View属性取值有误,可取范围:grid,chart,gis,mds";
                return ret;
            }
            else if (report.getView().toLowerCase().equals("chart") && ((report.getXaxis()==null || report.getXaxis().equals("")) || (report.getYaxis()==null|| report.getYaxis().equals(""))))
            {
                ret.Code = 1;
                ret.Message = "View属性为chart时,Xaxis,Yaxis属性必填";
                return ret;
            }
            else if (report.getView().toLowerCase().equals("gis") && (report.getGisKpiField()==null ||report.getGisKpiField().equals("")  || report.getGisCgiField()==null || report.getGisCgiField().equals("")))
            {
                ret.Code = 1;
                ret.Message = "View属性为gis时,GisKpiField,GisCgiField属性必填";
                return ret;
            }
            else if (report.getView().toLowerCase().equals("mds") && (report.getDimFields()==null || report.getDimFields().equals("")  || report.getMeasureFields()==null || report.getMeasureFields().equals("") ))
            {
                ret.Code = 1;
                ret.Message = "View属性为mds时,DimFields,MeasureFields属性必填";
                return ret;
            }
        }
        if (report.getSgwmmeField() != null && ! isHave(_sgwmmeFieldRange,report.getSgwmmeField()))
        {
            ret.Code = 1;
            ret.Message = "SgwmmeField属性取值有误,可取范围:intMMEGI+intMMEC,vcMmeIP,vcSgsnSgwIP,vcSgwIP";
            return ret;
        }
        if (report.getTacField() != null && !isHave(_tacFieldRange,report.getTacField()))
        {
            ret.Code = 1;
            ret.Message = "TacField属性取值有误,可取范围:intTAC,intLacTac";
            return ret;
        }
        if (report.getEnbField() != null && !isHave(_enbFieldRange,report.getEnbField()))
        {
            ret.Code = 1;
            ret.Message = "EnbField属性取值有误,可取范围:intEnbID";
            return ret;
        }
        if (report.getCellField() != null && !isHave(_cellFieldRange,report.getCellField()))
        {
            ret.Code = 1;
            ret.Message = "CellField属性取值有误,可取范围:intEnbID+intCI";
            return ret;
        }
        return ret;
    }


    public static VerifyResult VerifyAnalysis(List <TaskReport> list)
    {
        VerifyResult ret = new VerifyResult();
        ret.Code = 0;
        if (list != null)
        {

            for(TaskReport report : list){
                ret = VerifyReport(report);
                if (ret.Code == 1)
                {
                    return ret;
                }
            }

        }
        return ret;
    }

    public static boolean isHave(String[] strs,String s){
/*此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
* */
        for(int i=0;i<strs.length;i++){
            if(strs[i].indexOf(s)!=-1){//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
                return true;//查找到了就返回真，不在继续查询
            }
        }
        return false;//没找到返回false
    }




}




