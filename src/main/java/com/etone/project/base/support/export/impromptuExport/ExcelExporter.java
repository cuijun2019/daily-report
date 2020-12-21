package com.etone.project.base.support.export.impromptuExport;

//import jxl.Workbook;
//import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * User:chz
 * Date:13-8-20
 */
public class ExcelExporter {

    /**
     * 生成excel文件
     * @param fileName 文件名
     * @param data     hashmap结构的数据
     * @param columns  列信息<对应hashmap的属性名称，导出excel的列名>
     * @throws IOException
     */
    /*public static void export(String fileName, List<Map> data, LinkedHashMap columns) throws IOException, WriteException {
        WritableWorkbook book = Workbook.createWorkbook(new File(fileName));
        WritableSheet sheet = book.createSheet("Sheet1", 0);

        try{
            //创建列头
            Iterator iterator = columns.keySet().iterator();
            int c = 0;
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String val = columns.get(key).toString();
                Label label = new Label(c, 0, val, ExcelStyle.getHeaderCellFormat());
                sheet.addCell(label);
                sheet.setColumnView(c,ExcelStyle.ColumnWidth);
                c++;
            }
            //导入数据
            int row = 1;
            if(data!=null){
                for (Map map : data) {
                    Iterator it = columns.keySet().iterator();
                    int column = 0;
                    while (it.hasNext()) {
                        Object p = it.next();
                        Object value = map.get(p);
                        Label label = new Label(column, row, value==null?"":value.toString(), ExcelStyle.getContentFormat());
                        sheet.addCell(label);
                        column++;
                    }
                    row++;
                }
            }
        }
        finally {
            book.write();
            book.close();
        }
    }*/
}
