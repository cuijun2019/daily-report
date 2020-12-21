package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;
import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "ftbServerCellHour_ltekpi_", isUseUserId = true)
public class ServerCellHour {
    
//    @Meta(name = "序号")
    @ImportEntity(importName = "序号", primarykey = true)
    private Integer id;
    
    @Meta(name = "开始时间")
    @ImportEntity(importName = "开始时间")
    private Timestamp startTime;
    
    @Meta(name = "结束时间")
    @ImportEntity(importName = "结束时间")
    private Timestamp endTime;
    
//    @Meta(name = "查询粒度")
    @ImportEntity(importName = "查询粒度")
    private String granularity;
    
    @Meta(name = "小区名称")
    @ImportEntity(importName = "小区名称", exportFieldWidth= 100)
    private String cellName;
    
    @Meta(name = "ENODEBID")
    @ImportEntity(importName = "ENODEBID")
    private Long enodeBID;
    
    @Meta(name = "ENODEB名称")
    @ImportEntity(importName = "ENODEB名称")
    private String enodeBName;
    
//    @Meta(name = "SectorID")
    @ImportEntity(importName = "SectorID")
    private Integer sectorID;
    
    @Meta(name = "CELLID")
    @ImportEntity(importName = "CELLID")
    private String cellId1;
    
    @Meta(name = "小区ID")
    @ImportEntity(importName = "小区ID")
    private String cellId;
    
    @Meta(name = "[TDD]RB0到RB9最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB0到RB9最大噪声干扰(分贝毫瓦)")
    private Double rb0ToRb9MaximumNoise;
    
    @Meta(name = "[TDD]RB0到RB9平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB0到RB9平均噪声干扰(分贝毫瓦)")
    private Double rb0ToRb9avgNoise;
    
    @Meta(name = "[TDD]RB10到RB19最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB10到RB19最大噪声干扰(分贝毫瓦)")
    private Double rb10ToRb19MaximumNoise;
    
    @Meta(name = "[TDD]RB10到RB19平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB10到RB19平均噪声干扰(分贝毫瓦)")
    private Double rb10ToRb19avgNoise;
    
    @Meta(name = "[TDD]RB20到RB29最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB20到RB29最大噪声干扰(分贝毫瓦)")
    private Double rb20ToRb29MaximumNoise;
    
    @Meta(name = "[TDD]RB20到RB29平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB20到RB29平均噪声干扰(分贝毫瓦)")
    private Double rb20ToRb29avgNoise;
    
    @Meta(name = "[TDD]RB30到RB39最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB30到RB39最大噪声干扰(分贝毫瓦)")
    private Double rb30ToRb39MaximumNoise;
    
    @Meta(name = "[TDD]RB30到RB39平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB30到RB39平均噪声干扰(分贝毫瓦)")
    private Double rb30ToRb39avgNoise;
    
    @Meta(name = "[TDD]RB40到RB49最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB40到RB49最大噪声干扰(分贝毫瓦)")
    private Double rboT40Rb49MaximumNoise;
    
    @Meta(name = "[TDD]RB40到RB49平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB40到RB49平均噪声干扰(分贝毫瓦)")
    private Double rb40ToRb49avgNoise;
    
    @Meta(name = "[TDD]RB50到RB59最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB50到RB59最大噪声干扰(分贝毫瓦)")
    private Double rb50ToRb59MaximumNoise;
    
    @Meta(name = "[TDD]RB50到RB59平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB50到RB59平均噪声干扰(分贝毫瓦)")
    private Double rb50ToRb59avgNoise;
    
    @Meta(name = "[TDD]RB60到RB69最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB60到RB69最大噪声干扰(分贝毫瓦)")
    private Double rb60ToRb69MaximumNoise;
    
    @Meta(name = "[TDD]RB60到RB69平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB60到RB69平均噪声干扰(分贝毫瓦)")
    private Double rb60ToRb69avgNoise;
    
    @Meta(name = "[TDD]RB70到RB79最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB70到RB79最大噪声干扰(分贝毫瓦)")
    private Double rb70ToRb79MaximumNoise;
    
    @Meta(name = "[TDD]RB70到RB79平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB70到RB79平均噪声干扰(分贝毫瓦)")
    private Double rb70ToRb79avgNoise;
    
