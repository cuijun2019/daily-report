package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;
import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "ftbTDMr_GT")
public class FtbTDMrGT {
    
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
    
    @Meta(name = "RSCP采样点")
    @ImportEntity(importName = "RSCP采样点", convertRugular= "#N/A:")
    private Long intPccpchRscpPoint;
    
    @Meta(name = "RSCP平均值(dBm)")
    @ImportEntity(importName = "RSCP平均值(dBm)", convertRugular= "#N/A:")
    private Double dbPccpchRscpAvg;
    
    @Meta(name = "小区弱覆盖比例(%)")
    @ImportEntity(importName = "小区弱覆盖比例(%)", convertRugular = "%:,#N/A:")
    private Double intWeakCoverRate;
    
    @Meta(name = "ISCP采样点")
    @ImportEntity(importName = "ISCP采样点", convertRugular= "#N/A:")
    private Double intIscpPoint;
    
    @Meta(name = "ISCP平均值(dBm)")
    @ImportEntity(importName = "ISCP平均值(dBm)", convertRugular= "#N/A:")
    private Double intIscpAvg;
    
    @Meta(name = "小区干扰比例(%)")
    @ImportEntity(importName = "小区干扰比例(%)", convertRugular = "%:,#N/A:")
    private Double intCellDisRate;

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

//    public Integer getIntHourDayType() {
//        return intHourDayType;
//    }
//
//    public void setIntHourDayType(Integer intHourDayType) {
//        this.intHourDayType = intHourDayType;
//    }

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

    public Long getIntPccpchRscpPoint() {
        return intPccpchRscpPoint;
    }

    public void setIntPccpchRscpPoint(Long intPccpchRscpPoint) {
        this.intPccpchRscpPoint = intPccpchRscpPoint;
    }

    public Double getDbPccpchRscpAvg() {
        return dbPccpchRscpAvg;
    }

    public void setDbPccpchRscpAvg(Double dbPccpchRscpAvg) {
        this.dbPccpchRscpAvg = dbPccpchRscpAvg;
    }

    public Double getIntWeakCoverRate() {
        return intWeakCoverRate;
    }

    public void setIntWeakCoverRate(Double intWeakCoverRate) {
        this.intWeakCoverRate = intWeakCoverRate;
    }

    public Double getIntIscpPoint() {
        return intIscpPoint;
    }

    public void setIntIscpPoint(Double intIscpPoint) {
        this.intIscpPoint = intIscpPoint;
    }

    public Double getIntIscpAvg() {
        return intIscpAvg;
    }

    public void setIntIscpAvg(Double intIscpAvg) {
        this.intIscpAvg = intIscpAvg;
    }

    public Double getIntCellDisRate() {
        return intCellDisRate;
    }

    public void setIntCellDisRate(Double intCellDisRate) {
        this.intCellDisRate = intCellDisRate;
    }
}
