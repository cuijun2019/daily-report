package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;
import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "Mro_", isUseUserId = true)
public class Mro {
    @Meta(name= "id", exportable = true)
    @ImportEntity
    public String id;
    
    @Meta(name = "小区ID", exportable = true)
    @ImportEntity
    public String cellId;
    
    @Meta(name = "earfcn", exportable = true)
    @ImportEntity
    public String earfcn;
    
    @Meta(name = "SubFrameNbr")
    @ImportEntity
    public String SubFrameNbr;
    
    @Meta(name = "PRBNbr")
    @ImportEntity
    public String PRBNbr;
    
    @Meta(name = "TimeStamp")
    @ImportEntity
    public Timestamp TimeStamp;
    
    @Meta(name = "MmeUeS1apId")
    @ImportEntity
    public String MmeUeS1apId;
    
    @Meta(name = "MmeGroupId")
    @ImportEntity
    public String MmeGroupId;
    
    @Meta(name = "MmeCode")
    @ImportEntity
    public String MmeCode;
    
    @Meta(name = "mrId")
    @ImportEntity(exportFieldWidth= 80)
    public String mrId;
    
    @Meta(name = "LteScRSRP")
    @ImportEntity
    public Integer LteScRSRP;
    
    @Meta(name = "LteNcRSRP")
    @ImportEntity
    public Integer LteNcRSRP;
    
    @Meta(name = "LteScRSRQ")
    @ImportEntity
    public Integer LteScRSRQ;
    
    @Meta(name = "LteNcRSRQ")
    @ImportEntity
    public Integer LteNcRSRQ;
    
    @Meta(name = "LteScTADV")
    @ImportEntity
    public Integer LteScTAdv;
    
    @Meta(name = "LteScRI1")
    @ImportEntity
    public Integer LteScRI1;
    
    @Meta(name = "LteScRI2")
    @ImportEntity
    public Integer LteScRI2;
    
    @Meta(name = "LteScRI4")
    @ImportEntity
    public Integer LteScRI4;
    
    @Meta(name = "LteScRI8")
    @ImportEntity
    public Integer LteScRI8;
    
    @Meta(name = "LteScPUSCHPRBNum")
    @ImportEntity
    public Integer LteScPUSCHPRBNum;
    
    @Meta(name = "LteScPDSCHPRBNum")
    @ImportEntity
    public Integer LteScPDSCHPRBNum;
    
    @Meta(name = "LteScBSR")
    @ImportEntity
    public Integer LteScBSR;
    
    @Meta(name = "LteSceNBRxTxTimeDiff")
    @ImportEntity
    public Integer LteSceNBRxTxTimeDiff;
    
    @Meta(name = "LteScEarfcn")
    @ImportEntity
    public Integer LteScEarfcn;
    
    @Meta(name = "LteScPci")
    @ImportEntity
    public Integer LteScPci;
    
    @Meta(name = "LteNcEarfcn")
    @ImportEntity
    public Integer LteNcEarfcn;
    
    @Meta(name = "LteNcPci")
    @ImportEntity
    public Integer LteNcPci;
    
    @Meta(name = "LteScRTTD")
    @ImportEntity
    public Integer LteScRTTD;
    
    @Meta(name = "LteScPHR")
    @ImportEntity
    public Integer LteScPHR;
    
    @Meta(name = "LteScRIP")
    @ImportEntity
    public Integer LteScRIP;
    
    @Meta(name = "LteScAOA")
    @ImportEntity
    public Integer LteScAOA;
    
    @Meta(name = "LteScPlrULQci1")
    @ImportEntity
    public Integer LteScPlrULQci1;
    
    @Meta(name = "LteScPlrULQci2")
    @ImportEntity
    public Integer LteScPlrULQci2;
    
    @Meta(name = "LteScPlrULQci3")
    @ImportEntity
    public Integer LteScPlrULQci3;
    
    @Meta(name = "LteScPlrULQci4")
    @ImportEntity
    public Integer LteScPlrULQci4;
    
    @Meta(name = "LteScPlrULQci5")
    @ImportEntity
    public Integer LteScPlrULQci5;
    
    @Meta(name = "LteScPlrULQci6")
    @ImportEntity
    public Integer LteScPlrULQci6;
    
    @Meta(name = "LteScPlrULQci7")
    @ImportEntity
    public Integer LteScPlrULQci7;
    
