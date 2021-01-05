package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class EUtranCellTDD {
    
//    @Meta(name = "SubNetwork")
    private Long SubNetwork;
    
//    @Meta(name = "MEID")
    private Long MEID;
    
//    @Meta(name = "userLabel")
    private String userLabel;
    
    @Meta(name = "ENODEBID")
    private Long enodeBId;
    
    @Meta(name = "cellLocalId")
    private Integer cellLocalId;
    
    @Meta(name = "CELLID1")
    private String cellId1;
    
    @Meta(name = "CELLID2")
    private String cellId2;
    
    @Meta(name = "refPlmn")
    private String refPlmn;
    
    @Meta(name = "refECellEquipmentFunctionTDD")
    private String refECellEquipmentFunctionTDD;
    
    @Meta(name = "pci")
    private Long pci;
    
    @Meta(name = "eai")
    private Long eai;
    
    @Meta(name = "tac")
    private Long tac;
    
    @Meta(name = "cellSize")
    private Long cellSize;
    
    @Meta(name = "bandIndicator")
    private Long bandIndicator;
    
    @Meta(name = "earfcn")
    private Double earfcn;
    
    @Meta(name = "LteEarfcn")
    private Long lteEarfcn;
    
    @Meta(name = "pciList")
    private String pciList;
    
    @Meta(name = "cellRSPortNum")
    private Long cellRSPortNum;
    
    @Meta(name = "cellRadius")
    private Long cellRadius;
    
    @Meta(name = "cellReferenceSignalPower")
    private Double cellReferenceSignalPower;
    
    @Meta(name = "pb")
    private Long pb;
    
    @Meta(name = "paForDTCH")
    private Long paForDTCH;
    
    @Meta(name = "bandWidth")
    private Long bandWidth;
    
//    @ImportEntity
//    private Double latitude;
//    
//    @ImportEntity
//    private Double longitude;
    
    @Meta(name = "maxUeRbNumDl")
    private Long maxUeRbNumDl;
    
    @Meta(name = "maxUeRbNumUl")
    private Long maxUeRbNumUl;
    
    @Meta(name = "ueTransModeTDD")
    private Long ueTransModeTDD;
    
    @Meta(name = "flagSwiMode")
    private Long flagSwiMode;
    
    @Meta(name = "sfAssignment")
    private String sfAssignment;
    
    @Meta(name = "specialSfPatterns")
    private String specialSfPatterns;
    
    @Meta(name = "rbByteMapDl")
    private String rbByteMapDl;
    
    @Meta(name = "rbSharMode")
    private Integer rbSharMode;
    
    @Meta(name = "ratioOperatorn")
    private String ratioOperatorn;
    
    @Meta(name = "ratioShared")
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
