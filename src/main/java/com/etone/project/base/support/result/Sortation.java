/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.result;

/**
 * 排序信息
 */
public class Sortation {
    public String name;
    public String direction;

    public Sortation() {
    }

    public Sortation(String name, String direction) {
        this.name = name;
        this.direction = direction;
    }
}
