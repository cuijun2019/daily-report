package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;
import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "ftbGSMMr_GT")
public class FtbGSMMrGT {
    
    @Meta(name = "开始时间")
    @ImportEntity(importName = "开始时间")
    private Timestamp startTime;
    
    @Meta(name = "结束时间")
    @ImportEntity(importName = "结束时间")
    private Timestamp endTime;
    
//    @Meta(name = "统计粒度")
//    @ImportEntity(importName = "统计粒度", convertRugular = "1小时:1,1天:2")
//    private Integer intHourDayType;
    
    @Meta(name = "CellID")
    @ImportEntity(importName = "CellID")
    private String cellId;
    
    @Meta(name = "Cell名称")
    @ImportEntity(importName = "Cell名称")
    private String cellName;
    
    @Meta(name = "MR采样点")
    @ImportEntity(importName = "MR采样点")
    private Long intMrPoint;
    
    @Meta(name = "平均上行电平(dBm)")
    @ImportEntity(importName = "平均上行电平(dBm)")
    private Double intUpRsrp;
    
    @Meta(name = "平均下行电平(dBm)")
    @ImportEntity(importName = "平均下行电平(dBm)")
    private Double intDownRsrp;
    
    @Meta(name = "平均上行质量")
    @ImportEntity(importName = "平均上行质量")
    private Double dbUpQualityRate;
    
    @Meta(name = "平均下行质量")
    @ImportEntity(importName = "平均下行质量")
    private Double dbDownQualityRate;
    
    @Meta(name = "上行弱覆盖比例(%)")
    @ImportEntity(importName = "上行弱覆盖比例(%)", convertRugular = "%:")
    private Double dbUpCoverRate;
    
    @Meta(name = "下行弱覆盖比例(%)")
    @ImportEntity(importName = "下行弱覆盖比例(%)", convertRugular = "%:")
    private Double dbDownCoverRate;
    
    @Meta(name = "上行质差比例(%)")
    @ImportEntity(importName = "上行质差比例(%)", convertRugular = "%:")
    private Double dbUpConnectRate;
    
    @Meta(name = "下行质差比例(%)")
    @ImportEntity(importName = "下行质差比例(%)", convertRugular = "%:")
    private Double dbDownConnectRate;

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

    public Long getIntMrPoint() {
        return intMrPoint;
    }

    public void setIntMrPoint(Long intMrPoint) {
        this.intMrPoint = intMrPoint;
    }

    public Double getIntUpRsrp() {
        return intUpRsrp;
    }

    public void setIntUpRsrp(Double intUpRsrp) {
        this.intUpRsrp = intUpRsrp;
    }

    public Double getIntDownRsrp() {
        return intDownRsrp;
    }

    public void setIntDownRsrp(Double intDownRsrp) {
        this.intDownRsrp = intDownRsrp;
    }

    public Double getDbUpCoverRate() {
        return dbUpCoverRate;
    }

    public void setDbUpCoverRate(Double dbUpCoverRate) {
        this.dbUpCoverRate = dbUpCoverRate;
    }

    public Double getDbDownCoverRate() {
        return dbDownCoverRate;
    }

    public void setDbDownCoverRate(Double dbDownCoverRate) {
        this.dbDownCoverRate = dbDownCoverRate;
    }

    public Double getDbUpQualityRate() {
        return dbUpQualityRate;
    }

    public void setDbUpQualityRate(Double dbUpQualityRate) {
        this.dbUpQualityRate = dbUpQualityRate;
    }

    public Double getDbDownQualityRate() {
        return dbDownQualityRate;
    }

    public void setDbDownQualityRate(Double dbDownQualityRate) {
        this.dbDownQualityRate = dbDownQualityRate;
    }

    public Double getDbUpConnectRate() {
        return dbUpConnectRate;
    }

    public void setDbUpConnectRate(Double dbUpConnectRate) {
        this.dbUpConnectRate = dbUpConnectRate;
    }

    public Double getDbDownConnectRate() {
        return dbDownConnectRate;
    }

    public void setDbDownConnectRate(Double dbDownConnectRate) {
        this.dbDownConnectRate = dbDownConnectRate;
    }
}