    @Meta(name = "LteScPlrULQci8")
    @ImportEntity
    public Integer LteScPlrULQci8;
    
    @Meta(name = "LteScPlrULQci9")
    @ImportEntity
    public Integer LteScPlrULQci9;
    
    @Meta(name = "LteScPlrDLQci1")
    @ImportEntity
    public Integer LteScPlrDLQci1;
    
    @Meta(name = "LteScPlrDLQci2")
    @ImportEntity
    public Integer LteScPlrDLQci2;
    
    @Meta(name = "LteScPlrDLQci3")
    @ImportEntity
    public Integer LteScPlrDLQci3;
    
    @Meta(name = "LteScPlrDLQci4")
    @ImportEntity
    public Integer LteScPlrDLQci4;
    
    @Meta(name = "LteScPlrDLQci5")
    @ImportEntity
    public Integer LteScPlrDLQci5;
    
    @Meta(name = "LteScPlrDLQci6")
    @ImportEntity
    public Integer LteScPlrDLQci6;
    
    @Meta(name = "LteScPlrDLQci7")
    @ImportEntity
    public Integer LteScPlrDLQci7;
    
    @Meta(name = "LteScPlrDLQci8")
    @ImportEntity
    public Integer LteScPlrDLQci8;
    
    @Meta(name = "LteScPlrDLQci9")
    @ImportEntity
    public Integer LteScPlrDLQci9;
    
    @Meta(name = "LteScSinrUL")
    @ImportEntity
    public Integer LteScSinrUL;
    
    @Meta(name = "GsmNcellBcch")
    @ImportEntity
    public Integer GsmNcellBcch;
    
    @Meta(name = "GsmNcellCarrierRSSI")
    @ImportEntity
    public Integer GsmNcellCarrierRSSI;
    
    @Meta(name = "GsmNcellNcc")
    @ImportEntity
    public Integer GsmNcellNcc;
    
    @Meta(name = "GsmNcellBcc")
    @ImportEntity
    public Integer GsmNcellBcc;
    
    @Meta(name = "TdsPccpchRSCP")
    @ImportEntity
    public Integer TdsPccpchRSCP;
    
    @Meta(name = "TdsNcellUarfcn")
    @ImportEntity
    public Integer TdsNcellUarfcn;
    
    @Meta(name = "TdsCellParameterId")
    @ImportEntity
    public Integer TdsCellParameterId;

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

    public Integer getLteScTADV() {
        return LteScTAdv;
    }

    public void setLteScTADV(Integer LteScTADV) {
        this.LteScTAdv = LteScTADV;
    }

    public Integer getLteScRI1() {
        return LteScRI1;
    }

    public void setLteScRI1(Integer LteScRI1) {
        this.LteScRI1 = LteScRI1;
    }

    public Integer getLteScRI2() {
        return LteScRI2;
    }

    public void setLteScRI2(Integer LteScRI2) {
        this.LteScRI2 = LteScRI2;
    }

    public Integer getLteScRI4() {
        return LteScRI4;
    }

    public void setLteScRI4(Integer LteScRI4) {
        this.LteScRI4 = LteScRI4;
    }

    public Integer getLteScRI8() {
        return LteScRI8;
    }

    public void setLteScRI8(Integer LteScRI8) {
        this.LteScRI8 = LteScRI8;
    }

    public Integer getLteScPUSCHPRBNum() {
        return LteScPUSCHPRBNum;
    }

    public void setLteScPUSCHPRBNum(Integer LteScPUSCHPRBNum) {
        this.LteScPUSCHPRBNum = LteScPUSCHPRBNum;
    }

    public Integer getLteScPDSCHPRBNum() {
        return LteScPDSCHPRBNum;
    }

    public void setLteScPDSCHPRBNum(Integer LteScPDSCHPRBNum) {
        this.LteScPDSCHPRBNum = LteScPDSCHPRBNum;
    }

    public Integer getLteScBSR() {
        return LteScBSR;
    }

    public void setLteScBSR(Integer LteScBSR) {
        this.LteScBSR = LteScBSR;
    }

    public Integer getLteSceNBRxTxTimeDiff() {
        return LteSceNBRxTxTimeDiff;
    }

    public void setLteSceNBRxTxTimeDiff(Integer LteSceNBRxTxTimeDiff) {
        this.LteSceNBRxTxTimeDiff = LteSceNBRxTxTimeDiff;
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
