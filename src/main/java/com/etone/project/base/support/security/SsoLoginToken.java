package com.etone.project.base.support.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * User:chz
 * Date:13-12-17
 */
public class SsoLoginToken implements AuthenticationToken {

    private String userName;

    public SsoLoginToken(){}

    public SsoLoginToken(String username){
        userName=username;
    }


    @Override
    public Object getPrincipal() {
        return userName;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getCredentials() {
        return userName;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUserName(){
        return  userName;
    }

    public void clear(){
        userName=null;
    }

    public String toString(){
        return "userName="+userName;
    }

}
