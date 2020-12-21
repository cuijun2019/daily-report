package com.etone.project.utils;

import com.etone.project.modules.dbimp.entity.Mro;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class ImportMrUtil {
    
    /**
     * 用于判断数据来源 ，默认是ZTE，表示是中兴数据，目前另一种数据来源是HUAWEI,表示华为数据
     */
    public String dataType;

    public static void main(String[] args) throws ParseException {
        FileInputStream fileInputStream = null;
        try {
//            fileInputStream = new FileInputStream(new File("E:/work/20130813/华为MR样例/TD-LTE_MRE_HUAWEI_OMC_711138_20130427140000.xml"));
            fileInputStream = new FileInputStream(new File("E:/work/20130813/test/TD-LTE_MRO_ZTE_OMCR1_172086_20130812231500.xml"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        Class<?> pojoClass = Mro.class;

        ImportMrUtil app = new ImportMrUtil();
        Map<String, List<Map<String, String>>> contentsMap = app.getContentsMap(fileInputStream, pojoClass.getSimpleName());
//        boolean result = app.insertIntoTable(contentsMap);
//        System.out.println("end,excute " + (result ? "success" : "false"));
        
        
//        app.insertIntoTable2(contentsMap, "com.mycompany.mr.entity", true, true);
        
        
        Map<String, List> revertToMapListEntity = ImportUtil.revertToMapListEntity(contentsMap, "com.mycompany.mr.entity");
        
//        ImportUtil.insertIntoTable3(revertToMapListEntity, Boolean.FALSE, Boolean.FALSE);

        if(true) {
            return;
        }
        
        ByteArrayOutputStream exportToxls = app.exportToxls(contentsMap, "com.mycompany.mr.entity");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("E:/work/20130813/test/mr.xls");
            fileOutputStream.write(exportToxls.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ByteArrayOutputStream exportToxls(Map<String, List<Map<String, String>>> contentsMap, String entityPackagePath) {
        Set<String> tableNameSet = contentsMap.keySet();

        HSSFWorkbook wb = new HSSFWorkbook();
        for (String tableName : tableNameSet) {
            try {
                List<Map<String, String>> contentMapList = contentsMap.get(tableName);

                if (tableName == null || tableName.trim().equals("")) {
                    tableName = "Mre";
                }
                Class<?> entity = Class.forName(entityPackagePath + "." + tableName);
                Field[] declaredFields = entity.getDeclaredFields();

                List<String> colunmList = new ArrayList();
                for (int i = 0; i < declaredFields.length; i++) {
                    colunmList.add(declaredFields[i].getName());
                }


                this.setSheetInfo2(wb, tableName, colunmList, contentMapList);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, ("entity class " + tableName + " not found"));
                return new ByteArrayOutputStream();
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            wb.write(bos);
        } catch (IOException ex) {
            Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bos;
    }

    private void setSheetInfo(HSSFWorkbook wb, String sheetName, List<List<String>> contentsList) {

        if (contentsList == null || contentsList.isEmpty()) {
            Logger.getLogger("木有数据，导出不了Excel");
            return;
        }
        int columnCount = contentsList.get(0).size();

        HSSFSheet sheet = wb.createSheet(sheetName);

        int xu = 1;
        for (int i = 0, r = 0; i < contentsList.size(); i++, r++) {

            if (r > 65535) {
                for (int x = 0; x < columnCount; x++) {
                    sheet.autoSizeColumn(x);    //自动根据内容调整列宽
                }

                sheet.createFreezePane(0, 1, 0, 1); //冻结第一行

                sheet = wb.createSheet(sheetName + "(续" + xu + ")");

                xu++;
                r = 0;
            }


            List<String> contents = contentsList.get(i);
            HSSFRow row = sheet.createRow(r);

            for (int j = 0; j < contents.size(); j++) {
                HSSFCell cell = row.createCell(j);

                cell.setCellValue(contents.get(j));
            }
        }

        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);    //自动根据内容调整列宽
        }

        sheet.createFreezePane(0, 1, 0, 1); //冻结第一行
    }

    private void setSheetInfo2(HSSFWorkbook wb, String sheetName, List<String> columnNames, List<Map<String, String>> contentsList) {

        if (contentsList == null || contentsList.isEmpty()) {
            Logger.getLogger("木有数据，导出不了Excel");
            return;
        }
        int columnCount = contentsList.get(0).size();

        HSSFSheet sheet = wb.createSheet(sheetName);

        HSSFRow firstRow = sheet.createRow(0);
        for (int i = 0; i < columnNames.size(); i++) {
            HSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(columnNames.get(i));
        }

        int xu = 1;
        for (int i = 0, r = 1; i < contentsList.size(); i++, r++) {

            if (r > 65535) {
                for (int x = 0; x < columnCount; x++) {
                    sheet.autoSizeColumn(x);    //自动根据内容调整列宽
                }

                sheet.createFreezePane(0, 1, 0, 1); //冻结第一行

                sheet = wb.createSheet(sheetName + "(续" + xu + ")");

                firstRow = sheet.createRow(0);
                for (int ii = 0; ii < columnNames.size(); ii++) {
                    HSSFCell cell = firstRow.createCell(ii);
                    cell.setCellValue(columnNames.get(ii));
                }

                xu++;
                r = 1;
            }


            Map<String, String> contents = contentsList.get(i);
            HSSFRow row = sheet.createRow(r);

            for (int j = 0; j < contents.size(); j++) {
                HSSFCell cell = row.createCell(j);
                String column = columnNames.get(j);
                boolean containsKey = contents.containsKey(column);

                if (!containsKey) {
                    Logger.getLogger(ImportMrUtil.class.getName()).log(Level.SEVERE, ("不存在KEY：" + column));
                }

                cell.setCellValue(contents.get(columnNames.get(j)));
            }
        }

        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);    //自动根据内容调整列宽
        }

        sheet.createFreezePane(0, 1, 0, 1); //冻结第一行
    }

    
    public Map<String, List<Map<String, String>>> getContentsMap(InputStream inputStream, String defaultTableName) {

        Document document = DocumentUtil.getDocument(inputStream);

        if (document == null) {
            return new MyMap();
        }

        Element root = document.getRootElement();
        Element fileHeaderElement = root.getChild("fileHeader");
        String startTime = fileHeaderElement.getAttributeValue("startTime");
        String endTime = fileHeaderElement.getAttributeValue("endTime");

        Element eNBElement = root.getChild("eNB");
        if(eNBElement == null) {
            return new MyMap();
        }
        
        //中兴是有个class节点的（现阶段只有中兴和华为两种数据），以这个节点来判断是哪种数据
        if(this.dataType == null) {
            Element classElement = eNBElement.getChild("class");
            if(classElement == null) {
                this.dataType = "HUAWEI";
            } else {
                this.dataType = "ZTE";
            }
                
        }
        
        
        
        String enbId = eNBElement.getAttributeValue("id");

        List<Element> measurements = DocumentUtil.getChildren(eNBElement, "measurement");
        
        Map<String, List<Map<String, String>>> contentMapMap = new HashMap();

        for (Element measurement : measurements) {
            String mrName = measurement.getAttributeValue("mrName");


            List<Map<String, String>> contentMapList = new ArrayList();

            List<String> columnList = this.getColumnList(measurement);

            List<Element> objectList = DocumentUtil.getChildren(measurement, "object");

            for (Element object : objectList) {
                List<List<String>> objectContents = this.getObjectContent(object, startTime, endTime, enbId);

                for (List<String> contentList : objectContents) {
                    Map<String, String> contentMap = ImportUtil.getContentMap(columnList, contentList);
                    
                    if(contentMap == null) {
                        continue;
                    }
                    
                    if(this.dataType.equals("HUAWEI")) {
                        if(mrName == null) {    //mrName用于判断是否为MRS，MRS的数据文件这列不为空
                            String mmeUeS1apId = contentMap.get("MmeUeS1apId");
                            
                            contentMap.put("mrId", enbId 
                                    + "-" + contentMap.get("id") 
                                    + ((mmeUeS1apId != null)? ("-" + mmeUeS1apId) : "")
                                    + "-" + contentMap.get("TimeStamp").replace("T", " "));
                         
                        }
                        String id = contentMap.get("id");
                        
                        if(id != null) {
                            String cellId = id.split(":")[0];
                            contentMap.put("cellId", cellId);
                        }
                        
                    } else {
                        if(mrName == null) {    //mrName用于判断是否为MRS，MRS的数据文件这列不为空
                            contentMap.put("mrId", enbId 
                                    + "-" + contentMap.get("id") 
                                    + "-" + contentMap.get("C_RNTI") 
                                    + "-" + contentMap.get("TimeStamp").replace("T", " "));
                         
                        }

                        contentMap.put("cellId", enbId 
                                + "-" + contentMap.get("id"));
                   }
                    
                    
                    contentMapList.add(contentMap);

                }

            }

            if (mrName != null) {
                mrName = mrName.replace("MR.", "");
            } else {
                mrName = defaultTableName;
            }

            contentMapMap.put(mrName, contentMapList);

        }
        return contentMapMap;
    }

    private List<String> getColumnList(Element measurement) {


        List<String> columnList = new ArrayList();
        Element smrElement = measurement.getChild("smr");
        if(smrElement == null) {
            return columnList;
        }
        
        String smr = smrElement.getValue().replace("MR.", "").replace(".", "_");   //替换MR.前缀及.
        columnList.addAll(Arrays.asList(smr.trim().split(" ")));

        //补充列名
        Element firstObject = measurement.getChild("object");
        if (firstObject == null) {
            return columnList;
        }

        Attribute timeStamp = firstObject.getAttribute("TimeStamp");
        if (timeStamp == null) {
            columnList.add(0, "endTime");
            columnList.add(0, "startTime");
            columnList.add(0, "cellId");
            columnList.add(0, "id");
        } else {
            List<Attribute> attributes1 = firstObject.getAttributes();
            for (Attribute attribute : attributes1) {

                if (attribute.getName().equals("id")) {
                    String[] c = {"id", "cellId", "earfcn", "SubFrameNbr", "PRBNbr"};
                    columnList.addAll(0, Arrays.asList(c));
                } else {
                    columnList.add(0, attribute.getName().replace("-", "_"));
                }

            }
        }

        return columnList;
    }

    private List<List<String>> getObjectContent(Element object, String startTime, String endTime, String enbId) {
        List<String> firstPartContent = new ArrayList();

        Attribute id = object.getAttribute("id");
        if (id == null || id.getValue() == null || id.getValue().trim().equals("")) {
            System.out.println("格式有误，木有ID");
            return new ArrayList();
        }

        Attribute timeStamp = object.getAttribute("TimeStamp");
        if (timeStamp == null) {
            firstPartContent.add(0, endTime);
            firstPartContent.add(0, startTime);
            firstPartContent.add(0, enbId + "-" + id.getValue().split(":")[0]);
            firstPartContent.add(0, id.getValue());

        } else {
            List<Attribute> attributeList = object.getAttributes();
            for (Attribute attribute : attributeList) {
                String name = attribute.getName();
                String value = attribute.getValue();
                if(value.equals("NIL")) {
                    value = "";
                }

                if (name.equals("id")) {
                    String[] someColumn = {null, null, null, null};
                    String[] values = {null, null, null, null};
                    if(value != null) {
                        values = value.split(":");
                    }
                    
                    System.arraycopy(values, 0, someColumn, 0, values.length);

                    for (int i = 3; i >= 0; i--) {
                        firstPartContent.add(0, someColumn[i]);
                    }
                }

                firstPartContent.add(0, value);
            }
        }


        List<List<String>> contentsList = new ArrayList();
        List<Element> vs = object.getChildren("v");
        for (Element element : vs) {
            String value = element.getValue();
            
            //去掉头尾空格先
            value = value.trim();
            //再去掉双空格
            while(value.indexOf("  ") > 0) {
                value = value.replace("  ", " ");
            }

            if (value != null && !value.trim().equals("")) {

                List<String> contents = new ArrayList();
                contents.addAll(firstPartContent);
                for (String v1 : value.split(" ")) {
                    if (v1.equals("NIL")) {
                        v1 = "";
                    }
                    contents.add(v1);
                }
                contentsList.add(contents);
            }
        }

        return contentsList;
    }
}
