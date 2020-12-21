/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.project.base.dao.DictionaryDao;
import com.etone.project.base.entity.share.Dictionary;
import com.etone.project.base.support.BaseManager;
import com.etone.project.base.support.meta.MetaScanner;
import com.etone.project.core.model.TreeNode;
import com.etone.project.utils.CacheConstants;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * 数据字典业务逻辑处理
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-8-22 上午9:57:59
 */
@Service
@Transactional
public class DictionaryManager extends BaseManager<Dictionary, Long> {
    // members
    public static final Logger logger = LoggerFactory.getLogger(DictionaryManager.class);

    @Autowired
    private DictionaryDao dictionaryDao;

    @PersistenceUnit
    private EntityManagerFactory emf;
    // static block

    // constructors

    // properties

    @Override
    public void setRepository() {
        super.baseRepository = dictionaryDao;
    }
    
    /**
	 * 根据数据字典类型编码dictionaryTypeCode得到List<TreeNode>对象. <br>
	 * 当id不为空的时候根据id排除自身节点.
	 * 
	 * @param entity
	 *            数据字典对象
	 * @param id
	 *            数据字典ID
	 * @param isCascade
	 *            是否级联加载
	 * @return List<TreeNode> 映射关系： TreeNode.text-->Dicitonary.text;TreeNode.id-->Dicitonary.code;
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> getByDictionaryTypeCode(Dictionary entity,
			String dictionaryTypeCode, Long id, boolean isCascade){
        List<Dictionary> list = Lists.newArrayList();
		List<TreeNode> treeNodes = Lists.newArrayList();
		if (StringUtils.isBlank(dictionaryTypeCode)) {
			return treeNodes;
		}
		StringBuilder sb = new StringBuilder();
		Object[] objs;

		if (StringUtils.isBlank(entity.code)) {
			list = dictionaryDao.findByObject(dictionaryTypeCode);
		} else {
			list = dictionaryDao.findByObject(dictionaryTypeCode,entity.getCode());
		}

		for (Dictionary d : list) {
			// 排除自身
			if (!(d.id == entity.id)) {
				TreeNode t = getTreeNode(d, id, isCascade);
				if (t != null) {
					treeNodes.add(t);
				}
			}

		}
		return treeNodes;
	}
	
	
	/**
	 * /** 根据数据字典类型编码dictionaryTypeCode得到List<TreeNode>对象. <br>
	 * 当id不为空的时候根据id排除自身节点.
	 * 
	 * @param entity
	 *            数据字典对象
	 * @param id
	 *            数据字ID
	 * @param isCascade
	 *            是否级联加载
	 * @return
	 */
	public TreeNode getTreeNode(Dictionary entity, Long id, boolean isCascade){
		TreeNode node = new TreeNode(entity.getCode(), entity.getName());
//        node.getAttributes().put("code",entity.getCode());
//		 Map<String, Object> attributes = new HashMap<String, Object>();
//		 node.setAttributes(attributes);
		List<Dictionary> subDictionaries = getByParentCode(entity.getCode());
		if (subDictionaries.size() > 0) {
			if (isCascade) {// 递归查询子节点
				List<TreeNode> children = Lists.newArrayList();
				for (Dictionary d : subDictionaries) {
					boolean isInclude = true;// 是否包含到节点树
					TreeNode treeNode = null;
					treeNode = getTreeNode(d, id, true);
					// 排除自身
					if (id != null) {
						if (!(d.getId() == id)) {
							treeNode = getTreeNode(d, id, true);
						} else {
							isInclude = false;
						}
					} else {
						treeNode = getTreeNode(d, id, true);
					}
					if (isInclude) {
						children.add(treeNode);
						node.setState(TreeNode.STATE_CLOASED);
					} else {
						node.setState(TreeNode.STATE_OPEN);
					}
				}

				node.setChildren(children);
			}
		}
		return node;
	}
	
	
	/**
	 * 根据父ID得到list对象.
	 * 
	 * @param parentCode
	 *            父级编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getByParentCode(String parentCode) {
		if (parentCode == null) {
			return dictionaryDao.findByCode();  
		} else {
			return dictionaryDao.findByCode(parentCode);  
		}
	}
	
	/**
	 * 查询所有Dictionary对象List.
	 * 
	 * @return
	 */
	
	@Cacheable(value = { CacheConstants.DICTIONARYS_BY_TYPE_CACHE })
	public List<Dictionary> findAll() {
		return dictionaryDao.findAll();  
	}
	
	/**
	 * 得到排序字段的最大值.
	 * 
	 * @return 返回排序字段的最大值
	 */
	public Integer getMaxSort(){
		Integer max = dictionaryDao.findMaxSort();
		if (max == null) {
			max = 0;
		}
		return max;
	}
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(List<Long> ids){
		for (Long id : ids) {
//			Dictionary d = dictionaryDao.findOne(id);
//			dictionaryDao.delete(d);
//			EntityManager em = emf.createEntityManager();
//			em.createNativeQuery("delete from tbDictionary d where d.bigid = "+id);
			dictionaryDao.deleteById(id);
		}
	}
    // public methods

    
    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
