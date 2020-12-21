/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import com.etone.project.base.entity.Base;
import com.etone.project.base.type.LogLevel;
import com.etone.project.base.type.LogType;

import javax.persistence.*;
import java.util.Date;

/**
 * 操作日志
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
@Entity
@Table(name = "tbOperateLog")
public class OperateLog extends Base {
    // members
    private static final long serialVersionUID = -8301057347417278430L;

    /**
     * 年，统计辅助
     */
    @Column(name = "intYear")
    public int year;

    /**
     * 月，统计辅助
     */
    @Column(name = "intMonth")
    public int month;

    /**
     * 日，统计辅助
     */
    @Column(name = "intDay")
    public int day;

    /**
     * 时，统计辅助
     */
    @Column(name = "intHour")
    public int hour;

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
     * 调用开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtStartLogTime")
    public Date startLogTime;

    /**
     * 调用结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtEndLogTime")
    public Date endLogTime;

    /**
     * 总耗时，统计辅助
     */
    @Column(name = "intRespCost")
    public int respCost;

    /**
     * 查询耗时，统计辅助
     */
    @Column(name = "intQueryCost")
    public int queryCost;

    /**
     * 返回记录数，统计辅助
     */
    @Column(name = "intRespRecord")
    public int respRecord;

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

    /**
     * 状态，成功或失败
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "intStatus")
    public LogType status;

    /**
     * 日志信息
     */
    @Column(name = "vcContent", length = 5000)
    public String content;

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
