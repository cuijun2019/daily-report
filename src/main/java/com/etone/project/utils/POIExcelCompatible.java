package com.etone.project.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class POIExcelCompatible {
    //兼容03和07Excel

    public static Object getWorkbook(InputStream inputStream, String fileName) {

        String[] split = fileName.split("\\u002E"); //u002E是.转义
        String suffix = split[split.length - 1];    //获取后缀

        Object wb;
        if (suffix.toUpperCase().equals("XLSX")) {
            wb = get07Workbook(inputStream);
        } else {
            wb = get03Workbook(inputStream);
        }

        return wb;
    }

    public static HSSFWorkbook get03Workbook(InputStream inputStream) {
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(inputStream);//建立新HSSFWorkbook对象
        } catch (IOException ex) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hssfWorkbook;
    }

    public static XSSFWorkbook get07Workbook(InputStream inputStream) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);//建立新HSSFWorkbook对象
        } catch (IOException ex) {
            Logger.getLogger(ImportFromExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return xssfWorkbook;
    }

    public static int getNumberOfSheets(Object wb) {
        int num = 0;

        if (wb instanceof HSSFWorkbook) {
            num = ((HSSFWorkbook) wb).getNumberOfSheets();
        } else {
            if (wb instanceof XSSFWorkbook) {
                num = ((XSSFWorkbook) wb).getNumberOfSheets();
            }
        }

        return num;
    }

    public static Object getSheetAt(Object wb, int sheetNum) {
        Object sheet = null;

        if (wb instanceof HSSFWorkbook) {
            sheet = ((HSSFWorkbook) wb).getSheetAt(sheetNum);
        } else {
            if (wb instanceof XSSFWorkbook) {
                sheet = ((XSSFWorkbook) wb).getSheetAt(sheetNum);
            }
        }

        return sheet;
    }

    public static String getSheetName(Object sheet) {
        String sheetName = null;

        if (sheet instanceof HSSFSheet) {
            return ((HSSFSheet) sheet).getSheetName();
        } else {
            if (sheet instanceof XSSFSheet) {
                return ((XSSFSheet) sheet).getSheetName();
            }
        }

        return sheetName;
    }

    public static int getLastRowNum(Object sheet) {
        int lastRowNum = 0;

        if (sheet instanceof HSSFSheet) {
            return ((HSSFSheet) sheet).getLastRowNum();
        } else {
            if (sheet instanceof XSSFSheet) {
                return ((XSSFSheet) sheet).getLastRowNum();
            }
        }

        return lastRowNum;
    }

    public static Object getRow(Object sheet, int rowNum) {
        Object row = null;

        if (sheet instanceof HSSFSheet) {
            row = ((HSSFSheet) sheet).getRow(rowNum);
        } else {
            if (sheet instanceof XSSFSheet) {
                row = ((XSSFSheet) sheet).getRow(rowNum);
            }
        }

        return row;
    }

    public static Object getCell(Object row, int cellNum) {
        Object cell = null;

        if (row instanceof HSSFRow) {
            cell = ((HSSFRow) row).getCell(cellNum);
        } else {
            if (row instanceof XSSFRow) {
                cell = ((XSSFRow) row).getCell(cellNum);
            }
        }

        return cell;
    }

    public static int getLastCellNum(Object row) {
        int lastCellNum = 0;

        if (row instanceof HSSFRow) {
            lastCellNum = ((HSSFRow) row).getLastCellNum();
        } else {
            if (row instanceof XSSFRow) {
                lastCellNum = ((XSSFRow) row).getLastCellNum();
            }
        }

        return lastCellNum;
    }

    public static String getCellForString(Object cell) {
        String result = null;

        if (cell instanceof HSSFCell) {
            return get03CellForString(cell);
        } else {
            if (cell instanceof XSSFCell) {
                return get07CellForString(cell);
            }
        }

        return result;
    }
    
    public static String get03CellForString(Object cell) {

        int cellType = ((HSSFCell) cell).getCellType();
        if (cellType == HSSFCell.CELL_TYPE_STRING) {
            return ((HSSFCell) cell).getStringCellValue();
        } else {

            if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                double numericCellValue = ((HSSFCell) cell).getNumericCellValue();

                BigDecimal value = new BigDecimal(numericCellValue);

                return value.toString();
            }
            return ((HSSFCell) cell).toString();
        }
    }
    
    public static String get07CellForString(Object cell) {
        int cellType = ((XSSFCell) cell).getCellType();
        if (cellType == XSSFCell.CELL_TYPE_STRING) {
            return ((XSSFCell) cell).getStringCellValue();
        } else {

            if(cellType == XSSFCell.CELL_TYPE_NUMERIC) {

                double numericCellValue = ((XSSFCell) cell).getNumericCellValue();

                BigDecimal value = new BigDecimal(numericCellValue);

                return value.toString();
            }
            return ((XSSFCell) cell).toString();
        }
    }
    
    public static Date getDateCellValue(Object cell) {

        if (cell instanceof HSSFCell) {
            return get03DateCellValue(cell);
            
        } else {
            return get07DateCellValue(cell);
        }
    }
    
    public static Date get03DateCellValue(Object cell) {
        Date result = null;

        int cellType = ((HSSFCell) cell).getCellType();

        if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
            if(HSSFDateUtil.isCellDateFormatted(((HSSFCell) cell))){
                Date dateCellValue = ((HSSFCell) cell).getDateCellValue();
                return dateCellValue;
            }
        }
        return result;
    }
    
    public static Date get07DateCellValue(Object cell) {
        Date result = null;

        if (cell instanceof XSSFCell) {
            int cellType = ((XSSFCell) cell).getCellType();

            if(cellType == XSSFCell.CELL_TYPE_NUMERIC) {

                Date dateCellValue = ((XSSFCell) cell).getDateCellValue();
                return dateCellValue;
            }

        }
        
        return result;
    }
}
