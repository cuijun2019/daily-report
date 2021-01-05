package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class EUtranRelation {
    
//    @Meta(name = "SubNetwork")
    private Long SubNetwork;
    
//    @Meta(name = "MEID")
    private Long MEID;
    
    @Meta(name = "Sc.eNBId")
    private Long scEnbId;
    
    @Meta(name = "Sc.cellLocalId")
    private Long scCellLocalId;
    
    @Meta(name = "scID1")
    private String scId1;
    
    @Meta(name = "scID2")
    private String scId2;
    
    @Meta(name = "scPCI")
    private Long scPCI;
    
    @Meta(name = "Sc.LteEarfcn")
    private Long mrLteScEarfcn;

    @Meta(name = "userLabel")
    private String userLabel;
    
    @Meta(name = "mcc")
    private String mcc;
    
    @Meta(name = "mnc")
    private String mnc;
    
    @Meta(name = "Nc.eNBId")
    private Long ncENBId;
    
    @Meta(name = "Nc.cellLocalId")
    private Long ncCellLocalId;
    
    @Meta(name = "Nc.ID1")
    private String ncId1;
    
    @Meta(name = "Nc.ID2")
    private String ndId2;
    
    @Meta(name = "Nc.PCI")
    private Long pci;
   
    @Meta(name = "Nc.LteEarfcn")
    private Long ncLteEarfcn;
    
    @Meta(name = "isRemoveAllowed")
    private Boolean isRemoveAllowed;
    
    @Meta(name = "isX2HOAllowed")
    private Boolean isX2HOAllowed;
    
    @Meta(name = "isHOAllowed")
    private Boolean isHOAllowed;
    
    @Meta(name = "isIcicInformationSendAllowed")
    private Boolean isIcicInformationSendAllowed;
    
    @Meta(name = "isLbAllowed")
    private Boolean isLbAllowed;
    
    @Meta(name = "shareCover")
    private Integer shareCover;
    
    @Meta(name = "qofStCell")
    private Long qofStCell;
    
    @Meta(name = "isAnrCreated")
    private Boolean isAnrCreated;
    
    @Meta(name = "nCelPriority")
    private Integer nCelPriority;
    
    @Meta(name = "s1DataFwdFlag")
    private Boolean s1DataFwdFlag;
    
    @Meta(name = "cellIndivOffset")
    private Long cellIndivOffset;
    
    @Meta(name = "stateInd")
    private Integer stateInd;
    
    @Meta(name = "radioMode")
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
