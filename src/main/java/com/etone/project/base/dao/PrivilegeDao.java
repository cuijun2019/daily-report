package com.etone.project.base.dao;

import com.etone.project.base.entity.share.Privilege;
import com.etone.project.base.support.BaseRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 权限数据操作接口
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14093 $$
 */
public interface PrivilegeDao extends BaseRepository<Privilege, Long> {

    /**
     * 根据父节点查询其子节点
     *
     * @param parentId 父节点ID
     * @return
     */
    List<Privilege> findByParentId(long parentId);

    /**
     * 根据父节点查询其子节点
     *
     * @param parentId 父节点ID
     * @param leaf 是否叶子节点
     * @return
     */
    @Query("from Privilege d where d.parentId =?1 order by d.order asc")
    List<Privilege> findByObject(long parentId);

    @Query("select max(d.order)from Privilege d")
    public Integer findMaxSort();

    @Modifying
    @Query(nativeQuery = true, value = "delete from tbPrivilege where bigid = ?1")
    public Integer deleteById(Long id);
}
