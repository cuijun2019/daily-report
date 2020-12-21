package com.etone.project.utils;

import com.etone.project.core.utils.DateUtil;
import com.etone.project.modules.dbimp.entity.Mre;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class ImportFromCsvUtil {

    private Map<String, String> entityMap;
    private String packagePath = Mre.class.getPackage().getName();
    private int beginrow;
    private int headerrow;
    private int currentrow; //当前正在读取的行号
    private List<String> columnList;    //导入文件中的表头
    BufferedReader br;  //读取CSV的流
    Map<String, Boolean> isNotNullMap; //记录列是否非空
    Map<String, List> valueToEntityMethodMap; //list内容1:SetValueToEntityCsv工具类的set方法 2:类实体的Set方法 3:实体类 4:内容替换规则
    private int pageSize = 5000;    //每次获取的实体集合最大数
    private String sheetName;
    private Timestamp fileDate;
    private List<Method> setDateMethodList;

    /**
     * 初始化设置导入信息：表头行，数据开始行，实体路径，设置实体值的方法集合
     *
     * @param in
     * @param sheetNameOrFilePath
     */
    public ImportFromCsvUtil(InputStream in, String sheetNameOrFilePath) {
        this.init();
        this.sheetName = this.getEntityMapNameFromFilePath(sheetNameOrFilePath);
        String fileName = sheetNameOrFilePath.split("\\u002E")[0];
        String[] fileSplit = fileName.split("_");
        
        if(fileSplit.length > 1) {
            String dateString = fileSplit[1];
            Date date = DateUtil.parseStringToDate(dateString);
            if (date != null) {
                this.fileDate = new Timestamp(date.getTime());
            }
        }
        
        entityMap = DataTypeUtil.getEntityByName(this.sheetName);

        if(entityMap == null) {
            return;
        }
        
        if (entityMap.get("headerrow") == null || Integer.parseInt(entityMap.get("headerrow")) < 0) {
            entityMap.put("headerrow", "0");
        }
        if (entityMap.get("beginrow") == null || Integer.parseInt(entityMap.get("beginrow")) < 0) {
            entityMap.put("beginrow", "1");
        }

        headerrow = Integer.parseInt(entityMap.get("headerrow"));
        beginrow = Integer.parseInt(entityMap.get("beginrow"));

        String entityName = this.entityMap.get("entityName");
        this.packagePath = Mre.class.getPackage().getName();
        this.valueToEntityMethodMap = this.getSetValueToEntityMethodMap(entityName);
        this.isNotNullMap = this.getIsNotNullMap(entityName);
        
        columnList = new ArrayList();
        
        this.setImportInputStream(in);
    }
    
    private void init() {
        this.setDateMethodList = new ArrayList();
        
    }
    
    private Map<String, Boolean> getIsNotNullMap(String entityName) {
        Map<String, Boolean> notNullMap = new HashMap();
        
        Class<?> entityClass;
        try {
            entityClass = Class.forName(packagePath + "." + entityName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, ("cannot find class" + packagePath + "." + entityName));
            return notNullMap;
        }
        Field[] fields = entityClass.getDeclaredFields();
        
        for (int j = 0; j < fields.length; j++) {
            Field f = fields[j];
            
            ImportEntity importEntity = f.getAnnotation(ImportEntity.class);
            boolean notNull = importEntity.notNull();
            
            if(notNull) {
                notNullMap.put(f.getName(), notNull);
            }
            
        }
        
        return notNullMap;
    }

    /**
     * 设置输入流，并提取出文档的表头
     *
     * @param in
     */
    private void setImportInputStream(InputStream in) {
        try {
            this.br = new BufferedReader(new InputStreamReader(in, "gbk"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ImportFromCsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String readLine;
            this.currentrow = 0;
            //先提取表头
            for (; (readLine = br.readLine()) != null; currentrow++) {
                if (currentrow == this.headerrow) {
                    //设置表头

                    columnList = this.splitString(readLine, ",");   //提取了表头
                    currentrow++;
                    break;
                }
            }

            //转到内容行位置
            while (this.currentrow < this.beginrow) {
                String readLine1 = br.readLine();
                this.currentrow++;
            }

        } catch (IOException ex) {
            Logger.getLogger(ImportFromCsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List getSomeEntityList() {
        
        if(this.entityMap == null) {
            return null;
        }
        
        List dist = new ArrayList();

        if (this.valueToEntityMethodMap == null || this.valueToEntityMethodMap.isEmpty()) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE,
                    ("setValueToEntityMethodMap is empty, sheetName:" + this.entityMap.get("name")));
            return dist;
        }

        Class entityClass = (Class) this.valueToEntityMethodMap.values().iterator().next().get(2);  //集合里第三个就是实体类型
        try {

            for (int count = 0; dist.size() < this.pageSize; this.currentrow++, count++) {

                String content;
                if ((content = this.br.readLine()) == null) {
                    return (dist.size() > 0) ? dist : null;
                }

                Object entity;
                try {
                    entity = entityClass.newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, ("newInstance " + entityClass.getSimpleName() + "fail"));
                    break;
                }
                dist.add(entity);

                SetValueToEntity setValueToEntity = new SetValueToEntityCsv();

                List<String> contentList = this.splitString(content, ",");

                for (int j = 0; j < columnList.size() && j < contentList.size(); j++) {
                    String columnName = columnList.get(j);

                    try {
                        Object cell = contentList.get(j);
                        if (cell == null) {
                            continue;
                        }

                        List paramList = this.valueToEntityMethodMap.get(columnName);

                        if (paramList == null) {
                            continue;
                        }

                        Method setValueToEntityMethod = (Method) paramList.get(0);

                        setValueToEntityMethod.invoke(setValueToEntity, (Method) paramList.get(1), cell, entity, paramList.get(3));
                        //setInt(Method method, Object cell, Object entity, Map<String, String> regularMap);

                    } catch (Throwable exception) {
                        Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, 
                                exception.getMessage() + " columnName:" + columnName + "entity:" + entity.toString()
                                , exception);
                    }
                    
                    this.setTimeToEntityFromFileName(entity);   //将从文件名中获取的时间设置到实体类

                }
                
                //检查是否违犯非空约束
                if(this.chenNullCheck(entity)) {
                    dist.remove(entity);    //违犯非空约束就把它从集合去掉
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ImportFromCsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dist;
    }
    
    /**
     * 检查是否违犯实体配置的非空约束，违犯返回true,符合返回false
     * @param entity
     * @return 
     */
    private boolean chenNullCheck(Object entity) {
        
        for(String fieldName : this.isNotNullMap.keySet()) {
            try {
                Class<? extends Object> aClass = entity.getClass();
                Method getMethod = aClass.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), (Class<?>[]) null);
                Object value = getMethod.invoke(entity);
                if(value == null || value.toString().trim().isEmpty()) {
                    return true;
                }
            } catch (Exception ex) {
                Logger.getLogger(ImportFromCsvUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
    
    private void setTimeToEntityFromFileName(Object entity) {
        
        for(Method setMethod : this.setDateMethodList) {
            try {
                setMethod.invoke(entity, this.fileDate);
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, setMethod.getName() + " ERROR,fileDate:" + fileDate 
                        + ", msg:" + ex.getMessage());
            }
        }
        
    }

    /**
     * split String by regex and then check the result and merge some if they
     * are in the "" in initial String like this:"ljldf,sdl",kl result:
     * ljldf,sdl kl
     *
     * @param str the String what is to split
     * @param regex thie regex what is split by
     * @return
     */
    public List<String> splitString(String str, String regex) {

        String[] split = str.split(regex);

        List<String> contentList = new ArrayList();

        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            boolean matches = s.matches("^\".*$");
            if (matches) {
                s = s.substring(1);
                while (++i < split.length) {
                    String s1 = ',' + split[i];

                    s += s1;
                    if (s1.endsWith("\"")) {
                        s = s.substring(0, s.length() - 1);
                        break;
                    }

                    i++;
                }
            }
            contentList.add(s);
        }

        return contentList;
    }

    /**
     * 构建出SetValueToEntity集合以及它们除参数cell的部分参数： setString(Method method, Object
     * cell, Object entity, Map<String, String> regularMap); 其中improtName为KEY
     *
     * @param entityName
     * @return
     */
    private Map<String, List> getSetValueToEntityMethodMap(String entityName) {

        Map<String, List> setValueToEntityMethodMap = new HashMap();

        Class<?> entityClass = null;
        try {
            entityClass = Class.forName(packagePath + "." + entityName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, ("cannot find class" + packagePath + "." + entityName));
            return setValueToEntityMethodMap;
        }

        Class setValueToEntity = SetValueToEntityCsv.class;

        Field[] filed = entityClass.getDeclaredFields();

        for (int j = 0; j < filed.length; j++) {
            Field f = filed[j];
            ImportEntity importEntity = f.getAnnotation(ImportEntity.class);
            if (importEntity != null) {
                String fieldname = f.getName();
                String setMethodName = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
                Method setMethod = null;

                try {
                    setMethod = entityClass.getMethod(setMethodName, new Class[]{f.getType()});
                    if(importEntity.fromDataFileName()) {
                        this.setDateMethodList.add(setMethod);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
                }

                String importName = ImportUtil.getImportName(f);

                Map<String, String> regularMap = new HashMap();
                //get regularMap
                if (importEntity.convertRugular().indexOf(":") > 0) {
                    String[] regulars = importEntity.convertRugular().split(",");
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

                }

                Type type = f.getGenericType();
                String xclass = type.toString();

                Method setValueToEntityMethod = null;
                //get setValueToEntityMethod
                try {
                    String[] split = xclass.split("\\u002E");
                    String fieldType = split[split.length - 1];
                    setValueToEntityMethod = setValueToEntity.getMethod("set" + fieldType, new Class[]{Method.class, Object.class, Object.class, Map.class});
                } catch (Exception e) {
                    Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, "getSetValueToEntity Method Wrong", e);
                }

                //按顺序加入List
                List setValueParamList = new ArrayList();
                setValueParamList.add(setValueToEntityMethod);  //SetValueToEntityCsv工具类的set方法
                setValueParamList.add(setMethod);   //类实体的Set方法
                setValueParamList.add(entityClass); //实体类
                setValueParamList.add(regularMap);  //内容替换规则
                setValueToEntityMethodMap.put(importName, setValueParamList);

            }
        }

        return setValueToEntityMethodMap;
    }
    
    /**
     * 通过文件路径（包括文件名）获取到配置文件中对应类型的name值：
     * <entity>
        <name>LTE 服务小区-天粒度</name>
        <value>5</value>
        <entityName>ServerCellDay</entityName>
        <filetype>xls</filetype>
        <beginrow>6</beginrow>
        <headerrow>5</headerrow>
        <templatefile>templatefile/Mrs.xml</templatefile>
    </entity>
     * @param filePath
     * @return 
     */
    private String getEntityMapNameFromFilePath(String filePath) {
        List<String> dataTypeList = DataTypeUtil.getAllDataType();
        String dataTypeName = null;
        for (String typeName : dataTypeList) {
            if (filePath.toUpperCase().indexOf(typeName.toUpperCase()) >= 0) {
                dataTypeName = typeName;
                break;
            }
        }
        return dataTypeName;
    }

    public static void main(String[] args) throws Exception {

        long begin = System.currentTimeMillis();
//        FileInputStream fis = new FileInputStream("E:/work/20130813/流量均衡相关/导入数据模板/GSM 工程参数模板new.xlsx");
//        XLSX2CSV xlsx2csv = new XLSX2CSV(fis);
//        Map<String, InputStream> csvMap;
//        while ((csvMap = xlsx2csv.process()) != null) {
//            Set<String> sheetNameSet = csvMap.keySet();
//            for (String sheetName : sheetNameSet) {
//                InputStream in = csvMap.get(sheetName);
//                ImportFromCsvUtil importFromCsvUtil = new ImportFromCsvUtil(in, sheetName);
//
//                int count = 0;
//                List someEntityList;
//                while ((someEntityList = importFromCsvUtil.getSomeEntityList()) != null) {
//                    count += someEntityList.size();
//                    for (Object entity : someEntityList) {
//                        System.out.println(entity.toString());
//                    }
//                }
//
//                System.out.println("***************************************\n总数：" + count);
//            }
//
//        }
        
        test();
        
        long end = System.currentTimeMillis();

        System.out.println("转化消耗时间：" + (double) (end - begin) / 1000 + "s");

    }
    
    public static void test() {
        String path = "f:/GSM 工程参数.csv1";
        String[] split = path.split("/");
        String sheetName = split[split.length - 1].split("\\u002E")[0];
        System.out.println(sheetName);
        try {
            ImportFromCsvUtil importFromCsvUtil = new ImportFromCsvUtil(new FileInputStream(path), sheetName);
            
            List someEntityList = importFromCsvUtil.getSomeEntityList();
            
            if(someEntityList == null) {
                return;
            }
            
            for(Object str  : someEntityList) {
                System.out.println(str.toString());
            }
        } catch (Exception ex) {
            Logger.getLogger(ImportFromCsvUtil.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
