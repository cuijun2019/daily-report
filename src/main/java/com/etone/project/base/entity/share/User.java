/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;


import com.etone.ee.modules.data.Meta;
import com.etone.project.base.entity.Base;
import com.etone.project.base.state.AppConstants;
import com.etone.project.base.state.SexState;
import com.etone.project.base.type.AccountKind;
import com.etone.project.base.type.AccountStatus;
import com.etone.project.core.collections.Collections3;
import com.etone.project.core.utils.ConvertUtils;
import com.google.common.collect.Lists;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14069 $$
 */
@Entity
@Table(name = "tbUser")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.etone.project.base.entity.share")
public class User extends Base {
    // members
    private static final long serialVersionUID = 7378004766881438844L;

    /**
     * 员工编号
     */
    @Meta(name = "员工编号", exportable = true, order = 1)
    @Length(max=50)
    @Column(name = "vcStaffguid", length = 50)
    public String staffguid;

    /**
     * 组织架构编号(部门编号)
     */
    @Length(max=50)
    @Column(name = "vcOuguid", length = 50)
    public String ouguid;

    /**
     * 部门名称
     */
    @Meta(name = "部门名称", exportable = true, order = 2)
    @Length(max=100)
    @Column(name = "vcOuName", length = 100)
    public String ouName;

    /**
     * 账号
     */
    @Meta(name = "账号", exportable = true, order = 3)
    @Length(max=50)
    @Column(name = "vcAccount", nullable = false, length = 50)
    public String account;

    /**
     * 密码, SHA-1加密
     */
    @Length(max=50)
    @Column(name = "vcPassword", nullable = false, length = 50)
    public String password;

    /**
     * 随机强密码序列，基于base-64编码，用于密码加密验证
     */
    @Length(max=64)
    @Column(name = "vcSalt", nullable = false, length = 64)
    public String salt;

    /**
     * 图层空间
     */
    @Length(max=50)
    @Column(name = "vcTips", length = 50)
    public String tips;



    /**
     * 城市简称
     */
    @Length(max=20)
    @Column(name = "vcCityName", length = 20)
    public String cityName;

    /**
     * 城市区号
      */
   @Length(max=20)
    @Column(name = "intCityId", length = 20)
    public String intCityId;



    /**
     * 用户空间
     */
    @Length(max=50)
    @Column(name = "vcTipAnswer", length = 50)
    public String tipAnswer;

    /**
     * 默认定位坐标
     */
    @Length(max=50)
    @Column(name = "vcOuPath", length = 50)
    public String ouPath;

    /**
     * 账号类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vcKind")
    public AccountKind kind = AccountKind.LOCAL;

    /**
     * 姓名
     */
    @Meta(name = "姓名", exportable = true, order = 4)
    @Length(max=100)
    @Column(name = "vcFullName", length = 100)
    public String fullName;

    /**
     * 性别
     */
//    @Meta(name = "性别", exportable = true, order = 5)
//    @Enumerated(EnumType.STRING)
//    @Column(name = "vcSex")
//    public Gender sex;
    /**
     * 性别 女(0) 男(1) 保密(2) 默认：保密
     */
    @Meta(name = "性别", exportable = true, order = 5)
    @Column(name = "vcSex")
    private Integer sex = SexState.secrecy.getValue();
    
    /**
     * 性别描述.
     */
    @Transient
    public String getSexDesc() {
    	SexState ss = SexState.getSexState(sex);
    	String str = "";
    	if(ss != null){
    		str = ss.getDescription();
    	}
        return str;
    }
    
    /**
     * 工作电话号码
     */
    @Length(max=50)
    @Column(name = "vcWorkphone", length = 50)
    public String workphone;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 固话
     */
    @Length(max=50)
    @Column(name = "vcTelephone", length = 50)
    public String telephone;

    /**
     *厂家
     */
    @Length(max=30)
    @Column(name = "vcFactory", length = 30)
    public String factory;

    /**
     *所属专业室
     */
    @Length(max=30)
    @Column(name = "vcRoom", length = 30)
    public String room;

    /**
     * 集团短号
     */
    @Length(max=10)
    @Column(name = "vcShortMobile", length = 10)
    public String shortMobile;

    /**
     * 电邮
     */
    @Email
    @Length(max=128)
    @Column(name = "vcEmail", length = 128)
    public String email;

    @Length(max=255)
    @Column(name = "localId", length = 255)
    public String localId;

    @Length(max=255)
    @Column(name = "serverId", length = 255)
    public String serverId;

    @Length(max=255)
    @Column(name = "headPortraitAddress", length = 255)
    public String headPortraitAddress;

    /**
     * 是否超级用户
     */
    @Column(name = "isSupervisor")
    public boolean supervisor = false;

    /**
     * 是否锁定
     */
    @Meta(name = "是否锁定", exportable = true, order = 6)
    @Column(name = "isLock")
    public boolean lock = false;

    /**
     * 锁定时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtLockedDate")
    public Date lockedDate = null;

    /**
     * 账户状态, BLOCK : 停用， NORMAL : 正常
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vcStatus")
    public AccountStatus status = AccountStatus.NORMAL;

    /**
     * 用户角色
     */
    @JsonManagedReference("user-roles")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tbUserRole",
        joinColumns = @JoinColumn(name = "bigUserId"),
        inverseJoinColumns = @JoinColumn(name = "bigRoleId"))
    public List<Role> roles = Lists.newArrayList();

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     * <br>如果是超级管理员 直接返回 "超级管理员" AppConstants.ROLE_SUPERADMIN
     */
    @Transient
    // 非持久化属性.
    public String getRoleNames() {
    	Long superId = 1L;
	    if(superId.equals(this.getId())){
	        return AppConstants.ROLE_SUPERADMIN;
	    }
        return ConvertUtils.convertElementPropertyToString(roles, "name",
                ", ");
    }

    @SuppressWarnings("unchecked")
    @Transient
    public List<Long> getRoleIds() {
    	List<Long> roleIds = null;
        if (!Collections3.isEmpty(roles)) {
            roleIds = ConvertUtils.convertElementPropertyToList(roles, "id");
        }
        return roleIds;
    }
    
    // EL 支持 : BEGIN
    public String getStaffguid() {
        return staffguid;
    }

    public String getOuguid() {
        return ouguid;
    }

    public String getOuName() {
        return ouName;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getTips() {
        return tips;
    }



    public AccountKind getKind() {
        return kind;
    }

    public String getFullName() {
        return fullName;
    }

//    public Gender getSex() {
//        return sex;
//    }

    public String getWorkphone() {
        return workphone;
    }



    public String getShortMobile() {
        return shortMobile;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSupervisor() {
        return supervisor;
    }

    public boolean isLock() {
        return lock;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public List<Role> getRoles() {
        return roles;
    }
    // EL 支持 : END

    // public methods

    /**
     * 账号角色授权
     *
     * @param roles
     */
    public void grantRole(List<Role> roles) {
        this.roles = roles;
    }

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRoom() {
        return this.room;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getFactory() {
        return this.factory;
    }

    public String getIntCityId() {
        return intCityId;
    }

    public void setIntCityId(String intCityId) {
        this.intCityId = intCityId;
    }

    public void setTips(String tips) {
		this.tips = tips;
	}

    public String getOuPath() {
        return ouPath;
    }

    public void setOuPath(String ouPath) {
        this.ouPath = ouPath;
    }

    public String getTipAnswer() {
        return tipAnswer;
    }

    public void setTipAnswer(String tipAnswer) {
        this.tipAnswer = tipAnswer;
    }

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
