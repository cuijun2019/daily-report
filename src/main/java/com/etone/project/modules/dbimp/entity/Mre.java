package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;

import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class Mre {
    @Meta(name = "id")
    private String id;
    
    @Meta(name = "cellId")
    private String cellId;
    
    @Meta(name = "earfcn")
    private String earfcn;
    
    @Meta(name = "SubFrameNbr")
    private String SubFrameNbr;
    
    @Meta(name = "PRBNbr")
    private String PRBNbr;
    
    @Meta(name = "TimeStamp")
    private Timestamp TimeStamp;
    
    @Meta(name = "MmeUeS1apId")
    private String MmeUeS1apId;
    
    @Meta(name = "MmeGroupId")
    private String MmeGroupId;
    
    @Meta(name = "MmeCode")
    private String MmeCode;
    
    @Meta(name = "EventType")
    private String EventType;
    
    @Meta(name = "C_RNTI")
    private Long C_RNTI;
    
    @Meta(name = "mrId")
    private String mrId;
    
    @Meta(name = "LteScRSRP")
    private Integer LteScRSRP;
    
    @Meta(name = "LteNcRSRP")
    private Integer LteNcRSRP;
    
    @Meta(name = "LteScRSRQ")
    private Integer LteScRSRQ;
    
    @Meta(name = "LteNcRSRQ")
    private Integer LteNcRSRQ;
    
    @Meta(name = "LteScEarfcn")
    private Integer LteScEarfcn;
    
    @Meta(name = "LteScPci")
    private Integer LteScPci;
    
    @Meta(name = "LteNcEarfcn")
    private Integer LteNcEarfcn;
    
    @Meta(name = "LteNcPci")
    private Integer LteNcPci;
    
    @Meta(name = "LteScRTTD")
    private Integer LteScRTTD;
    
    @Meta(name = "LteScPHR")
    private Integer LteScPHR;
    
    @Meta(name = "LteScRIP")
    private Integer LteScRIP;
    
    @Meta(name = "LteScAOA")
    private Integer LteScAOA;
    
    @Meta(name = "LteScPlrULQci1")
    private Integer LteScPlrULQci1;
    
    @Meta(name = "LteScPlrULQci2")
    private Integer LteScPlrULQci2;
    
    @Meta(name = "LteScPlrULQci3")
    private Integer LteScPlrULQci3;
    
    @Meta(name = "LteScPlrULQci4")
    private Integer LteScPlrULQci4;
    
    @Meta(name = "LteScPlrULQci5")
    private Integer LteScPlrULQci5;
    
    @Meta(name = "LteScPlrULQci6")
    private Integer LteScPlrULQci6;
    
    @Meta(name = "LteScPlrULQci7")
    private Integer LteScPlrULQci7;
    
    @Meta(name = "LteScPlrULQci8")
    private Integer LteScPlrULQci8;
    
    @Meta(name = "LteScPlrULQci9")
    private Integer LteScPlrULQci9;
    
    @Meta(name = "LteScPlrDLQci1")
    private Integer LteScPlrDLQci1;
    
    @Meta(name = "LteScPlrDLQci2")
    private Integer LteScPlrDLQci2;
    
    @Meta(name = "LteScPlrDLQci3")
    private Integer LteScPlrDLQci3;
    
    @Meta(name = "LteScPlrDLQci4")
    private Integer LteScPlrDLQci4;
    
    @Meta(name = "LteScPlrDLQci5")
    private Integer LteScPlrDLQci5;
    
    @Meta(name = "LteScPlrDLQci6")
    private Integer LteScPlrDLQci6;
    
    @Meta(name = "LteScPlrDLQci7")
    private Integer LteScPlrDLQci7;
    
    @Meta(name = "LteScPlrDLQci8")
    private Integer LteScPlrDLQci8;
    
    @Meta(name = "LteScPlrDLQci9")
    private Integer LteScPlrDLQci9;
    
    @Meta(name = "LteScSinrUL")
    private Integer LteScSinrUL;
    
    @Meta(name = "GsmNcellBcch")
    private Integer GsmNcellBcch;
    
    @Meta(name = "GsmNcellCarrierRSSI")
    private Integer GsmNcellCarrierRSSI;
    
    @Meta(name = "GsmNcellNcc")
    private Integer GsmNcellNcc;
    
    @Meta(name = "GsmNcellBcc")
    private Integer GsmNcellBcc;
    
    @Meta(name = "TdsPccpchRSCP")
    private Integer TdsPccpchRSCP;
    
    @Meta(name = "TdsNcellUarfcn")
    private Integer TdsNcellUarfcn;
    
    @Meta(name = "TdsCellParameterId")
    private Integer TdsCellParameterId;
    
    
    @Meta(name = "LteScTADV")
    public Integer LteScTAdv;
    
    @Meta(name = "LteScRI1")
    public Integer LteScRI1;
    
    @Meta(name = "LteScRI2")
    public Integer LteScRI2;
    
    @Meta(name = "LteScRI4")
    public Integer LteScRI4;
    
    @Meta(name = "LteScRI8")
    public Integer LteScRI8;
    
    @Meta(name = "LteScPUSCHPRBNum")
    public Integer LteScPUSCHPRBNum;
    
    @Meta(name = "LteScPDSCHPRBNum")
    public Integer LteScPDSCHPRBNum;
    
    @Meta(name = "LteScBSR")
    public Integer LteScBSR;
    
    @Meta(name = "LteSceNBRxTxTimeDiff")
    public Integer LteSceNBRxTxTimeDiff;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(String earfcn) {
        this.earfcn = earfcn;
    }

    public String getSubFrameNbr() {
        return SubFrameNbr;
    }

    public void setSubFrameNbr(String SubFrameNbr) {
        this.SubFrameNbr = SubFrameNbr;
    }

    public String getPRBNbr() {
        return PRBNbr;
    }

    public void setPRBNbr(String PRBNbr) {
        this.PRBNbr = PRBNbr;
    }

    public Timestamp getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Timestamp TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

    public String getMmeUeS1apId() {
        return MmeUeS1apId;
    }

    public void setMmeUeS1apId(String MmeUeS1apId) {
        this.MmeUeS1apId = MmeUeS1apId;
    }

    public String getMmeGroupId() {
        return MmeGroupId;
    }

    public void setMmeGroupId(String MmeGroupId) {
        this.MmeGroupId = MmeGroupId;
    }

    public String getMmeCode() {
        return MmeCode;
    }

    public void setMmeCode(String MmeCode) {
        this.MmeCode = MmeCode;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String EventType) {
        this.EventType = EventType;
    }

    public Long getC_RNTI() {
        return C_RNTI;
    }

    public void setC_RNTI(Long C_RNTI) {
        this.C_RNTI = C_RNTI;
    }

    public String getMrId() {
        return mrId;
    }

    public void setMrId(String mrId) {
        this.mrId = mrId;
    }

    public Integer getLteScRSRP() {
        return LteScRSRP;
    }

    public void setLteScRSRP(Integer LteScRSRP) {
        this.LteScRSRP = LteScRSRP;
    }

    public Integer getLteNcRSRP() {
        return LteNcRSRP;
    }

    public void setLteNcRSRP(Integer LteNcRSRP) {
        this.LteNcRSRP = LteNcRSRP;
    }

    public Integer getLteScRSRQ() {
        return LteScRSRQ;
    }

    public void setLteScRSRQ(Integer LteScRSRQ) {
        this.LteScRSRQ = LteScRSRQ;
    }

    public Integer getLteNcRSRQ() {
        return LteNcRSRQ;
    }

    public void setLteNcRSRQ(Integer LteNcRSRQ) {
        this.LteNcRSRQ = LteNcRSRQ;
    }

    public Integer getLteScEarfcn() {
        return LteScEarfcn;
    }

    public void setLteScEarfcn(Integer LteScEarfcn) {
        this.LteScEarfcn = LteScEarfcn;
    }

    public Integer getLteScPci() {
        return LteScPci;
    }

    public void setLteScPci(Integer LteScPci) {
        this.LteScPci = LteScPci;
    }

    public Integer getLteNcEarfcn() {
        return LteNcEarfcn;
    }

    public void setLteNcEarfcn(Integer LteNcEarfcn) {
        this.LteNcEarfcn = LteNcEarfcn;
    }

    public Integer getLteNcPci() {
        return LteNcPci;
    }

    public void setLteNcPci(Integer LteNcPci) {
        this.LteNcPci = LteNcPci;
    }

    public Integer getLteScRTTD() {
        return LteScRTTD;
    }

    public void setLteScRTTD(Integer LteScRTTD) {
        this.LteScRTTD = LteScRTTD;
    }

    public Integer getLteScPHR() {
        return LteScPHR;
    }

    public void setLteScPHR(Integer LteScPHR) {
        this.LteScPHR = LteScPHR;
    }

    public Integer getLteScRIP() {
        return LteScRIP;
    }

    public void setLteScRIP(Integer LteScRIP) {
        this.LteScRIP = LteScRIP;
    }

    public Integer getLteScAOA() {
        return LteScAOA;
    }

    public void setLteScAOA(Integer LteScAOA) {
        this.LteScAOA = LteScAOA;
    }

    public Integer getLteScPlrULQci1() {
        return LteScPlrULQci1;
    }

    public void setLteScPlrULQci1(Integer LteScPlrULQci1) {
        this.LteScPlrULQci1 = LteScPlrULQci1;
    }

    public Integer getLteScPlrULQci2() {
        return LteScPlrULQci2;
    }

    public void setLteScPlrULQci2(Integer LteScPlrULQci2) {
        this.LteScPlrULQci2 = LteScPlrULQci2;
    }

    public Integer getLteScPlrULQci3() {
        return LteScPlrULQci3;
    }

    public void setLteScPlrULQci3(Integer LteScPlrULQci3) {
        this.LteScPlrULQci3 = LteScPlrULQci3;
    }

    public Integer getLteScPlrULQci4() {
        return LteScPlrULQci4;
    }

    public void setLteScPlrULQci4(Integer LteScPlrULQci4) {
        this.LteScPlrULQci4 = LteScPlrULQci4;
    }

    public Integer getLteScPlrULQci5() {
        return LteScPlrULQci5;
    }

    public void setLteScPlrULQci5(Integer LteScPlrULQci5) {
        this.LteScPlrULQci5 = LteScPlrULQci5;
    }

    public Integer getLteScPlrULQci6() {
        return LteScPlrULQci6;
    }

    public void setLteScPlrULQci6(Integer LteScPlrULQci6) {
        this.LteScPlrULQci6 = LteScPlrULQci6;
    }

    public Integer getLteScPlrULQci7() {
        return LteScPlrULQci7;
    }

    public void setLteScPlrULQci7(Integer LteScPlrULQci7) {
        this.LteScPlrULQci7 = LteScPlrULQci7;
    }

    public Integer getLteScPlrULQci8() {
        return LteScPlrULQci8;
    }

    public void setLteScPlrULQci8(Integer LteScPlrULQci8) {
        this.LteScPlrULQci8 = LteScPlrULQci8;
    }

    public Integer getLteScPlrULQci9() {
        return LteScPlrULQci9;
    }

    public void setLteScPlrULQci9(Integer LteScPlrULQci9) {
        this.LteScPlrULQci9 = LteScPlrULQci9;
    }

    public Integer getLteScPlrDLQci1() {
        return LteScPlrDLQci1;
    }

    public void setLteScPlrDLQci1(Integer LteScPlrDLQci1) {
        this.LteScPlrDLQci1 = LteScPlrDLQci1;
    }

    public Integer getLteScPlrDLQci2() {
        return LteScPlrDLQci2;
    }

    public void setLteScPlrDLQci2(Integer LteScPlrDLQci2) {
        this.LteScPlrDLQci2 = LteScPlrDLQci2;
    }

    public Integer getLteScPlrDLQci3() {
        return LteScPlrDLQci3;
    }

    public void setLteScPlrDLQci3(Integer LteScPlrDLQci3) {
        this.LteScPlrDLQci3 = LteScPlrDLQci3;
    }

    public Integer getLteScPlrDLQci4() {
        return LteScPlrDLQci4;
    }

    public void setLteScPlrDLQci4(Integer LteScPlrDLQci4) {
        this.LteScPlrDLQci4 = LteScPlrDLQci4;
    }

    public Integer getLteScPlrDLQci5() {
        return LteScPlrDLQci5;
    }

    public void setLteScPlrDLQci5(Integer LteScPlrDLQci5) {
        this.LteScPlrDLQci5 = LteScPlrDLQci5;
    }

    public Integer getLteScPlrDLQci6() {
        return LteScPlrDLQci6;
    }

    public void setLteScPlrDLQci6(Integer LteScPlrDLQci6) {
        this.LteScPlrDLQci6 = LteScPlrDLQci6;
    }

    public Integer getLteScPlrDLQci7() {
        return LteScPlrDLQci7;
    }

    public void setLteScPlrDLQci7(Integer LteScPlrDLQci7) {
        this.LteScPlrDLQci7 = LteScPlrDLQci7;
    }

    public Integer getLteScPlrDLQci8() {
        return LteScPlrDLQci8;
    }

    public void setLteScPlrDLQci8(Integer LteScPlrDLQci8) {
        this.LteScPlrDLQci8 = LteScPlrDLQci8;
    }

    public Integer getLteScPlrDLQci9() {
        return LteScPlrDLQci9;
    }

    public void setLteScPlrDLQci9(Integer LteScPlrDLQci9) {
        this.LteScPlrDLQci9 = LteScPlrDLQci9;
    }

    public Integer getLteScSinrUL() {
        return LteScSinrUL;
    }

    public void setLteScSinrUL(Integer LteScSinrUL) {
        this.LteScSinrUL = LteScSinrUL;
    }

    public Integer getGsmNcellBcch() {
        return GsmNcellBcch;
    }

    public void setGsmNcellBcch(Integer GsmNcellBcch) {
        this.GsmNcellBcch = GsmNcellBcch;
    }

    public Integer getGsmNcellCarrierRSSI() {
        return GsmNcellCarrierRSSI;
    }

    public void setGsmNcellCarrierRSSI(Integer GsmNcellCarrierRSSI) {
        this.GsmNcellCarrierRSSI = GsmNcellCarrierRSSI;
    }

    public Integer getGsmNcellNcc() {
        return GsmNcellNcc;
    }

    public void setGsmNcellNcc(Integer GsmNcellNcc) {
        this.GsmNcellNcc = GsmNcellNcc;
    }

    public Integer getGsmNcellBcc() {
        return GsmNcellBcc;
    }

    public void setGsmNcellBcc(Integer GsmNcellBcc) {
        this.GsmNcellBcc = GsmNcellBcc;
    }

    public Integer getTdsPccpchRSCP() {
        return TdsPccpchRSCP;
    }

    public void setTdsPccpchRSCP(Integer TdsPccpchRSCP) {
        this.TdsPccpchRSCP = TdsPccpchRSCP;
    }

    public Integer getTdsNcellUarfcn() {
        return TdsNcellUarfcn;
    }

    public void setTdsNcellUarfcn(Integer TdsNcellUarfcn) {
        this.TdsNcellUarfcn = TdsNcellUarfcn;
    }

    public Integer getTdsCellParameterId() {
        return TdsCellParameterId;
    }

    public void setTdsCellParameterId(Integer TdsCellParameterId) {
        this.TdsCellParameterId = TdsCellParameterId;
    }
    
}
