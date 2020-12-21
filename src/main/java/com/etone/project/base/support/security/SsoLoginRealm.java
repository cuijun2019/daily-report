package com.etone.project.base.support.security;

import com.etone.commons.util.Collections3;
import com.etone.project.base.entity.share.Privilege;
import com.etone.project.base.entity.share.Role;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.manager.OrganizationManager;
import com.etone.project.base.manager.PrivilegeManager;
import com.etone.project.base.manager.UserManager;
import com.etone.project.core.model.SessionInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User:chz
 * Date:13-12-18
 */
public class SsoLoginRealm extends AuthorizingRealm {
    // 组织服务
    protected OrganizationManager organizationManager;
    // 账号服务
    protected UserManager userManager;
    // 权限模块服务
    protected PrivilegeManager privilegeManager;
    // 验证码服务
    protected ImageCaptchaService imageCaptchaService;
    private String url = "http://10.248.112.31:8099/dbmp/service";

    Logger logger = LoggerFactory.getLogger(SsoLoginRealm.class);

    // static block

    // constructors

    // properties

    public void setOrganizationManager(OrganizationManager organizationManager) {
        this.organizationManager = organizationManager;
    }

    public void setPrivilegeManager(PrivilegeManager privilegeManager) {
        this.privilegeManager = privilegeManager;
    }


    public SsoLoginRealm() {
        setCredentialsMatcher(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(SsoLoginToken.class);
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        SsoLoginToken token = (SsoLoginToken) authcToken;
        Object username = token.getPrincipal();
      //  username = "/7ZglT6TPz3uHLD0TlHpRbWwKhtWOPnU";
        logger.info("request user encry info:" + username+",get sessionId");
        logger.info("verifica the userInfo");
        //String result = HttpActionSoapUtil.invoke( url, request);
        //logger.debug("response:"+result);
        Pattern pattern = Pattern.compile("<message>(.*?)<");
        //Matcher matcher = pattern.matcher(result);
        String userLoginName="";
//        if (matcher.find()) {
//            String   ui = matcher.group(1);
//            logger.info("id:" + ui);
//            userLoginName=ui.split("[|]")[0];
//        }
        User user = userManager.getAccount(token.getUserName());
        //不允许无username
        if (user == null) {
            // 自定义异常，于前端捕获
            throw new AccountException("can't find user!:" + token);
        }
        ShiroDbRealm.ShiroUser shiroUser = new ShiroDbRealm.ShiroUser(user.id, user.account, user);
        //初始化用户缓存
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setId(user.id.toString());
        sessionInfo.setLoginName(user.account);
        sessionInfo.setFullName(user.fullName);
        SecurityUtils.getSubject().getSession().setAttribute("sessionInfo", sessionInfo);
        SecurityUtils.getSubject().getSession().setAttribute("user", user);
        // 验证密码，这里可以缓存认证
        return new SimpleAuthenticationInfo(shiroUser, user.password, getName());
        //    return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
    }

        /**
         * 初始化用户权限信息
         * @param userRoles         用户角色
         * @param organizationRoles 部门角色
         * @param shiroUser         用户信息
         * @return
         */
        private Collection<String> buildPrivilege (List < Role > userRoles, List < Role > organizationRoles, ShiroDbRealm.ShiroUser shiroUser){
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
                        if (privilege.leaf) {
                            shiroPrivileges.add(privilege.parent.symbol + ":" + privilege.symbol);
                        }
                    }
                }
            }
            return shiroPrivileges;
        }
    }
