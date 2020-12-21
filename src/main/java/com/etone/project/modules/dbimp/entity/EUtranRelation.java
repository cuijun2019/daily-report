package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "EUtranRelation_", isUseUserId = true)
public class EUtranRelation {
    
//    @Meta(name = "SubNetwork")
    @ImportEntity
    private Long SubNetwork;
    
//    @Meta(name = "MEID")
    @ImportEntity(importName = "MEID")
    private Long MEID;
    
    @Meta(name = "Sc.eNBId")
    @ImportEntity(importName = "Sc.eNBId")
    private Long scEnbId;
    
    @Meta(name = "Sc.cellLocalId")
    @ImportEntity(importName = "Sc.cellLocalId")
    private Long scCellLocalId;
    
    @Meta(name = "scID1")
    @ImportEntity(importName = "Sc.ID1")
    private String scId1;
    
    @Meta(name = "scID2")
    @ImportEntity(importName = "Sc.ID2")
    private String scId2;
    
    @Meta(name = "scPCI")
    @ImportEntity(importName = "Sc.PCI")
    private Long scPCI;
    
    @Meta(name = "Sc.LteEarfcn")
    @ImportEntity(importName = "Sc.LteEarfcn")
    private Long mrLteScEarfcn;

    @Meta(name = "userLabel")
    @ImportEntity(exportFieldWidth = 128)
    private String userLabel;
    
    @Meta(name = "mcc")
    @ImportEntity
    private String mcc;
    
    @Meta(name = "mnc")
    @ImportEntity
    private String mnc;
    
    @Meta(name = "Nc.eNBId")
    @ImportEntity(importName = "Nc.eNBId")
    private Long ncENBId;
    
    @Meta(name = "Nc.cellLocalId")
    @ImportEntity(importName = "Nc.cellLocalId")
    private Long ncCellLocalId;
    
    @Meta(name = "Nc.ID1")
    @ImportEntity(importName = "Nc.ID1")
    private String ncId1;
    
    @Meta(name = "Nc.ID2")
    @ImportEntity(importName = "Nc.ID2")
    private String ndId2;
    
    @Meta(name = "Nc.PCI")
    @ImportEntity(importName = "Nc.PCI")
    private Long pci;
   
    @Meta(name = "Nc.LteEarfcn")
    @ImportEntity(importName= "Nc.LteEarfcn")
    private Long ncLteEarfcn;
    
    @Meta(name = "isRemoveAllowed")
    @ImportEntity(convertRugular= "0:false,1:true")
    private Boolean isRemoveAllowed;
    
    @Meta(name = "isX2HOAllowed")
    @ImportEntity(convertRugular= "0:false,1:true")
    private Boolean isX2HOAllowed;
    
    @Meta(name = "isHOAllowed")
    @ImportEntity(convertRugular= "0:false,1:true")
    private Boolean isHOAllowed;
    
    @Meta(name = "isIcicInformationSendAllowed")
    @ImportEntity(convertRugular= "0:false,1:true")
    private Boolean isIcicInformationSendAllowed;
    
    @Meta(name = "isLbAllowed")
    @ImportEntity(convertRugular= "0:false,1:true")
    private Boolean isLbAllowed;
    
    @Meta(name = "shareCover")
    @ImportEntity
    private Integer shareCover;
    
    @Meta(name = "qofStCell")
    @ImportEntity
    private Long qofStCell;
    
    @Meta(name = "isAnrCreated")
    @ImportEntity(convertRugular= "0:false,1:true")
    private Boolean isAnrCreated;
    
    @Meta(name = "nCelPriority")
    @ImportEntity
    private Integer nCelPriority;
    
    @Meta(name = "s1DataFwdFlag")
    @ImportEntity(convertRugular= "0:false,1:true")
    private Boolean s1DataFwdFlag;
    
    @Meta(name = "cellIndivOffset")
    @ImportEntity
    private Long cellIndivOffset;
    
    @Meta(name = "stateInd")
    @ImportEntity
    private Integer stateInd;
    
    @Meta(name = "radioMode")
    @ImportEntity
    private String radioMode;

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

    public Long getScEnbId() {
        return scEnbId;
    }

    public void setScEnbId(Long scEnbId) {
        this.scEnbId = scEnbId;
    }


    public String getScId1() {
        return scId1;
    }

    public void setScId1(String scId1) {
        this.scId1 = scId1;
    }

    public String getScId2() {
        return scId2;
    }

