package com.etone.project.modules.lte.manager.impl;

import com.etone.project.base.support.BaseManager;
import com.etone.project.core.db.CustomerContextHolder;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteMod3Mapper;
import com.etone.project.modules.lte.manager.ILteMod3Manager;
import com.etone.project.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rock on 2015/8/11.
 */

@Service
@Transactional
public class LteMod3Manager extends BaseManager<Map, Long> implements ILteMod3Manager{

    @Autowired
    private LteMod3Mapper lteMod3Mapper;

    @Override
    public void setRepository() {
        // TODO Auto-generated method stub
    }

    /**
     * 模三干扰小区列表查询
     * @param criteria
     * @return
     */
    @Override
    public PageResult<Map> findMod3CellList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_cause_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new PageResult<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//         //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> actual = lteMod3Mapper.findMod3CellList(criteria);
        int total = lteMod3Mapper.countMod3CellList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return page;
    }

    /**
     * 模三小区占比-全网列表
     * @param criteria
     * @return
     */
    @Override
    public PageResult<Map> findMod3WholeCellList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_wholeCell_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new PageResult<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> actual = lteMod3Mapper.findMod3WholeCellList(criteria);
        int total = lteMod3Mapper.countMode3WholeCellList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return page;
    }

    /**
     * 模三小区占比-行政区列表
     * @param criteria
     * @return
     */
    @Override
    public PageResult<Map> findMod3AreaCellList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_areaCell_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new PageResult<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> actual = lteMod3Mapper.findMod3AreaCellList(criteria);
        int total = lteMod3Mapper.countMode3AreaCellList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return page;
    }

    /**
     * 模三小区占比-网格列表
     * @param criteria
     * @return
     */
    @Override
    public PageResult<Map> findMod3GridCellList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        String timeStart = criteria.get("timeStart").toString();
        String timeEnd = criteria.get("timeEnd").toString();
        String sqlView="";
        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
        List<Map> tables = getTableName(criteria);
        //遍历表，创建视图
        //初始化视图名
        String vwuu_cause = "vwuu_gridCell_" + System.currentTimeMillis();
        for (int i=0;i<tables.size();i++){
            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
            if(sqlView == "" && t >= startfix && t <= endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString();
            }else if(t >= startfix && t <= endfix){
                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
            }
        }
        if(sqlView == "") return new PageResult<Map>();
        criteria.put("vwuu_cause",vwuu_cause);
        criteria.put("sqlView",sqlView);
        //创建视图
        lteMod3Mapper.createView(criteria);
        List<Map> actual = lteMod3Mapper.findMod3GridCellList(criteria);
        int total = lteMod3Mapper.countMode3GridCellList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
        lteMod3Mapper.dropView(criteria);
        return page;
    }

    /**
     * 模三区间分布-全网列表
     * @param criteria
     * @return
     */
    @Override
    public PageResult<Map> findMod3WholeList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_wholeList_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new PageResult<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> actual = lteMod3Mapper.findMod3WholeList(criteria);
        int total = lteMod3Mapper.countMode3WholeList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return page;
    }

    /**
     * 模三区间分布-行政区列表
     * @param criteria
     * @return
     */
    @Override
    public PageResult<Map> findMod3AreaList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_areaList_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new PageResult<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> actual = lteMod3Mapper.findMod3AreaList(criteria);
        int total = lteMod3Mapper.countMode3AreaList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return page;
    }

    /**
     * 模三区间分布-网格列表
     * @param criteria
     * @return
     */
    @Override
    public PageResult<Map> findMod3GridList(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_gridList_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new PageResult<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> actual = lteMod3Mapper.findMod3GridList(criteria);
        int total = lteMod3Mapper.countMode3GridList(criteria);
        PageResult page = new PageResult(criteria.getPageNo(),criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return page;
    }

    /**全网柱状图*/
    @Override
    public List<Map> wholeNetColumn(QueryCriteria criteria){
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_wholeNetColumn_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new ArrayList<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> list = lteMod3Mapper.wholeNetColumn(criteria);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return  list;
    }

    /**行政区柱状图*/
    @Override
    public List<Map> areaColumn(QueryCriteria criteria){
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_areaColumn_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//         /*   int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString() + " union all";
//            }else if(t==endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }*/
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new ArrayList<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> list = lteMod3Mapper.areaColumn(criteria);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return  list;
    }

    /**网格柱状图*/
    @Override
    public List<Map> gridColumn(QueryCriteria criteria){
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_gridColumn_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new ArrayList<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> list = lteMod3Mapper.gridColumn(criteria);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return  list;
    }

    /**天粒度模三干扰趋势分析*/
    @Override
    public List<Map> getDayLine(QueryCriteria criteria){
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        String timeStart = criteria.get("timeStart").toString();
        String timeEnd = criteria.get("timeEnd").toString();
        String sqlView="";
        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
        List<Map> tables = getTableName(criteria);
        //遍历表，创建视图
        //初始化视图名
        String vwuu_cause = "vwuu_dayline_" + System.currentTimeMillis();
        for (int i=0;i<tables.size();i++){
            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
            if(sqlView == "" && t >= startfix && t <= endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString();
            }else if(t >= startfix && t <= endfix){
                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
            }
        }
        if(sqlView == "") return new ArrayList<Map>();
        criteria.put("vwuu_cause",vwuu_cause);
        criteria.put("sqlView",sqlView);
        //创建视图
        lteMod3Mapper.createView(criteria);
        List<Map> list = lteMod3Mapper.getDayLine(criteria);
        //删除视图
        lteMod3Mapper.dropView(criteria);
        return  list;
    }

    /**小时粒度模三干扰趋势分析*/
    @Override
    public List<Map> getHourLine(QueryCriteria criteria){
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
//        String timeStart = criteria.get("timeStart").toString();
//        String timeEnd = criteria.get("timeEnd").toString();
//        String sqlView="";
//        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
//        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
//        List<Map> tables = getTableName(criteria);
//        //遍历表，创建视图
//        //初始化视图名
//        String vwuu_cause = "vwuu_hourline_" + System.currentTimeMillis();
//        for (int i=0;i<tables.size();i++){
//            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[2]);
//            if(sqlView == "" && t >= startfix && t <= endfix){
//                sqlView += " select * from " + tables.get(i).get("tablename").toString();
//            }else if(t >= startfix && t <= endfix){
//                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
//            }
//        }
//        if(sqlView == "") return new ArrayList<Map>();
//        criteria.put("vwuu_cause",vwuu_cause);
//        criteria.put("sqlView",sqlView);
//        //创建视图
//        lteMod3Mapper.createView(criteria);
        criteria.put("tableName",dayOrMonthTable(criteria));
        List<Map> list = lteMod3Mapper.getHourLine(criteria);
        //删除视图
//        lteMod3Mapper.dropView(criteria);
        return  list;
    }

    /**小时粒度模三干扰趋势分析*/
    @Override
    public List<Map> findNcCellList(QueryCriteria criteria){
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        String timeStart = criteria.get("timeStart").toString();
        String timeEnd = criteria.get("timeEnd").toString();
        String sqlView="";
        String tableName = "stbscnckpi"+criteria.get("userID");
        criteria.put("tableName",tableName);
        String format = getMatchGroup("{YEAR}{MONTH}{DAY}");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        int startfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeStart)));
        int endfix = Integer.parseInt(dateFormat.format(DateUtils.getDate(timeEnd)));
        List<Map> tables = getTables(criteria);
        //遍历表，创建视图
        //初始化视图名
        String vwuu_cause = "vwuu_nc_" + System.currentTimeMillis();
        for (int i=0;i<tables.size();i++){
            int t = Integer.parseInt(tables.get(i).get("tablename").toString().split("_")[1]);
            if(sqlView == "" && t >= startfix && t <= endfix){
                sqlView += " select * from " + tables.get(i).get("tablename").toString();
            }else if(t >= startfix && t <= endfix){
                sqlView += " union all select * from " + tables.get(i).get("tablename").toString() ;
            }
        }
        if(sqlView == "") return new ArrayList<Map>();
        criteria.put("vwuu_cause",vwuu_cause);
        criteria.put("sqlView",sqlView);
        //创建视图
        lteMod3Mapper.createView(criteria);
        List<Map> list = lteMod3Mapper.findNcCellList(criteria);
        //删除视图
        lteMod3Mapper.dropView(criteria);
        return  list;
    }

    /**
     * 获取表名
     * @param criteria
     * @return
     */
    @Override
    public List<Map> getTableName(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        List<Map> result = lteMod3Mapper.getTableName(criteria);
        return result;
    }

    /**
     * 判断是查月表还是天表
     * @param criteria
     * @return
     */
    public String dayOrMonthTable(QueryCriteria criteria) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition parsePosition=new ParsePosition(0);
        Date dateEnd=format.parse(criteria.get("timeEnd").toString(),parsePosition);
        ParsePosition parsePosition1=new ParsePosition(0);
        Date dateStart=format.parse(criteria.get("timeStart").toString(),parsePosition1);
        if((dateEnd.getTime()-dateStart.getTime())>1000*60*60*24*2L){
            criteria.put("table","month");
            return "f_et_sca_uemr_c_month";
        }else{
            criteria.put("table","day");
            return "f_et_sca_uemr_c_day";
        }
    }

    /**
     * 获取表名
     * @param criteria
     * @return
     */
    public List<Map> getTables(QueryCriteria criteria) {
        //切换GP数据库
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        List<Map> result = lteMod3Mapper.getTables(criteria);
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
