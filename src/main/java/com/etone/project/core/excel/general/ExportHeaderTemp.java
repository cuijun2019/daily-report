package com.etone.project.core.excel.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportHeaderTemp {

    /**LTE软采指标分析*/
    public List<Map> allKpiHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("gridid", "栅格ID");
        map.put("area", "行政区");
        map.put("RSRP均值dBm", "RSRP均值(dBm)");
        map.put("RSRQ均值dB", "RSRQ均值(dB)");
        map.put("上行SINR均值dB", "上行SINR均值dB");
        map.put("小区弱覆盖度", "小区弱覆盖度(%)");
        map.put("MR覆盖率", "MR覆盖度(%)");
        map.put("邻区缺失概率", "邻区缺失概率");
        map.put("边缘弱覆盖度", "边缘弱覆盖度(%)");
        map.put("室外覆盖室内概率", "室外覆盖室内概率");
        map.put("模三干扰强度", "模三干扰强度(%)");
        map.put("邻区过覆盖度", "邻区过覆盖度(%)");
        map.put("上行干扰强度", "上行干扰强度(%)");
        map.put("UE高发射功率强度", "UE高发射功率强度(%)");
        map.put("上行丢包率QCI1", "上行丢包率QCI1(‰)");
        map.put("上行丢包率QCI2", "上行丢包率QCI2(‰)");
        map.put("上行丢包率QCI3", "上行丢包率QCI3(‰)");
        map.put("上行丢包率QCI4", "上行丢包率QCI4(‰)");
        map.put("上行丢包率QCI5", "上行丢包率QCI5(‰)");
        map.put("上行丢包率QCI6", "上行丢包率QCI6(‰)");
        map.put("上行丢包率QCI7", "上行丢包率QCI7(‰)");
        map.put("上行丢包率QCI8", "上行丢包率QCI8(‰)");
        map.put("上行丢包率QCI9", "上行丢包率QCI9(‰)");
        map.put("下行丢包率QCI1", "下行丢包率QCI1(‰)");
        map.put("下行丢包率QCI2", "下行丢包率QCI2(‰)");
        map.put("下行丢包率QCI3", "下行丢包率QCI3(‰)");
        map.put("下行丢包率QCI4", "下行丢包率QCI4(‰)");
        map.put("下行丢包率QCI5", "下行丢包率QCI5(‰)");
        map.put("下行丢包率QCI6", "下行丢包率QCI6(‰)");
        map.put("下行丢包率QCI7", "下行丢包率QCI7(‰)");
        map.put("下行丢包率QCI8", "下行丢包率QCI8(‰)");
        map.put("下行丢包率QCI9", "下行丢包率QCI9(‰)");
        map.put("TA均值", "TA均值");
        map.put("RRC连接建立请求次数", "RRC连接建立请求次数");
        map.put("RRC连接建立成功次数", "RRC连接建立成功次数");
        map.put("RRC连接建立拒绝次数", "RRC连接建立拒绝次数");
        map.put("RRC连接建立超时次数", "RRC连接建立超时次数");
        map.put("RRC连接建立成功率", "RRC连接建立成功率(%)");
        map.put("RRC连接重建立请求次数", "RRC连接重建立请求次数");
        map.put("RRC连接重建立成功次数", "RRC连接重建立成功次数");
        map.put("RRC连接重建立拒绝次数", "RRC连接重建立拒绝次数");
        map.put("RRC连接重建立超时次数", "RRC连接重建立超时次数");
        map.put("RRC连接重建立成功率", "RRC连接重建立成功率(%)");
        map.put("RRC连接重建立比率", "RRC连接重建立比率(%)");
        map.put("RRC连接建立时长", "RRC连接建立时长(ms)");
        map.put("上行业务信息PRB占用率", "上行业务信息PRB占用率");
        map.put("下行业务信息PRB占用率", "下行业务信息PRB占用率");
        map.put("小区用户面下行丢包率", "小区用户面下行丢包率(‰)");
        map.put("小区用户面下行弃包率", "小区用户面下行弃包率(‰)");
        map.put("小区用户面上行丢包率", "小区用户面上行丢包率(‰)");
        map.put("上行64QAM占比", "上行64QAM占比(%)");
        map.put("下行64QAM占比", "下行64QAM占比(%)");
        map.put("上行初始HARQ重传比率", "上行初始HARQ重传比率(%)");
        map.put("下行初始HARQ重传比率", "下行初始HARQ重传比率(%)");
        map.put("RIP均值", "RIP均值(dBm)");
        map.put("PHR均值", "PHR均值(dB)");
        map.put("TM2378分布比例", "TM2378分布比例(%)");
        map.put("下行双流占比", "下行双流占比(%) ");
        map.put("下行双流时长占比", "下行双流时长占比(%)");
        map.put("grid", "网格");
        map.put("cellID", "小区");
        map.put("cellName", "小区名");
        map.put("eNodeBName", "eNodeB名称");
        map.put("eNodeBID", "EnbID");
        map.put("pci", "PCI");
        map.put("pcarrierfr", "频点");
        map.put("powerInfo", "频段");
        map.put("lonb", "经度");
        map.put("latb", "纬度");
        map.put("azimuth", "方位角");
        map.put("X2切换准备请求次数", "X2切换准备请求次数");
        map.put("X2切换准备成功次数", "X2切换准备成功次数");
        map.put("X2切换准备失败次数_等待切换响应消息超时", "X2切换准备失败次数_等待切换响应消息超时");
        map.put("X2切换准备失败次数_目标侧准备失败", "X2切换准备失败次数_目标侧准备失败");
        map.put("X2切换准备失败次数_源侧取消切换", "X2切换准备失败次数_源侧取消切换");
        map.put("X2切换执行成功次数", "X2切换执行成功次数");
        map.put("X2切换执行平均时长", "X2切换执行平均时长(ms)");
        map.put("X2切换成功率", "X2切换成功率(%)");
        map.put("RLF指示次数", "RLF指示次数");
        map.put("X2错误指示次数", "X2错误指示次数");
        map.put("dtTime","时间");
        map.put("vccity","城市");
        map.put("cityid","城市ID");
        map.put("小区数","小区数");


        list.add(map);
        return list;
    }
    /**模三干扰*/
    public List<Map> mode3Header(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intcity","城市");
        map.put("mod30SpCount", "模30干扰总采样点数");
        map.put("mod6SpCount", "模6干扰总采样点数");
        map.put("mod3SpCount", "模3干扰总采样点数");
        map.put("mod3DenominatorSpCount", "模3/6/30干扰统计采样点");
        map.put("totalSpCount", "总采样点数");
        map.put("intENbID", "intENbID");
        map.put("intCellID", "intCellID");
        map.put("cellID", "CellID");
        map.put("mod3Rate", "模3干扰强度(%)");
        map.put("mod6Rate", "模6干扰强度(%)");
        map.put("mod30Rate", "模30干扰强度(%)");
        map.put("eNodeBName", "eNodeB名称");
        map.put("eNodeBID", "EnbID");
        map.put("pci", "PCI");
        map.put("pcarrierfr", "频点");
        map.put("powerInfo", "频段");
        map.put("lonb", "lonb");
        map.put("latb", "latb");
        map.put("azimuth", "azimuth");
        map.put("dtTime", "时间");
        list.add(map);
        return list;
    }

    /**楼宇问题指标列表*/
    public List<Map> floorQuestionExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("int_id", "问题编号");
        map.put("city", "地市");
        map.put("grida", "A类网格");
        map.put("gridb", "B类网格");
        map.put("gridc", "C类网格");
        map.put("timeframe", "详细时间");
        map.put("times", "时间");
        map.put("years", "年份");
        map.put("months", "月份");
        map.put("days", "日份");
        map.put("buildingid","楼宇编号");
        map.put("buildingname", "楼宇名称");
        map.put("buildinglong", "经度");
        map.put("buildinglat", "纬度");
        map.put("vcintenbidendintcellid", "相关楼宇");
        map.put("vcenbidandname", "主小区名称");
        map.put("vccgi", "主小区CGI");
        map.put("fdistance", "主小区位置距离");
        map.put("avgrsrp", "主小区rsrp");
        map.put("sumcount", "总采样点数");
        map.put("wcount", "总弱覆盖采样点数");
        map.put("coverpercent", "总覆盖率");
        map.put("intallcount", "主小区采样点数");
        map.put("intweakcellcount", "主小区弱覆盖采样点数");
        map.put("fcoveragerate", "主小区弱覆盖率");
        map.put("ucperarea", "单位面积用户");
        map.put("distribution", "是否楼宇覆盖物");
        list.add(map);
        return list;
    }

    /**道路覆盖和干扰问题指标列表,分两个sheet*/
    public List<Map> roadQuestionExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("int_id", "问题编号");
        map.put("city", "地市");
        map.put("lon", "经度");
        map.put("lat", "纬度");
        map.put("datetime", "详细时间");
        map.put("years", "年份");
        map.put("months", "月份");
        map.put("days", "日份");
        map.put("times", "小时");
        map.put("vccitya", "A类网格");
        map.put("vccityb", "B类网格");
        map.put("vccityc", "C类网格");
        map.put("rd_name","道路名称");
        map.put("type2", "问题类型");
        map.put("gridid", "道路栅格ID");
        map.put("sumcount", "栅格采样点总数");
        map.put("usercount", "栅格用户数");
        map.put("avgrsrp", "栅格平均RSRP");
        map.put("coveragerate", "栅格覆盖率");
        map.put("wcount", "栅格弱覆盖采样点数");
        map.put("cgi_main", "主覆盖小区CGI");
        map.put("ci_main", "主覆盖小区号");
        map.put("cellname_main", "主覆盖小区名");
        map.put("sumcount_main", "主覆盖小区采样点总数");
        map.put("avgrsrp_main", "主覆盖小区平均RSRP");
        map.put("ci_bad", "问题小区号");
        map.put("cellname_bad", "问题小区名");
        map.put("coveragerate_bad", "问题小区覆盖率");
        map.put("wcount_bad", "问题小区弱覆盖采样点数");
        map.put("avgta_bad", "问题小区平均TA");
        map.put("avgphr_bad", "问题小区平均PHR");
        map.put("edgecoveragerate_bad", "问题小区覆盖空洞比例");
        map.put("multiplecoveragerate_bad", "问题小区重叠覆盖比例");
        //mosan
        map.put("avgmod3", "栅格干扰概率");
        map.put("mod3count", "栅格模三干扰采样点数");
        map.put("avgmod3_bad", "问题小区干扰概率");
        map.put("mod3count_bad", "问题小区模三干扰采样点数");
        map.put("pci_bad", "问题小区PCI");
        map.put("pci_ifterfere", "干扰小区PCI");
        map.put("avgrsrp_ifterfere", "干扰小区平均RSRP");
        map.put("avgrsrp_bad", "问题小区平均RSRP");
        list.add(map);
        return list;
    }
    /**道路覆盖和干扰问题,cqi,sinr指标列表*/
    public List<Map> coverMod3AnalyseExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("int_id", "问题编号");
        map.put("city", "地市");
        map.put("lon", "经度");
        map.put("lat", "纬度");
        map.put("datetime", "详细时间");
        map.put("years", "年份");
        map.put("months", "月份");
        map.put("days", "日份");
        map.put("times", "小时");
        //cqi,sinr
        map.put("gridid", "道路栅格ID");
        map.put("wgid", "网格");
        map.put("vcroadname", "所属道路");
        map.put("usercount", "总的用户数");
        map.put("intmrallcount","总采样点数");
        map.put("avgrsrp", "栅格平均RSRP");
        map.put("avgrsrq", "平均RSRQ");
        map.put("ltefgd", "LTE覆盖率");
        map.put("ltefgkdd", "覆盖空洞比例");
        map.put("ltecffgd", "重复覆盖率");
        map.put("mode3", "模三干扰强度");
        map.put("avgcqi", "平均CQI");
        map.put("avg6cqi", "低于6的CQI比例");
        map.put("avgsinr", "平均上行SINR");
        map.put("ltesinr0", "低于0的上行SINR比例");
        map.put("vcimsi", "IMSI");
        //覆盖,干扰
        map.put("vccitya", "A类网格");
        map.put("vccityb", "B类网格");
        map.put("vccityc", "C类网格");
        map.put("rd_name","道路名称");
        map.put("type2", "问题类型");
        map.put("gridid", "道路栅格ID");
        map.put("sumcount", "栅格采样点总数");
        map.put("usercount", "栅格用户数");
        map.put("avgrsrp", "栅格平均RSRP");
        map.put("coveragerate", "栅格覆盖率");
        map.put("wcount", "栅格弱覆盖采样点数");
        map.put("cgi_main", "主覆盖小区CGI");
        map.put("ci_main", "主覆盖小区号");
        map.put("cellname_main", "主覆盖小区名");
        map.put("sumcount_main", "主覆盖小区采样点总数");
        map.put("avgrsrp_main", "主覆盖小区平均RSRP");
        map.put("ci_bad", "问题小区号");
        map.put("cellname_bad", "问题小区名");
        map.put("coveragerate_bad", "问题小区覆盖率");
        map.put("wcount_bad", "问题小区弱覆盖采样点数");
        map.put("avgta_bad", "问题小区平均TA");
        map.put("avgphr_bad", "问题小区平均PHR");
        map.put("edgecoveragerate_bad", "问题小区覆盖空洞比例");
        map.put("multiplecoveragerate_bad", "问题小区重叠覆盖比例");
        //mosan
        map.put("avgmod3", "栅格干扰概率");
        map.put("mod3count", "栅格模三干扰采样点数");
        map.put("avgmod3_bad", "问题小区干扰概率");
        map.put("mod3count_bad", "问题小区模三干扰采样点数");
        map.put("pci_bad", "问题小区PCI");
        map.put("pci_ifterfere", "干扰小区PCI");
        map.put("avgrsrp_ifterfere", "干扰小区平均RSRP");
        map.put("avgrsrp_bad", "问题小区平均RSRP");
        //
        map.put("flon_lb", "经度");
        map.put("flat_lb", "纬度");
        //top小区
        map.put("mod3count", "模三干扰点数");
        map.put("avgmod3", "栅格干扰概率");
        map.put("cellid", "小区id");
        map.put("countt", "总采样点数");
        map.put("vcenbname", "小区名");
        map.put("chongdiefg", "重叠覆盖");
        map.put("pci", "PCI");
        map.put("pindian", "频点");
        map.put("pinduan", "频段");
        map.put("ruofugai", "弱覆盖比列");
        map.put("avgta", "干扰TA");
        //主邻小区
        map.put("mainid", "主小区ID");
        map.put("ncellid", "邻小区ID");
        map.put("intpci", "主服小区PCI");
        map.put("intnc1pci", "邻区PCI");
        map.put("scrsrptot", "主邻小区对的采样点数");
        map.put("scrsrp", "主小区RSRP");
        map.put("scrsrq", "主小区RSRQ");
        map.put("ncrsrp", "邻小区RSRP");
        map.put("ncrsrq", "邻小区RSRQ");
        map.put("ulsinr", "主上行SINR均值");
        map.put("phr", "主上行PHR均值");
        list.add(map);
        return list;
    }

    /**网格分析指标列表*/
    public List<Map> wgridAnalyseExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("wgid", "网格");
        map.put("vcroadname1", "道路");
        map.put("weidu", "所属道路");
        map.put("grid1", "栅格");
        map.put("lonb", "经度");
        map.put("latb", "纬度");
        map.put("roadtype", "道路类型");
        map.put("intmrallcount", "总采样点数");
        map.put("ltefgd", "LTE覆盖率");
        map.put("ltefgkdd", "覆盖空洞比例");
        map.put("ltecffgd", "重复覆盖率");
        map.put("avgphr", "平均PHR(dB)");
        map.put("lowue", "低UE发射功率余量比例(%)");
        map.put("mode3", "模三干扰强度");
        map.put("avgrsrp", "平均RSRP");
        map.put("avgrsrq", "平均RSRQ");
        map.put("avgcqi", "平均CQI");
        map.put("avg6cqi", "低于6的CQI比例");
        map.put("avgsinr","平均上行SINR");
        map.put("ltesinr0", "低于0的上行SINR比例");
        map.put("rrcsucc", "RRC连接建立成功率(%)");
        map.put("rrcresucc", "RRC连接重建成功率(%)");
        map.put("rrcre", "RRC连接重建比例率(%)");
        map.put("swithfail", "LTE切换失败次数");
        map.put("ltesucc", "LTE切换成功率(%)");
        map.put("enbinsucc", "Enb内切换出成功率(%)");
        map.put("enboutsucc", "Enb间切换出成功率(%)");

        list.add(map);
        return list;
    }

    /**问题栅格切换指标列表*/
    public List<Map> swithAnalyseExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("int_id", "问题编号");
        map.put("city", "地市");
        map.put("long", "经度");
        map.put("lat", "纬度");
        map.put("datetime", "详细时间");
        map.put("years", "年份");
        map.put("months", "月份");
        map.put("days", "日份");
        map.put("times", "小时");
        map.put("wgid", "网格ID");
        map.put("roadname", "所属道路");
        map.put("gridid", "栅格ID");
        map.put("intmrallcount", "总采样点数");
        map.put("usercount", "总的用户数");
        map.put("intrrchointrareq", "切换请求次数");
        map.put("intrrchointrasucc", "切换成功次数");
        map.put("intrrchointrafail", "切换失败次数");
        map.put("intrrchointratimeout", "切换超时次数");
        map.put("intrrchointracellfail", "小区内切换失败次数");
        map.put("intrrchointracelltimeout", "小区内切换超时次数");
        map.put("intrrchointraenbfail", "Enb内切换失败次数");
        map.put("intrrchointraenbtimeout", "Enb内切换超时次数");
        map.put("intrrchointerenbfail", "Enb间切换失败次数");
        map.put("intrrchointerenbtimeout","Enb间切换超时次数");
        map.put("cellid", "小区id");
        map.put("targetcellid", "目标小区id");
        map.put("pci", "小区PCI");
        map.put("vcimsi", "IMSI");
        map.put("mme_ue_s1ap_id", "MME_UE_S1AP_ID");

        list.add(map);
        return list;
    }


    /**楼宇问题加宽指标列表*/
    public List<Map> floorJkExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vccityname", "地市");
        map.put("vcgid", "网格");
        map.put("intflid", "楼宇ID");
        map.put("vcbuildingname", "楼宇名称");
        map.put("flon_lb", "经度");
        map.put("flat_lb", "纬度");
        map.put("intusertot", "家宽总数");
        map.put("intprousertot", "家宽问题数");
        map.put("total_rsrp", "楼宇总的平均RSRP");
        map.put("rsrp", "家宽的平均RSRP");
        list.add(map);
        return list;
    }

    /**楼宇用户问题加宽指标列表*/
    public List<Map> floorUserJkExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vccityname", "地市");
        map.put("vcgid", "网格");
        map.put("intflid", "楼宇ID");
        map.put("vcbuildingname", "楼宇名称");
        map.put("vcmsisdn", "电话");
        map.put("intrsrptot", "采样点数");
        map.put("avgrsrp", "单用户RSRP均值");
        map.put("vcaddress", "家宽地址");

        list.add(map);
        return list;
    }
    /**楼宇用户问题加宽指标列表*/
    public List<Map> cellTargerExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();

        map.put("cellid", "小区编号");
        map.put("countt", "采样点数");
        map.put("rsrp", "平均RSRP");
        map.put("rsrq", "平均RSRQ");
        map.put("avgta", "平均TA");
        map.put("avgrhr", "平均RHR");
        map.put("rfgfloor", "弱覆盖楼宇数");
        map.put("buildinglong", "小区经度");
        map.put("buildinglat", "小区纬度");
        list.add(map);
        return list;
    }

    /**重叠覆盖分析*/
    public List<Map> overCoverHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intcity","城市");
        map.put("intENbID", "intENbID");
        map.put("cellID", "CellID");
        map.put("totalSpCount", "总采样点数");
        map.put("intSumOverCoverSp", "重叠覆盖采样点数");
        map.put("eNodeBName", "eNodeB名称");
        map.put("eNodeBID", "EnbID");
        map.put("cellName", "小区名称");
        map.put("pci", "PCI");
        map.put("pcarrierfr", "频点");
        map.put("powerInfo", "频段");
        map.put("lonb", "lonb");
        map.put("latb", "latb");
        map.put("azimuth", "azimuth");
        map.put("overRate", "重叠覆盖度(%)");
        map.put("dtTime", "时间");
        list.add(map);
        return list;
    }

    /**邻区过覆盖分析*/
    public List<Map> ncoCoverHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intcity","城市");
        map.put("intENbID", "intENbID");
        map.put("cellID", "cellID");
        map.put("eNodeBName", "eNodeB名称");
        map.put("pci", "PCI");
        map.put("powerInfo", "频段");
        map.put("pcarrierfr", "频点");
        map.put("totalSpCount", "好覆盖采样点数");
        map.put("ncOverCoverSpCount", "邻区过覆盖采样点数");
        map.put("lonb", "lonb");
        map.put("latb", "latb");
        map.put("azimuth", "azimuth");
        map.put("mod3Rate", "邻区过覆盖度(%)");
        map.put("dtTime", "时间");
        list.add(map);
        return list;
    }

    /**邻区优化分析*/
    public List<Map> ncoPtimizeHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intENbID", "intENbID");
        map.put("cellID", "cellID");
        map.put("eNodeBName", "eNodeB名称");
        map.put("cellName", "小区名称");
        map.put("pci", "PCI");
        map.put("powerInfo", "频段");
        map.put("pcarrierfr", "频点");
        map.put("totalSpCount", "总采样点数");
        map.put("ncLosSpCount", "邻区缺失采样点数");
        map.put("lonb", "lonb");
        map.put("latb", "latb");
        map.put("azimuth", "azimuth");
        map.put("mod3Rate", "覆盖邻区缺失概率(%)");

        list.add(map);
        return list;
    }

    /**RSRP-RSRQ九宫格分析*/
    public List<Map> rsrqStyleboxHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("timestart", "开始时间");
        map.put("timeend", "结束时间");
        map.put("intenbid", "intENbID");
        map.put("intcellid", "cellID");
        map.put("cellcount", "采样点数");
        map.put("usercount", "采样点数");
        map.put("rsrpavg", "RSRP均值dBm");
        map.put("rsrqavg", "RSRQ均值dB");
        map.put("vcimsi", "vcimsi");
        map.put("cellid","cellID");
        map.put("type","type");
        map.put("taavg", "TA均值dBm");
        map.put("vccity", "城市");
        map.put("vccellname", "小区名称");
        map.put("vcmsisdn", "vcmsisdn");
        map.put("intcity", "城市");


        list.add(map);
        return list;
    }

    /**RSRP-TA九宫格分析*/
    public List<Map> taStyleboxHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intenbid", "intENbID");
        map.put("intcellid", "cellID");
        map.put("cellcount", "采样点数");
        map.put("usercount", "采样点数");
        map.put("rsrpavg", "RSRP均值dBm");
        map.put("taavg", "TA均值");
        map.put("vcimsi", "vcimsi");
        map.put("vcmsisdn", "vcmsisdn");
        map.put("vccity", "城市");
        map.put("vccellname", "小区名称");
        map.put("intcity", "城市");

        list.add(map);
        return list;
    }

    /**用户指标分析*/
    public List<Map> userKpiHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intcity", "城市");
        map.put("vcimsi", "vcimsi");
        map.put("vcmsisdn", "vcmsisdn");
        map.put("intenbid", "intENbID");
        map.put("intcellid", "cellID");
        map.put("dttime", "时间");
        map.put("rsrpavg", "RSRP均值dBm");
        map.put("rsrqavg", "RSRQ均值dBm");
        map.put("taavg", "TA均值");
        map.put("intrrcconnreq", "RRC连接建立请求次数");
        map.put("intrrcconnsucc", "RRC连接建立成功次数");
        map.put("intrrcconnrej", "RRC连接建立失败次数");
        map.put("intrrcconsuccrate", "RRC连接建立成功率(%)");
        map.put("intrrcreestabreq", "RRC连接重建请求次数");
        map.put("intrrcreestabsucc", "RRC连接重建成功次数");
        map.put("intrrcreestabsuccrate", "RRC连接重建成功率(%)");
        map.put("intrrcreestabrate", "RRC连接重建比例(%)");
        map.put("intrrcreestabrej", "RRC连接重建失败次数");
        map.put("intrrcreleasereq", "RRC连接释放次数");
        map.put("intemerrrcconnreq", "emergency RRC连接请求次数");
        map.put("inthparrcconnreq", "highPriorityAccess RRC连接请求次数");
        map.put("intmtarrcconnreq", "mt-Access RRC连接请求次数");
        map.put("intmosrrcconnreq", "mo-Signalling RRC连接请求次数");
        map.put("intmodrrcconnreq", "mo-Data RRC连接请求次数");
        map.put("intdtarrcconnreq", "delayTolerantAccess RRC连接请求次数");
        map.put("intemerrrcconncomp", "emergency RRC连接建立成功次数");
        map.put("inthparrcconncomp", "highPriorityAccess RRC连接建立成功次数");
        map.put("intmtarrcconncomp", "mt-Access RRC连接建立成功次数");
        map.put("intmosrrcconncomp", "mo-Signalling RRC连接建立成功次数");
        map.put("intmodrrcconncomp", "mo-Data RRC连接建立成功次数");
        map.put("intdtarrcconncomp", "delayTolerantAccess RRC连接建立成功次数");
        map.put("intemerrrcconnrej", "emergency RRC连接建立失败次数");
        map.put("inthparrcconnrej", "highPriorityAccess RRC连接建立失败次数");
        map.put("intmtarrcconnrej", "mt-Access RRC连接建立失败次数");
        map.put("intmosrrcconnrej", "mo-Signalling RRC连接建立失败次数");
        map.put("intmodrrcconnrej", "mo-Data RRC连接建立失败次数");
        map.put("intdtarrcconnrej", "delayTolerantAccess RRC连接建立失败次数");
        map.put("intrecfgfailrrcreestabreq", "reconfigurationFailure RRC重建请求次数");
        map.put("inthofailrrcreestabreq", "handoverFailure RRC重建请求次数");
        map.put("intothfailrrcreestabreq", "otherFailure RRC重建请求次数");
        map.put("intrecfgfailrrcreestabcomp", "reconfigurationFailure RRC重建成功次数");
        map.put("inthofailrrcreestabcomp", "handoverFailure RRC重建成功次数");
        map.put("intothfailrrcreestabcomp", "otherFailure RRC重建成功次数");
        map.put("intrecfgfailrrcreestabrej", "reconfigurationFailure RRC重建失败次数");
        map.put("inthofailrrcreestabrej", "handoverFailure RRC重建失败次数");
        map.put("intothfailrrcreestabrej", "otherFailure RRC重建失败次数");
        map.put("intlbtrrrcrel", "loadBalancingTAUrequired RRC连接释放次数");
        map.put("intothrrcrel", "other RRC连接释放次数");
        map.put("intcsfbhprrcrel", "cs-FallbackHighPriority RRC连接释放次数");

        //X2指标
        map.put("intx2horeq","X2切换请求次数");
        map.put("intx2hosucc","X2切换成功次数");
        map.put("intx2succrate","X2切换成功率(%)");
        map.put("intsenbid","源ENBID");
        map.put("intdenbid","目的ENBID");
        map.put("intsourceci","源cellID");
        map.put("inttargetci","目的cellID");
        map.put("intx2hofa","X2切换失败次数");
        map.put("intx2hocancel","X2切换取消次数");
        map.put("intreqclass0cause1","切换请求:handover-desirable-for-radio-reasons");
        map.put("intreqclass0cause8","切换请求:ho-target-not-allowed");
        map.put("intreqclass0cause10","切换请求:trelocprep-expiry");
        map.put("intreqclass0cause11","切换请求:cell-not-available");
        map.put("intreqclass0cause21","切换请求:unspecified");
        map.put("intfailclass0cause7","切换失败:unknown-pair-of-UE-X2AP-ID");
        map.put("intfailclass0cause8","切换失败:ho-target-not-allowed");
        map.put("intfailclass0cause11","切换失败:cell-not-available");
        map.put("intfailclass0cause14","切换失败:unknown-MME-Code");
        map.put("intfailclass0cause12","切换失败:no-radio-resources-available-in-target-cell");
        map.put("intfailclass0cause21","切换失败:unspecified");
        map.put("intcancelclass0cause7","切换取消:unknown-pair-of-UE-X2AP-ID");
        map.put("intcancelclass0cause8","切换取消:ho-target-not-allowed");
        map.put("intcancelclass0cause11","切换取消:cell-not-available");
        map.put("intcancelclass0cause14","切换取消:unknown-MME-Code");
        map.put("intcancelclass0cause12","切换取消:no-radio-resources-available-in-target-cell");
        map.put("intcancelclass0cause21","切换取消：unspecified");

        list.add(map);
        return list;
    }

    /**弱覆盖表头*/
    public List<Map> rfgHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intcity", "城市");
        map.put("intENbID", "eNodeBID");
        map.put("eNodeBName", "eNodeB名称");
        map.put("cellID", "CellID");
        map.put("pci", "PCI");
        map.put("powerInfo", "频段");
        map.put("pcarrierfr", "频点");
        map.put("cellName", "小区名称");
        map.put("mrweakcovercount", "弱覆盖采样点");
        map.put("mrocount", "总采样点");
        map.put("cellweak", "弱覆盖比例（%）");
        map.put("fgCell", "小区覆盖分析");
        map.put("alarmeNodeBID", "eNodeBID");
        map.put("alarmCellID", "小区ID");
        map.put("alarmCode", "告警码");
        map.put("standardAlarmCode", "标准告警码");
        map.put("alarmLevel", "告警级别");
        map.put("happenTime", "发生时间");
        map.put("alarmrestTime", "告警恢复时间");
        map.put("continueTime", "持续时间(hh:mm:ss)");
        map.put("ncmo_ncMissSp", "邻区缺失采样点");
        map.put("ncmoScale", "覆盖邻区缺失概率(%)");
        map.put("ewc_edgeWeakSp", "边缘弱覆盖采样点");
        map.put("ewc_calculSp", "边缘弱覆盖统计采样点");
        map.put("ewcPercent", "边缘弱覆盖强度(%)");
        map.put("nDistance", "最小站间距(米)");
        map.put("isLongly", "孤站分析");
        map.put("rcr_roomCroomSp", "室外打室内采样点");
        map.put("rcr_calculSp", "室外打室内统计采样点");
        map.put("rcrPercent", "室外打室内概率(%)");
        map.put("ncmo_fazhi", "覆盖邻区缺失阀值(%)");
        map.put("distance_fazhi", "最小站间距");
        map.put("rcr_fazhi", "室外打室内阀值(%)");
        map.put("wtdw", "问题定位");
        map.put("yhjy", "优化建议");
        map.put("cellNcmo", "覆盖邻区缺失概率(%)");
        map.put("ewc_fazhi", "边缘弱覆盖阀值(%)");
        map.put("isAlarm", "是否存在硬件故障告警");
        list.add(map);
        return list;
    }

    /**上行干扰表头*/
    public List<Map> upgrHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("eNodeBID", "eNodeBID");
        map.put("eNodeBName", "eNodeB名称");
        map.put("cellID", "小区ID");
        map.put("cellName", "小区名称");
        map.put("dayCount", "统计天数");
        map.put("rbAvgNum", "IOT异常天数");
        map.put("iotNum", "底噪异常天数");
        map.put("grCell", "小区干扰分析");
        map.put("timeStart", "开始时间");
        map.put("timeEnd", "结束时间");
        map.put("rb1", "RB0到RB9平均噪声干扰(dBm)");
        map.put("rb2", "RB10到RB19平均噪声干扰(dBm)");
        map.put("rb3", "RB20到RB29平均噪声干扰(dBm)");
        map.put("rb4", "RB30到RB39平均噪声干扰(dBm)");
        map.put("rb5", "RB40到RB49平均噪声干扰(dBm)");
        map.put("rb6", "RB50到RB59平均噪声干扰(dBm)");
        map.put("rb7", "RB60到RB69平均噪声干扰(dBm)");
        map.put("rb8", "RB70到RB79平均噪声干扰(dBm)");
        map.put("rb9", "RB80到RB89平均噪声干扰(dBm)");
        map.put("rb10", "RB90到RB99平均噪声干扰(dBm)");
        map.put("rbIOT1", "RB0到RB9平均噪声干扰抬升(dB)");
        map.put("rbIOT2", "RB10到RB19平均噪声干扰抬升(dB)");
        map.put("rbIOT3", "RB20到RB29平均噪声干扰抬升(dB)");
        map.put("rbIOT4", "RB30到RB39平均噪声干扰抬升(dB)");
        map.put("rbIOT5", "RB40到RB49平均噪声干扰抬升(dB)");
        map.put("rbIOT6", "RB50到RB59平均噪声干扰抬升(dB)");
        map.put("rbIOT7", "RB60到RB69平均噪声干扰抬升(dB)");
        map.put("rbIOT8", "RB70到RB79平均噪声干扰抬升(dB)");
        map.put("rbIOT9", "RB80到RB89平均噪声干扰抬升(dB)");
        map.put("rbIOT10", "RB90到RB99平均噪声干扰抬升(dB)");
        map.put("alarmCode", "告警码");
        map.put("standardAlarmCode", "标准告警码");
        map.put("alarmLevel", "告警级别");
        map.put("happenTime", "发生时间");
        map.put("alarmrestTime", "告警恢复时间");
        map.put("continueTime", "持续时间(hh:mm:ss)");
        map.put("sfAssignment", "上下行子帧分配配置");
        map.put("specialSfPatterns", "特殊子帧配置");
        map.put("wtdw", "问题定位");
        map.put("yhjy", "优化建议");
        list.add(map);
        return list;
    }

    /**下行干扰表头*/
    public List<Map> downgrHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("eNodeBID", "eNodeBID");
        map.put("eNodeBName", "eNodeB名称");
        map.put("cellID", "小区ID");
        map.put("cellName", "小区名称");
        map.put("macCount", "统计天数");
        map.put("macNum", "误块率异常天数");
        map.put("grCell", "小区干扰分析");
        map.put("timeStart", "开始时间");
        map.put("timeEnd", "结束时间");
        map.put("avgMc", "MAC层下行误块率(%)");
        map.put("td_threeCount", "模三干扰采样点");
        map.put("td_threeScale", "模三干扰强度(%)");
        map.put("oc_overCoSp", "重叠覆盖采样点");
        map.put("oc_overScale", "重叠覆盖度(%)");
        map.put("nca_ncAcrossSp", "邻区过覆盖采样点");
        map.put("nc_overScale", "邻区过覆盖度(%)");
        map.put("wtdw", "问题定位");
        map.put("yhjy", "优化建议");
        list.add(map);
        return list;
    }

    /**接入问题分析表头*/
    public List<Map> accessHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("eNodeBID", "eNodeBID");
        map.put("eNodeBName", "eNodeB名称");
        map.put("cellID", "小区ID");
        map.put("cellName", "小区名称");
        map.put("rrcCount", "统计天数");
        map.put("rrcNum", "接入异常天数");
        map.put("accessCell", "接入问题分析");
        map.put("timeStart", "开始时间");
        map.put("timeEnd", "结束时间");
        map.put("rrcConnReqCount", "RRC连接建立请求次数");
        map.put("rrcConnSuccessCount", "RRC连接建立成功次数");
        map.put("rrcConnSuccessPercent", "RRC连接建立成功率(%)");
        map.put("alarmeNodeBID", "eNodeBID");
        map.put("timerTimeoutCount", "定时器超时导致的RRC连接建立失败次数");
        map.put("otherReasonCount", "其他原因导致的RRC连接建立失败次数");
        map.put("enbAcceptCount", "eNB接纳失败导致的RRC连接建立失败次数");
        map.put("timerTimeoutPercent", "定时器超时导致的RRC连接建立失败比率(%)");
        map.put("otherReasonPercent", "其他原因导致的RRC连接建立失败比率(%)");
        map.put("enbAcceptPercent", "eNB接纳失败导致的RRC连接建立失败比率(%)");
        map.put("coverType", "覆盖类型");
        map.put("MROCount", "MRO采样点数");
        map.put("cellWeak", "小区弱覆盖度(%)");
        map.put("fgCell", "小区覆盖分析");
        map.put("alarmCode", "告警码");
        map.put("standardAlarmCode", "标准告警码");
        map.put("alarmLevel", "告警级别");
        map.put("happenTime", "发生时间");
        map.put("alarmrestTime", "告警恢复时间");
        map.put("continueTime", "持续时间(hh:mm:ss)");
        map.put("dayCount", "统计天数");
        map.put("rbAvgNum", "IOT异常天数");
        map.put("iotNum", "底噪异常天数");
        map.put("macCount", "统计天数");
        map.put("macNum", "误块率异常天数");
        map.put("grCell", "小区干扰分析");
        map.put("wtdw", "问题定位");
        map.put("yhjy", "优化建议");
        list.add(map);
        return list;
    }

    /**切换分析表头*/
    public List<Map> switchHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("eNodeBID", "eNodeBID");
        map.put("eNodeBName", "eNodeB名称");
        map.put("cellID", "小区ID");
        map.put("cellName", "小区名称");
        map.put("changeCount", "统计天数");
        map.put("changeNum", "切换问题天数");
        map.put("switchCell", "切换问题分析");
        map.put("timeStart", "开始时间");
        map.put("timeEnd", "结束时间");
        map.put("ncellID", "邻小区ID");
        map.put("ncellName", "邻小区名称");
        map.put("changePrematurelyResultInFailCount", "切换过早导致的切换失败次数");
        map.put("changeTooLateResultInChangeFailCount", "切换过晚导致的切换失败次数");
        map.put("ncmo_calculSp", "合理邻区缺失总采样点");
        map.put("ncmo_ncMissSp", "合理邻区缺失采样点");
        map.put("ncmoPercent", "合理邻区缺失概率(%)");
        map.put("sameChangeReqCount", "同频切换出执行请求次数");
        map.put("sameChangeSuccessCount", "同频切换出执行成功次数");
        map.put("sameChangePercent", "同频切换出执行成功率(%)");
        map.put("A3WeakSp", "A3弱覆盖采样点");
        map.put("A3upNcSp", "A3上报邻小区采样点");
        map.put("A3Percent", "A3弱覆盖概率(%)");
        map.put("dayCount", "统计天数");
        map.put("rbAvgNum", "IOT异常天数");
        map.put("iotNum", "底噪异常天数");
        map.put("macCount", "统计天数");
        map.put("macNum", "误块率异常天数");
        map.put("grCell", "小区干扰分析");
        map.put("wtdw", "问题定位");
        map.put("yhjy", "优化建议");
        list.add(map);
        return list;
    }

    /**掉线分析表头*/
    public List<Map> offlineHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("eNodeBID", "eNodeBID");
        map.put("eNodeBName", "eNodeB名称");
        map.put("cellID", "小区ID");
        map.put("cellName", "小区名称");
        map.put("eraCount", "统计天数");
        map.put("eraNum", "掉线问题天数");
        map.put("offlineCell", "掉线问题分析");
        map.put("dayCount", "统计天数");
        map.put("rbAvgNum", "IOT异常天数");
        map.put("iotNum", "底噪异常天数");
        map.put("macCount", "统计天数");
        map.put("macNum", "误块率异常天数");
        map.put("grCell", "小区干扰分析");
        map.put("timeStart", "开始时间");
        map.put("timeEnd", "结束时间");
        map.put("changeCount", "统计天数");
        map.put("changeNum", "切换问题天数");
        map.put("switchCell", "切换问题分析");
        map.put("coverType", "覆盖类型");
        map.put("MROCount", "MRO采样点数");
        map.put("cellWeak", "小区弱覆盖度(%)");
        map.put("fgCell", "小区覆盖分析");
        map.put("alarmeNodeBID", "eNodeBID");
        map.put("alarmCode", "告警码");
        map.put("standardAlarmCode", "标准告警码");
        map.put("alarmLevel", "告警级别");
        map.put("happenTime", "发生时间");
        map.put("alarmrestTime", "告警恢复时间");
        map.put("continueTime", "持续时间(hh:mm:ss)");
        map.put("changeExceptionCount", "切换失败导致的E-RAB异常释放次数");
        map.put("netJamExceptionCount", "网络拥塞导致的E-RAB异常释放次数");
        map.put("wirelessExceptionCount", "无线问题导致的E-RAB异常释放次数");
        map.put("coreNetExceptionCount", "核心网异常原因导致的E-RAB异常释放次数");
        map.put("changePercent", "切换失败导致的E-RAB异常释放比率(%)");
        map.put("netJamPercent", "网络拥塞导致的E-RAB异常释放比率(%)");
        map.put("wirelessPercent", "无线链路失败导致的E-RAB异常释放比率(%)");
        map.put("coreNetPercent", "核心网异常原因导致的E-RAB异常释放比率(%)");
        map.put("erabBuildSuccessCount", "E-RAB建立成功总数");
        map.put("erabOfflinePercent", "E-RAB掉线率(%)");
        map.put("wtdw", "问题定位");
        map.put("yhjy", "优化建议");
        list.add(map);
        return list;
    }

    /**MR阈值配置表头*/
    public List<Map> mrlteFazhiHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vcCreateUser", "创建用户ID");
        map.put("vcKey", "类型编码");
        map.put("name", "类型名称");
        map.put("module", "功能模块");
        map.put("orderNo", "排序");
        map.put("vcRemark", "备注");
        map.put("dictionarytypeCode", "系统字典类型");
        map.put("value", "参数值");
        list.add(map);
        return list;
    }


    /**LTE用指标查询导出*/
    public List<Map> customTargetChartListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("dttime", "时间");
        map.put("cellid", "小区ID");
        map.put("enodebname", "小区名称");
        map.put("cityname", "城市名称");
        map.put("badvcimsi", "差用户数");
        map.put("vcimsi", "总用户数");
        map.put("scale", "差用户占比");
        list.add(map);
        return list;
    }


    /**九宫格分析——小区趋势分析导出*/
    public List<Map> pickUpBaddlyDataForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("cityname", "城市名称");
        map.put("enodebid", "enodebid");
        map.put("cellid", "cellid");
        map.put("enodebname", "小区名称");
        map.put("gezi", "所在格子");
        map.put("countt", "出现次数");
        list.add(map);
        return list;
    }

    /**VIP用户保障报表导出*/
    public List<Map> vipIndexHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("dttime", "开始时间（15分钟粒度）");
        map.put("vcimsi", "IMSI");
        map.put("vcmsisdn", "用户");
        map.put("vcenbname", "eNodeB名称");
        map.put("intenbid", "ENBID");
        map.put("intcellid", "cellid");
        map.put("rsrpavg", "RSRP均值");
        map.put("rsrqavg", "RSRQ均值");
        map.put("taavg", "TA均值");
        map.put("intrrcconsuccrate", "RRC建立成功率");
        map.put("intrrcconnrej", "RRC建立失败数");
        map.put("x2changesuc", "X2切换成功率");
        map.put("intx2hofa", "X2切换失败数");
        list.add(map);
        return list;
    }


    /**VIP用户表导出*/
    public List<Map> vipAllIndexHeader(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vcmsisdn", "手机号码");
        map.put("intcity", "城市");
        list.add(map);
        return list;
    }


    /**室内用户分析查询导出*/
    public List<Map> getDataForCellAndGridDataXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("cityname", "地市");
        map.put("dttime", "时间");
        map.put("intenbid", "Enbid");
        map.put("intcellid", "Cellid");
        map.put("intgridid", "栅格ID");
        map.put("intrrcsetuptotalvalue", "RRC连接建立请求次数");
        map.put("intrrcsetuptotal", "RRC连接建立成功次数");
        map.put("rrcsuccscale", "RRC连接建立成功率（%）");
        map.put("intx2horeqtotalvalue", "X切换请求次数");
        map.put("intx2horeqtotal", "X2切换成功次数");
        map.put("x2succscale", "X2切换成功率（%）");
        map.put("rsrpavg", "RSRP均值dBm");
        map.put("rsrqavg", "RSRQ均值dB");
        map.put("intweakcovertotal", "弱覆盖采样点数量");
        map.put("intgoodcovertotal", "良好覆盖采样点数量");
        map.put("intmrtotalvalue", "总采样点");
        map.put("weaksuccscale", "弱覆盖比例");
        map.put("goodsuccscale", "良好覆盖比例");
        map.put("kpi_in", "稳定用户数");
        map.put("kpi_inh", "占用宏站数");
        map.put("flonb", "经度");
        map.put("flatb", "纬度");
        list.add(map);
        return list;
    }
    /**室内用户分析查询导出*/
    public List<Map> findCellDetiForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vcimei", "vcimei");
        map.put("dttime", "时间");
        map.put("vcmsisdn", "vcmsisdn");
        map.put("intcellid", "CellId");
        map.put("vcenbname", "小区名称");
        map.put("intenbid", "Enbid");

        map.put("area", "行政区");
        map.put("RSRP均值dBm", "RSRP均值dBm");
        map.put("RSRQ均值dB", "RSRQ均值dB");
        map.put("上行SINR均值dB", "上行SINR均值dB");
        map.put("小区弱覆盖度", "小区弱覆盖度(%)");
        map.put("重叠覆盖度", "重叠覆盖度(%)");
        map.put("邻区缺失概率", "邻区缺失概率");
        map.put("边缘弱覆盖度", "边缘弱覆盖度(%)");
        map.put("室外覆盖室内概率", "室外覆盖室内概率");
        map.put("模三干扰强度", "模三干扰强度(%)");
        map.put("邻区过覆盖度", "邻区过覆盖度(%)");
        map.put("上行干扰强度", "上行干扰强度(%)");
        map.put("UE高发射功率强度", "UE高发射功率强度(%)");
        map.put("上行丢包率QCI1", "上行丢包率QCI1(‰)");
        map.put("上行丢包率QCI2", "上行丢包率QCI2(‰)");
        map.put("上行丢包率QCI3", "上行丢包率QCI3(‰)");
        map.put("上行丢包率QCI4", "上行丢包率QCI4(‰)");
        map.put("上行丢包率QCI5", "上行丢包率QCI5(‰)");
        map.put("上行丢包率QCI6", "上行丢包率QCI6(‰)");
        map.put("上行丢包率QCI7", "上行丢包率QCI7(‰)");
        map.put("上行丢包率QCI8", "上行丢包率QCI8(‰)");
        map.put("上行丢包率QCI9", "上行丢包率QCI9(‰)");
        map.put("下行丢包率QCI1", "下行丢包率QCI1(‰)");
        map.put("下行丢包率QCI2", "下行丢包率QCI2(‰)");
        map.put("下行丢包率QCI3", "下行丢包率QCI3(‰)");
        map.put("下行丢包率QCI4", "下行丢包率QCI4(‰)");
        map.put("下行丢包率QCI5", "下行丢包率QCI5(‰)");
        map.put("下行丢包率QCI6", "下行丢包率QCI6(‰)");
        map.put("下行丢包率QCI7", "下行丢包率QCI7(‰)");
        map.put("下行丢包率QCI8", "下行丢包率QCI8(‰)");
        map.put("下行丢包率QCI9", "下行丢包率QCI9(‰)");
        map.put("TA均值", "TA均值");
        map.put("RRC连接建立请求次数", "RRC连接建立请求次数");
        map.put("RRC连接建立成功次数", "RRC连接建立成功次数");
        map.put("RRC连接建立拒绝次数", "RRC连接建立拒绝次数");
        map.put("RRC连接建立超时次数", "RRC连接建立超时次数");
        map.put("RRC连接建立成功率", "RRC连接建立成功率");
        map.put("RRC连接重建立请求次数", "RRC连接重建立请求次数");
        map.put("RRC连接重建立成功次数", "RRC连接重建立成功次数");
        map.put("RRC连接重建立拒绝次数", "RRC连接重建立拒绝次数");
        map.put("RRC连接重建立超时次数", "RRC连接重建立超时次数");
        map.put("RRC连接重建立成功率", "RRC连接重建立成功率");
        map.put("RRC连接重建立比率", "RRC连接重建立比率(%)");
        map.put("RRC连接建立时长", "RRC连接建立时长(ms)");
        map.put("上行业务信息PRB占用率", "上行业务信息PRB占用率");
        map.put("下行业务信息PRB占用率", "下行业务信息PRB占用率");
        map.put("小区用户面下行丢包率", "小区用户面下行丢包率(‰)");
        map.put("小区用户面下行弃包率", "小区用户面下行弃包率(‰)");
        map.put("小区用户面上行丢包率", "小区用户面上行丢包率(‰)");
        map.put("上行64QAM占比", "上行64QAM占比(%)");
        map.put("下行64QAM占比", "下行64QAM占比(%)");
        map.put("上行初始HARQ重传比率", "上行初始HARQ重传比率(%)");
        map.put("下行初始HARQ重传比率", "下行初始HARQ重传比率(%)");
        map.put("RIP均值", "RIP均值(dBm)");
        map.put("PHR均值", "PHR均值(dB)");
        map.put("TM2378分布比例", "TM2378分布比例(%)");
        map.put("下行双流占比", "下行双流占比(%) ");
        map.put("下行双流时长占比", "下行双流时长占比(%)");
        map.put("grid", "网格");
        map.put("cellID", "小区");
        map.put("cellName", "小区名");
        map.put("eNodeBName", "eNodeB名称");
        map.put("eNodeBID", "EnbID");
        map.put("pci", "PCI");
        map.put("pcarrierfr", "频点");
        map.put("powerInfo", "频段");
        map.put("lonb", "lonb");
        map.put("latb", "latb");
        map.put("azimuth", "azimuth");
        map.put("X2切换准备请求次数", "X2切换准备请求次数");
        map.put("X2切换准备成功次数", "X2切换准备成功次数");
        map.put("X2切换准备失败次数_等待切换响应消息超时", "X2切换准备失败次数_等待切换响应消息超时");
        map.put("X2切换准备失败次数_目标侧准备失败", "X2切换准备失败次数_目标侧准备失败");
        map.put("X2切换准备失败次数_源侧取消切换", "X2切换准备失败次数_源侧取消切换");
        map.put("X2切换执行成功次数", "X2切换执行成功次数");
        map.put("X2切换执行平均时长", "X2切换执行平均时长");
        map.put("RLF指示次数", "RLF指示次数");
        map.put("X2错误指示次数", "X2错误指示次数");
        list.add(map);
        return list;
    }


    /**上行sinr问题grid导出*/
    public List<Map> getGtSINRByGradingListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("gridid", "GRID号");
        map.put("问题路段", "问题路段");
        map.put("上行sinr均值", "上行SINR均值(dB)");
        list.add(map);
        return list;
    }
    /**上行sinr问题grid包含小区详情导出*/
    public List<Map> getGtSinrByGradingDetailForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();

        map.put("intenbid", "主EnbID");
        map.put("intcellid", "主CellID");
        map.put("mr采样点", "MR采样点");
        map.put("上行sinr均值", "上行SINR均值");
        map.put("上行sinr低占比", "上行SINR低占比");
        map.put("phr均值", "pPHR均值");
        map.put("高ue发射功率强度", "高UE发射功率强度");
        map.put("rip均值", "RIP均值");
        map.put("上行干扰强度", "上行干扰强度");
        list.add(map);
        return list;
    }


    /**动车高铁线路弱覆盖分析问题grid导出*/
    public List<Map> getGtErrorGridListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("gridid", "GRID号");
        map.put("问题路段", "问题路段");
        map.put("from_to", "行驶方向");
        map.put("avgrsrp", "RSRP均值(dBm)");
        map.put("经度", "经度");
        map.put("纬度", "纬度");
        list.add(map);
        return list;
    }


    /**动车线路切换分析问题grid导出*/
    public List<Map> getGtHoErrorGridListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("gridid", "GRID号");
        map.put("问题路段", "问题路段");
        map.put("行驶方向", "行驶方向");
        map.put("x2切换成功率", "切换出成功率");
        list.add(map);
        return list;
    }


    /**动车线路切换分析问题grid导出*/
    public List<Map> findAllNetKpiListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vcroadof", "线路");
        map.put("dfrom_to", "行驶方向");
        map.put("RSRP均值", "RSRP均值（dbm）");
        map.put("MR覆盖率", "MR覆盖率（%）");
        map.put("LTE里程覆盖率", "LTE里程覆盖率（%）");
        map.put("综合覆盖率覆盖率", "综合覆盖率（%）");
        map.put("上行SINR均值", "上行SINR均值dB");
        map.put("CQI均值", "CQI均值");
        map.put("上行SINR低于0占比", "上行SINR低于0占比（%）");
        map.put("低于7的CQI比例", "低于7的CQI比例（%）");
        map.put("模三干扰强度", "模三干扰强度（%）");
        map.put("切换失败次数", "切换失败次数");
        map.put("X2切换成功率", "X2切换成功率（%）");
        map.put("RSRQ均值", "RSRQ均值（%）");
        map.put("重叠覆盖度", "重叠覆盖度（%）");
        map.put("PHR均值", "PHR均值（dB）");
        map.put("低UE发射功率余量比例", "低UE发射功率余量比例（%）");
        map.put("RRC连接建立成功率", "RRC连接建立成功率（%）");
        map.put("RRC连接重建成功率", "RRC连接重建成功率（%）");
        map.put("RRC连接重建比率", "RRC连接重建比例率（%）");
        map.put("切换出成功率", "切换出成功率（%）");
        map.put("Enb内切换出成功率", "Enb内切换出成功率（%）");
        map.put("Enb间切换出成功率", "Enb间切换出成功率（%）");
        list.add(map);
        return list;
    }

    /**动车线路切换分析问题grid导出*/
    public List<Map> findAllGtNetKpiListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vccityname", "城市");
        map.put("vcroadname", "线路");
        map.put("dfrom_to", "行驶方向");
        map.put("RSRP均值", "RSRP均值（dbm）");
        map.put("MR覆盖率", "MR覆盖率（%）");
        map.put("LTE里程覆盖率", "LTE里程覆盖率（%）");
        map.put("综合覆盖率", "综合覆盖率（%）");
        map.put("切换失败次数", "切换失败次数");
        map.put("上行SINR均值", "上行SINR均值dB");
        map.put("CQI均值", "CQI均值");
        map.put("上行SINR低于0占比", "上行SINR低于0占比（%）");
        map.put("低于7的CQI比例", "低于7的CQI比例（%）");
        map.put("模三干扰强度", "模三干扰强度（%）");
        map.put("X2切换成功率", "X2切换成功率（%）");
        map.put("RSRQ均值", "RSRQ均值（%）");
        map.put("重叠覆盖度", "重叠覆盖度（%）");
        map.put("PHR均值", "PHR均值（dB）");
        map.put("低UE发射功率余量比例", "低UE发射功率余量比例（%）");
        map.put("RRC连接建立成功率", "RRC连接建立成功率（%）");
        map.put("RRC连接重建成功率", "RRC连接重建成功率（%）");
        map.put("RRC连接重建比率", "RRC连接重建比例率（%）");
        map.put("切换出成功率", "切换出成功率（%）");
        map.put("Enb内切换出成功率", "Enb内切换出成功率（%）");
        map.put("Enb间切换出成功率", "Enb间切换出成功率（%）");



        map.put("int_id","问题点编号");
        map.put("city","地市编号");
        map.put("lon","问题点经度");
        map.put("lat","问题点纬度");
        map.put("year","年");
        map.put("month","月");
        map.put("date","日");
        map.put("timestamp","时间");
        map.put("vccitya","A类网络");
        map.put("vccityb","B类网络");
        map.put("vccityc","C类网络");
        map.put("gtname","高铁名称");
        map.put("gt_dir","高铁行驶方向");
        map.put("rd_name","栅格名称");
        map.put("type","问题类别");
        map.put("gridid","高铁栅格ID");

        map.put("sumcount","栅格采样点总数");
        map.put("usercount","栅格用户数");
        map.put("avgrsrp","栅格内RSRP均值");
        map.put("coveragerate","栅格覆盖率");
        map.put("wcount","栅格弱覆盖采样点数");
        map.put("avgrsrq","栅格平均RSRQ");
        map.put("avgmod3","栅格干扰概率");
        map.put("mod3count","栅格模三干扰采样点数");
        map.put("avgcqi","栅格平均CQI");
        map.put("badcqirate","栅格内CQI低于6的比例");
        map.put("cqi6count","栅格CQI低于6的采样点数");
        map.put("avgup_sinr","栅格平均上行SINR");
        map.put("badup_sinrrate","栅格平均上行SINR低于0比例");
        map.put("up_sinr0count","栅格内上行SINR低于0采样点数");
        map.put("edgecoveragerate","栅格覆盖空洞比例");
        map.put("multiplecoveragerate","栅格重叠覆盖比例");

        map.put("cgi_main","主覆盖小区CGI");
        map.put("ci_main","主覆盖小区号");
        map.put("cellname_main","主覆盖小区名");
        map.put("sumcount_main","主覆盖小区采样点总数");
        map.put("avgrsrp_main","主覆盖小区平均RSRP");
        map.put("ci_bad","问题小区号");
        map.put("cellname_bad","问题小区名");
        map.put("coveragerate_bad","问题小区覆盖率");
        map.put("wcount_bad","问题小区弱覆盖采样点数");
        map.put("avgta_bad","问题小区平均TA");
        map.put("avgphr_bad","问题小区平均PHR");
        map.put("edgecoveragerate_bad","问题小区覆盖空洞比例");
        map.put("multiplecoveragerate_bad","问题小区重叠覆盖比例");

        map.put("avgup_sinr_bad","问题小区平均上行SINR");
        map.put("up_sinr0count_bad","问题小区上行SINR低于0采样点数");
        map.put("up_sinr0rate_bad","问题小区上行SINR低于0比例");

        map.put("avgmod3_bad","问题小区干扰概率");
        map.put("mod3count_bad","问题小区模三干扰采样点数");
        map.put("pci_bad","问题小区PCI");
        map.put("pci_ifterfere","干扰小区PCI");
        map.put("avgrsrp_ifterfere","干扰小区平均RSRP");
        map.put("avgrsrp_bad","问题小区平均RSRP");



        map.put("horeqcount","栅格内总切换请求次数");
        map.put("hosucccount","栅格内总切换成功次数");
        map.put("hofailcount","栅格内总切换失败次数");
        map.put("hootcount","栅格内总切换超时次数");
        map.put("horeqcount_cell1","栅格内小区1切换请求次数");
        map.put("hosucccount_cell1","栅格内小区1切换成功次数");
        map.put("hofailcount_cell1","栅格内小区1切换失败次数");
        map.put("hootcount_cell1","栅格内小区1切换超时次数");
        map.put("horeqcount_cell2","栅格内小区2切换请求次数");
        map.put("hosucccount_cell2","栅格内小区2切换成功次数");
        map.put("hofailcount_cell2","栅格内小区2切换失败次数");
        map.put("hootcount_cell2","栅格内小区2切换超时次数");
        map.put("horeqcount_cell3","栅格内小区3切换请求次数");
        map.put("hosucccount_cell3","栅格内小区3切换成功次数");
        map.put("hofailcount_cell3","栅格内小区3切换失败次数");
        map.put("hootcount_cell3","栅格内小区3切换超时次数");

        list.add(map);
        return list;
    }

    /**动车高铁线路干扰分析问题grid导出*/
    public List<Map> getGtError2GridListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("gridid", "GRID号");
        map.put("问题路段", "问题路段");
        map.put("from_to", "行驶方向");
        map.put("干扰概率", "干扰概率（%）");
        map.put("经度", "经度");
        map.put("纬度", "纬度");
        list.add(map);
        return list;
    }
    /**动车高铁线路上行SINR问题grid导出*/
    public List<Map> getGtSinrErrorGridListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("gridid", "GRID号");
        map.put("问题路段", "问题路段");
        map.put("from_to", "行驶方向");
        map.put("sinr", "上行SINR(dB)");
        map.put("经度", "经度");
        map.put("纬度", "纬度");
        list.add(map);
        return list;
    }


    /**动车高铁线路弱覆盖分析/干扰分析问题grid导出*/
    public List<Map> getGtError3GridListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intdenbid", "ENbID");
        map.put("inttargetci", "小区ID");
        //map.put("行驶方向", "行驶方向");
        map.put("平均rsrp", "RSRP均值(dBm)");
        map.put("no_gt_sum", "高负荷采样点数");
        map.put("gt_sum", "总采样点数");
        map.put("gtuserby", "高负荷比例（%）");
        map.put("问题路段", "问题路段");
        list.add(map);
        return list;
    }

    /**动车高铁线路弱覆盖分析/干扰分析问题grid导出*/
    public List<Map> getDtError3GridListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("dttime", "时间");
        map.put("gridid", "GRID号");
        map.put("问题路段", "问题路段");
        map.put("intenbid", "ENbID");
        map.put("intcellid", "cellID");
        map.put("cellid", "小区号");
        map.put("gt_sum", "高负荷数目");
        map.put("gt_count", "总数");
        map.put("dtuserby_count", "总数");
        map.put("dtuserby", "高负荷比例（%）");
        //map.put("行驶方向", "行驶方向");
        map.put("avgrsrp", "RSRP均值(dBm)");
        map.put("模三干扰比例", "模三干扰比例（%）");
        list.add(map);
        return list;
    }


    /**武汉RSRP-TA导出*/
    public List<Map> gettaWhStyleForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("timestart", "开始时间");
        map.put("timeend", "结束时间");
        map.put("vcimsi", "用户");
        map.put("cellid", "小区ID");
        map.put("rsrpavg", "RSRP均值");
        map.put("taavg", "TA均值");
        map.put("aoaavg", "AOA均值");
        map.put("type", "格子号");
        list.add(map);
        return list;
    }

    /**武汉小区体检导出*/
    public List<Map> getcellCheckUpForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("cellid", "小区ID");
        map.put("小区弱覆盖度", "小区弱覆盖度");
        map.put("重叠覆盖度", "重叠覆盖度");
        map.put("过覆盖度", "过覆盖度");
        map.put("模三干扰强度", "模三干扰强度");
        map.put("RRC连接建立成功率", "RRC连接建立成功率");
        map.put("X2切换成功率", "X2切换成功率");
        map.put("RRC掉线率", "RRC掉线率");
        map.put("上行SINR均值", "上行SINR均值");
        map.put("上行干扰强度（dbm）", "上行干扰强度（dbm）");
        map.put("平均覆盖距离(米)", "平均覆盖距离(米)");
        list.add(map);
        return list;
    }

    /**高铁切换分析问题点列表导出*/
    public List<Map> getHoErrorGridListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("问题路段", "问题路段");
        map.put("dfrom_to", "行驶方向");
        map.put("切换失败次数", "切换失败次数");
        map.put("经度", "经度");
        map.put("纬度", "纬度");
        map.put("city", "地市编号");
        map.put("lon", "经度");
        map.put("lat", "纬度");
        map.put("intyear", "年");
        map.put("intmonth", "月");
        map.put("intday", "日");
        map.put("vccitya", "A类网格");
        map.put("vccityb", "B类网格");
        map.put("vccityc", "C类网格");
        map.put("gt_name", "高铁线路名称");
        map.put("gt_dir", "行驶方向");
        map.put("type1", "问题类别");
        map.put("gridid", "高铁栅格ID");
        map.put("cgi", "主覆盖小区CGI");
        map.put("cellid", "主覆盖小区ID");
        map.put("cellname", "主覆盖小区名称");
        map.put("avgrsrp", "主覆盖小区平均RSRP");
        map.put("intrrchosucc", "差小区切换成功次数");
        map.put("intrrchoreq", "差小区切换请求次数");
        map.put("intrrchorate", "差小区切换成功率");
        map.put("enb内切换成功次数", "enb内切换成功次数");
        map.put("enb内切换请求次数", "enb内切换请求次数");
        map.put("enb内切换成功率", "enb内切换成功率");
        map.put("enb间切换成功次数", "enb间切换成功次数");
        map.put("enb间切换请求次数", "enb间切换请求次数");
        map.put("enb间切换成功率", "enb间切换成功率");
        map.put("栅格切换异常次数", "栅格切换异常次数");
        map.put("栅格切换成功次数", "栅格切换成功次数");
        map.put("栅格切换请求次数", "栅格切换请求次数");
        map.put("栅格切换成功率", "栅格切换成功率");
        map.put("异系统切换请求次数", "异系统切换请求次数");
        list.add(map);
        return list;
    }

    /**高铁专网切换序列分析问题点列表导出*/
    public List<Map> getSwitchErrorGridListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("线路", "线路");
        map.put("地市", "地市");
        map.put("源小区id", "源小区ID");
        map.put("目标小区id", "目标小区ID");
        map.put("目标小区名称", "目标小区名称");
        map.put("源小区名称", "源小区名称");
        map.put("切换出次数", "切换出次数");
        map.put("切换出用户数", "切换出用户数");
        list.add(map);
        return list;
    }

    /**高铁容量分析列表导出*/
    public List<Map> getGtCapaLteUserForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("线路", "线路");
        map.put("flong", "经度");
        map.put("flat", "纬度");
        map.put("intyear", "年");
        map.put("intmonth", "月");
        map.put("intday", "日");
        map.put("vccitya", "网格A");
        map.put("vccityb", "网格B");
        map.put("vccityc", "网格C");
        map.put("gt_dir", "行驶方向");
        map.put("gt_name", "高铁名称");
        map.put("type1", "问题类型");
        map.put("cgi", "小区CGI");
        map.put("地市", "地市");
        map.put("小区id", "小区号");
        map.put("小区名称", "小区名");
        map.put("lte用户数", "专网小区LTE用户数");
        map.put("高铁用户数", "专网小区高铁用户数");
        map.put("公网用户数", "专网小区公网用户数");
        map.put("公网用户占比", "专网小区公网用户占比(%)");
        list.add(map);
        return list;
    }
    /**聚类问题点导出*/
    public List<Map> queryPlbListExcel(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intdateid", "时间");
        map.put("vccity", "地市");
        map.put("intcityid", "地市");
        map.put("vcgridid", "网格名");
        map.put("intjlcount", "聚类问题");
        map.put("vcjltype", "聚类问题点类型");
        map.put("fjlradiu", "聚类问题点半径");
        map.put("vcjlplbnum", "聚类问题点编号");
        map.put("fjllonb", "聚类后中心经度");
        map.put("fjllat", "聚类后中心纬度");
        map.put("vcplbtype", "问题点类型");
        map.put("flonb", "中心经度");
        map.put("flat", "中心纬度");
        map.put("vcfilename", "文件名");
        map.put("vcproblemnum", "问题点编号");

        map.put("vcroadname", "道路");
        map.put("fmrweakcoverrate", "弱覆盖点占比(%)");
        map.put("fditance", "持续距离(米)");
        map.put("fdurationtime", "持续时间(秒)");
        map.put("intallcount", "采样点总数");
        map.put("fmaxrsrp", "rsrp最大值");
        map.put("fminrsrp", "rsrp最小值");
        map.put("favgrsrp", "rsrp平均值");
        map.put("fncmaxrsrp", "最强邻区最大rsrp");
        map.put("fncminrsrp", "最强邻区最小rsrp");
        map.put("fncavgrsrp", "最强邻区平均rsrp");
        map.put("fmaxsinr", "sinr最大值");
        map.put("fminsinr", "sinr最小值");
        map.put("favgsinr", "sinr平均值");
        map.put("foptproblemrate", "优化问题比例");
        map.put("fplanproblemrate", "规划问题比例");
        map.put("favgdownloadspeed", "平均下载速率(mbps)");
        map.put("fftpavgdownloadspeed", "ftp层平均下载速率(mbps");
        map.put("fdownspeed2mlowscprate", "ftp下载小于2m占比(%)");
        map.put("fftpavguploadspeed", "ftp层平均上传速率(mbps)");
        map.put("fupspeed512klowscprate", "ftp上传小于512k占比(%)");
        map.put("vctacci", "tac-ci");
        map.put("vccellname", "问题路段关联小区");
        map.put("vcarea", "区域名称");
        map.put("fdown064qamrate", "下行码字0 64qam占比(%)");
        map.put("fdoubledurationrate", "双流时长占比(%)");
        map.put("fbiterrorrate", "误块率(%)");
        map.put("ftm3rate", "tm3比例(%)");
        map.put("fdispatch", "prb调度数");
        map.put("fdown164qamrate", "下行码字1 64qam占比(%)");
        map.put("fmaxquality", "最高质量");
        map.put("fminquality", "最低质量");
        map.put("favgquality", "平均质量");
        map.put("fmaxfield", "最大场强");
        map.put("fminfield", "最小场强");
        map.put("favgfield", "平均场强");
        map.put("fbadsinrrate", "连续SINR质差里程占比(%)");
        map.put("fovercovermorethan3rate", "重叠覆盖度≥3比例");
        map.put("fovercoverdistancerate", "重叠覆盖里程占比");
        map.put("favgmcs0", "下行码字0MCS平均值");
        map.put("favgmcs1", "下行码字1MCS平均值");
        map.put("fmaxmcs0", "下行码字0最高频率MCS(%)");
        map.put("fmaxmcs1", "下行码字1最高频率MCS(%)");
        map.put("favgcqi0", "码字0CQI平均值");
        map.put("favgcqi1", "码字1CQI平均值");
        map.put("f064qamrate", "下行码字0 64QAM占比");
        map.put("f164qamrate", "下行码字1 64QAM占比");
        map.put("f016qamrate", "下行码字0 16QAM占比");
        map.put("f116qamrate", "下行码字1 16QAM占比");
        map.put("fmaxthroughputdl", "Throughput_DL最大值");
        map.put("fminthroughputdl", "Throughput_DL最小值");
        map.put("favgthroughputdl", "Throughput_DL平均值");
        map.put("ftransmissionmode", "Transmission_Mode");
        map.put("ftm3rate", "TM3比例（%）");
        map.put("frankindicator", "rank_indicator");
        map.put("fdoubleflowrate", "双流时长占比(%)");
        map.put("fpdcchdlgrantcount", "PDCCH_DL_Grant_Count");
        map.put("fratiodlcode0harqack", "Ratio_DL_Code0_HARQ_ACK");
        map.put("fratiodlcode0harqnack", "Ratio_DL_Code0_HARQ_NACK");
        map.put("fratiodlcode1harqack", "Ratio_DL_Code1_HARQ_ACK");
        map.put("fratiodlcode1harqnack", "Ratio_DL_Code1_HARQ_NACK");
        map.put("intyear", "年");
        map.put("intmonth", "月");
        map.put("intday", "日");
        map.put("intmaxrxquality", "最大RxQuality");
        map.put("intminrxquality", "最小RxQuality");
        map.put("intavgrxquality", "平均RxQuality");
        map.put("fmaxci", "最大C/I");
        map.put("fminci", "最小C/I");
        map.put("favgci", "平均C/I");
        map.put("vccellid", "占用小区");
        map.put("datetime", "问题点发生时间");
        map.put("vcnettype", "网络类型");
        map.put("vcservicetype", "业务类型");
        map.put("intlowcount", "低速率采样点个数");
        map.put("flowrate", "低速率采样点占比");
        map.put("intlessthan2mcount", "小于2M采样点占比");
        map.put("intlessthan512mcount", "小于512K采样点个数");
        map.put("flessthan512mrate", "小于512K采样点占比");
        map.put("fmorethan1mrate", "大于等于1M采样点占比");
        map.put("fmaximumrate", "最高速率");
        map.put("fminimumrate", "最低速率");
        map.put("fzerospeeddistance", "0速率里程");
        map.put("fzerospeeddistancerate", "0速率里程占比");
        map.put("favgspeed", "平均速率");
        map.put("favgpdsch_bler", "平均速率");
        map.put("fpdschrbnumber", "PDSCH_RB_Number");
        map.put("fprbrate", "PRB调度数");
        map.put("vctaccellid", "TAC-CellID");
        map.put("datatime", "问题点发生时间");
        map.put("morerxquality", "最大RxQuality");
        map.put("lessrxquality", "最小RxQuality");
        map.put("averagerxquality", "平均RxQuality");
        map.put("moreci", "最大C/I");
        map.put("lessci", "最小C/I");
        map.put("averageci", "平均C/I");
        map.put("flessthan2mrate", "小于2M采样点占比");
        map.put("flessthan512mrate", "小于512K采样点占比");
        map.put("favgncrsrp", "邻区RSRP平均值");
        map.put("sinrproportion", "质差点占比");
        map.put("vcname", "名称");
        map.put("intlactac", "lacTac");
        map.put("intciect", "CI/ECI");
        map.put("vctacci", "TAC-CI");
        list.add(map);
        return list;
    }

    /**武汉邻区漏配导出*/
    public List<Map> getleakageListListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("scell", "主服小区");
        map.put("eid", "主服小区cellID+邻区频点+邻区PCI");
        map.put("cnt", "采样点");
        map.put("scrsrp", "主小区rsrp均值");
        map.put("ncrsrp", "邻小区rsrp均值");
        map.put("ncellname", "邻小区名");
        list.add(map);
        return list;
    }

    //弱覆盖小区列表
    public List<Map> getWeakCoverListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("enbcellid", "小区ID");
        map.put("cellname", "小区名");
        map.put("pindian", "主频点");
        map.put("pinduan", "频段");
        map.put("mrallcount", "总采样点数");
        map.put("sumcount", "定位采样点数");
        map.put("intweakcovercount", "弱覆盖采样点数");
        map.put("weakcoverrate", "MR弱覆盖率");
        map.put("lonb", "经度");
        map.put("latb", "纬度");
        map.put("timeStart", "开始时间");
        map.put("timeEnd", "结束时间");
        list.add(map);
        return list;
    }

    //弱覆盖小区经纬度列表
    public List<Map> getLonLatListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("enbcellid", "小区ID");
        map.put("cellname", "小区名");
        map.put("avgrsrp", "平均RSRP");
        map.put("nettype", "网络类型");
        map.put("lon", "经度");
        map.put("lat", "纬度");
        list.add(map);
        return list;
    }

    /**
     * 全网数据概况-地市数据质量检查
     * @return
     */
    public List<Map> getQualityListForXls(String day1,String day2,String day3,String day4,String day5,String day6,String day7){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("city", "地市");
        map.put("one1", day7);
        map.put("two2", day6);
        map.put("three3", day5);
        map.put("four4", day4);
        map.put("five5", day3);
        map.put("six6", day2);
        map.put("seven7", day1);
        list.add(map);
        return list;
    }
    /**
     * 全网数据概况-网格数据质量检查
     * @return
     */
    public List<Map> getgridListForXls(String day1,String day2,String day3,String day4,String day5,String day6,String day7){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vccity", "地市");
        map.put("grids", "网格");
        map.put("one1", day7);
        map.put("two2", day6);
        map.put("three3", day5);
        map.put("four4", day4);
        map.put("five5", day3);
        map.put("six6", day2);
        map.put("seven7", day1);
        list.add(map);
        return list;
    }
    /**
     * 全网数据概况-天粒度网格数据质量检查
     * @return
     */
    public List<Map> getOneDayListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("vccity", "地市");
        map.put("intgridid", "网格");
        map.put("uemr_imsi回填率", "uemr_imsi回填率");
        map.put("uemr_imei回填率", "uemr_imei回填率");
        map.put("uemr_msisdn回填率", "uemr_msisdn回填率");
        map.put("uemr_主小区pci回填率", "uemr_主小区pci回填率");
        map.put("uemr_主小区earfcn回填率", "uemr_主小区earfcn回填率");
        map.put("uemr_mr类型回填率", "uemr_mr类型回填率");
        map.put("uemr_邻小区pci回填率", "uemr_邻小区pci回填率");
        map.put("uemr_邻小区earfcn回填率", "uemr_邻小区earfcn回填率");
        map.put("uemr_主小区pci是否回填正常", "uemr_主小区pci是否回填正常");
        map.put("uemr_pci是否符合规范", "uemr_pci是否符合规范");
        map.put("uemr_rsrp是否符合规范", "uemr_rsrp是否符合规范");
        map.put("uemr_rsrq是否符合规范", "uemr_rsrq是否符合规范");
        map.put("uemr_phr是否符合规范", "uemr_phr是否符合规范");
        map.put("uemr_sinrul是否符合规范", "uemr_sinrul是否符合规范");
        map.put("uemr_cqi是否符合规范", "uemr_cqi是否符合规范");
        map.put("uemr_ta是否符合规范", "uemr_ta是否符合规范");
        map.put("uemr_aoa是否符合规范", "uemr_aoa是否符合规范");
        map.put("cellmr_rip_sf1是否符合规范", "cellmr_rip_sf1是否符合规范");
        map.put("cellmr_rip_sf2是否符合规范", "cellmr_rip_sf2是否符合规范");
        map.put("cellmr_rip_sf3是否符合规范", "cellmr_rip_sf3是否符合规范");
        map.put("cellmr_rip_sf4是否符合规范", "cellmr_rip_sf4是否符合规范");
        map.put("cellmr_rip_sf5是否符合规范", "cellmr_rip_sf5是否符合规范");
        map.put("cellmr_rip_sf6是否符合规范", "cellmr_rip_sf6是否符合规范");
        map.put("cellmr_rip_sf7是否符合规范", "cellmr_rip_sf7是否符合规范");
        map.put("cellmr_rip_sf8是否符合规范", "cellmr_rip_sf8是否符合规范");
        map.put("cellmr_rip_sf9是否符合规范", "cellmr_rip_sf9是否符合规范");
        map.put("cellmr_rip_sf10是否符合规范", "cellmr_rip_sf10是否符合规范");
        map.put("cellmr_plrulqci1是否符合规范", "cellmr_plrulqci1是否符合规范");
        map.put("cellmr_plrulqci2是否符合规范", "cellmr_plrulqci2是否符合规范");
        map.put("cellmr_plrulqci3是否符合规范", "cellmr_plrulqci3是否符合规范");
        map.put("cellmr_plrulqci4是否符合规范", "cellmr_plrulqci4是否符合规范");
        map.put("cellmr_plrulqci5是否符合规范", "cellmr_plrulqci5是否符合规范");
        map.put("cellmr_plrulqci6是否符合规范", "cellmr_plrulqci6是否符合规范");
        map.put("cellmr_plrulqci7是否符合规范", "cellmr_plrulqci7是否符合规范");
        map.put("cellmr_plrulqci8是否符合规范", "cellmr_plrulqci8是否符合规范");
        map.put("cellmr_plrulqci9是否符合规范", "cellmr_plrulqci9是否符合规范");
        map.put("cellmr_plrdlqci1是否符合规范", "cellmr_plrdlqci1是否符合规范");
        map.put("cellmr_plrdlqci2是否符合规范", "cellmr_plrdlqci2是否符合规范");
        map.put("cellmr_plrdlqci3是否符合规范", "cellmr_plrdlqci3是否符合规范");
        map.put("cellmr_plrdlqci4是否符合规范", "cellmr_plrdlqci4是否符合规范");
        map.put("cellmr_plrdlqci5是否符合规范", "cellmr_plrdlqci5是否符合规范");
        map.put("cellmr_plrdlqci6是否符合规范", "cellmr_plrdlqci6是否符合规范");
        map.put("cellmr_plrdlqci7是否符合规范", "cellmr_plrdlqci7是否符合规范");
        map.put("cellmr_plrdlqci8是否符合规范", "cellmr_plrdlqci8是否符合规范");
        map.put("cellmr_plrdlqci9是否符合规范", "cellmr_plrdlqci9是否符合规范");
        map.put("uu_imsi回填率", "uu_imsi回填率");
        map.put("uu_imei回填率", "uu_imei回填率");
        map.put("uu_msisdn回填率", "uu_msisdn回填率");
        map.put("uu_基站间切换中目标小区的回填率", "uu_基站间切换中目标小区的回填率");
        map.put("uu_小区内切换中目标小区的回填率", "uu_小区内切换中目标小区的回填率");
        map.put("uu_基站内切换中目标小区的回填率", "uu_基站内切换中目标小区的回填率");
        map.put("one", "UEMR_采集的Enb数");
        map.put("intdateid", "日期");
        list.add(map);
        return list;
    }

    /**
     * 全网数据概况-小时粒度网格数据质量检查
     * @return
     */
    public List<Map> gethourListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();

        map.put("hour", "时段");
        map.put("grids", "网格");
        map.put("intdateid", "日期");
        map.put("uemr_imsi回填率", "uemr_imsi回填率");
        map.put("uemr_imei回填率", "uemr_imei回填率");
        map.put("uemr_msisdn回填率", "uemr_msisdn回填率");
        map.put("uemr_主小区pci回填率", "uemr_主小区pci回填率");
        map.put("uemr_主小区earfcn回填率", "uemr_主小区earfcn回填率");
        map.put("uemr_mr类型回填率", "uemr_mr类型回填率");
        map.put("uemr_邻小区pci回填率", "uemr_邻小区pci回填率");
        map.put("uemr_邻小区earfcn回填率", "uemr_邻小区earfcn回填率");
        map.put("uemr_主小区pci是否回填正常", "uemr_主小区pci是否回填正常");
        map.put("uemr_pci是否符合规范", "uemr_pci是否符合规范");
        map.put("uemr_rsrp是否符合规范", "uemr_rsrp是否符合规范");
        map.put("uemr_rsrq是否符合规范", "uemr_rsrq是否符合规范");
        map.put("uemr_phr是否符合规范", "uemr_phr是否符合规范");
        map.put("uemr_sinrul是否符合规范", "uemr_sinrul是否符合规范");
        map.put("uemr_cqi是否符合规范", "uemr_cqi是否符合规范");
        map.put("uemr_ta是否符合规范", "uemr_ta是否符合规范");
        map.put("uemr_aoa是否符合规范", "uemr_aoa是否符合规范");
        map.put("cellmr_rip_sf1是否符合规范", "cellmr_rip_sf1是否符合规范");
        map.put("cellmr_rip_sf2是否符合规范", "cellmr_rip_sf2是否符合规范");
        map.put("cellmr_rip_sf3是否符合规范", "cellmr_rip_sf3是否符合规范");
        map.put("cellmr_rip_sf4是否符合规范", "cellmr_rip_sf4是否符合规范");
        map.put("cellmr_rip_sf5是否符合规范", "cellmr_rip_sf5是否符合规范");
        map.put("cellmr_rip_sf6是否符合规范", "cellmr_rip_sf6是否符合规范");
        map.put("cellmr_rip_sf7是否符合规范", "cellmr_rip_sf7是否符合规范");
        map.put("cellmr_rip_sf8是否符合规范", "cellmr_rip_sf8是否符合规范");
        map.put("cellmr_rip_sf9是否符合规范", "cellmr_rip_sf9是否符合规范");
        map.put("cellmr_rip_sf10是否符合规范", "cellmr_rip_sf10是否符合规范");
        map.put("cellmr_plrulqci1是否符合规范", "cellmr_plrulqci1是否符合规范");
        map.put("cellmr_plrulqci2是否符合规范", "cellmr_plrulqci2是否符合规范");
        map.put("cellmr_plrulqci3是否符合规范", "cellmr_plrulqci3是否符合规范");
        map.put("cellmr_plrulqci4是否符合规范", "cellmr_plrulqci4是否符合规范");
        map.put("cellmr_plrulqci5是否符合规范", "cellmr_plrulqci5是否符合规范");
        map.put("cellmr_plrulqci6是否符合规范", "cellmr_plrulqci6是否符合规范");
        map.put("cellmr_plrulqci7是否符合规范", "cellmr_plrulqci7是否符合规范");
        map.put("cellmr_plrulqci8是否符合规范", "cellmr_plrulqci8是否符合规范");
        map.put("cellmr_plrulqci9是否符合规范", "cellmr_plrulqci9是否符合规范");
        map.put("cellmr_plrdlqci1是否符合规范", "cellmr_plrdlqci1是否符合规范");
        map.put("cellmr_plrdlqci2是否符合规范", "cellmr_plrdlqci2是否符合规范");
        map.put("cellmr_plrdlqci3是否符合规范", "cellmr_plrdlqci3是否符合规范");
        map.put("cellmr_plrdlqci4是否符合规范", "cellmr_plrdlqci4是否符合规范");
        map.put("cellmr_plrdlqci5是否符合规范", "cellmr_plrdlqci5是否符合规范");
        map.put("cellmr_plrdlqci6是否符合规范", "cellmr_plrdlqci6是否符合规范");
        map.put("cellmr_plrdlqci7是否符合规范", "cellmr_plrdlqci7是否符合规范");
        map.put("cellmr_plrdlqci8是否符合规范", "cellmr_plrdlqci8是否符合规范");
        map.put("cellmr_plrdlqci9是否符合规范", "cellmr_plrdlqci9是否符合规范");
        map.put("uu_imsi回填率", "uu_imsi回填率");
        map.put("uu_imei回填率", "uu_imei回填率");
        map.put("uu_msisdn回填率", "uu_msisdn回填率");
        map.put("uu_基站间切换中目标小区的回填率", "uu_基站间切换中目标小区的回填率");
        map.put("uu_小区内切换中目标小区的回填率", "uu_小区内切换中目标小区的回填率");
        map.put("uu_基站内切换中目标小区的回填率", "uu_基站内切换中目标小区的回填率");
        map.put("one", "UEMR_采集的Enb数");
        map.put("vccity", "城市");

        list.add(map);
        return list;
    }


    /**
     * 一体化指标考核天、月粒度
     * @return
     */
    public List<Map> getFindLineListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("name", "日期/城市");
        map.put("fcoverrate", "LTE综合覆盖<br/>率(%)");
        map.put("frssinr0rate", "RS-SINR 0 <br/>以上占比(%)");
        map.put("frdmroverlayrate", "道路重叠覆盖<br/>度(%)");
        map.put("fmrfullcoverrate", "LTE MR覆盖<br/>率(%)");
        map.put("fmrweakcoverrate", "LTE MR弱覆<br/>盖小区占比(%)");
        map.put("fftpdownloadspeed", "FTP下载速率<br/>(Mbps)");
        map.put("fftpuploadspeed", "FTP上传速率<br/>(Mbps)");
        map.put("f10mscprate", "LTE10M以上<br/>采样点占比(%)");
        map.put("fdownspeed2mlowscprate", "下载速率2M以<br/>下采样点占比<br/>(%)");
        map.put("fup512kbpsloawrate", "上行512Kbps<br/>以下占比(%)");
        map.put("fcsfbcallsucc", "CSFB全程呼<br/>叫成功率(%)");
        map.put("fcsfbvoicerate", "CSFB语音质量<br/>(%)");
        map.put("fsuperfarcellrate", "LTE超远站小<br/>区占比(%)");
        map.put("fsuperhighcellrate", "LTE超高站小<br/>区占比(%)");
        map.put("fsupernearcellrate", "LTE超近站小<br/>区占比(%)");
        map.put("fhighbyte2goutdoorcoverrate", "2G高流量宏站<br/>小区的LTE覆<br/>盖率(%)");
        map.put("fhighbyte2gindoorcoverrate", "2G高流量室分<br/>小区的LTE覆<br/>盖率(%)");
        map.put("fhighloadcapacitycellrate", "高负荷待扩容<br/>小区占比(%)");
        map.put("fgsmhighbytecellrate", "GSM高流量小<br/>区占比(%)");
        map.put("f4gdaystayrate", "LTE日驻留比<br/>(%)");
        map.put("f0bytecellrate","LTE零流量小<br/>区占比(%)");
        map.put("f4gcellonrate","LTE小区退服<br/>率(%)");
        map.put("fhighphrcellrate","LTE高干扰小<br/>区占比(%)");
        map.put("fwirelessconrate","LTE无线接通<br/>率(%)");
        map.put("fwirelessdroprate","LTE无线掉话<br/>率(%)");
        map.put("fgsmbadtrafficrate","GSM质差话务<br/>占比(六忙时)<br/>(%)");
        map.put("fgsmwirelessconrate","GSM无线接通<br/>率(%)");
        map.put("fgsmwirelessdroprate","GSM无线掉话<br/>率(%)");
        map.put("fregisteredsuccrate","注册成功率<br/>(%)");
        map.put("fcallthroughrate","呼叫接通率<br/>(mtc+moc)<br/>(%)");
        map.put("fbegincallthroughrate","始呼接通率<br/>(%)");
        map.put("fvoltedroprate","volte 掉话<br/>率(%)");
        map.put("fesrvcchosuccrate","esrvcc切换<br/>成功率(%)");
        map.put("fesesrvhodelay","esrvcc切<br/>换时延 (ms)");
        map.put("fesesrvhomediadelayavg","平均esrvcc<br/>媒体切换时<br/>长(ms)");
        map.put("fmos3rate","mos3.0占比<br/>(%)");
        map.put("fbegincalldelayv2vavg","平均始呼建<br/>立时延v2v<br/>(ms)");
        map.put("fbegincalldelayvallavg","平均始呼建<br/>立时延v-all<br/>(ms)");

        list.add(map);
        return list;

    }

    /**
     * 高速功能 整体指标导出[如果查询字段有变，导出配置也要变]
     * @return
     */
    public List<Map> getMotorwayRoadTargetExport(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("province", "省份");
        map.put("cityname", "城市");
        map.put("vcroadname", "高速");
        map.put("mrallcountt", "MR采样点");

        map.put("ltefgd", "LTE覆盖率(%)");
        map.put("avgrsrp", "平均RSRP(dBm)");
        map.put("chongdie", "重叠覆盖度(%)");
        map.put("avgphr", "平均PHR(dB)");
        map.put("diue", "低UE发射功率余量比例(%)");

        map.put("avgrsrq", "平均RSRQ(dB)");
        map.put("avgcqi", "平均CQI");
        map.put("di6cqi", "低于6的CQI比例");
        map.put("avgsinr", "平均上行SINR值(dB)");
        map.put("di0sinr", "上行SINR低于0占比(%)");

        map.put("mode3", "模三干扰比例(%)");

        map.put("rrcconnsucc", "RRC连接建立成功率(%)");
        map.put("rrcreestabsucc", "RRC连接重建成功率(%)");
        map.put("rrcreestabbili", "RRC连接重建比例率(%)");

        map.put("yichang", "LTE切换异常次数");
        map.put("rrchointrasucc", "LTE切换成功率(%)");
        map.put("rrchointraenbsucc", "Enb内切换出成功率(%)");
        map.put("rrchointerenbsucc", "Enb间切换出成功率(%)");

        list.add(map);
        return list;

    }

    /**
     * 楼宇覆盖分析列表
     * @return
     */
    public List<Map> floorQuestionExport2(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("rownum", "编号");
        map.put("vccityname", "城市");
        map.put("jingdu", "经度");
        map.put("weidu", "纬度");
        map.put("louyuriqi", "日期");
        map.put("nowyear", "年");
        map.put("nowmonth", "月");
        map.put("nowday", "日");
        map.put("louyushijian", "时");
        map.put("wanggea", "网格A");
        map.put("wanggeb", "网格B");
        map.put("wanggec", "网格C");
        map.put("vcbuildingname", "楼宇名称");
        map.put("wentileixing", "问题类型");
        map.put("louyubianhao", "楼宇ID");
        map.put("usershu", "用户数");
        map.put("louyucaiyangdianzongshu", "楼宇采样点总数");
        map.put("avgrsrp", "楼宇平均RSRP");
        map.put("louyufugailv", "楼宇覆盖率");
        map.put("ruocaiyangdianshu", "楼宇弱覆盖采样点数");
        map.put("avgrsrq", "楼宇平均RSRQ");
        map.put("louyuganraogailv", "楼宇干扰概率");
        map.put("louyumosancaiyang", "楼宇模三干扰采样点数");
        map.put("avgcqi", "楼宇平均CQI");
        map.put("cqidiliu", "楼宇CQI低于6比例");
        map.put("cqidiliucaiyang", "楼宇CQI低于6的采样点数");
        map.put("avgsinr", "楼宇平均上行SINR");
        map.put("avgsinrdiling", "楼宇平均上行SINR低于0比例");
        map.put("neisinrdiling", "楼宇内上行SINR低于0采样点数");
        map.put("fugaikongdong", "楼宇覆盖空洞比例");
        map.put("chongdiefugai", "楼宇重叠覆盖比例");
        map.put("cgi", "主覆盖小区CGI");
        map.put("zhufugaixiaoquid", "主覆盖小区ID");
        map.put("zhufugaixiaoquming", "主覆盖小区名");
        map.put("zhufugaicaiyangdianzongshu", "主覆盖小区采样点总数");
        map.put("zhufugaixiaoquavgrsrp", "主覆盖小区平均rsrp");
        map.put("wentixiaoquid", "问题小区ID");
        map.put("vccellname", "问题小区名");
        map.put("wentixiaoqufugailv", "问题小区覆盖率");
        map.put("wentixiaoquruofugaicaiyangdianshu", "问题小区弱覆盖采样点数");
        map.put("wentixiaoquavgta", "问题小区平均TA");
        map.put("wentixiaoquphr", "问题小区平均PHR");
        map.put("wentixiaoqufugaikongdong", "问题小区覆盖空洞");
        map.put("wentixiaoquchongdiefugai", "问题小区重叠覆盖");

        list.add(map);
        return list;

    }

    /**
     * 楼宇干扰分析列表
     * @return
     */
    public List<Map> floorQuestionExport3(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("rownum", "编号");
        map.put("vccityname", "城市");
        map.put("jingdu", "经度");
        map.put("weidu", "纬度");
        map.put("louyuriqi", "日期");
        map.put("nowyear", "年");
        map.put("nowmonth", "月");
        map.put("nowday", "日");
        map.put("louyushijian", "时");
        map.put("wanggea", "网格A");
        map.put("wanggeb", "网格B");
        map.put("wanggec", "网格C");
        map.put("vcbuildingname", "楼宇名称");
        map.put("wentileixing", "问题类型");
        map.put("louyubianhao", "楼宇ID");
        map.put("usershu", "用户数");
        map.put("louyucaiyangdianzongshu", "楼宇采样点总数");
        map.put("avgrsrp", "楼宇平均RSRP");
        map.put("louyufugailv", "楼宇覆盖率");
        map.put("ruocaiyangdianshu", "楼宇弱覆盖采样点数");
        map.put("avgrsrq", "楼宇平均RSRQ");
        map.put("louyuganraogailv", "楼宇干扰概率");
        map.put("louyumosancaiyang", "楼宇模三干扰采样点数");
        map.put("avgcqi", "楼宇平均CQI");
        map.put("cqidiliu", "楼宇CQI低于6比例");
        map.put("cqidiliucaiyang", "楼宇CQI低于6的采样点数");
        map.put("avgsinr", "楼宇平均上行SINR");
        map.put("avgsinrdiling", "楼宇平均上行SINR低于0比例");
        map.put("neisinrdiling", "楼宇内上行SINR低于0采样点数");
        map.put("fugaikongdong", "楼宇覆盖空洞比例");
        map.put("chongdiefugai", "楼宇重叠覆盖比例");
        map.put("zhufugaixiaoquid", "主覆盖小区ID");
        map.put("zhufugaixiaoquming", "主覆盖小区名");
        map.put("zhufugaicaiyangdianzongshu", "主覆盖小区采样点总数");
        map.put("zhufugaixiaoquavgrsrp", "主覆盖小区平均rsrp");
        map.put("wentixiaoquid", "问题小区ID");
        map.put("vccellname", "问题小区名");
        map.put("wentixiaoquganraogailv", "问题小区干扰概率");
        map.put("wentixiaoqumosanraoganraocaiyangdianshu", "问题小区模三干扰采样点数");
        map.put("wentixiaoqupci", "问题小区PCI");
        map.put("wentixiaoqufugailv", "问题小区覆盖率");
        map.put("ganraoxiaoquavgrsrp", "干扰小区平均RSRP");
        map.put("wentixiaoquavgta", "问题小区平均TA");
        map.put("wentixiaoquphr", "问题小区平均PHR");
        map.put("wentixiaoqufugaikongdong", "问题小区覆盖空洞");
        map.put("wentixiaoquchongdiefugai", "问题小区重叠覆盖");

        list.add(map);
        return list;

    }


    /**
     * 楼宇CQI分析列表
     * @return
     */
    public List<Map> floorQuestionExport4(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("rownum", "编号");
        map.put("vccityname", "城市");
        map.put("jingdu", "经度");
        map.put("weidu", "纬度");
        map.put("louyuriqi", "日期");
        map.put("nowyear", "年");
        map.put("nowmonth", "月");
        map.put("nowday", "日");
        map.put("louyushijian", "时");
        map.put("wanggea", "网格A");
        map.put("wanggeb", "网格B");
        map.put("wanggec", "网格C");
        map.put("vcbuildingname", "楼宇名称");
        map.put("wentileixing", "问题类型");
        map.put("louyubianhao", "楼宇ID");
        map.put("usershu", "用户数");
        map.put("louyucaiyangdianzongshu", "楼宇采样点总数");
        map.put("avgrsrp", "楼宇平均RSRP");
        map.put("louyufugailv", "楼宇覆盖率");
        map.put("ruocaiyangdianshu", "楼宇弱覆盖采样点数");
        map.put("avgrsrq", "楼宇平均RSRQ");
        map.put("louyuganraogailv", "楼宇干扰概率");
        map.put("louyumosancaiyang", "楼宇模三干扰采样点数");
        map.put("avgcqi", "楼宇平均CQI");
        map.put("cqidiliu", "楼宇CQI低于6比例");
        map.put("cqidiliucaiyang", "楼宇CQI低于6的采样点数");
        map.put("avgsinr", "楼宇平均上行SINR");
        map.put("avgsinrdiling", "楼宇平均上行SINR低于0比例");
        map.put("neisinrdiling", "楼宇内上行SINR低于0采样点数");
        map.put("fugaikongdong", "楼宇覆盖空洞比例");
        map.put("chongdiefugai", "楼宇重叠覆盖比例");
        map.put("zhufugaixiaoquid", "主覆盖小区ID");
        map.put("zhufugaixiaoquming", "主覆盖小区名");
        map.put("zhufugaicaiyangdianzongshu", "主覆盖小区采样点总数");
        map.put("zhufugaixiaoquavgrsrp", "主覆盖小区平均rsrp");
        map.put("wentixiaoquid", "问题小区ID");
        map.put("vccellname", "问题小区名");
        map.put("wentixiaoquganraogailv", "问题小区干扰概率");
        map.put("wentixiaoquavgcqi", "问题小区CQI的平均值");
        map.put("wentixiaoqucqidiliucaiyang", "问题小区CQI低于6采样点数");
        map.put("wenticqidiliubili", "问题小区CQI低于6采样点比例");
        map.put("wentixiaoqupci", "问题小区PCI");
        map.put("ganraoxiaoqupci", "干扰小区PCI");
        map.put("ganraoxiaoqursrp", "干扰小区RSRP");
        map.put("wentixiaoqufugailv", "问题小区覆盖率");
        map.put("wentixiaoquavgrsrp", "问题小区平均RSRP");
        map.put("wentixiaoquavgta", "问题小区平均TA");
        map.put("wentixiaoquphr", "问题小区平均PHR");
        map.put("wentixiaoqufugaikongdong", "问题小区覆盖空洞");
        map.put("wentixiaoquchongdiefugai", "问题小区重叠覆盖");

        list.add(map);
        return list;

    }


    /**
     * 楼宇SINR分析列表
     * @return
     */
    public List<Map> floorQuestionExport5(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("rownum", "编号");
        map.put("vccityname", "城市");
        map.put("jingdu", "经度");
        map.put("weidu", "纬度");
        map.put("louyuriqi", "日期");
        map.put("nowyear", "年");
        map.put("nowmonth", "月");
        map.put("nowday", "日");
        map.put("louyushijian", "时");
        map.put("wanggea", "网格A");
        map.put("wanggeb", "网格B");
        map.put("wanggec", "网格C");
        map.put("vcbuildingname", "楼宇名称");
        map.put("wentileixing", "问题类型");
        map.put("louyubianhao", "楼宇ID");
        map.put("usershu", "用户数");
        map.put("louyucaiyangdianzongshu", "楼宇采样点总数");
        map.put("avgrsrp", "楼宇平均RSRP");
        map.put("louyufugailv", "楼宇覆盖率");
        map.put("ruocaiyangdianshu", "楼宇弱覆盖采样点数");
        map.put("avgrsrq", "楼宇平均RSRQ");
        map.put("louyuganraogailv", "楼宇干扰概率");
        map.put("louyumosancaiyang", "楼宇模三干扰采样点数");
        map.put("avgcqi", "楼宇平均CQI");
        map.put("cqidiliu", "楼宇CQI低于6比例");
        map.put("cqidiliucaiyang", "楼宇CQI低于6的采样点数");
        map.put("avgsinr", "楼宇平均上行SINR");
        map.put("avgsinrdiling", "楼宇平均上行SINR低于0比例");
        map.put("neisinrdiling", "楼宇内上行SINR低于0采样点数");
        map.put("fugaikongdong", "楼宇覆盖空洞比例");
        map.put("chongdiefugai", "楼宇重叠覆盖比例");
        map.put("zhufugaixiaoquid", "主覆盖小区ID");
        map.put("zhufugaixiaoquming", "主覆盖小区名");
        map.put("zhufugaicaiyangdianzongshu", "主覆盖小区采样点总数");
        map.put("zhufugaixiaoquavgrsrp", "主覆盖小区平均rsrp");
        map.put("wentixiaoquid", "问题小区ID");
        map.put("vccellname", "问题小区名");
        map.put("wentixiaoquganraogailv", "问题小区干扰概率");
        map.put("wentixiaoquavgsinr", "问题上行SINR平均值");
        map.put("wentixiaoquavgsinrdiling", "问题小区上行SINR低于0采样点比例");
        map.put("wentixiaoqudisinrdianshu", "问题小区上行SINR低于0采样点数");
        map.put("wentixiaoqufugailv", "问题小区覆盖率");
        map.put("ganraoxiaoquavgrsrp", "干扰小区平均RSRP");
        map.put("wentixiaoquavgta", "问题小区平均TA");
        map.put("wentixiaoquphr", "问题小区平均PHR");
        map.put("wentixiaoqufugaikongdong", "问题小区覆盖空洞");
        map.put("wentixiaoquchongdiefugai", "问题小区重叠覆盖");

        list.add(map);
        return list;

    }


    /**
     * 小区指标列表
     * @return
     */
    public List<Map> exportCellTargerExcel2(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("intdateid", "日期");
        map.put("vccityname", "城市");
        map.put("inthour", "时段");
        map.put("vccellname", "小区名称");
        map.put("xiaoquid", "小区ID");
        map.put("xiaoqucaiyangdianzongshu", "小区采样点总数");
        map.put("avgrsrp", "平均RSRQ");
        map.put("xiaoqufugailv", "小区覆盖率");
        map.put("ruocaiyangdianshu", "小区弱覆盖采样点数");
        map.put("avgrsrq", "平均RSRQ");
        map.put("louyuganraogailv", "小区干扰概率");
        map.put("louyumosancaiyang", "小区模三干扰概率");
        map.put("avgcqi", "平均CQI");
        map.put("cqidiliu", "小区CQI低于6比例");
        map.put("cqidiliucaiyang", "小区CQI低于6的采样点数");
        map.put("avgsinr", "平均SINR");
        map.put("avgsinrdiling", "小区平均上行SINR低于0比例");
        map.put("neisinrdiling", "小区内上行SINR低于0采样点数");
        map.put("fugaikongdong", "小区覆盖空洞比例");
        map.put("chongdiefugai", "小区重叠覆盖比例");


        list.add(map);
        return list;

    }

    /**
     * 小区指标列表
     * @return
     */
    public List<Map> getVolteProvinceListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("cityid", "城市");
        map.put("intgridid", "网格");
        map.put("cellid", "小区ID");
        map.put("lonb", "小区经度");
        map.put("latb", "小区纬度");
        map.put("usercount", "用户数");
        map.put("registersuccrate", "注册成功率");
        map.put("hujiaorate", "呼叫接通率");
        map.put("shihurate", "始呼接通率");
        map.put("droprate", "Volte掉话率");
        map.put("esrvccsuccrate", "Esrvcc切换成功率");
        map.put("esrvcclatency", "Esrvcc切换延时");
        map.put("avgesrvcclatency", "Esrvcc媒体切换时长");
        map.put("mos3rate", "Mos3.0占比");
        map.put("valllatency", "始呼建立时延v-all");
        map.put("compare", "对比");
        list.add(map);
        return list;
    }

    /**
     * CQI问题列表GRID号,问题路段,dfrom_to,CQI采样点,CQI均值,低CQI占比,MR采样点,RSRP均值,RSRQ均值,覆盖问题点,
     模三干扰强度,干扰问题点,flong,flat
     * @return
     */
    public List<Map> getGtCqiListForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("grid号", "GRID号");
        map.put("问题路段", "问题路段");
        map.put("dfrom_to", "行驶方向");
        map.put("cqi采样点", "CQI采样点");
        map.put("cqi均值", "CQI均值");
        map.put("低cqi占比", "低CQI占比");
        map.put("mr采样点", "MR采样点");
        map.put("rsrp均值", "RSRP均值");
        map.put("rsrq均值", "RSRQ均值");
        map.put("覆盖问题点", "覆盖问题点");
        map.put("模三干扰强度", "模三干扰强度");
        map.put("干扰问题点", "干扰问题点");
        map.put("flong", "经度");
        map.put("flat", "纬度");
        list.add(map);
        return list;
    }

    public List<Map> getUserExperForXls(){
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();
        map.put("kpiMasterField", "主指标");
        map.put("cityid", "城市ID");
        map.put("gridid", "网格ID");
        map.put("cellID", "小区ID");
        map.put("cellName", "小区名");
        map.put("pci", "PCI");
        map.put("powerInfo", "发射功率");
        map.put("pcarrierfr", "频点");
        map.put("lonb", "经度");
        map.put("latb", "纬度");
        map.put("azimuth", "方向角");
        map.put("小区数", "小区数");
        map.put("vccity", "城市名");
        map.put("RSRP均值dBm", "RSRP均值");
        map.put("RSRQ均值dB", "RSRQ均值");
        list.add(map);
        return list;
    }
}
