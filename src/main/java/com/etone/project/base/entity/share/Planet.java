/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import com.etone.project.base.entity.Base;
import com.etone.project.base.type.AreaKind;

import javax.persistence.*;

/**
 * 行星信息，目前只配置地球上的各大洲、国家、省、市、行政区、镇
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
@Entity
@Table(name = "tbPlanet")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.etone.project.base.entity.share")
public class Planet extends Base {
    // members
    private static final long serialVersionUID = -2169936284160431064L;

    /**
     * 上级区域
     */
    @Column(name = "bigParentId")
    public long parentId;

    /**
     * 区域类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vcKind")
    public AreaKind kind = AreaKind.PROVINCE;

    /**
     * 区域编号
     */
    @Column(name = "vcCode", length = 50)
    public String code;

    /**
     * 区域名称
     */
    @Column(name = "vcName", length = 200)
    public String name;

    /**
     * 是否叶子节点( 辅助构建树 )
     */
    @Column(name = "isLeaf")
    public boolean leaf;

    /**
     * 层次( 辅助构建树 )
     */
    @Column(name = "intLevel")
    public int level;

    // static block

    // constructors

    // properties

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
