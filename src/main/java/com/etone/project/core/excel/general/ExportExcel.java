package com.etone.project.core.excel.general;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
//import org.hsqldb.lib.StringUtil;
/**
 * Excel导出工具类
 * 
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2014-01-15
 */
public class ExportExcel {
	/**
	 * 导出excel 导出的excel只有一个表格，支持复杂的表头
	 * 
	 * @param dataset
	 *            需要显示的数据集合 <br/>
	 *            在excel中的显示的数据样式，必须已转换成相应的格式，<br/>
	 *            如：一个date数据，想在excel中显示为2013-07-20，那么在组装List的时候，一定要求把date的格式转换成字符串的格式
	 *            。<br/>
	 *            在这个方法中只是负责显示出来传递过来的数据，不进行数据格式的转换。<br/>
	 * @param mergeHeader
	 *            excel表头合并的列集合：由行和列组成<br/>
	 *            其中的Map是由四个元素组成分别为first_row:合并起始行;last_row:合并结束行；first_column：合并起始列
	 *            ；last_column:合并结束列<br/>
	 *            表头合并时，是开始行表头的值覆盖结束行表头的值或者开始列表头的值覆盖结束列表头的值。<br/>
	 *            如：从第一行第一列（表头的值：第一行）到第三行第一列（表头的值：第三行）的表头进行合并，那么最后导出来的excel的表头的值为第一行
	 * @param headers
	 *            表格属性列名数组<br/>
	 *            是一个二维数组，这个数组一定要注意放入的顺序。因为excel表头的显示都是按照这个数组的顺序遍历生成的
	 * @param headerKey
	 *            对应数据库中的字段名称的数组<br/>
	 *            这个数组一定要与header这个二维数组的值要对应。<br/>
	 *            如：各个值与数据库中的字段相对应如下,组织名称--group_name,护士名称--name,如果header数组为{组织名称
	 *            ，护士名称}，那么headerKey这个数组的顺序一定为{group_name,name}<br/>
	 *            如果不是这样的顺序的话，那么excel中显示的值，会与表头的值对应不上出现数据错误
	 * @param title
	 *            标题 excel表格的名称
	 * @param out
	 *            输出流 导出放入的位置
	 */
	/*public void exportExcel(List<Map> dataset, List<Map<String, Integer>> mergeHeader, String[][] headers, String[] headerKey, String title, OutputStream out) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为8个字节
		sheet.setDefaultColumnWidth((short) 8);
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置边框
		setBorder(style, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
		// 设置excel表头居中显示，字体加粗
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		setFont(workbook, style, null, (short) 10, HSSFColor.GREY_80_PERCENT.index, true);

		// excel需要显示的单元格的样式
		HSSFCellStyle style1 = workbook.createCellStyle();
		setBorder(style1, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
		try {
			// 生成表头
			createExcelHeader(sheet, style, mergeHeader, headers);
			// excel赋值
			int headerRow = headers.length;
			setDataSet(sheet, style1, dataset, headerKey, headerRow);
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 创建多个sheet并返回workbook
	 * @param workbook
	 * @param dataset
	 * @param mergeHeader
	 * @param headers
	 * @param headerKey
	 * @param title

	public void exportExcel(HSSFWorkbook workbook,List<Map> dataset, List<Map<String, Integer>> mergeHeader, String[][] headers, String[] headerKey, String title) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为8个字节
		sheet.setDefaultColumnWidth((short) 20);
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置边框
		setBorder(style, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
		// 设置excel表头居中显示，字体加粗
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		setFont(workbook, style, null, (short) 10, HSSFColor.GREY_80_PERCENT.index, true);

		// excel需要显示的单元格的样式
		HSSFCellStyle style1 = workbook.createCellStyle();
		setBorder(style1, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
		// 生成表头
		createExcelHeader(sheet, style, mergeHeader, headers);
		// excel赋值
		int headerRow = headers.length;
		setDataSet(sheet, style1, dataset, headerKey, headerRow);
	}*/
	