    public void setScId2(String scId2) {
        this.scId2 = scId2;
    }

    public Long getScPCI() {
        return scPCI;
    }

    public void setScPCI(Long scPCI) {
        this.scPCI = scPCI;
    }

    public Long getMrLteScEarfcn() {
        return mrLteScEarfcn;
    }

    public void setMrLteScEarfcn(Long mrLteScEarfcn) {
        this.mrLteScEarfcn = mrLteScEarfcn;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public Long getNcENBId() {
        return ncENBId;
    }

    public void setNcENBId(Long ncENBId) {
        this.ncENBId = ncENBId;
    }

    public String getNcId1() {
        return ncId1;
    }

    public void setNcId1(String ncId1) {
        this.ncId1 = ncId1;
    }

    public String getNdId2() {
        return ndId2;
    }

    public void setNdId2(String ndId2) {
        this.ndId2 = ndId2;
    }

    public Long getPci() {
        return pci;
    }

    public void setPci(Long pci) {
        this.pci = pci;
    }

    public Boolean getIsRemoveAllowed() {
        return isRemoveAllowed;
    }

    public void setIsRemoveAllowed(Boolean isRemoveAllowed) {
        this.isRemoveAllowed = isRemoveAllowed;
    }

    public Boolean getIsX2HOAllowed() {
        return isX2HOAllowed;
    }

    public void setIsX2HOAllowed(Boolean isX2HOAllowed) {
        this.isX2HOAllowed = isX2HOAllowed;
    }

    public Boolean getIsHOAllowed() {
        return isHOAllowed;
    }

    public void setIsHOAllowed(Boolean isHOAllowed) {
        this.isHOAllowed = isHOAllowed;
    }

    public Boolean getIsIcicInformationSendAllowed() {
        return isIcicInformationSendAllowed;
    }

    public void setIsIcicInformationSendAllowed(Boolean isIcicInformationSendAllowed) {
        this.isIcicInformationSendAllowed = isIcicInformationSendAllowed;
    }

    public Boolean getIsLbAllowed() {
        return isLbAllowed;
    }

    public void setIsLbAllowed(Boolean isLbAllowed) {
        this.isLbAllowed = isLbAllowed;
    }

    public Integer getShareCover() {
        return shareCover;
    }

    public void setShareCover(Integer shareCover) {
        this.shareCover = shareCover;
    }

    public Long getQofStCell() {
        return qofStCell;
    }

    public void setQofStCell(Long qofStCell) {
        this.qofStCell = qofStCell;
    }

    public Boolean getIsAnrCreated() {
        return isAnrCreated;
    }

    public void setIsAnrCreated(Boolean isAnrCreated) {
        this.isAnrCreated = isAnrCreated;
    }

    public Integer getNCelPriority() {
        return nCelPriority;
    }

    public void setNCelPriority(Integer nCelPriority) {
        this.nCelPriority = nCelPriority;
    }

    public Boolean getS1DataFwdFlag() {
        return s1DataFwdFlag;
    }

    public void setS1DataFwdFlag(Boolean s1DataFwdFlag) {
        this.s1DataFwdFlag = s1DataFwdFlag;
    }

    public Long getCellIndivOffset() {
        return cellIndivOffset;
    }

    public void setCellIndivOffset(Long cellIndivOffset) {
        this.cellIndivOffset = cellIndivOffset;
    }

    public Integer getStateInd() {
        return stateInd;
    }

    public void setStateInd(Integer stateInd) {
        this.stateInd = stateInd;
    }

    public String getRadioMode() {
        return radioMode;
    }

    public void setRadioMode(String radioMode) {
        this.radioMode = radioMode;
    }

    public Long getScCellLocalId() {
        return scCellLocalId;
    }

    public void setScCellLocalId(Long scCellLocalId) {
        this.scCellLocalId = scCellLocalId;
    }

    public Long getNcCellLocalId() {
        return ncCellLocalId;
    }

    public void setNcCellLocalId(Long ncCellLocalId) {
        this.ncCellLocalId = ncCellLocalId;
    }

    public Long getNcLteEarfcn() {
        return ncLteEarfcn;
    }

    public void setNcLteEarfcn(Long ncLteEarfcn) {
        this.ncLteEarfcn = ncLteEarfcn;
    }

    public Integer getnCelPriority() {
        return nCelPriority;
    }

    public void setnCelPriority(Integer nCelPriority) {
        this.nCelPriority = nCelPriority;
    }
}