    @Meta(name = "[TDD]RB80到RB89最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB80到RB89最大噪声干扰(分贝毫瓦)")
    private Double rb80ToRb89MaximumNoise;
    
    @Meta(name = "[TDD]RB80到RB89平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB80到RB89平均噪声干扰(分贝毫瓦)")
    private Double rb80ToRb89avgNoise;
    
    @Meta(name = "[TDD]RB90到RB99最大噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB90到RB99最大噪声干扰(分贝毫瓦)")
    private Double rb90ToRb99MaximumNoise;
    
    @Meta(name = "TDD]RB90到RB99平均噪声干扰(分贝毫瓦)")
    @ImportEntity(importName = "[TDD]RB90到RB99平均噪声干扰(分贝毫瓦)")
    private Double rb90ToRb99avgNoise;
    
    @Meta(name = "修正-RRC连接建立请求次数")
    @ImportEntity(importName = "修正-RRC连接建立请求次数")
    private Long rrcConnReqCount;
    
    @Meta(name = "修正-RRC连接建立成功次数")
    @ImportEntity(importName = "修正-RRC连接建立成功次数")
    private Long rrcConnSuccessCount;
    
    @Meta(name = "修正-RRC连接建立成功率")
    @ImportEntity(importName = "修正-RRC连接建立成功率", convertRugular = "%:")
    private Double rrcConnSuccessPercent;
    
    @Meta(name = "修正-其它类型的RRC连接重建立请求次数")
    @ImportEntity(importName = "修正-其它类型的RRC连接重建立请求次数")
    private Long otherRrcReConnReqCount;
    
    @Meta(name = "修正-切换类型的RRC连接重建立请求次数")
    @ImportEntity(importName = "修正-切换类型的RRC连接重建立请求次数")
    private Long allTypeRrcReConnReqCount;
    
    @Meta(name = "修正-重配置类型的RRC连接重建立请求次数")
    @ImportEntity(importName = "修正-重配置类型的RRC连接重建立请求次数")
    private Long reConfigRrcReConnReqCount;
    
    @Meta(name = "修正-切换失败触发的RRC重建比率")
    @ImportEntity(importName = "修正-切换失败触发的RRC重建比率", convertRugular = "%:")
    private Double changeFailTriggerRrcRebuildPercent;
    
    @Meta(name = "修正-定时器超时导致的RRC连接建立失败次数")
    @ImportEntity(importName = "修正-定时器超时导致的RRC连接建立失败次数")
    private Long timerTimeoutResultInConnFailCount;
    
    @Meta(name = "修正-其他原因导致的RRC连接建立失败次数")
    @ImportEntity(importName = "修正-其他原因导致的RRC连接建立失败次数")
    private Long otherReasonResultInConnFileCount;
    
    @Meta(name = "修正-eNB接纳失败导致的RRC连接建立失败次数")
    @ImportEntity(importName = "修正-eNB接纳失败导致的RRC连接建立失败次数")
    private Long enbAcceptFailResultInConnFailCount;
    
    @Meta(name = "修正-定时器超时导致的RRC连接建立失败比率")
    @ImportEntity(importName = "修正-定时器超时导致的RRC连接建立失败比率", convertRugular = "%:")
    private Double timerTimeoutResultInConnFailPercent;
    
    @Meta(name = "修正-其他原因导致的RRC连接建立失败比率")
    @ImportEntity(importName = "修正-其他原因导致的RRC连接建立失败比率", convertRugular = "%:")
    private Double otherReasonResultInConnFilePsercent;
    
    @Meta(name = "修正-ENB接纳失败导致的RRC连接建立失败比率")
    @ImportEntity(importName = "修正-ENB接纳失败导致的RRC连接建立失败比率", convertRugular = "%:")
    private Double enbAcceptFailResultInConnFailPercent;
    
    @Meta(name = "修正-E-RAB拥塞率")
    @ImportEntity(importName = "修正-E-RAB拥塞率", convertRugular = "%:")
    private Double erabJamPercent;
    
    @Meta(name = "修正-E-RAB建立成功总数")
    @ImportEntity(importName = "修正-E-RAB建立成功总数")
    private Long erabBuildSuccessCount;
    
    @Meta(name = "修正-E-RAB掉线率")
    @ImportEntity(importName = "修正-E-RAB掉线率", convertRugular = "%:")
    private Double erabOfflinePercent;
    
