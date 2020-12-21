/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.manager;

import com.etone.ee.modules.persistence.Hibernates;
import com.etone.project.base.dao.PrivilegeDao;
import com.etone.project.base.entity.share.Privilege;
import com.etone.project.base.entity.share.Role;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.state.StatusState;
import com.etone.project.base.support.BaseManager;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.exception.DaoException;
import com.etone.project.core.exception.ServiceException;
import com.etone.project.core.exception.SystemException;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.TreeNode;
import com.etone.project.modules.lte.dao.LteMrAdvanceMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 权限管理
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 140218 $$
 * @date 2014-02-18
 */
@Service
@Transactional
public class PrivilegeManager extends BaseManager<Privilege, Long> {
    // members

    public static final Logger logger = LoggerFactory.getLogger(PrivilegeManager.class);
    @Autowired
    private PrivilegeDao privilegeDao;
    
    // static block
    // constructors
    // properties
    @Override
    public void setRepository() {
        super.baseRepository = privilegeDao;
    }

    // public methods
    /**
     * 根据父节点查询其子节点
     *
     * @param parentId 父节点ID
     * @return
     */
    public List<Privilege> findByParentId(long parentId) {
        return privilegeDao.findByParentId(parentId);
    }

    public List<Privilege> findByParentIdAndLeaf(long parentId, boolean leaf) {
        return privilegeDao.findByObject(parentId);
    }

    /**
     * 得到排序字段的最大值.
     *
     * @return 返回排序字段的最大值
     */
    public Integer getMaxSort() {
        Integer max = privilegeDao.findMaxSort();
        if (max == null) {
            max = 0;
        }
        return max;
    }

