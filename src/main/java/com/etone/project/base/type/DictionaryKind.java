/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.type;

/**
 * 字典类型
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14017 $$
 */
public enum DictionaryKind {
    // members
    /**
     * 字符串
     */
    STRING(0, "字符串"),
    /**
     * 数值
     */
    NUMERIC(1, "数值"),
    /**
     * 日期
     */
    DATE(2, "日期");

    private int value;
    private String name;
    // static block

    // constructors
    DictionaryKind(int value, String name){
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
