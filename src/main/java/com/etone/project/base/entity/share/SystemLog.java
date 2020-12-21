/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import com.etone.project.base.entity.Base;
import com.etone.project.base.type.LogLevel;
import com.etone.project.base.type.LogType;

import javax.persistence.*;

/**
 * 登录日志
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
@Entity
@Table(name = "tbSystemLog")
public class SystemLog extends Base {
    // members
    private static final long serialVersionUID = -3667574128025958621L;

    /**
     * IPV4
     */
    @Column(name="vcIpv4", length = 20)
    public String ipv4;

    /**
     * IPV6
     */
    @Column(name="vcIpv6", length = 20)
    public String ipv6;

    /**
     * 登录账号
     */
    @Column(name="vcAccount", length = 50)
    public String account;

    /**
     * 系统编码
     */
    @Column(name="vcSystemCode", length = 100)
    public String systemCode;

    /**
     * 系统名称
     */
    @Column(name="vcSystem", length = 100)
    public String system;

    /**
     * 模块编码
     */
    @Column(name="vcModuleCode", length = 100)
    public String moduleCode;

    /**
     * 模块名称
     */
    @Column(name="vcModule", length = 100)
    public String module;

    /**
     * 内容
     */
    @Column(name = "vcContent", length = 5000)
    public String content;

    /**
     * 返回码
     */
    @Column(name = "vcResultCode", length = 20)
    public String resultCode;

    /**
     * 日志级别
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "intLogLevel")
    public LogLevel logLevel;

    /**
     * 日志级别名称
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vcLogLevelName", length = 20)
    public LogLevel logLevelName;

    /**
     * 日志类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "intLogType")
    public LogType logType;

    /**
     * 日志类型名称
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vcLogTypeName", length = 20)
    public LogType logTypeName;


    // static block

    // constructors

    // properties

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