	/**
	 * 创建一个sheet并返回workbook
	 * @param workbook
	 * @param dataset
	 * @param mergeHeader
	 * @param headers
	 * @param headerKey
	 * @param title

	public void exportOneExcel(HSSFWorkbook workbook,List<Map> dataset, List<Map<String, Integer>> mergeHeader, String[][] headers, String[] headerKey, String title,int headerRow) {
		// 生成一个表格
		HSSFSheet sheet = workbook.getSheet("详细");
		if(sheet == null){
			sheet = workbook.createSheet("详细");
		}
		// 设置表格默认列宽度为8个字节
		sheet.setDefaultColumnWidth((short) 20);
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置边框
		setBorder(style, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
		// 设置excel表头居中显示，字体加粗
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(HSSFColor.LIME.index);//前景颜色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充方式，前色填充
		setFont(workbook, style, null, (short) 10, HSSFColor.GREY_80_PERCENT.index, true);

		// excel需要显示的单元格的样式
		HSSFCellStyle style1 = workbook.createCellStyle();
		setBorder(style1, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
		// 生成表头
		createExcelHeaderOS(sheet, style, mergeHeader, headers,headerRow,title);
		// excel赋值
		setDataSetOS(sheet, style1, dataset, headerKey, headerRow+1);
	}
	
	public void createExcelHeaderOS(HSSFSheet sheet, HSSFCellStyle style, List<Map<String, Integer>> mergeHeader, String[][] headers,int headerRow,String title) {
		HSSFRow row_t = sheet.createRow(headerRow);
		HSSFCell cell_t = row_t.createCell(0);
		if (style != null)
			cell_t.setCellStyle(style);
		HSSFRichTextString text_t = new HSSFRichTextString(title);
		cell_t.setCellValue(text_t);
		for (short i = 0; i < headers.length; i++) {
			HSSFRow row = sheet.createRow(i+headerRow);
			for (short j = 0; j < headers[i].length; j++) {
				HSSFCell cell = row.createCell(j);
				if (style != null)
					cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headers[i][j]);
				cell.setCellValue(text);
			}
		}
	}  */

	public void setDataSetOS(HSSFSheet sheet, HSSFCellStyle style, List<Map> dataset, String[] headerKey, int headerRow) {
		for (int i = 0; i < dataset.size(); i++) {
			Map map = dataset.get(i);
			HSSFRow row = sheet.createRow(i + headerRow);
			for (int j = 0; j < headerKey.length; j++) {
				HSSFCell cell = row.createCell(j);
				if (style != null)
					cell.setCellStyle(style);
				String cellName = headerKey[j];
				String cellValue = map.get(cellName) == null ? "" : map.get(cellName).toString();
//				if (!StringUtil.isEmpty(cellValue))
					cell.setCellValue(cellValue);
			}
		}
	}

	/**
	 * 导出excel 导出的excel只有一个表格，支持复杂的表头
	 * 
	 * @param dataset需要显示的数据集合
	 * <br/>
	 *            在excel中的显示的数据样式，必须已转换成相应的格式，<br/>
	 *            如：一个date数据，想在excel中显示为2013-07-20，那么在组装List的时候，一定要求把date的格式转换成字符串的格式
	 *            。<br/>
	 *            在这个方法中只是负责显示出来传递过来的数据，不进行数据格式的转换。<br/>
	 * @param headerList
	 *            excel表头对象的集合<br/>
	 *            添加对象的时候，注意添加的顺序即先添加就显示在前面。因为excel表头的显示是直接遍历headerList<br/>
	 *            注意headerList中的对象ExcelHeaderColumnPojo中headerLabel不能为空
	 * @param out
	 *            输出流 导出放入的位置
	 * @param title
	 *            标题 excel表格的名称
	 * @param headerTotalRow
	 *            excel表头总共占的行数
	 * @param headerTotalColumn
	 *            excel表头总共占的列数

	public void exportExcel(List<Map> dataset, List<ExcelHeaderColumnPojo> headerList, OutputStream out, String title, int headerTotalRow, int headerTotalColumn) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为8个字节
		sheet.setDefaultColumnWidth((short) 8);
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置边框
		setBorder(style, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
		// 设置excel表头居中显示，字体加粗
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		setFont(workbook, style, null, (short) 10, HSSFColor.GREY_80_PERCENT.index, true);

		// excel需要显示的单元格的样式
		HSSFCellStyle style1 = workbook.createCellStyle();
		setBorder(style1, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
		List<Map<String, Integer>> mergeHeader = new ArrayList<Map<String, Integer>>();
		String[][] headers = new String[headerTotalRow][headerTotalColumn];
		String[] headerKeys = new String[headerTotalColumn];
		try {
			// 根据excel表头对象组装表头集合
//			getHeaderArray(headerList, mergeHeader, headers, headerKeys);
			// 生成表头
			createExcelHeader(sheet, style, mergeHeader, headers);
			// excel赋值
			int headerRow = headers.length;
			setDataSet(sheet, style1, dataset, headerKeys, headerRow);
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  */

