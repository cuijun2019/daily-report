package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;
import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "ftbWLANKpiDay")
public class FtbWLANKpiDay {
    
    @Meta(name = "开始时间")
    @ImportEntity(importName = "开始时间")
    private Timestamp startTime;
    
    @Meta(name = "结束时间")
    @ImportEntity(importName = "结束时间")
    private Timestamp endTime;
    
    @Meta(name = "HOTSPOT_ID")
    @ImportEntity(importName = "HOTSPOT_ID")
    private String vcHostPotId;
    
    @Meta(name = "HOTSPOT_名称")
    @ImportEntity(importName = "HOTSPOT_名称")
    private String vcHostPotName;
    
    @Meta(name = "总流量(MB)")
    @ImportEntity(importName = "总流量(MB)")
    private Double cellByte;
    
    @Meta(name = "在线用户数")
    @ImportEntity(importName = "在线用户数")
    private Integer terminalNum;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getVcHostPotId() {
        return vcHostPotId;
    }

    public void setVcHostPotId(String vcHostPotId) {
        this.vcHostPotId = vcHostPotId;
    }

    public String getVcHostPotName() {
        return vcHostPotName;
    }

    public void setVcHostPotName(String vcHostPotName) {
        this.vcHostPotName = vcHostPotName;
    }

    public Double getCellByte() {
        return cellByte;
    }

    public void setCellByte(Double cellByte) {
        this.cellByte = cellByte;
    }

    public Integer getTerminalNum() {
        return terminalNum;
    }

    public void setTerminalNum(Integer terminalNum) {
        this.terminalNum = terminalNum;
    }
}
