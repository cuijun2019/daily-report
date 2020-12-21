/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.type;

/**
 * 组织架构类型
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14017 $$
 */
public enum OrganizationKind {
    // members
    /**
     * 集团
     */
    GROUP(0, "集团"),
    /**
     * 省公司
     */
    COMPANY(1, "省公司"),
    /**
     * 分公司
     */
    BRANCH(2, "分公司"),
    /**
     * 部门
     */
    DEPARTMENT(3, "部门"),
    /**
     * 科室
     */
    OFFICE(4, "科室"),
    /**
     * 营业厅
     */
    HALL(5, "营业厅"),
    /**
     * 销售点
     */
    OUTLETS(6, "销售点");

    private int value;
    private String name;
    // static block

    // constructors
    OrganizationKind(int value, String name) {
        this.value = value;
        this.name = name;
    }

    // properties
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public methods
    @Override
    public String toString() {
        return this.getName();
    }

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
