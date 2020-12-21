/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.ui;

import com.etone.project.base.entity.Base;
import com.etone.project.base.type.MetaKind;

import javax.persistence.*;

/**
 * 实体元数据信息，通过Meta注解收集，不可编辑<br/>
 * Baseta是Base Datameta的缩写
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14129 $$
 */
@Entity
@Table(name = "tbBaseta")
public class Baseta extends Base {
    // members
    private static final long serialVersionUID = -3055196617529264403L;

    /**
     * 父ID
     */
    @Column(name = "bigParentId")
    public long parentId = 0;

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
