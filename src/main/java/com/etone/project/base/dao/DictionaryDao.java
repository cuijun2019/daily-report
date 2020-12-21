package com.etone.project.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.etone.project.base.entity.share.Dictionary;
import com.etone.project.base.support.BaseRepository;

/**
 * 数据字典数据操作接口
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-8-22 上午9:57:59
 */
public interface DictionaryDao extends BaseRepository<Dictionary, Long> {
	
	//@Query("from Dictionary d where  d.dictionaryType.code = ?1 and d.parentDictionary is null order by d.id asc") 
	@Query("from Dictionary d where  d.dictionaryType.code = ?1 order by d.id asc")  
	public List<Dictionary> findByObject(String code);  
	
	//@Query("from Dictionary d where d.dictionaryType.code = ?1 and d.parentDictionary.code = ?2 order by d.id asc")
	@Query("from Dictionary d where d.dictionaryType.code = ?1 order by d.id asc")  
	public List<Dictionary> findByObject(String dictionaryTypeCode,String code1);  
	
	//@Query("from Dictionary d where d.parentDictionary is null order by d.id asc")  
	@Query("from Dictionary d where 1=1 order by d.id asc")  
	public List<Dictionary> findByCode();  
	
	//@Query("from Dictionary d where  d.parentDictionary.code = ?1 order by d.id asc")  
	@Query("from Dictionary d where  1=1 order by d.id asc")  
	public List<Dictionary> findByCode(String parentCode);
	
	@Query("select max(d.orderNo)from Dictionary d")  
	public Integer findMaxSort();  
	
	@Modifying
	@Query(nativeQuery=true,value="delete from tbDictionary where bigid = ?1")  
	public Integer deleteById(Long id);  
	
	@Query("from Dictionary d order by d.id asc")  
	public List<Dictionary> findAll();  
}
