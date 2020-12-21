package com.etone.project.modules.dbimp.dao;

import com.etone.project.core.model.QueryCriteria;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */

public interface ImportLogDao extends DataImportDao{
    public List<String> getTableNames();
    
    
    public List<Map> query(QueryCriteria criteria);
    
    public int count(QueryCriteria criteria);
}
