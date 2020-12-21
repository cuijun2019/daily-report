package com.etone.project.base.support.security;

import com.etone.project.base.type.LogLevel;
import com.etone.project.base.type.LogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限模块描述信息(Audit Metadata简称)
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14079 $$
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditmeta {
    /**
     * 模块编号
     *
     * @return 模块编号
     */
    String code() default "";

    /**
     * 模块名称
     *
     * @return 模块名称
     */
    String name();

    /**
     * 模块标识
     *
     * @return
     */
    String symbol();

    /**
     * 访问地址(Controller中必填)
     *
     * @return 访问地址
     */
    String url() default "";

    /**
     * 日志信息
     *
     * @return
     */
    String message() default "";

    /**
     * 日志记录等级, 默认为TRACE<br/>
     * ALL > TRACE > DEBUG > INFO > WARN > ERROR > FATAL > OFF <br/>
     * 当level等级大于等于Log4Database中的moduleLevel时执行日志入库
     *
     * @return
     */
    LogLevel level() default LogLevel.TRACE;

    /**
     * 日志类型
     *
     * @return
     */
    LogType type() default LogType.OPERATE;

    /**
     * 是否强制日志入库<br/>
     * false: 不会参考Log4Database中的moduleLevel属性。<br/>
     * true: 不会参考Log4Database中的moduleLevel属性。<br/>
     *
     * @return
     */
    boolean force() default false;
}
