/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.etone.ee.modules.data.Meta;
import com.etone.project.base.entity.Base;
import com.google.common.collect.Lists;

/**
 * 数据字典
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-8-22 上午9:57:59
 */
@Entity
@Table(name = "tbDictionary")
//@org.hibernate.annotations.Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.etone.project.base.entity.share")
public class Dictionary extends Base {
    // members

    private static final long serialVersionUID = 7359706900294731860L;
    /**
     * 参数名称
     */
    @Meta(name = "类型名称", exportable = true, order = 1, hide = false)
    @Column(name = "name")
    public String name;
    
    @Column(name = "module")
    public String module;
    /**
     * 参数编码
     */
    @Meta(name = "类型编码", exportable = true, order = 1, hide = false)
    @Column(name = "code")
    public String code;
    /**
     * 参数编码
     */
    @Meta(name = "类型编码", exportable = true, order = 1, hide = false)
    @Column(name = "vcKey")
    public String vcKey;
    /**
     * 参数值
     */
    @Meta(name = "参数值", exportable = true, order = 1, hide = false)
    @Column(name = "value")
    public String value;
    /**
     * 排序
     */
    @Meta(name = "排序", exportable = true, order = 1, hide = false)
    @Column(name = "orderNo")
    public Integer orderNo;
    /**
     * 上级编码
     
    @JsonBackReference
    @Meta(name = "父级类型即分组", exportable = false, order = 4, hide = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentCode", referencedColumnName = "code")
    public Dictionary parentDictionary;
    */
    /**
     * 子Dictionary集合
     *
     * @Transient
     
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parentDictionary")
    public List<Dictionary> subDictionarys = Lists.newArrayList();
    */
    /**
     * 上级编码
     *
     * @Transient
    
    @Meta(name = "上级编码", exportable = true, order = 1, hide = false)
    @Transient
    public String parentDictionaryCode;
     */
    /**
     * 上级名称
     *
     * @Transient
     */
    @Meta(name = "上级名称", exportable = true, order = 1, hide = false)
    @Transient
    public String parentDictionaryName;
    /**
     * 系统字典类型
     */
    @JsonBackReference
    @Meta(name = "系统字典类型", exportable = false, order = 4, hide = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionarytypeCode", referencedColumnName = "code")
    public DictionaryType dictionaryType;
     
    /**
     * 系统字典类型 编码code
     *
     * @Transient
     */
    @Meta(name = "系统字典类型编码", exportable = true, order = 1, hide = false)
    @Transient
    public String dictionaryTypeCode;
    /**
     * 系统字典类型 名称name
     *
     * @Transient
     */
    @Meta(name = "系统字典类型名称", exportable = true, order = 1, hide = false)
    @Transient
    public String dictionaryTypeName;

    /*
    @PostLoad
    public void postLoad() {
        if (parentDictionary != null) {
            this.parentDictionaryCode = parentDictionary.code;
            this.parentDictionaryName = parentDictionary.name;
        }
        if (dictionaryType != null) {
            this.dictionaryTypeCode = dictionaryType.code;
            this.dictionaryTypeName = dictionaryType.name;
        }
    }
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVcKey() {
		return vcKey;
	}

	public void setVcKey(String vcKey) {
		this.vcKey = vcKey;
	}

	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /*
    public Dictionary getParentDictionary() {
        return parentDictionary;
    }

    public void setParentDictionary(Dictionary parentDictionary) {
        this.parentDictionary = parentDictionary;
    }

    public List<Dictionary> getSubDictionarys() {
        return subDictionarys;
    }

    public void setSubDictionarys(List<Dictionary> subDictionarys) {
        this.subDictionarys = subDictionarys;
    }

    public String getParentDictionaryCode() {
        return parentDictionaryCode;
    }

    public void setParentDictionaryCode(String parentDictionaryCode) {
        this.parentDictionaryCode = parentDictionaryCode;
    }
    
    public DictionaryType getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(DictionaryType dictionaryType) {
        this.dictionaryType = dictionaryType;
    }
     */
    public String getParentDictionaryName() {
        return parentDictionaryName;
    }

    public void setParentDictionaryName(String parentDictionaryName) {
        this.parentDictionaryName = parentDictionaryName;
    }

    

    public String getDictionaryTypeCode() {
        return dictionaryTypeCode;
    }

    public void setDictionaryTypeCode(String dictionaryTypeCode) {
        this.dictionaryTypeCode = dictionaryTypeCode;
    }

    public String getDictionaryTypeName() {
        return dictionaryTypeName;
    }

    public void setDictionaryTypeName(String dictionaryTypeName) {
        this.dictionaryTypeName = dictionaryTypeName;
    }
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
