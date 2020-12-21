package com.etone.project.base.dao;

import com.etone.project.base.entity.share.Organization;
import com.etone.project.base.support.BaseRepository;

/**
 * 组织架构数据操作接口
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14087 $$
 */
public interface OrganizationDao extends BaseRepository<Organization, Long> {
    /**
     *
     * @param ouguid
     * @return
     */
    Organization findByOuguid(String ouguid);
}
