package com.etone.project.base.type;

/**
 * 账号状态
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14017 $$
 */
public enum AccountStatus {
    /**
     * 停用
     */
    BLOCKED(0, "停用"),
    /**
     * 正常
     */
    NORMAL(1, "正常");

    private int value;
    private String name;
    // static block

    // constructors
    AccountStatus(int value, String name) {
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
}
