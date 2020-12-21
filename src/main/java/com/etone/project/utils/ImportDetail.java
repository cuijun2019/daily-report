package com.etone.project.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *用于记录导入数据的过程
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class ImportDetail {
    
    //每次导入，获取一个开始时间，getTime()转成long,作为KEY，每次入库前或产生错误信息就往List加入信息：“时间 内容”
    public static Map<Long, List<String>> importMsgListMap = new HashMap();
    public static Map<Long, Object> importPercentMap = new HashMap();
}