    /**
     * 删除
     *
     * @param ids
     */
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            privilegeDao.deleteById(id);
        }
    }

    /**
     * 获取所有导航资源（无权限限制）.
     *
     * @param excludeResourceId 需要排除的资源ID 子级也会被排除
     * @param isCascade 是否级联
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public List<TreeNode> getResourceTree(Long parentId, boolean isCascade) {
        List<TreeNode> treeNodes = Lists.newArrayList();
        // 顶级资源
        List<Privilege> privileges = getByParentId(null, StatusState.normal.getValue());
        for (Privilege rs : privileges) {
            TreeNode rootNode = getTreeNode(rs, parentId, isCascade);
            treeNodes.add(rootNode);
        }
        return treeNodes;

    }

    /**
     * 获取所有导航资源（有权限限制）.
     *
     * @param excludeResourceId 需要排除的资源ID 子级也会被排除
     * @param isCascade 是否级联
     * @param privilegeList 具体权限的节点
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public List<TreeNode> getResourceTree(Long parentId, boolean isCascade, List<Privilege> privilegeList) {
        List<TreeNode> treeNodes = Lists.newArrayList();
        // 顶级资源
        List<Privilege> privileges = getByParentId(null, StatusState.normal.getValue());
        for (Privilege privilege : privileges) {
            //排除没有权限的节点
            if (!privilegeList.contains(privilege)) {
                continue;
            }
            TreeNode rootNode = getTreeNode(privilege, parentId, isCascade, privilegeList);
            treeNodes.add(rootNode);
        }
        return treeNodes;

    }

    /**
     * 根据用户 获取权限树结构
     *
     * @param user
     * @return
     */
    public List<TreeNode> getResourceTree(User user) {

        List<TreeNode> treeNodes = Lists.newArrayList();
        if (user.id == 1l) {
            treeNodes = getResourceTree(1l, true);
        } else {
            //Hibernates.initLazyProperty(user);
            List<Role> roles = user.roles;
            List<Privilege> privilegeList = new ArrayList();
            for (Role role : roles) {
                List<Privilege> privileges = role.privileges;
                privilegeList.addAll(privileges);
            }

            treeNodes = getResourceTree(1l, true, privilegeList);
        }
        return treeNodes;
    }

    /**
     * 获取权限树结构(内存计算模式)
     *
     * @param entity
     * @param id
     * @param isCascade 是否级联
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
//    public TreeNode getListTreeNode(List<Privilege> privilegeList, Long id, boolean isCascade) {
//        //TreeNode node = this.resourceToTreeNode(entity,true);
//        List<Privilege> subPrivileges = this.getByParentId(entity.getId(),StatusState.normal.getValue());
//        if (subPrivileges.size() > 0) {
//            if (isCascade) {// 递归查询子节点
//                List<TreeNode> children = Lists.newArrayList();
//                for (Privilege d : subPrivileges) {
//                    boolean isInclude = true;// 是否包含到节点树
//                    TreeNode treeNode = null;
//                    treeNode = getTreeNode(d, id, true);
//                    // 排除自身
//                    if (id != null) {
//                        if (!d.getId().equals(id)) {
//                            treeNode = getTreeNode(d, id, true);
//                        } else {
//                            isInclude = false;
//                        }
//                    } else {
//                        treeNode = getTreeNode(d, id, true);
//                    }
//                    if (isInclude) {
//                        children.add(treeNode);
//                        node.setState(TreeNode.STATE_CLOASED);
//                    } else {
//                        node.setState(TreeNode.STATE_OPEN);
//                    }
//                }
//
//                node.setChildren(children);
//            }
//        }
//        return node;
//    }
    /**
     * 获取权限树结构(查询数据库模式)
     *
     * @param entity
     * @param id
     * @param isCascade 是否级联
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public TreeNode getTreeNode(Privilege entity, Long id, boolean isCascade) {
        TreeNode node = this.resourceToTreeNode(entity, true);
        List<Privilege> subPrivileges = this.getByParentId(entity.getId(), StatusState.normal.getValue());
        if (subPrivileges.size() > 0) {
            if (isCascade) {// 递归查询子节点
                List<TreeNode> children = Lists.newArrayList();
                for (Privilege d : subPrivileges) {
                    boolean isInclude = true;// 是否包含到节点树
                    TreeNode treeNode = null;
                    treeNode = getTreeNode(d, id, true);
                    // 排除自身
                    if (id != null) {
                        if (!d.getId().equals(id)) {
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
     * 获取权限树结构(查询数据库模式)
     *
     * @param entity
     * @param id
     * @param isCascade 是否级联
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public TreeNode getTreeNode(Privilege entity, Long id, boolean isCascade, List<Privilege> privilegeList) {
        TreeNode node = this.resourceToTreeNode(entity, false);
        List<Privilege> subPrivileges = this.getByParentId(entity.getId(), StatusState.normal.getValue());
        if (subPrivileges.size() > 0) {
            if (isCascade) {// 递归查询子节点
                List<TreeNode> children = Lists.newArrayList();
                for (Privilege d : subPrivileges) {
                    if (!privilegeList.contains(d)) {
                        continue;
                    }
                    boolean isInclude = true;// 是否包含到节点树
                    TreeNode treeNode = null;
                    treeNode = getTreeNode(d, id, true, privilegeList);
                    // 排除自身
                    if (id != null) {
                        if (!d.getId().equals(id)) {
                            treeNode = getTreeNode(d, id, true, privilegeList);
                        } else {
                            isInclude = false;
                        }
                    } else {
                        treeNode = getTreeNode(d, id, true, privilegeList);
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
     * Resource转TreeNode
     *
     * @param resource 资源
     * @param isCascade 是否级联
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    private TreeNode resourceToTreeNode(Privilege resource, boolean isCascade) {
        TreeNode treeNode = new TreeNode(resource.getId().toString(),
                resource.getName(), resource.getSymbol());
        // 自定义属性 url
        Map<String, Object> attributes = Maps.newHashMap();
        attributes.put("url", resource.getUrl());
        attributes.put("name", resource.getName());
        attributes.put("code", resource.getCode());
        attributes.put("type", resource.getKind());
        attributes.put("id", resource.getId());
        treeNode.setAttributes(attributes);
        if (isCascade) {
            List<TreeNode> childrenTreeNodes = Lists.newArrayList();
            for (Privilege subResource : resource.getChildren()) {
                TreeNode node = resourceToTreeNode(subResource, isCascade);
                if (node != null) {
                    childrenTreeNodes.add(node);
                }
            }
            treeNode.setChildren(childrenTreeNodes);
        }

        return treeNode;
    }

    /**
     *
     * 根据父ID得到 Privilege. <br> 默认按 orderNo asc,id asc排序.
     *
     * @param parentId 父节点ID(当该参数为null的时候查询顶级资源列表)
     * @param status 数据状态
     * @see com.eryansky.entity.base.state.StatusState <br>status传null则使用默认值
     * 默认值:StatusState.normal.getValue()
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Privilege> getByParentId(Long parentId, Integer status) {
        //默认值 正常
        if (status == null) {
            status = StatusState.normal.getValue();
        }
        if (parentId == null) {
            parentId = 0l;
            return privilegeDao.findByObject(parentId);
        } else {
            return privilegeDao.findByObject(parentId);
        }
    }
    
   
}
