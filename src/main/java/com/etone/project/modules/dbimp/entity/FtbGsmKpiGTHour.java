package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;
import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "ftbGsmKpi_GT_hour")
public class FtbGsmKpiGTHour {
    
    @Meta(name = "开始时间")
    @ImportEntity(importName = "开始时间")
    private Timestamp startTime;
    
    @Meta(name = "结束时间")
    @ImportEntity(importName = "结束时间")
    private Timestamp endTime;
    
    @Meta(name = "CellID")
    @ImportEntity(importName = "CellID")
    private String cellId;
    
    @Meta(name = "Cell名称")
    @ImportEntity(importName = "Cell名称")
    private String cellName;
    
    @Meta(name = "基站ID")
    @ImportEntity(importName = "基站ID")
    private String NodeBId;
    
    @Meta(name = "基站名称")
    @ImportEntity(importName = "基站名称")
    private String NodeBName;
    
    @Meta(name = "忙时流量(MB)")
    @ImportEntity(importName = "忙时流量(MB)")
    private Double dbBusyByte;
    
    @Meta(name = "忙时无线利用率(%)")
    @ImportEntity(importName = "忙时无线利用率(%)", convertRugular = "%:")
    private Double dbNetUsingRate;
    
    @Meta(name = "忙时上行PDCH复用度")
    @ImportEntity(importName = "忙时上行PDCH复用度")
    private Double dbUpPDCH;
    
    @Meta(name = "忙时下行PDCH复用度")
    @ImportEntity(importName = "忙时下行PDCH复用度")
    private Double dbDownPDCH;

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

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getNodeBId() {
        return NodeBId;
    }

    public void setNodeBId(String NodeBId) {
        this.NodeBId = NodeBId;
    }

    public String getNodeBName() {
        return NodeBName;
    }

    public void setNodeBName(String NodeBName) {
        this.NodeBName = NodeBName;
    }

    public Double getDbBusyByte() {
        return dbBusyByte;
    }

    public void setDbBusyByte(Double dbBusyByte) {
        this.dbBusyByte = dbBusyByte;
    }

    public Double getDbNetUsingRate() {
        return dbNetUsingRate;
    }

    public void setDbNetUsingRate(Double dbNetUsingRate) {
        this.dbNetUsingRate = dbNetUsingRate;
    }

    public Double getDbDownPDCH() {
        return dbDownPDCH;
    }

    public void setDbDownPDCH(Double dbDownPDCH) {
        this.dbDownPDCH = dbDownPDCH;
    }

    public Double getDbUpPDCH() {
        return dbUpPDCH;
    }

    public void setDbUpPDCH(Double dbUpPDCH) {
        this.dbUpPDCH = dbUpPDCH;
    }
}
