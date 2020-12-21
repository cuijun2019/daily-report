package com.etone.project.base.type;

/**
 * 原数据类型
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14087 $$
 */
public enum MetaKind {
    DATABAE(1, "DATABASE"),
    TABLE(2, "TABLE"),
    FIELD(3, "FIELD");

    private final int value;
    private final String name;

    MetaKind(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