	/**
	 * 创建excel表头
	 * 
	 * @param sheet
	 *            excel表单
	 * @param style
	 *            单元格样式
	 * @param mergeHeader
	 *            合并的列集合：由行和列组成
	 * @param headers
	 *            表格属性列名数组

	public void createExcelHeader(HSSFSheet sheet, HSSFCellStyle style, List<Map<String, Integer>> mergeHeader, String[][] headers) {
		for (short i = 0; i < headers.length; i++) {
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < headers[i].length; j++) {
				HSSFCell cell = row.createCell(j);
				if (style != null)
					cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headers[i][j]);
				cell.setCellValue(text);
			}
		}
		// excel表头合并
		mergeHeader(sheet, mergeHeader);
	} */

	/**
	 * excel赋值
	 * 
	 * @param sheet
	 *            excel表单
	 * @param style
	 *            单元格样式
	 * @param dataset
	 *            需要显示的数据集合
	 * @param headerKey
	 *            对应数据库中的字段名称的数组
	 * @param headerRow
	 *            excel表头所占了多少行
	 */
	public void setDataSet(HSSFSheet sheet, HSSFCellStyle style, List<Map> dataset, String[] headerKey, int headerRow) {
		for (int i = 0; i < dataset.size(); i++) {
			Map map = dataset.get(i);
			HSSFRow row = sheet.createRow(i + headerRow);
			for (int j = 0; j < headerKey.length; j++) {
				HSSFCell cell = row.createCell(j);
				if (style != null)
					cell.setCellStyle(style);
				String cellName = headerKey[j];
				String cellValue = map.get(cellName) == null ? "" : map.get(cellName).toString();
//				if (!StringUtil.isEmpty(cellValue))
					cell.setCellValue(cellValue);
			}
		}
	}

