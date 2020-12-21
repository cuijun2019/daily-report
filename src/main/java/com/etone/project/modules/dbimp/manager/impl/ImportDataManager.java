package com.etone.project.modules.dbimp.manager.impl;

import com.etone.project.base.entity.share.User;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.dbimp.dao.ILoadTableDao;
import com.etone.project.modules.dbimp.dao.ImportDao;
import com.etone.project.modules.dbimp.dao.ImportLogDao;
import com.etone.project.modules.dbimp.manager.IImportDataManager;
import com.etone.project.utils.DataTypeUtil;
import com.etone.project.utils.ImportDetail;
import com.etone.project.utils.ImportTable;
import com.etone.project.utils.ImportUtil;
import com.etone.project.utils.SQLAdapter;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@Service
@Transactional
public class ImportDataManager implements IImportDataManager {

    public static final Logger logger = LoggerFactory.getLogger(ImportDataManager.class);
    @Autowired
    private ImportDao importDao;
    @Autowired
    private ILoadTableDao loadTableDao;
    @Autowired
    private ImportLogDao importLogDao;

    @Override
    public Map<String, String> importData(InputStream inputStream, String typeValue, String fileName, User user, String isDelete) {

        ImportThread importThread = new ImportThread(inputStream, fileName, loadTableDao, importLogDao);
        
        importThread.setUserName(user.account);
        importThread.setImprotDao(importDao);
        importThread.setIsDelete(isDelete);
        importThread.userId = user.id;
        importThread.setImportDataManager(this);

        new Thread(importThread).start();

        Map<String, String> resultMap = new HashMap();

        resultMap.put("name", fileName + "已经上传,处理进度:");
        resultMap.put("size", "size");
        resultMap.put("url", "SERVLET_NAME?getfile=fileName");
        resultMap.put("thumbnail_url", "SERVLET_NAME?getthumb=fileName");
        resultMap.put("delete_url", "SERVLET_NAME?delfile=fileName");
        resultMap.put("delete_type", "DELETE");
        resultMap.put("importKey", importThread.start + "");

        return resultMap;
    }

    /**
     * 查询排除流量均衡的导入日志
     * @param criteria
     * @param userId
     * @return 
     */
    @Override
    public PageResult<Map> queryImportLog(QueryCriteria criteria, Long userId) {
        List<Map> actual;
        int total;

        
        String sql1 = "  SELECT id,importTime,entityName,sum(importRowCount) importRowCount,userName,msg FROM "
                + "( SELECT * FROM `tbImportLog` "
                + "WHERE (entityName='Mre' or "
                + "entityName='Mro' or "
                + "entityName='PowerHeadRoom' or "
                + "entityName='PrachTDD' or "
                
                + "entityName='ReceivedIPower' or "
                + "entityName='RSRP' or "
                + "entityName='RSRQ' or "
                + "entityName='SinrUL' or "
                
                + "entityName='DtbHistoricalAlarm' or "
                + "entityName='DtbWorkerParticipationFrequency' or "
                + "entityName='EUtranCellTDD' or "
                + "entityName='EUtranRelation' or "
                + "entityName='EUtranRelationHour' or "
                + "entityName='ServerCellHour') and userid=" + userId
                + " ORDER BY importTime DESC ) t GROUP BY entityName ORDER BY importTime DESC";
        String sql2 = " select count(1) FROM ( "
                + "SELECT id,importTime,entityName,importRowCount,userName,msg FROM "
                + "( SELECT * FROM `tbImportLog` "
                + "WHERE (entityName='Mre' or "
                + "entityName='Mro' or "
                + "entityName='PowerHeadRoom' or "
                + "entityName='PrachTDD' or "
                
                + "entityName='ReceivedIPower' or "
                + "entityName='RSRP' or "
                + "entityName='RSRQ' or "
                + "entityName='SinrUL' or "
                
                + "entityName='DtbHistoricalAlarm' or "
                + "entityName='DtbWorkerParticipationFrequency' or "
                + "entityName='EUtranCellTDD' or "
                + "entityName='EUtranRelation' or "
                + "entityName='EUtranRelationHour' or "
                + "entityName='ServerCellHour') and userId=" + userId
                + " ORDER BY importTime DESC ) t GROUP BY entityName ORDER BY importTime DESC ) t ";
        criteria.put("sql", sql1);
        actual = this.importDao.query(criteria);
        
        for(Map<String, Object> map : actual) {
            
            try {
                map.put("name", DataTypeUtil.getNameByEntityName(map.get("entityName").toString()));
            } catch (Exception e) {
                logger.error("map==null? " + (map == null) + ", log Id:" + map.get("id"));
            }
        }

        criteria.put("sql", sql2);
        total = this.importDao.count(criteria);

        PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);

