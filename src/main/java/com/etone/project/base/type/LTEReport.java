package com.etone.project.base.type;

import com.etone.project.core.model.QueryCriteria;
import com.etone.project.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该类描述
 *
 * @author <a href="mailto:417877417@qq.com">menergy</a>
 *         DateTime: 14-10-13  下午3:21
 */
public class LTEReport {

    public  static TaskReport getDefineReport(TaskReport report, QueryCriteria criteria ) {


        //分析语句
        TaskReport t = analyzeSQL(report,criteria);
        //检查语法
        //checkGrammar(query);
        //logger.info("返回数据！");
        return t;
    }

    /**
     * 分析查询语句
     * @param report,timeStart,timeEnd
     */
    public   static TaskReport analyzeSQL(TaskReport report, QueryCriteria criteria) {
        TaskReport t  = new TaskReport();
        Map<String, String> marksToViewNameMap = new HashMap<String, String>();
        Map<String, List<String>> viewNameToTableNamesMap = new HashMap<String, List<String>>();
        //String errorInfo = "";
        String userId = criteria.get("id").toString().trim();
        if("-1".equals(userId.trim())){
            userId = "";
        }
        Map<String,String> dimenMap = new HashMap<String, String>();
        Map<String,String> FieldMap = new HashMap<String, String>();
        //sgwmmeip":null,"sgwmme":"GZSAEGW1301","sgwmmeid"
        FieldMap.put("intMMEGI+intMMEC","sgwmmeid");
        FieldMap.put("vcSgsnSgwIP","sgwmmeip");
        FieldMap.put("vcSgwIP","sgwmmeip");
        FieldMap.put("vcMmeIP","sgwmmeip");
        FieldMap.put("vcOriginHost||vcDestHost","sgwmmeip");
        FieldMap.put("vcsrcip||vcdstip","sgwmmeip");
        FieldMap.put("vcMmeName","sgwmme");
        FieldMap.put("vcSgw","sgwmme");
        FieldMap.put("intTAC","tac");
        FieldMap.put("intEnbID","enb");
        FieldMap.put("intEnbID+intCI","cell");
        FieldMap.put("intLacTac","tac");
        String dimen = "";
        String field = "";
        String mmeField = "";
        if(report.getSgwmmeField() !=null || !report.getSgwmmeField().equals("")){
            dimenMap.put("MME",report.getSgwmmeField().toString().trim());
        }
        if(report.getEnbField() != null || !report.getEnbField().equals("")){
            dimenMap.put("ENB",report.getEnbField().toString().trim());
        }
        if(report.getTacField() != null || !report.getTacField().equals("")){
            dimenMap.put("TAC",report.getTacField().toString().trim());
        }
        if(report.getCellField() != null || !report.getCellField().equals("")){
            dimenMap.put("CELL",report.getCellField().toString().trim());
        }

       // dimen = query.root().find("areaRange").find("areaGran").getValue();
        field = dimenMap.get(dimen);
        if(dimen=="MME"){
            mmeField = FieldMap.get(field);
        }
        String reportSql =report.getSql().toString().trim();
        String startTimeStr = report.getTimeStart().toString().trim();
        String endTimeStr = report.getTimeEnd().toString().trim();
//        "areaRange":{"areaGran":"TAC","items":[{"sgwmme":["GZMME401Bzx"],"tac":"9454"},{"sgwmme":["GZMME401Bzx"],"tac":"9457"},{"sgwmme":["GZMME401Bzx"],"tac":"9459"}]},

        List<String> ipList = new ArrayList<String>();

            String areaGran = "";
            List<Map> areaMap = null;
            String area = "";

        String replaceSql="";

        Set<String> prefixs= getMatchersGroup(reportSql,"\\s+([\\w]+)\\.#AREA_FIELD_NAME",1);
        if(prefixs==null||prefixs.size()==0){
            replaceSql = formatParameter(field,"");
        }else {
            for(String prefix : prefixs) {
                replaceSql = formatParameter(field,prefix);
            }
        }
        //String areaFieldName = areaGranMap.get(areaGran);
        reportSql = reportSql.replaceAll("\\s+[\\S]*#AREA_FIELD_NAME\\s+in\\s*\\(\\s*#AREA\\s*\\)",replaceSql);
        if(area!=null&&!"".equals(area)){
            area = area.substring(0,area.length()-1);
            reportSql = reportSql.replace("#AREA",area);
        }

    else if(reportSql.contains("#AREA_FIELD_NAME")&&reportSql.contains("#AREA")) {
        reportSql=reportSql.replaceAll("\\s+[\\S]*#AREA_FIELD_NAME"," 1");
        reportSql = reportSql.replace("#AREA","1");
    }
    int dimenID ;
    String dimenName = "";


    Date startTime = null;
    Date endTime = null;

        try {
            startTime = DateUtils.convertStringToDate(startTimeStr);
            endTime = DateUtils.convertStringToDate(endTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    //匹配表名标记字符串
    String regex = "\\$\\w+\\$";
    Set<String> tableNameMarks = getMatchers(reportSql, regex);
    //处理每一个匹配的表名
   /* if(tableNameMarks != null && !tableNameMarks.isEmpty()){
        for(String tableNameMark : tableNameMarks){
            //获取表名前缀
            String tableNamePrefix = tableNameMark.trim().substring(1, tableNameMark.length() - 1);
            //获取表策略
            if(report.getTableStrategy()!=null || !report.getTableStrategy().equals("")){
                String tableStrategy = report.getTableStrategy().toString();
                //获取时间范围内合法格式的表名
                List<String> tableNames = calcTables(startTime, endTime, tableNamePrefix + userId, getDateFormatD(tableStrategy));
                // 效验在数据库中存在的表
                List<String> vaildTables = LTEDefineReportDao.getValidTables(tableNames);
                if(vaildTables != null && !vaildTables.isEmpty()){
                    //视图名
                    String viewName = "vw" + tableNamePrefix + System.currentTimeMillis();
                    //表名标记-视图名
                    marksToViewNameMap.put(tableNameMark, viewName);
                    //视图名-时间范围内合法表集合
                    viewNameToTableNamesMap.put(viewName, vaildTables);
                }else{
                    for(String table: tableNames){
                        errorInfo = errorInfo + "数据库不存在表:"+table+"\n";
                    }
                }
            }else{
                String tableStrategy = LTEPmcDao.getTableStrategy(tableNamePrefix);
                if(tableStrategy != null && !"".equals(tableStrategy.trim())){
                    //获取时间范围内合法格式的表名
                    List<String> tableNames = calcTables(startTime, endTime, tableNamePrefix + userId, getDateFormat(tableStrategy));
                    // 效验在数据库中存在的表
                    List<String> vaildTables = LTEDefineReportDao.getValidTables(tableNames);
                    if(vaildTables != null && !vaildTables.isEmpty()){
                        //视图名
                        String viewName = "vw" + tableNamePrefix + System.currentTimeMillis();
                        //表名标记-视图名
                        marksToViewNameMap.put(tableNameMark, viewName);
                        //视图名-时间范围内合法表集合
                        viewNameToTableNamesMap.put(viewName, vaildTables);
                    } else {
                        for(String table: tableNames){
                            errorInfo = errorInfo + "数据库不存在表:"+table+"\n";
                        }
                    }
                }
            }
        }
    }*/

    //替换查询语句中标志表名为对应时间范围内该表的视图
    if(marksToViewNameMap != null && !marksToViewNameMap.isEmpty()){
        for(String tableNameMark : marksToViewNameMap.keySet()){
            reportSql = reportSql.replace(tableNameMark, marksToViewNameMap.get(tableNameMark));
        }
    }
    //替换查询时间标志字符串
    reportSql = reportSql.replace("#STIME", " '"+startTimeStr+"' ");
    reportSql = reportSql.replace("#ETIME", " '"+endTimeStr+"' ");

    //存放单SQL语句
    List<String> sqlList = new ArrayList<String>();
    //数据查询语句指针
    int dataSelectSqlPointer = -1;
    //数据查询语句计数
    int dataSelectSqlCount = 0;

    //增加创建视图语句
    if(viewNameToTableNamesMap != null && !viewNameToTableNamesMap.isEmpty()){
        for(String viewName : viewNameToTableNamesMap.keySet()){
            StringBuilder createViewSql = new StringBuilder("create view ");
            createViewSql.append(viewName);
            createViewSql.append(" as select * from ");
            List<String> tables = viewNameToTableNamesMap.get(viewName);
            if(tables != null && !tables.isEmpty()){
                for(String table : tables){
                    createViewSql.append(table);
                }
            }
            createViewSql.append(";");
            sqlList.add(createViewSql.toString());
        }
    }
    //获取自定义查询语句
   /* String[] sqls = reportSql.split(";");
    for(int i = 0; i < sqls.length; i++){
        String singleSql = sqls[i].replace("\n", " ").replace("\r", " ").trim();
        sqlList.add(singleSql);
        if(isDataSelect(singleSql)){
            dataSelectSqlPointer = sqlList.lastIndexOf(singleSql);
            dataSelectSqlCount ++;
        }
    }*/

    //增加删除视图语句
    if(viewNameToTableNamesMap != null && !viewNameToTableNamesMap.isEmpty()){
        for(String viewName : viewNameToTableNamesMap.keySet()){
            StringBuilder createViewSql = new StringBuilder("if exists( select 1 from sys.systable where table_name='");
            createViewSql.append(viewName);
            createViewSql.append("') then drop view ");
            createViewSql.append(viewName);
            createViewSql.append(" end if;");
            sqlList.add(createViewSql.toString());
        }
    }

    if(dataSelectSqlCount > 1){
        //logger.warn("返回多组查询结果，默认返回最后一组！");
    }

    //可执行查询语句列，顺序执行
   // query.root().add("sqlList", sqlList);
    //query.root().add("dataSelectSqlPointer", dataSelectSqlPointer);
        t.setSql(reportSql);
        return t;
    }


    public  static Set<String> getMatchersGroup(String targetString, String regex,int groupid){
        Set<String> set = new HashSet<String>();
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(targetString);
        while(m.find()){
            set.add(m.group(groupid));
        }
        return set;
    }

    public  static String formatParameter(String parameter,String prefix){
        String areaField = "";
        if(prefix!=null && !"".equals(prefix)){
            prefix += ".";
        }
        if(parameter.contains("+")){
            String[] parameters = parameter.split("\\+");
            for (String p : parameters){
                p = "cast(" + prefix + p +" as varchar(32)),";
                areaField += p;
            }
            areaField = " string("+areaField.substring(0,areaField.length()-1)+") in (#AREA)";
        }else if(parameter.contains("||")){
            String[] parameters = parameter.split("\\|\\|");
            for (String p : parameters){
                p = " cast(" + prefix + p +" as varchar(32)) in (#AREA)|";
                areaField += p;
            }
            areaField = areaField.substring(0,areaField.length()-1).replaceAll("\\|"," or ");
        }else{
            areaField = " cast(" + prefix + parameter +" as varchar(32)) in (#AREA)";
        }
        return areaField;
    }

    /**
     * 获取匹配结果
     *
     * @return
     */
    public static  Set<String> getMatchers(String targetString, String regex){
        Set<String> set = new HashSet<String>();
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(targetString);
        while(m.find()){
            set.add(m.group());
        }
        return set;
    }

    /**
     * 计算合法的表集合，用于构成视图
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param prefix    表前缀
     * @param suffix    表后缀
     * @return 表集合
     */
    public List<String> calcTables(Date startTime, Date endTime, String prefix, String suffix) {
        List<String> actual = new ArrayList<String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat(suffix);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        Date current = calendar.getTime();
        int CalendarType = getCalenderType(suffix);
        while (current.compareTo(endTime) <= 0) {
            String compileSuffix = dateFormat.format(current);
            String tableName = String.format("%s_%s", prefix, compileSuffix);
            actual.add(tableName);
            calendar.add(CalendarType, 1);
            current = calendar.getTime();
        }
        return actual;
    }
    /**
     * 获得分表策略
     *
     * @param suffix 表后缀
     * @return 分表策略
     */
    public int getCalenderType(String suffix) {
        int actual = 0;
        if (suffix.endsWith("yyyy")) {
            actual = Calendar.YEAR;
        }
        if (suffix.endsWith("MM")) {
            actual = Calendar.MONTH;
        }
        if (suffix.endsWith("dd")) {
            actual = Calendar.DATE;
        }
        if (suffix.endsWith("HH")) {
            actual = Calendar.HOUR;
        }
        return actual;
    }

    /**
     * 获取表时间后缀
     *
     * @param tableStrategy 表策略，格式：{YEAR}{MONTH}{DAY}{HOUR}
     * @return 表时间后缀
     */
    public String getDateFormat(String tableStrategy) {
        String dateformat = "";
        if (tableStrategy.endsWith("{YEAR}")) {
            dateformat = "yyyy";
        }
        if (tableStrategy.endsWith("{MONTH}")) {
            dateformat = "yyyyMM";
        }
        if (tableStrategy.endsWith("{DAY}")) {
            dateformat = "yyyyMMdd";
        }
        if (tableStrategy.endsWith("{HOUR}")) {
            dateformat = "yyyyMMddHH";
        }
        return dateformat;
    }

    public String getDateFormatD(String tableStrategy) {
        String dateformat = "";
        if ("m".equals(tableStrategy)) {
            dateformat = "yyyyMM";
        }
        if ("d".equals(tableStrategy)) {
            dateformat = "yyyyMMdd";
        }
        if ("h".equals(tableStrategy)) {
            dateformat = "yyyyMMddHH";
        }
        return dateformat;
    }

}
