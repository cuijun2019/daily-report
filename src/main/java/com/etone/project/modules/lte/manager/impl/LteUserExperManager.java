package com.etone.project.modules.lte.manager.impl;

import com.etone.project.core.db.CustomerContextHolder;
import com.etone.project.core.db.factory.DaoFactory;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteMsgDao;
import com.etone.project.modules.lte.dao.LteUserExperMapper;
import com.etone.project.modules.lte.manager.ILteUserExperManager;
import com.etone.project.utils.DateUtils;
import com.google.common.collect.Lists;
import com.mchange.v1.identicator.IdHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class LteUserExperManager implements ILteUserExperManager {

    @Autowired
    private LteUserExperMapper lteUserExperMapper;
    private LteMsgDao lteMsgDao;
    /**
     * 查询指标树
     * @param criteria
     * @return
     */
    @Override
    public List<Map> findKpiTree(QueryCriteria criteria) {
        //切换MYSQl数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
        //CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        List<Map> actual = lteUserExperMapper.findKpiTree(criteria);
        return actual;
    }

    @Override
    public List<Map> findSlaveKpiTree(QueryCriteria criteria) {
        //切换MYSQl数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
        //CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        List<Map> actual = lteUserExperMapper.findSlaveKpiTree(criteria);
        return actual;
    }

    /**
     * 查询颜色区间
     * @param criteria
     * @return
     */
    public List<Map> findKpiColor(QueryCriteria criteria) {
        //切换MYSQl数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
        List<Map> actual = lteUserExperMapper.findKpiColor(criteria);
        return actual;
    }

    /**
     * 用户透视图查询
     * @param criteria
     * @return
     */
    @Override
    public Map<String,List> findUserMsgChart(QueryCriteria criteria) {
        return null;
    }

    /**
     * 用户透视图查询
     * @param criteria
     * @return
     */
    @Override
    public List<Map> findUserPieChart(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        String timeStart = criteria.get("timeStart").toString();
        String timeEnd = criteria.get("timeEnd").toString();
        String sqlView="";
        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
        int intKpiClassify = Integer.parseInt(criteria.get("intKpiClassify").toString());
        String tableName="";
//        if(intKpiClassify==1){
//            tableName = "stbuu_cell";
//        } else if (intKpiClassify==2){
//            tableName = "stbuu_cause";
//        } else if (intKpiClassify==3){
//            tableName = "stbx2_cell";
//        } else if (intKpiClassify==4){
//            tableName = "stbuuex_cell";
//        }
        tableName = "stbuu_imsi";
        criteria.put("tableName",tableName);
        List<Map> tables = getTableName(criteria);
        //遍历表，创建视图
        //初始化视图名
        String vwallkpi = "vwareakpi" + System.currentTimeMillis();
        for (int i=0;i<tables.size();i++){
            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
            if(t >= startfix && t < endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString() + " union all";
            }else if(t==endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString();
            }
        }
        criteria.put("vwallkpi",vwallkpi);
        criteria.put("sqlView",sqlView);
        //创建视图
        lteUserExperMapper.createView(criteria);

        //获取页面kpiid并组装kpiid
        String kpiId = criteria.get("master_kpi").toString();
        List<String> kpiList = Lists.newArrayList();
        kpiList.add(kpiId);
        //获取所有指标 kpiMasterField
        Map<String,Map> kpiMap = (Map)criteria.get("kpiMap");
        String vcPiece_1_min = "";
        String vcPiece_1_max = "";
        String vcPiece_2_min = "";
        String vcPiece_2_max = "";
        String vcPiece_3_min = "";
        String vcPiece_3_max = "";
        String vcPiece_4_min = "";
        String vcPiece_4_max = "";

        //组装SQL
        String sql = "",sql1 = "",allfield="";
        //页面指标范围
        for(String id :kpiList){
            Map kpi =  kpiMap.get(id);
            allfield +=  "\""+kpi.get("text") + "\",";
            sql1 += kpi.get("vcSelect") + " \"" +kpi.get("text") + "\",";
            vcPiece_1_min = kpi.get("vcPiece_1_min").toString();
            vcPiece_1_max = kpi.get("vcPiece_1_max").toString();
            vcPiece_2_min = kpi.get("vcPiece_2_min").toString();
            vcPiece_2_max = kpi.get("vcPiece_2_max").toString();
            vcPiece_3_min = kpi.get("vcPiece_3_min").toString();
            vcPiece_3_max = kpi.get("vcPiece_3_max").toString();
            vcPiece_4_min = kpi.get("vcPiece_4_min").toString();
            vcPiece_4_max = kpi.get("vcPiece_4_max").toString();
        }

        criteria.put("sql1",sql1.substring(0,sql1.length()-1));
        criteria.put("vcPiece_1_min",vcPiece_1_min);
        criteria.put("vcPiece_1_max",vcPiece_1_max);
        criteria.put("vcPiece_2_min",vcPiece_2_min);
        criteria.put("vcPiece_2_max",vcPiece_2_max);
        criteria.put("vcPiece_3_min",vcPiece_3_min);
        criteria.put("vcPiece_3_max",vcPiece_3_max);
        criteria.put("vcPiece_4_min",vcPiece_4_min);
        criteria.put("vcPiece_4_max",vcPiece_4_max);

        List<Map> list = lteUserExperMapper.findUserPieList(criteria);
        //删除视图
        lteUserExperMapper.dropView(criteria);
        return list;
    }

    /*
    * 行政区指标查询
    * */
    @Override
    public PageResult<Map> findAreaKpiList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        String timeStart = criteria.get("timeStart").toString();
        String timeEnd = criteria.get("timeEnd").toString();
        String sqlView="";
        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
        int intKpiClassify = Integer.parseInt(criteria.get("intKpiClassify").toString());
        String tableName="";
        if(intKpiClassify==1){
            tableName = "stbuu_cell";
        } else if (intKpiClassify==2){
            tableName = "stbuu_cause";
        } else if (intKpiClassify==3){
            tableName = "stbx2_cell";
        } else if (intKpiClassify==4){
            tableName = "stbuuex_cell";
        }
        criteria.put("tableName",tableName);
        List<Map> tables = getTableName(criteria);
        //遍历表，创建视图
        //初始化视图名
        String vwallkpi = "vwareakpi" + System.currentTimeMillis();
        for (int i=0;i<tables.size();i++){
            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
            if(t >= startfix && t < endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString() + " union all";
            }else if(t==endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString();
            }
        }
        criteria.put("vwallkpi",vwallkpi);
        criteria.put("sqlView",sqlView);
        //创建视图
        lteUserExperMapper.createView(criteria);

        //全选指标条件过滤根节点
        String slave_kpi = criteria.get("slave_kpi").toString();
        if (slave_kpi.indexOf("0")>=0){
            slave_kpi = slave_kpi.substring(slave_kpi.indexOf("0")+2,slave_kpi.length());
        }
        //获取页面kpiid并组装kpiid
        String kpiId = criteria.get("master_kpi").toString();
        String[] kpiIds = criteria.get("slave_kpi").toString().split(",");
        List<String> kpiList = Lists.newArrayList();
        kpiList.add(kpiId);
        for(int i = 0;i<kpiIds.length;i++){
            if(!kpiId.equals(kpiIds[i])){
                kpiList.add(kpiIds[i]);
            }
        }
        //获取所有指标 kpiMasterField
        Map<String,Map> kpiMap = (Map)criteria.get("kpiMap");

        //组装SQL
        String sql = "",sql1 = "",allfield="";
        //页面指标范围
        for(String id :kpiList){
            Map kpi =  kpiMap.get(id);
            allfield +=  "\""+kpi.get("text") + "\",";
            sql1 += kpi.get("vcSelect") + " \"" +kpi.get("text") + "\",";
        }

        criteria.put("sql1",sql1.substring(0,sql1.length()-1));

        List<Map> actual = lteUserExperMapper.findAreaKpiList(criteria);
        int total = lteUserExperMapper.countAreaKpiList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);;
        page.setTotalItems(total);
        //删除视图
        lteUserExperMapper.dropView(criteria);
        return page;
    }

    /*
    * 网格指标查询
    * */
    @Override
    public PageResult<Map> findGridKpiList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        String timeStart = criteria.get("timeStart").toString();
        String timeEnd = criteria.get("timeEnd").toString();
        String sqlView="";
        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
        int intKpiClassify = Integer.parseInt(criteria.get("intKpiClassify").toString());
        String tableName="";
        if(intKpiClassify==1){
            tableName = "stbuu_cell";
        } else if (intKpiClassify==2){
            tableName = "stbuu_cause";
        } else if (intKpiClassify==3){
            tableName = "stbx2_cell";
        } else if (intKpiClassify==4){
            tableName = "stbuuex_cell";
        }
        criteria.put("tableName",tableName);
        List<Map> tables = getTableName(criteria);
        //遍历表，创建视图
        //初始化视图名
        String vwallkpi = "vwgridkpi" + System.currentTimeMillis();
        for (int i=0;i<tables.size();i++){
            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
            if(t >= startfix && t < endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString() + " union all";
            }else if(t==endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString();
            }
        }
        criteria.put("vwallkpi",vwallkpi);
        criteria.put("sqlView",sqlView);
        //创建视图
        lteUserExperMapper.createView(criteria);

        //全选指标条件过滤根节点
        String slave_kpi = criteria.get("slave_kpi").toString();
        if (slave_kpi.indexOf("0")>=0){
            slave_kpi = slave_kpi.substring(slave_kpi.indexOf("0")+2,slave_kpi.length());
        }
        //获取页面kpiid并组装kpiid
        String kpiId = criteria.get("master_kpi").toString();
        String[] kpiIds = criteria.get("slave_kpi").toString().split(",");
        List<String> kpiList = Lists.newArrayList();
        kpiList.add(kpiId);
        for(int i = 0;i<kpiIds.length;i++){
            if(!kpiId.equals(kpiIds[i])){
                kpiList.add(kpiIds[i]);
            }
        }
        //获取所有指标 kpiMasterField
        Map<String,Map> kpiMap = (Map)criteria.get("kpiMap");

        //组装SQL
        String sql = "",sql1 = "",allfield="";
        //页面指标范围
        for(String id :kpiList){
            Map kpi =  kpiMap.get(id);
            allfield +=  "\""+kpi.get("text") + "\",";
            sql1 += kpi.get("vcSelect") + " \"" +kpi.get("text") + "\",";
        }

        criteria.put("sql1",sql1.substring(0,sql1.length()-1));
        List<Map> actual = lteUserExperMapper.findGridKpiList(criteria);
        int total = lteUserExperMapper.countGridKpiList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
        lteUserExperMapper.dropView(criteria);
        return page;
    }

    /*
    * 小区指标查询
    * */
    @Override
    public PageResult<Map> findCellKpiList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        String timeStart = criteria.get("timeStart").toString();
        String timeEnd = criteria.get("timeEnd").toString();
        String sqlView="";
        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
        int intKpiClassify = Integer.parseInt(criteria.get("intKpiClassify").toString());
        String tableName="";
        if(intKpiClassify==1){
            tableName = "stbuu_cell";
        } else if (intKpiClassify==2){
            tableName = "stbuu_cause";
        } else if (intKpiClassify==3){
            tableName = "stbx2_cell";
        } else if (intKpiClassify==4){
            tableName = "stbuuex_cell";
        }
        criteria.put("tableName",tableName);
        List<Map> tables = getTableName(criteria);
        //遍历表，创建视图
        //初始化视图名
        String vwallkpi = "vwcellkpi" + System.currentTimeMillis();
        for (int i=0;i<tables.size();i++){
            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
            if(t >= startfix && t < endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString() + " union all";
            }else if(t==endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString();
            }
        }
        criteria.put("vwallkpi",vwallkpi);
        criteria.put("sqlView",sqlView);
        //创建视图
        lteUserExperMapper.createView(criteria);

        //全选指标条件过滤根节点
        String slave_kpi = criteria.get("slave_kpi").toString();
        if (slave_kpi.indexOf("0")>=0){
            slave_kpi = slave_kpi.substring(slave_kpi.indexOf("0")+2,slave_kpi.length());
        }
        //获取页面kpiid并组装kpiid
        String kpiId = criteria.get("master_kpi").toString();
        String[] kpiIds = criteria.get("slave_kpi").toString().split(",");
        List<String> kpiList = Lists.newArrayList();
        kpiList.add(kpiId);
        for(int i = 0;i<kpiIds.length;i++){
            if(!kpiId.equals(kpiIds[i])){
                kpiList.add(kpiIds[i]);
            }
        }
        //获取所有指标 kpiMasterField
        Map<String,Map> kpiMap = (Map)criteria.get("kpiMap");

        //组装SQL
        String sql = "",sql1 = "",allfield="";
        //页面指标范围
        for(String id :kpiList){
            Map kpi =  kpiMap.get(id);
            allfield +=  "\""+kpi.get("text") + "\",";
            sql1 += kpi.get("vcSelect") + " \"" +kpi.get("text") + "\",";
        }
        Map master_kpi =  kpiMap.get(kpiId);
        sql1 += master_kpi.get("vcSelect") + " \"kpiMasterField\",";

        criteria.put("sql1",sql1.substring(0,sql1.length()-1));
        List<Map> actual = lteUserExperMapper.findCellKpiList(criteria);
        int total = lteUserExperMapper.countCellKpiList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
        lteUserExperMapper.dropView(criteria);
        return page;
    }

    /*
    * 获取表名
    * **/
    @Override
    public List<Map> getTableName(QueryCriteria criteria){
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        List<Map> result = lteUserExperMapper.getTableName(criteria);
        return result;
    }


    /**
     * 根据表策略vcStrategy获取日期结构
     *
     * @param formula
     *            公式
     * @return
     */
    private static String getMatchGroup(String formula) {
        List<String> counterNames = new ArrayList<String>();
        String regex = "\\{[^{}]*}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formula);
        while (matcher.find()) {
            String counterName = matcher.group();
            counterNames.add(counterName);
        }
        String[] actual = new String[counterNames.size()];
        counterNames.toArray(actual);

        StringBuilder sb = new StringBuilder();
        for (String item : actual) {
            if ("{YEAR}".equals(item)) {
                sb.append("yyyy");
            }
            if ("{MONTH}".equals(item)) {
                sb.append("MM");
            }
            if ("{DAY}".equals(item)) {
                sb.append("dd");
            }
            if ("{HOUR}".equals(item)) {
                sb.append("HH");
            }
        }
        return sb.toString();
    }
}
