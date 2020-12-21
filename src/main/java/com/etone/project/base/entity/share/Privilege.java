/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import com.etone.ee.modules.data.Meta;
import com.etone.project.base.entity.Base;
import com.etone.project.base.type.PrivilegeKind;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

/**
 * 权限与模块信息
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14148 $$
 */
@Entity
@Table(name = "tbPrivilege")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.etone.project.base.entity.share")
public class Privilege extends Base {
    // members
    private static final long serialVersionUID = -4456477813845248608L;
    // 列表
    public static final String PRIVILEGE_LIST = "list";
    // 列表
    public static final String PRIVILEGE_VIEW = "view";
    // 保存
    public static final String PRIVILEGE_SAVE = "save";
    // 删除
    public static final String PRIVILEGE_DELETE = "delete";
    // 导入
    public static final String PRIVILEGE_IMPORT = "import";
    // 导出
    public static final String PRIVILEGE_EXPORT = "export";

    @JsonBackReference
    @Meta(name = "父模块信息", hide = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bigParentId")
    public Privilege parent;

    @JsonProperty("_parentId")
    @Meta(name = "父模块", hide = true)
    @Column(name = "bigParentId", insertable = false, updatable = false)
    public Long parentId;

    
    /**
     * 资源类型( MENU, URL )
     */
    @Meta(name = "类型", exportable = true, order = 1)
    @Enumerated(EnumType.STRING)
    @Column(name = "vcKind")
    public Integer kind = PrivilegeKind.MENU.getValue();
    
//    /**
//     * 资源类型描述
//     */
//    @Transient
//	public String kindDesc;
//    
//    @Transient
//    public String getTypeDesc() {
//    	PrivilegeKind r = PrivilegeKind.getResourceState(kind);
//        String str = "";
//        if(r != null){
//            str = r.getDescription();
//        }
//        return str;
//    }

    /**
     * 模块或权限名称
     */
    @Meta(name = "名称", exportable = true, order = 2)
    @Length(max = 200)
    @Column(name = "vcName", nullable = false, length = 200)
    public String name;

    /**
     * 权限模块编码
     */
    @Meta(name = "编码", exportable = true, order = 3)
    @Length(max = 12)
    @Column(name = "vcCode", nullable = false, length = 12)
    public String code;

    /**
     * 权限模块标识(针对客户应用，如dotnet)
     */
    @Meta(name = "标识", exportable = true, order = 4)
    @Length(max = 200)
    @Column(name = "vcSymbol", nullable = false, length = 200)
    public String symbol;

    /**
     * 权限上下文路径( 辅助用，如查询 )
     */
    @Meta(name = "全路径", exportable = true, order = 5)
    @Length(max = 1000)
    @Column(name = "vcPath", length = 1000)
    public String path;

    /**
     * 访问地址( ACL 模式中使用到，或基于拦截器应用 )
     */
    @Meta(name = "地址", exportable = true, order = 6)
    @Length(max = 1000)
    @Column(name = "vcUrl", length = 1000)
    public String url;

    /**
     * 是否叶子节点( 辅助构建树 )
     */
    @Meta(name = "叶子", exportable = true, order = 7)
    @Column(name = "isLeaf")
    public boolean leaf;

    /**
     * 层次( 辅助构建树 )
     */
    @Meta(name = "层次", exportable = true, order = 8)
    @Column(name = "intLevel")
    public Integer level;

    /**
     * 位置，从父节点开发计算, 0+N ( 辅助构建树 )
     */
    @Meta(name = "位置", exportable = true, order = 9)
    @Column(name = "intOrder")
    public Integer order = 0;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
    private Set<Privilege> children = Sets.newHashSet();

    /**
     * 权限角色
     */
    @Meta(name = "权限角色", hide = true, exportable = false, order = 10)
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tbRolePrivilege",
        joinColumns = @JoinColumn(name = "bigPrivilegeId"),
        inverseJoinColumns = @JoinColumn(name = "bigRoleId"))
    public Set<Role> roles = Sets.newHashSet();
    // static block

    // constructors

    // properties

    // public methods

   

	public Privilege getParent() {
		return parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}



	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


	public Set<Privilege> getChildren() {
		return children;
	}

	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}



	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}



	public void setOrder(Integer order) {
		this.order = order;
	}

    @Override
    public boolean equals(Object o) {
        Privilege p = (Privilege) o;
        if (p.id.longValue() == this.id.longValue()) {
            return true;
        }
        return false;
    }
    
    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
