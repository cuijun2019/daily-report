package com.etone.project.base.support.export.impromptuExport;

/**
 * User:chz
 * Date:13-8-20
 */
public class ExcelStyle {
    public static  int ColumnWidth=20;
    /*private  static WritableCellFormat headerFormat =null;
    public static WritableCellFormat getHeaderCellFormat() throws WriteException {
        if(headerFormat!=null) return  headerFormat;
        WritableFont headerFont = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
        WritableCellFormat format = new WritableCellFormat(headerFont);
        format.setBackground(Colour.GRAY_25);
        format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.PALETTE_BLACK);
        return  format;
    }

    private static WritableCellFormat contentFormat;

    public static WritableCellFormat getContentFormat() throws WriteException {
        if(contentFormat!=null)
            return contentFormat;
        WritableFont headerFont = new WritableFont(WritableFont.TIMES,WritableFont.DEFAULT_POINT_SIZE);
        contentFormat = new WritableCellFormat(headerFont);
        contentFormat.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.PALETTE_BLACK);
        return contentFormat;
    }  */
}
