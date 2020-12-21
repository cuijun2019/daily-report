/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.security;

import com.etone.commons.util.Collections3;
import com.etone.commons.util.Encodes;
import com.etone.commons.util.security.Digests;
import com.etone.ee.modules.persistence.Hibernates;
import com.etone.project.base.entity.share.Privilege;
import com.etone.project.base.entity.share.Role;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.manager.OrganizationManager;
import com.etone.project.base.manager.PrivilegeManager;
import com.etone.project.base.manager.UserManager;
import com.etone.project.base.type.AccountStatus;
import com.etone.project.core.model.SessionInfo;
import com.etone.project.core.utils.encode.MD5Util;
import com.etone.project.modules.lte.manager.ILteSysLogManager;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 权限认证
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14158 $$
 */
public class ShiroDbRealm extends AuthorizingRealm {
    // members
    private static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
    // 使用的加密算法
    private static final String HASH_ALGORITHM = "SHA-1";
    // 加密迭代次数
    private static final int HASH_INTERATIONS = 1024;
    // 加密盐的大小
    private static final int SALT_SIZE = 8;
    // 是否使用验证码
    private static boolean isCaptchEnable;
    // 是否使用验证码
    protected boolean captchable = false;
    // 组织服务
    protected OrganizationManager organizationManager;
    // 账号服务
    protected UserManager userManager;
    // 权限模块服务
    protected PrivilegeManager privilegeManager;
    // 验证码服务
    protected ImageCaptchaService imageCaptchaService;
    // static block

    // constructors

    // properties

    public void setOrganizationManager(OrganizationManager organizationManager) {
        this.organizationManager = organizationManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }


    public void setPrivilegeManager(PrivilegeManager privilegeManager) {
        this.privilegeManager = privilegeManager;
    }

    public void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
        this.imageCaptchaService = imageCaptchaService;
    }

    public void setCaptchable(boolean captchable) {
        this.captchable = captchable;
        ShiroDbRealm.setCaptchEnable(captchable);
    }

    public static boolean isCaptchEnable() {
        return isCaptchEnable;
    }

    public static void setCaptchEnable(boolean captchEnable) {
        isCaptchEnable = captchEnable;
    }

