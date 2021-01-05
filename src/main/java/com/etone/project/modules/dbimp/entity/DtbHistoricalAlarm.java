package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;

import java.sql.Timestamp;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class DtbHistoricalAlarm {
    
    @Meta(name = "根源告警标识")
    private String alarmSourceSign;
    
    @Meta(name = "确认状态")
    private String confirmState;
    
    @Meta(name = "告警级别")
    private String alarmLevel;
    
    @Meta(name = "网元")
    private String netInfo;
    
    @Meta(name = "网元内定位")
    private String netInfoPosit;
    
    @Meta(name = "告警码")
    private String alarmCode;
    
    @Meta(name = "发生时间")
    private Timestamp happenTime;
    
    @Meta(name = "网元类型")
    private String netInfoType;
    
    @Meta(name = "告警类型")
    private String alarmType;
    
    @Meta(name = "告警原因")
    private String alarmCourse;
    
    @Meta(name = "附加文本")
    private String addText;
    
    @Meta(name = "CELLID")
    private String cellId;
    
    @Meta(name = "CELLID2")
    private String cellId2;
    
    @Meta(name = "ADMC告警")
    private Boolean isAdmcAlarm;
    
    @Meta(name = "告警恢复时间")
    private Timestamp alarmrestTime;
    
    @Meta(name = "闪断计数")
    private Long offCount;
    
    @Meta(name = "告警对象类型")
    private String alarmObjectType;
    
    @Meta(name = "单板类型")
    private String veneerType;
    
    @Meta(name = "告警对象ID")
    private Long alarmObjectId;
    
    @Meta(name = "站点名称(局向)")
    private String siteName;
    
    @Meta(name = "站点ID(局向)")
    private String siteId;
    
    @Meta(name = "ENODEBID")
    private Long enodeId;
    
    @Meta(name = "告警对象名称")
    private String alarmObjectName;
    
    @Meta(name = "标准告警码")
    private String standardAlarmCode;
    
    @Meta(name = "产品")
    private String product;
    
    @Meta(name = "告警标识")
    private String alarmState;
    
    @Meta(name = "影响网元")
    private String impactNetInfo;
    
    @Meta(name = "影响网元内定位")
    private String impactNetInfoPosit;
    
    @Meta(name = "告警修改时间")
    private Timestamp alarmModifyTime;
    
    @Meta(name = "附加内容")
    private String addContent;
    
    @Meta(name = "确认/反确认用户")
    private String confirmUser;
    
    @Meta(name = "确认/反确认系统")
    private String confirmSys;
    
    @Meta(name = "告警确认/反确认时间")
    private Timestamp confirmTime;
    
    @Meta(name = "告警确认/反确认信息")
    private String confirmInfo;
    
    @Meta(name = "清除用户")
    private String removeUser;
    
    @Meta(name = "清除系统")
    private String removeSys;
    
    @Meta(name = "恢复方式")
    private String restWay;
    
    @Meta(name = "告警注释")
    private String alarmExplain;
    
    @Meta(name = "注释用户")
    private String explainUser;
    
    @Meta(name = "注释系统")
    private String explainSys;
    
    @Meta(name = "注释时间")
    private Timestamp explainTime;
    
    @Meta(name = "告警编号")
    private String alarmNo;
    
    @Meta(name = "网元IP")
    private String netIp;
    
    @Meta(name = "链路")
    private String link;
    
    @Meta(name = "网元分组")
    private String netInfoGroup;
    
    @Meta(name = "网元代理")
    private String netAgent;
    
    @Meta(name = "系统类型")
    private String sysType;
    
    @Meta(name = "持续时间(hh:mm:ss)")
    private String continueTime;
    
    @Meta(name = "关联业务")
    private String aboutBusi;
    
    @Meta(name = "产生方式")
    private String triggerWay;
    
    @Meta(name = "门限任务信息")
    private String thresholdInfo;
    
    @Meta(name = "调测状态")
    private String testState;

    public String getAlarmSourceSign() {
        return alarmSourceSign;
    }

    public void setAlarmSourceSign(String alarmSourceSign) {
        this.alarmSourceSign = alarmSourceSign;
    }

    public String getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(String confirmState) {
        this.confirmState = confirmState;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getNetInfo() {
        return netInfo;
    }

    public void setNetInfo(String netInfo) {
        this.netInfo = netInfo;
    }

    public String getNetInfoPosit() {
        return netInfoPosit;
    }

    public void setNetInfoPosit(String netInfoPosit) {
        this.netInfoPosit = netInfoPosit;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public Timestamp getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Timestamp happenTime) {
        this.happenTime = happenTime;
    }

    public String getNetInfoType() {
        return netInfoType;
    }

    public void setNetInfoType(String netInfoType) {
        this.netInfoType = netInfoType;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmCourse() {
        return alarmCourse;
    }

    public void setAlarmCourse(String alarmCourse) {
        this.alarmCourse = alarmCourse;
    }

    public String getAddText() {
        return addText;
    }

    public void setAddText(String addText) {
        this.addText = addText;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCellId2() {
        return cellId2;
    }

    public void setCellId2(String cellId2) {
        this.cellId2 = cellId2;
    }


    public Boolean getIsAdmcAlarm() {
        return isAdmcAlarm;
    }

    public void setIsAdmcAlarm(Boolean isAdmcAlarm) {
        this.isAdmcAlarm = isAdmcAlarm;
    }

    public Timestamp getAlarmrestTime() {
        return alarmrestTime;
    }

    public void setAlarmrestTime(Timestamp alarmrestTime) {
        this.alarmrestTime = alarmrestTime;
    }

    public Long getOffCount() {
        return offCount;
    }

    public void setOffCount(Long offCount) {
        this.offCount = offCount;
    }

    public String getAlarmObjectType() {
        return alarmObjectType;
    }

    public void setAlarmObjectType(String alarmObjectType) {
        this.alarmObjectType = alarmObjectType;
    }

    public String getVeneerType() {
        return veneerType;
    }

    public void setVeneerType(String veneerType) {
        this.veneerType = veneerType;
    }

    public Long getAlarmObjectId() {
        return alarmObjectId;
    }

    public void setAlarmObjectId(Long alarmObjectId) {
        this.alarmObjectId = alarmObjectId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Long getEnodeId() {
        return enodeId;
    }

    public void setEnodeId(Long enodeId) {
        this.enodeId = enodeId;
    }

    public String getAlarmObjectName() {
        return alarmObjectName;
    }

    public void setAlarmObjectName(String alarmObjectName) {
        this.alarmObjectName = alarmObjectName;
    }

    public String getStandardAlarmCode() {
        return standardAlarmCode;
    }

    public void setStandardAlarmCode(String standardAlarmCode) {
        this.standardAlarmCode = standardAlarmCode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(String alarmState) {
        this.alarmState = alarmState;
    }

    public String getImpactNetInfo() {
        return impactNetInfo;
    }

    public void setImpactNetInfo(String impactNetInfo) {
        this.impactNetInfo = impactNetInfo;
    }

    public String getImpactNetInfoPosit() {
        return impactNetInfoPosit;
    }

    public void setImpactNetInfoPosit(String impactNetInfoPosit) {
        this.impactNetInfoPosit = impactNetInfoPosit;
    }

    public Timestamp getAlarmModifyTime() {
        return alarmModifyTime;
    }

    public void setAlarmModifyTime(Timestamp alarmModifyTime) {
        this.alarmModifyTime = alarmModifyTime;
    }

    public String getAddContent() {
        return addContent;
    }

    public void setAddContent(String addContent) {
        this.addContent = addContent;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getConfirmSys() {
        return confirmSys;
    }

    public void setConfirmSys(String confirmSys) {
        this.confirmSys = confirmSys;
    }

    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmInfo() {
        return confirmInfo;
    }

    public void setConfirmInfo(String confirmInfo) {
        this.confirmInfo = confirmInfo;
    }

    public String getRemoveUser() {
        return removeUser;
    }

    public void setRemoveUser(String removeUser) {
        this.removeUser = removeUser;
    }

    public String getRemoveSys() {
        return removeSys;
    }

    public void setRemoveSys(String removeSys) {
        this.removeSys = removeSys;
    }

    public String getRestWay() {
        return restWay;
    }

    public void setRestWay(String restWay) {
        this.restWay = restWay;
    }

    public String getAlarmExplain() {
        return alarmExplain;
    }

    public void setAlarmExplain(String alarmExplain) {
        this.alarmExplain = alarmExplain;
    }

    public String getExplainUser() {
        return explainUser;
    }

    public void setExplainUser(String explainUser) {
        this.explainUser = explainUser;
    }

    public String getExplainSys() {
        return explainSys;
    }

    public void setExplainSys(String explainSys) {
        this.explainSys = explainSys;
    }

    public Timestamp getExplainTime() {
        return explainTime;
    }

    public void setExplainTime(Timestamp explainTime) {
        this.explainTime = explainTime;
    }

    public String getAlarmNo() {
        return alarmNo;
    }

    public void setAlarmNo(String alarmNo) {
        this.alarmNo = alarmNo;
    }

    public String getNetIp() {
        return netIp;
    }

    public void setNetIp(String netIp) {
        this.netIp = netIp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNetInfoGroup() {
        return netInfoGroup;
    }

    public void setNetInfoGroup(String netInfoGroup) {
        this.netInfoGroup = netInfoGroup;
    }

    public String getNetAgent() {
        return netAgent;
    }

    public void setNetAgent(String netAgent) {
        this.netAgent = netAgent;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(String continueTime) {
        this.continueTime = continueTime;
    }

    public String getAboutBusi() {
        return aboutBusi;
    }

    public void setAboutBusi(String aboutBusi) {
        this.aboutBusi = aboutBusi;
    }

    public String getTriggerWay() {
        return triggerWay;
    }

    public void setTriggerWay(String triggerWay) {
        this.triggerWay = triggerWay;
    }

    public String getThresholdInfo() {
        return thresholdInfo;
    }

    public void setThresholdInfo(String thresholdInfo) {
        this.thresholdInfo = thresholdInfo;
    }

    public String getTestState() {
        return testState;
    }

    public void setTestState(String testState) {
        this.testState = testState;
    }
}