    @Meta(name = "修正-切换失败导致的E-RAB异常释放次数")
    @ImportEntity(importName = "修正-切换失败导致的E-RAB异常释放次数")
    private Long changeFailResultInERabExceptionCount;
    
    @Meta(name = "修正-网络拥塞导致的E-RAB异常释放次数")
    @ImportEntity(importName = "修正-网络拥塞导致的E-RAB异常释放次数")
    private Long netJamResultInERabExceptionCount;
    
    @Meta(name = "修正-无线问题导致的E-RAB异常释放次数")
    @ImportEntity(importName = "修正-无线问题导致的E-RAB异常释放次数")
    private Long wirelessResultInERabExceptionCount;
    
    @Meta(name = "修正-核心网异常原因导致的E-RAB异常释放次数")
    @ImportEntity(importName = "修正-核心网异常原因导致的E-RAB异常释放次数")
    private Long coreNetExceptiionResultInERabExceptionCount;
    
    @Meta(name = "修正-切换失败导致的E-RAB异常释放比率")
    @ImportEntity(importName = "修正-切换失败导致的E-RAB异常释放比率", convertRugular = "%:")
    private Double changeFailResultInERabExceptionPercent;
    
    @Meta(name = "修正-网络拥塞导致的E-RAB异常释放比率")
    @ImportEntity(importName = "修正-网络拥塞导致的E-RAB异常释放比率", convertRugular = "%:")
    private Double netJamResultInERabExceptionPercent;
    
    @Meta(name = "修正-核心网异常原因导致的E-RAB异常释放比率")
    @ImportEntity(importName = "修正-核心网异常原因导致的E-RAB异常释放比率", convertRugular = "%:")
    private Double coreNetExceptiionResultInERabExceptionPercent;
    
    @Meta(name = "修正-无线链路失败导致的E-RAB异常释放比率")
    @ImportEntity(importName = "修正-无线链路失败导致的E-RAB异常释放比率", convertRugular = "%:")
    private Double wirelessResultInERabExceptionPercent;
    
    @Meta(name = "修正-MAC层上行误块率*")
    @ImportEntity(importName = "修正-MAC层上行误块率*", convertRugular = "%:")
    private Double macUpWrongPercent;
    
    @Meta(name = "修正-MAC层下行误块率*")
    @ImportEntity(importName = "修正-MAC层下行误块率*", convertRugular = "%:")
    private Double macDownWrongPercent;
    
    @Meta(name = "MR-小区空口上行业务字节数（KB）")
    @ImportEntity(importName = "MR-小区空口上行业务字节数（KB）")
    private Double cellUpThruput;
    
    @Meta(name = "MR-小区空口下行业务字节数（KB）")
    @ImportEntity(importName = "MR-小区空口下行业务字节数（KB）")
    private Double cellDownThruput;
    
    @Meta(name = "修正-上行业务信道PRB利用率")
    @ImportEntity(importName = "修正-上行业务信道PRB利用率", convertRugular = "%:")
    private Double upBusinessChannelPRBUsage;
    
    @Meta(name = "修正-下行业务信道PRB利用率")
    @ImportEntity(importName = "修正-下行业务信道PRB利用率", convertRugular = "%:")
    private Double downBusinessChannelPRBUsage;
    
    @Meta(name = "修正-上行初始HARQ重传比率")
    @ImportEntity(importName = "修正-上行初始HARQ重传比率", convertRugular = "%:")
    private Double upInitHARQReSentPercent;
    
    @Meta(name = "修正-下行初始HARQ重传比率")
    @ImportEntity(importName = "修正-下行初始HARQ重传比率", convertRugular = "%:")
    private Double downInitHARQReSentPercent;
    
    @Meta(name = "修正-下行双流占比")
    @ImportEntity(importName = "修正-下行双流占比", convertRugular = "%:")
    private Double downDoubleTrafficPercent;
    
    @Meta(name = "修正-上行MSC QPSK编码占比")
    @ImportEntity(importName = "修正-上行MSC QPSK编码占比", convertRugular = "%:")
    private Double upMscQpskEncodePercent;
    
    @Meta(name = "修正-下行MSC QPSK编码占比")
    @ImportEntity(importName = "修正-下行MSC QPSK编码占比", convertRugular = "%:")
    private Double downMscQpskEncodePercent;
    
