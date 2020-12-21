package com.etone.project.base.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.etone.project.base.entity.share.Dictionary;
import com.etone.project.base.entity.share.DictionaryType;
import com.etone.project.base.support.BaseRepository;

/**
 * 数据字典类型数据操作接口
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-8-22 上午9:57:59
 */
public interface DictionaryTypeDao extends BaseRepository<DictionaryType, Integer> {
	@Query("select max(d.orderNo)from DictionaryType d")  
	public Integer findMaxSort();  
	
	@Modifying
	@Query(nativeQuery=true,value="delete from tbdictionarytype where bigid = ?1")  
	public Integer deleteById(Long id);  
}
