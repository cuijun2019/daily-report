/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.meta;

import com.etone.project.base.support.SpringContext;
import com.etone.project.base.type.MetaKind;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 数据库元数据扫描工具
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14148 $$
 */
public class MetaScanner {
    // members
    private static final Logger logger = LoggerFactory.getLogger(MetaScanner.class);
    // 数据源名称
    private String scan;
    // 数据库名称
    private Map<String, List<String>> config = Maps.newHashMap();
    // static block

    // constructors

    // properties

    public void setScan(String scan) {
        this.scan = scan;
        initConfig();
    }

    public String getScan() {
        return scan;
    }

    // public methods

    /**
     * 基于JDBC获取元数据信息
     *
     * @return
     */
    public List<MetaInfo> getMetadata() {
        final List<MetaInfo> actual = Lists.newArrayList();
        execute(new Template() {
            @Override
            public void action(Connection conn, List<String> catalogs) throws SQLException {
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet catalogRs = metaData.getCatalogs();
                int catalogOrder = 0;
                // 获取数据库元数据
                while (catalogRs.next()) {
                    String catalogName = catalogRs.getString("TABLE_CAT");
                    MetaInfo catalogInfo = new MetaInfo(MetaKind.DATABAE, catalogName, catalogName, true, catalogOrder++, MetaKind.DATABAE.getValue());
                    actual.add(catalogInfo);
                    if (catalogs.contains(catalogName)) {
                        ResultSet tableRs = metaData.getTables(catalogName, null, null, new String[]{"TABLE"});
                        int tableOrder = 0;
                        // 获取表元数据
                        while (tableRs.next()) {
                            catalogInfo.leaf = false;
                            String tableName = tableRs.getString("TABLE_NAME");
                            MetaInfo tableInfo = new MetaInfo(MetaKind.TABLE, catalogName, tableName, tableName, true, tableOrder++, MetaKind.TABLE.getValue());
                            actual.add(tableInfo);
                            ResultSet columnRs = metaData.getColumns(catalogName, null, tableName, null);
                            int columnOrder = 0;
                            // 获取字段元数据
                            while (columnRs.next()) {
                                tableInfo.leaf = false;
                                String columnName = columnRs.getString("COLUMN_NAME");
                                MetaInfo columnInfo = new MetaInfo(MetaKind.FIELD, catalogName, tableName, columnName, columnName, true, columnOrder++, MetaKind.FIELD.getValue());
                                actual.add(columnInfo);
                            }
                        }
                    }
                }
            }
        });
        return actual;
    }
    // protected methods

    // friendly methods

    // private methods

    /**
     * 初始数据源
     */
    private void initConfig() {
        // 格式：数据源1:数据库1|数据库2|..|数据库n,数据源2:数据库1|数据库2|..|数据库n
        if (!Strings.isNullOrEmpty(scan)) {
            StringTokenizer configs = new StringTokenizer(scan, ",");
            while (configs.hasMoreElements()) {
                String[] pair = configs.nextToken().split(":");
                String datasource = pair[0];
                String[] catalogs = pair[1].split(",");
                config.put(datasource, Arrays.asList(catalogs));
            }
        }
    }

    /**
     * 执行查询
     *
     * @param template 查询模板
     */
    private void execute(Template template) {
        if (!config.isEmpty()) {
            for (String dataSource : config.keySet()) {
                if (SpringContext.containsBean(dataSource)) {
                    DataSource source = (DataSource) SpringContext.getBean(dataSource);
                    Connection connection = null;
                    try {
                        connection = source.getConnection();
                        template.action(source.getConnection(), config.get(dataSource));
                    } catch (SQLException e) {
                        logger.error("get connection error: ", connection);
                    } finally {
                        close(connection);
                    }
                }
            }
        }
    }

    /**
     * 关闭连接
     *
     * @param connection 连接
     */
    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("close connection error: ", e);
        }
    }

    // inner class
    public interface Template {
        void action(Connection conn, List<String> catalogs) throws SQLException;
    }
    // test main
}
