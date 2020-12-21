package com.etone.project.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *导入功能中，实体对应对的表名
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ImportTable {
    /**
     * 表名
     * @return 
     */
    public String tableName() default "";
    
    public boolean isUseUserId() default false;
}
