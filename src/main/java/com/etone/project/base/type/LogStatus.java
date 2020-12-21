package com.etone.project.base.type;

/**
 * 日志状态
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
public enum LogStatus {
    // members
    SUCCESS(0, "成功"),
    FAILURE(1, "失败");

    private Integer value;

    private String name;

    // static block

    // constructors
    LogStatus(int value, String name) {
        setValue(value);
        setName(name);
    }
    // properties

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
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
        return getName();
    }
}
