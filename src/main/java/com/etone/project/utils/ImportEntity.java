package com.etone.project.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导入导出注解.
 * 
 * @author Zhang Jianlong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ImportEntity {
	/**
	 * 导入时，对应数据库的字段 主要是用户区分每个字段，不能有annocation重名的 导出时的列名<br>
	 * 导出排序跟定义了annotation的字段的顺序有关
	 * 
	 * @return
	 */
	public String importName() default "";
        
//        /**
//         * 数据类型
//         * @return 
//         */
//        public String type() default "Integer";

	/**
	 * 导出时在excel中每个列的宽 单位为字符，一个汉字=2个字符<br>
	 * 如 以列名列内容中较合适的长度 例如姓名列6 【姓名一般三个字】 性别列4【男女占1，但是列标题两个汉字】<br>
	 * 限制1-255
	 */
	public int exportFieldWidth() default 50;

	/**
	 * 导出时是否进行字段转换 默认值：false <br>
	 * 例如 性别用int存储，导出时可能转换为男，女 <br>
	 * 若是sign为1,则需要在pojo中加入一个方法 get字段名Convert() <br>
	 * 例如，字段sex ，需要加入 public String getSexConvert() 返回值为string <br>
	 * 若是sign为0,则不必管
	 */
	public boolean exportConvert() default false;

	/**
	 * 导入数据是否需要转换 及 对已有的excel，是否需要将字段转为对应的数据 默认值：false <br>
	 * 若是sign为1,则需要在pojo中加入 void<br>
	 * set字段名Convert(String text)
	 */
	public boolean importConvert() default false;
        
        /**
         * 转换规则
         * 如：Y：1，N：2
         * 当值人Y时转成字符串1，再将1转成对应值
         * @return 
         */
        public String convertRugular() default "";
        
        public int doublePrecision() default 0;
        
        public boolean primarykey() default false;
        
        public boolean fromDataFileName() default false;
        
        public String timeFormatString() default "yyyyMMdd";
        
        public boolean notNull() default false;
}
