package com.etone.project.base.type;

/**
 * 日志类型
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
public enum LogType {
    // members
    SYSTEM(0, "SYSTEM"),
    SECURITY(1, "SECURITY"),
    OPERATE(2, "OPERATE");

    private Integer value;

    private String name;

    // static block

    // constructors
    LogType(int value, String name) {
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
