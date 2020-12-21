/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.text.SimpleDateFormat;

/**
 * JSON格式全局配置，针对@ResponseBody
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14170 $$
 */
public class CustomObjectMapper extends ObjectMapper {
    // members

    // static block

    // constructors
    public CustomObjectMapper() {
        configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    // properties

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
