package com.etone.project.modules.lte.entity;

import com.etone.ee.modules.data.Meta;

public class LteRfgDto {
	
	@Meta(name = "eNodeBID", exportable = true)
	public String eNodeBID;
	
	@Meta(name = "小区ID", exportable = true)
	public String cellID;
	
	@Meta(name = "MRO采样点", exportable = true)
	public String MROCount;
	
	@Meta(name = "小区弱覆盖度", exportable = true)
	public String cellWeak;
	
	@Meta(name = "小区覆盖分析", exportable = true)
	public String fgCell;
	
	@Meta(name = "告警数", exportable = true)
	public String alarmNum;
	
	@Meta(name = "覆盖邻区缺失概率", exportable = true)
	public String cellNcmo;
	
	@Meta(name = "邻区缺失分析", exportable = true)
	public String ncmoAnalyse;
	
	@Meta(name = "边缘弱覆盖强度", exportable = true)
	public String cellEwc;
	
	@Meta(name = "站间距", exportable = true)
	public String nDistance;
	
	@Meta(name = "孤站分析", exportable = true)
	public String isLonely;
	
	@Meta(name = "室外打室内概率", exportable = true)
	public String cellRcr;
	
	@Meta(name = "边缘弱覆盖分析", exportable = true)
	public String ewcAnalyse;
	
	@Meta(name = "其它弱覆盖场景", exportable = true)
	public String othWeak;
	
	@Meta(name = "小区分析定位", exportable = true)
	public String cellAnalyse;
	
	@Meta(name = "告警eNodeBID", exportable = true)
	public String alarmeNodeBID;
	
	@Meta(name = "告警小区ID", exportable = true)
	public String alarmCellID;
	
	@Meta(name = "告警码", exportable = true)
	public String alarmCode;
	
	@Meta(name = "标准告警码", exportable = true)
	public String standardAlarmCode;
	
	@Meta(name = "告警级别", exportable = true)
	public String alarmLevel;
	
	@Meta(name = "发生时间", exportable = true)
	public String happenTime;
	
	@Meta(name = "告警恢复时间", exportable = true)
	public String alarmrestTime;
	
	@Meta(name = "持续时间(hh:mm:ss)", exportable = true)
	public String continueTime;
	
	@Meta(name = "缺失邻区频点", exportable = true)
	public String lteNcEarfcn;
	
	@Meta(name = "缺失邻区PCI", exportable = true)
	public String lteNcPCI;
	
	@Meta(name = "MR采样点", exportable = true)
	public String mrNum;
	
	@Meta(name = "占比", exportable = true)
	public String scale;
	
	@Meta(name = "数量", exportable = true)
	public String rfgNum;
	
}
