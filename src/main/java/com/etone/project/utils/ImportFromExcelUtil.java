package com.etone.project.utils;

import com.etone.project.modules.dbimp.entity.Mre;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class ImportFromExcelUtil {

    private static final Logger logger = Logger.getLogger(ImportFromExcelUtil.class.getName());
    private Integer currentSheetNum = 0;
    private Integer sheetCount = null;
    private Integer currentRowNum = null;
    private Object workBook = null;
    private String excelType = "XLSX";
    private List<String> columnList = null;
    private Map<String, List> valueToEntityMethodMap = null;
    private String packagePath = Mre.class.getPackage().getName();
    private int pageSize = 5000;    //一次最多取5000条

    public ImportFromExcelUtil() {
    }

    public ImportFromExcelUtil(Object wb) {
        if (wb != null) {
            this.workBook = wb;
            int numberOfSheets = POIExcelCompatible.getNumberOfSheets(wb);
            this.sheetCount = numberOfSheets;
        }
    }

    public void setWorkbood(Object wb) {
        if (wb != null) {
            this.workBook = wb;
            int numberOfSheets = POIExcelCompatible.getNumberOfSheets(wb);
            this.sheetCount = numberOfSheets;
        }
    }

    //设置Excel类型，是03：XLS还是07:XLSX
    public void setExcelType(String excelType) {
        this.excelType = excelType;
    }

    /**
     * 这个sheet读完了，可以跳到下一个Sheet去了,同时当前行回到首行，重置表头用户方法集合
     */
    private void reSet() {

        this.currentSheetNum++;
        this.currentRowNum = 0;
        if(this.columnList != null) {
            this.columnList.clear();
        }
        
        this.columnList = null;
        if(this.valueToEntityMethodMap != null) {
            this.valueToEntityMethodMap.clear();
        }
        
        if(this.valueToEntityMethodMap != null) {
            this.valueToEntityMethodMap = null;
        }
        
    }

    /**
     * 由表头序号获取表头
     * @param sheet
     * @param headerRowNum
     * @return 
     */
    public List<String> getColumnList(Object sheet, Integer headerRowNum) {
        List<String> columnsList = new ArrayList();

        Object headerRow = POIExcelCompatible.getRow(sheet, headerRowNum);

        for (int j = 0; j < POIExcelCompatible.getLastCellNum(headerRow); j++) {
            String content = "";
            Object cell = POIExcelCompatible.getCell(headerRow, j);
            if (cell != null) {
                content = POIExcelCompatible.getCellForString(cell).replace(".0", "");
            }
            columnsList.add(content);
        }

        return columnsList;
    }

    /**
     * 最多获取一个sheet中pageSize条记录，余下记录下次再取
     * 1.根据sheetName读取配置文件，找出对应的实体
     * 2.获取表头
     * 3.获取将Cxcel的Cell转化成entity数据的方法集合Map
     * 4.获取部分Sheet数据
     * @return 
     */
    public List getEntityList() {

        //这是文档读取结束的标志，同时只有文档读取结束返回NULL
        if (currentSheetNum >= this.sheetCount) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, "currentSheetNum >= this.sheetCount doc is end ");
            return null;
        }

        //当然，文档为NULL，也可以结束了
        if (this.workBook == null) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, "this.workBook == null end");
            return null;
        }

        Object sheet = POIExcelCompatible.getSheetAt(workBook, currentSheetNum);

        String sheetName = POIExcelCompatible.getSheetName(sheet);

        Map<String, String> entityMap = DataTypeUtil.getEntityByName(sheetName);    //由sheetName获取对应实体信息

        if (entityMap == null) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, ("has not content to " + sheetName));
            this.reSet();
            return new ArrayList();
        }

        int headerRow, beginRow;

        try {
            headerRow = Integer.parseInt(entityMap.get("headerrow"));
            beginRow = Integer.parseInt(entityMap.get("beginrow"));
        } catch (Exception exception) { //当配置文件没有配置，缺省这两个值时
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, ("dataType.xml config has not complete"
                    + "check " + sheetName + " 's 'headerrow' and 'beginrow'"));
            headerRow = 0;
            beginRow = 1;
        }
        
        //如果currentRowNum < beginRow,即还未读取过sheet,获取当前值
        if (this.currentRowNum == null) {
            this.currentRowNum = beginRow;
        } else {
            beginRow = (beginRow > this.currentRowNum) ? beginRow : currentRowNum;
        }


        String entityName = entityMap.get("entityName");
        if (entityName == null || entityName.equals("")) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, (sheetName + ":" + entityName + " == null"));
            return new ArrayList();
        }

        //get fieldSetMap and fieldSetConvertMap END!!
        if (this.columnList == null) {
            columnList = getColumnList(sheet, headerRow);
        }

        //获取转化方法集合
        if (this.valueToEntityMethodMap == null) {
            valueToEntityMethodMap = this.getSetValueToEntityMethodMap(entityName);
        }

        long beginTime = System.currentTimeMillis();
        List entityList = this.getSomeEntityList(sheet, beginRow, this.columnList, this.valueToEntityMethodMap);//获取数据
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "getSomeEntityList cost {0}ms", (endTime - beginTime));

        return entityList;
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

        Class setValueToEntity;

        if (this.excelType.equals("XLSX")) {
            setValueToEntity = SetValueToEntity07.class;
        } else {
            setValueToEntity = SetValueToEntity03.class;
        }

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

                //setBoolean(Method method, Object cell, Object entity, Map<String, String> regularMap);
                //按顺序加入List
                List setValueParamList = new ArrayList();
                setValueParamList.add(setValueToEntityMethod);
                setValueParamList.add(setMethod);
                setValueParamList.add(entityClass);
                setValueParamList.add(regularMap);
                setValueToEntityMethodMap.put(importName, setValueParamList);

            }
        }

        return setValueToEntityMethodMap;
    }

    //获取sheet的数据
    private List<?> getSomeEntityList(Object sheet, int beginRow, List<String> columnList, Map<String, List> setValueToEntityMethodMap) {
        List dist = new ArrayList();

        if (sheet == null) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, "sheet == null, what's wrong? ");
            this.currentSheetNum++;
            this.currentRowNum = 0;
            this.columnList.clear();
            this.columnList = null;
            return dist;
        }

        if (setValueToEntityMethodMap == null || setValueToEntityMethodMap.isEmpty()) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE,
                    ("setValueToEntityMethodMap is empty, sheetName:" + POIExcelCompatible.getSheetName(sheet)));
            return dist;
        }

        Class entityClass = (Class) setValueToEntityMethodMap.values().iterator().next().get(2);  //集合里第三个就是实体类型

        for (int i = beginRow; i <= POIExcelCompatible.getLastRowNum(sheet); i++) {

            if (dist.size() == this.pageSize) {
                this.currentRowNum = i;
                return dist;
            }

            Object row = POIExcelCompatible.getRow(sheet, i);

            if (row == null) {
                continue;
            }
            Object entity;
            try {
                entity = entityClass.newInstance();
            } catch (Exception ex) {
                Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, ("newInstance " + entityClass.getSimpleName() + "fail"));
                break;
            }
            dist.add(entity);

            SetValueToEntity setValueToEntity;
            if (this.excelType.equals("XLSX")) {
                setValueToEntity = new SetValueToEntity07();
            } else {
                setValueToEntity = new SetValueToEntity03();
            }

            for (int j = 0; j < columnList.size(); j++) {
                String columnName = columnList.get(j);

                try {
                    Object cell = POIExcelCompatible.getCell(row, j);
                    if (cell == null) {
                        continue;
                    }

                    List paramList = setValueToEntityMethodMap.get(columnName);

                    if (paramList == null) {
                        continue;
                    }

                    Method setValueToEntityMethod = (Method) paramList.get(0);

                    setValueToEntityMethod.invoke(setValueToEntity, (Method) paramList.get(1), cell, entity, paramList.get(3));
                    //setInt(Method method, Object cell, Object entity, Map<String, String> regularMap);

                } catch (Exception exception) {
                    Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, exception.getMessage(), exception);
                }

            }

        }

        //这个sheet读完了，可以跳到下一个Sheet去了,同时当前行回到首行
        this.reSet();

        return dist;
    }

    public static void main(String[] args) throws Exception {
        logger.info("start ...");
        String filePath = "E:/work/20130813/广州告警数据-20130912.xlsx";
        FileInputStream fileInputStream;
        fileInputStream = new FileInputStream(filePath);

        long beginTime = System.currentTimeMillis();
        String excelType;
        Object workbook;
        String[] split = filePath.split("\\u002E"); //u002E是.转义
        String suffix = split[split.length - 1];    //获取后缀
        if (suffix.toUpperCase().equals("XLSX")) {
            workbook = POIExcelCompatible.get07Workbook(fileInputStream);
            excelType = "XLSX";
        } else {
            workbook = POIExcelCompatible.get03Workbook(fileInputStream);
            excelType = "XLS";
        }

        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "open xls file cost{0}ms", (endTime - beginTime));

        ImportFromExcelUtil importFromExcelUtil = new ImportFromExcelUtil(workbook);
        importFromExcelUtil.setExcelType(excelType);

        List entityList;
        entityList = importFromExcelUtil.getEntityList();
        logger.log(Level.INFO, "entityList size : {0}", entityList.size());
    }
}
