/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.result;

import com.etone.commons.json.JsonUtil;
import com.etone.ee.modules.data.Meta;
import com.etone.project.core.model.PageResult;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 构建返回信息
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14125 $$
 */
public class Results<T> implements Serializable {
    // members
    private static final long serialVersionUID = 5596433770903606205L;
    private static final Logger logger = LoggerFactory.getLogger(Results.class);

    // 调用结果状态
    private Message message;
    // 表头信息
    private List<Column> columns;
    // 页数据
    private List<T> rows;
    // 分页
    private Pagination pagination;
    // 排序信息
    private List<Sortation> sortation;

    private long total;

    // static block

    // constructors
    private Results() {
        this.columns = Lists.newArrayList();
        this.pagination = new Pagination();
        this.sortation = Lists.newArrayList();
        this.rows = Lists.newArrayList();
        this.message = new Message();
    }
    // properties

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Sortation> getSortation() {
        return sortation;
    }

    public void setSortation(List<Sortation> sortation) {
        this.sortation = sortation;
    }

    // public methods
    public static <T> Results<T> getInstance() {
        return new Results<T>();
    }

    /**
     * 构建分页信息
     *
     * @param page 页信息
     * @param type 实体类型
     * @param <T>  实体类型
     * @return
     */
    public static <T> Results<T> getPage(Page<T> page, Class<?> type) {
        Results<T> actual = Results.getInstance();
        actual.rows = page.getContent();
        actual.buildPagination(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        actual.buildMetadata(type);
        actual.buildSortation(page.getSort());
        return actual;
    }
    
    /**
	 * 构建分页信息mybatis版本
	 * 
	 * @param page
	 *            页信息
	 * @param type
	 *            实体类型
	 * @param <T>
	 *            实体类型
	 * @return
	 */
	public static <T> Results<T> getPage(PageResult<T> page, Class<?> type) {
		Results<T> actual = Results.getInstance();
		actual.rows = page.getResult();
		actual.buildPagination(page.getPageSize(), page.getPageNo(), page
				.getTotalItems(), page.getTotalPages());
		actual.buildMetadata(type);
		//actual.buildSortation(page.getSort());
		return actual;
	}
        
        /**
         * 构建分页信息mybatis版本
         * @param <T>
         * @param page  页信息
         * 
         * @param columnSet 表头
         * @return 
         */
        public static <T> Results<T> getPage(PageResult<T> page, Set<String> columnSet) {
		Results<T> actual = Results.getInstance();
		actual.rows = page.getResult();
		actual.buildPagination(page.getPageSize(), page.getPageNo(), page
				.getTotalItems(), page.getTotalPages());

                if(actual.columns == null) {
                    actual.columns = new ArrayList();
                }
                
                
                for(String columnStr : columnSet) {
                    Column column;
                    column = new Column(columnStr.replace("百分比", "%"), null, false, true, 999);
                    column.dataIndex = columnStr;
                    actual.columns.add(column);
                    
                }
                
		//actual.buildSortation(page.getSort());
		return actual;
	}
    
    /**
     * 构建DataGrid信息
     *
     * @param page 页信息
     * @param type 实体类型
     * @param <T>  实体类型
     * @return
     */
    public static <T> Results<T> getList(List<T> list, Class<?> type) {
        Results<T> actual = Results.getInstance();
        actual.rows = list;
        actual.buildMetadata(type);
        return actual;
    }

    /**
     * 通过JSON反序列化
     *
     * @param json  JSON
     * @param clazz 结果集类型
     * @param <T>   结果集类型
     * @return
     */
    public static <T> Results<T> deserialize(String json, Class<T> clazz) {
        return Results.deserialize(JsonUtil.toMap(json), clazz);
    }

    /**
     * 通过HashMap反序列化
     *
     * @param params Map
     * @param <T>    结果集类型
     * @return
     */
    public static <T> Results<T> deserialize(Map params, Class<T> clazz) {
        Results<T> actual = Results.getInstance();
        if (params != null) {
            if (params.containsKey("pagination")) {
                Object args = params.get("pagination");
                actual.setPagination(JsonUtil.update(args, actual.getPagination()));
            }
            if (params.containsKey("columns")) {
                Object args =params.get("columns");
                List<Column> columns = JsonUtil.fromJson(args, List.class, Column.class);
                actual.setColumns(columns);
            }
            if (params.containsKey("message")) {
                Object args = params.get("message");
                actual.setMessage(JsonUtil.update(args, actual.getMessage()));
            }
            if (params.containsKey("sortation")) {
                Object args =params.get("sortation");
                List<Sortation> sortations = JsonUtil.fromJson(args, List.class, Sortation.class);
                actual.setSortation(sortations);
            }
            if (params.containsKey("rows")) {
                Object args = params.get("rows");
                List<T> contents = JsonUtil.fromJson(args, List.class, clazz);

                actual.setRows(contents);
            }
        }
        return actual;
    }

    // protected methods

    /**
     * 构建表头信息
     *
     * @param type
     * @return
     */
    public void buildMetadata(Class<?> type) {
        this.columns = extractEntity(type);
    }

    /**
     * 构建分页信息
     *
     * @param pageSize    页大小
     * @param currentPage 当前页
     * @param totalCount  记录数
     * @param totalPage   总页数
     * @return 分页信息
     */
    public Pagination buildPagination(int pageSize, int currentPage, long totalCount, int totalPage) {
        this.pagination = new Pagination(pageSize, currentPage, totalCount, totalPage);
        return this.pagination;
    }


    /**
     * 构建排序信息
     *
     * @return
     */
    public List<Sortation> buildSortation(Sort sort) {
        if (this.sortation == null) this.sortation = Lists.newArrayList();
        if (sort != null) {
            for (Sort.Order order : sort) {
                this.sortation.add(new Sortation(order.getProperty(), order.getDirection().name()));
            }
        }
        return this.sortation;
    }

    // friendly methods

    // private methods

    /**
     * 从实体中提表( 临时的，全部从数据库读取)
     *
     * @param type 类型
     * @return
     */
    private List<Column> extractEntity(Class<?> type) {
        List<Column> actual = Lists.newArrayList();
        Field[] fields = type.getDeclaredFields();
        // 构建列头信息
        for (Field field : fields) {
            // 忽略static field
            if (Modifier.isStatic(field.getModifiers())) continue;
            Meta meta = field.getAnnotation(Meta.class);
            String fieldName = field.getName();
            Column column;
            if (meta != null && !meta.hide()) {
                column = new Column(meta.name(), (meta.width() == 0? null : meta.width()), meta.hide(), meta.exportable(), meta.order());
                column.dataIndex = fieldName;
                actual.add(column);
            }
        }
        // 列头排序
        Collections.sort(actual, new Comparator<Column>() {
            @Override
            public int compare(Column o1, Column o2) {
                return o1.order.compareTo(o2.order);
            }
        });
        return actual;
    }

    // inner class

    // test main
}

