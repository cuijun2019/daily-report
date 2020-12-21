package com.etone.project.modules.dbimp.dao;

import java.util.List;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public interface ILoadTableDao {
    public void test();
    public String insertTable(List entityList, String tableName);
    
    public int insertTable(StringBuilder sb, List<String> columnList, String tableName);
    
    public void clearTable(String tableName);
}
