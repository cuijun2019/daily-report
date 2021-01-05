package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;

public class TbDictionary {
	
	@Meta(name = "创建用户ID")
    private String vcCreateUser;
	
	@Meta(name = "类型编码")
    private String vcKey;
	
	@Meta(name = "类型名称")
    private String name;
	
	@Meta(name = "排序")
    private Integer orderNo;
	
	@Meta(name = "备注")
    private String vcRemark;
	
	@Meta(name = "系统字典类型")
    private String dictionarytypeCode;
	
	@Meta(name = "参数值")
    private Integer value;

	public String getVcCreateUser() {
		return vcCreateUser;
	}

	public void setVcCreateUser(String vcCreateUser) {
		this.vcCreateUser = vcCreateUser;
	}

	public String getVcKey() {
		return vcKey;
	}

	public void setVcKey(String vcKey) {
		this.vcKey = vcKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getVcRemark() {
		return vcRemark;
	}

	public void setVcRemark(String vcRemark) {
		this.vcRemark = vcRemark;
	}

	public String getDictionarytypeCode() {
		return dictionarytypeCode;
	}

	public void setDictionarytypeCode(String dictionarytypeCode) {
		this.dictionarytypeCode = dictionarytypeCode;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