    @Meta(name = "MR-RRC连接建立最大用户数")
    @ImportEntity(importName = "MR-RRC连接建立最大用户数")
    private Long onlineUserCount;
    
    @Meta(name = "修正-同频切换出成功率")
    @ImportEntity(importName = "修正-同频切换出成功率", convertRugular = "%:")
    private Double sameChannelChangeSuccessPercent;
    
    @Meta(name = "修正-异频切换出成功率")
    @ImportEntity(importName = "修正-异频切换出成功率", convertRugular = "%:")
    private Double diffChannelChangeSuccessPercent;
    
    @Meta(name = "修正-异频切换出执行请求次数")
    @ImportEntity(importName = "修正-异频切换出执行请求次数")
    private Long diffChannelExecReqCount;
    
    @Meta(name = "修正-异频切换出执行成功次数")
    @ImportEntity(importName = "修正-异频切换出执行成功次数")
    private Long diffchannelExecSuccessCount;
    
    @Meta(name = "修正-小区完好率")
    @ImportEntity(importName = "修正-小区完好率", convertRugular = "%:")
    private Double cellCompletionRate;
    
    @Meta(name = "修正-上行每PRB承载效率（bit/PRB）")
    @ImportEntity(importName = "修正-上行每PRB承载效率（bit/PRB）")
    private Double upPrbEfficiency;
    
    @Meta(name = "修正-下行每PRB承载效率（bit/PRB）")
    @ImportEntity(importName = "修正-下行每PRB承载效率（bit/PRB）")
    private Double downPrbEfficiency;
    
    @Meta(name = "同频切换出执行请求次数")
    @ImportEntity(importName = "同频切换出执行请求次数")
    private Long sameChannelChangeExecReqCount;
    
    @Meta(name = "同频切换出执行成功次数")
    @ImportEntity(importName = "同频切换出执行成功次数")
    private Long sameChannelChangeExecSuccessCount;
    
    @Meta(name = "修正-上行丢包率")
    @ImportEntity(importName = "修正-上行丢包率", convertRugular = "%:")
    private Double upLossRate;
    
    @Meta(name = "修正-下行丢包率")
    @ImportEntity(importName = "修正-下行丢包率", convertRugular = "%:")
    private Double downLossRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getCellId1() {
        return cellId1;
    }

