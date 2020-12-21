package com.etone.project.base.support.export.impromptuExport;

import com.etone.commons.util.CsvUtils;
import com.etone.commons.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * CSV格式文件导出
 *
 * @author <a href="mailto:liushuanglong@etonetech.com">LiuShuanglong</a>
 *         DateTime: 13-9-12  下午2:40
 */
public class CsvExporter {
    private static final Logger logger = LoggerFactory.getLogger(CsvExporter.class);

    private CsvUtils csvUtils;

    public CsvExporter() {
        csvUtils = CsvUtils.getInstance();
    }

    /**
     * 导出csv格式文件
     * @param dataMaps 数据
     * @param description 说明
     * @param headersMap 表头映射
     * @param path 文件路径
     */
    public void exportCsvFile(List<Map> dataMaps, String description, LinkedHashMap<String,String> headersMap, String path) throws IOException {
        List<String> headers = new ArrayList<String>();
        List<String> columns = new ArrayList<String>();
        //初始化表头列和字段列
        if(headersMap == null || headersMap.isEmpty()){
            if (dataMaps != null && !dataMaps.isEmpty()){
                columns.addAll(dataMaps.get(0).keySet());
                logger.info("传入表头映射为空，采用默认表头");
            }else{
                logger.warn("表头映射不能为空");
                return;
            }
        }
        initHeadersAndColumns(headersMap, headers, columns);

        FileUtils.getInstance(path).deleteFile();
        CsvUtils.Wirter wirter = null;
        try {
            wirter = csvUtils.openWriter(path);
        } catch (FileNotFoundException e) {
            logger.warn("找不到文件！");
            e.printStackTrace();
            return;
        }

        // 写描述
        if (description != null && !description.isEmpty()) {
            wirter.writeLine(new String[]{description});
        }

        //写表头
        if(columns != null && !columns.isEmpty()){
            wirter.writeLine(parseHeadLine(columns));
        }else{
            wirter.writeLine(parseHeadLine(headers));
        }

        //逐行逐列写数据
        if(dataMaps == null || dataMaps.isEmpty()) return;
        for(Map dataMap : dataMaps){
            if(dataMap == null || dataMap.isEmpty()) continue;
            wirter.writeLine(parseLine(headers, dataMap));
        }

        csvUtils.closeWriter();
    }



    /**
     * 初始化表头列和字段列
     * @param headersMap
     * @param headers
     * @param columns
     */
    public void initHeadersAndColumns(LinkedHashMap<String,String> headersMap, List<String> headers, List<String> columns){
        for(String key : headersMap.keySet()){
            if (key == null || "".equals(key.trim())) continue;
            headers.add(key);
            columns.add(headersMap.get(key));
        }
    }

    /**
     * 生成列头
     *
     * @param headerList 列头信息
     * @return
     */
    private String[] parseHeadLine(List<String> headerList) {
        String[] actual = new String[headerList.size()];
        int index = 0;
        for (String key : headerList) {
            actual[index] = key;
            index++;
        }
        return actual;
    }

    /**
     * 生成数据行
     *
     * @param columns 列信息
     * @param row     翻译后的列内容
     * @return
     */
    public String[] parseLine(List<String> columns, Map row) {
        String[] actual = null;
        if (columns.size() > 0) {
            actual = new String[columns.size()];
            int index = 0;
            for (int i = 0; i < columns.size(); i++) {
                Object ovalue = row.get(columns.get(i));
                if (ovalue != null) {
                    String value = String.valueOf(ovalue);
                    if (value != null && value.indexOf("E-") != -1) {
                        try {
                            BigDecimal converter = new BigDecimal(value);
                            value = converter.toPlainString();
                        } catch (Exception ignored) {
                            logger.error("{} to BigDecimal error!", value);
                        }
                    }
                    actual[index] = value;
                }
                index++;
            }
        }
        return actual;
    }
}
