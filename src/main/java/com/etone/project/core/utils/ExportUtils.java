package com.etone.project.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 导出工具类
 * 
 * @author chenfangle
 * @date 2011-5-15
 */
public class ExportUtils {
    
//    private static SystemEnv env = SessionContainer.getInstance().getEnv(OmcConstants.SYSTEM_CODE);

    /**
     * 获取时间字符串
     *
     * @param date
     * @return
     */
    public static String getLineDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        String formatString = "yyyyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        String dateString = "";
        try {
            dateString = sdf.format(date);
        } catch (Exception e) {
        }
        return dateString;
    }

    /**
     * 获取当前时间字符串
     *
     * @param date
     * @return
     */
    public static String getCurrentLineDate() {
        Date date = new Date();
        String formatString = "yyyyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        String dateString = "";
        try {
            dateString = sdf.format(date);
        } catch (Exception e) {
        }
        return dateString;
    }

    

    private static HSSFCellStyle getExportStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        cellStyle.setHidden(true);
        return cellStyle;
    }

    private static String checkSheetName(String sheetName) {
        if (sheetName != null) {
            sheetName = sheetName.replaceAll(":", "");
            sheetName = sheetName.replaceAll("：", "");
            sheetName = sheetName.replaceAll("/", "");
            sheetName = sheetName.replaceAll("\\\\", "");
            sheetName = sheetName.replaceAll("\\?", "");
            sheetName = sheetName.replaceAll("？", "");
            sheetName = sheetName.replaceAll("\\*", "");
            sheetName = sheetName.replaceAll("\\[", "");
            sheetName = sheetName.replaceAll("\\]", "");
        }
        if (sheetName.length() > 31) {
            return null;
        }
        return sheetName;
    }


    /**
     * 获得Excel表中数据转换成double型
     * @param cell
     * @return
     */
    public static double getCellForDouble(HSSFCell cell) {
        int cellType = cell.getCellType();
        if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue();
        } else {
            return new Double(cell.getStringCellValue());
        }
    }

    /**
     * 获得Excel表中数据转换成String型
     * @param cell
     * @return
     */
    public static String getCellForString(HSSFCell cell) {
        int cellType = cell.getCellType();
        if (cellType == HSSFCell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else {
            return cell.toString();
        }
    }

    public static String UniC(String gb2312Str) {  
        String unicoStr = "";  
        if (gb2312Str == null) {  
            gb2312Str = "";  
        }  
        try {  
            byte[] yte = gb2312Str.getBytes("GB2312");  
            unicoStr = new String(yte, "ISO8859_1");  
        } catch (Exception ex) {  
        }  
        return unicoStr;  
    }  
}
