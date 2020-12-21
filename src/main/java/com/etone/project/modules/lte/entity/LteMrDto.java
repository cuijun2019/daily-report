package com.etone.project.modules.lte.entity;

import com.etone.ee.modules.data.Meta;

public class LteMrDto {
//	@Meta(name="时间", exportable = true)
//	public String datetime;
	
	@Meta(name="小区ID", exportable = true)
	public String cellId;
	
	@Meta(name = "RSRP平均值", exportable = true)
	public String avgRsrp;
	
	@Meta(name = "PHR平均值", exportable = true)
	public String avgPhr;
	
	@Meta(name = "弱覆盖小区数量", exportable = true)
	public String rsrpNum;
	
	@Meta(name = "弱覆盖小区占比", exportable = true)
	public String rsrpScale;
	
	@Meta(name = "高UE发射功率小区数量", exportable = true)
	public String phrNum;
	
	@Meta(name = "高UE发射功率小区占比", exportable = true)
	public String phrScale;
	
	@Meta(name = "良好覆盖小区数量", exportable = true)
	public String goodCoverNum;
	
	@Meta(name = "良好覆盖小区占比", exportable = true)
	public String goodCoverScale;
	
	@Meta(name = "RIP平均值", exportable = true)
	public String avgRip;
	
	@Meta(name = "RSRQ平均值", exportable = true)
	public String avgRsrq;
	
	@Meta(name = "上行SINR平均值", exportable = true)
	public String avgSinr;
	
	@Meta(name = "重叠覆盖度", exportable = true)
	public String muCover;
	
	@Meta(name = "重叠覆盖小区数量", exportable = true)
	public String mcCell;
	
	@Meta(name = "重叠覆盖小区占比", exportable = true)
	public String mcScale;
	
	@Meta(name = "模三干扰强度", exportable = true)
	public String mo3Scale;
	
	@Meta(name = "模三干扰小区数量", exportable = true)
	public String moCell;
	
	@Meta(name = "模三干扰小区占比", exportable = true)
	public String moScale;
	
	@Meta(name = "小区数", exportable = true)
	public String cellNum;
	
	@Meta(name = "RSRP区间1", exportable = true)
	public String rsrp1;
	
	@Meta(name = "RSRP区间2", exportable = true)
	public String rsrp2;
	
	@Meta(name = "RSRP区间3", exportable = true)
	public String rsrp3;
	
	@Meta(name = "RSRP区间4", exportable = true)
	public String rsrp4;
	
	@Meta(name = "RSRP区间5", exportable = true)
	public String rsrp5;
	
	@Meta(name = "RSRP区间6", exportable = true)
	public String rsrp6;
	
	@Meta(name = "RSRP区间7", exportable = true)
	public String rsrp7;
	
	@Meta(name = "PHR区间1", exportable = true)
	public String phr1;
	
	@Meta(name = "PHR区间2", exportable = true)
	public String phr2;
	
	@Meta(name = "PHR区间3", exportable = true)
	public String phr3;
	
	@Meta(name = "PHR区间4", exportable = true)
	public String phr4;
	
	@Meta(name = "PHR区间5", exportable = true)
	public String phr5;
	
	@Meta(name = "PHR区间6", exportable = true)
	public String phr6;
	
	@Meta(name = "PHR区间7", exportable = true)
	public String phr7;
	
	@Meta(name = "RIP区间1", exportable = true)
	public String rip1;
	
	@Meta(name = "RIP区间2", exportable = true)
	public String rip2;
	
	@Meta(name = "RIP区间3", exportable = true)
	public String rip3;
	
	@Meta(name = "RIP区间4", exportable = true)
	public String rip4;
	
	@Meta(name = "RIP区间5", exportable = true)
	public String rip5;
	
	@Meta(name = "RIP区间6", exportable = true)
	public String rip6;
	
	@Meta(name = "RIP区间7", exportable = true)
	public String rip7;
	