        return page;
    }
    
    /**
     * 查询流量均衡导入日志
     * @param criteria
     * @return 
     * 
     */
    @Override
    public PageResult<Map> queryTrafficImportLog(QueryCriteria criteria) {
        List<Map> actual;
        int total;

        String sql1 = "  SELECT id,importTime,entityName,sum(importRowCount) importRowCount,userName,msg FROM "
                + "( SELECT * FROM `tbImportLog` "
                + "WHERE entityName='DtbGSMEngineerInfoGT' OR "
                + "entityName='DtbTDEachOperation' OR "
                + "entityName='DtbTDEngineerInfoGT' OR "
                + "entityName='DtbWLANEngineerInfoGT' OR "
                
                + "entityName='FtbGSMMrGT' OR "
                + "entityName='FtbGsmKpiGTDay' OR  "
                + "entityName='FtbGsmKpiGTHour' OR "
                + "entityName='FtbPointsListGT' OR "
                
                + "entityName='FtbTDMrGT' OR "
                + "entityName='FtbTdKpiGTDay' OR "
                + "entityName='FtbTdKpiGTHour' OR "
                + "entityName='FtbWLANKpiDay'  OR "
                + "entityName='FtbPointsListGTHour' "
                        
                + " ORDER BY importTime DESC ) t GROUP BY entityName ORDER BY importTime DESC";
        String sql2 = " select count(1) FROM ( "
                + "SELECT id,importTime,entityName,importRowCount,userName,msg FROM "
                + "( SELECT * FROM `tbImportLog` "
                + "WHERE entityName='DtbGSMEngineerInfoGT' OR "
                + "entityName='DtbTDEachOperation' OR "
                + "entityName='DtbTDEngineerInfoGT' OR "
                + "entityName='DtbWLANEngineerInfoGT' OR "
                
                + "entityName='FtbGSMMrGT' OR "
                + "entityName='FtbGsmKpiGTDay' OR  "
                + "entityName='FtbGsmKpiGTHour' OR "
                + "entityName='FtbPointsListGT' OR "
                
                + "entityName='FtbTDMrGT' OR "
                + "entityName='FtbTdKpiGTDay' OR "
                + "entityName='FtbTdKpiGTHour' OR "
                + "entityName='FtbWLANKpiDay' OR "
                + "entityName='FtbPointsListGTHour' "
                + " ORDER BY importTime DESC ) t GROUP BY entityName ORDER BY importTime DESC ) t ";

        criteria.put("sql", sql1);
        actual = this.importDao.query(criteria);
        
        for(Map<String, Object> map : actual) {
            
            try {
                map.put("name", DataTypeUtil.getNameByEntityName(map.get("entityName").toString()));
            } catch (Exception e) {
                logger.error("map==null? " + (map == null) + ", log Id:" + map.get("id"));
            }
        }

        criteria.put("sql", sql2);
        total = this.importDao.count(criteria);

        PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);

        return page;
    }
    
    
    /**
     * 根据实体名查询导入日志
     * @param entityName
     * @return 
     */
    public List<Map> queryLogByEntity(String entityName,QueryCriteria cc) {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("entityName", entityName);
        criteria.put("userId", cc.get("userId"));
        List<Map> importLogMapList = importLogDao.query(criteria);
        
        return importLogMapList;
    }

    @Override
    public PageResult<Map> query(QueryCriteria criteria, String entityName) {
        List<Map> importLogMapList = this.queryLogByEntity(entityName, criteria);
        
        int total = 0;
        List<String> querySqlList = new ArrayList();
        Integer rowStart = criteria.getRowStart();
        Integer pageSize = criteria.getPageSize();
        
        for(Map importLogMap : importLogMapList) {
            Object tableNameObject = importLogMap.get("tableName");
            Object importRowCount = importLogMap.get("importRowCount");
            
            int rowCount;
            try {
                rowCount = Integer.parseInt(importRowCount.toString());
            } catch (Exception exception) {
                logger.debug("tableName:" + tableNameObject + " importRowCount:" + importRowCount + "  excetion:" + exception.getMessage());
                rowCount =0;
            }
            
            total += rowCount;
            
            
            if(rowCount <= rowStart) {
                rowStart -= rowCount;
                continue;
            }
            
            if(pageSize >= 0) {
                String sql = " select * from " + tableNameObject + " limit " + rowStart + ", " + pageSize;
                querySqlList.add(sql);
            }
            
            pageSize = pageSize - (rowCount-rowStart);
            rowStart = 0;
            
        }
        
        List<Map> actual = new ArrayList();
        
        for(String querySql : querySqlList) {
            QueryCriteria queryCriteria = new QueryCriteria();
            
            queryCriteria.put("sql", querySql);
            queryCriteria.setRowStart(null);
            queryCriteria.setPageSize(null);
            
            actual.addAll(this.importDao.query(queryCriteria));
        }

        PageResult page = new PageResult(criteria.getPageNo(), criteria.getPageSize());
        page.setResult(actual);
        page.setTotalItems(total);

        return page;
    }

    @Override
    public String queryImportDetail(Long importKey, Integer msgIndex) {
        if (importKey == null) {
            return "";
        }

        if (msgIndex == null) {
            msgIndex = -1;
        }

        StringBuilder msgSb = new StringBuilder();
        List<String> msgList = ImportDetail.importMsgListMap.get(importKey);
        if (msgList == null) {
            return msgSb.toString();
        }

        for (int i = msgIndex + 1; i < msgList.size(); i++) {
            msgSb.append(msgList.get(i)).append("\n<br />");
        }

        if (msgSb.length() > 0) {
            msgSb.deleteCharAt(msgSb.length() - 1);
        }

        return msgSb.toString();
    }
    
    /**
     * 查看导入处理进程
     * @param importKey
     * @param msgIndex
     * @return 
     */
    @Override
    public String queryImportPercent(Long importKey) {
        if (importKey == null) {
            return "100";
        }
        Object percent = ImportDetail.importPercentMap.get(importKey);
        
        if(percent != null && Double.parseDouble(percent.toString()) == 100) {
            ImportDetail.importPercentMap.remove(importKey);
        }
                
        return (percent == null? 100 : percent) + "";

    }

    /**
     * 清空表数据
     *
     * @param criteria
     * @param entityName
     * @param userId
     * @return
     */
    @Override
    public Object clear(QueryCriteria criteria, String entityName) {
        List<Map> importLogMapList = this.queryLogByEntity(entityName, criteria);
        try {
            
            //对于一个entity对应多个表的情况，就删除了那些分表，如果是一对一，那就清空它
            String sqlPrefix = importLogMapList.size() > 1? " drop table " : "truncate table ";
            
            for(Map importLogMap : importLogMapList) {
                Object tableNameObject = importLogMap.get("tableName");
                
                if(tableNameObject != null && !tableNameObject.toString().isEmpty()) {
                    
                    SQLAdapter sqlAdapter = new SQLAdapter(sqlPrefix + tableNameObject.toString());

                    this.importDao.clearTable(sqlAdapter);
                }
            }
            
        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }
    
    /**
     * 删除导入日志表中实体名为entityName导入记录
     * @param criteria
     * @param entityName
     * @return 
     */
    @Override
    public Object deleteImportLog(QueryCriteria criteria, String entityName) {
        try {
            String sql = "DELETE FROM tbImportLog WHERE entityName = '" + entityName + "' and userId=" + criteria.get("userId");
            
            criteria.put("sql", sql);
            this.importDao.executeSql(criteria);
        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }
}
