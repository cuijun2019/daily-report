/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support;

import com.etone.ee.modules.persistence.DynamicSpecifications;
import com.etone.ee.modules.persistence.SearchFilter;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.result.Sortation;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

/**
 * 基础服务类
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14093 $$
 */

public abstract class BaseManager<T, ID extends Serializable> {
    // members
    protected BaseRepository<T, ID> baseRepository;
    // static block

    // constructors

    // properties

    // public methods

    @PostConstruct
    public abstract void setRepository();

    //:~ 简化接口 BEGIN
    public Page<T> findPage(Map<String, Object> params, Class<T> clazz) {
        if (params != null && !params.isEmpty()) {
            Results<T> results = Results.deserialize(params, clazz);
            PageRequest pageRequest = buildPageRequest(results);
            Specification<T> spec = buildSpecification(params);
            return findAll(spec, pageRequest);
        }
        return null;
    }

  //:~ 简化接口 BEGIN
    public T findOne(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            Specification<T> spec = buildSpecification(params);
            return findOne(spec);
        }
        return null;
    }

    
    /**
     * 针对MyBatis查询建分页
     *
     * @param content
     * @param params
     * @return
     */
    public <E> Page<E> buildPage(List<E> content, Map params, Class<E> clazz) {
        if (params != null && !params.isEmpty()) {
            Results<E> results = Results.deserialize(params, clazz);
            return new PageImpl<E>(content, buildPageRequest(results), content.size());
        }
        return null;
    }
    //:~ 简化接口 END

    //:~ CrudRepository BEGIN
    public <S extends T> S save(S entity) {
        return baseRepository.save(entity);
    }

    public T findOne(ID id) {
        return baseRepository.findOne(id);
    }

    public boolean exists(ID id) {
        return baseRepository.exists(id);
    }

    public Iterable<T> findAll(Iterable<ID> ids) {
        return baseRepository.findAll(ids);
    }

    public long count() {
        return baseRepository.count();
    }

    public void delete(ID id) {
        baseRepository.delete(id);
    }

    public void delete(T entity) {
        baseRepository.delete(entity);
    }

    public void delete(Iterable<? extends T> entities) {
        baseRepository.delete(entities);
    }

    public void deleteAll() {
        baseRepository.deleteAll();
    }
    //:~ CrudRepository END

    //:~ JpaRepository BEGIN
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    public <S extends T> List<S> save(Iterable<S> entities) {
        return baseRepository.save(entities);
    }

    public void flush() {
        baseRepository.flush();
    }

    public T saveAndFlush(T entity) {
        return baseRepository.saveAndFlush(entity);
    }

    public void deleteInBatch(Iterable<T> entities) {
        baseRepository.deleteInBatch(entities);
    }

    public void deleteAllInBatch() {
        baseRepository.deleteAllInBatch();
    }
    //:~ JpaRepository END

    //:~ PagingAndSortingRepository BEGIN
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }
    //:~ PagingAndSortingRepository END

    //:~ PagingAndSortingRepository BEGIN
    public T findOne(Specification<T> spec) {
        return baseRepository.findOne(spec);
    }
    //:~ PagingAndSortingRepository END

    //:~ JpaSpecificationExecutor BEGIN
    public List<T> findAll(Specification<T> spec) {
        return baseRepository.findAll(spec);
    }

    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return baseRepository.findAll(spec, pageable);
    }

    public List<T> findAll(Specification<T> spec, Sort sort) {
        return baseRepository.findAll(spec, sort);
    }

    public long count(Specification<T> spec) {
        return baseRepository.count(spec);
    }
    //:~ JpaSpecificationExecutor END

    // protected methods

    // friendly methods

    // private methods

    /**
     * 创建分页请求.
     */
    private <E> PageRequest buildPageRequest(Results<E> results) {
        Sort sort = null;
        int page = results.getPagination().currentPage, size = results.getPagination().pageSize;
        if (results.getSortation() != null) {
            List<Sort.Order> orders = Lists.newArrayList();
            for (Sortation item : results.getSortation()) {
                Sort.Order order = new Sort.Order(Sort.Direction.fromString(item.direction.toUpperCase()), item.name);
                orders.add(order);
            }
            if (!orders.isEmpty()) sort = new Sort(orders);
        }
        return new PageRequest(page - 1, size, sort);
    }

    /**
     * 创建动态查询条件组合
     *
     * @param searchParams
     * @return
     */
    private Specification<T> buildSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values());
        return spec;
    }
    // inner class

    // test main
}