	/**
	 * excel表头合并
	 * 
	 * @param sheet
	 *            excel表单
	 * @param mergeHeader
	 *            合并的列集合
	 */
	public void mergeHeader(HSSFSheet sheet, List<Map<String, Integer>> mergeHeader) {
		if (mergeHeader == null || mergeHeader.size() == 0)
			return;
		for (Map map : mergeHeader) {
			int firstRow = (Integer) map.get("first_row");
			int lastRow = (Integer) map.get("last_row");
			int firstColumn = (Integer) map.get("first_column");
			int lastColumn = (Integer) map.get("last_column");
			sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn));
		}
	}

	/**
	 * 设置单元格的边框
	 * 
	 * @param style
	 *            单元格样式
	 * @param bottom
	 *            下边框
	 * @param left
	 *            左边框
	 * @param right
	 *            右边框
	 * @param top
	 *            上边框
	 */
	public void setBorder(HSSFCellStyle style, short bottom, short left, short right, short top) {
		style.setBorderBottom(bottom);
		style.setBorderLeft(left);
		style.setBorderRight(right);
		style.setBorderTop(top);
	}

	/**
	 * 设置单元格的背景颜色
	 * 
	 * @param style
	 *            单元格样式
	 * @param backgroundColor
	 *            背景颜色
	 */
	public void setBackGroudColor(HSSFCellStyle style, short backgroundColor) {
		style.setFillForegroundColor(backgroundColor);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	}

	/**
	 * 设置单元格的字体
	 * 
	 * @param workbook
	 *            excel 工作薄
	 * @param style
	 *            单元格样式
	 * @param fontName
	 *            字体样式
	 * @param fontSize
	 *            字体大小
	 * @param fontColor
	 *            字体颜色
	 * @param fontWeight
	 *            是否加粗 true：是，false：正常
	 */
	public void setFont(HSSFWorkbook workbook, HSSFCellStyle style, String fontName, short fontSize, short fontColor, boolean fontWeight) {
		HSSFFont font = workbook.createFont();
//		if (!StringUtil.isEmpty(fontName))
			font.setFontName(fontName);
		font.setFontHeightInPoints(fontSize);// 设置字体大小
		font.setColor(fontColor);// 设置字体颜色
		if (fontWeight)
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 加粗
		style.setFont(font);// 选择需要用到的字体格式
	}

	/**
	 * 组装excel表头合并的集合
	 * 
	 * @param mergeArray
	 *            需要合并的excel表头数组 二维数组并且长度必须等于四。如int[1][4]，int[5][4]
	 * @return list
	 */
	public List<Map<String, Integer>> addMergeList(int[][] mergeArray) {
		List<Map<String, Integer>> mergeHeader = new ArrayList<Map<String, Integer>>();
		for (int i = 0; i < mergeArray.length; i++) {
			Map<String, Integer> mergeMap = new HashMap<String, Integer>();
			mergeMap.put("first_row", mergeArray[i][0]);
			mergeMap.put("last_row", mergeArray[i][1]);
			mergeMap.put("first_column", mergeArray[i][2]);
			mergeMap.put("last_column", mergeArray[i][3]);
			mergeHeader.add(mergeMap);
		}
		return mergeHeader;
	}

	/**
	 * 根据excel表头对象组装表头集合
	 * 
	 * @param headerList
	 *            excel表头对象的集合<br/>
	 *            添加对象的时候，注意添加的顺序即先添加就显示在前面。因为excel表头的显示是直接遍历headerList<br/>
	 *            注意headerList中的对象ExcelHeaderColumnPojo中headerLabel不能为空
	 * @param mergeHeader
	 *            excel表头合并的列集合 即创建一个空的集合
	 * @param headers
	 *            表格属性列名数组 -- 创建一个空的二位数组
	 * @param headerKeys
	 *            对应数据库中的字段名称的数组 -- 创建一个空的数组
	 * @throws Exception
	 */
	/*public void getHeaderArray(List<ExcelHeaderColumnPojo> headerList, List<Map<String, Integer>> mergeHeader, String[][] headers, String[] headerKeys) throws Exception {
		int curRowNum = -1, curIndex = 0;
		String mergerNum = "";
		int headerSize = headerList.size();
		int keysLength = headerKeys.length;
		for (int i = 0; i < headerSize; i++) {
			ExcelHeaderColumnPojo header = headerList.get(i);
			String headerLabel = header.getHeaderLabel();
			if (StringUtil.isEmpty(headerLabel)) {
				throw new Exception("excel中的表头单元格名称不能有空的字段");
			}
			String headerKey = header.getHeaderKey();
			int rowNum = header.getRowNum();
			// 获取当前列的序列号
			if (curRowNum == rowNum) {
				curIndex = curIndex + 1;
			} else {
				curIndex = 0;
				curRowNum = rowNum;
			}
			// 判断当前单元格是否已合并
			if (!StringUtil.isEmpty(mergerNum) && mergerNum.indexOf(rowNum + "-" + curIndex) != -1) {
				headers[rowNum][curIndex] = "";
				for (int j = 0; j < 100000; j++) {
					curIndex = curIndex + 1;
					if (curIndex == keysLength)
						return;
					if (mergerNum.indexOf(rowNum + "-" + curIndex) == -1) {
						break;
					}
					headers[rowNum][curIndex] = "";
				}
			}
			headers[rowNum][curIndex] = headerLabel;
			if (!StringUtil.isEmpty(headerKey))
				headerKeys[curIndex] = headerKey;
			// 组装excel表头合并的集合
			int occupyColumnNum = header.getOccupyColumnNum();
			int occupyRowNum = header.getOccupyRowNum();
			mergerNum = addMergeList(mergeHeader, mergerNum, occupyColumnNum, occupyRowNum, curRowNum, curIndex);
		}
	}*/

	/***
	 * 组装excel表头合并的集合
	 * 
	 * @param mergeHeader
	 *            excel表头合并的集合
	 * @param mergerNum
	 *            已合并的行和列的字符串
	 * @param occupyColumnNum
	 *            当前单元格占用的列
	 * @param occupyRowNum
	 *            当前单元格占用的行
	 * @param curRowNum
	 *            当前单元格行序列号
	 * @param curIndex
	 *            当前单元格列序列号
	 * 
	 * @return string
	 */
	private String addMergeList(List<Map<String, Integer>> mergeHeader, String mergerNum, int occupyColumnNum, int occupyRowNum, int curRowNum, int curIndex) {
		if (occupyRowNum == 1 && occupyColumnNum == 1)
			return mergerNum;
		// 组装excel表头合并的集合
		Map<String, Integer> mergeMap = new HashMap<String, Integer>();
		int lastRowNum = curRowNum + occupyRowNum - 1;
		int lastColumnNum = curIndex + occupyColumnNum - 1;
		mergeMap.put("first_row", curRowNum);
		mergeMap.put("last_row", lastRowNum);
		mergeMap.put("first_column", curIndex);
		mergeMap.put("last_column", lastColumnNum);
		mergeHeader.add(mergeMap);

		// 获得合并的列和行
		for (int i = curRowNum; i < lastRowNum + 1; i++) {
			for (int j = curIndex; j < lastColumnNum + 1; j++) {
				if (curRowNum != i || curIndex != j)
					mergerNum = mergerNum + "," + i + "-" + j;
			}
		}
		return mergerNum;
	}
}
