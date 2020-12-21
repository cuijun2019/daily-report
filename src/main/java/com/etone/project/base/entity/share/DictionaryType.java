package com.etone.project.base.entity.share;

import com.etone.ee.modules.data.Meta;
import com.etone.project.base.entity.Base;
import com.google.common.collect.Lists;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;

import java.util.List;

/**
 * 系统字典类型Entity.
 * 
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-8-22 上午9:57:59
 */

@Entity
@Table(name = "tbDictionaryType")
public class DictionaryType extends Base {

	/**
	 * 类型名称
	 */
	@Meta(name = "类型名称", exportable = true, order = 1,hide = false)
	@Column(name = "name")
	public String name;
	/**
	 * 类型编码
	 */
	@Meta(name = "类型编码", exportable = true, order = 1,hide = false)
	@Column(name = "code")
	public String code;
    
    /**
     * 父级类型 即分组
     */
	@JsonBackReference
	@Meta(name = "父级类型即分组", exportable = false, order = 4,hide = true)
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "groupCode",referencedColumnName = "code")
    private DictionaryType groupDictionaryType;
    /**
     * @Transient 父级类型 即分组名称
     */
    @Meta(name = "父级类型分组名称", exportable = true, order = 1,hide = false)
	@Transient
	public String groupDictionaryTypeName;
    
    /**
     * @Transient 父级类型 即分组编码
     */
    @Meta(name = "父级类型即分组编码", exportable = true, order = 1,hide = false)
	@Transient
	@JsonProperty("_parentId")
	public String groupDictionaryTypeCode;

	@PostLoad
	public void postLoad(){
		if(groupDictionaryType != null){
			this.groupDictionaryTypeName = groupDictionaryType.name;
			this.groupDictionaryTypeCode = groupDictionaryType.code;
        }
	}
  
    /**
     * 子DictionaryType集合
     */
	@JsonBackReference
	@Meta(name = "子DictionaryType集合", exportable = false, order = 4,hide = true)
	@OneToMany(mappedBy = "groupDictionaryType")
    public List<DictionaryType> subDictionaryTypes = Lists.newArrayList();
  

	/**
	 * 排序
	 */
	@Meta(name = "排序", exportable = true, order = 1,hide = false)
	@Column(name = "orderNo")
	private Integer orderNo;

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public DictionaryType getGroupDictionaryType() {
		return groupDictionaryType;
	}


	public void setGroupDictionaryType(DictionaryType groupDictionaryType) {
		this.groupDictionaryType = groupDictionaryType;
	}


	public String getGroupDictionaryTypeName() {
		return groupDictionaryTypeName;
	}


	public void setGroupDictionaryTypeName(String groupDictionaryTypeName) {
		this.groupDictionaryTypeName = groupDictionaryTypeName;
	}


	public String getGroupDictionaryTypeCode() {
		return groupDictionaryTypeCode;
	}


	public void setGroupDictionaryTypeCode(String groupDictionaryTypeCode) {
		this.groupDictionaryTypeCode = groupDictionaryTypeCode;
	}


	public List<DictionaryType> getSubDictionaryTypes() {
		return subDictionaryTypes;
	}


	public void setSubDictionaryTypes(List<DictionaryType> subDictionaryTypes) {
		this.subDictionaryTypes = subDictionaryTypes;
	}


	public Integer getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	
}