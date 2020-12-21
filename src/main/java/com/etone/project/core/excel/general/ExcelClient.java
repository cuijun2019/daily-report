package com.etone.project.core.excel.general;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Excel导出业务逻辑类
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2014-01-15
 */

public class ExcelClient {

    /**
     * Excel导出
     * @param titleList sheet标题
     * @param dataSetList 数据集合
     * @return

    public HSSFWorkbook exportExcel(List<String> titleList,
                                    List<List<Map>> dataSetList,String type){
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 引用导出工具类
        ExportExcel exportExcel = new ExportExcel();
        ExportHeaderTemp headerTemp = new ExportHeaderTemp();
        for(int k=0;k<titleList.size();k++){
            // 拿到所有列名
            List<Map> dataset = dataSetList.get(k);
            if(dataset != null && dataset.size() > 0){
                // 初始化列名
                int headerSize = 0,index = 0;
                for (int i = 0;i < dataset.size();i++)  {
                    if (dataset.get(i).keySet().size() > headerSize )  {
                        headerSize = dataset.get(i).keySet().size();
                        index = i;
                    }
                }
                Set<String> kset = dataset.get(index).keySet();
                String[][] headers = new String[1][kset.size()];
                String[] headerKey = new String[kset.size()];
                int x=0;
                for(String ks : kset){
                    if (type.equals("rfgExport")) {
                        headers[0][x] = (String) headerTemp.rfgHeader().get(0).get(ks);
                    }else if (type.equals("ltemodeExport")) {
                        headers[0][x] = (String) headerTemp.mode3Header().get(0).get(ks);
                    }else if (type.equals("overCoverExport")) {
                        headers[0][x] = (String) headerTemp.overCoverHeader().get(0).get(ks);
                    }else if (type.equals("allKpiExport")) {
                        headers[0][x] = (String) headerTemp.allKpiHeader().get(0).get(ks);
                    }else if (type.equals("experienceExport")) {
                        headers[0][x] = (String) headerTemp.allKpiHeader().get(0).get(ks);
                    }else if (type.equals("ncoCoverExport")) {
                        headers[0][x] = (String) headerTemp.ncoCoverHeader().get(0).get(ks);
                    }else if (type.equals("ncoPtimizeExport")) {
                        headers[0][x] = (String) headerTemp.ncoPtimizeHeader().get(0).get(ks);
                    }else if (type.equals("rsrqStyleExport")) {
                        headers[0][x] = (String) headerTemp.rsrqStyleboxHeader().get(0).get(ks);
                    }else if (type.equals("taStyleExport")) {
                        headers[0][x] = (String) headerTemp.taStyleboxHeader().get(0).get(ks);
                    }else if (type.equals("userKpiExport")) {
                        headers[0][x] = (String) headerTemp.userKpiHeader().get(0).get(ks);
                    } else if (type.equals("upgrExport")) {
                        headers[0][x] = (String) headerTemp.upgrHeader().get(0).get(ks);
                    } else if (type.equals("downgrExport")) {
                        headers[0][x] = (String) headerTemp.downgrHeader().get(0).get(ks);
                    } else if (type.equals("accessExport")) {
                        headers[0][x] = (String) headerTemp.accessHeader().get(0).get(ks);
                    } else if (type.equals("switchExport")) {
                        headers[0][x] = (String) headerTemp.switchHeader().get(0).get(ks);
                    } else if (type.equals("offlineExport")) {
                        headers[0][x] = (String) headerTemp.offlineHeader().get(0).get(ks);
                    } else if (type.equals("mrFazhiExport")) {
                        headers[0][x] = (String) headerTemp.mrlteFazhiHeader().get(0).get(ks);
                    } else if (type.equals("customTargetChartListForXls")) {
                        headers[0][x] = (String) headerTemp.customTargetChartListForXls().get(0).get(ks);
                    } else if (type.equals("pickUpBaddlyDataForXls")) {
                        headers[0][x] = (String) headerTemp.pickUpBaddlyDataForXls().get(0).get(ks);
                    } else if (type.equals("vipIndexExport")) {
                        headers[0][x] = (String) headerTemp.vipIndexHeader().get(0).get(ks);
                    } else if (type.equals("vipAllIndexExport")) {
                        headers[0][x] = (String) headerTemp.vipAllIndexHeader().get(0).get(ks);
                    } else if (type.equals("getDataForCellAndGridDataXls")) {
                        headers[0][x] = (String) headerTemp.getDataForCellAndGridDataXls().get(0).get(ks);
                    } else if (type.equals("findCellDetiForXls")) {
                        headers[0][x] = (String) headerTemp.findCellDetiForXls().get(0).get(ks);
                    } else if (type.equals("getGtSINRByGradingListForXls")) {
                        headers[0][x] = (String) headerTemp.getGtSINRByGradingListForXls().get(0).get(ks);
                    } else if (type.equals("getGtSinrByGradingDetailForXls")) {
                        headers[0][x] = (String) headerTemp.getGtSinrByGradingDetailForXls().get(0).get(ks);
                    } else if (type.equals("getGtErrorGridListForXls")) {
                        headers[0][x] = (String) headerTemp.getGtErrorGridListForXls().get(0).get(ks);
                    } else if (type.equals("getGtGridsGradingListForXls")) {
                        headers[0][x] = (String) headerTemp.getGtError2GridListForXls().get(0).get(ks);
                    }  else if (type.equals("getGtHoErrorGridListForXls")) {
                        headers[0][x] = (String) headerTemp.getGtHoErrorGridListForXls().get(0).get(ks);
                    }  else if (type.equals("findAllNetKpiListForXls")) {
                        headers[0][x] = (String) headerTemp.findAllNetKpiListForXls().get(0).get(ks);
                    }  else if(type.equals("findAllGtNetKpiListForXls")){
                        headers[0][x] = (String) headerTemp.findAllGtNetKpiListForXls().get(0).get(ks);
                    }   else if(type.equals("getGtError2GridListForXls")){
                        headers[0][x] = (String)  headerTemp.getGtError2GridListForXls().get(0).get(ks);
                    }   else if(type.equals("getGtError3GridListForXls")){
                        headers[0][x] = (String)  headerTemp.getGtError3GridListForXls().get(0).get(ks);
                    }   else if(type.equals("getGtSinrErrorGridListForXls")){
                        headers[0][x] = (String)  headerTemp.getGtSinrErrorGridListForXls().get(0).get(ks);
                    }

                    else if(type.equals("getDtError3GridListForXls")){
                        headers[0][x] = (String)  headerTemp.getDtError3GridListForXls().get(0).get(ks);
                    }   else if(type.equals("taWhStyleExport")){
                        headers[0][x] = (String)  headerTemp.gettaWhStyleForXls().get(0).get(ks);
                    }   else if(type.equals("cellCheckUpExport")){
                        headers[0][x] = (String)  headerTemp.getcellCheckUpForXls().get(0).get(ks);
                    }  else if(type.equals("getHoErrorGridListForXls")){
                        headers[0][x] = (String)  headerTemp.getHoErrorGridListForXls().get(0).get(ks);
                    } else if(type.equals("getSwitchErrorGridListForXls")){
                        headers[0][x] = (String)  headerTemp.getSwitchErrorGridListForXls().get(0).get(ks);
                    } else if(type.equals("getGtCapaLteUserForXls")){
                        headers[0][x] = (String)  headerTemp.getGtCapaLteUserForXls().get(0).get(ks);
                    }   else if(type.equals("queryPlbListExcel")){
                        headers[0][x] = (String)  headerTemp.queryPlbListExcel().get(0).get(ks);
                    }  else if(type.equals("leakageListExport")){
                        headers[0][x] = (String)  headerTemp.getleakageListListForXls().get(0).get(ks);
                    }  else if(type.equals("floorQuestionExport")){
                        headers[0][x] = (String)  headerTemp.floorQuestionExport().get(0).get(ks);
                    }else if(type.equals("roadQuestionExport")){  //道路覆盖干扰问题点导出
                        headers[0][x] = (String)  headerTemp.roadQuestionExport().get(0).get(ks);
                    }else if(type.equals("coverMod3AnalyseExport")){  //道路覆盖干扰问题点导出-修改版
                        headers[0][x] = (String)  headerTemp.coverMod3AnalyseExport().get(0).get(ks);
                    }else if(type.equals("wgridAnalyseExport")){  //网格分析列表导出
                        headers[0][x] = (String)  headerTemp.wgridAnalyseExport().get(0).get(ks);
                    }else if(type.equals("swithAnalyseExport")){  //问题点切换列表导出
                        headers[0][x] = (String)  headerTemp.swithAnalyseExport().get(0).get(ks);
                    }else if(type.equals("getWeakCoverListForXls")){
                        headers[0][x] = (String)  headerTemp.getWeakCoverListForXls().get(0).get(ks);
                    }else if(type.equals("getLonLatListForXls")){
                        headers[0][x] = (String)  headerTemp.getLonLatListForXls().get(0).get(ks);
                    } else if(type.equals("floorJkExport")){
                        headers[0][x] = (String)  headerTemp.floorJkExport().get(0).get(ks);
                    }  else if(type.equals("floorUserJkExport")){
                        headers[0][x] = (String)  headerTemp.floorUserJkExport().get(0).get(ks);
                    }  else if(type.equals("cellTargerExport")){
                        headers[0][x] = (String)  headerTemp.cellTargerExport().get(0).get(ks);
                    }else if(type.equals("getFindLineListForXls")){
                        headers[0][x] = (String)  headerTemp.getFindLineListForXls().get(0).get(ks);
                    }else if(type.equals("roadTargetExport")){                      // 高速功能 整体指标导出
                        headers[0][x] = (String)  headerTemp.getMotorwayRoadTargetExport().get(0).get(ks);
                    }     else if(type.split(",")[0].equals("getQualityListForXls")){
                        String day1=type.split(",")[1];
                        String day2=type.split(",")[2];
                        String day3=type.split(",")[3];
                        String day4=type.split(",")[4];
                        String day5=type.split(",")[5];
                        String day6=type.split(",")[6];
                        String day7=type.split(",")[7];
                        headers[0][x] = (String)  headerTemp.getQualityListForXls(day1,day2,day3,day4,day5,day6,day7).get(0).get(ks);
                    } else if(type.split(",")[0].equals("getgridListForXls")){
                        String day1=type.split(",")[1];
                        String day2=type.split(",")[2];
                        String day3=type.split(",")[3];
                        String day4=type.split(",")[4];
                        String day5=type.split(",")[5];
                        String day6=type.split(",")[6];
                        String day7=type.split(",")[7];
                        headers[0][x] = (String)  headerTemp.getgridListForXls(day1,day2,day3,day4,day5,day6,day7).get(0).get(ks);
                    }else if(type.equals("getOneDayListForXls")){
                        headers[0][x] = (String)  headerTemp.getOneDayListForXls().get(0).get(ks);
                    }
                    else if(type.equals("floorQuestionExport2")){
                        headers[0][x] = (String)  headerTemp.floorQuestionExport2().get(0).get(ks);
                    }
                    else if(type.equals("floorQuestionExport3")){
                        headers[0][x] = (String)  headerTemp.floorQuestionExport3().get(0).get(ks);
                    }
                    else if(type.equals("floorQuestionExport4")){
                        headers[0][x] = (String)  headerTemp.floorQuestionExport4().get(0).get(ks);
                    }
                    else if(type.equals("floorQuestionExport5")){
                        headers[0][x] = (String)  headerTemp.floorQuestionExport5().get(0).get(ks);
                    }
                    else if(type.equals("exportCellTargerExcel2")){
                        headers[0][x] = (String)  headerTemp.exportCellTargerExcel2().get(0).get(ks);
                    }
                    else if(type.equals("gethourListForXls")){
                        headers[0][x] = (String)  headerTemp.gethourListForXls().get(0).get(ks);
                    }
                    else if(type.equals("getVolteProvinceListForXls")){
                        headers[0][x] = (String)  headerTemp.getVolteProvinceListForXls().get(0).get(ks);
                    }
                    else if(type.equals("getGtCqiListForXls")){
                        headers[0][x] = (String)  headerTemp.getGtCqiListForXls().get(0).get(ks);
                    }
                    else if(type.equals("getUserExperForXls")){
                        headers[0][x] = (String)  headerTemp.getUserExperForXls().get(0).get(ks);
                    }
                        headerKey[x] = ks;
                        x++;
                    }
                //导出
                exportExcel.exportExcel(workbook, dataset, null, headers, headerKey, titleList.get(k));
            }
        }
        return workbook;
    }  */

