/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity;

import com.etone.ee.modules.data.Meta;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 统一定义实际基类, 用于进行数据级权限查询<br/>
 * 需要对部门、用户过滤必须继承该类
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14093 $$
 */
@MappedSuperclass
public abstract class Base implements Serializable {
    // members
    /**
     * 主建, Oracle 请修改该成员变量
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bigId", unique = true, nullable = false)
    @Meta(name = "主键", exportable = true, order = 0)
    public Long id;

    /**
     * 组织架构路径，如：广东省公司/广州分公司/研发中心/系统架构室
     */
//    @Length(max=1000)
//    @Column(name = "vcOuPath", length =1000)
//    @Meta(name = "组织全称", exportable = true, order = 1000)
//    public String ouPath;

    /**
     * 创建用户
     */
    @Column(name = "vcCreateUser", length = 20)
    @Meta(name = "创建用户", exportable = true, order = 1001)
    public String createUser;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtCreateTime")
    @Meta(name = "创建时间", exportable = true, order = 1002)
    public Date createTime;

    /**
     * 修改用户
     */
    @Column(name = "vcModifyUser")
    @Meta(name = "修改用户", exportable = true, order = 1003)
    public String modifyUser;

    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtModifyTime")
    @Meta(name = "修改时间", exportable = true, order = 1004)
    public Date modifyTime;

    /**
     * 备注
     */
    @Length(max=2000)
    @Column(name = "vcRemark", length = 2000)
    @Meta(name = "备注", exportable = true, order = 1005)
    public String remark;
    
    // static block

    // constructors

    // properties

    // public methods
    @PreUpdate
    public void beforeUpdate() {
        this.modifyTime = new Date();
        if (this.createTime == null) {
            this.createTime = this.modifyTime;
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public String getCreateUser() {
//		return createUser;
//	}
//
//	public void setCreateUser(String createUser) {
//		this.createUser = createUser;
//	}
//
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
//
//	public String getModifyUser() {
//		return modifyUser;
//	}
//
//	public void setModifyUser(String modifyUser) {
//		this.modifyUser = modifyUser;
//	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
    
    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
