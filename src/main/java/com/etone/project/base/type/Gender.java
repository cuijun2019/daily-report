package com.etone.project.base.type;

/**
 * 性别枚举
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14017 $$
 */
public enum Gender {
    // members
    /**
     * 男
     */
    MALE(0, "男"),
    /**
     * 女
     */
    FEMALE(1, "女");

    private int value;
    private String name;
    // static block

    // constructors
    Gender(int value, String name) {
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
