package com.etone.project.core.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * session登录用户对象.
 * 
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-9-4
 * 
 */
@SuppressWarnings("serial")
public class SessionInfo implements java.io.Serializable {

	/**
	 * sessionID
	 */
	private String id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 用户全称
	 */
	private String fullName;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 客户端IP
	 */
	private String ip;
	/**
	 * 角色ID集合
	 */
	private List<Long> roleIds;
	/**
	 * 角色名称组合
	 */
	private String roleNames;
	/**
	 * 登录时间
	 */
	private Date loginTime = new Date();;

	public SessionInfo() {
	}

	/**
	 * 
	 * @param id
	 *            sessionID
	 * @param userId
	 *            用户ID
	 * @param loginName
	 *            登录名
	 * @param userType
	 *            用户类型
	 * @param ip
	 *            客户端IP
	 * @param roleIds
	 *            角色ID集合
	 * @param roleNames
	 *            角色名称组合
	 * @param loginTime
	 *            登录时间
	 */
	public SessionInfo(String id, Long userId, String loginName,
			String userType, String ip, List<Long> roleIds, String roleNames,
			Date loginTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.loginName = loginName;
		this.userType = userType;
		this.ip = ip;
		this.roleIds = roleIds;
		this.roleNames = roleNames;
		this.loginTime = loginTime;
	}

	/**
	 * sessionID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 sessionID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 登录名
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 设置 登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 用户类型
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * 设置 用户类型
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 客户端IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置 客户端IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 角色名称组合
	 */
	public String getRoleNames() {
		return roleNames;
	}

	/**
	 * 设置 角色名称组合
	 */
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	/**
	 * 角色ID集合
	 */
	public List<Long> getRoleIds() {
		return roleIds;
	}

	/**
	 * 设置 角色ID集合
	 */
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}



	/**
	 * 设置登录时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
}
