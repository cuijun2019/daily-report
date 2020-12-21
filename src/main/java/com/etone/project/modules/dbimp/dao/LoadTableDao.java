package com.etone.project.modules.dbimp.dao;

import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */

@Service
@Transactional
public class LoadTableDao implements ILoadTableDao {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoadTableDao.class);
    //@Autowired
    //private DataSource dataSource;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private static Connection conn = null;

    @Override
    public void test() {
        try {
            if(conn == null) {
                //conn = dataSource.getConnection();
                conn = sqlSessionTemplate.getConnection();
            }
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("show tables");

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoadTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InputStream getDataInputStream(List entityList) {

        if (entityList == null || entityList.isEmpty()) {
            return null;
        }
        Class<? extends Object> entityClass = entityList.get(0).getClass();
        Field[] fields = entityClass.getDeclaredFields();

        StringBuilder contentsBuilder = new StringBuilder();

        for (Object entity : entityList) {
            StringBuilder contentBuilder = new StringBuilder();

            for (int j = 0; j < fields.length; j++) {
                Field f = fields[j];

                //判断这一字段是否要导入
                ImportEntity annotation = f.getAnnotation(ImportEntity.class);
                if (annotation == null) {
                    continue;
                }

                if (contentBuilder.length() > 0) {
                    contentBuilder.append("\t");
                }

                String fieldname = f.getName();
                String getMethodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
                try {
                    Method getMethod = entityClass.getMethod(getMethodName);

                    Object valueObject = getMethod.invoke(entity);

                    if(valueObject == null || valueObject.toString().trim().equals("")) {
                        contentBuilder.append("NULL");
                    } else {
                        //坑爹的，布尔类型写成true or false还不能识别
                        if(valueObject instanceof Boolean) {
                            contentBuilder.append(valueObject.toString().toLowerCase().equals("true") ? "1" : "0");
                        } else {
                            contentBuilder.append(valueObject.toString());
                        }
                    }
                    

                } catch (Exception exception) {
                    logger.error(exception.getMessage() + ", has wrong: " + entityClass.getSimpleName()
                            + ", field:" + f.getName(), exception);
                }

            }

            if(contentsBuilder.length() > 0) {
                contentsBuilder.append("%\r\n");
            }
            
            contentsBuilder.append(contentBuilder.toString());

        }

        byte[] bytes = {1};
        try {
            bytes = contentsBuilder.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoadTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputStream is = new ByteArrayInputStream(bytes);

        return is;
    }


/**
     *将流中数据输出到数据库表
     * load bulk data from InputStream to MySQL
     */

    public int loadFromInputStream(String loadDataSql,
            InputStream dataStream) throws SQLException {
        if (dataStream == null) {
            logger.info("InputStream is null ,No data is imported");
            return 0;
        }

        if(conn == null || conn.isClosed()) {

            //conn = this.dataSource.getConnection();
            conn = sqlSessionTemplate.getConnection();
        }
        System.out.println(conn);
        
        PreparedStatement statement = conn.prepareStatement(loadDataSql);

        int result = 0;

        if (statement.isWrapperFor(com.mysql.jdbc.Statement.class)) {

            com.mysql.jdbc.PreparedStatement mysqlStatement = statement
                    .unwrap(com.mysql.jdbc.PreparedStatement.class);

            mysqlStatement.setLocalInfileInputStream(dataStream);
            result = mysqlStatement.executeUpdate();
            
            //关闭它
            mysqlStatement.close();
            statement.close();
            conn.close();
        }
        return result;
    }

    private String getTableColumnString(Class entityClass) {
        Field[] fields = entityClass.getDeclaredFields();

        StringBuilder sb = new StringBuilder();

        for (Field f : fields) {
            ImportEntity ie = f.getAnnotation(ImportEntity.class);
            if (ie == null) {
                continue;
            }

            if (sb.length() > 0) {
                sb.append(",");
            }

            sb.append(f.getName());
        }

        return sb.toString();
    }

    @Override
    public String insertTable(List entityList, String tableName) {

        if (entityList == null || entityList.isEmpty()) {
            return "lit is empty";
        }
        Class<? extends Object> entityClass = entityList.get(0).getClass();

        String testSql = "LOAD DATA LOCAL INFILE 'mretest.txt' REPLACE INTO TABLE " + tableName + " CHARACTER SET utf8  fields terminated by '\t' ENCLOSED BY '\t' LINES TERMINATED BY '%\r\n'  (" + this.getTableColumnString(entityClass) + ")";
        
        long beginTime = System.currentTimeMillis();
        InputStream dataStream = getDataInputStream(entityList);
        try {
            int rows = loadFromInputStream(testSql, dataStream);
            long endTime = System.currentTimeMillis();
            logger.info("imported " + rows + " rows data into " + tableName + " and cost " + (endTime - beginTime) + " ms!");
        } catch (SQLException e) {
            logger.info("import failure!");
            String message = e.getMessage();
            
            //如果表不存在，就抛出这个异常，会有专逻辑去创建这个表
            if(message.indexOf("doesn't exist") > 0) {
                throw new RuntimeException(message);
            } else {
                logger.error(message, e);
            }
        } finally{
        	try {
				dataStream.close();
				logger.info("close inputStream!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        return "success";
    }
    
    @Override
    public int insertTable(StringBuilder sb, List<String> columnList, String tableName) {
        if (sb == null || sb.length() == 0) {
            return -1;
        }

        long beginTime = System.currentTimeMillis();

        StringBuilder columnSb = new StringBuilder();

        for (String column : columnList) {
            columnSb.append(column).append(",");
        }
        columnSb.deleteCharAt(columnSb.length() - 1);


        String testSql = "LOAD DATA LOCAL INFILE 'mretest.txt' REPLACE INTO TABLE " + tableName + " CHARACTER SET gbk  fields terminated by '\t' ENCLOSED BY '\t' LINES TERMINATED BY '%\r\n'  (" + columnSb.toString() + ")";


        InputStream dataStream = new ByteArrayInputStream(sb.toString().getBytes());

        int rows = 0;
        try {
            rows = loadFromInputStream(testSql, dataStream);
            long endTime = System.currentTimeMillis();
            logger.info("imported " + rows + " rows data into " + tableName + " and cost " + ((double) (endTime - beginTime) / 1000) + " s!");
        } catch (SQLException e) {
            logger.info("import failure!");
            logger.error(e.getMessage(), e);
        } finally {
            try {
                dataStream.close();
                logger.info("close inputStream!");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rows;
    }

/**
     * 清空实体类entityClass对应的表数据
     */

    @Override
    public void clearTable(String tableName) {
        try {
            if(tableName == null || tableName.trim().isEmpty()) {
                return;
            }
            if (conn == null) {
                //conn = this.dataSource.getConnection();
                conn = sqlSessionTemplate.getConnection();
            }
            
            Statement st = conn.createStatement();
            
            String sql = "truncate table " + tableName;
            
            st.execute(sql);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
