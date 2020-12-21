/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.type;

/**
 * 账号类型
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14017 $$
 */
public enum  AccountKind {
    // members
    /**
     * 用户与员工关联后，用户类型为ORGANIZATION
     */
    ORGANIZATION(0, "组织"),
    /**
     * 默认本地用户
     */
    LOCAL(1, "本地");

    private int value;
    private String name;
    // static block

    // constructors
    AccountKind(int value, String name){
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
