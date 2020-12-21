package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;
import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "ftbPointsList_GT_Hour#")  //#代表日期，来自数据文件名
public class FtbPointsListGTHour {
    
    @Meta(name = "时间")
    @ImportEntity(importName = "时间", fromDataFileName = true)
    private Timestamp startTime;
    
    @Meta(name = "IMEI")
    @ImportEntity(importName = "IMEI")
    private String vcImei;
    
    @Meta(name = "MSISDN")
    @ImportEntity(importName = "MSISDN")
    private String vcMsisdn;
    
    @Meta(name = "时段")
    @ImportEntity(importName = "时段")
    private Integer period;
    
    @Meta(name = "终端品牌")
    @ImportEntity(importName = "终端品牌", convertRugular = "不详:")
    private String vcTerminalBrand;
    
    @Meta(name = "终端型号")
    @ImportEntity(importName = "终端型号", convertRugular = "不详:")
    private String vcTerminalTypeNo;
    
    @Meta(name = "是否支持TD")
    @ImportEntity(importName = "是否支持TD(1，是；2，否)", convertRugular = "是:1,否:2")
    private Integer vcSupportTD;
    
    @Meta(name = "是否支持WIFI")
    @ImportEntity(importName = "是否支持WIFI(1，是；2，否)", convertRugular = "是:1,否:2")
    private Integer vcSupportWIFI;
    
    @Meta(name = "是否智能机")
    @ImportEntity(importName = "是否智能机(1，是；2，否)", convertRugular = "是:1,否:2")
    private Integer vcSupportSmart;
    
    @Meta(name = "占用网络")
    @ImportEntity(importName = "占用网络", convertRugular = "3G:TD")
    private String vcTakeNet;
    
    @Meta(name = "占用小区ID")
    @ImportEntity(importName = "占用小区ID")
    private String vcTakeCellId;
    
    @Meta(name = "数据业务流量(KB)")
    @ImportEntity(importName = "数据业务流量(KB)")
    private Double intAppFlush;
    
    @Meta(name = "数据业务时长(秒)")
    @ImportEntity(importName = "数据业务时长(秒)")
    private Long intAppTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getVcImei() {
        return vcImei;
    }

    public void setVcImei(String vcImei) {
        this.vcImei = vcImei;
    }

    public String getVcMsisdn() {
        return vcMsisdn;
    }

    public void setVcMsisdn(String vcMsisdn) {
        this.vcMsisdn = vcMsisdn;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getVcTerminalBrand() {
        return vcTerminalBrand;
    }

    public void setVcTerminalBrand(String vcTerminalBrand) {
        this.vcTerminalBrand = vcTerminalBrand;
    }

    public String getVcTerminalTypeNo() {
        return vcTerminalTypeNo;
    }

    public void setVcTerminalTypeNo(String vcTerminalTypeNo) {
        this.vcTerminalTypeNo = vcTerminalTypeNo;
    }

    public Integer getVcSupportTD() {
        return vcSupportTD;
    }

    public void setVcSupportTD(Integer vcSupportTD) {
        this.vcSupportTD = vcSupportTD;
    }

    public Integer getVcSupportWIFI() {
        return vcSupportWIFI;
    }

    public void setVcSupportWIFI(Integer vcSupportWIFI) {
        this.vcSupportWIFI = vcSupportWIFI;
    }

    public Integer getVcSupportSmart() {
        return vcSupportSmart;
    }

    public void setVcSupportSmart(Integer vcSupportSmart) {
        this.vcSupportSmart = vcSupportSmart;
    }

    public String getVcTakeNet() {
        return vcTakeNet;
    }

    public void setVcTakeNet(String vcTakeNet) {
        this.vcTakeNet = vcTakeNet;
    }

    public String getVcTakeCellId() {
        return vcTakeCellId;
    }

    public void setVcTakeCellId(String vcTakeCellId) {
        this.vcTakeCellId = vcTakeCellId;
    }

    public Double getIntAppFlush() {
        return intAppFlush;
    }

    public void setIntAppFlush(Double intAppFlush) {
        this.intAppFlush = intAppFlush;
    }

    public Long getIntAppTime() {
        return intAppTime;
    }

    public void setIntAppTime(Long intAppTime) {
        this.intAppTime = intAppTime;
    }

}
