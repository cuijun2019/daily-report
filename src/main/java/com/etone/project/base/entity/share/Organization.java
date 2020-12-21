/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import com.etone.project.base.entity.Base;
import com.etone.project.base.type.OrganizationKind;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * 组织架构(用户部门)
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
@Entity
@Table(name = "tbOrganization")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.etone.project.base.entity.share")
public class Organization extends Base {
    // members
    private static final long serialVersionUID = -3914775335246401178L;

    /**
     * 上级组织
     */
    @Column(name = "bigParentId")
    public long parentId = 0;

    /**
     * 组织ID, 格式为GUID
     */
    @NotBlank
    @Length(max = 50)
    @Column(name = "vcOuguid", length = 50)
    public String ouguid;

    /**
     * 上级组织,OUGUID
     */
    @NotBlank
    @Length(max = 50)
    @Column(name = "vcParentOuguid", length = 50)
    public String parentOuguid;

    /**
     * 组织类型
     */
    @Column(name = "intOuKind")
    public OrganizationKind ouKind = OrganizationKind.COMPANY;

    /**
     * 组织名称
     */
    @NotBlank
    @Length(max = 200)
    @Column(name = "vcOuName", length = 200)
    public String ouName;

    /**
     * 上级组织名称
     */
    @Length(max = 200)
    @Column(name = "vcParentName", length = 200)
    public String parentName;

    /**
     * 是否叶子节点( 辅助构建树 )
     */
    @Column(name = "isLeaf")
    public boolean leaf;

    /**
     * 层次( 辅助构建树 )
     */
    @Column(name = "intLevel")
    public int level;

    /**
     * 位置，从父节点开发计算, 0+N ( 辅助构建树 )
     */
    @Column(name = "intOrder")
    public int order = 0;

    /**
     * 组织角色
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tbOrganizationRole",
        joinColumns = @JoinColumn(name = "bigOrganizationId"),
        inverseJoinColumns = @JoinColumn(name = "bigRoleId"))
    public List<Role> roles = Lists.newArrayList();

    /**
     * 部门直属人员信息
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    public List<Employee> employees = Lists.newArrayList();

    // static block

    // constructors

    // properties

    // public methods
    /**
     * 权限角色关联
     *
     * @param roles
     */
    public void grantRole(List<Role> roles) {
        this.roles = roles;
    }
    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
