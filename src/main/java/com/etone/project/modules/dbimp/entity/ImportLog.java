package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;

import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class ImportLog {
    @Meta
    public Integer id;
    
    @Meta(name = "导入时间", exportable = true)
    public Timestamp importTime;
    
    public String tableName;
    
    @Meta(name = "导入数据", exportable = true)
    public String entityName;
    
    @Meta(name = "导入记录数", exportable = true)
    public Long importRowCount;
    
    public Long userId;
    
    @Meta(name = "导入者", exportable = true)
    public String userName;
    
    @Meta(name = "导入结果", exportable = true)
    public String msg;
}
