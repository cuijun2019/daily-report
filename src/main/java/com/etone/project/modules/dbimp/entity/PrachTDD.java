package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "PrachTDD_", isUseUserId = true)
public class PrachTDD {
    
    @Meta(name = "SubNetwork")
    @ImportEntity
    private Long SubNetwork;
    
    @Meta(name = "MEID")
    @ImportEntity
    private Long MEID;
    
    @Meta(name = "eNBId")
    @ImportEntity(importName = "eNBId")
    private Long enbId;
    
    @Meta(name = "cellIdentity")
    @ImportEntity
    private Long cellIdentity;
    
    @Meta(name = "CELLID1")
    @ImportEntity(importName = "CELLID1")
    private String cellId1;
    
    @Meta(name = "CELLID2")
    @ImportEntity(importName = "CELLID2")
    private String cellId2;
    
    @Meta(name = "userLabel")
    @ImportEntity(exportFieldWidth = 100)
    private String userLabel;
    
    @Meta(name = "prachConfigIndex")
    @ImportEntity
    private Long prachConfigIndex;
    
    @Meta(name = "prachFreqOffset")
    @ImportEntity
    private Long prachFreqOffset;
    
    @Meta(name = "highSpeedFlag")
    @ImportEntity(convertRugular= "0:false,1:true")
    private Boolean highSpeedFlag;
    
    @Meta(name = "rootSequenceIndex")
    @ImportEntity
    private Long rootSequenceIndex;
    
    @Meta(name = "ncs")
    @ImportEntity
    private Long ncs;
    
    @Meta(name = "numberOfRAPreambles")
    @ImportEntity
    private Long numberOfRAPreambles;
    
    @Meta(name = "sizeOfRAPreamblesGroupA")
    @ImportEntity
    private Long sizeOfRAPreamblesGroupA;

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

    public Long getCellIdentity() {
        return cellIdentity;
    }

    public void setCellIdentity(Long cellIdentity) {
        this.cellIdentity = cellIdentity;
    }

    public Long getPrachConfigIndex() {
        return prachConfigIndex;
    }

    public void setPrachConfigIndex(Long prachConfigIndex) {
        this.prachConfigIndex = prachConfigIndex;
    }

    public Long getPrachFreqOffset() {
        return prachFreqOffset;
    }

    public void setPrachFreqOffset(Long prachFreqOffset) {
        this.prachFreqOffset = prachFreqOffset;
    }

    public Boolean getHighSpeedFlag() {
        return highSpeedFlag;
    }

    public void setHighSpeedFlag(Boolean highSpeedFlag) {
        this.highSpeedFlag = highSpeedFlag;
    }

    public Long getRootSequenceIndex() {
        return rootSequenceIndex;
    }

    public void setRootSequenceIndex(Long rootSequenceIndex) {
        this.rootSequenceIndex = rootSequenceIndex;
    }

    public Long getNcs() {
        return ncs;
    }

    public void setNcs(Long ncs) {
        this.ncs = ncs;
    }

    public Long getNumberOfRAPreambles() {
        return numberOfRAPreambles;
    }

    public void setNumberOfRAPreambles(Long numberOfRAPreambles) {
        this.numberOfRAPreambles = numberOfRAPreambles;
    }

    public Long getSizeOfRAPreamblesGroupA() {
        return sizeOfRAPreamblesGroupA;
    }

    public void setSizeOfRAPreamblesGroupA(Long sizeOfRAPreamblesGroupA) {
        this.sizeOfRAPreamblesGroupA = sizeOfRAPreamblesGroupA;
    }

    public Long getEnbId() {
        return enbId;
    }

    public void setEnbId(Long enbId) {
        this.enbId = enbId;
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

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }
}