    /**
     * Excel导出到同一个sheet
     * @param titleList sheet标题
     * @param dataSetList 数据集合
     * @return

    public HSSFWorkbook exportExcelSheet(List<String> titleList,
                                         List<List<Map>> dataSetList,String type){
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 引用导出工具类
        ExportExcel exportExcel = new ExportExcel();
        ExportHeaderTemp headerTemp = new ExportHeaderTemp();
        int rowStart = 0;
        for(int k=0;k<titleList.size();k++){
            if(k==0){
                rowStart = 0;
            }else{
                rowStart += 2+dataSetList.get(k-1).size();
            }
            // 拿到所有列名
            List<Map> dataset = dataSetList.get(k);
            if(dataset != null && dataset.size() > 0){
                // 初始化列名
                Set<String> kset = dataset.get(0).keySet();
                String[][] headers = new String[1][kset.size()];
                String[] headerKey = new String[kset.size()];
                int x=0;
                for(String ks : kset){
                    if (type.equals("rfgExport")) {
                        //headers[0][0] = "告警分析";
                        headers[0][x] = (String) headerTemp.rfgHeader().get(0).get(ks);
                    } else if (type.equals("upgrExport")) {
                        headers[0][x] = (String) headerTemp.upgrHeader().get(0).get(ks);
                    } else if (type.equals("downgrExport")) {
                        headers[0][x] = (String) headerTemp.downgrHeader().get(0).get(ks);
                    } else if (type.equals("accessExport")) {
                        headers[0][x] = (String) headerTemp.accessHeader().get(0).get(ks);
                    } else if (type.equals("switchExport")) {
                        headers[0][x] = (String) headerTemp.switchHeader().get(0).get(ks);
                    } else if (type.equals("offlineExport")) {
                        headers[0][x] = (String) headerTemp.offlineHeader().get(0).get(ks);
                    } else if (type.equals("mrFazhiExport")) {
                        headers[0][x] = (String) headerTemp.mrlteFazhiHeader().get(0).get(ks);
                    }
                    headerKey[x] = ks;
                    x++;
                }


                //导出
                exportExcel.exportOneExcel(workbook, dataset, null, headers, headerKey, titleList.get(k),rowStart);
            }
        }
        return workbook;
    }  */
}
