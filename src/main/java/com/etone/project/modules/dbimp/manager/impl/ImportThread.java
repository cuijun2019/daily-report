package com.etone.project.modules.dbimp.manager.impl;

import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.dbimp.dao.ILoadTableDao;
import com.etone.project.modules.dbimp.dao.ImportDao;
import com.etone.project.modules.dbimp.dao.ImportLogDao;
import com.etone.project.modules.dbimp.entity.ImportLog;
import com.etone.project.modules.dbimp.entity.Mre;
import com.etone.project.modules.dbimp.manager.IImportDataManager;
import com.etone.project.utils.DataTypeUtil;
import com.etone.project.utils.FileData;
import com.etone.project.utils.ImportDetail;
import com.etone.project.utils.ImportFromCsvUtil;
import com.etone.project.utils.ImportMrUtil;
import com.etone.project.utils.ImportTable;
import com.etone.project.utils.ImportUtil;
import com.etone.project.utils.ReadZipUtil;
import com.etone.project.utils.XLS2CSV;
import com.etone.project.utils.XLSX2CSV;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *用于导入操作的线程
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class ImportThread implements Runnable{
    
    private static final Logger logger = LoggerFactory.getLogger(ImportThread.class);
    
    private IImportDataManager importDataManager;
    
    private ILoadTableDao loadTableDao;
    private ImportDao importDao;
    private ImportLogDao importLogDao;
    private Map<String, List> dataMap;
    
    private Map<String, StringBuilder> dataSbMap;
    
    /**
     * 用于记录表入库情况
     */
    private Map<String, ImportLog> importLogMap;
    private int loadTableSize = 5000;
    
    private Map<String, Long> importRecordMap;
    
    /**
     * 是否清空原数据，当isDelete=on 且 importLogMap中没有入库记录，就清空数据
     */
    private String isDelete;
        
    /**
     * 入库长度
     * dataSbMap里的StringBuilder长度大于1M就入库
     */
    private long loadTableLength = 10000000;
    public long start;
    
    public long userId;
    
    private String userName;
    
    private InputStream inputStream;
    private String fileName;
    
    private Long totalData;
    
    public ImportThread() {
        this.init();
        this.start = new Date().getTime() + Math.round(Math.random()*1000);
    }
    
    /**
     * 更新导入文件的处理进度（百分比）
     */
    public final void updateHandlePercent() {
        try {
            int available = this.inputStream.available();
            long percent = Math.round((totalData-available+0.0)/this.totalData*100);
            if(percent == 100) {
                percent = 99;
            }
            ImportDetail.importPercentMap.put(start, percent);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    
    public ImportThread(InputStream inputStream, String fileName, ILoadTableDao loadTableDao, ImportLogDao importLogDao) {
        this();
        
        this.inputStream = this.inputStreamToByteArrayInputStream(inputStream); //没有这样转化好像流会丢
        this.fileName = fileName;
        this.loadTableDao = loadTableDao;
        this.importLogDao = importLogDao;
        
        try {
            this.totalData = this.inputStream.available() + 0l;
            
            //更新处理进度
            updateHandlePercent();
            
            logger.info("总字节数（total dataCount）:" + this.totalData);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    
    private ByteArrayInputStream inputStreamToByteArrayInputStream(InputStream in) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1444];
            int byteread; 
            
            while ((byteread = in.read(buffer)) != -1) {
                baos.write(buffer, 0, byteread);
    //            System.out.println(bytesum);
    //            fs.write(buffer, 0, byteread);
            }
            try {
                in.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
            
            return new ByteArrayInputStream(baos.toByteArray());
            
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }
    
    private void init() {
        this.importLogMap = new HashMap();
        this.dataSbMap = new HashMap();
        this.importRecordMap = new HashMap();
    }
    
    @Override
    public void run() {
        if(this.inputStream == null) {
            logger.error("inputStream is null");
        }
        this.excuteImport();
        this.addImportMsg(null, null);
        try {
            ImportDetail.importPercentMap.put(start, 100l);
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage(), ex);
        }
        ImportDetail.importMsgListMap.remove(this.start);   //清除导入细节
        ImportDetail.importPercentMap.remove(this.start);
        

        try {
            if (this.inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ImportDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String excuteImport() {
        
        
        //选将文件存到磁盘上
//        String path = this.saveFile(inputStream, fileName);
//        try {
//            System.out.println("文件写到：" + path);
//            inputStream = new FileInputStream(path);
//        } catch (FileNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ImportDataManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
//        }

        Class mreClass = Mre.class;
        String entityPath = mreClass.getPackage().getName();

        List<String> resultList = new ArrayList();

        String suffix = this.getSuffix(fileName);
        if (suffix.toUpperCase().equals("ZIP")) {
            ReadZipUtil readZipUtil = new ReadZipUtil(inputStream, "gb2312");
            FileData nextFileData;

            while ((nextFileData = readZipUtil.getNextFileData()) != null) {

//                logger.info("unzip a file: " + nextFileData.fileName
//                        + ", handling it");
                updateHandlePercent();
                String suffix1 = this.getSuffix(nextFileData.fileName);
                
                //判断是否是GZ压缩文件
                if(suffix1.toUpperCase().equals("GZ")) {
                    GZIPInputStream gzipis = null;
                    try {
                        gzipis = new GZIPInputStream(new ByteArrayInputStream(nextFileData.data));
                        String handleImport = handleImport(entityPath, gzipis, nextFileData.fileName);
                        if (!handleImport.equals("success")) {
                            resultList.add(handleImport);
                        }
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(ImportDataManager.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            gzipis.close();
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(ImportDataManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                } else {
                    String handleImport = handleImport(entityPath, new ByteArrayInputStream(nextFileData.data), nextFileData.fileName);
                    if (!handleImport.equals("success")) {
                        resultList.add(handleImport);
                    }
                }
                
            }
            
            
        } else {
            if(suffix.toUpperCase().equals("GZ")) {
                try {
                    GZIPInputStream gzipis = new GZIPInputStream(inputStream);
                    String handleImport = handleImport(entityPath, gzipis, fileName);
                    if (!handleImport.equals("success")) {
                        resultList.add(handleImport);
                    }
                    
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(ImportDataManager.class.getName()).log(Level.SEVERE, null, ex);
                    return ex.getMessage();
                }
            }else {

                String handleImport = handleImport(entityPath, inputStream, fileName);
                if (!handleImport.equals("success")) {
                    resultList.add(handleImport);
                }
            }
        }
        
        //将MAP中剩余的入库
        this.flushData();
        this.flushData2();
        //插入日志
        this.insertLog();
        
        if (resultList.isEmpty()) {
            return "success";
        } else {
            StringBuilder results = new StringBuilder();
            for (String s : resultList) {
                if (results.length() > 0) {
                    results.append(", ");
                }
                results.append(s);
            }
            return results.toString();
        }

    }

    private String getSuffix(String fileName) {
        String[] split = fileName.split("\\u002E"); //u002E是.转义
        String suffix = split[split.length - 1];
        return suffix;
    }

    public Class getEntityClass(String entityPath, String filePath) {
        List<String> dataTypeList = DataTypeUtil.getAllDataType();
        String dataTypeName = null;
        for (String typeName : dataTypeList) {
            if (filePath.toUpperCase().indexOf(typeName.toUpperCase()) >= 0) {
                dataTypeName = typeName;
                break;
            }
        }
        if (dataTypeName == null) {
            return null;
        }

        Class<?> entityClass;

        if (dataTypeName.equals("Mrs")) {
            entityClass = Mre.class;
        } else {
            try {
                entityClass = Class.forName(entityPath + "." + dataTypeName);
            } catch (ClassNotFoundException ex) {
                entityClass = Mre.class;
            }
        }

        return entityClass;
    }
    
    

    public String handleImport(String entityPath, InputStream fileInputStream, String fileName) {

        String suffix = this.getSuffix(fileName);


        if (suffix.toUpperCase().equals("XML") || suffix.toUpperCase().equals("GZ")) {
            ImportMrUtil importMrUtil = new ImportMrUtil();
            
//            //判断数据来源类型，华为和中兴数据有些不一样
//            if(fileName.toUpperCase().indexOf("HUAWEI") >= 0) { 
//                importMrUtil.dataType = "HUAWEI";
//            }
            
            Class pojoClass = this.getEntityClass(entityPath, fileName);
            Map<String, List<Map<String, String>>> contentsMap;
            contentsMap = importMrUtil.getContentsMap(fileInputStream, pojoClass.getSimpleName());

            if (contentsMap == null || contentsMap.isEmpty()) {
                return fileName + ":null";
            }
            Map<String, StringBuilder> contentSbMap = ImportUtil.revertContentListMapToStringBuilder(contentsMap, entityPath);
            this.putData2(contentSbMap);
//            Map<String, List> revertToMapListEntity = ImportUtil.revertToMapListEntity(contentsMap, entityPath);
//
//            this.putData(revertToMapListEntity);

        } else {

            if (suffix.toUpperCase().equals("XLSX")) {
                XLSX2CSV xlsx2csv = new XLSX2CSV(fileInputStream);
                Map<String, InputStream> csvMap;
                while ((csvMap = xlsx2csv.process()) != null) {
                    this.handleCsvMap(csvMap);
                }
            } else {
                if(suffix.toUpperCase().equals("XLS")) {
                    XLS2CSV xls2csv = new XLS2CSV(fileInputStream);
                    Map<String, InputStream> csvMap = xls2csv.getCsvMap();
                    
                    this.handleCsvMap(csvMap);
                } else {
                    if(suffix.toUpperCase().equals("CSV")) {
                        this.handleCsv(fileInputStream, fileName);
                    } else {
                        logger.error("unknow file Type, fileName:" + fileName);
                    }
                }
            }

        }
        try {
            fileInputStream.close();    //关闭流
        } catch (IOException ex) {
            logger.info(ex.getMessage());
        }

        return "success";
    }
    
    private void handleCsvMap(Map<String, InputStream> csvMap) {
        
        //更新处理进度
        updateHandlePercent();
        
        Set<String> sheetNameSet = csvMap.keySet();
        for (String sheetName : sheetNameSet) {
            InputStream in = csvMap.get(sheetName);
            this.handleCsv(in, sheetName);
        }
    }
    
    private void handleCsv(InputStream in, String sheetNameOrFilePath) {
        ImportFromCsvUtil importFromCsvUtil = new ImportFromCsvUtil(in, sheetNameOrFilePath);

        List entityList;
        while ((entityList = importFromCsvUtil.getSomeEntityList()) != null) {
            if (entityList.isEmpty()) {
                continue;
            }
            
            //更新处理进度
            updateHandlePercent();

            logger.info("handle entity:" + entityList.get(0).getClass().getSimpleName()
                    + ", 个数：" + entityList.size());

            Map<String, List> entityListMap = new HashMap();
            entityListMap.put(ImportUtil.getTableName(entityList.get(0).getClass().getSimpleName(), userId, sheetNameOrFilePath), entityList);
            this.putData(entityListMap);
        }
    }

    private void putData(Map<String, List> mapListEntity) {
        if (this.dataMap == null || this.dataMap.isEmpty()) {
            this.dataMap = new HashMap();
        }

        for (String tableName : mapListEntity.keySet()) {
            List entityList = this.dataMap.get(tableName);
            List entityList1 = mapListEntity.get(tableName);
            if (entityList == null || entityList.isEmpty()) {
                this.dataMap.put(tableName, entityList1);
                entityList = this.dataMap.get(tableName);
            } else {
                entityList.addAll(entityList1);
            }

            if (entityList.size() >= this.loadTableSize) {
                int k;
                List entityPartList;
                for (k = 1; k * this.loadTableSize < entityList.size(); k++) {
                    entityPartList = entityList.subList((k - 1) * this.loadTableSize, k * this.loadTableSize);
                    this.loadToTable(entityPartList, tableName);
                }

                entityPartList = entityList.subList((k - 1) * this.loadTableSize, entityList.size());
                this.loadToTable(entityPartList, tableName);

                this.dataMap.remove(tableName);
            }
        }

    }
    
    private void putData2(Map<String, StringBuilder> contentSbMap) {
        for(String entityName : contentSbMap.keySet()) {
            StringBuilder contentSb = contentSbMap.get(entityName);
            if(contentSb == null || contentSb.length() == 0) {
                continue;
            }
            
            StringBuilder currentContentSb = this.dataSbMap.get(entityName);
            if(currentContentSb == null || currentContentSb.length() == 0) {
                this.dataSbMap.put(entityName, contentSb);
                currentContentSb = this.dataSbMap.get(entityName);
            } else {
                if(contentSb.length() > 0) {
                    currentContentSb.append("%\r\n").append(contentSb);
                }
            }
            
            //入库内容大于1M就入库，不大于就继续叠加吧，减少入库次数
            if(currentContentSb.length() > this.loadTableLength) {
                this.loadToTable2(currentContentSb, entityName);
                
                this.dataSbMap.remove(entityName);
            }
        }
    }
    
    public void flushData2() {
        if (this.dataSbMap == null || this.dataSbMap.isEmpty()) {
            return;
        }
        
        for(String entityName : this.dataSbMap.keySet()) {
            
            this.loadToTable2(this.dataSbMap.get(entityName), entityName);
        }
        
        this.dataSbMap.clear();
    }

    public void flushData() {
        if (this.dataMap == null || this.dataMap.isEmpty()) {
            return;
        }
        for (String tableName : this.dataMap.keySet()) {
            this.loadToTable(this.dataMap.get(tableName), tableName);
        }
        this.dataMap.clear();
    }

    private void loadToTable(List entityList, String tableName) {
        if (entityList == null || entityList.isEmpty()) {
            return;
        }

        ImportLog importLog = this.importLogMap.get(tableName);
        
        /**
         * 判断是否清空原数据
         * isDelete==on 而且 importLogMap没有入库记录（即本次导入数据该表还没有入库过）
         * 
         */
        if(importLog == null && this.isDelete != null && this.isDelete.equals("on")) {
            String entityName = entityList.get(0).getClass().getSimpleName();
            
            QueryCriteria criteria = new QueryCriteria();
            criteria.put("userId", this.userId);
            this.importDataManager.clear(criteria, entityName);
            QueryCriteria queryCriteria = new QueryCriteria();
            queryCriteria.put("userId", this.userId);
            this.importDataManager.deleteImportLog(queryCriteria, entityName);
//            this.loadTableDao.clearTable(tableName);
        }
        
        try {
            this.loadTableDao.insertTable(entityList, tableName);
        } catch (Exception e) {
            ImportTable importTable = entityList.get(0).getClass().getAnnotation(ImportTable.class);
            String tableModelName = importTable.tableName().replace("#", "");
            
            String createTableSql = "CREATE TABLE " + tableName
                    + " LIKE " + tableModelName;
            
            QueryCriteria queryCriteria = new QueryCriteria();
            queryCriteria.put("sql", createTableSql);
            this.importDao.executeSql(queryCriteria);   //根据表模板创建表
            this.loadTableDao.insertTable(entityList, tableName);
        }
        
        this.addImportMsg(tableName, entityList.size());

        if (importLog == null) {
            importLog = new ImportLog();
            this.importLogMap.put(tableName, importLog);

            importLog.importRowCount = entityList.size() + 0l;
            importLog.importTime = new Timestamp(new Date().getTime());
            importLog.msg = "success";
            importLog.userId = this.userId;
            importLog.userName = this.userName;
            importLog.entityName = entityList.get(0).getClass().getSimpleName();
            importLog.tableName = tableName;
        } else {
            importLog.importRowCount += entityList.size();
        }
    }
    
    private void loadToTable2(StringBuilder contentSb, String entityName) {
        ImportLog importLog = this.importLogMap.get(entityName);    //从集合中获取入库日志
        
        /**
         * 判断是否清空原数据
         * isDelete==on 而且 importLogMap没有入库记录（即本次导入数据该表还没有入库过）
         * 
         */
        if(importLog == null && this.isDelete != null && this.isDelete.equals("on")) {
            
            QueryCriteria criteria = new QueryCriteria();
            criteria.put("userId", this.userId);
            this.importDataManager.clear(criteria, entityName);
            QueryCriteria queryCriteria = new QueryCriteria();
            queryCriteria.put("userId", this.userId);
            this.importDataManager.deleteImportLog(queryCriteria, entityName);
//            this.loadTableDao.clearTable(ImportUtil.getLteTableName(entityName, this.userId));
        }
        
        //入库
        int insertCount = this.loadTableDao.insertTable(contentSb, ImportUtil.getColumnList(entityName), ImportUtil.getLteTableName(entityName, this.userId));
                
        if(insertCount == -1) {
            return;
        }
        
        this.addImportMsg(entityName, insertCount);

        Long alreadyInsertCount = this.importRecordMap.get(entityName);
        if(alreadyInsertCount == null) {
            this.importRecordMap.put(entityName, insertCount + 0l);
        } else {
            this.importRecordMap.put(entityName, insertCount + alreadyInsertCount);
        }
        
        //更新入库日志Map,Map中没有这个入库日志就加入Map中，有就更新入库记录数
        this.addImportMsg(entityName, insertCount);

        if (importLog == null) {
            importLog = new ImportLog();
            this.importLogMap.put(entityName, importLog);

            importLog.importRowCount = insertCount + 0l;
            importLog.importTime = new Timestamp(new Date().getTime());
            importLog.msg = "success";
            importLog.userId = this.userId;
            importLog.userName = this.userName;
            importLog.tableName = ImportUtil.getLteTableName(entityName, userId);
            importLog.entityName = entityName;
        } else {
            importLog.importRowCount += insertCount;
        }
        
    }
    
    private void addImportMsg(String entityName, Integer insertCount) {
        //加入导入数据过程信息
        List<String> msgList = ImportDetail.importMsgListMap.get(this.start);
        //更新处理进度
        updateHandlePercent();
        
        if(msgList == null) {
            msgList = new ArrayList();
            ImportDetail.importMsgListMap.put(start, msgList);
        }
        
        if(entityName == null && insertCount == null) {
            String msg = new Date() + " import Finish";
            msgList.add(msg);
            return;
        }
        
        String msg = new Date() + " 向表：" + entityName + "插入" + insertCount + "条数据";
        msgList.add(msg);
    }
    
    public void insertLog() {
        Collection<ImportLog> values = this.importLogMap.values();
        List<ImportLog> importLogList = new ArrayList();
        importLogList.addAll(values);

        if (!importLogList.isEmpty()) {
            this.importLogDao.insertEntity(importLogList);
        }
        
        //检查有没有工参，需要计算最短站间距的
        for(ImportLog importLog : importLogList) {
            String entityName = importLog.entityName;
            if(entityName.equals("DtbWorkerParticipationFrequency")) {
                String sql = "UPDATE dtbWorkerParticipationFrequency_" + this.userId
                        + " wp INNER JOIN ( SELECT * FROM ( SELECT 	`ge`.EnodeBId AS `EnodeBId`, 	2 * ATAN2(" 

                    + "SQRT( SIN( (`we`.`Latb`-`ge`.`Latb`)*PI()/180/2 ) * SIN( (`we`.`Latb`-`ge`.`Latb`)*PI()/180/2 ) + "
                    + " COS( `ge`.`Latb`*PI()/180 ) * COS( `we`.`Latb`*PI()/180 ) * SIN( (`we`.`Lonb`-`ge`.`Lonb`)*PI()/180/2 ) * SIN( (`we`.`Lonb`-`ge`.`Lonb`)*PI()/180/2 ) ), "

                    + " SQRT(1 -SIN( (`we`.`Latb`-`ge`.`Latb`)*PI()/180/2 ) * SIN( (`we`.`Latb`-`ge`.`Latb`)*PI()/180/2 ) + "
                    + " COS( `ge`.`Latb`*PI()/180 ) * COS( `we`.`Latb`*PI()/180 ) * SIN( (`we`.`Lonb`-`ge`.`Lonb`)*PI()/180/2 ) * SIN( (`we`.`Lonb`-`ge`.`Lonb`)*PI()/180/2 ) ) "
                    + " ) * 6371000 AS ndistance,`we`.EnodeBId AS nenodebId FROM	((SELECT  DISTINCT ge.EnodeBId, ge.Latb,ge.Lonb	 FROM dtbWorkerParticipationFrequency_" + this.userId
                        + " ge ) `ge`	LEFT JOIN ( SELECT  DISTINCT ge.EnodeBId, ge.Latb,ge.Lonb FROM dtbWorkerParticipationFrequency_" + this.userId
                        + " ge ) `we` ON (ge.EnodeBId<>we.EnodeBId AND (`ge`.`Lonb` - 0.55)< `we`.`Lonb` AND `we`.`Lonb` <(`ge`.`Lonb` + 0.55) AND (`ge`.`Latb` - 0.03)< `we`.`Latb`	AND	`we`.`Latb` <(`ge`.`Latb` + 0.03))) ORDER BY EnodeBId,ndistance) t GROUP BY EnodeBId ) t ON t.EnodeBId=wp.EnodeBId SET wp.ndistance=t.ndistance,wp.nenodebId=t.nenodebId";
                QueryCriteria criteria = new QueryCriteria();
                criteria.put("sql", sql);
                this.importDao.executeSql(criteria);
            }
        }
    }
    
    //
//    private String saveFile(InputStream inputStream, String fileName) {
//        try {
//            FileOutputStream fos = new FileOutputStream(fileName);
//            
//            byte[] data = new byte[2048];
//            int len;
//            while((len = inputStream.read(data)) != 0) {
//                fos.write(data, 0, len);
//            }
//            
//            fos.close();
//            inputStream.close();
//            
//        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(ImportDataManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
//        }
//        
//        return fileName;
//    }

    public ILoadTableDao getLoadTableDao() {
        return loadTableDao;
    }

    public void setLoadTableDao(ILoadTableDao loadTableDao) {
        this.loadTableDao = loadTableDao;
    }

    public IImportDataManager getImportDataManager() {
        return importDataManager;
    }

    public void setImportDataManager(IImportDataManager importDataManager) {
        this.importDataManager = importDataManager;
    }

    public ImportLogDao getImportLogDao() {
        return importLogDao;
    }

    public void setImportLogDao(ImportLogDao importLogDao) {
        this.importLogDao = importLogDao;
    }

    public String getUserName() {
        return userName;
    }

    public ImportDao getImprotDao() {
        return importDao;
    }

    public void setImprotDao(ImportDao improtDao) {
        this.importDao = improtDao;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
