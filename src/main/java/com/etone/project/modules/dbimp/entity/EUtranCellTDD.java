package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "EUtranCellTDD_", isUseUserId = true)
public class EUtranCellTDD {
    
//    @Meta(name = "SubNetwork")
    @ImportEntity
    private Long SubNetwork;
    
//    @Meta(name = "MEID")
    @ImportEntity
    private Long MEID;
    
//    @Meta(name = "userLabel")
    @ImportEntity(exportFieldWidth = 128)
    private String userLabel;
    
    @Meta(name = "ENODEBID")
    @ImportEntity(importName = "ENODEBID")
    private Long enodeBId;
    
    @Meta(name = "cellLocalId")
    @ImportEntity
    private Integer cellLocalId;
    
    @Meta(name = "CELLID1")
    @ImportEntity(importName = "CELLID1")
    private String cellId1;
    
    @Meta(name = "CELLID2")
    @ImportEntity(importName = "CELLID2")
    private String cellId2;
    
    @Meta(name = "refPlmn")
    @ImportEntity
    private String refPlmn;
    
    @Meta(name = "refECellEquipmentFunctionTDD")
    @ImportEntity
    private String refECellEquipmentFunctionTDD;
    
    @Meta(name = "pci")
    @ImportEntity
    private Long pci;
    
    @Meta(name = "eai")
    @ImportEntity
    private Long eai;
    
    @Meta(name = "tac")
    @ImportEntity
    private Long tac;
    
    @Meta(name = "cellSize")
    @ImportEntity
    private Long cellSize;
    
    @Meta(name = "bandIndicator")
    @ImportEntity
    private Long bandIndicator;
    
    @Meta(name = "earfcn")
    @ImportEntity
    private Double earfcn;
    
    @Meta(name = "LteEarfcn")
    @ImportEntity(importName = "LteEarfcn")
    private Long lteEarfcn;
    
    @Meta(name = "pciList")
    @ImportEntity(exportFieldWidth= 200)
    private String pciList;
    
    @Meta(name = "cellRSPortNum")
    @ImportEntity
    private Long cellRSPortNum;
    
    @Meta(name = "cellRadius")
    @ImportEntity
    private Long cellRadius;
    
    @Meta(name = "cellReferenceSignalPower")
    @ImportEntity
    private Double cellReferenceSignalPower;
    
    @Meta(name = "pb")
    @ImportEntity
    private Long pb;
    
    @Meta(name = "paForDTCH")
    @ImportEntity
    private Long paForDTCH;
    
    @Meta(name = "bandWidth")
    @ImportEntity
    private Long bandWidth; 
    
//    @ImportEntity
//    private Double latitude;
//    
//    @ImportEntity
//    private Double longitude;
    
    @Meta(name = "maxUeRbNumDl")
    @ImportEntity
    private Long maxUeRbNumDl;
    
    @Meta(name = "maxUeRbNumUl")
    @ImportEntity
    private Long maxUeRbNumUl;
    
    @Meta(name = "ueTransModeTDD")
    @ImportEntity
    private Long ueTransModeTDD;
    
    @Meta(name = "flagSwiMode")
    @ImportEntity
    private Long flagSwiMode;
    
    @Meta(name = "sfAssignment")
    @ImportEntity
    private String sfAssignment;
    
    @Meta(name = "specialSfPatterns")
    @ImportEntity
    private String specialSfPatterns;
    
    @Meta(name = "rbByteMapDl")
    @ImportEntity(exportFieldWidth= 100)
    private String rbByteMapDl;
    
    @Meta(name = "rbSharMode")
    @ImportEntity
    private Integer rbSharMode;
    
    @Meta(name = "ratioOperatorn")
    @ImportEntity
    private String ratioOperatorn;
    
    @Meta(name = "ratioShared")
    @ImportEntity
    private Long ratioShared;

    public Long getSubNetwork() {
        return SubNetwork;
    }

    public void setSubNetwork(Long SubNetwork) {
        this.SubNetwork = SubNetwork;
    }

    public Long getMEID() {
        return MEID;
    }

