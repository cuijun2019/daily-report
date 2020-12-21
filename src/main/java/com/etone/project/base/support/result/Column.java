/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.result;

/**
 * 列头配置信息
 */
public class Column {
    public String title;
    public Integer width;
    public boolean sortable;
    public boolean hide;
    public boolean exportable;
    public String dataIndex;
    public Integer order;
    
    public Column() {
    }

    public Column(String title, Integer width, boolean hide, boolean exportable, int order) {
        this.title = title;
        this.width = width;
        this.hide = hide;
        this.exportable = exportable;
        this.order = order;
        sortable = true;
    }
}
