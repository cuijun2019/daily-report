package com.etone.project.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 跟servlet的响应对象对应的一些工具类
 * 
 */
public class ResponseUtils {
	/**
	 * servlet输出
	 * 
	 * @param response
	 * @param result
	 * @throws java.io.IOException
	 */
	public static void print(HttpServletResponse response, String result) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
        response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
        response.setHeader("expires", "0");
        PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	/**
	 * 输出plain文本
	 * 
	 * @param response
	 * @param result
	 * @throws java.io.IOException
	 */
	public static void write(HttpServletResponse response, String result) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	/**
	 * 输出plain文本
	 * 
	 * @param response
	 * @param object
	 * @throws java.io.IOException
	 */
	public static void write(HttpServletResponse response, Object object) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(JsonUtil.getJsonObject(object));
		out.flush();
		out.close();
	}

	public static <T> void printArrayList(HttpServletResponse response, List<T> list) throws IOException {
		print(response, JsonUtil.getJsonArray(list));
	}

	public static void printObjToJson(HttpServletResponse response, Object object) throws IOException {
		print(response, JsonUtil.getJsonObject(object));
	}
	
	public static void writeExcel(HttpServletResponse response, String filepath, String filename) {
		String contentType = "application/vnd.ms-excel;charset=gb2312";
		writeFile(response, filepath, filename, contentType);
	}

	public static void writeZip(HttpServletResponse response, String filePath) {
		String contentType = "application/x-zip-compressed;charset=gb2312";
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
		writeFile(response, filePath, fileName, contentType);
	}
	
	public static void writeZip(HttpServletResponse response, String filePath, String fileName) {
		String contentType = "application/x-zip-compressed;charset=gb2312";
		writeFile(response, filePath, fileName, contentType);
	}

	/**
	 * 输出文件
	 * 
	 * @param response
	 * @param filepath
	 * @param filename
	 * @param contentType
	 */
	public static void writeFile(HttpServletResponse response, String filepath, String filename, String contentType) {
		try {
			InputStream in = new FileInputStream(filepath);
			writeFile(response, in, filename, contentType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出文件
	 * 
	 * @param response
	 * @param in
	 * @param filename
	 * @param contentType
	 */
	public static void writeFile(HttpServletResponse response, InputStream in, String filename, String contentType) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setContentType(contentType);
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);

			int n = 0;
			byte b[] = new byte[1024];
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}
}
