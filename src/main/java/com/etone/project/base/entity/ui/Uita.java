/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.ui;

import com.etone.project.base.entity.Base;
import com.etone.project.base.type.MetaKind;

import javax.persistence.*;

/**
 * 用户界面字段配置，信息可从Baseta中获得，也可以自定义添加，最终可应用到Gird或表单控件中<br/>
 * Uita 是 UI Metadate的缩写
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14129 $$
 */
@Entity
@Table(name = "tbUita")
public class Uita extends Base {
    // members
    private static final long serialVersionUID = 6103140946792895113L;

    /**
     * 父ID
     */
    @Column(name = "bigParentId")
    public long parentId = 0;

    /**
     * 权限
     */
    @Column(name = "bigPrivilegeId")
    public long privilegeId = 0;

    /**
     * 类型
     */
    @Column(name = "vcKind")
    @Enumerated(EnumType.STRING)
    public MetaKind kind = MetaKind.FIELD;

    /**
     * 数据库
     */
    @Column(name = "vcCatalog", length = 200)
    public String catalog;

    /**
     * 用户
     */
    @Column(name = "vcSchema", length = 200)
    public String schema;

    /**
     * 表
     */
    @Column(name = "vcTableName", length = 200)
    public String tableName;

    /**
     * 别名
     */
    @Column(name = "vcName", length = 200)
    public String name;

    /**
     * 字段
     */
    @Column(name="vcFieldName", length = 200)
    public String fieldName;

    /**
     * 实体属性名
     */
    @Column(name = "vcPropertyName", length = 200)
    public String propertyName;

    /**
     * 宽度, -1 自适应
     */
    @Column(name = "intWidth")
    public int width = -1;

    /**
     * 是否隐藏
     */
    @Column(name = "isHidden")
    public boolean hidden;

    /**
     * 是否导出
     */
    @Column(name = "isExportable")
    public boolean exportable;

    /**
     * 是否叶子
     */
    @Column(name = "isLeaf")
    public boolean leaf;

    /**
     * 排序
     */
    @Column(name = "intOrder")
    public int order = 0;

    /**
     * 层级
     */
    @Column(name = "intLevel")
    public int level = 0;
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
