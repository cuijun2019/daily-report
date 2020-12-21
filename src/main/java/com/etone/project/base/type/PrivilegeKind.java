/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.type;

/**
 * 权限模块类型
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
public enum PrivilegeKind {
    // members
    /**
     * 菜单
     */
    MENU(0, "菜单"),
    /**
     * 访问路径
     */
    URL(1, "路径"),
    /**
     * 系统
     */
    SYSTEM(2, "系统");

    private final Integer value;
    private final String description;
    // static block

    // constructors
    PrivilegeKind(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    // properties
    public Integer getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }

  

    // public methods
    @Override
    public String toString() {
        return this.getDescription();
    }

    public static PrivilegeKind getResourceState(Integer value) {
		if (null == value)
			return null;
		for (PrivilegeKind _enum : PrivilegeKind.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static PrivilegeKind getResourceState(String description) {
		if (null == description)
			return null;
		for (PrivilegeKind _enum : PrivilegeKind.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}
    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
