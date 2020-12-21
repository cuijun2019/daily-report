package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;
import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "ftbEUtranRelationHour_ltekpi_", isUseUserId = true)
public class EUtranRelationHour {
    
//    @Meta(name = "序号")
    @ImportEntity(importName = "序号", primarykey = true)
    private Integer id;
    
    @Meta(name = "开始时间")
    @ImportEntity(importName = "开始时间")
    private Timestamp startTime;
    
    @Meta(name = "结束时间")
    @ImportEntity(importName = "结束时间")
    private Timestamp endTime;
    
//    @Meta(name = "查询粒度")
    @ImportEntity(importName = "查询粒度")
    private String granularity;
    
    @Meta(name = "小区名称")
    @ImportEntity(importName = "小区名称", exportFieldWidth= 100)
    private String cellName;
    
    @Meta(name = "ENODEBID")
    @ImportEntity(importName = "ENODEBID")
    private Long enodeBID;
    
    @Meta(name = "ENODEB名称")
    @ImportEntity(importName = "ENODEB名称")
    private String enodeBName;
    
//    @Meta(name = "SectorID")
    @ImportEntity(importName = "SectorID")
    private Integer sectorID;
    
    @Meta(name = "CELLID")
    @ImportEntity(importName = "CELLID")
    private String cellId1;
    
    @Meta(name = "小区ID")
    @ImportEntity(importName = "小区ID")
    private String cellId;
    
    @Meta(name = "邻区关系")
    @ImportEntity(importName = "邻区关系")
    private String neighbourRelation;
    
    @Meta(name = "邻区关系名称")
    @ImportEntity(importName = "邻区关系名称", exportFieldWidth = 100)
    private String neighbourRelationName;
    
    @Meta(name = "NCELLID")
    @ImportEntity(importName = "NCELLID")
    private Long ncellId;
    
    @Meta(name = "邻区ID")
    @ImportEntity(importName = "邻区ID")
    private String neighbourCellId;
    
    @Meta(name = "切换错误导致的切换失败次数")
    @ImportEntity(importName = "切换错误导致的切换失败次数")
    private Long changeWrongResultInFailCount;
    
    @Meta(name = "切换过晚导致的切换失败次数")
    @ImportEntity(importName = "切换过晚导致的切换失败次数")
    private Long changeTooLateResultInChangeFailCount;
    
    @Meta(name = "切换过早导致的切换失败次数")
    @ImportEntity(importName = "切换过早导致的切换失败次数")
    private Long changePrematurelyResultInFailCount;
    
    @Meta(name = "异频切换出请求次数")
    @ImportEntity(importName = "异频切换出请求次数")
    private Long diffChannelChangeReqCount;
    
    @Meta(name = "异频切换出成功次数")
    @ImportEntity(importName = "异频切换出成功次数")
    private Long diffChannelChangeSuccessCount;
    
    @Meta(name = "同频切换出请求次数")
    @ImportEntity(importName = "同频切换出请求次数")
    private Long sameChannelChangeReqCount;
    
    @Meta(name = "同频切换出成功次数")
    @ImportEntity(importName = "同频切换出成功次数")
    private Long sameChannelChangeSuccessCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getCellId1() {
        return cellId1;
    }

    public void setCellId1(String cellId1) {
        this.cellId1 = cellId1;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getNeighbourRelation() {
        return neighbourRelation;
    }

    public void setNeighbourRelation(String neighbourRelation) {
        this.neighbourRelation = neighbourRelation;
    }

    public Long getNcellId() {
        return ncellId;
    }

    public void setNcellId(Long ncellId) {
        this.ncellId = ncellId;
    }

    public String getNeighbourCellId() {
        return neighbourCellId;
    }

    public void setNeighbourCellId(String neighbourCellId) {
        this.neighbourCellId = neighbourCellId;
    }

    public Long getEnodeBID() {
        return enodeBID;
    }

    public void setEnodeBID(Long enodeBID) {
        this.enodeBID = enodeBID;
    }

    public String getEnodeBName() {
        return enodeBName;
    }

    public void setEnodeBName(String enodeBName) {
        this.enodeBName = enodeBName;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public String getNeighbourRelationName() {
        return neighbourRelationName;
    }

    public void setNeighbourRelationName(String neighbourRelationName) {
        this.neighbourRelationName = neighbourRelationName;
    }

    public Long getChangeWrongResultInFailCount() {
        return changeWrongResultInFailCount;
    }

    public void setChangeWrongResultInFailCount(Long changeWrongResultInFailCount) {
        this.changeWrongResultInFailCount = changeWrongResultInFailCount;
    }

    public Long getChangeTooLateResultInChangeFailCount() {
        return changeTooLateResultInChangeFailCount;
    }

    public void setChangeTooLateResultInChangeFailCount(Long changeTooLateResultInChangeFailCount) {
        this.changeTooLateResultInChangeFailCount = changeTooLateResultInChangeFailCount;
    }

    public Long getChangePrematurelyResultInFailCount() {
        return changePrematurelyResultInFailCount;
    }

    public void setChangePrematurelyResultInFailCount(Long changePrematurelyResultInFailCount) {
        this.changePrematurelyResultInFailCount = changePrematurelyResultInFailCount;
    }

    public Long getDiffChannelChangeReqCount() {
        return diffChannelChangeReqCount;
    }

    public void setDiffChannelChangeReqCount(Long diffChannelChangeReqCount) {
        this.diffChannelChangeReqCount = diffChannelChangeReqCount;
    }

    public Long getDiffChannelChangeSuccessCount() {
        return diffChannelChangeSuccessCount;
    }

    public void setDiffChannelChangeSuccessCount(Long diffChannelChangeSuccessCount) {
        this.diffChannelChangeSuccessCount = diffChannelChangeSuccessCount;
    }

    public Long getSameChannelChangeReqCount() {
        return sameChannelChangeReqCount;
    }

    public void setSameChannelChangeReqCount(Long sameChannelChangeReqCount) {
        this.sameChannelChangeReqCount = sameChannelChangeReqCount;
    }

    public Long getSameChannelChangeSuccessCount() {
        return sameChannelChangeSuccessCount;
    }

    public void setSameChannelChangeSuccessCount(Long sameChannelChangeSuccessCount) {
        this.sameChannelChangeSuccessCount = sameChannelChangeSuccessCount;
    }
}