    public void setCellId1(String cellId1) {
        this.cellId1 = cellId1;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public Long getEnodeBID() {
        return enodeBID;
    }

    public void setEnodeBID(Long enodeBID) {
        this.enodeBID = enodeBID;
    }

    public String getEnodeBName() {
        return enodeBName;
    }

    public void setEnodeBName(String enodeBName) {
        this.enodeBName = enodeBName;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public Double getRb0ToRb9MaximumNoise() {
        return rb0ToRb9MaximumNoise;
    }

    public void setRb0ToRb9MaximumNoise(Double rb0ToRb9MaximumNoise) {
        this.rb0ToRb9MaximumNoise = rb0ToRb9MaximumNoise;
    }

    public Double getRb0ToRb9avgNoise() {
        return rb0ToRb9avgNoise;
    }

    public void setRb0ToRb9avgNoise(Double rb0ToRb9avgNoise) {
        this.rb0ToRb9avgNoise = rb0ToRb9avgNoise;
    }

    public Double getRb10ToRb19MaximumNoise() {
        return rb10ToRb19MaximumNoise;
    }

    public void setRb10ToRb19MaximumNoise(Double rb10ToRb19MaximumNoise) {
        this.rb10ToRb19MaximumNoise = rb10ToRb19MaximumNoise;
    }

    public Double getRb10ToRb19avgNoise() {
        return rb10ToRb19avgNoise;
    }

    public void setRb10ToRb19avgNoise(Double rb10ToRb19avgNoise) {
        this.rb10ToRb19avgNoise = rb10ToRb19avgNoise;
    }

    public Double getRb20ToRb29MaximumNoise() {
        return rb20ToRb29MaximumNoise;
    }

    public void setRb20ToRb29MaximumNoise(Double rb20ToRb29MaximumNoise) {
        this.rb20ToRb29MaximumNoise = rb20ToRb29MaximumNoise;
    }

    public Double getRb20ToRb29avgNoise() {
        return rb20ToRb29avgNoise;
    }

    public void setRb20ToRb29avgNoise(Double rb20ToRb29avgNoise) {
        this.rb20ToRb29avgNoise = rb20ToRb29avgNoise;
    }

    public Double getRb30ToRb39MaximumNoise() {
        return rb30ToRb39MaximumNoise;
    }

    public void setRb30ToRb39MaximumNoise(Double rb30ToRb39MaximumNoise) {
        this.rb30ToRb39MaximumNoise = rb30ToRb39MaximumNoise;
    }

    public Double getRb30ToRb39avgNoise() {
        return rb30ToRb39avgNoise;
    }

    public void setRb30ToRb39avgNoise(Double rb30ToRb39avgNoise) {
        this.rb30ToRb39avgNoise = rb30ToRb39avgNoise;
    }

    public Double getRboT40Rb49MaximumNoise() {
        return rboT40Rb49MaximumNoise;
    }

    public void setRboT40Rb49MaximumNoise(Double rboT40Rb49MaximumNoise) {
        this.rboT40Rb49MaximumNoise = rboT40Rb49MaximumNoise;
    }

    public Double getRb40ToRb49avgNoise() {
        return rb40ToRb49avgNoise;
    }

    public void setRb40ToRb49avgNoise(Double rb40ToRb49avgNoise) {
        this.rb40ToRb49avgNoise = rb40ToRb49avgNoise;
    }

    public Double getRb50ToRb59MaximumNoise() {
        return rb50ToRb59MaximumNoise;
    }

    public void setRb50ToRb59MaximumNoise(Double rb50ToRb59MaximumNoise) {
        this.rb50ToRb59MaximumNoise = rb50ToRb59MaximumNoise;
    }

    public Double getRb50ToRb59avgNoise() {
        return rb50ToRb59avgNoise;
    }

    public void setRb50ToRb59avgNoise(Double rb50ToRb59avgNoise) {
        this.rb50ToRb59avgNoise = rb50ToRb59avgNoise;
    }

    public Double getRb60ToRb69MaximumNoise() {
        return rb60ToRb69MaximumNoise;
    }

    public void setRb60ToRb69MaximumNoise(Double rb60ToRb69MaximumNoise) {
        this.rb60ToRb69MaximumNoise = rb60ToRb69MaximumNoise;
    }

    public Double getRb60ToRb69avgNoise() {
        return rb60ToRb69avgNoise;
    }

    public void setRb60ToRb69avgNoise(Double rb60ToRb69avgNoise) {
        this.rb60ToRb69avgNoise = rb60ToRb69avgNoise;
    }

    public Double getRb70ToRb79MaximumNoise() {
        return rb70ToRb79MaximumNoise;
    }

    public void setRb70ToRb79MaximumNoise(Double rb70ToRb79MaximumNoise) {
        this.rb70ToRb79MaximumNoise = rb70ToRb79MaximumNoise;
    }

    public Double getRb70ToRb79avgNoise() {
        return rb70ToRb79avgNoise;
    }

    public void setRb70ToRb79avgNoise(Double rb70ToRb79avgNoise) {
        this.rb70ToRb79avgNoise = rb70ToRb79avgNoise;
    }

    public Double getRb80ToRb89MaximumNoise() {
        return rb80ToRb89MaximumNoise;
    }

    public void setRb80ToRb89MaximumNoise(Double rb80ToRb89MaximumNoise) {
        this.rb80ToRb89MaximumNoise = rb80ToRb89MaximumNoise;
    }

    public Double getRb80ToRb89avgNoise() {
        return rb80ToRb89avgNoise;
    }

    public void setRb80ToRb89avgNoise(Double rb80ToRb89avgNoise) {
        this.rb80ToRb89avgNoise = rb80ToRb89avgNoise;
    }

    public Double getRb90ToRb99MaximumNoise() {
        return rb90ToRb99MaximumNoise;
    }

    public void setRb90ToRb99MaximumNoise(Double rb90ToRb99MaximumNoise) {
        this.rb90ToRb99MaximumNoise = rb90ToRb99MaximumNoise;
    }

    public Double getRb90ToRb99avgNoise() {
        return rb90ToRb99avgNoise;
    }

    public void setRb90ToRb99avgNoise(Double rb90ToRb99avgNoise) {
        this.rb90ToRb99avgNoise = rb90ToRb99avgNoise;
    }

    public Long getRrcConnReqCount() {
        return rrcConnReqCount;
    }

    public void setRrcConnReqCount(Long rrcConnReqCount) {
        this.rrcConnReqCount = rrcConnReqCount;
    }

    public Long getRrcConnSuccessCount() {
        return rrcConnSuccessCount;
    }

    public void setRrcConnSuccessCount(Long rrcConnSuccessCount) {
        this.rrcConnSuccessCount = rrcConnSuccessCount;
    }

    public Double getRrcConnSuccessPercent() {
        return rrcConnSuccessPercent;
    }

    public void setRrcConnSuccessPercent(Double rrcConnSuccessPercent) {
        this.rrcConnSuccessPercent = rrcConnSuccessPercent;
    }

    public Long getOtherRrcReConnReqCount() {
        return otherRrcReConnReqCount;
    }

    public void setOtherRrcReConnReqCount(Long otherRrcReConnReqCount) {
        this.otherRrcReConnReqCount = otherRrcReConnReqCount;
    }

    public Long getAllTypeRrcReConnReqCount() {
        return allTypeRrcReConnReqCount;
    }

    public void setAllTypeRrcReConnReqCount(Long allTypeRrcReConnReqCount) {
        this.allTypeRrcReConnReqCount = allTypeRrcReConnReqCount;
    }

    public Long getReConfigRrcReConnReqCount() {
        return reConfigRrcReConnReqCount;
    }

    public void setReConfigRrcReConnReqCount(Long reConfigRrcReConnReqCount) {
        this.reConfigRrcReConnReqCount = reConfigRrcReConnReqCount;
    }

    public Double getChangeFailTriggerRrcRebuildPercent() {
        return changeFailTriggerRrcRebuildPercent;
    }

    public void setChangeFailTriggerRrcRebuildPercent(Double changeFailTriggerRrcRebuildPercent) {
        this.changeFailTriggerRrcRebuildPercent = changeFailTriggerRrcRebuildPercent;
    }

    public Long getTimerTimeoutResultInConnFailCount() {
        return timerTimeoutResultInConnFailCount;
    }

    public void setTimerTimeoutResultInConnFailCount(Long timerTimeoutResultInConnFailCount) {
        this.timerTimeoutResultInConnFailCount = timerTimeoutResultInConnFailCount;
    }

    public Long getOtherReasonResultInConnFileCount() {
        return otherReasonResultInConnFileCount;
    }

    public void setOtherReasonResultInConnFileCount(Long otherReasonResultInConnFileCount) {
        this.otherReasonResultInConnFileCount = otherReasonResultInConnFileCount;
    }

    public Long getEnbAcceptFailResultInConnFailCount() {
        return enbAcceptFailResultInConnFailCount;
    }

    public void setEnbAcceptFailResultInConnFailCount(Long enbAcceptFailResultInConnFailCount) {
        this.enbAcceptFailResultInConnFailCount = enbAcceptFailResultInConnFailCount;
    }

    public Double getTimerTimeoutResultInConnFailPercent() {
        return timerTimeoutResultInConnFailPercent;
    }

    public void setTimerTimeoutResultInConnFailPercent(Double timerTimeoutResultInConnFailPercent) {
        this.timerTimeoutResultInConnFailPercent = timerTimeoutResultInConnFailPercent;
    }

    public Double getOtherReasonResultInConnFilePsercent() {
        return otherReasonResultInConnFilePsercent;
    }

    public void setOtherReasonResultInConnFilePsercent(Double otherReasonResultInConnFilePsercent) {
        this.otherReasonResultInConnFilePsercent = otherReasonResultInConnFilePsercent;
    }

    public Double getEnbAcceptFailResultInConnFailPercent() {
        return enbAcceptFailResultInConnFailPercent;
    }

    public void setEnbAcceptFailResultInConnFailPercent(Double enbAcceptFailResultInConnFailPercent) {
        this.enbAcceptFailResultInConnFailPercent = enbAcceptFailResultInConnFailPercent;
    }

    public Double getErabJamPercent() {
        return erabJamPercent;
    }

    public void setErabJamPercent(Double erabJamPercent) {
        this.erabJamPercent = erabJamPercent;
    }

    public Long getErabBuildSuccessCount() {
        return erabBuildSuccessCount;
    }

    public void setErabBuildSuccessCount(Long erabBuildSuccessCount) {
        this.erabBuildSuccessCount = erabBuildSuccessCount;
    }

    public Double getErabOfflinePercent() {
        return erabOfflinePercent;
    }

    public void setErabOfflinePercent(Double erabOfflinePercent) {
        this.erabOfflinePercent = erabOfflinePercent;
    }

    public Long getChangeFailResultInERabExceptionCount() {
        return changeFailResultInERabExceptionCount;
    }

    public void setChangeFailResultInERabExceptionCount(Long changeFailResultInERabExceptionCount) {
        this.changeFailResultInERabExceptionCount = changeFailResultInERabExceptionCount;
    }

    public Long getNetJamResultInERabExceptionCount() {
        return netJamResultInERabExceptionCount;
    }

    public void setNetJamResultInERabExceptionCount(Long netJamResultInERabExceptionCount) {
        this.netJamResultInERabExceptionCount = netJamResultInERabExceptionCount;
    }

    public Long getWirelessResultInERabExceptionCount() {
        return wirelessResultInERabExceptionCount;
    }

    public void setWirelessResultInERabExceptionCount(Long wirelessResultInERabExceptionCount) {
        this.wirelessResultInERabExceptionCount = wirelessResultInERabExceptionCount;
    }

    public Long getCoreNetExceptiionResultInERabExceptionCount() {
        return coreNetExceptiionResultInERabExceptionCount;
    }

    public void setCoreNetExceptiionResultInERabExceptionCount(Long coreNetExceptiionResultInERabExceptionCount) {
        this.coreNetExceptiionResultInERabExceptionCount = coreNetExceptiionResultInERabExceptionCount;
    }

    public Double getChangeFailResultInERabExceptionPercent() {
        return changeFailResultInERabExceptionPercent;
    }

    public void setChangeFailResultInERabExceptionPercent(Double changeFailResultInERabExceptionPercent) {
        this.changeFailResultInERabExceptionPercent = changeFailResultInERabExceptionPercent;
    }

    public Double getNetJamResultInERabExceptionPercent() {
        return netJamResultInERabExceptionPercent;
    }

    public void setNetJamResultInERabExceptionPercent(Double netJamResultInERabExceptionPercent) {
        this.netJamResultInERabExceptionPercent = netJamResultInERabExceptionPercent;
    }

    public Double getCoreNetExceptiionResultInERabExceptionPercent() {
        return coreNetExceptiionResultInERabExceptionPercent;
    }

    public void setCoreNetExceptiionResultInERabExceptionPercent(Double coreNetExceptiionResultInERabExceptionPercent) {
        this.coreNetExceptiionResultInERabExceptionPercent = coreNetExceptiionResultInERabExceptionPercent;
    }

    public Double getWirelessResultInERabExceptionPercent() {
        return wirelessResultInERabExceptionPercent;
    }

    public void setWirelessResultInERabExceptionPercent(Double wirelessResultInERabExceptionPercent) {
        this.wirelessResultInERabExceptionPercent = wirelessResultInERabExceptionPercent;
    }

    public Double getMacUpWrongPercent() {
        return macUpWrongPercent;
    }

    public void setMacUpWrongPercent(Double macUpWrongPercent) {
        this.macUpWrongPercent = macUpWrongPercent;
    }

    public Double getMacDownWrongPercent() {
        return macDownWrongPercent;
    }

    public void setMacDownWrongPercent(Double macDownWrongPercent) {
        this.macDownWrongPercent = macDownWrongPercent;
    }

    public Double getCellUpThruput() {
        return cellUpThruput;
    }

    public void setCellUpThruput(Double cellUpThruput) {
        this.cellUpThruput = cellUpThruput;
    }

    public Double getCellDownThruput() {
        return cellDownThruput;
    }

    public void setCellDownThruput(Double cellDownThruput) {
        this.cellDownThruput = cellDownThruput;
    }

    public Double getUpBusinessChannelPRBUsage() {
        return upBusinessChannelPRBUsage;
    }

    public void setUpBusinessChannelPRBUsage(Double upBusinessChannelPRBUsage) {
        this.upBusinessChannelPRBUsage = upBusinessChannelPRBUsage;
    }

    public Double getDownBusinessChannelPRBUsage() {
        return downBusinessChannelPRBUsage;
    }

    public void setDownBusinessChannelPRBUsage(Double downBusinessChannelPRBUsage) {
        this.downBusinessChannelPRBUsage = downBusinessChannelPRBUsage;
    }

    public Double getUpInitHARQReSentPercent() {
        return upInitHARQReSentPercent;
    }

    public void setUpInitHARQReSentPercent(Double upInitHARQReSentPercent) {
        this.upInitHARQReSentPercent = upInitHARQReSentPercent;
    }

    public Double getDownInitHARQReSentPercent() {
        return downInitHARQReSentPercent;
    }

    public void setDownInitHARQReSentPercent(Double downInitHARQReSentPercent) {
        this.downInitHARQReSentPercent = downInitHARQReSentPercent;
    }

    public Double getDownDoubleTrafficPercent() {
        return downDoubleTrafficPercent;
    }

    public void setDownDoubleTrafficPercent(Double downDoubleTrafficPercent) {
        this.downDoubleTrafficPercent = downDoubleTrafficPercent;
    }

    public Double getUpMscQpskEncodePercent() {
        return upMscQpskEncodePercent;
    }

    public void setUpMscQpskEncodePercent(Double upMscQpskEncodePercent) {
        this.upMscQpskEncodePercent = upMscQpskEncodePercent;
    }

    public Double getDownMscQpskEncodePercent() {
        return downMscQpskEncodePercent;
    }

    public void setDownMscQpskEncodePercent(Double downMscQpskEncodePercent) {
        this.downMscQpskEncodePercent = downMscQpskEncodePercent;
    }

    public Long getOnlineUserCount() {
        return onlineUserCount;
    }

    public void setOnlineUserCount(Long onlineUserCount) {
        this.onlineUserCount = onlineUserCount;
    }

    public Double getSameChannelChangeSuccessPercent() {
        return sameChannelChangeSuccessPercent;
    }

    public void setSameChannelChangeSuccessPercent(Double sameChannelChangeSuccessPercent) {
        this.sameChannelChangeSuccessPercent = sameChannelChangeSuccessPercent;
    }

    public Double getDiffChannelChangeSuccessPercent() {
        return diffChannelChangeSuccessPercent;
    }

    public void setDiffChannelChangeSuccessPercent(Double diffChannelChangeSuccessPercent) {
        this.diffChannelChangeSuccessPercent = diffChannelChangeSuccessPercent;
    }

    public Long getDiffChannelExecReqCount() {
        return diffChannelExecReqCount;
    }

    public void setDiffChannelExecReqCount(Long diffChannelExecReqCount) {
        this.diffChannelExecReqCount = diffChannelExecReqCount;
    }

    public Long getDiffchannelExecSuccessCount() {
        return diffchannelExecSuccessCount;
    }

    public void setDiffchannelExecSuccessCount(Long diffchannelExecSuccessCount) {
        this.diffchannelExecSuccessCount = diffchannelExecSuccessCount;
    }

    public Double getCellCompletionRate() {
        return cellCompletionRate;
    }

    public void setCellCompletionRate(Double cellCompletionRate) {
        this.cellCompletionRate = cellCompletionRate;
    }

    public Double getUpPrbEfficiency() {
        return upPrbEfficiency;
    }

    public void setUpPrbEfficiency(Double upPrbEfficiency) {
        this.upPrbEfficiency = upPrbEfficiency;
    }

    public Double getDownPrbEfficiency() {
        return downPrbEfficiency;
    }

    public void setDownPrbEfficiency(Double downPrbEfficiency) {
        this.downPrbEfficiency = downPrbEfficiency;
    }

    public Long getSameChannelChangeExecReqCount() {
        return sameChannelChangeExecReqCount;
    }

    public void setSameChannelChangeExecReqCount(Long sameChannelChangeExecReqCount) {
        this.sameChannelChangeExecReqCount = sameChannelChangeExecReqCount;
    }

    public Long getSameChannelChangeExecSuccessCount() {
        return sameChannelChangeExecSuccessCount;
    }

    public void setSameChannelChangeExecSuccessCount(Long sameChannelChangeExecSuccessCount) {
        this.sameChannelChangeExecSuccessCount = sameChannelChangeExecSuccessCount;
    }

    public Double getUpLossRate() {
        return upLossRate;
    }

    public void setUpLossRate(Double upLossRate) {
        this.upLossRate = upLossRate;
    }

    public Double getDownLossRate() {
        return downLossRate;
    }

    public void setDownLossRate(Double downLossRate) {
        this.downLossRate = downLossRate;
    }
}
