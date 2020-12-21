/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import com.etone.project.base.entity.Base;
import com.etone.project.base.state.StatusState;
import com.etone.project.core.collections.Collections3;
import com.etone.project.core.utils.ConvertUtils;
import com.google.common.collect.Lists;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * 角色配置
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
@Entity
@Table(name = "tbRole")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.etone.project.base.entity.share")
public class Role extends Base {
    // members
    private static final long serialVersionUID = -512591809357003162L;

    /**
     * 角色名称
     */
    @NotBlank
    @Length(max = 200)
    @Column(name = "vcName", nullable = false, length = 200)
    public String name;

    /**
     * 角色用户
     */
    @JsonBackReference("role-users")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tbUserRole",
        joinColumns = @JoinColumn(name = "bigRoleId"),
        inverseJoinColumns = @JoinColumn(name = "bigUserId"))
    public List<User> users = Lists.newArrayList();


    /**
     * 角色权限
     */
    @JsonManagedReference("role-privileges")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbRolePrivilege",
        joinColumns = @JoinColumn(name = "bigRoleId"),
        inverseJoinColumns = @JoinColumn(name = "bigPrivilegeId"))
    public List<Privilege> privileges = Lists.newArrayList();

    
    /**
     * 角色拥有的资源字符串,多个之间以","分割
     * 
     * @return
     */
    @Transient
    public String getResourceNames() {
    	List<Privilege> ms = Lists.newArrayList();

    	for(Privilege m: privileges){
    		if(m.getKind() == StatusState.normal.getValue()){
    			ms.add(m);
    		}
    	}


        return ConvertUtils.convertElementPropertyToString(ms, "name",
                ", ") ;

    }
    
    /**
     * 角色拥有的资源id字符串集合
     * 
     * @return
     */
    @Transient
    public List<Long> getResourceIds() {
    	List<Long> resourceIds = null;
        if (!Collections3.isEmpty(privileges)) {
            resourceIds = ConvertUtils.convertElementPropertyToList(privileges, "id");
        }

        return resourceIds;
    }
    

    /**
     * 分配用户角色
     *
     * @param users 用户
     */
    public void grantUser(List<User> users) {
        this.users = users;
    }

    /**
     * 分配角色权限
     *
     * @param privileges 权限
     */
    public void grantPrivilege(List<Privilege> privileges) {
        this.privileges = privileges;
    }



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	
    
    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
