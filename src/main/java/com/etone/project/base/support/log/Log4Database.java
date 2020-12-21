/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.log;

import com.etone.commons.json.JsonUtil;
import com.etone.project.base.manager.OperateLogManager;
import com.etone.project.base.manager.SystemLogManager;
import com.etone.project.base.type.LogLevel;
import com.etone.project.base.type.LogStatus;
import com.etone.project.base.type.LogType;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 全局日志等级<包日志等级<类和方法日志等级
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14159 $$
 */
public class Log4Database implements DBLogger {
    // members
    private static final Logger logger = LoggerFactory.getLogger(Log4Database.class);

    private SystemLogManager systemLogManager;
    private OperateLogManager operateLogManager;
    private LogLevel rootLevel;
    private Map<String, LogLevel> moduleLevel = Maps.newHashMap();

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public SystemLogManager getSystemLogManager() {
        return systemLogManager;
    }

    public void setOperateLogManager(OperateLogManager operateLogManager) {
        this.operateLogManager = operateLogManager;
    }

    public OperateLogManager getOperateLogManager() {
        return operateLogManager;
    }

    public void setRootLevel(LogLevel rootLevel) {
        this.rootLevel = rootLevel;
    }

    public LogLevel getRootLevel() {
        return rootLevel;
    }

    public void setModuleLevel(Map<String, LogLevel> moduleLevel) {
        this.moduleLevel = moduleLevel;
    }

    public Map<String, LogLevel> getModuleLevel() {
        return moduleLevel;
    }
    // static block

    // constructors

    // properties

    // public methods

    @Override
    public void log(String message, LogMessage logMessage, LogLevel level, LogType type, LogStatus status) {
        logMessage.account = "admin";
        logger.info("--------------------------------log4database----------------------------------");
        logger.info("{} - {}",message, JsonUtil.toJson(logMessage));
        //logger.info(JsonUtil.toJson(SecurityUtils.getSubject().getPrincipal()));
    }

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
