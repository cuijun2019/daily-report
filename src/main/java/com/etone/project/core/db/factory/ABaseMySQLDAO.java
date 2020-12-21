package com.etone.project.core.db.factory;


import com.etone.project.core.db.conn.MySQLConnectionManager;

import java.sql.*;


/**
 * 一个基础DAO接口，其他DAO都必须实现该接口
 * Created with IntelliJ IDEA.
 * User: Guojian
 * Date: 14-9-01
 * Time: 上午11:18
 */
public abstract class ABaseMySQLDAO<K> {
    private static MySQLConnectionManager cm = MySQLConnectionManager.getInstance();

    /**
     * 获取连接
     * 
     * @return
     */
    protected Connection getConn() {
        return cm.getConnection();
    }

    /**
     * 释放连接
     * 
     * @return
     */
    protected void releaseConn(Connection conn) {
        try {
            conn.close();
        }
        catch (SQLException e) {
        }
    }

    /**
     * 关闭PreparedStatement
     * 
     * @param ps
     */
    protected void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            }
            catch (SQLException e) {
            }
            ps = null;
        }
    }
    
    /**
     * 关闭Statement
     * 
     * @param ps
     */
    protected void closeStatement(Statement ps) {
        if (ps != null) {
            try {
                ps.close();
            }
            catch (SQLException e) {
            }
            ps = null;
        }
    }

    /**
     * 关闭ResultSet
     * 
     * @param
     */
    protected void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
            }
            rs = null;
        }
    }
}
