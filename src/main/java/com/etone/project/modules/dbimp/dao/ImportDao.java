package com.etone.project.modules.dbimp.dao;

import com.etone.project.core.model.QueryCriteria;
import com.etone.project.utils.SQLAdapter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */

public interface ImportDao {

    public void clearTable(SQLAdapter sql);
    
    public List<Map> query(QueryCriteria criteria);
    
    public int count(QueryCriteria criteria);
    
    public void executeSql(QueryCriteria criteria);
}