	@Meta(name = "RSRQ区间1", exportable = true)
	public String rsrq1;
	
	@Meta(name = "RSRQ区间2", exportable = true)
	public String rsrq2;
	
	@Meta(name = "RSRQ区间3", exportable = true)
	public String rsrq3;
	
	@Meta(name = "RSRQ区间4", exportable = true)
	public String rsrq4;
	
	@Meta(name = "RSRQ区间5", exportable = true)
	public String rsrq5;
	
	@Meta(name = "RSRQ区间6", exportable = true)
	public String rsrq6;
	
	@Meta(name = "RSRQ区间7", exportable = true)
	public String rsrq7;
	
	@Meta(name = "重叠覆盖度区间1", exportable = true)
	public String mccell1;
	
	@Meta(name = "重叠覆盖度区间2", exportable = true)
	public String mccell2;
	
	@Meta(name = "重叠覆盖度区间3", exportable = true)
	public String mccell3;
	
	@Meta(name = "重叠覆盖度区间4", exportable = true)
	public String mccell4;
	
	@Meta(name = "重叠覆盖度区间5", exportable = true)
	public String mccell5;
	
	@Meta(name = "重叠覆盖度区间6", exportable = true)
	public String mccell6;
	
	@Meta(name = "重叠覆盖度区间7", exportable = true)
	public String mccell7;
	
	@Meta(name = "模三干扰区间1", exportable = true)
	public String mocell1;
	
	@Meta(name = "模三干扰区间2", exportable = true)
	public String mocell2;
	
	@Meta(name = "模三干扰区间3", exportable = true)
	public String mocell3;
	
	@Meta(name = "模三干扰区间4", exportable = true)
	public String mocell4;
	
	@Meta(name = "模三干扰区间5", exportable = true)
	public String mocell5;
	
	@Meta(name = "模三干扰区间6", exportable = true)
	public String mocell6;
	
	@Meta(name = "模三干扰区间7", exportable = true)
	public String mocell7;
	
	@Meta(name = "MRS采样点数", exportable = true)
	public String MRScount;
	
	@Meta(name = "RSRP平均值", exportable = true)
	public String cellRSRP;
	
	@Meta(name = "PHR平均值", exportable = true)
	public String cellPHR;
	
	@Meta(name = "RIP平均值", exportable = true)
	public String cellRIP;
	
	@Meta(name = "RSRQ平均值", exportable = true)
	public String cellRSRQ;
	
	@Meta(name = "上行SINR平均值", exportable = true)
	public String cellSINR;
	
	@Meta(name = "MRO采样点数", exportable = true)
	public String MROcount;
	
	@Meta(name = "小区弱覆盖度", exportable = true)
	public String cellWeak;
	
	@Meta(name = "覆盖邻区缺失概率", exportable = true)
	public String cellNcmo;
	
	@Meta(name = "边缘弱覆盖度", exportable = true)
	public String cellEwc;
	
	@Meta(name = "室外打室内概率", exportable = true)
	public String cellRcr;
	
	@Meta(name = "上行干扰强度", exportable = true)
	public String cellUd;
	
	@Meta(name = "UE高发射功率强度", exportable = true)
	public String cellUEp;
	
	@Meta(name = "模三干扰强度", exportable = true)
	public String cellTdtree;
	
	@Meta(name = "重叠覆盖度", exportable = true)
	public String cellOC;
	
	@Meta(name = "邻区过覆盖度", exportable = true)
	public String cellNca;
	
	@Meta(name = "MRE采样点数", exportable = true)
	public String MREcount;
	
	@Meta(name = "A3测量过覆盖概率", exportable = true)
	public String A3Weak;
	
	@Meta(name = "A3服务小区RSRP平均值", exportable = true)
	public String A3RSRP;
	
	@Meta(name = "合理邻区缺失概率", exportable = true)
	public String A3Ncmo;
}
