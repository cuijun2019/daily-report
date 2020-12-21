/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import com.etone.project.base.entity.Base;
import com.etone.project.base.type.Gender;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * 员工信息
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
@Entity
@Table(name = "tbEmployee")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.etone.project.base.entity.share")
public class Employee extends Base {
    // members
    private static final long serialVersionUID = 617784854087068121L;

    /**
     * 归属部门, 员工数据维护需要从该属性中获取ouPath[组织明细]，ouName[部门]信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bigOrganizationId", nullable = false)
    public Organization organization;

    /**
     * 公司，从部门信息中关联
     */
    @Column(name = "vcCompany", length = 200)
    public String company;

    /**
     * 公司编码，程序使用，从部门信息中关联, 列表与编辑均不显示
     */
    @Column(name = "vcCompanyGuid", length = 50)
    public String companyGuid;

    /**
     * 部门
     */
    @Column(name = "vcDepartment", length = 200)
    public String department;

    /**
     * 部门编码，程序使用，从部门信息中关联, 列表与编辑均不显示
     */
    @Column(name = "vcDepartmentGuid", length = 50)
    public String departmentGuid;

    /**
     * 员工编码，程序使用，从部门信息中关联, 列表与编辑均不显示
     */
    @Column(name = "vcStaffGuid", length = 50)
    public String staffGuid;

    /**
     * 工号，程序使用，从部门信息中关联, 列表与编辑均不显示
     */
    @Column(name = "vcStaffNo", length = 20)
    public String staffNo;

    /**
     * 姓名
     */
    @Length(max=100)
    @Column(name = "vcFullName", length = 100)
    public String fullName;

    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vcSex")
    public Gender sex;

    /**
     * 工作电话号码
     */
    @Length(max=50)
    @Column(name = "vcWorkhone", length = 50)
    public String workphone;

    /**
     * 固话
     */
    @Length(max=50)
    @Column(name = "vcTelephone", length = 50)
    public String telephone;

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

    /**
     * 职称
     */
    @Column(name = "vcTitle", length = 50)
    public String title;

    /**
      * 居住地址
      */
     @Column(name = "vcAddress", length = 200)
    public String address;

    /**
     * 职位
     */
    @Column(name = "vcPosition", length = 50)
    public String position;

    /**
     * 生日
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "dtBrithday")
    public Date brithday;

    /**
     * 入职时间
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "dtHireDate")
    public Date hiredate;

    /**
     * 离职时间
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "dtFireDate")
    public Date firedate;


    // static block

    // constructors

    // properties

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
