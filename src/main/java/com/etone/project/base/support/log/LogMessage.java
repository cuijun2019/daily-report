/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.log;

import com.etone.project.base.entity.share.OperateLog;

/**
 * 日志信息
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
public class LogMessage extends OperateLog {
    // members
    private Object[] args;

    // static block

    // constructors

    // properties
    // @Value("${system.code}")
    public void setSystemCode(String systemCode){
        this.systemCode = systemCode;
    }

    // @Value("${system.name}")
    public void setSystem(String system){
        this.system = system;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }
    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
