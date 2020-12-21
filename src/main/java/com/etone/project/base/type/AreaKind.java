/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.type;

/**
 * 区域类型
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14017 $$
 */
public enum AreaKind {
    // members
    /**
     * 洲
     */
    CONTINENT(0, "洲"),
    /**
     * 洋
     */
    OCEAN(1, "洋"),
    /**
     * 国家
     */
    COUNTRY(2, "国家"),
    /**
     * 省
     */
    PROVINCE(3, "省"),
    /**
     * 市
     */
    CITY(4, "市"),
    /**
     * 镇、县、乡
     */
    TOWN(5, "镇"),
    /**
     * 区
     */
    REGION(6, "区");

    private int value;
    private String name;
    // static block

    // constructors
    AreaKind(int value, String name){
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
