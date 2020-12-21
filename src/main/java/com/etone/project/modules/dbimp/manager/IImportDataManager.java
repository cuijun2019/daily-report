package com.etone.project.modules.dbimp.manager;

import com.etone.project.base.entity.share.User;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public interface IImportDataManager {
    /**
     * 导入数据
     * @param inputStream
     * @param type
     * @return 
     */
    public Map<String, String> importData(InputStream inputStream, String type, String fileName, User user, String isDelete);
    
    /**
     * 查询导入日志
     * @param criteria
     * @param userId
     * @return 
     * 
     */
    public PageResult<Map> queryImportLog(QueryCriteria criteria, Long userId);
    
    /**
     * 查询流量均衡导入日志
     * @param criteria
     * @return 
     * 
     */
    public PageResult<Map> queryTrafficImportLog(QueryCriteria criteria);
    
//    /**
//     * 根据实体名查询导入日志
//     * @param entityName
//     * @param criteria 
//     * @return 
//     */
//    public List<Map> queryLogByEntity(String entityName, QueryCriteria criteria);
    
    /**
     * 查询表数据
     * @param criteria
     * @param entityName 
     * @return 
     * 
     */
    public PageResult<Map> query(QueryCriteria criteria, String entityName);
        
    /**
     * 清空表数据
     * @param criteria
     * @param entityName
     * @return 
     * 
     */
    public Object clear(QueryCriteria criteria, String entityName);
    
    /**
     * 删除导入日志表中表名为importTableName导入记录
     * @param criteria
     * @param importTableName
     * @return 
     */
    public Object deleteImportLog(QueryCriteria criteria, String importTableName);
    
    /**
     * 查看导入详细信息
     * @param importKey
     * @param msgIndex
     * @return 
     */
    public String queryImportDetail(Long importKey, Integer msgIndex);
    
    /**
     * 查看导入处理进程
     * @param importKey
     * @return 
     */
    public String queryImportPercent(Long importKey);
}