// public methods

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
        matcher.setHashIterations(HASH_INTERATIONS);
        // 设置密码验证
        setCredentialsMatcher(matcher);
    }

    /** 加密密码数据
     * @param password
     * @return
     */
    public HashPassword getEncrypt(String password){
        HashPassword result = new HashPassword();

        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        byte[] salt = rng.nextBytes().getBytes();
        String saltStr = Encodes.encodeHex(salt);
        result.salt = saltStr;

        String hashedPassword = new Sha1Hash(password, salt, HASH_INTERATIONS).toString();
        result.password = hashedPassword;
        return result;
    }
    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 验证码效验
        if (captchable) {
            CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authenticationToken;
            String parm = token.getCaptcha();
            int x = 0;
            try {
                x = Integer.parseInt(parm.substring(0,1)); //通过验证码获得生成的随机数
            } catch (Exception e) {
            }
            Date d = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(d);
            calendar.add(calendar.DATE,x);//把日期往后增加一天.整数往后推,负数往前移动
            String yanzhengma = x+"yanmafalsegdltemr"+dateFormat.format(calendar.getTime());
            if (!parm.equals(yanzhengma)){ //如果是链接登录就跳过验证码。
                try {
                    if (!imageCaptchaService.validateResponseForID(SecurityUtils.getSubject().getSession().getId().toString(), parm)) {
                        throw new IncorrectCaptchaException("验证码错误！");
                    }
                } catch (Exception e) {
                    // session如果没有刷新，validateResponseForID会抛出com.octo.captcha.service.CaptchaServiceException的异常
                    throw new IncorrectCaptchaException("验证码错误！");
                }
            }
        }
        // 账号认证
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userManager.getAccount(token.getUsername());

        //这里判断用户状态,如果状态不对就返回一个DisabledAccountException。我们可以通过各种Exception进行判断该用户为何被阻止登录。这里可以看下手册，手册有详细说明
        if (user != null) {
            user.roles = null; // 单点登陆有个很奇怪的bug【如果用户的roles有东西，就不会跳转？】所以这里的暂时解决是设为空
            if (AccountStatus.BLOCKED.equals(user.status)) {
                throw new DisabledAccountException();
            }
            //获取盐，都需要进行一个Encodes.decodeHex。
            byte[] salt = Encodes.decodeHex(user.salt);
            // 设置会话信息
            Hibernates.initLazyProperty(user.roles);
            ShiroUser shiroUser = new ShiroUser(user.id, user.account, user);

            //登陆日志  ServletRequest request 中会存有用户ip和浏览器类型
            userManager.insertSysLog("login",user,((WebSubject)SecurityUtils.getSubject()).getServletRequest());
            //doGetAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
            //ShiroUser只是一个去掉密码的User,无需关心
            //这里所操作的是把数据库的密码和盐返回,然后对用户登录时所填写的密码进行加盐加密，然后和数据库已经加密的密码进行对比。如果匹配则通过。
            return new SimpleAuthenticationInfo(shiroUser, user.password, ByteSource.Util.bytes(salt), getName());
            //return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), getName());
        } else {
            return null;
        }
    }

    // protected methods

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Collection<?> collection = principalCollection.fromRealm(getName());
        if (Collections3.isEmpty(collection)) {
            return null;
        }
        ShiroUser shiroUser = (ShiroUser) collection.iterator().next();

        List<Role> userRoles =  userManager.findOne(shiroUser.getId()).roles;
        List<Role> organizationRoles = Lists.newArrayList();
        if(shiroUser.getUser().ouguid != null) organizationRoles = organizationManager.getOrganization(shiroUser.getUser().ouguid).roles;

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //添加用户拥有的权限.如 "users:add"
        authorizationInfo.addStringPermissions(buildPrivilege(userRoles, organizationRoles, shiroUser));

        return authorizationInfo;
    }



    // friendly methods

    // private methods

    /**
     * 初始化用户权限信息
     *
     * @param userRoles         用户角色
     * @param organizationRoles 部门角色
     * @param shiroUser         用户信息
     * @return
     */
    private Collection<String> buildPrivilege(List<Role> userRoles, List<Role> organizationRoles, ShiroUser shiroUser) {
        Collection<String> shiroPrivileges = Sets.newHashSet();

        // 处理超级管理员
        if (shiroUser.getUser().supervisor) {
            List<Privilege> privileges = privilegeManager.findAll();
            for (Privilege privilege : privileges) {
                if (privilege.leaf) {
                    shiroPrivileges.add(privilege.parent.symbol + ":" + privilege.symbol);
                }
            }
        } else {
            Set<Role> roles = Sets.newHashSet();
            if (userRoles != null) roles.addAll(userRoles);
            if (organizationRoles != null) roles.addAll(organizationRoles);
            for (Role role : roles) {
                for (Privilege privilege : role.privileges) {
                    if(privilege.leaf){
                        shiroPrivileges.add(privilege.parent.symbol + ":" + privilege.symbol);
                    }
                }
            }
        }

        return shiroPrivileges;
    }
    // inner class

    /**
     * 密码HASH实体
     */
    public static class HashPassword {
        public String salt;
        public String password;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public String account;
        public User user;

        /**
         * 构造函数
         *
         * @param id
         * @param account
         * @param user
         */
        public ShiroUser(Long id, String account, User user) {
            this.id = id;
            this.account = account;
            this.user = user;
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setId(user.id.toString());
            sessionInfo.setLoginName(user.account);
            sessionInfo.setFullName(user.fullName);
//            Map<String, Object> userInfo = new HashMap<String, Object>();
//            userInfo.put("fullName", user.fullName);
//            userInfo.put("ouguid", user.ouguid);
//            userInfo.put("ouName", user.ouName);
//            userInfo.put("account", user.account);
//            userInfo.put("id", user.id);
            SecurityUtils.getSubject().getSession().setAttribute("sessionInfo", sessionInfo);
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
        }

        /**
         * 用户ID
         *
         * @return
         */
        public long getId() {
            return id;
        }

        /**
         * 完整用户信息
         *
         * @return
         */
        public User getUser() {
            return user;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return account;
        }

        /**
         * 重载hashCode,只计算account;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(account);
        }

        /**
         * 重载equals,只计算account;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ShiroUser other = (ShiroUser) obj;
            if (account == null) {
                if (other.account != null) return false;
            } else if (!account.equals(other.account)) {
                return false;
            }
            return true;
        }
    }
    // test main
}
