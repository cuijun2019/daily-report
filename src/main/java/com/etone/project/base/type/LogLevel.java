package com.etone.project.base.type;

/**
 * 日志级别<br/>
 * ALL > TRACE > DEBUG > INFO > WARN > ERROR > FATAL > OFF
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
public enum LogLevel {
    // members
    ALL(0, "ALL"),
    TRACE(1, "TRACE"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO"),
    WARN(4, "WARN"),
    ERROR(5, "ERROR"),
    FATAL(6, "FATAL"),
    OFF(7, "OFF");

    private Integer value;

    private String name;

    // static block

    // constructors
    LogLevel(int value, String name) {
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
