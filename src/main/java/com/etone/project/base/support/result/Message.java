/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.result;

import javax.servlet.http.HttpServletResponse;

/**
 * 返回信息
 */
public class Message {
    /**
     * 状态码
     */
    public int statusCode = HttpServletResponse.SC_OK;
    /**
     * 调用是否成功
     */
    public boolean success = true;
    /**
     * 返回码
     */
    public String resultCode;
    /**
     * 返回信息
     */
    public String message;
    /**
     * 调用者（用于UI回调）
     */
    public String invoker;
    /**
     * 回调地址（用于UI回调）
     */
    public String callback;

    public static Message getInstance(){
        return new Message();
    }
}
