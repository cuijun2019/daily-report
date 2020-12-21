/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.result;

/**
 * 分页信息
 */
public class Pagination {
    public long startRow = 0;
    public long endRow = 0;
    public long totalCount = 0;
    public int pageSize = 10;
    public int currentPage = 1;
    public int totalPage = 1;

    public Pagination() {
    }

    public Pagination(Integer pageSize, Integer currentPage, Long totalCount, Integer totalPage) {
        this.pageSize = pageSize != null ? pageSize : this.pageSize;
        this.currentPage = currentPage != null ? currentPage : this.currentPage;
        this.totalCount = totalCount != null ? totalCount : this.totalCount;
        this.totalPage = totalPage != null ? totalPage : this.totalPage;
        long start = (this.currentPage - 1) * this.pageSize + 1;
        long end = this.currentPage * this.pageSize;
        this.startRow = start > 0 ? start : 1;
        this.endRow = end >= this.totalCount ? this.totalCount : end;
    }
}
