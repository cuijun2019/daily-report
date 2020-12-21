package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;

/**
 *工参F频段
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "dtbWorkerParticipationFrequency_", isUseUserId = true)
public class DtbWorkerParticipationFrequency {
    
    @Meta(name = "MCC")
    @ImportEntity(importName= "MCC")
    private Long Mcc;
    
    @Meta(name = "MNC")
    @ImportEntity(importName = "MNC")
    private Long Mnc;
    
    @Meta(name = "TAC")
    @ImportEntity(importName = "TAC")
    private Long Tac;
    
    @Meta(name = "是开否开通")
    @ImportEntity(importName = "是开通开通", exportFieldWidth = 50)
    private String OpenState;
    
//    @Meta(name = "工参是更新更新")
    @ImportEntity(importName = "工参是更新更新", exportFieldWidth = 50) 
    private String UpdateState;
    
//    @Meta(name = "本期规划")
    @ImportEntity(importName = "本期规划", exportFieldWidth = 50)
    private String ThisPlan;
    
    @Meta(name = "ENODEBID")
    @ImportEntity(importName = "ENODEBID")
    private Long EnodeBId;
    
//    @Meta(name = "NodeBID(TD-SCDMA)")
    @ImportEntity(importName = "NodeBID(TD-SCDMA)")
    private Long NodeBId;
    
    @Meta(name = "ENODEBName")
    @ImportEntity(importName = "ENODEBName", exportFieldWidth = 50)
    private String EnodeBName;
    
//    @Meta(name = "规划ENODEBName")
    @ImportEntity(importName = "规划ENODEBName", exportFieldWidth = 50)
    private String PlanEnodeBName;
    
//    @Meta(name = "NODEBName（TD-SCDMA）")
    @ImportEntity(importName = "NODEBName（TD-SCDMA）", exportFieldWidth = 50)
    private String NodeBName;
    
    @Meta(name = "行政区")
    @ImportEntity(importName = "行政区")
    private String Area;

    @Meta(name = "行政区经度")
    @ImportEntity(importName = "行政区经度")
    private String AreaLonb;

    @Meta(name = "行政区纬度")
    @ImportEntity(importName = "行政区纬度")
    private String AreaLatb;

    @Meta(name = "网格")
    @ImportEntity(importName = "网格")
    private String Grid;

    @Meta(name = "网格经度")
    @ImportEntity(importName = "网格经度")
    private String GridLonb;

    @Meta(name = "网格纬度")
    @ImportEntity(importName = "网格纬度")
    private String GridLatb;
    
//    @Meta(name = "分簇")
    @ImportEntity(importName = "分簇")
    private String Custering;
    
//    @Meta(name = "ENODEBtype")
    @ImportEntity(importName = "ENODEBtype")
    private String EnodeBType;
    
    @Meta(name = "LONB")
    @ImportEntity(importName = "LONB")
    private Double Lonb;
    
    @Meta(name = "LATB")
    @ImportEntity(importName = "LATB")
    private Double Latb;
    
    @Meta(name = "SectorID")
    @ImportEntity(importName = "SectorID", exportFieldWidth = 100)
    private String SectorID;
    
    @Meta(name = "CELLID")
    @ImportEntity(importName = "CELLID")
    private String CellId;
    
    @Meta(name = "CELLID2")
    @ImportEntity(importName = "CELLID2")
    private String cellId2;
    
    @Meta(name = "CELLNAME")
    @ImportEntity(importName = "CELLNAME", exportFieldWidth = 50)
    private String CellName;
    
//    @Meta(name = "规划CELLNAME")
    @ImportEntity(importName = "规划CELLNAME", exportFieldWidth = 50)
    private String PlanCellName;
    
//    @Meta(name = "CELLNAME（TD-SCDMA）")
    @ImportEntity(importName = "CELLNAME（TD-SCDMA）", exportFieldWidth = 50)
    private String CellNameTD;
    
    @Meta(name = "频点属性")
    @ImportEntity(importName = "频点属性", exportFieldWidth = 50)
    private String PowerInfo;
    
    @Meta(name = "PCARRIERFR")
    @ImportEntity(importName = "PCARRIERFR")
    private Long Pcarrierfr;
    
//    @Meta(name = "CELLTYPE")
    @ImportEntity(importName = "CELLTYPE")
    private String CellType;
    
    @Meta(name = "覆盖类型")
    @ImportEntity(importName = "覆盖类型")
    private String CoverType;
    
//    @Meta(name = "LONC")
    @ImportEntity(importName = "LONC")
    private Double Lonc;
    
//    @Meta(name = "LATC")
    @ImportEntity(importName = "LATC")
    private Double Latc;
    
//    @Meta(name = "规划PCI")
    @ImportEntity(importName = "规划PCI")
    private Long PlanPci;
    
    @Meta(name = "PCI")
    @ImportEntity(importName = "PCI")
    private Long Pci;
    
    @Meta(name = "MOD/3")
    @ImportEntity(importName = "MOD/3")
    private Long Mod3;
    
    @Meta(name = "Azimuth")
    @ImportEntity(importName = "Azimuth")
    private Long Azimuth;
    
    @Meta(name = "MTILT")
    @ImportEntity(importName = "MTILT")
    private Long Mtilt;
    
    @Meta(name = "ETILT")
    @ImportEntity(importName = "ETILT", convertRugular = "-:")
    private Long Etilt;
    
    @Meta(name = "总下倾角")
    @ImportEntity(importName = "总下倾角")
    private Long TopAngle;
    
    @Meta(name = "ANTHIGH(小数型)")
    @ImportEntity(importName = "ANTHIGH(小数型)")
    private Double AntHighPoint;
    
    @Meta(name = "ANTHIGH")
    @ImportEntity(importName = "ANTHIGH")
    private Double AntHigh;
    
    @Meta(name = "AntennaGAIN")
    @ImportEntity(importName = "AntennaGAIN", exportFieldWidth = 50)
    private String AntennaGAIN;
    
//    @Meta(name = "天线调整状态")
    @ImportEntity(importName = "天线调整状态", exportFieldWidth = 50)
    private String AntennaState;
    
//    @Meta(name = "coverCharacter")
    @ImportEntity(importName = "coverCharacter", exportFieldWidth = 50)
    private String CoverCharacter;
    
//    @Meta(name = "ANTTYPE")
    @ImportEntity(importName = "ANTTYPE", exportFieldWidth = 50)
    private String AntType;
    
//    @Meta(name = "HBWD")
    @ImportEntity(importName = "HBWD", exportFieldWidth = 50)
    private String Hbwd;
    
//    @Meta(name = "VBWD")
    @ImportEntity(importName = "VBWD", exportFieldWidth = 50)
    private String Vbwd;
    
//    @Meta(name = "SRHIGH")
    @ImportEntity(importName = "SRHIGH", exportFieldWidth = 50)
    private String SrHigh;
    
//    @Meta(name = "COVERTYPE")
    @ImportEntity(importName = "COVERTYPE", exportFieldWidth = 50)
    private String CoverType2;
    
//    @Meta(name = "PANTGAIN")
    @ImportEntity(importName = "PANTGAIN", exportFieldWidth = 50)
    private String Pantgain;
    
//    @Meta(name = "RFPOWER")
    @ImportEntity(importName = "RFPOWER")
    private Double RfPower;
    
//    @Meta(name = "RSPOWER")
    @ImportEntity(importName = "RSPOWER")
    private Double RsPower;
    
//    @Meta(name = "NBP_REUSEDIS1")
    @ImportEntity(importName = "NBP_REUSEDIS1")
    private Double NbpReusedis1;
    
//    @Meta(name = "FP_SAFEDIS")
    @ImportEntity(importName = "FP_SAFEDIS")
    private Double FpSafedis;
    
//    @Meta(name = "归属网管")
    @ImportEntity(importName = "归属网管")
    private Long BelongNetManage;
    
//    @Meta(name = "PA")
    @ImportEntity(importName = "PA")
    private Long Pa;
    
//    @Meta(name = "PB")
    @ImportEntity(importName = "PB")
    private Long Pb;
    
//    @Meta(name = "IP")
    @ImportEntity(importName = "IP", exportFieldWidth = 50)
    private String Ip;
    
    @Meta(name = "邻基站enodeId")
    @ImportEntity(importName = "邻基站enodeId")
    private Long nenodebId;
    
    @Meta(name = "邻基站距离")
    @ImportEntity(importName = "邻基站距离")
    private Double ndistance;

    public Long getMcc() {
        return Mcc;
    }

    public void setMcc(Long Mcc) {
        this.Mcc = Mcc;
    }

    public Long getMnc() {
        return Mnc;
    }

    public void setMnc(Long Mnc) {
        this.Mnc = Mnc;
    }

    public Long getTac() {
        return Tac;
    }

    public void setTac(Long Tac) {
        this.Tac = Tac;
    }

    public String getOpenState() {
        return OpenState;
    }

    public void setOpenState(String OpenState) {
        this.OpenState = OpenState;
    }

    public String getUpdateState() {
        return UpdateState;
    }

    public void setUpdateState(String UpdateState) {
        this.UpdateState = UpdateState;
    }

    public String getThisPlan() {
        return ThisPlan;
    }

    public void setThisPlan(String ThisPlan) {
        this.ThisPlan = ThisPlan;
    }

    public Long getEnodeBId() {
        return EnodeBId;
    }

    public void setEnodeBId(Long EnodeBId) {
        this.EnodeBId = EnodeBId;
    }

    public Long getNodeBId() {
        return NodeBId;
    }

    public void setNodeBId(Long NodeBId) {
        this.NodeBId = NodeBId;
    }

    public String getEnodeBName() {
        return EnodeBName;
    }

    public void setEnodeBName(String EnodeBName) {
        this.EnodeBName = EnodeBName;
    }

    public String getPlanEnodeBName() {
        return PlanEnodeBName;
    }

    public void setPlanEnodeBName(String PlanEnodeBName) {
        this.PlanEnodeBName = PlanEnodeBName;
    }

    public String getNodeBName() {
        return NodeBName;
    }

    public void setNodeBName(String NodeBName) {
        this.NodeBName = NodeBName;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getCustering() {
        return Custering;
    }

    public void setCustering(String Custering) {
        this.Custering = Custering;
    }

    public String getEnodeBType() {
        return EnodeBType;
    }

    public void setEnodeBType(String EnodeBType) {
        this.EnodeBType = EnodeBType;
    }

    public Double getLonb() {
        return Lonb;
    }

    public void setLonb(Double Lonb) {
        this.Lonb = Lonb;
    }

    public Double getLatb() {
        return Latb;
    }

    public void setLatb(Double Latb) {
        this.Latb = Latb;
    }

    public String getSectorID() {
        return SectorID;
    }

    public void setSectorID(String SectorID) {
        this.SectorID = SectorID;
    }

    public String getCellId() {
        return CellId;
    }

    public void setCellId(String CellId) {
        this.CellId = CellId;
    }

    public String getCellId2() {
        return cellId2;
    }

    public void setCellId2(String cellId2) {
        this.cellId2 = cellId2;
    }

    public String getCellName() {
        return CellName;
    }

    public void setCellName(String CellName) {
        this.CellName = CellName;
    }

    public String getPlanCellName() {
        return PlanCellName;
    }

    public void setPlanCellName(String PlanCellName) {
        this.PlanCellName = PlanCellName;
    }

    public String getCellNameTD() {
        return CellNameTD;
    }

    public void setCellNameTD(String CellNameTD) {
        this.CellNameTD = CellNameTD;
    }

    public String getPowerInfo() {
        return PowerInfo;
    }

    public void setPowerInfo(String PowerInfo) {
        this.PowerInfo = PowerInfo;
    }

    public Long getPcarrierfr() {
        return Pcarrierfr;
    }

    public void setPcarrierfr(Long Pcarrierfr) {
        this.Pcarrierfr = Pcarrierfr;
    }

    public String getCellType() {
        return CellType;
    }

    public void setCellType(String CellType) {
        this.CellType = CellType;
    }

    public String getCoverType() {
        return CoverType;
    }

    public void setCoverType(String CoverType) {
        this.CoverType = CoverType;
    }

    public Double getLonc() {
        return Lonc;
    }

    public void setLonc(Double Lonc) {
        this.Lonc = Lonc;
    }

    public Double getLatc() {
        return Latc;
    }

    public void setLatc(Double Latc) {
        this.Latc = Latc;
    }

    public Long getPlanPci() {
        return PlanPci;
    }

    public void setPlanPci(Long PlanPci) {
        this.PlanPci = PlanPci;
    }

    public Long getPci() {
        return Pci;
    }

    public void setPci(Long Pci) {
        this.Pci = Pci;
    }

    public Long getMod3() {
        return Mod3;
    }

    public void setMod3(Long Mod3) {
        this.Mod3 = Mod3;
    }

    public Long getAzimuth() {
        return Azimuth;
    }

    public void setAzimuth(Long Azimuth) {
        this.Azimuth = Azimuth;
    }

    public Long getMtilt() {
        return Mtilt;
    }

    public void setMtilt(Long Mtilt) {
        this.Mtilt = Mtilt;
    }

    public Long getEtilt() {
        return Etilt;
    }

    public void setEtilt(Long Etilt) {
        this.Etilt = Etilt;
    }

    public Long getTopAngle() {
        return TopAngle;
    }

    public void setTopAngle(Long TopAngle) {
        this.TopAngle = TopAngle;
    }

    public Double getAntHighPoint() {
        return AntHighPoint;
    }

    public void setAntHighPoint(Double AntHighPoint) {
        this.AntHighPoint = AntHighPoint;
    }

    public Double getAntHigh() {
        return AntHigh;
    }

    public void setAntHigh(Double AntHigh) {
        this.AntHigh = AntHigh;
    }

    public String getAntennaGAIN() {
        return AntennaGAIN;
    }

    public void setAntennaGAIN(String AntennaGAIN) {
        this.AntennaGAIN = AntennaGAIN;
    }

    public String getAntennaState() {
        return AntennaState;
    }

    public void setAntennaState(String AntennaState) {
        this.AntennaState = AntennaState;
    }

    public String getCoverCharacter() {
        return CoverCharacter;
    }

    public void setCoverCharacter(String CoverCharacter) {
        this.CoverCharacter = CoverCharacter;
    }

    public String getAntType() {
        return AntType;
    }

    public void setAntType(String AntType) {
        this.AntType = AntType;
    }

    public String getHbwd() {
        return Hbwd;
    }

    public void setHbwd(String Hbwd) {
        this.Hbwd = Hbwd;
    }

    public String getVbwd() {
        return Vbwd;
    }

    public void setVbwd(String Vbwd) {
        this.Vbwd = Vbwd;
    }

    public String getSrHigh() {
        return SrHigh;
    }

    public void setSrHigh(String SrHigh) {
        this.SrHigh = SrHigh;
    }

    public String getCoverType2() {
        return CoverType2;
    }

    public void setCoverType2(String CoverType2) {
        this.CoverType2 = CoverType2;
    }

    public String getPantgain() {
        return Pantgain;
    }

    public void setPantgain(String Pantgain) {
        this.Pantgain = Pantgain;
    }

    public Double getRfPower() {
        return RfPower;
    }

    public void setRfPower(Double RfPower) {
        this.RfPower = RfPower;
    }

    public Double getRsPower() {
        return RsPower;
    }

    public void setRsPower(Double RsPower) {
        this.RsPower = RsPower;
    }

    public Double getNbpReusedis1() {
        return NbpReusedis1;
    }

    public void setNbpReusedis1(Double NbpReusedis1) {
        this.NbpReusedis1 = NbpReusedis1;
    }

    public Double getFpSafedis() {
        return FpSafedis;
    }

    public void setFpSafedis(Double FpSafedis) {
        this.FpSafedis = FpSafedis;
    }

    public Long getBelongNetManage() {
        return BelongNetManage;
    }

    public void setBelongNetManage(Long BelongNetManage) {
        this.BelongNetManage = BelongNetManage;
    }

    public Long getPa() {
        return Pa;
    }

    public void setPa(Long Pa) {
        this.Pa = Pa;
    }

    public Long getPb() {
        return Pb;
    }

    public void setPb(Long Pb) {
        this.Pb = Pb;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String Ip) {
        this.Ip = Ip;
    }

    public Long getNenodebId() {
        return nenodebId;
    }

    public void setNenodebId(Long nenodebId) {
        this.nenodebId = nenodebId;
    }

    public Double getNdistance() {
        return ndistance;
    }

    public void setNdistance(Double ndistance) {
        this.ndistance = ndistance;
    }

    public String getAreaLonb() {
        return AreaLonb;
    }

    public void setAreaLonb(String areaLonb) {
        AreaLonb = areaLonb;
    }

    public String getAreaLatb() {
        return AreaLatb;
    }

    public void setAreaLatb(String areaLatb) {
        AreaLatb = areaLatb;
    }

    public String getGrid() {
        return Grid;
    }

    public void setGrid(String grid) {
        Grid = grid;
    }

    public String getGridLonb() {
        return GridLonb;
    }

    public void setGridLonb(String gridLonb) {
        GridLonb = gridLonb;
    }

    public String getGridLatb() {
        return GridLatb;
    }

    public void setGridLatb(String gridLatb) {
        GridLatb = gridLatb;
    }
}
