package com.etone.project.base.support.log;

import com.etone.project.base.type.LogLevel;
import com.etone.project.base.type.LogStatus;
import com.etone.project.base.type.LogType;

/**
 * 对该类型功能用途的描述
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14067 $$
 */
public interface DBLogger {

    void log(String message, LogMessage logMessage, LogLevel level, LogType type, LogStatus status);

}
