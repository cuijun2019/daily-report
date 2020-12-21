/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.meta;

import com.etone.project.base.type.MetaKind;

/**
 * 元数据信息
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14129 $$
 */
public class MetaInfo {
    // members
    /**
     * ID
     */
    public long id;
    /**
     * 父ID
     */
    public long parentId;
    /**
     * 类型
     */
    public MetaKind kind = MetaKind.FIELD;

    /**
     * 数据库
     */
    public String catalog;

    /**
     * 用户
     */
    public String schema;

    /**
     * 表
     */
    public String tableName;

    /**
     * 别名
     */
    public String name;

    /**
     * 字段
     */
    public String fieldName;

    /**
     * 实体属性名
     */
    public String propertyName;

    /**
     * 宽度, -1 自适应
     */
    public int width = -1;

    /**
     * 是否隐藏
     */
    public boolean hidden;

    /**
     * 是否导出
     */
    public boolean exportable;

    /**
     * 是否叶子
     */
    public boolean leaf;

    /**
     * 排序
     */
    public int order = 0;

    /**
     * 层级
     */
    public int level = 0;
    // static block

    // constructors

    /**
     * 构造函数
     *
     * @param kind 类型
     * @param catalog 数据库
     * @param name 数据库别名
     * @param leaf 是否叶子
     * @param order 排序
     * @param level 层级
     */
    public MetaInfo(MetaKind kind, String catalog, String name, boolean leaf, int order, int level) {
        this.kind = kind;
        this.catalog = catalog;
        this.name = name;
        this.leaf = leaf;
        this.order = order;
        this.level = level;
    }

    /**
     * 构造函数
     *
     * @param kind 类型
     * @param catalog 数据库
     * @param tableName 表名
     * @param name 表别名
     * @param leaf 是否叶子
     * @param order 排序
     * @param level 层级
     */
    public MetaInfo(MetaKind kind, String catalog, String tableName, String name, boolean leaf, int order, int level) {
        this.kind = kind;
        this.catalog = catalog;
        this.tableName = tableName;
        this.name = name;
        this.leaf = leaf;
        this.order = order;
        this.level = level;
    }

    /**
     * 构造函数
     *
     * @param kind 类型
     * @param catalog 数据库
     * @param tableName 表名
     * @param name 字段别名
     * @param fieldName 字段名
     * @param leaf 是否叶子
     * @param order 排序
     * @param level 层级
     */
    public MetaInfo(MetaKind kind, String catalog, String tableName, String name, String fieldName, boolean leaf, int order, int level) {
        this.kind = kind;
        this.catalog = catalog;
        this.tableName = tableName;
        this.name = name;
        this.fieldName = fieldName;
        this.leaf = leaf;
        this.order = order;
        this.level = level;
    }

    // properties

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
