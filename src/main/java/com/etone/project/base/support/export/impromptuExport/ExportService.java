package com.etone.project.base.support.export.impromptuExport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * User:chz
 * Date:13-8-20
 */
public class ExportService {
    private static Logger logger = LoggerFactory.getLogger(ExcelExporter.class);

    public static void export(HttpServletResponse response, String fullPath) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel");
        int index = fullPath.lastIndexOf('\\');
        if (index < 1)
            index = fullPath.lastIndexOf('/');
        String fileName = fullPath.substring(index+1);
        File file = new File(fullPath);
        // 清空response
        response.reset();
        // 设置response的Header
        //转码之后下载的文件不会出现中文乱码
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        try {
            //以流的形式下载文件
            InputStream fis = new BufferedInputStream(new FileInputStream(fullPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            BufferedOutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("响应导出流时，发生错误", e);
        }
    }
}
