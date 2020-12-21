package com.etone.project.utils;

import com.etone.project.core.utils.DateUtil;
import com.etone.project.modules.dbimp.entity.Mre;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class ImportUtil {
    
    public static String entityPackage = Mre.class.getPackage().getName();
    public static Map<String, List<String>> columnListMap = new HashMap();
    public static Map<String, List<String>> importNameListMap = new HashMap();
    
    /**
     * 列的转换规则集合
     * 第一个KEY：对应实体名
     * 第二个KEY：对应列名
     * 最里面的MAP，对应具体的转换规则
     */
    public static Map<String, Map<String, Map<String, String>>> regularMapMapMap = new HashMap();
    
    public static Map<String, String> getContentMap(List<String> columnList, List<String> contentList) {
        
        if(columnList == null || columnList.isEmpty()) {
            Logger.getLogger(ImportUtil.class.getName()).log(Level.SEVERE, "列内容为空");
            return null;
        }
        
        if(contentList == null || contentList.isEmpty()) {
            Logger.getLogger(ImportUtil.class.getName()).log(Level.SEVERE, "表内容为空");
            return null;
        }
        
        
        if (columnList.size() != contentList.size()) {
            
            Logger.getLogger(ImportUtil.class.getName()).log(Level.SEVERE, "\u8868\u5217\u6570\u548c\u5185\u5bb9\u4e0d\u5bf9,"
                    + " 列数：" + columnList.size() + ", 内容列数:" + contentList.size());
            return null;
        }
        Map<String, String> contentMap = new MyMap();
        for (int i = 0; i < columnList.size(); i++) {
            contentMap.put(columnList.get(i), contentList.get(i));
        }
        return contentMap;
    }
    
    public static String getImportName(Field f) {
        
        if(f == null) {
            return null;
        }
        
        ImportEntity annotation = f.getAnnotation(ImportEntity.class);
            
        if(annotation == null) {
            return null;
        }
        String importName = annotation.importName().trim();

        if(importName.equals("")) {
            importName = f.getName();
        }
        
        return importName;
    }

    public static List<?> revertToEntity(List<Map<String, String>> contentMapList, Class pojoClass) {
        List dist = new ArrayList();
        try {
            Field[] filed = pojoClass.getDeclaredFields();
            Map<String, Method> fieldSetMap = new HashMap<String, Method>();
            Map<String, Map<String, String>> fieldSetConvertMap = new HashMap();
            for (int i = 0; i < filed.length; i++) {
                Field f = filed[i];
                ImportEntity importEntity = f.getAnnotation(ImportEntity.class);
                if (importEntity != null) {
                    String fieldname = f.getName();
                    String setMethodName = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
                    Method setMethod = pojoClass.getMethod(setMethodName, new Class[]{f.getType()});
                    String importName = ImportUtil.getImportName(f);
                    fieldSetMap.put(importName, setMethod);
                    if (importEntity.convertRugular().indexOf(":") > 0) {
                        String[] regulars = importEntity.convertRugular().split(",");
                        Map<String, String> regularMap = new HashMap();
                        for (String regular : regulars) {
                            String[] split = regular.split(":");
                            if (split.length == 2) {
                                regularMap.put(split[0], split[1].equals("null") ? null : split[1]);
                            } else {
                                if(split.length == 1) {
                                    regularMap.put(split[0], "");
                                }
                            }
                        }
                        fieldSetConvertMap.put(importName, regularMap);
                    }
                }
            }
            for (Map<String, String> contentMap : contentMapList) {
                Object tObject = pojoClass.newInstance();
                dist.add(tObject);
                Set<String> fieldSet = fieldSetMap.keySet();
                for (String importName : fieldSet) {
                    Method setMethod = (Method) fieldSetMap.get(importName);
                    Type[] ts = setMethod.getGenericParameterTypes();
                    String xclass = ts[0].toString();
                    if (!contentMap.containsKey(importName)) {
//                        Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, "\u5c5e\u6027\uff1a" + exportName + "\u6ca1\u6709\u503c");
                    }
                    
                    String value = contentMap.get(importName);
                    if (value == null) {
                        continue;
                    }
                    
                    Map<String, String> regular = fieldSetConvertMap.get(importName);
                    if (regular != null && !regular.isEmpty()) {
                        Set<String> keySet = regular.keySet();
                        
                        for(String key : keySet) {
                            value = value.replace(key, regular.get(key));
                        }
                    }
                    
                    if (xclass.equals("class java.lang.String")) {
                        setMethod.invoke(tObject, value);
                    } else if (xclass.equals("class java.util.Date")) {
                        Date date = DateUtil.parseStringToDate(value);
                        setMethod.invoke(tObject, date);
                    } else if (xclass.equals("class java.sql.Timestamp")) {
                        Date date = DateUtil.parseStringToDate(value);
                        if (date != null) {
                            Timestamp date1 = new Timestamp(date.getTime());
                            setMethod.invoke(tObject, date1);
                        }
                    } else if (xclass.equals("class java.lang.Boolean")) {
                        Boolean valBool = null;
                        if (value.toUpperCase().equals("TRUE")) {
                            valBool = true;
                        } else {
                            if (value.toUpperCase().equals("FALSE")) {
                                valBool = false;
                            }
                        }
                        setMethod.invoke(tObject, valBool);
                    } else if (xclass.equals("boolean")) {
                        Boolean valBool = null;
                        if (value.toUpperCase().equals("TRUE")) {
                            valBool = true;
                        } else {
                            if (value.toUpperCase().equals("FALSE")) {
                                valBool = false;
                            }
                        }
                        setMethod.invoke(tObject, valBool.booleanValue());
                    } else if (xclass.equals("class java.lang.Integer")) {
                        Integer valInt = null;
                        try {
                            valInt = Integer.parseInt(value.trim());
                        } catch (NumberFormatException numberFormatException) {
                        }
                        setMethod.invoke(tObject, valInt);
                    } else if (xclass.equals("int")) {
                        Integer valInt = null;
                        try {
                            valInt = Integer.parseInt(value.trim());
                        } catch (NumberFormatException numberFormatException) {
                        }
                        setMethod.invoke(tObject, valInt.intValue());
                    } else if (xclass.equals("class java.lang.Long")) {
                        Long valLong = null;
                        try {
                            valLong = Long.parseLong(value.trim());
                        } catch (NumberFormatException numberFormatException) {
                        }
                        setMethod.invoke(tObject, valLong);
                    } else if (xclass.equals("long")) {
                        Long valLong = null;
                        try {
                            valLong = Long.parseLong(value.trim());
                        } catch (NumberFormatException numberFormatException) {
                        }
                        setMethod.invoke(tObject, valLong.longValue());
                    } else if (xclass.equals("class java.math.BigDecimal")) {
                        BigDecimal valDecimal = null;
                        try {
                            valDecimal = new BigDecimal(value.trim());
                        } catch (Exception e) {
                        }
                        setMethod.invoke(tObject, valDecimal);
                    } else if(xclass.equals("class java.lang.Double")) {
                        Double valDouble = null;
                        try {
                            valDouble = new Double(value.trim());
                        } catch (NumberFormatException numberFormatException) {
                        }
                        setMethod.invoke(tObject, valDouble);
                    } else if(xclass.equals("double")) {
                        Double valDouble = null;
                        try {
                            valDouble = new Double(value.trim());
                        } catch (NumberFormatException numberFormatException) {
                        }
                        setMethod.invoke(tObject, valDouble.doubleValue());
                    } else if(xclass.equals("class java.lang.Float")) {
                        Float valFloat = null;
                        try {
                            valFloat = new Float(value.trim());
                        } catch (Exception exception) {
                        }
                        setMethod.invoke(tObject, valFloat);
                    } else if(xclass.equals("float")) {
                        Float valFloat = null;
                        try {
                            valFloat = new Float(value.trim());
                        } catch (Exception exception) {
                        }
                        setMethod.invoke(tObject, valFloat.floatValue());
                    } else {
                        Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, tObject.getClass().getSimpleName() + ":unknow dataType:" + xclass);
                    }
                }
            }
        } catch (Exception exception) {
            Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, exception.getMessage(), exception);
        }
        return dist;
    }

    public static Map<String, List> revertToMapListEntity(Map<String, List<Map<String, String>>> contentsMap, String packagePath) {
        Set<String> entityNameSet = contentsMap.keySet();
        Map<String, List> map = new HashMap();
        for (String entityName : entityNameSet) {
            try {
                Class<?> entityObject = Class.forName(packagePath + "." + entityName);
                List<Map<String, String>> contentList = contentsMap.get(entityName);
                List<?> entityList = revertToEntity(contentList, entityObject);
                map.put(entityName, entityList);
            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, "\u6728\u6709\u627e\u5230\u7c7b\uff1a" + packagePath + "." + entityName);
            }
        }
        return map;
    }

    public static String getTableNameByEntityclass(Class entityclass) {
        Annotation annotation = entityclass.getAnnotation(ImportTable.class);
        String tableName;
        if(annotation == null || ((ImportTable)annotation).tableName().isEmpty()) {
            tableName = entityclass.getSimpleName();
        } else {
            tableName = ((ImportTable)annotation).tableName();
        }
        
        return tableName;
    }
    
    /**
     * 返回实体数据成员名List,对应表名
     * @param entityClass
     * @return 
     */
    public static List<String> getColumnList(Class entityClass) {
        Field[] declaredFields = entityClass.getDeclaredFields();
        
        List<String> columnList = new ArrayList();
        
        for(Field f : declaredFields) {
            String column = f.getName();
            columnList.add(column);
        }
        
        return columnList;
    }
    
    /**
     * 返回实体数据成员注解导入名，如果导入名importName是空，就返回数据成员名
     * @param entityClass
     * @return 
     */
    public static List<String> getImportNameList(Class entityClass) {
        Field[] declaredFields = entityClass.getDeclaredFields();
        
        List<String> importNameList = new ArrayList();
        
        for(Field f : declaredFields) {
            ImportEntity annotation = f.getAnnotation(ImportEntity.class);
            if(annotation == null) {
                continue;
            }
            String importName = annotation.importName();
            
            if(importName.isEmpty()) {
                importName = f.getName();
            }
            
            importNameList.add(importName);
        }
        
        return importNameList;
    }
    
    public static Map<String, Map<String, String>> getRegularMapMap(Class entityClass) {
        Field[] filed = entityClass.getDeclaredFields();

        Map<String, Map<String, String>> regularMapMap = new HashMap();
        for (int i = 0; i < filed.length; i++) {
            Field f = filed[i];
            ImportEntity importEntity = f.getAnnotation(ImportEntity.class);
            if (importEntity != null) {
                String importName = ImportUtil.getImportName(f);

                if (importEntity.convertRugular().indexOf(":") > 0) {
                    String[] regulars = importEntity.convertRugular().split(",");
                    Map<String, String> regularMap = new HashMap();
                    for (String regular : regulars) {
                        String[] split = regular.split(":");
                        if (split.length == 2) {
                            regularMap.put(split[0], split[1].equals("null") ? null : split[1]);
                        } else {
                            if (split.length == 1) {
                                regularMap.put(split[0], "");
                            }
                        }
                    }
                    regularMapMap.put(importName, regularMap);
                }
            }
        }

        return regularMapMap;
    }
    
    /**
     * 将contentMapList按columnList的顺序转成StringBuilder,为入库准备
     * @param contentMapList
     * @param importNameList
     * @param regularMapMap
     * @return 
     */
    public static StringBuilder revertContentListToStringBuilder(List<Map<String, String>> contentMapList, List<String> importNameList, Map<String, Map<String, String>> regularMapMap) {
        StringBuilder contentSb = new StringBuilder();

        for (Map<String, String> contentMap : contentMapList) {
            
            if(contentMap == null || contentMap.isEmpty()) {
                continue;
            }
            
            StringBuilder sb = new StringBuilder();
            for (String importName : importNameList) {
                String content = contentMap.get(importName);
                if (content == null) {
                    sb.append("NULL").append("\t");
                    continue;
                }

                Map<String, String> regular = regularMapMap.get(importName);
                if (regular != null && !regular.isEmpty()) {
                    Set<String> keySet = regular.keySet();

                    for (String key : keySet) {
                        content = content.replace(key, regular.get(key));
                    }
                }

                sb.append(content).append("\t");
            }
            
            sb.deleteCharAt(sb.length() - 1);   //删除最后多余的'\t'
            
            contentSb.append(sb).append("%\r\n");   //加入这一行内容，并加入%各换行作为换行标志
        }
        
        if(contentSb.length() > 3) {
            contentSb.delete(contentSb.length() - 3, contentSb.length());   //删除末尾多余的"%\r\n" 这三个字符
        }
        
        return contentSb;
    }
    
    /**
     * 将数据内容集合contentsMap，转化成能直接入库的StringBuilder集合
     * @param contentsMap
     * @param packagePath
     * @return 
     */
    public static Map<String, StringBuilder> revertContentListMapToStringBuilder(Map<String, List<Map<String, String>>> contentsMap, String packagePath) {

        Map<String, StringBuilder> contentSbmap = new HashMap();
        Set<String> entityNameSet = contentsMap.keySet();
        for (String entityName : entityNameSet) {
            List<String> columnList = ImportUtil.columnListMap.get(entityName);
            List<String> importNameList = ImportUtil.importNameListMap.get(entityName);
            Map<String, Map<String, String>> regularMapMap = ImportUtil.regularMapMapMap.get(entityName);

            //检查是否空，如果空就获取它并加入集合
            if (columnList == null || importNameList == null || regularMapMap == null) {
                try {
                    Class<?> entityClass = Class.forName(packagePath + "." + entityName);
                    if (columnList == null) {
                        columnList = getColumnList(entityClass);
                        ImportUtil.columnListMap.put(entityName, columnList);
                    }

                    if (importNameList == null) {
                        importNameList = getImportNameList(entityClass);
                        ImportUtil.importNameListMap.put(entityName, importNameList);
                    }

                    if (regularMapMap == null) {
                        regularMapMap = getRegularMapMap(entityClass);
                        ImportUtil.regularMapMapMap.put(entityName, regularMapMap);
                    }

                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(ImportUtil.class.getName()).log(Level.SEVERE, ("cannot find entity:" + entityName));
                    continue;
                }
            }

            List<Map<String, String>> contentList = contentsMap.get(entityName);
            StringBuilder contentSb = ImportUtil.revertContentListToStringBuilder(contentList, importNameList, regularMapMap);
            contentSbmap.put(entityName, contentSb);

        }
        return contentSbmap;
    }
    
    /**
     * 通过实体名获取表名（有些实体有表名注解）
     * @param entityName
     * @return 
     */
    public static String getTableName(String entityName, long userId, String sheetNameOrFilePath) {
        
        String tableName = "";
            try {
                String fileName = sheetNameOrFilePath.split("\\u002E")[0];
                String[] fileSplit = fileName.split("_");
                String dateString = "";
                if (fileSplit.length > 1) {
                    dateString = fileSplit[1];
                }
                Class<?> entityClass = Class.forName(ImportUtil.entityPackage + "." + entityName);
                ImportTable annotation = entityClass.getAnnotation(ImportTable.class);

                if (annotation != null) {
                    ImportTable importTable = (ImportTable) annotation;
                    String tableName1 = importTable.tableName();
                    if (!tableName1.isEmpty()) {
                        tableName = tableName1;
                    } else {
                        tableName = entityName;
                    }
                    //判断表名是否需要加入用户ID
                    if(importTable.isUseUserId()) {
                        tableName += userId;
                    }
                    tableName = tableName.replace("#", dateString);
                } else {
                    tableName = entityName;
                }


            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ImportUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        

        return tableName;
    }
    
    public static String getLteTableName(String entityName, long userId) {
        String tableName = "";
        try {
            Class<?> entityClass = Class.forName(ImportUtil.entityPackage + "." + entityName);
            ImportTable annotation = entityClass.getAnnotation(ImportTable.class);

            if (annotation != null) {
                ImportTable importTable = (ImportTable) annotation;
                String tableName1 = importTable.tableName();
                if (!tableName1.isEmpty()) {
                    tableName = tableName1;
                } else {
                    tableName = entityName;
                }
                //判断表名是否需要加入用户ID
                if (importTable.isUseUserId()) {
                    tableName += userId;
                }
            } else {
                tableName = entityName;
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImportUtil.class.getName()).log(Level.SEVERE, null, ex);
        }


        return tableName;
    }
    
    public static List<String> getColumnList(String entityName) {
        List<String> columnList = ImportUtil.columnListMap.get(entityName);
        
        if(columnList == null) {
            try {
                Class<?> entityClass = Class.forName(ImportUtil.entityPackage + "." + entityName);
                columnList = ImportUtil.getColumnList(entityClass);
                ImportUtil.columnListMap.put(entityName, columnList);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ImportUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return columnList;
    }
    
}
