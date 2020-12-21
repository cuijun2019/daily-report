package com.etone.project.base.dao;

import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户数据操作接口
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 140218 $$
 * @date 2014-02-18
 */
public interface UserDao extends BaseRepository<User, Long> {

    /**
     * 根据账号查找用户信息
     *
     * @param account 登录账号，用户名
     * @return
     */
    User findByAccount(String account);

    @Modifying
    @Query(nativeQuery = true, value = "delete from tbUser where bigid = ?1")
    public Integer deleteById(Long id);

    /**
     * 调用存储过程创建LTE表
     * @param id 
     */
    @Modifying
    @Query(nativeQuery = true, value = "call create_lte_table_by_userid(?1)")
    public void createLteTableByUserId(Long id);
    
    /**
     * 调用存储过程删除LTE表
     * @param id 
     */
    @Modifying
    @Query(nativeQuery = true, value = "call drop_lte_table_by_userid(?1)")
    public void dropLteTableByUserId(Long id);
}
