package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "dtbGSMEngineerInfo_GT")
public class DtbGSMEngineerInfoGT {
    
    @Meta(name = "CGI")
    @ImportEntity(importName = "CGI")
    private String intCgi;
    
    @Meta(name = "CellID")
    @ImportEntity(importName = "CellID", notNull = true)
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
    
    @Meta(name = "小区类型")
    @ImportEntity(importName = "小区类型", convertRugular = "室外:1,室内:2")
    private Integer intCellType;
    
    @Meta(name = "覆盖类型")
    @ImportEntity(importName = "覆盖类型", convertRugular = "密集城区:1,郊区:2,农村:3")
    private Integer intCoverType;
    
    @Meta(name = "地址")
    @ImportEntity(importName = "地址")
    private String vcAddress;
    
    @Meta(name = "经度")
    @ImportEntity(importName = "经度")
    private Double dbLonb;
    
    @Meta(name = "纬度")
    @ImportEntity(importName = "纬度")
    private Double dbLatb;
    
    @Meta(name = "天线方向角")
    @ImportEntity(importName = "天线方向角")
    private Integer intAngle;
    
    @Meta(name = "设备厂家")
    @ImportEntity(importName = "设备厂家")
    private String vcFactory;
    
//    @Meta(name = "是否高价值小区")
    @ImportEntity
    private Integer intHighCellType;
    
//    @Meta(name = "是否有同站址/同覆盖TD")
    @ImportEntity
    private Integer intWithCoverTDType;
    
//    @Meta(name = "同站址/同覆盖TD小区")
    @ImportEntity
    private String vcWithCoverTDCell;
    
//    @Meta(name = "是否有同站址/同覆盖WLAN")
    @ImportEntity
    private Integer intWithCoverWLANType;
    
//    @Meta(name = "同站址/同覆盖WLAN")
    @ImportEntity
    private String vcWithCoverWLANCell;

    public String getIntCgi() {
        return intCgi;
    }

    public void setIntCgi(String intCgi) {
        this.intCgi = intCgi;
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

    public Integer getIntCellType() {
        return intCellType;
    }

    public void setIntCellType(Integer intCellType) {
        this.intCellType = intCellType;
    }

    public Integer getIntCoverType() {
        return intCoverType;
    }

    public void setIntCoverType(Integer intCoverType) {
        this.intCoverType = intCoverType;
    }

    public String getVcAddress() {
        return vcAddress;
    }

    public void setVcAddress(String vcAddress) {
        this.vcAddress = vcAddress;
    }

    public Double getDbLonb() {
        return dbLonb;
    }

    public void setDbLonb(Double dbLonb) {
        this.dbLonb = dbLonb;
    }

    public Double getDbLatb() {
        return dbLatb;
    }

    public void setDbLatb(Double dbLatb) {
        this.dbLatb = dbLatb;
    }

    public Integer getIntAngle() {
        return intAngle;
    }

    public void setIntAngle(Integer intAngle) {
        this.intAngle = intAngle;
    }

    public String getVcFactory() {
        return vcFactory;
    }

    public void setVcFactory(String vcFactory) {
        this.vcFactory = vcFactory;
    }

    public Integer getIntHighCellType() {
        return intHighCellType;
    }

    public void setIntHighCellType(Integer intHighCellType) {
        this.intHighCellType = intHighCellType;
    }

    public Integer getIntWithCoverTDType() {
        return intWithCoverTDType;
    }

    public void setIntWithCoverTDType(Integer intWithCoverTDType) {
        this.intWithCoverTDType = intWithCoverTDType;
    }

    public String getVcWithCoverTDCell() {
        return vcWithCoverTDCell;
    }

    public void setVcWithCoverTDCell(String vcWithCoverTDCell) {
        this.vcWithCoverTDCell = vcWithCoverTDCell;
    }

    public Integer getIntWithCoverWLANType() {
        return intWithCoverWLANType;
    }

    public void setIntWithCoverWLANType(Integer intWithCoverWLANType) {
        this.intWithCoverWLANType = intWithCoverWLANType;
    }

    public String getVcWithCoverWLANCell() {
        return vcWithCoverWLANCell;
    }

    public void setVcWithCoverWLANCell(String vcWithCoverWLANCell) {
        this.vcWithCoverWLANCell = vcWithCoverWLANCell;
    }

    @Override
    public String toString() {
        return "DtbGSMEngineerInfoGT{ intCgi=" + intCgi + ", cellId=" + cellId + ", cellName=" + cellName + ", NodeBId=" + NodeBId + ", NodeBName=" + NodeBName + ", intCellType=" + intCellType + ", intCoverType=" + intCoverType + ", vcAddress=" + vcAddress + ", dbLonb=" + dbLonb + ", dbLatb=" + dbLatb + ", intAngle=" + intAngle + ", vcFactory=" + vcFactory + '}';
    }
}