    public void setMEID(Long MEID) {
        this.MEID = MEID;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public Integer getCellLocalId() {
        return cellLocalId;
    }

    public void setCellLocalId(Integer cellLocalId) {
        this.cellLocalId = cellLocalId;
    }

    public String getRefECellEquipmentFunctionTDD() {
        return refECellEquipmentFunctionTDD;
    }

    public void setRefECellEquipmentFunctionTDD(String refECellEquipmentFunctionTDD) {
        this.refECellEquipmentFunctionTDD = refECellEquipmentFunctionTDD;
    }

    public String getRefPlmn() {
        return refPlmn;
    }

    public void setRefPlmn(String refPlmn) {
        this.refPlmn = refPlmn;
    }

    public Long getPci() {
        return pci;
    }

    public void setPci(Long pci) {
        this.pci = pci;
    }

    public String getPciList() {
        return pciList;
    }

    public void setPciList(String pciList) {
        this.pciList = pciList;
    }

    public Long getTac() {
        return tac;
    }

    public void setTac(Long tac) {
        this.tac = tac;
    }

    public Long getEai() {
        return eai;
    }

    public void setEai(Long eai) {
        this.eai = eai;
    }

    public Long getCellSize() {
        return cellSize;
    }

    public void setCellSize(Long cellSize) {
        this.cellSize = cellSize;
    }

    public Long getCellRadius() {
        return cellRadius;
    }

    public void setCellRadius(Long cellRadius) {
        this.cellRadius = cellRadius;
    }

    public Double getCellReferenceSignalPower() {
        return cellReferenceSignalPower;
    }

    public void setCellReferenceSignalPower(Double cellReferenceSignalPower) {
        this.cellReferenceSignalPower = cellReferenceSignalPower;
    }

    public Long getPb() {
        return pb;
    }

    public void setPb(Long pb) {
        this.pb = pb;
    }

    public Long getPaForDTCH() {
        return paForDTCH;
    }

    public void setPaForDTCH(Long paForDTCH) {
        this.paForDTCH = paForDTCH;
    }

    public Long getBandIndicator() {
        return bandIndicator;
    }

    public void setBandIndicator(Long bandIndicator) {
        this.bandIndicator = bandIndicator;
    }

    public Double getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(Double earfcn) {
        this.earfcn = earfcn;
    }

    public Long getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(Long bandWidth) {
        this.bandWidth = bandWidth;
    }

    public String getCellId1() {
        return cellId1;
    }

    public void setCellId1(String cellId1) {
        this.cellId1 = cellId1;
    }

    public String getCellId2() {
        return cellId2;
    }

    public void setCellId2(String cellId2) {
        this.cellId2 = cellId2;
    }

    public Long getMaxUeRbNumDl() {
        return maxUeRbNumDl;
    }

    public void setMaxUeRbNumDl(Long maxUeRbNumDl) {
        this.maxUeRbNumDl = maxUeRbNumDl;
    }

    public Long getMaxUeRbNumUl() {
        return maxUeRbNumUl;
    }

    public void setMaxUeRbNumUl(Long maxUeRbNumUl) {
        this.maxUeRbNumUl = maxUeRbNumUl;
    }

    public String getSfAssignment() {
        return sfAssignment;
    }

    public void setSfAssignment(String sfAssignment) {
        this.sfAssignment = sfAssignment;
    }

    public String getSpecialSfPatterns() {
        return specialSfPatterns;
    }

    public void setSpecialSfPatterns(String specialSfPatterns) {
        this.specialSfPatterns = specialSfPatterns;
    }

    public Long getCellRSPortNum() {
        return cellRSPortNum;
    }

    public void setCellRSPortNum(Long cellRSPortNum) {
        this.cellRSPortNum = cellRSPortNum;
    }

    public Long getUeTransModeTDD() {
        return ueTransModeTDD;
    }

    public void setUeTransModeTDD(Long ueTransModeTDD) {
        this.ueTransModeTDD = ueTransModeTDD;
    }

    public Long getFlagSwiMode() {
        return flagSwiMode;
    }

    public void setFlagSwiMode(Long flagSwiMode) {
        this.flagSwiMode = flagSwiMode;
    }

    public String getRbByteMapDl() {
        return rbByteMapDl;
    }

    public void setRbByteMapDl(String rbByteMapDl) {
        this.rbByteMapDl = rbByteMapDl;
    }

    public Integer getRbSharMode() {
        return rbSharMode;
    }

    public void setRbSharMode(Integer rbSharMode) {
        this.rbSharMode = rbSharMode;
    }

    public String getRatioOperatorn() {
        return ratioOperatorn;
    }

    public void setRatioOperatorn(String ratioOperatorn) {
        this.ratioOperatorn = ratioOperatorn;
    }

    public Long getRatioShared() {
        return ratioShared;
    }

    public void setRatioShared(Long ratioShared) {
        this.ratioShared = ratioShared;
    }

    public Long getEnodeBId() {
        return enodeBId;
    }

    public void setEnodeBId(Long enodeBId) {
        this.enodeBId = enodeBId;
    }

    public Long getLteEarfcn() {
        return lteEarfcn;
    }

    public void setLteEarfcn(Long lteEarfcn) {
        this.lteEarfcn = lteEarfcn;
    }
}
